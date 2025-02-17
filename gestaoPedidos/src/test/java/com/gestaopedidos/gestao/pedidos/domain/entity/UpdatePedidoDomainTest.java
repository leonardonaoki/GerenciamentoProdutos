package com.gestaopedidos.gestao.pedidos.domain.entity;
import com.gestaopedidos.gestao.pedidos.domain.enums.StatusEnum;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UpdatePedidoDomainTest {

    @Test
    void testStatusGetterSetter() {
        StatusEnum status = StatusEnum.CONCLUIDO;
        String cep = "12345-678";
        Double latitude = -15d;
        Double longitude = 30d;

        UpdatePedidoDomain updatePedido = new UpdatePedidoDomain(status,cep,latitude,longitude);
        assertEquals(status, updatePedido.getStatus());
        assertEquals(cep, updatePedido.getCep());
        assertEquals(latitude, updatePedido.getLatitude());
        assertEquals(longitude, updatePedido.getLongitude());
    }
}
