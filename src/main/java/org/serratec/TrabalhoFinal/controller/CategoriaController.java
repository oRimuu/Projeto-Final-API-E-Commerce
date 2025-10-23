package org.serratec.TrabalhoFinal.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.TrabalhoFinal.domain.Categoria;
import org.serratec.TrabalhoFinal.dto.CategoriaDTO;
import org.serratec.TrabalhoFinal.service.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping ("/categoria")
public class CategoriaController {
	
	private final CategoriaService categoriaService;
	
	public CategoriaController (CategoriaService categoriaService) {
		this.categoriaService = categoriaService;
	}
	
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listarTodas() {
        List<CategoriaDTO> categorias = categoriaService.listarCategorias()
                .stream()
                .map(c -> new CategoriaDTO(c.getId(), c.getNome(), c.getDescricao()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(categorias);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> buscarPorId(@PathVariable Long id) {
        Categoria categoria = categoriaService.buscarPorId(id);
        CategoriaDTO dto = new CategoriaDTO(categoria.getId(), categoria.getNome(), categoria.getDescricao());
        return ResponseEntity.ok(dto);
    }
    
    @PostMapping
    public ResponseEntity<CategoriaDTO> criar(@Valid @RequestBody CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());

        Categoria novaCategoria = categoriaService.salvar(categoria);
        CategoriaDTO novaDTO = new CategoriaDTO(novaCategoria.getId(), novaCategoria.getNome(), novaCategoria.getDescricao());

        return ResponseEntity.status(HttpStatus.CREATED).body(novaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> atualizar(@PathVariable Long id, @Valid @RequestBody CategoriaDTO dto) {
        Categoria categoriaAtualizada = new Categoria();
        categoriaAtualizada.setNome(dto.getNome());
        categoriaAtualizada.setDescricao(dto.getDescricao());

        Categoria categoria = categoriaService.atualizar(id, categoriaAtualizada);
        CategoriaDTO categoriaDTO = new CategoriaDTO(categoria.getId(), categoria.getNome(), categoria.getDescricao());

        return ResponseEntity.ok(categoriaDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        categoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
