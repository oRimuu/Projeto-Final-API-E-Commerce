package org.serratec.TrabalhoFinal.repository;


import java.util.List;

import org.serratec.TrabalhoFinal.domain.Pedido;
import org.serratec.TrabalhoFinal.enums.OpcaoPagamento;
import org.serratec.TrabalhoFinal.enums.StatusPedido;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @EntityGraph(attributePaths = {"itens", "itens.produto"})
    List<Pedido> findAll();
    
    @Modifying
    @Query("update Pedido p set p.status = :status where p.id = :id")
    void AtualizarStatusEPagamento(@Param("id") Long id, @Param("status") StatusPedido status, @Param("tipoPagamento") OpcaoPagamento opcaoPagamento);

}

