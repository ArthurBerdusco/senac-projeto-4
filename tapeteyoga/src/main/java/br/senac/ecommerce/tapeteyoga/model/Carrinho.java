package br.senac.ecommerce.tapeteyoga.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "carrinho", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<ItemCarrinho> itens;

    private BigDecimal total;

    public void atualizarTotal() {
        this.total = BigDecimal.ZERO; // Inicializa o total como zero
        for (ItemCarrinho item : this.itens) {
            System.out.println("TOTAL: " + this.total);
            this.total = this.total.add(item.getTotal()); // Soma o total de cada item ao total do carrinho
            System.out.println("TOTAL: " + this.total);
        }
    }

    public void subTotal(ItemCarrinho item){
        if (this.total == null) {
            this.total = BigDecimal.ZERO;
        }
    
        this.total = this.total.subtract(item.getTotal());
    } 

    public void removerValor(BigDecimal valor){
        if (this.total == null) {
            this.total = BigDecimal.ZERO;
        }
    
        this.total = this.total.subtract(valor);
    }

      @Override
    public String toString() {
        return "Carrinho{" +
                "id=" + id +
                ", total=" + total +
                '}';
    }
    

}
