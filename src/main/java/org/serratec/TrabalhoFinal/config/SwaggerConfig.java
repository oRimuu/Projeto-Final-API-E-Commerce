package org.serratec.TrabalhoFinal.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

    @Value("${dominio.openapi.dev-url}")
    private String devUrl;

  

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("URL do servidor de desenvolvimento");

        
        Contact contact = new Contact();
        contact.setEmail("APIprojetofinal@gmail.com");
        contact.setName("ProjetoAPIgrupo6");
        

        License mitLicense = new License()
                .name("Apache License 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0");

        Info info = new Info()
                .title("ecommerce")
                .version("1.7")
                .contact(contact)
                .description("O projeto Serratec API foi desenvolvido com o objetivo de proporcionar uma plataforma moderna, estruturada e escalável para o gerenciamento de dados e processos internos do programa Serratec - Parque Tecnológico da Região Serrana. Essa API tem como principal finalidade integrar e automatizar operações que envolvem alunos, instrutores, cursos, avaliações e empresas parceiras, garantindo um fluxo de informações eficiente e seguro.\r\n"
                		+ "\r\n"
                		+ "A aplicação foi construída utilizando o framework Spring Boot, que oferece uma base sólida para o desenvolvimento de aplicações RESTful em Java, permitindo alta performance e fácil manutenção. O projeto segue o padrão MVC (Model-View-Controller) e utiliza Spring Data JPA para integração com o banco de dados relacional, proporcionando persistência de dados de forma simples e padronizada.\r\n"
                		+ "\r\n"
                		+ "Entre as principais funcionalidades da API, destacam-se:\r\n"
                		+ "\r\n"
                		+ "📘 Cadastro e gerenciamento de alunos: inclusão, atualização, exclusão e listagem de estudantes matriculados nos cursos do Serratec.\r\n"
                		+ "\r\n"
                		+ "👨‍🏫 Gestão de instrutores e cursos: controle das turmas, disciplinas ministradas e acompanhamento do progresso dos alunos.\r\n"
                		+ "\r\n"
                		+ "🧮 Módulo de avaliações: registro de notas, feedbacks e médias de desempenho.\r\n"
                		+ "\r\n"
                		+ "🏢 Empresas parceiras: integração de empresas interessadas em contratar alunos formados ou em estágio.\r\n"
                		+ "\r\n"
                		+ "🔐 Autenticação e segurança: endpoints protegidos por JWT (JSON Web Token), garantindo acesso apenas a usuários autenticados.\r\n"
                		+ "\r\n"
                		+ "🧠 Documentação interativa: gerada automaticamente com o Swagger/OpenAPI, facilitando o consumo dos endpoints por desenvolvedores e testadores.\r\n"
                		+ "\r\n"
                		+ "\r\n"
                		+ "A arquitetura foi planejada para ser modular e expansível, permitindo que novos módulos sejam adicionados sem comprometer o funcionamento dos já existentes. O projeto também prioriza boas práticas de programação, como injeção de dependências, tratamento de exceções centralizado e uso de DTOs (Data Transfer Objects) para melhor controle das informações trafegadas pela API.\r\n"
                		+ "\r\n"
                		+ "Além disso, o projeto busca refletir valores essenciais do Serratec, como inovação, colaboração e aprendizado contínuo, servindo como exemplo de aplicação prática dos conhecimentos adquiridos durante o curso técnico.\r\n"
                		+ "\r\n"
                		+ "O desenvolvimento foi realizado em equipe, seguindo o fluxo de versionamento do Git/GitHub, com branches organizadas e revisões de código. Isso garante qualidade, rastreabilidade e um ambiente colaborativo de desenvolvimento.\r\n"
                		+ "\r\n"
                		+ "\r\n"
                		+ "---\r\n"
                		+ "\r\n"
                		+ "💡 Tecnologias utilizadas:\r\n"
                		+ "\r\n"
                		+ "Java 17\r\n"
                		+ "\r\n"
                		+ "Spring Boot\r\n"
                		+ "\r\n"
                		+ "Spring Data JPA\r\n"
                		+ "\r\n"
                		+ "Hibernate\r\n"
                		+ "\r\n"
                		+ "Maven\r\n"
                		+ "\r\n"
                		+ "PostgreSQL\r\n"
                		+ "\r\n"
                		+ "Swagger / OpenAPI\r\n"
                		+ "\r\n"
                		+ "JWT Security\r\n"
                		+ "\r\n"
                		+ "Lombok")
                .termsOfService("https://www.meudominio.com.br/termos")
                .license(mitLicense);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer ));
    }
}