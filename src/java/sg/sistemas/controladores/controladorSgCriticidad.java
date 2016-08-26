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
import sg.sistemas.entidades.Sgcriticidad;
import sg.sistemas.entity.SgAutenticar;
import sg.sistemas.util.ConnectDB;
import sg.sistemas.util.ManejoSessiones;

/**
 *
 * @author Misael
 */
@ManagedBean(name = "ctrlCriticidad")
@RequestScoped
public class controladorSgCriticidad implements Serializable {

    private final String SELECT_TABLE = "Sgcriticidad.findAll";
    private final String SP_CREATE = "SP_INSERT_SGCRITICIDAD";
    private final String SP_UPDATE = "SP_UPDATE_SGCRITICIDAD";
    private final String SP_DELETE = "SP_DELETE_SGCRITICIDAD";
    private final String SP_IN_PARAMETER1 = "P_IDCRITICIDAD";
    private final String SP_IN_PARAMETER2 = "P_DESCRIPCION";
    private final String SP_OUT_PARAMETER = "P_RESULTADO";
    private final String RESULT_SP = "OK";
    private final String msjDialog = "form1:mjs";
    private final String msjCreate = "Se inserto correctamente";
    private final String msjUpdate = "Se actualizo correctamente";
    private final String msjDelete = "Se Elimino correctamente";
    private final String error = "La operacion no ha sido realiazada";

    private SgAutenticar autenticar;
    private Sgcriticidad sgcriticidad;

    EntityManagerFactory emf;
    EntityManager em;
    ConnectDB db;

    /**
     * Creates a new instance of controladorSgCriticidad
     */
    @PostConstruct
    public void init() {
        autenticar = new SgAutenticar();
        sgcriticidad = new Sgcriticidad();
        HttpSession ss = (HttpSession) ManejoSessiones.getSession();
        autenticar = (SgAutenticar) ss.getAttribute(ManejoSessiones.sessionName);
        db = new ConnectDB();
        emf = db.accesoDB(autenticar.getUser(), autenticar.getPass());
    }

    public controladorSgCriticidad() {
    }

    public void readAllCriticidad() {
        List<Sgcriticidad> result = null;

        try {
            em = emf.createEntityManager();
            TypedQuery<Sgcriticidad> query = em.createNamedQuery(SELECT_TABLE, Sgcriticidad.class);

            result = query.getResultList();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            emf.close();
        }

        for (Sgcriticidad c : result) {
            System.out.println(c.getIdcriticidad() + " " + c.getDescripcion());
        }
    }

    public void insertCriticidad() {
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
            System.err.println(e.getMessage());
        } finally {
            emf.close();
        }
    }

    public void updateCriticidad() {
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
            System.err.println(e.getMessage());
        } finally {
            emf.close();
        }
    }

    public void deleteCriticidad() {
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
            System.err.println(e.getMessage());
        } finally {
            emf.close();
        }
    }

    public Sgcriticidad getSgcriticidad() {
        return sgcriticidad;
    }

    public void setSgcriticidad(Sgcriticidad sgcriticidad) {
        this.sgcriticidad = sgcriticidad;
    }

}
