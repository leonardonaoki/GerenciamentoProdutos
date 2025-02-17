package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.job.config;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.entityjpa.ProdutosEntity;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.job.ProdutosProcessor;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.job.mapper.JobProdutoMapper;
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
            return new JobBuilder("importarProduto", jobRepository)
                    .incrementer(new RunIdIncrementer())
                    .start(stepImportacao)
                    .build();

    }
    @Bean
    public Step stepImportacao(JobRepository jobRepository,
                     ItemReader<ProdutosEntity> itemReader,
                     ItemWriter<ProdutosEntity> itemWriter){
            return new StepBuilder("Step de Importacao", jobRepository)
                    .<ProdutosEntity, ProdutosEntity>chunk(20,transactionManager)
                    .reader(itemReader)
                    .processor(itemProcessor())
                    .writer(itemWriter)
                    .allowStartIfComplete(true)
                    .build();
    }
    @Bean
    public ItemReader<ProdutosEntity> itemReader(){
            return new FlatFileItemReaderBuilder<ProdutosEntity>()
                    .name("Leitura-csv")
                    .resource(new FileSystemResource(diretorioEntrada + "/dados.csv"))
                    .comments("--")
                    .delimited()
                    .delimiter(";")
                    .names("descricao","preco","quantidadeEstoque")
                    .fieldSetMapper(new JobProdutoMapper())
                    .build();
    }
    @Bean
    public ProdutosProcessor itemProcessor(){
        return new ProdutosProcessor();
    }
    @Bean
    public ItemWriter<ProdutosEntity> itemWriter(DataSource dataSource){
            ProdutoBatchCommands commands = new ProdutoBatchCommands();
            return new JdbcBatchItemWriterBuilder<ProdutosEntity>()
                    .dataSource(dataSource)
                    .sql(commands.getInsertCommand())
                    .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                    .build();
    }
}