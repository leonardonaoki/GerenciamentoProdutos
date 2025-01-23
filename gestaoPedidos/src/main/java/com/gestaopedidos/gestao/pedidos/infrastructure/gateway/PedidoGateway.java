package com.gestaopedidos.gestao.pedidos.infrastructure.gateway;

import com.gestaopedidos.gestao.pedidos.domain.dto.PedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.UpdatePedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.InsertPedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.ProdutoDTO;
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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PedidoGateway implements IPedidoGateway {
    private static final String ERROR_MESSAGE = "Não foi possível identificar o pedido com o ID ";
    private final IPedidoRepository pedidoRepository;
    private final IItensPedidoRepository itensPedidoRepository;
    private final IPedidoMapper pedidoMapper;
    private final IItensPedidoMapper itensPedidoMapper;
    private final IEstoque estoqueProducer;
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
    public PedidoDTO criarPedido(InsertPedidoDTO insertDTO, BigDecimal valorTotal) {
        PedidosEntity pedidoASalvar = pedidoMapper.toEntity(insertDTO);
        pedidoASalvar.setPrecoFinal(valorTotal);
        PedidosEntity pedidoCriado = pedidoRepository.save(pedidoASalvar);
        long idPedido = pedidoCriado.getIdPedido();
        insertDTO.listaProdutos().stream().forEach(p -> {
            ItensPedidosEntity itensPedido = new ItensPedidosEntity();
            itensPedido.setIdPedido(idPedido);
            itensPedido.setIdProduto(p.idProduto);
            itensPedido.setQuantidade(p.quantidadeDesejada);
            itensPedidoRepository.save(itensPedido);
        });

        estoqueProducer.atualizaEstoque(insertDTO.listaProdutos(),AcaoEstoqueEnum.BAIXAR_ESTOQUE);

        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setIdPedido(pedidoCriado.getIdPedido());
        pedidoDTO.setStatus(pedidoCriado.getStatus());
        pedidoDTO.setIdCliente(pedidoCriado.getIdCliente());
        pedidoDTO.setListaProdutos(insertDTO.listaProdutos());
        pedidoDTO.setCep(pedidoCriado.getCEP());
        pedidoDTO.setLatitude(pedidoCriado.getLatitude());
        pedidoDTO.setLongitude(pedidoCriado.getLongitude());
        pedidoDTO.setPrecoFinal(valorTotal);
        return pedidoDTO;
    }

    @Override
    public void atualizarStatusPedidoPorId(long id, UpdatePedidoDTO dto) throws SystemBaseHandleException {
        PedidosEntity pedidoEncontrado = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ERROR_MESSAGE + id));

        if(pedidoEncontrado.getStatus() == StatusEnum.CANCELADO.name() ||
                pedidoEncontrado.getStatus() == StatusEnum.CONCLUIDO.name())
            throw new SystemBaseHandleException("Não é possível alterar o status de um produto cancelado ou concluido");

        pedidoEncontrado.setStatus(dto.status().name());
        pedidoEncontrado.setCEP(dto.CEP());
        pedidoEncontrado.setLatitude(dto.Latitude());
        pedidoEncontrado.setLongitude(dto.Longitude());
        pedidoRepository.save(pedidoEncontrado);

        if(dto.status().name() == StatusEnum.CANCELADO.name()){
            List<ItensPedidosEntity> listaItens = itensPedidoRepository.findByIdPedido(id);
            List<ProdutoDTO> listaProdutos = new ArrayList<>();
            listaItens.stream().forEach(
                    p -> {
                        listaProdutos.add(itensPedidoMapper.toProdutoDTO(p));
                    }
            );
            estoqueProducer.atualizaEstoque(listaProdutos, AcaoEstoqueEnum.REPOR_ESTOQUE);
        }
    }
}
