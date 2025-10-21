package org.serratec.TrabalhoFinal.domain;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table (name = "categoria")
public class Categoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank (message = "Preencha o nome da categoria")
	@Column
	@Size (min = 3,max = 50, message = "O nome deve ter entre 3 e 50 caracteres.")
	private String nome;
	
	@Column
	@Size (min = 3,max = 200, message = "A descrição deve ter entre 3 e 50 caracteres.")
	private String descricao;

	// VÁRIAS CATEGORIA PODE TER VÁRIOS PRODUTOS - 
	@ManyToMany
	@JoinColumn (name = "id_produto", nullable = false)
	private List<Produto> produtos;
}
