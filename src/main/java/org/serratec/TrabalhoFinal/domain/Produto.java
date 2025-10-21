package org.serratec.TrabalhoFinal.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "produto")
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank (message = "Preencha o nome do Produto")
	@Column
	@Size (min = 3,max = 50, message = "O nome deve ter entre 3 e 50 caracteres.")
	private String nome;
	
	@Column
	@Size (min = 3,max = 50, message = "A descrição deve ter entre 3 e 50 caracteres.")
	private String descricao;
	
	@NotNull(message = "O preço é obrigatório")
	@DecimalMin(value = "0.01", message = "O preço deve ser maior que zero")
	private BigDecimal preco;
	
	@NotNull(message = "A quantidade em estoque deve ser obrigatória")
    @Min(value = 0, message = "A quantidade não pode ser negativa")
	private Integer quantidadeEstoque;
	
	@PastOrPresent(message = "A data de cadastro não pode ser futura")
	private LocalDate dataCadastro = LocalDate.now();
	
	
	// MUITOS PRODUTOS PARA MUITAS CATEGORIAS
	
	@ManyToMany
	@JoinColumn (name = "id_categoria",nullable = false)
	private Categoria categoria;

    @OneToMany(mappedBy = "id_produto", cascade = CascadeType.ALL)
    private List<PedidoProduto> itens;
}
