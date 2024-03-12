package manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import modelo.Produto;
import servico.ProdutoServico;
import utilitario.Message;

/**
 *
 * @author victor
 */
@Named
@ManagedBean(value = "managerCarrinho")
@SessionScoped
public class ManagerCarrinho implements Serializable {

    @EJB
    ProdutoServico produtoServico;

    private List<Produto> produtosCarrinho;

    @PostConstruct
    public void instanciar() {
        System.out.println("instanciei carrinho");
        produtosCarrinho = new ArrayList<>();
    }

    public void adicionarAoCarrinho(Produto produto) {
        System.out.println("Produto --->  " + produto);
            System.out.println("Entrei não nulo");
            System.out.println("Produto (2) -----> " + produto);
            produtosCarrinho.add(produto);
            Message.msg("Produto adicionado com sucesso!");
    }

    public void removerDoCarrinho(Produto produto) {
        produtosCarrinho.remove(produto);
    }

    public List<Produto> getProdutosCarrinho() {
        return produtosCarrinho;
    }

    public void setProdutosCarrinho(List<Produto> produtosCarrinho) {
        this.produtosCarrinho = produtosCarrinho;
    }

}
