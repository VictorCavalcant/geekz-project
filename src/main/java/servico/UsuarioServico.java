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

    public String validarCadastro(Usuario usuario) {

        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(usuario.getEmail());

        if (!matcher.matches()) {
            return "email inválido!";
        } else {

            String sql = "SELECT u from  Usuario u where u.email :email)";

            Query query = getEntityManager().createQuery(sql);

            query.setParameter("email", "%" + usuario.getEmail() + "%");

            if (query.getResultList().isEmpty()) {
                return "valido";
            } else {
                return "Usuário já existe!";
            }
        }

    }

}
