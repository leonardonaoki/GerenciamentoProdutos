package com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.processors.impl;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.processors.IProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
@RequiredArgsConstructor
public class CsvProcessor implements IProcessor {
    private final JobLauncher jobLauncher;
    private final Job importProductsJob;
    @Override
    public void Process(MultipartFile file) throws Exception {
        // Criar um arquivo temporário
        File tempFile = File.createTempFile("BatchProcess-", ".csv");
        try (InputStream fileInputStream = file.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            fileInputStream.transferTo(outputStream);
        }

        // Executar o job de Spring Batch com o caminho do arquivo como parâmetro
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("filePath", tempFile.getAbsolutePath())
                .toJobParameters();

        jobLauncher.run(importProductsJob, jobParameters);
    };
}
