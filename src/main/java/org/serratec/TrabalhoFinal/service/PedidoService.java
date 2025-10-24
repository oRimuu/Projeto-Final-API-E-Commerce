package org.serratec.TrabalhoFinal.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.serratec.TrabalhoFinal.domain.Pedido;
import org.serratec.TrabalhoFinal.domain.PedidoProduto;
import org.serratec.TrabalhoFinal.domain.PedidoProdutoPK;
import org.serratec.TrabalhoFinal.domain.Produto;
import org.serratec.TrabalhoFinal.dto.ClienteDTO;
import org.serratec.TrabalhoFinal.dto.PedidoDTO;
import org.serratec.TrabalhoFinal.repository.PedidoRepository;
import org.serratec.TrabalhoFinal.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

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
        dto.setValorDesconto(pedido.getValorDesconto());

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

        if (pedido.getValorTotal().compareTo(new BigDecimal("100")) > 0) {
            BigDecimal desconto = pedido.getValorTotal().multiply(new BigDecimal("0.10"));
            pedido.setValorDesconto(desconto);
            pedido.setValorTotal(pedido.getValorTotal().subtract(desconto));
        }

        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido atualizar(Long id, Pedido pedidoAtualizado) {
        Pedido pedidoExistente = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado com ID: " + id));

        pedidoExistente.setCliente(pedidoAtualizado.getCliente());
        pedidoExistente.setDataPedido(pedidoAtualizado.getDataPedido());
        pedidoExistente.setStatus(pedidoAtualizado.getStatus());

        pedidoExistente.getItens().clear();

        for (PedidoProduto itemNovo : pedidoAtualizado.getItens()) {
            Produto produto = itemNovo.getProduto();

            if (produto == null || produto.getId() == null) {
                throw new IllegalArgumentException("Produto inválido em um dos itens do pedido.");
            }

            PedidoProduto novoItem = new PedidoProduto();
            novoItem.setPedido(pedidoExistente);
            novoItem.setProduto(produto);
            novoItem.setQuantidade(itemNovo.getQuantidade());
            novoItem.setId(new PedidoProdutoPK(pedidoExistente.getId(), produto.getId()));
            
            if (itemNovo.getQuantidade() != null && produto.getPreco() != null) {
                novoItem.setSubtotal(produto.getPreco().multiply(new BigDecimal(itemNovo.getQuantidade())));
            }

            pedidoExistente.getItens().add(novoItem);
        }

        calcularValorTotal(pedidoExistente);

        if (pedidoExistente.getValorTotal().compareTo(new BigDecimal("100")) > 0) {
            BigDecimal desconto = pedidoExistente.getValorTotal().multiply(new BigDecimal("0.10"));
            pedidoExistente.setValorDesconto(desconto);
            pedidoExistente.setValorTotal(pedidoExistente.getValorTotal().subtract(desconto));
        } else {
            pedidoExistente.setValorDesconto(BigDecimal.ZERO);
        }

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
