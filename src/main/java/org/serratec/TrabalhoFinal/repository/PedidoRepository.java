package org.serratec.TrabalhoFinal.repository;


import java.util.List;

import org.serratec.TrabalhoFinal.domain.Pedido;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @EntityGraph(attributePaths = {"itens", "itens.produto"})
    List<Pedido> findAll();
}

