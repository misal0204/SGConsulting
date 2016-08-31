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
import sg.sistemas.entidades.Sgdepartamento;
import sg.sistemas.entity.SgAutenticar;
import sg.sistemas.util.ConnectDB;
import sg.sistemas.util.ManejoSessiones;

/**
 *
 * @author Misael
 */
@ManagedBean(name = "ctrlDepartamento")
@RequestScoped
public class controladorSgDepartamentos implements Serializable {

    private final String SELECT_TABLE = "Sgdepartamento.findAll";
    private final String SP_CREATE = "SP_INSERT_SGDEPARTAMENTO";
    private final String SP_UPDATE = "SP_UPDATE_SGDEPARTAMENTO";
    private final String SP_DELETE = "SP_DELETE_SGDEPARTAMENTO";
    private final String SP_IN_PARAMETER1 = "P_IDDEPT";
    private final String SP_IN_PARAMETER2 = "P_DESCRIPCION";
    private final String SP_OUT_PARAMETER = "P_RESULTADO";
    private final String RESULT_SP = "OK";
    private final String msjDialog = "form1:mjs";
    private final String msjCreate = "Se inserto correctamente";
    private final String msjUpdate = "Se actualizo correctamente";
    private final String msjDelete = "Se Elimino correctamente";
    private final String error = "La operacion no ha sido realiazada";

    /**
     * Creates a new instance of controladorSgDepartamentos
     */
    private SgAutenticar autenticar;
    private Sgdepartamento sgdepartamento;

    EntityManagerFactory emf;
    EntityManager em;
    ConnectDB db;

    @PostConstruct
    public void init() {
        autenticar = new SgAutenticar();
        sgdepartamento = new Sgdepartamento();
        HttpSession ss = (HttpSession) ManejoSessiones.getSession();
        autenticar = (SgAutenticar) ss.getAttribute(ManejoSessiones.sessionName);
        db = new ConnectDB();
        emf = db.accesoDB(autenticar.getUser(), autenticar.getPass());
    }

    public controladorSgDepartamentos() {
    }

    public List<Sgdepartamento> readAllCentros() {
        List<Sgdepartamento> result = null;

        try {
            em = emf.createEntityManager();
            TypedQuery<Sgdepartamento> query = em.createNamedQuery(SELECT_TABLE, Sgdepartamento.class);

            result = query.getResultList();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally{
            em.close();
        }

        for (Sgdepartamento c : result) {
            System.out.println(c.getIddept() + " " + c.getDescripcion());
        }
        return result;
    }

    public void insertCentros() {
        try {
            em = emf.createEntityManager();
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery(SP_CREATE);
            query.setParameter(SP_IN_PARAMETER1, sgdepartamento.getIddept());
            query.setParameter(SP_IN_PARAMETER2, sgdepartamento.getDescripcion());

            query.execute();

            String resultado = (String) query.getOutputParameterValue(SP_OUT_PARAMETER);
            System.out.println("Resultado: " + resultado);

            if (resultado.equals(RESULT_SP)) {
                FacesContext.getCurrentInstance().addMessage(msjDialog, new FacesMessage(msjCreate));
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally{
            em.close();
        }
    }

    public void updateCentros() {
        try {
            em = emf.createEntityManager();
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery(SP_UPDATE);
            query.setParameter(SP_IN_PARAMETER1, sgdepartamento.getIddept());
            query.setParameter(SP_IN_PARAMETER2, sgdepartamento.getDescripcion());
            
            query.execute();

            String resultado = (String) query.getOutputParameterValue(SP_OUT_PARAMETER);
            System.out.println("Resultado: " + resultado);

            if (resultado.equals(RESULT_SP)) {
                FacesContext.getCurrentInstance().addMessage(msjDialog, new FacesMessage(msjUpdate));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally{
            em.close();
        }
    }

    public void deleteCentros() {
        try {
            em = emf.createEntityManager();
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery(SP_DELETE);
            query.setParameter(SP_IN_PARAMETER1, sgdepartamento.getIddept());
            
            query.execute();

            String resultado = (String) query.getOutputParameterValue(SP_OUT_PARAMETER);
            System.out.println("Resultado: " + resultado);
            if (resultado.equals(RESULT_SP)) {
                FacesContext.getCurrentInstance().addMessage(msjDialog, new FacesMessage(msjDelete));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally{
            em.close();
        }
    }

    public Sgdepartamento getSgdepartamento() {
        return sgdepartamento;
    }

    public void setSgdepartamento(Sgdepartamento sgdepartamento) {
        this.sgdepartamento = sgdepartamento;
    }

}
