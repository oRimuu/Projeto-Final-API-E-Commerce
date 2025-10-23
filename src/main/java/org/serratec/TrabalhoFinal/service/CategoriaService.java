package org.serratec.TrabalhoFinal.service;

import java.util.List;

import org.serratec.TrabalhoFinal.domain.Categoria;
import org.serratec.TrabalhoFinal.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoriaService {

	@Autowired
    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com ID: " + id));
    }

    public Categoria salvar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria atualizar(Long id, Categoria novaCategoria) {
        Categoria categoriaExistente = buscarPorId(id);
        categoriaExistente.setNome(novaCategoria.getNome());
        categoriaExistente.setDescricao(novaCategoria.getDescricao());
        return categoriaRepository.save(categoriaExistente);
    }

    public void deletar(Long id) {
        Categoria categoria = buscarPorId(id);
        categoriaRepository.delete(categoria);
    }
}
