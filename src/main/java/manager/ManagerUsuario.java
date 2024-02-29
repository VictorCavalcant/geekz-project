/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import modelo.Usuario;
import servico.UsuarioServico;
import utilitario.Message;

/**
 *
 * @author victo
 */

@Named
@ManagedBean(value = "managerUsuario")
@SessionScoped
public class ManagerUsuario implements Serializable {

    @EJB
    UsuarioServico usuarioServico;

    private Usuario usuario;

    @PostConstruct
    public void instanciar() {
        Map<String, String> parms = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String visualizar = parms.get("visualizar");
        String editar = parms.get("editar");

        if (visualizar != null) {
            usuario = usuarioServico.find(Long.valueOf(visualizar));
        } else if (editar != null) {
            usuario = usuarioServico.find(Long.valueOf(editar));
        } else {
            usuario = new Usuario();
            usuario.ativo = true;
        }
    }

    public String logar() {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(usuario.getEmail());

        if (!matcher.matches()) {
            Message.errorMsg("Email inválido");
        } else {

            usuario = usuarioServico.validarLogin(usuario);
            if (usuario != null) {
                System.out.println("entrei!");
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                session.setAttribute("usuario", usuario);
                
                return "main.xhtml?faces-redirect=true";
                
                
//                FacesContext context = FacesContext.getCurrentInstance();;
//                String url = context.getExternalContext().getRequestContextPath();
//                try {
//                    System.out.println("id usuario ------> " + usuario.getId());
//                    context.getExternalContext().redirect(url + "/" + "main.xhtml");
//                } catch (IOException ex) {
//                    System.out.println(ex);
//                }
            } else {
                Message.errorMsg("Usuário não encontrado");
                return "login?faces-redirect=true";
            }
        }
        instanciar();
        return null;
    }

    public void cadastrar() {

        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(usuario.getEmail());

        if (!usuario.getEmail().isEmpty() && !usuario.getSenha().isEmpty()) {

            if (!matcher.matches()) {
                Message.errorMsg("Email inválido");
            } else {
                Usuario checkUser = usuarioServico.validarCadastro(usuario);
                if (checkUser == null) {
                    usuarioServico.salvar(usuario);
                    Message.msg("Usuário cadastrado com sucesso!");
                } else {
                    System.out.println("falhou");
                    Message.errorMsg("Usuário já cadastrado");
                }

            }

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Todos os campos devem ser preenchidos!", "Todos os campos devem ser preenchidos!"));
        }
        instanciar();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
