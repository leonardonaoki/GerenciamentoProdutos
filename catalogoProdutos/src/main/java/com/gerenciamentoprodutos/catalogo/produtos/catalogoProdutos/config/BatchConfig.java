package com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.config;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.entities.ProdutoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.InputStream;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    public Job importarProduto(JobRepository jobRepository,Step step){
        return new JobBuilder("importarProduto", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }
    @Bean
    public Step step(JobRepository jobRepository,
                     PlatformTransactionManager platformTransactionManager,
                     ItemReader<ProdutoEntity> itemReader,
                     ItemWriter<ProdutoEntity> itemWriter){
        return new StepBuilder("step", jobRepository)
                .<ProdutoEntity,ProdutoEntity>chunk(10,platformTransactionManager)
                .reader(itemReader)
                .writer(itemWriter)
                .build();
    }

    public ItemReader<ProdutoEntity> itemReader(Resource inputStreamSource){
        BeanWrapperFieldSetMapper<ProdutoEntity> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(ProdutoEntity.class);
        return new FlatFileItemReaderBuilder<ProdutoEntity>()
                .name("produtoItemReader")
                .resource(inputStreamSource)
                .delimited()
                .names("descricao","preco","quantidade_estoque")
                .fieldSetMapper(fieldSetMapper)
                .build();
    }

    public ItemWriter<ProdutoEntity> itemWriter(){
        return null;
    }
}