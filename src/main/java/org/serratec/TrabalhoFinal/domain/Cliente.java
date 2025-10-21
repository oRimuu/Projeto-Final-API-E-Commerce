package org.serratec.TrabalhoFinal.domain;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 3, max = 80, message = "O nome deve ter entre 3 e 80 caracteres.")
    private String nome;

    @NotBlank(message = "O telefone é obrigatório.")
    @Pattern(regexp = "\\d{10,11}", message = "O telefone deve conter 10 ou 11 dígitos.")
    private String telefone;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "E-mail inválido.")
    @Column(unique = true)
    private String email;

    @Column(unique = true, length = 14)
    //@CPF
    private String cpf;

    @NotBlank(message = "O CEP é obrigatório.")
    @Pattern(regexp = "\\d{8}", message = "O CEP deve conter 8 dígitos numéricos.")
    private String cep;

    // Campos que virão da API ViaCEP
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;
    
}
