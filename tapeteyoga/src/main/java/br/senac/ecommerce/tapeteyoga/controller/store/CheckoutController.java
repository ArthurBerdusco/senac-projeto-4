package br.senac.ecommerce.tapeteyoga.controller.store;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.senac.ecommerce.tapeteyoga.model.Carrinho;
import br.senac.ecommerce.tapeteyoga.model.Client;
import br.senac.ecommerce.tapeteyoga.model.ItemCarrinho;
import br.senac.ecommerce.tapeteyoga.model.ItemPedido;
import br.senac.ecommerce.tapeteyoga.model.Pedido;
import br.senac.ecommerce.tapeteyoga.repository.ClientRepository;
import br.senac.ecommerce.tapeteyoga.repository.PedidoRepository;
import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("checkout")
public class CheckoutController {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    PedidoRepository pedidoRepository;
    
    @GetMapping
    public String checkout(HttpSession session){
        return "store/checkout";
    }

    @PostMapping
    public String concluirPagamento(HttpSession session, Model model){
      
        Pedido pedido = new Pedido();
        List<ItemPedido> itensPedido = new ArrayList<>();


        Client client = clientRepository.findById((Long) session.getAttribute("clientId")).orElseThrow();

        Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");

        pedido.setTotal(carrinho.getTotal());
        pedido.setCliente(client);
        pedido.setStatus("Aguardando pagamento");

        pedido.setSerial(ThreadLocalRandom.current().nextLong(1000000000L, 10000000000L));
        
       // Obtém a data e hora atual
       LocalDateTime dataAtual = LocalDateTime.now();
        
       // Formata a data e hora no padrão 'dd/MM/yyyy - hh:mm'
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm");
       String dataHoraFormatada = dataAtual.format(formatter);

        // Define a data formatada no pedido
        pedido.setDataPedido(dataHoraFormatada);
        
        for(ItemCarrinho itemCarrinho : carrinho.getItens()){
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setProduto(itemCarrinho.getProduto());
            itemPedido.setPedido(pedido);
            itemPedido.setTotal(itemCarrinho.getTotal());
            itemPedido.setQuantidade(itemCarrinho.getQuantidade());
            itensPedido.add(itemPedido);
        }

        pedido.setItens(itensPedido);

        pedidoRepository.save(pedido);

        session.setAttribute("carrinho", new Carrinho());

        return "redirect:/pedidos";
    }

}
