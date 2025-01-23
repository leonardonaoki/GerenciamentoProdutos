package com.gestaopedidos.gestao.pedidos.domain.validator;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CepValidatorTest {

    private CepValidator cepValidator;
    private ConstraintValidatorContext context;

    @BeforeEach
    public void setUp() {
        cepValidator = new CepValidator();
        context = null;
    }

    @Test
    void testValidCepWithHyphen() {
        assertTrue(cepValidator.isValid("12345-678", context));
    }

    @Test
    void testValidCepWithoutHyphen() {
        assertTrue(cepValidator.isValid("12345678", context));
    }

    @Test
    void testInvalidCepWithIncorrectFormat() {
        assertFalse(cepValidator.isValid("1234-5678", context));
        assertFalse(cepValidator.isValid("1234567", context));
        assertFalse(cepValidator.isValid("12345_678", context));
    }

    @Test
    void testNullValue() {
        assertTrue(cepValidator.isValid(null, context));
    }

    @Test
    void testEmptyString() {
        assertTrue(cepValidator.isValid("", context));
    }
}
