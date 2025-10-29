package org.serratec.TrabalhoFinal.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.TrabalhoFinal.domain.Categoria;
import org.serratec.TrabalhoFinal.dto.CategoriaDTO;
import org.serratec.TrabalhoFinal.service.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    
    @Operation(summary = "Lista todas as categorias",
               description = "Retorna uma lista com todas as categorias cadastradas no sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = CategoriaDTO.class))),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listarTodas() {
        List<CategoriaDTO> categorias = categoriaService.listarCategorias()
                .stream()
                .map(c -> new CategoriaDTO(c.getId(), c.getNome()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(categorias);
    }

   
    @Operation(summary = "Busca uma categoria pelo ID",
               description = "Retorna uma categoria específica de acordo com o ID informado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoria encontrada",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = CategoriaDTO.class))),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> buscarPorId(@PathVariable Long id) {
        Categoria categoria = categoriaService.buscarPorId(id);
        CategoriaDTO dto = new CategoriaDTO(categoria.getId(), categoria.getNome());
        return ResponseEntity.ok(dto);
    }

    
    @Operation(summary = "Cria uma nova categoria",
               description = "Adiciona uma nova categoria no sistema a partir dos dados enviados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = CategoriaDTO.class))),
        @ApiResponse(responseCode = "400", description = "Erro de validação dos dados enviados")
    })
    @PostMapping
    public ResponseEntity<CategoriaDTO> criar(@Valid @RequestBody CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());

        Categoria novaCategoria = categoriaService.salvar(categoria);
        CategoriaDTO novaDTO = new CategoriaDTO(novaCategoria.getId(), novaCategoria.getNome());

        return ResponseEntity.status(HttpStatus.CREATED).body(novaDTO);
    }

    
    @Operation(summary = "Atualiza uma categoria existente",
               description = "Atualiza os dados de uma categoria com base no ID informado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = CategoriaDTO.class))),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> atualizar(@PathVariable Long id, @Valid @RequestBody CategoriaDTO dto) {
        Categoria categoriaAtualizada = new Categoria();
        categoriaAtualizada.setNome(dto.getNome());

        Categoria categoria = categoriaService.atualizar(id, categoriaAtualizada);
        CategoriaDTO categoriaDTO = new CategoriaDTO(categoria.getId(), categoria.getNome());

        return ResponseEntity.ok(categoriaDTO);
    }

   
    @Operation(summary = "Deleta uma categoria",
               description = "Remove uma categoria existente pelo ID informado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Categoria deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        categoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
