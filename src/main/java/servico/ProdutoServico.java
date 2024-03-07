package servico;

import generico.ServicoGenerico;
import javax.ejb.Stateless;
import javax.persistence.Query;
import modelo.Produto;

/**
 *
 * @author victor
 */
@Stateless
public class ProdutoServico extends ServicoGenerico<Produto> {

    public ProdutoServico() {
        super(Produto.class);
    }
    
    public Produto validarRegistro(Produto produto) {
        
        String sql = "SELECT p from Produto p where p.nome like lower(:nome) and p.descricao like lower(:descricao)";

        Query query = getEntityManager().createQuery(sql);
        query.setParameter("nome", "%" + produto.getNome() + "%");
        query.setParameter("descricao", "%" + produto.getDescricao() + "%");

        if (!query.getResultList().isEmpty()) {
            System.out.println("entrei não ta vazio!");
            return (Produto) query.getResultList().get(0);
        } else {
            return null;
        } 
    }
    
}
