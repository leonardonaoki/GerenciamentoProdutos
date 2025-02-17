package com.gestaopedidos.gestao.pedidos.domain.dto.request;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.util.Set;
import java.util.List;

class InsertPedidoDTOTest {

    private final Validator validator;

    public InsertPedidoDTOTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidInsertPedidoDTO() {
        InsertPedidoDTO dto = new InsertPedidoDTO(1L, List.of(new ProdutoDTO()), "12345-678", 10.0, 20.0);
        Set<ConstraintViolation<InsertPedidoDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidIdCliente() {
        InsertPedidoDTO dto = new InsertPedidoDTO(-1L, List.of(new ProdutoDTO()), "12345-678", 10.0, 20.0);
        Set<ConstraintViolation<InsertPedidoDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testEmptyListaProdutos() {
        InsertPedidoDTO dto = new InsertPedidoDTO(1L, List.of(), "12345-678", 10.0, 20.0);
        Set<ConstraintViolation<InsertPedidoDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testInvalidCEP() {
        InsertPedidoDTO dto = new InsertPedidoDTO(1L, List.of(new ProdutoDTO()), "", 10.0, 20.0);
        Set<ConstraintViolation<InsertPedidoDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testNullCEP() {
        InsertPedidoDTO dto = new InsertPedidoDTO(1L, List.of(new ProdutoDTO()), null, 10.0, 20.0);
        Set<ConstraintViolation<InsertPedidoDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }
}