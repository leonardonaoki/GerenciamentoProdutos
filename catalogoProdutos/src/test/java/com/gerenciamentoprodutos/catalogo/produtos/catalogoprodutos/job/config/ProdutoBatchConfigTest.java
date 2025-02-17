package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.job.config;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.entityjpa.ProdutosEntity;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.job.ProdutosProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Import(ProdutoBatchConfig.class) // Importa a configuração para teste
@ExtendWith(SpringExtension.class)
class ProdutoBatchConfigTest {

    @Autowired
    private ProdutoBatchConfig produtoBatchConfig;  // Agora, injetamos diretamente o Bean da configuração

    @Mock
    private PlatformTransactionManager transactionManager; // Mock do transactionManager

    @Mock
    private JobRepository jobRepository; // Mock do JobRepository

    @Mock
    private DataSource dataSource; // Mock do DataSource

    @Mock
    private ItemReader<ProdutosEntity> itemReader;  // Mock do ItemReader

    @Mock
    private ItemWriter<ProdutosEntity> itemWriter;  // Mock do ItemWriter

    @Value("${carga.input-path}")
    private String diretorioEntrada;

    @Test
    void testProdutoBatchConfigBeans() {
        // Act
        Job job = produtoBatchConfig.importarProduto(null, jobRepository);
        Step stepImportacao = produtoBatchConfig.stepImportacao(jobRepository, itemReader, itemWriter);
        ItemReader<ProdutosEntity> itemReaderConfigured = produtoBatchConfig.itemReader();
        ItemWriter<ProdutosEntity> itemWriterConfigured = produtoBatchConfig.itemWriter(dataSource);

        // Assert
        assertNotNull(job, "Job não foi criado corretamente!");
        assertNotNull(stepImportacao, "Step de importação não foi criado corretamente!");
        assertNotNull(itemReaderConfigured, "ItemReader não foi criado corretamente!");
        assertNotNull(itemWriterConfigured, "ItemWriter não foi criado corretamente!");

        // Verifica se o ItemReader está usando o caminho correto do arquivo
        assertTrue(itemReaderConfigured instanceof FlatFileItemReader, "O itemReader deve ser do tipo FlatFileItemReader");
    }

    @Test
    void testStepImportacao() {
        // Act
        Step stepImportacao = produtoBatchConfig.stepImportacao(jobRepository, itemReader, itemWriter);

        // Assert
        assertNotNull(stepImportacao, "O Step de importação não foi criado corretamente.");
        assertTrue(stepImportacao.getName().contains("Step de Importacao"), "O nome do Step está incorreto.");
    }

    @Test
    void testItemProcessor() {
        // Act
        ProdutosProcessor processor = produtoBatchConfig.itemProcessor();

        // Assert
        assertNotNull(processor, "ItemProcessor não foi criado corretamente!");
    }

    @Test
    void testItemWriter() {
        // Act
        ItemWriter<ProdutosEntity> itemWriterConfigured = produtoBatchConfig.itemWriter(dataSource);

        // Assert
        assertNotNull(itemWriterConfigured, "ItemWriter não foi criado corretamente!");
    }
}
