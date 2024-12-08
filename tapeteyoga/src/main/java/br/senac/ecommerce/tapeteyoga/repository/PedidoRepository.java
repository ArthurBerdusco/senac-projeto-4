package br.senac.ecommerce.tapeteyoga.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senac.ecommerce.tapeteyoga.model.Pedido;


public interface PedidoRepository extends JpaRepository<Pedido, Long> {
  
    List<Pedido> findAllByOrderByIdDesc();
    List<Pedido> findAllByOrderByDataPedidoDesc();

    List<Pedido> findByClienteIdOrderByIdDesc(Long clienteId);

}
