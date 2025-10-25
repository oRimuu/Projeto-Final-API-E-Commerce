package org.serratec.TrabalhoFinal.service;

import org.serratec.TrabalhoFinal.domain.Cliente;
import org.serratec.TrabalhoFinal.dto.ClienteDTO;
import org.serratec.TrabalhoFinal.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ViaCepService viaCepService;
    
    @Autowired
    private EmailService emailService;


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
        // método padrão usado pelo controller — envia e-mail de cadastro
        return salvar(cliente, true);
    }

    public Cliente salvar(Cliente cliente, boolean enviarEmail) {
        Map<String, Object> endereco = viaCepService.buscarCep(cliente.getCep());

        if (endereco == null || endereco.get("erro") != null) {
            throw new IllegalArgumentException("CEP inválido");
        }

        cliente.setLogradouro((String) endereco.get("logradouro"));
        cliente.setBairro((String) endereco.get("bairro"));
        cliente.setCidade((String) endereco.get("localidade"));
        cliente.setUf((String) endereco.get("uf"));

        Cliente clienteSalvo = clienteRepository.save(cliente);

        // Só envia e-mail se enviarEmail = true
        if (enviarEmail) {
            try {
                emailService.enviarEmail(
                    clienteSalvo.getEmail(),
                    "Cadastro realizado com sucesso!",
                    "Olá " + clienteSalvo.getNome() + ",\n\n" +
                    "Seja bem-vindo(a) à nossa plataforma! Seu cadastro foi concluído com sucesso.\n\n" +
                    "Este projeto foi desenvolvido pelo Grupo 6 da disciplina de API RESTful do Serratec,\n" +
                    "composto por Fernando, Nathan e João Vitor.\n\n" +
                    "Agradecemos por fazer parte da nossa aplicação!\n\n" +
                    "Atenciosamente,\n" +
                    "Equipe Grupo 6 - Serratec"
                );
            } catch (Exception e) {
                System.out.println("Erro ao enviar e-mail: " + e.getMessage());
            }
        }

        return clienteSalvo;
    }

    public Cliente atualizar(Long id, Cliente cliente) {
        cliente.setId(id);
        // Evita o e-mail duplicado
        Cliente clienteAtualizado = salvar(cliente, false);

        try {
            emailService.enviarEmail(
                clienteAtualizado.getEmail(),
                "Dados atualizados com sucesso!",
                "Olá " + clienteAtualizado.getNome() + ",\n\n" +
                "Suas informações foram atualizadas com sucesso em nossa plataforma.\n\n" +
                "Este projeto foi desenvolvido pelo Grupo 6 da disciplina de API RESTful do Serratec,\n" +
                "composto por Fernando, Nathan e João Vitor.\n\n" +
                "Agradecemos pela confiança!\n\n" +
                "Atenciosamente,\n" +
                "Equipe Grupo 6 - Serratec"
            );
        } catch (Exception e) {
            System.out.println("Erro ao enviar e-mail de atualização: " + e.getMessage());
        }

        return clienteAtualizado;
    }

    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }

}
