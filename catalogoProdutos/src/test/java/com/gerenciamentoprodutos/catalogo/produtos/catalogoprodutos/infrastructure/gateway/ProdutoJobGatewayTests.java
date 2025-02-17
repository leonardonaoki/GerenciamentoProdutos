package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.gateway;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ResponseDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.entity.CsvFile;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.exception.SystemBaseHandleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

class ProdutoJobGatewayTests {
    private IProdutoJobGateway produtoJobGateway;
    @Mock
    private JobLauncher joblauncher;
    @Mock
    private Job job;
    @Mock
    private CsvFile csvFile;
    private JobExecution jobExecution;
    @BeforeEach
    void setUp() {
        // Inicializa os mocks do Mockito
        MockitoAnnotations.openMocks(this);
        produtoJobGateway = new ProdutoJobGateway(joblauncher,job);
        csvFile = new CsvFile("dados.csv", "C://Entrada", new byte[] {});
        jobExecution = new JobExecution(1L);
    }
    @Test
    void deveExecutarOJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException, SystemBaseHandleException {
        jobExecution.setStatus(BatchStatus.COMPLETED);
        Mockito.when(joblauncher.run(any(), any())).thenReturn(jobExecution);

        ResponseDTO response = produtoJobGateway.execute(csvFile);

        assertEquals(HttpStatus.OK,HttpStatus.valueOf(response.HttpStatusCode()));
    }
    @Test
    void deveExibirUmErroEmCasoDeTimeOut() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        jobExecution.setStatus(BatchStatus.STARTED);
        Mockito.when(joblauncher.run(any(), any())).thenReturn(jobExecution);
        assertThrows(SystemBaseHandleException.class, () -> produtoJobGateway.execute(csvFile));
    }
    @Test
    void deveExibirUmResponseComErroNoCasoDeStatusInesperado() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        jobExecution.setStatus(BatchStatus.UNKNOWN);
        Mockito.when(joblauncher.run(any(), any())).thenReturn(jobExecution);

        assertThrows(SystemBaseHandleException.class, () -> produtoJobGateway.execute(csvFile));
    }
    @Test
    void deveRetornarUmErroSeArquivoNaoExistir() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        jobExecution.setStatus(BatchStatus.UNKNOWN);
        doThrow(new JobInstanceAlreadyCompleteException("")).when(joblauncher).run(any(), any());

        assertThrows(SystemBaseHandleException.class, () -> produtoJobGateway.execute(csvFile));
    }
}
