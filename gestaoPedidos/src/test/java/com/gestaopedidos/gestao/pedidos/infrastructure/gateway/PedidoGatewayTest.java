package com.gestaopedidos.gestao.pedidos.infrastructure.gateway;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.gestaopedidos.gestao.pedidos.domain.dto.PedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.ProdutoDTO;
import com.gestaopedidos.gestao.pedidos.domain.entity.InsertPedidoDomain;
import com.gestaopedidos.gestao.pedidos.domain.entity.UpdatePedidoDomain;
import com.gestaopedidos.gestao.pedidos.domain.enums.AcaoEstoqueEnum;
import com.gestaopedidos.gestao.pedidos.domain.enums.StatusEnum;
import com.gestaopedidos.gestao.pedidos.domain.mapper.IItensPedidoMapper;
import com.gestaopedidos.gestao.pedidos.domain.mapper.IPedidoMapper;
import com.gestaopedidos.gestao.pedidos.exception.SystemBaseHandleException;
import com.gestaopedidos.gestao.pedidos.infrastructure.entityjpa.ItensPedidosEntity;
import com.gestaopedidos.gestao.pedidos.infrastructure.entityjpa.PedidosEntity;
import com.gestaopedidos.gestao.pedidos.infrastructure.repository.IItensPedidoRepository;
import com.gestaopedidos.gestao.pedidos.infrastructure.repository.IPedidoRepository;
import com.gestaopedidos.gestao.pedidos.rabbitmq.IEstoque;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class PedidoGatewayTest {
    private static final String ERROR_MESSAGE = "Não foi possível identificar o pedido com o ID ";
    @Mock
    private IPedidoRepository pedidoRepository;

    @Mock
    private IItensPedidoRepository itensPedidoRepository;

    @Mock
    private IPedidoMapper pedidoMapper;

    @Mock
    private IItensPedidoMapper itensPedidoMapper;

    @Mock
    private IEstoque estoqueProducer;

    @InjectMocks
    private PedidoGateway pedidoGateway;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarPedidos() {
        int offset = 0;
        int limit = 10;

        PedidosEntity pedidoEntity = new PedidosEntity();
        pedidoEntity.setIdPedido(1L);
        List<PedidosEntity> pedidoEntities = List.of(pedidoEntity);
        Page<PedidosEntity> pedidoPage = new PageImpl<>(pedidoEntities);

        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setIdPedido(1L);

        ItensPedidosEntity itensPedidosEntity = new ItensPedidosEntity();
        List<ItensPedidosEntity> itensPedidosEntities = List.of(itensPedidosEntity);

        ProdutoDTO produtoDTO = new ProdutoDTO();
        List<ProdutoDTO> produtoDTOs = List.of(produtoDTO);

        when(pedidoRepository.findAll(PageRequest.of(offset, limit))).thenReturn(pedidoPage);
        when(pedidoMapper.toDTO(pedidoEntity)).thenReturn(pedidoDTO);
        when(itensPedidoRepository.findByIdPedido(1L)).thenReturn(itensPedidosEntities);
        when(itensPedidoMapper.toProdutoDTO(itensPedidosEntity)).thenReturn(produtoDTO);

        Page<PedidoDTO> result = pedidoGateway.listarPedidos(offset, limit);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().get(0).getIdPedido());
        assertEquals(produtoDTOs, result.getContent().get(0).getListaProdutos());

        verify(pedidoRepository, times(1)).findAll(PageRequest.of(offset, limit));
        verify(pedidoMapper, times(1)).toDTO(pedidoEntity);
        verify(itensPedidoRepository, times(1)).findByIdPedido(1L);
        verify(itensPedidoMapper, times(1)).toProdutoDTO(itensPedidosEntity);
    }

    @Test
    void testListarPedidoPorId_PedidoFoundWithItems() {

        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setIdPedido(1);
        List<ItensPedidosEntity> listaItens = new ArrayList<>();
        ItensPedidosEntity item = new ItensPedidosEntity();
        listaItens.add(item);
        ProdutoDTO produtoDTO = new ProdutoDTO();
        PedidosEntity entity = new PedidosEntity();
        entity.setIdPedido(1);
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(pedidoMapper.toDTO(entity)).thenReturn(pedidoDTO);
        when(itensPedidoRepository.findByIdPedido(1)).thenReturn(listaItens);
        when(itensPedidoMapper.toProdutoDTO(item)).thenReturn(produtoDTO);

        PedidoDTO result = pedidoGateway.listarPedidoPorId(1);

        assertNotNull(result);
        assertEquals(1, result.getListaProdutos().size());
        verify(pedidoRepository).findById(1L);
        verify(pedidoMapper).toDTO(entity);
        verify(itensPedidoRepository).findByIdPedido(1);
        verify(itensPedidoMapper).toProdutoDTO(item);
    }

    @Test
    void testListarPedidoPorId_PedidoNotFound() {
        long id = 1L;

        when(pedidoRepository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> pedidoGateway.listarPedidoPorId(id));

        assertEquals(ERROR_MESSAGE + id, exception.getMessage());
        verify(pedidoRepository).findById(id);
        verify(pedidoMapper, never()).toDTO(any());
        verify(itensPedidoRepository, never()).findByIdPedido(anyLong());
        verify(itensPedidoMapper, never()).toProdutoDTO(any());
    }

    @Test
    void testListarPedidoPorId_PedidoFoundNoItems() {

        PedidoDTO pedido = new PedidoDTO();
        pedido.setIdPedido(1);
        List<ItensPedidosEntity> listaItens = new ArrayList<>();
        PedidosEntity entity = new PedidosEntity();
        entity.setIdPedido(1);
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(pedidoMapper.toDTO(entity)).thenReturn(pedido);
        when(itensPedidoRepository.findByIdPedido(1)).thenReturn(listaItens);

        PedidoDTO result = pedidoGateway.listarPedidoPorId(1);

        assertNotNull(result);
        assertTrue(result.getListaProdutos().isEmpty());
        verify(pedidoRepository).findById(1L);
        verify(pedidoMapper).toDTO(entity);
        verify(itensPedidoRepository).findByIdPedido(1);
        verify(itensPedidoMapper, never()).toProdutoDTO(any());
    }

    @Test
    void testCriarPedido() {
        // Arrange
        ProdutoDTO prod1 = new ProdutoDTO();
        prod1.setIdProduto(1);
        prod1.setQuantidadeDesejada(20);
        ProdutoDTO prod2 = new ProdutoDTO();
        prod1.setIdProduto(2);
        prod1.setQuantidadeDesejada(30);
        List<ProdutoDTO> produtos = Arrays.asList(prod1,prod2);

        IProdutoGateway produtoGateway = mock(IProdutoGateway.class);
        IClienteGateway clienteGateway = mock(IClienteGateway.class);
        InsertPedidoDomain domain = new InsertPedidoDomain(produtoGateway,clienteGateway);
        domain.setIdCliente(1);
        domain.setListaProdutos(produtos);
        domain.setCep("01508001");
        domain.setLatitude(12.345);
        domain.setLongitude(67.890);

        BigDecimal valorTotal = new BigDecimal("100.00");

        PedidosEntity pedidoEntity = new PedidosEntity();
        pedidoEntity.setIdPedido(1L);
        pedidoEntity.setStatus("EM_CURSO");
        pedidoEntity.setIdCliente(1L);
        pedidoEntity.setCep("01508001");
        pedidoEntity.setLatitude(12.345);
        pedidoEntity.setLongitude(67.890);
        pedidoEntity.setPrecoFinal(valorTotal);

        when(pedidoMapper.toEntity(domain)).thenReturn(pedidoEntity);
        when(pedidoRepository.save(any(PedidosEntity.class))).thenReturn(pedidoEntity);

        // Act
        PedidoDTO result = pedidoGateway.criarPedido(domain, valorTotal);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getIdPedido());
        assertEquals("EM_CURSO", result.getStatus());
        assertEquals(1L, result.getIdCliente());
        assertEquals(produtos, result.getListaProdutos());
        assertEquals("01508001", result.getCep());
        assertEquals(12.345, result.getLatitude());
        assertEquals(67.890, result.getLongitude());
        assertEquals(valorTotal, result.getPrecoFinal());

        verify(pedidoMapper).toEntity(domain);
        verify(pedidoRepository).save(any(PedidosEntity.class));
        verify(itensPedidoRepository, times(produtos.size())).save(any(ItensPedidosEntity.class));
        verify(estoqueProducer).atualizaEstoque(produtos, AcaoEstoqueEnum.BAIXAR_ESTOQUE);
    }

    @Test
    void testAtualizarStatusPedidoPorId_OrderNotFound() {
        long id = 1L;
        UpdatePedidoDomain dto = mock(UpdatePedidoDomain.class);
        when(pedidoRepository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> pedidoGateway.atualizarStatusPedidoPorId(id, dto));

        assertEquals(ERROR_MESSAGE + id, exception.getMessage());
    }

    @Test
    void testAtualizarStatusPedidoPorId_OrderStatusCancelado() {
        long id = 1L;
        UpdatePedidoDomain domain = mock(UpdatePedidoDomain.class);
        PedidosEntity pedido = new PedidosEntity();
        pedido.setStatus(StatusEnum.CANCELADO.name());
        when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedido));

        SystemBaseHandleException exception = assertThrows(SystemBaseHandleException.class, () -> pedidoGateway.atualizarStatusPedidoPorId(id, domain));

        assertEquals("Não é possível alterar o status de um produto cancelado ou concluido", exception.getMessage());
    }
    @Test
    void testAtualizarStatusPedidoPorId_OrderStatusConcluido() {
        long id = 1L;
        UpdatePedidoDomain domain = mock(UpdatePedidoDomain.class);
        PedidosEntity pedido = new PedidosEntity();
        pedido.setStatus(StatusEnum.CONCLUIDO.name());
        when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedido));

        SystemBaseHandleException exception = assertThrows(SystemBaseHandleException.class, () -> pedidoGateway.atualizarStatusPedidoPorId(id, domain));

        assertEquals("Não é possível alterar o status de um produto cancelado ou concluido", exception.getMessage());
    }

    @Test
    void testAtualizarStatusPedidoPorId_Success() throws SystemBaseHandleException {
        long id = 1L;
        UpdatePedidoDomain domain = mock(UpdatePedidoDomain.class);
        PedidosEntity pedido = new PedidosEntity();
        pedido.setStatus(StatusEnum.EM_CURSO.name());
        when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedido));
        when(domain.getStatus()).thenReturn(StatusEnum.CONCLUIDO);

        pedidoGateway.atualizarStatusPedidoPorId(id, domain);

        verify(pedidoRepository).save(pedido);
        assertEquals(StatusEnum.CONCLUIDO.name(), pedido.getStatus());
    }

    @Test
    void testAtualizarStatusPedidoPorId_CanceladoStatus() throws SystemBaseHandleException {
        long id = 1L;
        UpdatePedidoDomain domain = mock(UpdatePedidoDomain.class);
        PedidosEntity pedido = new PedidosEntity();
        pedido.setStatus(StatusEnum.EM_CURSO.name());
        when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedido));
        when(domain.getStatus()).thenReturn(StatusEnum.CANCELADO);
        List<ItensPedidosEntity> listaItens = new ArrayList<>();
        ItensPedidosEntity item = new ItensPedidosEntity();
        listaItens.add(item);
        when(itensPedidoRepository.findByIdPedido(id)).thenReturn(listaItens);
        ProdutoDTO produtoDTO = new ProdutoDTO();
        when(itensPedidoMapper.toProdutoDTO(item)).thenReturn(produtoDTO);

        pedidoGateway.atualizarStatusPedidoPorId(id, domain);

        verify(pedidoRepository).save(pedido);
        verify(estoqueProducer).atualizaEstoque(anyList(), eq(AcaoEstoqueEnum.REPOR_ESTOQUE));
    }
}
