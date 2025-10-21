package org.serratec.TrabalhoFinal.service;

import java.util.List;
import java.util.Optional;

import org.serratec.TrabalhoFinal.domain.Categoria;
import org.serratec.TrabalhoFinal.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {
	
	private final CategoriaRepository categoriaRepository;
	
	public CategoriaService (CategoriaRepository categoriaRepository) {
		this.categoriaRepository = categoriaRepository;
	}
	
	public List<Categoria> listaCategoria(){
		return categoriaRepository.findAll();
	}
	public Optional<Categoria> listaId(Long id){
		return categoriaRepository.findById(id);
	}
    public Categoria salvar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }
    public Categoria atualizar(Long id, Categoria categoria) {
    	categoria.setId(id);
        return categoriaRepository.save(categoria);
    }
    public void deletar(Long id) {
    	categoriaRepository.deleteById(id);
    }
}
