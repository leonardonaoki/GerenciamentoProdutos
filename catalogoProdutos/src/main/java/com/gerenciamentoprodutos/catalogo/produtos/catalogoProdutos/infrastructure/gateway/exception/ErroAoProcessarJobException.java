package com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.infrastructure.gateway.exception;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.exception.SystemBaseException;
import lombok.Getter;

@Getter
public class ErroAoProcessarJobException extends SystemBaseException {
    private static final long serialVersionUID = 4306545258260552901L;

    private final String code = "erroAoProcessarJob";//NOSONAR
    private final String message = "Erro ao processar arquivo";//NOSONAR
    private final Integer httpStatus = 500;//NOSONAR
}