/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.solution.beans;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sg.solution.entity.Usuario;

/**
 *
 * @author Misael
 */
@ManagedBean(name = "validarBeans", eager = true)
@SessionScoped
public class ValidarBeans implements Serializable {

    private Usuario user;

    /**
     * Creates a new instance of ValidarBeans
     */
    public ValidarBeans() {

        user = new Usuario();
    }

    @PostConstruct
    public void init() {
        user = new Usuario();
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public String ingresarSistema() {
        String usuario = user.getUsuario();
        String password = user.getPassword();

        if (usuario.equals("misael") && password.equals("12345")) {
            return "menuprincipal?faces-redirect=true";
        } else {
            return "login?faces-redirect=true";
        }

    }

}
