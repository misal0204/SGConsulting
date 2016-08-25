/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.controladores;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;
import sg.sistemas.entidades.SgRol;
import sg.sistemas.entity.SgAutenticar;
import sg.sistemas.util.ConnectDB;
import sg.sistemas.util.ManejoSessiones;

/**
 *
 * @author Misael
 */
@ManagedBean(name = "ctrlRol")
@RequestScoped
public class controladorSgRol implements Serializable {

    /**
     * Creates a new instance of controladorSgRol
     */
    private SgAutenticar autenticar;
    private SgRol sgrol;

    EntityManagerFactory emf;
    EntityManager em;

    @PostConstruct
    public void init() {
        autenticar = new SgAutenticar();
        sgrol = new SgRol();
        HttpSession ss = (HttpSession) ManejoSessiones.getSession();
        autenticar = (SgAutenticar) ss.getAttribute("credenciales");
    }

    public controladorSgRol() {
    }

    public void readAllRol() {
        ConnectDB db = new ConnectDB();
        List<SgRol> result = null;

        try {

            emf = db.accesoDB(autenticar.getUser(), autenticar.getPass());
            em = emf.createEntityManager();
            TypedQuery<SgRol> query = em.createNamedQuery("SgRol.findAll", SgRol.class);

            result = query.getResultList();

        } catch (Exception e) {
            System.err.println("Error sgrol: " + e.getMessage());
        } finally {
            emf.close();
        }

        for (SgRol r : result) {
            System.out.println(r.getIdrol() + " " + r.getNombre());
        }
    }

    public void insertRol() {
        ConnectDB db= new ConnectDB();
        try {
            
            emf = db.accesoDB(autenticar.getUser(), autenticar.getPass());
            em = emf.createEntityManager();
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery("SP_INSERT_SG_ROL");
            query.setParameter("p_idrol", "SUPER3");
            query.setParameter("p_nombre", "Supervisor de planta II");

            query.execute();

            String resultado = (String) query.getOutputParameterValue("p_resultado");
            System.out.println("Resultado: " + resultado);

            if (resultado.equals("OK")) {
                FacesContext.getCurrentInstance().addMessage("form1:mjs", new FacesMessage("Se inserto correctamente"));
            }

        } catch (Exception e) {
            System.err.println("Error sgrol: " + e.getMessage());
        } finally {
            emf.close();
        }
    }

    public void updateRol() {
        ConnectDB db= new ConnectDB();
        try {
            
            emf = db.accesoDB(autenticar.getUser(), autenticar.getPass());
            em = emf.createEntityManager();
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery("SP_UPDATE_SG_ROL");
            query.setParameter("p_idrol", "SUPER3");
            query.setParameter("p_nombre", "Supervisor de planta II Y III");

            query.execute();

            String resultado = (String) query.getOutputParameterValue("p_resultado");
            System.out.println("Resultado: " + resultado);

            if (resultado.equals("OK")) {
                FacesContext.getCurrentInstance().addMessage("form1:mjs", new FacesMessage("Se actualizo correctamente"));
            }
        } catch (Exception e) {
            System.err.println("Error sgrol: " + e.getMessage());
        } finally {
            emf.close();
        }
    }

    public void deleteRol() {
        ConnectDB db= new ConnectDB();
        try {
            
            emf = db.accesoDB(autenticar.getUser(), autenticar.getPass());
            em = emf.createEntityManager();
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery("SP_DELETE_SG_ROL");
            query.setParameter("p_idrol", "SUPER2");

            query.execute();

            String resultado = (String) query.getOutputParameterValue("p_resultado");
            System.out.println("Resultado: " + resultado);
            if (resultado.equals("OK")) {
                FacesContext.getCurrentInstance().addMessage("form1:mjs", new FacesMessage("Se Elimino correctamente"));
            }
        } catch (Exception e) {
            System.err.println("Error sgrol: " + e.getMessage());
        } finally {
            emf.close();
        }
    }
}
