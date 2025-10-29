package org.serratec.TrabalhoFinal.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "pedido_produto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoProduto {

    @EmbeddedId
    private PedidoProdutoPK id = new PedidoProdutoPK();

    @ManyToOne
    @MapsId("pedidoId")
    @JoinColumn(name = "id_pedido")
    @JsonIgnore
    private Pedido pedido;

    @ManyToOne
    @MapsId("produtoId")
    @JsonBackReference
    @JoinColumn(name = "id_produto")
    private Produto produto;

    @NotNull(message = "A quantidade não pode ser nula.")
    @Min(value = 1, message = "A quantidade deve ser pelo menos 1.")
    @Column(nullable = false)
    private Integer quantidade;

    @NotNull(message = "Preencha o valor unitário.")
    @Column(nullable = false)
    private BigDecimal precoUnitario;

    @Column(nullable = false)
    private BigDecimal subtotal; 

    public void atualizarSubtotal() {
        if (precoUnitario != null && quantidade != null) {
            this.subtotal = precoUnitario.multiply(BigDecimal.valueOf(quantidade));
        } else {
            this.subtotal = BigDecimal.ZERO;
        }
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
        atualizarSubtotal();
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
        atualizarSubtotal();
    }
}
