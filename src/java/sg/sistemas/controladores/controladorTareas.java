/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.controladores;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.http.HttpSession;
import sg.sistemas.entidades.Sgcentro;
import sg.sistemas.entidades.Sgprocesos;
import sg.sistemas.entity.SgAutenticar;
import sg.sistemas.util.ConnectDB;
import sg.sistemas.util.ManejoSessiones;

/**
 *
 * @author Misael
 */
@ManagedBean(name = "ctrlTareas")
@RequestScoped
public class controladorTareas implements Serializable {

    /**
     * Creates a new instance of controladorTareas
     */
    private Sgprocesos sgprocesos;
    private Sgcentro sgcentro;
    private SgAutenticar usuario;
    EntityManagerFactory emf;
    EntityManager em;

    @PostConstruct
    public void init() {

        sgprocesos = new Sgprocesos();
        sgcentro= new Sgcentro();
        HttpSession ss = (HttpSession) ManejoSessiones.getSession();
        usuario = (SgAutenticar) ss.getAttribute("credenciales");
    }

    public controladorTareas() {
    }

    public void insertProceso() {
        ConnectDB db = new ConnectDB();
        System.out.println("Session de usuario: " + usuario.getUser() + " " + usuario.getPass());
        try {

            emf = db.accesoDB(usuario.getUser(), usuario.getPass());
            em = emf.createEntityManager();
            em.getTransaction().begin();

            StoredProcedureQuery query = em.createNamedStoredProcedureQuery("SP_INSERT_SGPROCESOS");
            query.setParameter("p_idprocesos", sgprocesos.getIdprocesos());
            query.setParameter("p_descripcion", sgprocesos.getDescripcion());
            query.execute();

            String resultado = (String) query.getOutputParameterValue("p_resultado");
            System.out.println("Resultado: " + resultado);

        } catch (Exception e) {

            System.err.println("Error en insertar proceso: " + e.getMessage());
        } finally {
            emf.close();
        }

    }

    public void insertCentro() {

        ConnectDB db = new ConnectDB();
        System.out.println("Session de usuario: " + usuario.getUser() + " " + usuario.getPass());

        try {
            emf = db.accesoDB(usuario.getUser(), usuario.getPass());
            em = emf.createEntityManager();
            em.getTransaction().begin();

            StoredProcedureQuery query = em.createNamedStoredProcedureQuery("SP_INSERT_SGCENTRO");
            query.setParameter("P_IDCENTRO", sgcentro.getIdcentro());
            query.setParameter("P_DESCRIPCION", sgcentro.getDescripcion());
            query.execute();

            String resultado = (String) query.getOutputParameterValue("P_RESULTADO");
            System.out.println("Resultado: " + resultado);
        } catch (Exception e) {
            System.err.println("Error en insertar centro " + e.getMessage());
        } finally {
            emf.close();
        }
    }

    public Sgprocesos getSgprocesos() {
        return sgprocesos;
    }

    public void setSgprocesos(Sgprocesos sgprocesos) {
        this.sgprocesos = sgprocesos;
    }

    public Sgcentro getSgcentro() {
        return sgcentro;
    }

    public void setSgcentro(Sgcentro sgcentro) {
        this.sgcentro = sgcentro;
    }

}
