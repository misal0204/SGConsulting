/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.controladores;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import sg.sistemas.entity.SgAutenticar;
import sg.sistemas.util.ConnectDB;
import sg.sistemas.util.ManejoSessiones;

/**
 *
 * @author Misael
 */
@ManagedBean(name = "ctrlAcceso")
@SessionScoped
public class controladorAcesso implements Serializable {

    private SgAutenticar autenticar;
    private EntityManagerFactory emf;
    private EntityManager em;

    @PostConstruct
    public void init() {
        autenticar = new SgAutenticar();
    }

    /**
     * Creates a new instance of controladorAcesso
     */
    public controladorAcesso() {
    }

    public String inicioSession() throws EntityExistsException {
        ConnectDB db = new ConnectDB();
        String page = "login.xhtml";
        HttpSession ss;
        HttpServletRequest request
                = (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequest();
        try {
            emf = db.accesoDB(autenticar.getUser(), autenticar.getPass());
            em = emf.createEntityManager();

            if (em != null) {
                ss = request.getSession(false);
                ss.setAttribute(ManejoSessiones.sessionName, autenticar);
                page = "menuprincipal?faces-redirect=true";

            } else {
                System.err.println("Mensaje de Error en credenciales");

            }
        } catch (Exception e) {
            System.err.println("Error controlSession: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage("formLogin:mensajes", new FacesMessage("Error en credenciales, error en usuario o contraseña"));
        } finally {
            emf.close();
        }
        return page;
    }

    public boolean getUserDB() {

        return false;
    }

    public SgAutenticar getAutenticar() {
        return autenticar;
    }

    public void setAutenticar(SgAutenticar autenticar) {
        this.autenticar = autenticar;
    }

    public String logoutSession() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSession(false);
        session.invalidate();
        

        return "/login?faces-redirect=true";
    }
}
