package com.gestaopedidos.gestao.pedidos.infrastructure.gateway;

import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.json.FindByClienteIdResponseDTO;

public interface IClienteGateway {
    FindByClienteIdResponseDTO findById(long id);
}
