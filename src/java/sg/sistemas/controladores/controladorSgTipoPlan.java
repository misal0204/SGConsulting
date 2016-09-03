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
import sg.sistemas.entidades.SgtipoPlan;
import sg.sistemas.entity.SgAutenticar;
import sg.sistemas.util.ConnectDB;
import sg.sistemas.util.ManejoSessiones;

/**
 *
 * @author Misael
 */
@ManagedBean(name = "ctrlSgTipoPlan")
@RequestScoped
public class controladorSgTipoPlan implements Serializable {

    private final String SELECT_TABLE = "SgtipoPlan.findAll";
    private final String SP_CREATE = "SP_INSERT_SGTIPO_PLAN";
    private final String SP_UPDATE = "SP_UPDATE_SGTIPO_PLAN";
    private final String SP_DELETE = "SP_DELETE_SGTIPO_PLAN";
    private final String SP_IN_PARAMETER1 = "P_IDTIPO_PLAN";
    private final String SP_IN_PARAMETER2 = "P_DESCRIPCION";
    private final String SP_OUT_PARAMETER = "P_RESULTADO";
    private final String RESULT_SP = "OK";
    private final String msjDialog = "form1:mjs";
    private final String msjCreate = "Se inserto correctamente";
    private final String msjUpdate = "Se actualizo correctamente";
    private final String msjDelete = "Se Elimino correctamente";
    private final String error = "La operacion no ha sido realiazada";

    private SgAutenticar autenticar;
    private SgtipoPlan sgtipoplan;

    EntityManagerFactory emf;
    EntityManager em;
    ConnectDB db;

    /**
     * Creates a new instance of controladorSgCriticidad
     */
    @PostConstruct
    public void init() {
        autenticar = new SgAutenticar();
        sgtipoplan = new SgtipoPlan();
        HttpSession ss = (HttpSession) ManejoSessiones.getSession();
        autenticar = (SgAutenticar) ss.getAttribute(ManejoSessiones.sessionName);
        db = new ConnectDB();
        emf = db.accesoDB(autenticar.getUser(), autenticar.getPass());
    }

    /**
     * Creates a new instance of controladorSgTipoPlan
     */
    public controladorSgTipoPlan() {
    }

    public List<SgtipoPlan> readAllSgTipoPLan() {
        List<SgtipoPlan> result = null;

        try {
            em = emf.createEntityManager();
            TypedQuery<SgtipoPlan> query = em.createNamedQuery(SELECT_TABLE, SgtipoPlan.class);

            result = query.getResultList();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }finally{
            em.close();
        }

        for (SgtipoPlan c : result) {
            System.out.println(c.getIdtipoPlan() + " " + c.getDescripcion());
        }
        return result;
    }

    public void insertSgTipoPlan() {
        try {
            em = emf.createEntityManager();
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery(SP_CREATE);
            query.setParameter(SP_IN_PARAMETER1, sgtipoplan.getIdtipoPlan());
            query.setParameter(SP_IN_PARAMETER2, sgtipoplan.getDescripcion());

            query.execute();

            String resultado = (String) query.getOutputParameterValue(SP_OUT_PARAMETER);
            System.out.println(resultado);

            if (resultado.equals(RESULT_SP)) {
                FacesContext.getCurrentInstance().addMessage(msjDialog, new FacesMessage(msjCreate));
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }finally{
            em.close();
        }
    }

    public void updateSgTipoPlan() {
        try {
            em = emf.createEntityManager();
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery(SP_UPDATE);
            query.setParameter(SP_IN_PARAMETER1, sgtipoplan.getIdtipoPlan());
            query.setParameter(SP_IN_PARAMETER2, sgtipoplan.getDescripcion());

            query.execute();

            String resultado = (String) query.getOutputParameterValue(SP_OUT_PARAMETER);
            System.out.println(resultado);

            if (resultado.equals(RESULT_SP)) {
                FacesContext.getCurrentInstance().addMessage(msjDialog, new FacesMessage(msjUpdate));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }finally{
            em.close();
        }
    }

    public void deleteSgTipoPlan() {
        try {
            em = emf.createEntityManager();
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery(SP_DELETE);
            query.setParameter(SP_IN_PARAMETER1, sgtipoplan.getIdtipoPlan());

            query.execute();

            String resultado = (String) query.getOutputParameterValue(SP_OUT_PARAMETER);
            System.out.println(resultado);
            if (resultado.equals(RESULT_SP)) {
                FacesContext.getCurrentInstance().addMessage(msjDialog, new FacesMessage(msjDelete));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }finally{
            em.close();
        }
    }

    public SgtipoPlan getSgtipoplan() {
        return sgtipoplan;
    }

    public void setSgtipoplan(SgtipoPlan sgtipoplan) {
        this.sgtipoplan = sgtipoplan;
    }

}
