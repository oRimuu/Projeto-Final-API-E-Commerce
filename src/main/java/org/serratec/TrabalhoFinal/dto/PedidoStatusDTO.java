package org.serratec.TrabalhoFinal.dto;

import org.serratec.TrabalhoFinal.enums.OpcaoPagamento;
import org.serratec.TrabalhoFinal.enums.StatusPedido;

import lombok.Data;

@Data
public class PedidoStatusDTO {
	
	private OpcaoPagamento TipoPagamento;
	private StatusPedido status;
	private Long id;
}
