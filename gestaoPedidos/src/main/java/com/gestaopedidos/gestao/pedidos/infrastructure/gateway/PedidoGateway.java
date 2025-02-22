package com.gestaopedidos.gestao.pedidos.infrastructure.gateway;

import com.gestaopedidos.gestao.pedidos.domain.dto.PedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.ProdutoDTO;
import com.gestaopedidos.gestao.pedidos.domain.entity.InsertPedidoDomain;
import com.gestaopedidos.gestao.pedidos.domain.entity.UpdateLocalizacaoPedidoDomain;
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
                                    p ->listaProdutos.add(itensPedidoMapper.toProdutoDTO(p))
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
                p ->listaProdutos.add(itensPedidoMapper.toProdutoDTO(p))
        );
        pedido.setListaProdutos(listaProdutos);
        return pedido;
    }

    @Override
    @Transactional
    public PedidoDTO criarPedido(InsertPedidoDomain insertDTO, BigDecimal valorTotal) {
        PedidosEntity pedidoASalvar = pedidoMapper.toEntity(insertDTO);
        pedidoASalvar.setPrecoFinal(valorTotal);
        PedidosEntity pedidoCriado = pedidoRepository.save(pedidoASalvar);
        long idPedido = pedidoCriado.getIdPedido();
        insertDTO.getListaProdutos().stream().forEach(p -> {
            ItensPedidosEntity itensPedido = new ItensPedidosEntity();
            itensPedido.setIdPedido(idPedido);
            itensPedido.setIdProduto(p.getIdProduto());
            itensPedido.setQuantidade(p.getQuantidadeDesejada());
            itensPedidoRepository.save(itensPedido);
        });

        estoqueProducer.atualizaEstoque(insertDTO.getListaProdutos(),AcaoEstoqueEnum.BAIXAR_ESTOQUE);

        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setIdPedido(pedidoCriado.getIdPedido());
        pedidoDTO.setStatus(pedidoCriado.getStatus());
        pedidoDTO.setIdCliente(pedidoCriado.getIdCliente());
        pedidoDTO.setListaProdutos(insertDTO.getListaProdutos());
        pedidoDTO.setCep(pedidoCriado.getCep());
        pedidoDTO.setLatitude(pedidoCriado.getLatitude());
        pedidoDTO.setLongitude(pedidoCriado.getLongitude());
        pedidoDTO.setPrecoFinal(valorTotal);
        return pedidoDTO;
    }

    @Override
    public void atualizarStatusPedidoPorId(long id, UpdatePedidoDomain domain) throws SystemBaseHandleException {
        PedidosEntity pedidoEncontrado = buscaPedidoId(id);

        validaStatus(pedidoEncontrado);

        pedidoEncontrado.setStatus(domain.getStatus().name());
        pedidoEncontrado.setCep(domain.getCep());
        pedidoEncontrado.setLatitude(domain.getLatitude());
        pedidoEncontrado.setLongitude(domain.getLongitude());
        pedidoRepository.save(pedidoEncontrado);

        if(domain.getStatus().name().equals(StatusEnum.CANCELADO.name())){
            List<ItensPedidosEntity> listaItens = itensPedidoRepository.findByIdPedido(id);
            List<ProdutoDTO> listaProdutos = new ArrayList<>();
            listaItens.stream().forEach(
                    p ->listaProdutos.add(itensPedidoMapper.toProdutoDTO(p))
            );
            estoqueProducer.atualizaEstoque(listaProdutos, AcaoEstoqueEnum.REPOR_ESTOQUE);
        }
    }

    @Override
    public void atualizarLocalizacao(long id, UpdateLocalizacaoPedidoDomain domain) throws SystemBaseHandleException {
        PedidosEntity pedidoEncontrado = buscaPedidoId(id);

        validaStatus(pedidoEncontrado);
        pedidoEncontrado.setLatitude(domain.getLatitude());
        pedidoEncontrado.setLongitude(domain.getLongitude());
        pedidoRepository.save(pedidoEncontrado);
    }
    @Override
    public void atualizarStatusEntregue(long id) throws SystemBaseHandleException {
        PedidosEntity pedidoEncontrado = buscaPedidoId(id);

        validaStatus(pedidoEncontrado);

        pedidoEncontrado.setStatus(StatusEnum.CONCLUIDO.name());
        pedidoRepository.save(pedidoEncontrado);
    }

	
    @Override
    public void atualizarEntregadorPedido(long id, long idEntregador) throws SystemBaseHandleException {
        PedidosEntity pedidoEncontrado = buscaPedidoId(id);

        validaStatus(pedidoEncontrado);

        pedidoEncontrado.setStatus(StatusEnum.EM_ROTA_DE_ENTREGA.name());
        pedidoEncontrado.setIdEntregador(idEntregador);
        pedidoRepository.save(pedidoEncontrado);
    }
    
	private void validaStatus(PedidosEntity pedidoEncontrado) throws SystemBaseHandleException {
		if(pedidoEncontrado.getStatus().equals(StatusEnum.CANCELADO.name()) ||
                pedidoEncontrado.getStatus().equals(StatusEnum.CONCLUIDO.name()))
            throw new SystemBaseHandleException("Não é possível alterar o status de um produto cancelado ou concluido");
	}
	
	private PedidosEntity buscaPedidoId(long id) {
		PedidosEntity pedidoEncontrado = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ERROR_MESSAGE + id));
		return pedidoEncontrado;
	}
	
	@Override
    public List <PedidoDTO> buscaPedidosEmAbertos(long idEntregador) {
        List<PedidosEntity> listaPedidos = pedidoRepository.findByIdEntregador(idEntregador);
        List <PedidoDTO> listDto = new ArrayList<PedidoDTO>();
        for(var pedido: listaPedidos) {
        	PedidoDTO dto = new PedidoDTO();
        	if(StatusEnum.EM_ROTA_DE_ENTREGA.name().equals(pedido.getStatus())) {
        		dto.setCep(pedido.getCep());
        		dto.setIdPedido(pedido.getIdPedido());
        		listDto.add(dto);
        	}
        }
        
        return listDto;
    }
	
	@Override
    public List <Long> buscaPorCep(String cep) {
		List<PedidosEntity> listaPedidos = pedidoRepository.findByCep(cep);
        List <Long> listLong = new ArrayList<Long>();
        for(var pedido: listaPedidos) {
        	if(StatusEnum.CONFIRMADO.name().equals(pedido.getStatus())) {
        		listLong.add(pedido.getIdPedido());
        	}
        }
        
        return listLong;

    }
}
