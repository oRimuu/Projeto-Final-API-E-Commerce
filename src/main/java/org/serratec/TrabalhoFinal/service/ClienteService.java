package org.serratec.TrabalhoFinal.service;

import org.serratec.TrabalhoFinal.domain.Cliente;
import org.serratec.TrabalhoFinal.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente atualizar(Long id, Cliente cliente) {
        cliente.setId(id);
        return clienteRepository.save(cliente);
    }

    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }
}
