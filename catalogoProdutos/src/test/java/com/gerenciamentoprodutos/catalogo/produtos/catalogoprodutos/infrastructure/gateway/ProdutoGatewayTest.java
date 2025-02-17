package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.gateway;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.entity.ProdutosDomain;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.entity.consumer.AtualizacaoProdutosDomain;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.entity.consumer.ListaProdutosDomain;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.mapper.IProdutoMapper;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.entityjpa.ProdutosEntity;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.repository.IProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoGatewayTest {

    @Mock
    private IProdutoRepository produtoRepository;

    @Mock
    private IProdutoMapper produtoMapper;

    @InjectMocks
    private ProdutoGateway produtoGateway;

    @Test
    void listarProdutosDeveRetornarPageDeProdutoDTOs() {
        // Arrange
        int offset = 0;
        int limit = 10;
        PageRequest pageRequest = PageRequest.of(offset, limit);

        List<ProdutosEntity> produtos = List.of(new ProdutosEntity(), new ProdutosEntity());
        Page<ProdutosEntity> produtosPage = new PageImpl<>(produtos);

        when(produtoRepository.findAll(pageRequest)).thenReturn(produtosPage);
        when(produtoMapper.toDTO(any(ProdutosEntity.class))).thenReturn(new ProdutoDTO(1,"DescricaoTeste",new BigDecimal(100),300));

        // Act
        Page<ProdutoDTO> resultado = produtoGateway.listarProdutos(offset, limit);

        // Assert
        assertNotNull(resultado);
        assertEquals(produtos.size(), resultado.getContent().size());
        verify(produtoRepository).findAll(pageRequest);
        verify(produtoMapper, times(produtos.size())).toDTO(any(ProdutosEntity.class));
    }

    @Test
    void listarProdutoPorIdDeveRetornarProdutoDTOQuandoProdutoExiste(){
        // Arrange
        long id = 1L;
        ProdutosEntity produtoEntity = new ProdutosEntity();
        ProdutoDTO produtoDTO = new ProdutoDTO(id,"DescricaoTeste",new BigDecimal(100),300);

        when(produtoRepository.findById(id)).thenReturn(Optional.of(produtoEntity));
        when(produtoMapper.toDTO(produtoEntity)).thenReturn(produtoDTO);

        // Act
        ProdutoDTO resultado = produtoGateway.listarProdutoPorId(id);

        // Assert
        assertNotNull(resultado);
        assertEquals(produtoDTO, resultado);
        verify(produtoRepository).findById(id);
        verify(produtoMapper).toDTO(produtoEntity);
    }

    @Test
    void listarProdutoPorIdDeveLancarExcecaoQuandoProdutoNaoExiste() {
        // Arrange
        long id = 1L;
        when(produtoRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> produtoGateway.listarProdutoPorId(id));
        assertEquals("Não foi possível identificar o produto com o ID " + id, exception.getMessage());
        verify(produtoRepository).findById(id);
    }

    @Test
    void criarProdutoDeveRetornarProdutoDTO() {
        // Arrange

        ProdutosDomain domain = mock(ProdutosDomain.class);
        ProdutosEntity produtoSalvo = new ProdutosEntity();
        ProdutoDTO produtoDTO = new ProdutoDTO(1,"DescricaoTeste",new BigDecimal(100),300);

        when(produtoMapper.toEntity(domain)).thenReturn(produtoSalvo);
        when(produtoRepository.save(produtoSalvo)).thenReturn(produtoSalvo);
        when(produtoMapper.toDTO(produtoSalvo)).thenReturn(produtoDTO);

        // Act
        ProdutoDTO resultado = produtoGateway.criarProduto(domain);

        // Assert
        assertNotNull(resultado);
        assertEquals(produtoDTO, resultado);
        verify(produtoMapper).toEntity(domain);
        verify(produtoRepository).save(produtoSalvo);
        verify(produtoMapper).toDTO(produtoSalvo);
    }

    @Test
    void atualizarProdutoPorIdDeveRetornarProdutoDTOQuandoProdutoExiste(){
        // Arrange
        long id = 1L;
        ProdutosDomain domain = mock(ProdutosDomain.class);

        ProdutosEntity produtoEncontrado = new ProdutosEntity();
        ProdutosEntity produtoAtualizado = new ProdutosEntity();
        ProdutoDTO produtoDTO = new ProdutoDTO(1,"DescricaoTeste",new BigDecimal(100),300);

        when(produtoRepository.findById(id)).thenReturn(Optional.of(produtoEncontrado));
        when(produtoRepository.save(produtoEncontrado)).thenReturn(produtoAtualizado);
        when(produtoMapper.toDTO(produtoAtualizado)).thenReturn(produtoDTO);

        // Act
        ProdutoDTO resultado = produtoGateway.atualizarProdutoPorId(id, domain);

        // Assert
        assertNotNull(resultado);
        assertEquals(produtoDTO, resultado);
        verify(produtoRepository).findById(id);
        verify(produtoRepository).save(produtoEncontrado);
        verify(produtoMapper).toDTO(produtoAtualizado);
    }

    @Test
    void atualizarProdutoPorIdDeveLancarExcecaoQuandoProdutoNaoExiste() {
        // Arrange
        long id = 1L;
        ProdutosDomain domain = mock(ProdutosDomain.class);

        when(produtoRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> produtoGateway.atualizarProdutoPorId(id, domain));
        assertEquals("Não foi possível identificar o produto com o ID " + id, exception.getMessage());
        verify(produtoRepository).findById(id);
    }

    @Test
    void deletarProdutoPorIdDeveExcluirProdutoQuandoProdutoExiste(){
        // Arrange
        long id = 1L;
        when(produtoRepository.existsById(id)).thenReturn(true);

        // Act
        produtoGateway.deletarProdutoPorId(id);

        // Assert
        verify(produtoRepository).existsById(id);
        verify(produtoRepository).deleteById(id);
    }

    @Test
    void deletarProdutoPorIdDeveLancarExcecaoQuandoProdutoNaoExiste() {
        // Arrange
        long id = 1L;
        when(produtoRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> produtoGateway.deletarProdutoPorId(id));
        assertEquals("Não foi possível identificar o produto com o ID " + id, exception.getMessage());
        verify(produtoRepository).existsById(id);
    }

    @Test
    void atualizarListaProdutosDeveAtualizarQuantidadeEstoque() {
        // Arrange
        AtualizacaoProdutosDomain atualizacaoProdutosDomain = mock(AtualizacaoProdutosDomain.class);
        when(atualizacaoProdutosDomain.acao()).thenReturn("BAIXAR");

        // Agora instanciamos ListaProdutosDomain ao invés de InsertAndUpdateProdutoDTO
        List<ListaProdutosDomain> listaProdutos = List.of(
                new ListaProdutosDomain(1L, 10), // idProduto 1, quantidadeDesejada 10
                new ListaProdutosDomain(2L, 5)   // idProduto 2, quantidadeDesejada 5
        );
        when(atualizacaoProdutosDomain.listaProdutos()).thenReturn(listaProdutos);

        // Mockando os produtos na base
        ProdutosEntity produto1 = new ProdutosEntity();
        produto1.setId(1L);
        produto1.setQuantidadeEstoque(50);
        ProdutosEntity produto2 = new ProdutosEntity();
        produto2.setId(2L);
        produto2.setQuantidadeEstoque(100);

        when(produtoRepository.findAllById(List.of(1L, 2L))).thenReturn(List.of(produto1, produto2));

        // Act
        produtoGateway.atualizarListaProdutos(atualizacaoProdutosDomain);

        // Assert
        assertEquals(40, produto1.getQuantidadeEstoque());  // 50 - 10 = 40
        assertEquals(95, produto2.getQuantidadeEstoque());  // 100 - 5 = 95
        verify(produtoRepository).saveAll(List.of(produto1, produto2));
    }

    @Test
    void atualizarListaProdutosDeveAtualizarQuantidadeEstoqueQuandoAcaoRepor() {
        // Arrange
        AtualizacaoProdutosDomain atualizacaoProdutosDomain = mock(AtualizacaoProdutosDomain.class);
        when(atualizacaoProdutosDomain.acao()).thenReturn("REPOR");

        // Agora instanciamos ListaProdutosDomain ao invés de InsertAndUpdateProdutoDTO
        List<ListaProdutosDomain> listaProdutos = List.of(
                new ListaProdutosDomain(1L, 10), // idProduto 1, quantidadeDesejada 10
                new ListaProdutosDomain(2L, 5)   // idProduto 2, quantidadeDesejada 5
        );
        when(atualizacaoProdutosDomain.listaProdutos()).thenReturn(listaProdutos);

        // Mockando os produtos na base
        ProdutosEntity produto1 = new ProdutosEntity();
        produto1.setId(1L);
        produto1.setQuantidadeEstoque(50);
        ProdutosEntity produto2 = new ProdutosEntity();
        produto2.setId(2L);
        produto2.setQuantidadeEstoque(100);

        when(produtoRepository.findAllById(List.of(1L, 2L))).thenReturn(List.of(produto1, produto2));
        // Act
        produtoGateway.atualizarListaProdutos(atualizacaoProdutosDomain);

        // Assert
        assertEquals(60, produto1.getQuantidadeEstoque());  // 50 - 10 = 40
        assertEquals(105, produto2.getQuantidadeEstoque());  // 100 - 5 = 95
        verify(produtoRepository).saveAll(List.of(produto1, produto2));
    }
}
