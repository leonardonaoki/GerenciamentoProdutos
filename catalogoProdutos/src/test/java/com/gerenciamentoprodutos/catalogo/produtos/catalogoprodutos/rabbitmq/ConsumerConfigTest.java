package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.app.AtualizaListaProdutoUseCase;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.entity.consumer.AtualizacaoProdutosDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.function.Consumer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ConsumerConfigTest {

    @Mock
    private AtualizaListaProdutoUseCase atualizaListaProdutoUseCase; // Mock da dependência

    @InjectMocks
    private ConsumerConfig consumerConfig; // A classe que estamos testando

    private String mensagemValida;
    private AtualizacaoProdutosDomain atualizacaoProdutosDomain;

    @BeforeEach
    void setUp() {
        // Preparamos um objeto AtualizacaoProdutosDomain para ser utilizado no teste
        atualizacaoProdutosDomain = new AtualizacaoProdutosDomain(
                // Preencha com os valores esperados do objeto
                List.of(),  // Exemplo: Lista de produtos ou dados que o domínio pode conter
                "acao exemplo"
        );

        // Serializamos o objeto para uma String JSON que será enviada ao consumidor
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            mensagemValida = objectMapper.writeValueAsString(atualizacaoProdutosDomain);
        } catch (Exception e) {
            e.printStackTrace(); // Para fins de teste, mas em produção deve tratar adequadamente.
        }
    }

    @Test
    void testAtulizaEstoque_ComMensagemValida_ChamaAtualizaListaProdutoUseCase() {
        // Act: Consumir a mensagem válida
        Consumer<String> consumer = consumerConfig.atualizaEstoque();
        consumer.accept(mensagemValida);

        // Assert: Verificar se o método 'atualizaListaProdutos' foi chamado com o objeto correto
        verify(atualizaListaProdutoUseCase, times(1)).atualizaListaProdutos(atualizacaoProdutosDomain);
    }

    @Test
    void testAtulizaEstoque_ComMensagemInvalida_NaoChamaAtualizaListaProdutoUseCase() {
        // Act: Consumir uma mensagem inválida (não pode ser convertida para o tipo esperado)
        String mensagemInvalida = "mensagem inválida";
        Consumer<String> consumer = consumerConfig.atualizaEstoque();
        consumer.accept(mensagemInvalida);

        // Assert: Verificar que o método não foi chamado
        verify(atualizaListaProdutoUseCase, times(0)).atualizaListaProdutos(atualizacaoProdutosDomain);
    }
}
