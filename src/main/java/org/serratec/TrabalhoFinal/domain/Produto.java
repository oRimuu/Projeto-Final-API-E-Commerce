package org.serratec.TrabalhoFinal.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Preencha o nome do Produto")
    @Column(nullable = false)
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres.")
    private String nome;

    @Column
    @Size(min = 3, max = 50, message = "A descrição deve ter entre 3 e 50 caracteres.")
    private String descricao;

    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero")
    @Column(nullable = false)
    private BigDecimal preco;

    @NotNull(message = "A quantidade em estoque é obrigatória")
    @Min(value = 0, message = "A quantidade não pode ser negativa")
    @Column(nullable = false)
    private Integer quantidadeEstoque;

    @PastOrPresent(message = "A data de cadastro não pode ser futura")
    @Column(nullable = false)
    private LocalDate dataCadastro = LocalDate.now();

    // Muitos produtos podem estar em muitas categorias (lado inverso)
    @ManyToMany(mappedBy = "produtos")
    private List<Categoria> categorias;

    // Um produto pode estar em vários itens de pedido
    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PedidoProduto> itens;
}
