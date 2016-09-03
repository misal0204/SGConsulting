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
    private String SELECT_TABLE = "SgRol.findAll";
    private String SP_CREATE = "SP_INSERT_SG_ROL";
    private String SP_UPDATE = "SP_UPDATE_SG_ROL";
    private String SP_DELETE = "SP_DELETE_SG_ROL";
    private String SP_IN_PARAMETER1 = "P_IDROL";
    private String SP_IN_PARAMETER2 = "P_NOMBRE";
    private String SP_OUT_PARAMETER = "P_RESULTADO";
    private final String RESULT_SP = "OK";
    private final String msjDialog = "form1:msj";
    private final String msjCreate = "Se inserto correctamente";
    private final String msjUpdate = "Se actualizo correctamente";
    private final String msjDelete = "Se Elimino correctamente";
    private final String error = "La operacion no ha sido realiazada";

    private SgAutenticar autenticar;
    private SgRol sgrol;

    EntityManagerFactory emf;
    EntityManager em;
    ConnectDB db;

    @PostConstruct
    public void init() {
        autenticar = new SgAutenticar();
        sgrol = new SgRol();
        HttpSession ss = (HttpSession) ManejoSessiones.getSession();
        autenticar = (SgAutenticar) ss.getAttribute(ManejoSessiones.sessionName);
        db = new ConnectDB();
        emf = db.accesoDB(autenticar.getUser(), autenticar.getPass());
    }

    public controladorSgRol() {
    }

    public List<SgRol> readAllRol() {
        List<SgRol> result = null;

        try {
            em = emf.createEntityManager();
            TypedQuery<SgRol> query = em.createNamedQuery(SELECT_TABLE, SgRol.class);

            result = query.getResultList();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }

        return result;
    }

    public void insertRol() {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery(SP_CREATE);
            query.setParameter(SP_IN_PARAMETER1, sgrol.getIdrol());
            query.setParameter(SP_IN_PARAMETER2, sgrol.getNombre());

            query.execute();

            String resultado = (String) query.getOutputParameterValue(SP_OUT_PARAMETER);
            System.out.println(resultado);

            if (resultado.equals(RESULT_SP)) {
                FacesContext.getCurrentInstance().addMessage(msjDialog, new FacesMessage(msjCreate));
            } else {
                FacesContext.getCurrentInstance().addMessage(msjDialog, new FacesMessage(error + " : " + resultado));
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
            FacesContext.getCurrentInstance().addMessage(msjDialog, new FacesMessage(e.getMessage()));
        } finally {
            em.close();
        }
    }

    public void updateRol() {
        try {
            em = emf.createEntityManager();
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery(SP_UPDATE);
            query.setParameter(SP_IN_PARAMETER1, sgrol.getIdrol());
            query.setParameter(SP_IN_PARAMETER2, sgrol.getNombre());

            query.execute();

            String resultado = (String) query.getOutputParameterValue(SP_OUT_PARAMETER);
            System.out.println(resultado);

            if (resultado.equals(RESULT_SP)) {
                FacesContext.getCurrentInstance().addMessage(msjDialog, new FacesMessage(msjUpdate));
            } else {
                FacesContext.getCurrentInstance().addMessage(msjDialog, new FacesMessage(error + " : " + resultado));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            FacesContext.getCurrentInstance().addMessage(msjDialog, new FacesMessage(e.getMessage()));
        } finally {
            em.close();
        }
    }

    public void deleteRol() {
        try {
            em = emf.createEntityManager();
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery(SP_DELETE);
            query.setParameter(SP_IN_PARAMETER1, sgrol.getIdrol());

            query.execute();

            String resultado = (String) query.getOutputParameterValue(SP_OUT_PARAMETER);
            System.out.println(resultado);
            if (resultado.equals(RESULT_SP)) {
                FacesContext.getCurrentInstance().addMessage(msjDialog, new FacesMessage(msjDelete));
            } else {
                FacesContext.getCurrentInstance().addMessage(msjDialog, new FacesMessage(error + " : " + resultado));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            FacesContext.getCurrentInstance().addMessage(msjDialog, new FacesMessage(e.getMessage()));
        } finally {
            em.close();
        }
    }

    public SgAutenticar getAutenticar() {
        return autenticar;
    }

    public void setAutenticar(SgAutenticar autenticar) {
        this.autenticar = autenticar;
    }

    public SgRol getSgrol() {
        return sgrol;
    }

    public void setSgrol(SgRol sgrol) {
        this.sgrol = sgrol;
    }
}
