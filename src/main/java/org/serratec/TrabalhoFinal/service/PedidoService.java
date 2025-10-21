package org.serratec.TrabalhoFinal.service;

import jakarta.persistence.EntityNotFoundException;

import org.serratec.TrabalhoFinal.domain.Pedido;
import org.serratec.TrabalhoFinal.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido criarPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado com ID: " + id));
    }

    public Pedido atualizarPedido(Long id, Pedido pedidoAtualizado) {
        Pedido pedidoExistente = buscarPorId(id);
        	
        if (pedidoAtualizado.getStatus() != null) {
            pedidoExistente.setStatus(pedidoAtualizado.getStatus());
    }
        return pedidoRepository.save(pedidoExistente);
    }

    public void deletarPedido(Long id) {
        Pedido pedido = buscarPorId(id);
        pedidoRepository.delete(pedido);
    }
}