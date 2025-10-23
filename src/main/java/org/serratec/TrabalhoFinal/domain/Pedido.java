package org.serratec.TrabalhoFinal.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.serratec.TrabalhoFinal.enums.StatusPedido;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PastOrPresent(message = "A data de cadastro não pode ser futura.")
    @NotNull(message = "Preencha o campo data do pedido.")
    @Column(nullable = false)
    private LocalDate dataPedido;

    @NotNull(message = "Preencha o valor total.")
    @Column(nullable = false)
    private BigDecimal valorTotal;
    
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido")//, cascade = CascadeType.ALL
    private List<PedidoProduto> itens;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedido status = StatusPedido.PENDENTE;

    // Calcula o total automaticamente com base nos itens
    public BigDecimal calcularValorTotal() {
        if (itens != null && !itens.isEmpty()) {
        	 return itens.stream()
                .map(PedidoProduto::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return BigDecimal.ZERO;
        }
    }
}
