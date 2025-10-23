package org.serratec.TrabalhoFinal.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.serratec.TrabalhoFinal.domain.Pedido;
import org.serratec.TrabalhoFinal.domain.PedidoProduto;
import org.serratec.TrabalhoFinal.domain.Produto;
import org.serratec.TrabalhoFinal.dto.ClienteDTO;
import org.serratec.TrabalhoFinal.dto.PedidoDTO;
import org.serratec.TrabalhoFinal.repository.PedidoRepository;
import org.serratec.TrabalhoFinal.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoService(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
    }

    private PedidoDTO pedidoToDTO(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(pedido.getId());
        dto.setDataPedido(pedido.getDataPedido());
        dto.setValorTotal(pedido.getValorTotal());
        dto.setStatus(pedido.getStatus());

        if (pedido.getCliente() != null) {
            ClienteDTO clienteDTO = new ClienteDTO();
            clienteDTO.setId(pedido.getCliente().getId());
            clienteDTO.setNome(pedido.getCliente().getNome());
            clienteDTO.setTelefone(pedido.getCliente().getTelefone());
            clienteDTO.setCep(pedido.getCliente().getCep());
            clienteDTO.setCidade(pedido.getCliente().getCidade());
            dto.setCliente(clienteDTO);
        }

        if (pedido.getItens() != null && !pedido.getItens().isEmpty()) {
            List<PedidoDTO.ItemPedidoDTO> itensDTO = pedido.getItens().stream()
                    .map(item -> {
                        PedidoDTO.ItemPedidoDTO itemDTO = new PedidoDTO.ItemPedidoDTO();
                        itemDTO.setProdutoId(item.getProduto().getId());
                        itemDTO.setNomeProduto(item.getProduto().getNome());
                        itemDTO.setQuantidade(item.getQuantidade());
                        itemDTO.setSubtotal(item.getSubtotal());
                        return itemDTO;
                    }).collect(Collectors.toList());
            dto.setItens(itensDTO);
        }

        return dto;
    }


    public List<PedidoDTO> listarTodos() {
        return pedidoRepository.findAll().stream()
                .map(this::pedidoToDTO)
                .collect(Collectors.toList());
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado com ID: " + id));
    }

    public Pedido salvar(Pedido pedido) {
        calcularValorTotal(pedido);
        return pedidoRepository.save(pedido);
    }

    public Pedido atualizar(Long id, Pedido pedidoAtualizado) {
        Pedido pedidoExistente = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado com ID: " + id));

        pedidoExistente.setCliente(pedidoAtualizado.getCliente());
        pedidoExistente.setDataPedido(pedidoAtualizado.getDataPedido());
        pedidoExistente.setStatus(pedidoAtualizado.getStatus());
        pedidoExistente.setItens(pedidoAtualizado.getItens());

        calcularValorTotal(pedidoExistente);
        return pedidoRepository.save(pedidoExistente);
    }

    public void deletar(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado com ID: " + id));
        pedidoRepository.delete(pedido);
    }

    private void calcularValorTotal(Pedido pedido) {
        if (pedido.getItens() != null && !pedido.getItens().isEmpty()) {
            BigDecimal valorTotal = BigDecimal.ZERO;

            for (PedidoProduto item : pedido.getItens()) {
                Produto produto = produtoRepository.findById(item.getProduto().getId())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Produto não encontrado com ID: " + item.getProduto().getId()));

                item.setProduto(produto);
                item.setPrecoUnitario(produto.getPreco());
                item.setPedido(pedido);

                valorTotal = valorTotal.add(item.getSubtotal());
            }

            pedido.setValorTotal(valorTotal);
        } else {
            pedido.setValorTotal(BigDecimal.ZERO);
        }
    }
}
