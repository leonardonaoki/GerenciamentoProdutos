package com.gestaopedidos.gestao.pedidos.infrastructure.gateway;

import com.gestaopedidos.gestao.pedidos.domain.dto.PedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.InsertAndUpdatePedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.ProdutoDTO;
import com.gestaopedidos.gestao.pedidos.domain.mapper.IItensPedidoMapper;
import com.gestaopedidos.gestao.pedidos.domain.mapper.IPedidoMapper;
import com.gestaopedidos.gestao.pedidos.exception.SystemBaseHandleException;
import com.gestaopedidos.gestao.pedidos.infrastructure.entityjpa.ItensPedidosEntity;
import com.gestaopedidos.gestao.pedidos.infrastructure.entityjpa.PedidosEntity;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.specification.PedidosSpecification;
import com.gestaopedidos.gestao.pedidos.infrastructure.repository.IItensPedidoRepository;
import com.gestaopedidos.gestao.pedidos.infrastructure.repository.IPedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PedidoGateway implements IPedidoGateway {
    private static final String ERROR_MESSAGE = "Não foi possível identificar o pedido com o ID ";
    private final IPedidoRepository pedidoRepository;
    private final IItensPedidoRepository itensPedidoRepository;
    private final IPedidoMapper pedidoMapper;
    private final IItensPedidoMapper itensPedidoMapper;
    @Override
    public Page<PedidoDTO> listarPedidos(int offset, int limit) {
        Page<PedidoDTO> paginaPedidos = pedidoRepository.findAll(PageRequest.of(offset,limit)).map(pedidoMapper::toDTO);
        paginaPedidos.stream().forEach(
                pagina -> {
                            List<ItensPedidosEntity> listaItens = itensPedidoRepository.findByIdPedido(pagina.getIdPedido());
                            List<ProdutoDTO> listaProdutos = new ArrayList<>();
                                    listaItens.stream().forEach(
                                    p -> {
                                        listaProdutos.add(itensPedidoMapper.toProdutoDTO(p));
                                    }
                            );
                            pagina.setListaProdutos(listaProdutos);
                    });
        return paginaPedidos;
    }

    @Override
    public PedidoDTO listarPedidoPorId(long id){
        PedidoDTO pedido = pedidoRepository.findById(id)
                .map(pedidoMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException(ERROR_MESSAGE + id));

        List<ItensPedidosEntity> listaItens = itensPedidoRepository.findByIdPedido(pedido.getIdPedido());
        List<ProdutoDTO> listaProdutos = new ArrayList<>();
        listaItens.stream().forEach(
                p -> {
                    listaProdutos.add(itensPedidoMapper.toProdutoDTO(p));
                }
        );
        pedido.setListaProdutos(listaProdutos);
        return pedido;
    }

    @Override
    @Transactional
    public PedidoDTO criarPedido(InsertAndUpdatePedidoDTO dto) {
        PedidosEntity produtoSalvo = pedidoRepository.save(pedidoMapper.toEntity(dto));
        return pedidoMapper.toDTO(produtoSalvo);
    }

    @Override
    public PedidoDTO atualizarPedidoPorId(long id,InsertAndUpdatePedidoDTO dto){
//        PedidosEntity produtoEncontrado = produtoRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException(ERROR_MESSAGE + id));
//        produtoEncontrado.setDescricao(dto.Descricao());
//        produtoEncontrado.setPreco(dto.Preco());
//        produtoEncontrado.setQuantidadeEstoque(dto.QuantidadeEstoque());
        //return produtoMapper.toDTO(produtoRepository.save(produtoEncontrado));
        return null;
    }

    @Override
    public void deletarPedidoPorId(long id){
        if(pedidoRepository.existsById(id))
            pedidoRepository.deleteById(id);
        else
            throw new EntityNotFoundException(ERROR_MESSAGE + id);
    }
}
