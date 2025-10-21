package org.serratec.TrabalhoFinal.service;

import java.util.List;
import java.util.Optional;

import org.serratec.TrabalhoFinal.domain.Produto;
import org.serratec.TrabalhoFinal.repository.ProdutoRepository;

public class ProdutoService {
	
	private final ProdutoRepository produtoRepository;
	
	public ProdutoService (ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}
	
	public List<Produto> listaCategoria(){
		return produtoRepository.findAll();
	}
	public Optional<Produto> listaId(Long id){
		return produtoRepository.findById(id);
	}
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }
    public Produto atualizar(Long id, Produto produto) {
    	produto.setId(id);
        return produtoRepository.save(produto);
    }
    public void deletar(Long id) {
    	produtoRepository.deleteById(id);
    }
}
