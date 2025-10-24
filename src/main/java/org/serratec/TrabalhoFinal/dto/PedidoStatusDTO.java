package org.serratec.TrabalhoFinal.dto;

import org.serratec.TrabalhoFinal.enums.StatusPedido;

import lombok.Data;

@Data
public class PedidoStatusDTO {

	private StatusPedido status;
	private Long id;
}
