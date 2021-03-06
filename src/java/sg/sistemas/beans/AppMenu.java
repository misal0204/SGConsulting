/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.beans;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;
import sg.sistemas.entidades.Sgusuario;
import sg.sistemas.entity.SgAutenticar;
import sg.sistemas.entity.SgPrivilegios;
import sg.sistemas.util.ConnectDB;
import sg.sistemas.util.ManejoSessiones;

/**
 *
 * @author Misael
 */
@ManagedBean(name = "appMenu")
@SessionScoped
public class AppMenu implements Serializable {

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
        ss = ManejoSessiones.getSession();
        datos = (SgAutenticar) ss.getAttribute("credenciales");
        usuario = datosUsuario();
        privilegiosUsuario();
    }

    private Sgusuario datosUsuario() {
        Sgusuario sg = null;
        db = new ConnectDB();
        try {
            emf = db.accesoDB(datos.getUser(), datos.getPass());
            em = emf.createEntityManager();

            if (em != null) {
                Query q = em.createNamedQuery("Sgusuario.findByCodUsuario")
                        .setParameter("codUsuario", datos.getUser());

                sg = (Sgusuario) q.getSingleResult();
            }

        } catch (Exception e) {
            System.err.println("Error AppMenu " + e.getMessage());
        } finally {
            em.close();
        }

        return sg;
    }

    private void privilegiosUsuario() {
        List<SgPrivilegios> result = null;
        db = new ConnectDB();
        try {
            emf = db.accesoDB(datos.getUser(), datos.getPass());
            em = emf.createEntityManager();

            em.getTransaction().begin();

            TypedQuery<SgPrivilegios> query = em.createNamedQuery("SgPrivilegios.findByUser", SgPrivilegios.class);
            query.setParameter("cod_usuario", datos.getUser());
            
            result = query.getResultList();

            for (SgPrivilegios l : result) {
                System.out.println(l.getCod_usuario() + " - " + l.getIdprogram() + " - " + l.getIdmenu());
            }
        } catch (Exception e) {
            System.err.println("Listado de privilegios: " + e.getMessage());
        }
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
