package manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.Produto;
import org.primefaces.PrimeFaces;
import servico.ProdutoServico;
import utilitario.Message;

/**
 *
 * @author victor
 */
@Named
@ManagedBean(value = "managerProduto")
@ViewScoped
public class ManagerProduto implements Serializable {

    @EJB
    ProdutoServico produtoServico;

    private Produto produto;
    private List<Produto> produtos;
    private List<Produto> produtosCarrinho;

    @PostConstruct
    public void instanciar() {
        System.out.println("Passando: ");
        PrimeFaces.current().ajax().update("@form");
        Map<String, String> parms = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String visualizar = parms.get("visualizar");
        String editar = parms.get("editar");

        if (visualizar != null) {
            produto = produtoServico.find(Long.valueOf(visualizar));
            System.out.println("Produto param ----> " + produto);
        } else if (editar != null) {
            produto = produtoServico.find(Long.valueOf(editar));
        } else {
            produto = new Produto();
            produto.ativo = true;
            produtos = produtoServico.findAll();
            produtosCarrinho = new ArrayList<>();
        }
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
    }

    public void atualizarProduto(Produto produto) {
        
        System.out.println("Produto que ta vindo ----> " + produto);
        
        Produto updProduto = produtoServico.find(produto.getId());

        if (updProduto.getId() != null) {
            produtoServico.atualizar(produto);
            Message.msg("Produto atualizado com sucesso");
        } else {
            Message.errorMsg("Erro ao atualizar o produto");
        }

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
