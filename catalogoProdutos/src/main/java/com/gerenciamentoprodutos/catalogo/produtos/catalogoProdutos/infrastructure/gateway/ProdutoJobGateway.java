package com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.infrastructure.gateway;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.domain.dto.ResponseDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.domain.entity.CsvFile;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.infrastructure.gateway.exception.ErroAoProcessarJobException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProdutoJobGateway implements IProdutoJobGateway{
    private JobLauncher jobLauncher;
    private Job job;
    private static final long TIMEOUT = 60000;
    @Autowired
    public ProdutoJobGateway(
            JobLauncher jobLauncher,
            Job importarProdutoJob) {
        this.jobLauncher = jobLauncher;
        this.job = importarProdutoJob;
    }


    @Override
    public ResponseEntity<?> execute(CsvFile csvFile) {
        try {
            JobParameters jobParameters = new JobParameters();

            long startTime = System.currentTimeMillis();
            JobExecution execution = jobLauncher.run(job, jobParameters);
            while (execution.isRunning() && (System.currentTimeMillis() - startTime) < TIMEOUT) {
                log.info("Job em execução...");
                Thread.sleep(5000);
            }
            if (execution.isRunning()) {
                return ResponseEntity.internalServerError().body(new ResponseDTO("O Job ultrapassou o limite de timeout."));
            }

            if(execution.getStatus() == BatchStatus.COMPLETED)
                return ResponseEntity.ok().body(new ResponseDTO("Importação executada com sucesso!"));

            throw new ErroAoProcessarJobException();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ErroAoProcessarJobException();
        }
    }
}
