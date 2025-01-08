package com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.job.config;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.infrastructure.entityjpa.ProdutoEntity;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.job.mapper.ProdutoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ProdutoBatchConfig {

    @Value("${carga.input-path}")
    private String diretorioEntrada;

    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job importarProduto(
            Step stepImportacao,
            JobRepository jobRepository
    )
    {
        try{
            return new JobBuilder("importarProduto", jobRepository)
                    .incrementer(new RunIdIncrementer())
                    .start(stepImportacao)
                    .build();
        }
        catch(Exception e){
            throw new RuntimeException("Não foi possível executar o job: " + e.toString());
        }

    }
    @Bean
    public Step stepImportacao(JobRepository jobRepository,
                     ItemReader<ProdutoEntity> itemReader,
                     ItemWriter<ProdutoEntity> itemWriter){
        try{
            return new StepBuilder("Step de Importacao", jobRepository)
                    .<ProdutoEntity,ProdutoEntity>chunk(20,transactionManager)
                    .reader(itemReader)
                    .writer(itemWriter)
                    .allowStartIfComplete(true)
                    .build();
        }
        catch(Exception e){
            throw new RuntimeException("Não foi possível realizar o step de importação" + e.toString());
        }
    }
    @Bean
    public ItemReader<ProdutoEntity> itemReader(){
        try{
            return new FlatFileItemReaderBuilder<ProdutoEntity>()
                    .name("Leitura-csv")
                    .resource(new FileSystemResource(diretorioEntrada + "/dados.csv"))
                    .comments("--")
                    .delimited()
                    .delimiter(";")
                    .names("descricao","preco","quantidadeEstoque")
                    .fieldSetMapper(new ProdutoMapper())
                    .build();
        }catch(Exception e){
            throw new RuntimeException("Não foi ler os registros do arquivo: " + e.toString());
        }
    }
    @Bean
    public ItemWriter<ProdutoEntity> itemWriter(DataSource dataSource){
        try{
            return new JdbcBatchItemWriterBuilder<ProdutoEntity>()
                    .dataSource(dataSource)
                    .sql("INSERT INTO produto"+
                            "(descricao,preco,quantidade_estoque) " +
                            "VALUES (:descricao, :preco, :quantidadeEstoque) "
                    )
                    .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                    .build();
        }
        catch(Exception e){
            throw new RuntimeException("Não foi possível adicionar os registros na base de dados: " + e.toString());
        }
    }

    /* Step - Mover os arquivos para processado para não ficar poluido */
}