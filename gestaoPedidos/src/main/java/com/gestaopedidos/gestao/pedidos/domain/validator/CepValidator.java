package com.gestaopedidos.gestao.pedidos.domain.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CepValidator implements ConstraintValidator<IValidaCep, String> {

    private static final String CEP_REGEX = "^\\d{5}-\\d{3}$|^\\d{8}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true; // Pode permitir valores nulos ou vazios; ajuste se necessário
        }
        return value.matches(CEP_REGEX);
    }
}