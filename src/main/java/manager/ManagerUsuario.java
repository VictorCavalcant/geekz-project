/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
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
public class ManagerUsuario implements Serializable{

    @EJB
    UsuarioServico usuarioServico;

    private Usuario usuario;

    @PostConstruct
    public void instanciar() {
        Map<String, String> parms = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String visualizar = parms.get("visualizar");
        String editar = parms.get("editar");

        if (visualizar != null) {
            usuario = usuarioServico.find(Long.parseLong(visualizar));
        } else if (editar != null) {
            usuario = usuarioServico.find(Long.parseLong(editar));
        } else {
            usuario = new Usuario();
            usuario.ativo = true;
        }
    }
    
    public void salvar() {
        if (usuarioServico.validarCadastro(usuario) == "valido") {
            System.out.println("entrei é valido!");
            usuarioServico.salvar(usuario);
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuário registrado com sucesso!"));
        } else {
            System.out.println("falhou");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, usuarioServico.validarCadastro(usuario), usuarioServico.validarCadastro(usuario)));
        }
        
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
    
    
    

}
