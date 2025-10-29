package org.serratec.TrabalhoFinal.domain;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "categoria")
@Schema(description = "Representa uma categoria de produtos")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único da categoria")
    private Long id;

    @NotBlank(message = "Preencha o nome da categoria")
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres.")
    @Column(nullable = false, length = 50)
    @Schema(description = "Nome da categoria", required = true, example = "Eletrônicos")
    private String nome;

    @Size(min = 3, max = 200, message = "A descrição deve ter entre 3 e 200 caracteres.")
    @Column(length = 200)
    @Schema(description = "Descrição detalhada da categoria", example = "Produtos de tecnologia e acessórios eletrônicos.")
    private String descricao;

    @ManyToMany
    @JoinTable(
        name = "categoria_produto",
        joinColumns = @JoinColumn(name = "categoria_id"),
        inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    @Schema(description = "Lista de produtos associados a esta categoria")
    private List<Produto> produtos;

    
}
