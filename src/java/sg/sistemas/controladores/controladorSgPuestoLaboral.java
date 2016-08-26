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
import sg.sistemas.entidades.SgprocesosSistema;
import sg.sistemas.entidades.SgpuestoLaboral;
import sg.sistemas.entity.SgAutenticar;
import sg.sistemas.util.ConnectDB;
import sg.sistemas.util.ManejoSessiones;

/**
 *
 * @author Misael
 */
@ManagedBean(name = "ctrlSgPuestoLaboral")
@RequestScoped
public class controladorSgPuestoLaboral implements Serializable {

    private final String SELECT_TABLE = "SgpuestoLaboral.findAll";
    private final String SP_CREATE = "SP_INSERT_SGPUESTO_LABORAL";
    private final String SP_UPDATE = "SP_UPDATE_SGPUESTO_LABORAL";
    private final String SP_DELETE = "SP_DELETE_SGPUESTO_LABORAL";
    private final String SP_IN_PARAMETER1 = "P_IDPUESTO";
    private final String SP_IN_PARAMETER2 = "P_PUESTO";
    private final String SP_IN_PARAMETER3 = "P_DESCRIPCION";
    private final String SP_OUT_PARAMETER = "P_RESULTADO";
    private final String RESULT_SP = "OK";
    private final String msjDialog = "form1:mjs";
    private final String msjCreate = "Se inserto correctamente";
    private final String msjUpdate = "Se actualizo correctamente";
    private final String msjDelete = "Se Elimino correctamente";
    private final String error = "La operacion no ha sido realiazada";

    private SgAutenticar autenticar;
    private SgpuestoLaboral sgpuestolaboral;

    EntityManagerFactory emf;
    EntityManager em;
    ConnectDB db;

    /**
     * Creates a new instance of controladorSgCriticidad
     */
    @PostConstruct
    public void init() {
        autenticar = new SgAutenticar();
        sgpuestolaboral = new SgpuestoLaboral();
        HttpSession ss = (HttpSession) ManejoSessiones.getSession();
        autenticar = (SgAutenticar) ss.getAttribute(ManejoSessiones.sessionName);
        db = new ConnectDB();
        emf = db.accesoDB(autenticar.getUser(), autenticar.getPass());
    }

    /**
     * Creates a new instance of controladorSgPuestoLaboral
     */
    public controladorSgPuestoLaboral() {
    }

    public void readAllPuestoLaboral() {
        List<SgpuestoLaboral> result = null;

        try {
            em = emf.createEntityManager();
            TypedQuery<SgpuestoLaboral> query = em.createNamedQuery(SELECT_TABLE, SgpuestoLaboral.class);

            result = query.getResultList();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            emf.close();
        }

        for (SgpuestoLaboral c : result) {
            System.out.println(c.getIdpuesto() + " " + c.getPuesto());
        }
    }

    public void insertPuestoLaboral() {
        try {
            em = emf.createEntityManager();
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery(SP_CREATE);
            query.setParameter(SP_IN_PARAMETER1, "c000");
            query.setParameter(SP_IN_PARAMETER2, "Supervisor de planta II");
            query.setParameter(SP_IN_PARAMETER3, "Supervisor de planta II");

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

    public void updatePuestoLaboral() {
        try {
            em = emf.createEntityManager();
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery(SP_UPDATE);
            query.setParameter(SP_IN_PARAMETER1, "SUPER3");
            query.setParameter(SP_IN_PARAMETER2, "Supervisor de planta II Y III");
            query.setParameter(SP_IN_PARAMETER3, "Supervisor de planta II");
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

    public void deletePuestoLaboral() {
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

    public SgpuestoLaboral getSgpuestolaboral() {
        return sgpuestolaboral;
    }

    public void setSgpuestolaboral(SgpuestoLaboral sgpuestolaboral) {
        this.sgpuestolaboral = sgpuestolaboral;
    }

}
