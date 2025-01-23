package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.gateway;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.consumer.AtualizacaoProdutosDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.request.InsertAndUpdateProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.mapper.IProdutoMapper;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.entityjpa.ProdutosEntity;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.repository.IProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProdutoGateway implements IProdutoGateway{
    private static final String ERROR_MESSAGE = "Não foi possível identificar o produto com o ID ";
    private final IProdutoRepository produtoRepository;
    private final IProdutoMapper produtoMapper;
    @Override
    public Page<ProdutoDTO> listarProdutos(int offset,int limit) {
        return produtoRepository.findAll(PageRequest.of(offset,limit)).map(produtoMapper::toDTO);
    }

    @Override
    public ProdutoDTO listarProdutoPorId(long id){
        return produtoRepository.findById(id).map(produtoMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException(ERROR_MESSAGE + id));
    }

    @Override
    public ProdutoDTO criarProduto(InsertAndUpdateProdutoDTO dto) {
        ProdutosEntity produtoSalvo = produtoRepository.save(produtoMapper.toEntity(dto));
        return produtoMapper.toDTO(produtoSalvo);
    }

    @Override
    public ProdutoDTO atualizarProdutoPorId(long id,InsertAndUpdateProdutoDTO dto){
        ProdutosEntity produtoEncontrado = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ERROR_MESSAGE + id));
        produtoEncontrado.setDescricao(dto.Descricao());
        produtoEncontrado.setPreco(dto.Preco());
        produtoEncontrado.setQuantidadeEstoque(dto.QuantidadeEstoque());
        return produtoMapper.toDTO(produtoRepository.save(produtoEncontrado));
    }
    @Override
    public void atualizarListaProdutos(AtualizacaoProdutosDTO dto){
        boolean baixarEstoque = dto.acao().toUpperCase().contains("BAIXAR");
        List<Long> listaIds = dto.listaProdutos().stream()
                .map(p -> p.idProduto())
                .toList();
        List<ProdutosEntity> listaProdutos = produtoRepository.findAllById(listaIds);

        dto.listaProdutos().forEach(produtoDTO ->
            listaProdutos.stream()
                    .filter(produtoEntity -> produtoEntity.getId() == produtoDTO.idProduto())
                    .findFirst()
                    .ifPresent(produtoEntity -> {
                        long novaQuantidade = baixarEstoque?
                                produtoEntity.getQuantidadeEstoque() - produtoDTO.quantidadeDesejada() :
                                produtoEntity.getQuantidadeEstoque() + produtoDTO.quantidadeDesejada();
                        produtoEntity.setQuantidadeEstoque(novaQuantidade);
                    })
        );

        produtoRepository.saveAll(listaProdutos);
    }

    @Override
    public void deletarProdutoPorId(long id){
        if(produtoRepository.existsById(id))
            produtoRepository.deleteById(id);
        else
            throw new EntityNotFoundException(ERROR_MESSAGE + id);
    }
}
