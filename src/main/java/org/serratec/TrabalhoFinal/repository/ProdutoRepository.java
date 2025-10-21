package org.serratec.TrabalhoFinal.repository;

import org.serratec.TrabalhoFinal.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

}
