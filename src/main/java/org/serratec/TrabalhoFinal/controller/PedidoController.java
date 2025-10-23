package org.serratec.TrabalhoFinal.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.TrabalhoFinal.domain.Pedido;
import org.serratec.TrabalhoFinal.dto.PedidoDTO;
import org.serratec.TrabalhoFinal.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> listarTodos() {
        List<PedidoDTO> pedidos = pedidoService.listarPedidos()
                .stream()
                .map(p -> {
                    PedidoDTO dto = new PedidoDTO(p);
                    dto.setCliente(p.getCliente()); // inclui o cliente completo
                    dto.setItens(p.getItens());     // inclui os itens do pedido
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> buscarPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPorId(id);
        PedidoDTO dto = new PedidoDTO(pedido);
        dto.setCliente(pedido.getCliente());
        dto.setItens(pedido.getItens());
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> criarPedido(@Valid @RequestBody Pedido pedido) {
        pedido.calcularValorTotal(); // calcula o total antes de salvar
        Pedido novoPedido = pedidoService.criarPedido(pedido);

        PedidoDTO dto = new PedidoDTO(novoPedido);
        dto.setCliente(novoPedido.getCliente());
        dto.setItens(novoPedido.getItens());

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> atualizarStatus(@PathVariable Long id, @Valid @RequestBody Pedido pedidoAtualizado) {
        Pedido pedido = pedidoService.atualizarPedido(id, pedidoAtualizado);

        PedidoDTO dto = new PedidoDTO(pedido);
        dto.setCliente(pedido.getCliente());
        dto.setItens(pedido.getItens());

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pedidoService.deletarPedido(id);
        return ResponseEntity.noContent().build(); // 204 - sem corpo
    }
}