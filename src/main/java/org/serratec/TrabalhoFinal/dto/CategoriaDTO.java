package org.serratec.TrabalhoFinal.dto;


import org.serratec.TrabalhoFinal.domain.Categoria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {
	

	private Long id;
	
	private String nome;
	
	
	public CategoriaDTO(Categoria categoria){
		this.id = categoria.getId();
		this.nome = categoria.getNome();
	}
}