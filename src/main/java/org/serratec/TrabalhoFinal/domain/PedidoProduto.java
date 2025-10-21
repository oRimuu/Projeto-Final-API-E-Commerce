package org.serratec.TrabalhoFinal.domain;

import java.math.BigDecimal;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Data;

@Data
@Entity
public class PedidoProduto {
    @EmbeddedId
    // private PedidoProdutoId id  NÃO ESQUECER!!!!!!!
    private Long id;

    @ManyToOne
    @MapsId("pedidoId")
    private Pedido pedido;

    @ManyToOne
    @MapsId("produtoId")
    private Produto produto;

    private BigDecimal precoVenda;
    private BigDecimal desconto;
    private int quantidade;
}