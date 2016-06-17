/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.solution.entity;

/**
 *
 * @author Misael
 */
public class Usuario {

    private String usuario;
    private String password;
    private String centro;

    public Usuario() {
    }

    public Usuario(String usuario, String password, String centro) {
        this.usuario = usuario;
        this.password = password;
        this.centro = centro;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

}
