package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.gateway;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.request.InsertAndUpdateProdutoDTO;
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
        InsertAndUpdateProdutoDTO dto = mock(InsertAndUpdateProdutoDTO.class);
        ProdutosEntity produtoSalvo = new ProdutosEntity();
        ProdutoDTO produtoDTO = new ProdutoDTO(1,"DescricaoTeste",new BigDecimal(100),300);

        when(produtoMapper.toEntity(dto)).thenReturn(produtoSalvo);
        when(produtoRepository.save(produtoSalvo)).thenReturn(produtoSalvo);
        when(produtoMapper.toDTO(produtoSalvo)).thenReturn(produtoDTO);

        // Act
        ProdutoDTO resultado = produtoGateway.criarProduto(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals(produtoDTO, resultado);
        verify(produtoMapper).toEntity(dto);
        verify(produtoRepository).save(produtoSalvo);
        verify(produtoMapper).toDTO(produtoSalvo);
    }

    @Test
    void atualizarProdutoPorIdDeveRetornarProdutoDTOQuandoProdutoExiste(){
        // Arrange
        long id = 1L;
        InsertAndUpdateProdutoDTO dto = mock(InsertAndUpdateProdutoDTO.class);

        ProdutosEntity produtoEncontrado = new ProdutosEntity();
        ProdutosEntity produtoAtualizado = new ProdutosEntity();
        ProdutoDTO produtoDTO = new ProdutoDTO(1,"DescricaoTeste",new BigDecimal(100),300);

        when(produtoRepository.findById(id)).thenReturn(Optional.of(produtoEncontrado));
        when(produtoRepository.save(produtoEncontrado)).thenReturn(produtoAtualizado);
        when(produtoMapper.toDTO(produtoAtualizado)).thenReturn(produtoDTO);

        // Act
        ProdutoDTO resultado = produtoGateway.atualizarProdutoPorId(id, dto);

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
        InsertAndUpdateProdutoDTO dto = mock(InsertAndUpdateProdutoDTO.class);

        when(produtoRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> produtoGateway.atualizarProdutoPorId(id, dto));
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
}
