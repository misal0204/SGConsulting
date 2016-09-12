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

    private boolean inputText = false;
    private boolean btnInsert = true;
    private boolean btnClose = false;

    private Map<String, Boolean> checked = new HashMap<String, Boolean>();
    private List<SgRol> items;

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

    public void submit() {
        List<SgRol> checkedItems = new ArrayList<SgRol>();
        items = readAllRol();

        for (SgRol item : items) {
            if (checked.get(sgrol.getIdrol())) {
                checkedItems.add(item);
            }
        }

        for (SgRol r : checkedItems) {
            System.out.println("seleccionado: " + r.getIdrol() + " " + r.getNombre() + " Eliminado");
            deleteRol(r.getIdrol());
        }
        checked.clear();
    }

    public void changeState() {
        inputText = false;
        btnInsert = true;
        btnClose = false;
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
    
    public void nuevoElem()
    {
        RequestContext context = RequestContext.getCurrentInstance();
        try
        {
            this.sgrol = new SgRol();
            context.execute("ABRI_FORM();");
        }
        catch(Exception ex)
        {}
        finally
        {}
    }
    
    public void consultarElem()
    {
        RequestContext context = RequestContext.getCurrentInstance();
        String codi = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codi");
        try
        {
            em = emf.createEntityManager();
            this.sgrol = em.find(SgRol.class, codi);
            this.btnInsert = false;
            context.execute("ABRI_FORM();");
            //context.execute("setMessage('MESS_INFO', 'Atención', 'Registro consultado');");
        }
        catch(Exception ex)
        {
            context.execute("setMessage('MESS_ERRO', 'Error', '" + ex.getMessage() + "');");
        }
        finally
        {}
    }
    
    public void guardarElem()
    {
        RequestContext context = RequestContext.getCurrentInstance();
        em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try
        {
            em.persist(this.sgrol);
            this.btnInsert = false;
            tx.commit();
            context.execute("setMessage('MESS_INFO', 'Atención', 'Guardado');");
        }
        catch(Exception ex)
        {
            context.execute("setMessage('MESS_ERRO', 'Error', '" + ex.getMessage() + "');");
            tx.rollback();
        }
        finally
        {}
    }
    
    public void actualizarElem()
    {
        RequestContext context = RequestContext.getCurrentInstance();
        em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try
        {
            em.merge(this.sgrol);
            this.btnInsert = false;
            tx.commit();
            context.execute("setMessage('MESS_INFO', 'Atención', 'Modificado');");
        }
        catch(Exception ex)
        {
            context.execute("setMessage('MESS_ERRO', 'Error', '" + ex.getMessage() + "');");
            tx.rollback();
        }
        finally
        {}
    }
    
    public void eliminarElem()
    {
        RequestContext context = RequestContext.getCurrentInstance();
        em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try
        {
            em.remove(em.merge(this.sgrol));
            this.btnInsert = false;
            tx.commit();
            context.execute("setMessage('MESS_INFO', 'Atención', 'Eliminado');");
            context.execute("CERR_FORM();");
        }
        catch(Exception ex)
        {
            context.execute("setMessage('MESS_ERRO', 'Error', '" + ex.getMessage() + "');");
            tx.rollback();
        }
        finally
        {}
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
                inputText = true;
                btnInsert = false;
                btnClose = true;
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

    public void deleteRol(String id) {
        try {
            em = emf.createEntityManager();
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery(SP_DELETE);
            query.setParameter(SP_IN_PARAMETER1, id);

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
    } //YA ---------------------------------------------

    public void setSgrol(SgRol sgrol) {
        this.sgrol = sgrol;
    } //YA ---------------------------------------------

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

    public List<SgRol> getItems() {
        return items;
    }

    public void setItems(List<SgRol> items) {
        this.items = items;
    }

}
