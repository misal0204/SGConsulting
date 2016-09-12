/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.controladores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
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

    private boolean inputText = false;
    private boolean btnInsert = true;
    private boolean btnClose = false;

    private Map<String, Boolean> checked = new HashMap<String, Boolean>();
    private List<Sgcriticidad> items;

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

    public void submit() {
        List<Sgcriticidad> checkedItems = new ArrayList<Sgcriticidad>();
        items = readAllCriticidad();

        for (Sgcriticidad item : items) {
            if (checked.get(sgcriticidad.getIdcriticidad())) {
                checkedItems.add(item);
            }
        }

        for (Sgcriticidad r : checkedItems) {
            System.out.println("seleccionado: " + r.getIdcriticidad()+ " " + r.getDescripcion()+ " Eliminado");
            deleteCriticidad(r.getIdcriticidad());
        }
        checked.clear();
    }

    public void changeState() {
        inputText = false;
        btnInsert = true;
        btnClose = false;
    }

    public List<Sgcriticidad> readAllCriticidad() {
        List<Sgcriticidad> result = null;

        try {
            em = emf.createEntityManager();
            TypedQuery<Sgcriticidad> query = em.createNamedQuery(SELECT_TABLE, Sgcriticidad.class);

            result = query.getResultList();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }

        return result;
    }

    public void nuevoElem() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            this.sgcriticidad = new Sgcriticidad();
            context.execute("ABRI_FORM();");
        } catch (Exception ex) {
        } finally {
        }
    }

    public void consultarElem() {
        RequestContext context = RequestContext.getCurrentInstance();
        String codi = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codi");
        try {
            em = emf.createEntityManager();
            this.sgcriticidad = em.find(Sgcriticidad.class, codi);
            this.btnInsert = false;
            context.execute("ABRI_FORM();");
            //context.execute("setMessage('MESS_INFO', 'Atención', 'Registro consultado');");
        } catch (Exception ex) {
            context.execute("setMessage('MESS_ERRO', 'Error', '" + ex.getMessage() + "');");
        } finally {
        }
    }

    public void guardarElem() {
        RequestContext context = RequestContext.getCurrentInstance();
        em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            em.persist(this.sgcriticidad);
            this.btnInsert = false;
            tx.commit();
            context.execute("setMessage('MESS_INFO', 'Atención', 'Guardado');");
        } catch (Exception ex) {
            context.execute("setMessage('MESS_ERRO', 'Error', '" + ex.getMessage() + "');");
            tx.rollback();
        } finally {
        }
    }

    public void actualizarElem() {
        RequestContext context = RequestContext.getCurrentInstance();
        em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            em.merge(this.sgcriticidad);
            this.btnInsert = false;
            tx.commit();
            context.execute("setMessage('MESS_INFO', 'Atención', 'Modificado');");
        } catch (Exception ex) {
            context.execute("setMessage('MESS_ERRO', 'Error', '" + ex.getMessage() + "');");
            tx.rollback();
        } finally {
        }
    }

    public void eliminarElem() {
        RequestContext context = RequestContext.getCurrentInstance();
        em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            em.remove(em.merge(this.sgcriticidad));
            this.btnInsert = false;
            tx.commit();
            context.execute("setMessage('MESS_INFO', 'Atención', 'Eliminado');");
            context.execute("CERR_FORM();");
        } catch (Exception ex) {
            context.execute("setMessage('MESS_ERRO', 'Error', '" + ex.getMessage() + "');");
            tx.rollback();
        } finally {
        }
    }

    public void insertCriticidad() {
        try {
            em = emf.createEntityManager();
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery(SP_CREATE);
            query.setParameter(SP_IN_PARAMETER1, sgcriticidad.getIdcriticidad());
            query.setParameter(SP_IN_PARAMETER2, sgcriticidad.getDescripcion());

            query.execute();

            String resultado = (String) query.getOutputParameterValue(SP_OUT_PARAMETER);
            System.out.println(resultado);

            if (resultado.equals(RESULT_SP)) {
                FacesContext.getCurrentInstance().addMessage(msjDialog, new FacesMessage(msjCreate));
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
    }

    public void updateCriticidad() {
        try {
            em = emf.createEntityManager();
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery(SP_UPDATE);
            query.setParameter(SP_IN_PARAMETER1, sgcriticidad.getIdcriticidad());
            query.setParameter(SP_IN_PARAMETER2, sgcriticidad.getDescripcion());
            query.execute();

            String resultado = (String) query.getOutputParameterValue(SP_OUT_PARAMETER);
            System.out.println(resultado);

            if (resultado.equals(RESULT_SP)) {
                FacesContext.getCurrentInstance().addMessage(msjDialog, new FacesMessage(msjUpdate));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
    }

    public void deleteCriticidad(String id) {
        try {
            em = emf.createEntityManager();
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery(SP_DELETE);
            query.setParameter(SP_IN_PARAMETER1, id);

            query.execute();

            String resultado = (String) query.getOutputParameterValue(SP_OUT_PARAMETER);
            System.out.println(resultado);
            if (resultado.equals(RESULT_SP)) {
                FacesContext.getCurrentInstance().addMessage(msjDialog, new FacesMessage(msjDelete));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            FacesContext.getCurrentInstance().addMessage(msjDialog, new FacesMessage(e.getMessage()));
        } finally {
            em.close();
        }
    }

    public Sgcriticidad getSgcriticidad() {
        return sgcriticidad;
    }

    public void setSgcriticidad(Sgcriticidad sgcriticidad) {
        this.sgcriticidad = sgcriticidad;
    }
    
    public SgAutenticar getAutenticar() {
        return autenticar;
    }

    public void setAutenticar(SgAutenticar autenticar) {
        this.autenticar = autenticar;
    }
    
    public boolean isInputText() {
        return inputText;
    }

    public void setInputText(boolean inputText) {
        this.inputText = inputText;
    }

    public boolean isBtnInsert() {
        return btnInsert;
    }

    public void setBtnInsert(boolean btnInsert) {
        this.btnInsert = btnInsert;
    }

    public boolean isBtnClose() {
        return btnClose;
    }

    public void setBtnClose(boolean btnClose) {
        this.btnClose = btnClose;
    }

    public Map<String, Boolean> getChecked() {
        return checked;
    }

    public void setChecked(Map<String, Boolean> checked) {
        this.checked = checked;
    }

    public List<Sgcriticidad> getItems() {
        return items;
    }

    public void setItems(List<Sgcriticidad> items) {
        this.items = items;
    }
}
