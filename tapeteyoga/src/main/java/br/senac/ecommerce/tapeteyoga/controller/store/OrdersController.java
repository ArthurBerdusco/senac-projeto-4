package br.senac.ecommerce.tapeteyoga.controller.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.senac.ecommerce.tapeteyoga.repository.PedidoRepository;
import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("pedidos")
public class OrdersController {

    @Autowired
    PedidoRepository pedidoRepository;
    
    @GetMapping
    public String pedidos(HttpSession session, Model model){

        if (session.getAttribute("UsuarioLogado") == null) {
            return "redirect:/login";
        }
        
        session.setAttribute("pedidos", pedidoRepository.findByClienteIdOrderByIdDesc((long)session.getAttribute("clientId")));

        return "store/orders";
    }

   

}
