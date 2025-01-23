package com.gestaopedidos.gestao.pedidos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class GestaoPedidosApplicationTests {
	@Test
	void deveExecutarAplicacaoNormalmente(){
		assertDoesNotThrow(() -> GestaoPedidosApplication.main(new String[]{}));
	}
}
