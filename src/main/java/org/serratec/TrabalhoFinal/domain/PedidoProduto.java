package org.serratec.TrabalhoFinal.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "pedido_produto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_produto", nullable = false)
    private Produto produto;

    @NotNull(message = "Preencha a quantidade.")
    @Column(nullable = false)
    private Integer quantidade;

    @NotNull(message = "Preencha o valor unitário.")
    @Column(nullable = false)
    private BigDecimal precoUnitario;

    //Método para calcular valor total
    public BigDecimal getSubtotal() {
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }
}
