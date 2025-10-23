package org.serratec.TrabalhoFinal.dto;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {
	
    private Long id;

    @NotBlank(message = "Preencha o nome do Produto")
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres.")
    private String nome;

    @Size(min = 3, max = 50, message = "A descrição deve ter entre 3 e 50 caracteres.")
    private String descricao;

    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero")
    private BigDecimal preco;

    @NotNull(message = "A quantidade em estoque deve ser obrigatória")
    @Min(value = 0, message = "A quantidade não pode ser negativa")
    private Integer quantidadeEstoque;

    private List<Long> categoriasIds; 
}
