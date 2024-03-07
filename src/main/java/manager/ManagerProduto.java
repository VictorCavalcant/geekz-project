package manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.Produto;
import servico.ProdutoServico;
import utilitario.Message;

/**
 *
 * @author victor
 */
@Named
@ManagedBean(value = "managerProduto")
@SessionScoped
public class ManagerProduto implements Serializable {

    @EJB
    ProdutoServico produtoServico;

    private Produto produto;
    private List<Produto> produtos;

    @PostConstruct
    public void instanciar() {
        produto = new Produto();
        produto.ativo = true;
        produtos = produtoServico.findAll();
    }

    public void adicionarAoCarrinho() {
        produtos.add(produto);
    }

    public void removerDoCarrinho() {

    }

    public void registrarProduto() {
        if (!produto.getNome().isEmpty() && !produto.getDescricao().isEmpty() && (produto.getPreco() > 0.0)) {
            Produto checkProduto = produtoServico.validarRegistro(produto);
            if (checkProduto == null) {
                produtoServico.salvar(produto);
                Message.msg("Produto cadastrado com sucesso!");
            } else {
                System.out.println("falhou");
                Message.errorMsg("Produto já cadastrado");
            }
        }

        instanciar();
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

}
