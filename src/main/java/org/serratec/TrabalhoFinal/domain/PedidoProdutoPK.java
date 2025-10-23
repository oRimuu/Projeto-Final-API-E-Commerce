package org.serratec.TrabalhoFinal.domain;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PedidoProdutoPK implements Serializable {

    private Long pedidoId;
    private Long produtoId;

    public PedidoProdutoPK() {
    }

    public PedidoProdutoPK(Long pedidoId, Long produtoId) {
        this.pedidoId = pedidoId;
        this.produtoId = produtoId;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PedidoProdutoPK)) return false;
        PedidoProdutoPK that = (PedidoProdutoPK) o;
        return Objects.equals(getPedidoId(), that.getPedidoId()) &&
               Objects.equals(getProdutoId(), that.getProdutoId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPedidoId(), getProdutoId());
    }
}
