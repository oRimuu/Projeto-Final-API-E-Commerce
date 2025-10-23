package org.serratec.TrabalhoFinal.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class ViaCepService {

    private final RestTemplate restTemplate;

    public ViaCepService() {
        this.restTemplate = new RestTemplate();
    }

    public Map<String, Object> buscarCep(String cep) {
        // Monta a URL completa usando o CEP recebido
        String urlCompleta = "https://viacep.com.br/ws/" + cep + "/json/";
        // Faz a requisição GET para o ViaCEP e retorna como Map
        return restTemplate.getForObject(urlCompleta, Map.class);
    }
}
