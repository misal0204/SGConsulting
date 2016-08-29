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
import sg.sistemas.entidades.SgrolAuditor;
import sg.sistemas.entity.SgAutenticar;
import sg.sistemas.util.ConnectDB;
import sg.sistemas.util.ManejoSessiones;

/**
 *
 * @author Misael
 */
@ManagedBean(name = "ctrlSgRolAuditor")
@RequestScoped
public class controladorSgRolAuditor implements Serializable {

    private final String SELECT_TABLE = "SgrolAuditor.findAll";
    private final String SP_CREATE = "SP_INSERT_SGROL_AUDITOR";
    private final String SP_UPDATE = "SP_UPDATE_SGROL_AUDITOR";
    private final String SP_DELETE = "\"SP_DELETE_SGROL_AUDITOR";
    private final String SP_IN_PARAMETER1 = "P_IDROL_AUDITOR";
    private final String SP_IN_PARAMETER2 = "P_DESCRIPCION";
    private final String SP_OUT_PARAMETER = "P_RESULTADO";
    private final String RESULT_SP = "OK";
    private final String msjDialog = "form1:mjs";
    private final String msjCreate = "Se inserto correctamente";
    private final String msjUpdate = "Se actualizo correctamente";
    private final String msjDelete = "Se Elimino correctamente";
    private final String error = "La operacion no ha sido realiazada";

    private SgAutenticar autenticar;
    private SgrolAuditor sgrolauditor;

    EntityManagerFactory emf;
    EntityManager em;
    ConnectDB db;

    /**
     * Creates a new instance of controladorSgCriticidad
     */
    @PostConstruct
    public void init() {
        autenticar = new SgAutenticar();
        sgrolauditor = new SgrolAuditor();
        HttpSession ss = (HttpSession) ManejoSessiones.getSession();
        autenticar = (SgAutenticar) ss.getAttribute(ManejoSessiones.sessionName);
        db = new ConnectDB();
        emf = db.accesoDB(autenticar.getUser(), autenticar.getPass());
    }

    /**
     * Creates a new instance of controladorSgRolAuditor
     */
    public controladorSgRolAuditor() {
    }

    public List<SgrolAuditor> readAllProcesosSistema() {
        List<SgrolAuditor> result = null;

        try {
            em = emf.createEntityManager();
            TypedQuery<SgrolAuditor> query = em.createNamedQuery(SELECT_TABLE, SgrolAuditor.class);

            result = query.getResultList();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            emf.close();
        }

        for (SgrolAuditor c : result) {
            System.out.println(c.getIdrolAuditor() + " " + c.getDescripcion());
        }
        return result;
    }

    public void insertSgRolAuditor() {
        try {
            em = emf.createEntityManager();
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery(SP_CREATE);
            query.setParameter(SP_IN_PARAMETER1, sgrolauditor.getIdrolAuditor());
            query.setParameter(SP_IN_PARAMETER2, sgrolauditor.getDescripcion());

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

    public void updateSgRolAuditor() {
        try {
            em = emf.createEntityManager();
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery(SP_UPDATE);
            query.setParameter(SP_IN_PARAMETER1, sgrolauditor.getIdrolAuditor());
            query.setParameter(SP_IN_PARAMETER2, sgrolauditor.getDescripcion());
            
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

    public void deleteSgRolAuditor() {
        try {
            em = emf.createEntityManager();
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery(SP_DELETE);
            query.setParameter(SP_IN_PARAMETER1, sgrolauditor.getIdrolAuditor());
            
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

    public SgrolAuditor getSgrolauditor() {
        return sgrolauditor;
    }

    public void setSgrolauditor(SgrolAuditor sgrolauditor) {
        this.sgrolauditor = sgrolauditor;
    }
    
}
