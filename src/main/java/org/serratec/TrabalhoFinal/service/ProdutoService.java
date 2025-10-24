package org.serratec.TrabalhoFinal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.serratec.TrabalhoFinal.domain.Categoria;
import org.serratec.TrabalhoFinal.domain.Produto;
import org.serratec.TrabalhoFinal.dto.CategoriaDTO;
import org.serratec.TrabalhoFinal.dto.ProdutoDTO;
import org.serratec.TrabalhoFinal.repository.CategoriaRepository;
import org.serratec.TrabalhoFinal.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProdutoService {
	
    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    
    public ProdutoService(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    // DTO -> Entity
    private Produto dtoToProduto(ProdutoDTO dto) {
        Produto produto = new Produto();
        produto.setId(dto.getId());
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setQuantidadeEstoque(dto.getQuantidadeEstoque());

        if (dto.getCategoriaDTO() != null && !dto.getCategoriaDTO().isEmpty()) {
            List<Categoria> categorias = dto.getCategoriaDTO().stream()
                    .map(categoriaDTO -> categoriaRepository.findById(categoriaDTO.getId())
                            .orElseThrow(() -> new EntityNotFoundException(
                                    "Categoria não encontrada: " + categoriaDTO.getId())))
                    .collect(Collectors.toList());
            produto.setCategorias(categorias);
        }
        return produto;
    }

    // Entity -> DTO
    private ProdutoDTO produtoToDTO(Produto produto) {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setDescricao(produto.getDescricao());
        dto.setPreco(produto.getPreco());
        dto.setQuantidadeEstoque(produto.getQuantidadeEstoque());

        if (produto.getCategorias() != null && !produto.getCategorias().isEmpty()) {
            List<CategoriaDTO> categoriasDto = produto.getCategorias().stream()
                    .map(cat -> {
                        CategoriaDTO cd = new CategoriaDTO();
                        cd.setId(cat.getId());
                        cd.setNome(cat.getNome());
                        return cd;
                    })
                    .collect(Collectors.toList());
            dto.setCategoriaDTO(categoriasDto);
        } else {
            dto.setCategoriaDTO(new ArrayList<>()); 
        }
        return dto;
    }


    public ProdutoDTO salvar(ProdutoDTO dto) {
        Produto produto = dtoToProduto(dto); 
        List<Categoria> categorias = new ArrayList<>();

        if (dto.getCategoriaDTO() != null && !dto.getCategoriaDTO().isEmpty()) {
            for (CategoriaDTO catDto : dto.getCategoriaDTO()) {
                Long id = catDto.getId();
                Categoria categoria = categoriaRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada: " + id));
                categorias.add(categoria);
            }
        }
        produto.setCategorias(categorias);
        Produto salvo = produtoRepository.save(produto);
        return produtoToDTO(salvo);
    }

    public List<ProdutoDTO> listarTodos() {
        return produtoRepository.findAll().stream()
                .map(this::produtoToDTO)
                .collect(Collectors.toList());
    }

    public ProdutoDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + id));
        return produtoToDTO(produto);
    }

    public ProdutoDTO atualizar(Long id, ProdutoDTO dto) {
        Produto existente = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + id));

        Produto atualizado = dtoToProduto(dto);
        atualizado.setId(id);

        Produto salvo = produtoRepository.save(atualizado);
        return produtoToDTO(salvo);
    }

    public void deletar(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + id));
        produtoRepository.delete(produto);
    }
}