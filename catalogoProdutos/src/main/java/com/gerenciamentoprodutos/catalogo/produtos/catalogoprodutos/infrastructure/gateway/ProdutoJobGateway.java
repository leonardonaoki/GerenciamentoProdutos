package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.gateway;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ResponseDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.entity.CsvFile;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.exception.SystemBaseHandleException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProdutoJobGateway implements IProdutoJobGateway{
    private final JobLauncher jobLauncher;
    private final Job job;
    private static final long TIMEOUT = 10000;
    @Autowired
    public ProdutoJobGateway(
            JobLauncher jobLauncher,
            Job importarProdutoJob) {
        this.jobLauncher = jobLauncher;
        this.job = importarProdutoJob;
    }


    @Override
    public ResponseDTO execute(CsvFile csvFile) throws SystemBaseHandleException{
        try {
            JobParameters jobParameters = new JobParameters();
            long startTime = System.currentTimeMillis();
            JobExecution execution = jobLauncher.run(job, jobParameters);
            while (execution.isRunning() && (System.currentTimeMillis() - startTime) < TIMEOUT) {
                log.info("Job em execução...");
            }
            if (execution.isRunning()) {
                throw new SystemBaseHandleException("O Job ultrapassou o limite de timeout.");
            }

            if(execution.getStatus() == BatchStatus.COMPLETED)
                return new ResponseDTO(HttpStatus.OK.value(),"Importação executada com sucesso!");

            throw new SystemBaseHandleException("Código: " + execution.getExitStatus().getExitCode() + " -> " + execution.getExitStatus().getExitDescription());
        } catch (SystemBaseHandleException|
                 JobInstanceAlreadyCompleteException|
                 JobExecutionAlreadyRunningException|
                 JobParametersInvalidException|
                 JobRestartException
                e) {
            log.error("Erro" + e.getMessage());
            throw new SystemBaseHandleException("Ocorreu um erro ao processar o arquivo." + e.getMessage());
        }
    }
}
