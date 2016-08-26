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
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;
import sg.sistemas.entidades.SgRol;
import sg.sistemas.entidades.Sgcentro;
import sg.sistemas.entity.SgAutenticar;
import sg.sistemas.util.ConnectDB;
import sg.sistemas.util.ManejoSessiones;

/**
 *
 * @author Carlos
 */
@ManagedBean(name = "ctrlCentros")
@RequestScoped
public class controladorSgCentros implements Serializable {

    private final String SELECT_TABLE = "Sgcentro.findAll";
    private final String SP_CREATE = "SP_INSERT_SGCENTRO";
    private final String SP_UPDATE = "SP_UPDATE_SGCENTRO";
    private final String SP_DELETE = "SP_DELETE_SGCENTRO";
    private final String SP_IN_PARAMETER1 = "P_IDCENTRO";
    private final String SP_IN_PARAMETER2 = "P_DESCRIPCION";
    private final String SP_OUT_PARAMETER = "P_RESULTADO";
    private final String RESULT_SP = "OK";
    private final String msjDialog = "form1:mjs";
    private final String msjCreate = "Se inserto correctamente";
    private final String msjUpdate = "Se actualizo correctamente";
    private final String msjDelete = "Se Elimino correctamente";
    private final String error = "La operacion no ha sido realiazada";

    private SgAutenticar autenticar;
    private Sgcentro sgcentro;

    EntityManagerFactory emf;
    EntityManager em;
    ConnectDB db;

    @PostConstruct
    public void init() {
        autenticar = new SgAutenticar();
        sgcentro = new Sgcentro();
        HttpSession ss = (HttpSession) ManejoSessiones.getSession();
        autenticar = (SgAutenticar) ss.getAttribute(ManejoSessiones.sessionName);
        db = new ConnectDB();
        emf = db.accesoDB(autenticar.getUser(), autenticar.getPass());
    }

    public controladorSgCentros() {
    }

    public void readAllCentros() {
        List<Sgcentro> result = null;

        try {
            em = emf.createEntityManager();
            TypedQuery<Sgcentro> query = em.createNamedQuery(SELECT_TABLE, Sgcentro.class);

            result = query.getResultList();

        } catch (Exception e) {
            System.err.println("Error sgcentro: " + e.getMessage());
        } finally {

            emf.close();
        }
    }

    public void insertCentros() {
        try {
            em = emf.createEntityManager();
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery(SP_CREATE);
            query.setParameter(SP_IN_PARAMETER1, "c000");
            query.setParameter(SP_IN_PARAMETER2, "Supervisor de planta II");

            query.execute();

            String resultado = (String) query.getOutputParameterValue(SP_OUT_PARAMETER);
            System.out.println("Resultado: " + resultado);

            if (resultado.equals(RESULT_SP)) {
                FacesContext.getCurrentInstance().addMessage(msjDialog, new FacesMessage(msjCreate));
            }

        } catch (Exception e) {
            System.err.println("Error sgcentro: " + e.getMessage());
        } finally {
            emf.close();
        }
    }

    public void updateCentros() {
        try {
            em = emf.createEntityManager();
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery(SP_UPDATE);
            query.setParameter(SP_IN_PARAMETER1, "SUPER3");
            query.setParameter(SP_IN_PARAMETER2, "Supervisor de planta II Y III");

            query.execute();

            String resultado = (String) query.getOutputParameterValue(SP_OUT_PARAMETER);
            System.out.println("Resultado: " + resultado);

            if (resultado.equals(RESULT_SP)) {
                FacesContext.getCurrentInstance().addMessage(msjDialog, new FacesMessage(msjUpdate));
            }
        } catch (Exception e) {
            System.err.println("Error sgCentros: " + e.getMessage());
        } finally {
            emf.close();
        }
    }

    public void deleteCentros() {
        try {
            em = emf.createEntityManager();
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery(SP_DELETE);
            query.setParameter(SP_IN_PARAMETER1, "SUPER3");

            query.execute();

            String resultado = (String) query.getOutputParameterValue(SP_OUT_PARAMETER);
            System.out.println("Resultado: " + resultado);
            if (resultado.equals(RESULT_SP)) {
                FacesContext.getCurrentInstance().addMessage(msjDialog, new FacesMessage(msjDelete));
            }
        } catch (Exception e) {
            System.err.println("Error sgrol: " + e.getMessage());
        } finally {
            emf.close();
        }
    }

    public Sgcentro getSgcentro() {
        return sgcentro;
    }

}
