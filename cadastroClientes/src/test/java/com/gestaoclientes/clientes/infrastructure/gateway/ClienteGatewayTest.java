package com.gestaoclientes.clientes.infrastructure.gateway;

import com.gerenciamentoclientes.clientes.domain.dto.ClienteDTO;
import com.gerenciamentoclientes.clientes.domain.entity.ClienteDomain;
import com.gerenciamentoclientes.clientes.domain.mapper.IClienteMapper;
import com.gerenciamentoclientes.clientes.infrastructure.entity.ClienteEntity;
import com.gerenciamentoclientes.clientes.infrastructure.gateway.ClienteGateway;
import com.gerenciamentoclientes.clientes.infrastructure.repository.IClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteGatewayTest {

    @Mock
    private IClienteRepository clienteRepository;

    @Mock
    private IClienteMapper clienteMapper;

    @InjectMocks
    private ClienteGateway clienteGateway;

    @Test
    void listarClientesDeveRetornarPageDeClienteDTOs() {
        // Arrange
        int offset = 0;
        int limit = 10;
        PageRequest pageRequest = PageRequest.of(offset, limit);

        List<ClienteEntity> clientes = List.of(new ClienteEntity(), new ClienteEntity());
        Page<ClienteEntity> clientesPage = new PageImpl<>(clientes);

        when(clienteRepository.findAll(pageRequest)).thenReturn(clientesPage);
        when(clienteMapper.toDTO(any(ClienteEntity.class))).thenReturn(new ClienteDTO(1L, "NomeTeste", "EmailTeste"));

        // Act
        Page<ClienteDTO> resultado = clienteGateway.listarClientes(offset, limit);

        // Assert
        assertNotNull(resultado);
        assertEquals(clientes.size(), resultado.getContent().size());
        verify(clienteRepository).findAll(pageRequest);
        verify(clienteMapper, times(clientes.size())).toDTO(any(ClienteEntity.class));
    }

    @Test
    void listarClientePorIdDeveRetornarClienteDTOQuandoClienteExiste() {
        // Arrange
        long id = 1L;
        ClienteEntity clienteEntity = new ClienteEntity();
        ClienteDTO clienteDTO = new ClienteDTO(id, "NomeTeste", "EmailTeste");

        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteEntity));
        when(clienteMapper.toDTO(clienteEntity)).thenReturn(clienteDTO);

        // Act
        ClienteDTO resultado = clienteGateway.listarClientePorId(id);

        // Assert
        assertNotNull(resultado);
        assertEquals(clienteDTO, resultado);
        verify(clienteRepository).findById(id);
        verify(clienteMapper).toDTO(clienteEntity);
    }

    @Test
    void listarClientePorIdDeveLancarExcecaoQuandoClienteNaoExiste() {
        // Arrange
        long id = 1L;
        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> clienteGateway.listarClientePorId(id));
        assertEquals("Não foi possível identificar o cliente com o ID " + id, exception.getMessage());
        verify(clienteRepository).findById(id);
    }

    @Test
    void criarClienteDeveRetornarClienteDTO() {
        // Arrange
        ClienteDomain domain = mock(ClienteDomain.class);
        ClienteEntity clienteSalvo = new ClienteEntity();
        ClienteDTO clienteDTO = new ClienteDTO(1L, "NomeTeste", "EmailTeste");

        when(clienteMapper.toEntity(domain)).thenReturn(clienteSalvo);
        when(clienteRepository.save(clienteSalvo)).thenReturn(clienteSalvo);
        when(clienteMapper.toDTO(clienteSalvo)).thenReturn(clienteDTO);

        // Act
        ClienteDTO resultado = clienteGateway.criarCliente(domain);

        // Assert
        assertNotNull(resultado);
        assertEquals(clienteDTO, resultado);
        verify(clienteMapper).toEntity(domain);
        verify(clienteRepository).save(clienteSalvo);
        verify(clienteMapper).toDTO(clienteSalvo);
    }

    @Test
    void atualizarClientePorIdDeveRetornarClienteDTOQuandoClienteExiste() {
        // Arrange
        long id = 1L;
        ClienteDomain domain = mock(ClienteDomain.class);

        ClienteEntity clienteEncontrado = new ClienteEntity();
        ClienteEntity clienteAtualizado = new ClienteEntity();
        ClienteDTO clienteDTO = new ClienteDTO(1L, "NomeTeste", "EmailTeste");

        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteEncontrado));
        when(clienteRepository.save(clienteEncontrado)).thenReturn(clienteAtualizado);
        when(clienteMapper.toDTO(clienteAtualizado)).thenReturn(clienteDTO);

        // Act
        ClienteDTO resultado = clienteGateway.atualizarClientePorId(id, domain);

        // Assert
        assertNotNull(resultado);
        assertEquals(clienteDTO, resultado);
        verify(clienteRepository).findById(id);
        verify(clienteRepository).save(clienteEncontrado);
        verify(clienteMapper).toDTO(clienteAtualizado);
    }

    @Test
    void atualizarClientePorIdDeveLancarExcecaoQuandoClienteNaoExiste() {
        // Arrange
        long id = 1L;
        ClienteDomain domain = mock(ClienteDomain.class);

        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> clienteGateway.atualizarClientePorId(id, domain));
        assertEquals("Não foi possível identificar o cliente com o ID " + id, exception.getMessage());
        verify(clienteRepository).findById(id);
    }

    @Test
    void deletarClientePorIdDeveExcluirClienteQuandoClienteExiste() {
        // Arrange
        long id = 1L;
        when(clienteRepository.existsById(id)).thenReturn(true);

        // Act
        clienteGateway.deletarClientePorId(id);

        // Assert
        verify(clienteRepository).existsById(id);
        verify(clienteRepository).deleteById(id);
    }

    @Test
    void deletarClientePorIdDeveLancarExcecaoQuandoClienteNaoExiste() {
        // Arrange
        long id = 1L;
        when(clienteRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> clienteGateway.deletarClientePorId(id));
        assertEquals("Não foi possível identificar o cliente com o ID " + id, exception.getMessage());
        verify(clienteRepository).existsById(id);
    }
}

