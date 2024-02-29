package servico;

import javax.ejb.Stateless;
import modelo.Usuario;
import generico.ServicoGenerico;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Query;

/**
 *
 * @author victo
 */
@Stateless
public class UsuarioServico extends ServicoGenerico<Usuario> {

    public UsuarioServico() {
        super(Usuario.class);
    }

    public Usuario validarLogin(Usuario usuario) {

        String sql = "SELECT u from Usuario u where u.email like lower(:email) and u.senha like lower(:senha)";

        Query query = getEntityManager().createQuery(sql);
        query.setParameter("email", "%" + usuario.getEmail() + "%");
        query.setParameter("senha", "%" + usuario.getSenha() + "%");

        if (!query.getResultList().isEmpty()) {
            System.out.println("entrei não ta vazio!");
            return (Usuario) query.getResultList().get(0);
        } else {
            return null;
        }

    }

    public Usuario validarCadastro(Usuario usuario) {

        String sql = "SELECT u from Usuario u where u.email like lower(:email)";

        Query query = getEntityManager().createQuery(sql);

        query.setParameter("email", "%" + usuario.getEmail() + "%");

        return (Usuario) query.getResultList().get(0);
    }

}
