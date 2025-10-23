package org.serratec.TrabalhoFinal.service;

import org.serratec.TrabalhoFinal.domain.Cliente;
import org.serratec.TrabalhoFinal.dto.ClienteDTO;
import org.serratec.TrabalhoFinal.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ViaCepService viaCepService;

    public ClienteService(ClienteRepository clienteRepository, ViaCepService viaCepService) {
        this.clienteRepository = clienteRepository;
        this.viaCepService = viaCepService;
    }

    public List<ClienteDTO> listarTodos() {
        return clienteRepository.findAll().stream()
                .map(cliente -> {
                    ClienteDTO dto = new ClienteDTO();
                    dto.setId(cliente.getId());
                    dto.setNome(cliente.getNome());
                    dto.setTelefone(cliente.getTelefone());
                    dto.setCep(cliente.getCep());
                    dto.setCidade(cliente.getCidade());
                    return dto;
                })
                .collect(Collectors.toList());
    }


    public Optional<ClienteDTO> buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    ClienteDTO dto = new ClienteDTO();
                    dto.setId(cliente.getId());
                    dto.setNome(cliente.getNome());
                    dto.setTelefone(cliente.getTelefone());
                    dto.setCep(cliente.getCep());
                    dto.setCidade(cliente.getCidade());
                    return dto;
                });
    }

    public Cliente salvar(Cliente cliente) {
        Map<String, Object> endereco = viaCepService.buscarCep(cliente.getCep());

        if (endereco == null || endereco.get("erro") != null) {
            throw new IllegalArgumentException("CEP inválido");
        }

        cliente.setLogradouro((String) endereco.get("logradouro"));
        cliente.setBairro((String) endereco.get("bairro"));
        cliente.setCidade((String) endereco.get("localidade"));
        cliente.setUf((String) endereco.get("uf"));

        return clienteRepository.save(cliente);
    }

    public Cliente atualizar(Long id, Cliente cliente) {
        cliente.setId(id);
        return salvar(cliente);
    }

    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }

}
