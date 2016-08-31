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
import sg.sistemas.entidades.SgtipoAlerta;
import sg.sistemas.entity.SgAutenticar;
import sg.sistemas.util.ConnectDB;
import sg.sistemas.util.ManejoSessiones;

/**
 *
 * @author Misael
 */
@ManagedBean(name = "ctrlSgTipoAlerta")
@RequestScoped
public class controladorSgTipoAlerta implements Serializable {

    private final String SELECT_TABLE = "SgtipoAlerta.findAll";
    private final String SP_CREATE = "SP_INSERT_SGTIPO_ALERTA";
    private final String SP_UPDATE = "SP_UPDATE_SGTIPO_ALERTA";
    private final String SP_DELETE = "SP_DELETE_SGTIPO_ALERTA";
    private final String SP_IN_PARAMETER1 = "P_IDTIPO_ALERTA";
    private final String SP_IN_PARAMETER2 = "P_DESCRIPCION";
    private final String SP_OUT_PARAMETER = "P_RESULTADO";
    private final String RESULT_SP = "OK";
    private final String msjDialog = "form1:mjs";
    private final String msjCreate = "Se inserto correctamente";
    private final String msjUpdate = "Se actualizo correctamente";
    private final String msjDelete = "Se Elimino correctamente";
    private final String error = "La operacion no ha sido realiazada";

    private SgAutenticar autenticar;
    private SgtipoAlerta sgtipoalerta;

    EntityManagerFactory emf;
    EntityManager em;
    ConnectDB db;

    /**
     * Creates a new instance of controladorSgCriticidad
     */
    @PostConstruct
    public void init() {
        autenticar = new SgAutenticar();
        sgtipoalerta = new SgtipoAlerta();
        HttpSession ss = (HttpSession) ManejoSessiones.getSession();
        autenticar = (SgAutenticar) ss.getAttribute(ManejoSessiones.sessionName);
        db = new ConnectDB();
        emf = db.accesoDB(autenticar.getUser(), autenticar.getPass());
    }

    /**
     * Creates a new instance of controladorSgTipoAlerta
     */
    public controladorSgTipoAlerta() {
    }

    public List<SgtipoAlerta> readAllSgTipoAlerta() {
        List<SgtipoAlerta> result = null;

        try {
            em = emf.createEntityManager();
            TypedQuery<SgtipoAlerta> query = em.createNamedQuery(SELECT_TABLE, SgtipoAlerta.class);

            result = query.getResultList();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }finally{
            em.close();
        }

        for (SgtipoAlerta c : result) {
            System.out.println(c.getIdtipoAlerta() + " " + c.getDescripcion());
        }
        return result;
    }

    public void insertSgTipoAlerta() {
        try {
            em = emf.createEntityManager();
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery(SP_CREATE);
            query.setParameter(SP_IN_PARAMETER1, sgtipoalerta.getIdtipoAlerta());
            query.setParameter(SP_IN_PARAMETER2, sgtipoalerta.getDescripcion());

            query.execute();

            String resultado = (String) query.getOutputParameterValue(SP_OUT_PARAMETER);
            System.out.println("Resultado: " + resultado);

            if (resultado.equals(RESULT_SP)) {
                FacesContext.getCurrentInstance().addMessage(msjDialog, new FacesMessage(msjCreate));
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }finally{
            em.close();
        }
    }

    public void updateSgTipoAlerta() {
        try {
            em = emf.createEntityManager();
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery(SP_UPDATE);
            query.setParameter(SP_IN_PARAMETER1, sgtipoalerta.getIdtipoAlerta());
            query.setParameter(SP_IN_PARAMETER2, sgtipoalerta.getDescripcion());

            query.execute();

            String resultado = (String) query.getOutputParameterValue(SP_OUT_PARAMETER);
            System.out.println("Resultado: " + resultado);

            if (resultado.equals(RESULT_SP)) {
                FacesContext.getCurrentInstance().addMessage(msjDialog, new FacesMessage(msjUpdate));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }finally{
            em.close();
        }
    }

    public void deleteSgTipoAlerta() {
        try {
            em = emf.createEntityManager();
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery(SP_DELETE);
            query.setParameter(SP_IN_PARAMETER1, sgtipoalerta.getIdtipoAlerta());

            query.execute();

            String resultado = (String) query.getOutputParameterValue(SP_OUT_PARAMETER);
            System.out.println("Resultado: " + resultado);
            if (resultado.equals(RESULT_SP)) {
                FacesContext.getCurrentInstance().addMessage(msjDialog, new FacesMessage(msjDelete));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }finally{
            em.close();
        }
    }

    public SgtipoAlerta getSgtipoalerta() {
        return sgtipoalerta;
    }

    public void setSgtipoalerta(SgtipoAlerta sgtipoalerta) {
        this.sgtipoalerta = sgtipoalerta;
    }

}
