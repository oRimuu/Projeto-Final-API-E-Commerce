package org.serratec.TrabalhoFinal.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.serratec.TrabalhoFinal.domain.Cliente;
import org.serratec.TrabalhoFinal.domain.Pedido;
import org.serratec.TrabalhoFinal.domain.PedidoProduto;
import org.serratec.TrabalhoFinal.enums.StatusPedido;

import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
	
	private Long id;
	
	private LocalDate dataPedido;

	private BigDecimal valorTotal;
	
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;
	
	private List<PedidoProduto> itens;
	
	private StatusPedido status;
	
	
    public PedidoDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.dataPedido = pedido.getDataPedido();
        this.valorTotal = pedido.getValorTotal();
        this.status = pedido.getStatus();
    }
    // this.cliente = pedido.getCliente() !=null ? pedido.getCliente().getNome(): null; NÃO ESQUECER DE REALIZAR NO CONTROLLER!
}
