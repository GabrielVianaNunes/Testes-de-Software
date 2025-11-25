package com.example.cadastro_clientes.service;

import com.example.cadastro_clientes.model.Cliente;
import com.example.cadastro_clientes.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    private ClienteRepository repository;
    private ClienteService service;

    @BeforeEach
    void setup() {
        repository = mock(ClienteRepository.class);
        service = new ClienteService(repository);
    }

    @Test
    void deveSalvarCliente() {
        Cliente cliente = new Cliente("João", "joao@test.com", "123", "São Paulo");

        when(repository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente salvo = service.salvar(cliente);

        assertNotNull(salvo);
        assertEquals("João", salvo.getNome());
        verify(repository, times(1)).save(cliente);
    }

    @Test
    void deveAtualizarClienteExistente() {
        Cliente existente = new Cliente("João", "joao@test.com", "123", "São Paulo");
        existente.setId(1L);

        Cliente atualizado = new Cliente("João Silva", "joao.silva@test.com", "999", "Rio");

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cliente result = service.atualizar(1L, atualizado);

        assertEquals("João Silva", result.getNome());
        assertEquals("Rio", result.getCidade());
        verify(repository, times(1)).save(existente);
    }

    @Test
    void deveLancarExcecaoAoAtualizarClienteInexistente() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        Cliente atualizado = new Cliente("Teste", "t@test.com", "000", "Cidade");

        assertThrows(RuntimeException.class,
                () -> service.atualizar(99L, atualizado));
    }
}
