package org.serratec.TrabalhoFinal.dto;

import lombok.Data;
import org.serratec.TrabalhoFinal.enums.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class PedidoDTO {

    private Long id;
    private LocalDate dataPedido;
    private BigDecimal valorTotal;
    private ClienteDTO cliente;
    private List<ItemPedidoDTO> itens;
    private StatusPedido status;

    @Data
    public static class ItemPedidoDTO {
        private Long produtoId;
        private String nomeProduto;
        private Integer quantidade;
        private BigDecimal subtotal;
    }
}
