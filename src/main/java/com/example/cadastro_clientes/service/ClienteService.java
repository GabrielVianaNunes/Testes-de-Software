package com.example.cadastro_clientes.service;

import com.example.cadastro_clientes.model.Cliente;
import com.example.cadastro_clientes.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public Cliente salvar(Cliente cliente) {
        return repository.save(cliente);
    }

    public List<Cliente> listarTodos() {
        return repository.findAll();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Cliente atualizar(Long id, Cliente dadosAtualizados) {
        return repository.findById(id)
                .map(cliente -> {
                    cliente.setNome(dadosAtualizados.getNome());
                    cliente.setEmail(dadosAtualizados.getEmail());
                    cliente.setTelefone(dadosAtualizados.getTelefone());
                    cliente.setCidade(dadosAtualizados.getCidade());
                    return repository.save(cliente);
                })
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
