/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.beans;

import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import sg.sistemas.entidades.Sgusuario;
import sg.sistemas.entity.SgAutenticar;
import sg.sistemas.util.ConnectDB;
import sg.sistemas.util.ManejoSessiones;

/**
 *
 * @author Misael
 */
@ManagedBean(name = "appMenu")
@SessionScoped
public class AppMenu {

    private EntityManagerFactory emf;
    private EntityManager em;
    private SgAutenticar datos;
    private Sgusuario usuario;
    private byte[] getImages;
    HttpSession ss;

    ConnectDB db;

    /**
     * Creates a new instance of AppMenu
     */
    @PostConstruct
    public void init() {
        db = new ConnectDB();
        ss = ManejoSessiones.getSession();
        datos = (SgAutenticar) ss.getAttribute("credenciales");

    }

    public AppMenu() {
        db = new ConnectDB();
        ss = ManejoSessiones.getSession();
        datos = (SgAutenticar) ss.getAttribute("credenciales");
        usuario = datosUsuario();
        
    }

    private Sgusuario datosUsuario() {
        Sgusuario sg = null;
        try {
            emf = db.accesoDB(datos.getUser(), datos.getPass());
            em = emf.createEntityManager();

            if (em != null) {
                Query q = em.createNamedQuery("Sgusuario.findByCodUsuario")
                        .setParameter("codUsuario", datos.getUser());

                sg = (Sgusuario) q.getSingleResult();
                /*System.out.println("Nombre: " + usuario.getUsuario());
                System.out.println("Nombre: " + usuario.getDui());
                System.out.println("Nombre: " + usuario.getEmail());*/
            }

        } catch (Exception e) {
            System.err.println("Error AppMenu " + e.getMessage());
        } finally {
            emf.close();
        }

        return sg;
    }
    

    public SgAutenticar getDatos() {
        return datos;
    }

    public void setDatos(SgAutenticar datos) {
        this.datos = datos;
    }

    public Sgusuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Sgusuario usuario) {
        this.usuario = usuario;
    }

    public byte[] getGetImages() {
        return getImages;
    }

    public void setGetImages(byte[] getImages) {
        this.getImages = getImages;
    }
}
