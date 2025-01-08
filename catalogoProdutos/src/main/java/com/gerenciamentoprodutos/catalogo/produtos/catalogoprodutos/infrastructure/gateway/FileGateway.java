package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.gateway;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.entity.CsvFile;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.exception.SystemBaseHandleException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Component
public class FileGateway implements IFileGateway {
    @Override
    public void salvar(CsvFile csvFile) throws SystemBaseHandleException {
        try {
            Path caminhoArquivo = Paths.get(csvFile.getDiretorio(), csvFile.getNomeArquivo());
            Files.createDirectories(caminhoArquivo.getParent());
            Files.write(caminhoArquivo, csvFile.getBinario());
            if(Files.notExists(caminhoArquivo)){
                throw new SystemBaseHandleException("O Arquivo não foi criado corretamente");
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new SystemBaseHandleException("Ocorreu um erro ao tentar criar o arquivo temporário.Mensagem original:" + e.getMessage());
        }
    }
}
