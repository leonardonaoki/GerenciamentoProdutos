package com.gestaopedidos.gestao.pedidos.domain.mapper;

import com.gestaopedidos.gestao.pedidos.domain.dto.PedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.InsertPedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.LocalizacaoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.UpdatePedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.entity.InsertPedidoDomain;
import com.gestaopedidos.gestao.pedidos.domain.entity.UpdateLocalizacaoPedidoDomain;
import com.gestaopedidos.gestao.pedidos.domain.entity.UpdatePedidoDomain;
import com.gestaopedidos.gestao.pedidos.infrastructure.entityjpa.PedidosEntity;

public interface IPedidoMapper {
    PedidoDTO toDTO(PedidosEntity produto);
    PedidosEntity toEntity(InsertPedidoDomain dto);
    InsertPedidoDomain toInsertDomain(InsertPedidoDTO insertPedidoDTO);
    UpdatePedidoDomain toUpdateDomain(UpdatePedidoDTO updatePedidoDTO);
	UpdateLocalizacaoPedidoDomain toUpdateLocalizacao(LocalizacaoDTO localizacaoDTO);
}
