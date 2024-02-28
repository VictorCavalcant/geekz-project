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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.Usuario;
import servico.UsuarioServico;

/**
 *
 * @author victo
 */
@Named
@ViewScoped
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

    public void logar() {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(usuario.getEmail());

        if (!matcher.matches()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email invalido", "Email invalido"));
        } else {

            usuario = usuarioServico.validarLogin(usuario);
            if (usuario.getId() != null) {
                System.out.println(usuario);
                FacesContext context = FacesContext.getCurrentInstance();
                String url = context.getExternalContext().getRequestContextPath();
                try {
                    System.out.println("id usuario ------> " + usuario.getId());
                    context.getExternalContext().redirect(url + "/" + "main.xhtml?visualizar=" + usuario.getId());
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario não encontrado", "Usuario não encontrado"));
            }
        }
        instanciar();
    }

    public void cadastrar() {

        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(usuario.getEmail());

        if (!usuario.getEmail().isBlank() && !usuario.getSenha().isBlank()) {

            if (!matcher.matches()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email invalido", "Email invalido"));
            } else {
                Usuario checkUser = usuarioServico.validarCadastro(usuario);
                if (checkUser == null) {
                    usuarioServico.salvar(usuario);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuário registrado com sucesso!"));
                } else {
                    System.out.println("falhou");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário já cadastrado", "Usuário já cadastrado"));
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
