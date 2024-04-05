package br.senac.ecommerce.tapeteyoga.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.senac.ecommerce.tapeteyoga.model.Produto;
import br.senac.ecommerce.tapeteyoga.service.ProdutoService;

@Controller
@RequestMapping("")
public class PaginaPrincipal {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String landingPage(Model model) {
        var listaProdutos = produtoService.buscarProdutosAtivos();
        model.addAttribute("produtoPage", listaProdutos);
        return "index";
    }

    @GetMapping("/produto")
    public String obterporId(@RequestParam(name = "id", required = false) Long id, Model model) {

        Optional<Produto> produtoVisualizar = produtoService.buscarProdutoPorId(id);

        if (produtoVisualizar.isPresent()) {
            Produto produto = produtoVisualizar.get();
            model.addAttribute("produto", produto);
            return "visualizar";
        } else {
            return "index";
        }
    }

}