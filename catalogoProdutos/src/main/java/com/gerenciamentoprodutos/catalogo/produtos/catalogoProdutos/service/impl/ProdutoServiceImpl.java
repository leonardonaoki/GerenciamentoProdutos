package com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.service.impl;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.dto.ProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.infrastructure.repository.IProdutoRepository;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.mapper.IProdutoMapper;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.processors.IProcessor;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.processors.ProcessorFactory;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {
    private final IProdutoRepository produtoRepo;
    private final IProdutoMapper IProdutoMapper;

    @Override
    public List<ProdutoDTO> listarProdutos(){
        return produtoRepo.findAll().stream().map(p -> IProdutoMapper.toDTO(p)).toList();
    }
    @Override
    public ResponseEntity<?> importarProdutosBatch(MultipartFile file){
        if(file.isEmpty())
            return ResponseEntity.badRequest().body("Arquivo vazio!");

        String nomeDoArquivo = file.getOriginalFilename();
        if (nomeDoArquivo == null || !nomeDoArquivo.contains(".")) {
            return ResponseEntity.badRequest().body("Arquivo inválido!");
        }

        String fileExtension = nomeDoArquivo.substring(nomeDoArquivo.lastIndexOf('.') + 1).toUpperCase();
        IProcessor processor = ProcessorFactory.getProcessor(fileExtension);
        if(processor == null)
            return ResponseEntity.badRequest().body("Extensão de arquivo inválida!");
        try{
            processor.Process(file);
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Não foi possível processar o arquivo!");
        }
        return ResponseEntity.ok("Sucesso");
    }
}
