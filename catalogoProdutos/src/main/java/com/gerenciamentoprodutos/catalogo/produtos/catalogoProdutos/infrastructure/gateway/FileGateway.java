package com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.infrastructure.gateway;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.domain.entity.CsvFile;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.infrastructure.gateway.exception.SalvarArquivoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Component
public class FileGateway implements IFileGateway {
    @Override
    public void salvar(CsvFile csvFile) {
        try {
            Path caminhoArquivo = Paths.get(csvFile.getDiretorio(), csvFile.getNomeArquivo());
            Files.createDirectories(caminhoArquivo.getParent());
            Files.write(caminhoArquivo, csvFile.getBinario());
            if(Files.notExists(caminhoArquivo)){
                throw new SalvarArquivoException();
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new SalvarArquivoException();
        }
    }
}
