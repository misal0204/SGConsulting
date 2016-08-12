/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.controladores;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;
import sg.sistemas.entidades.Sgcentro;
import sg.sistemas.entity.SgAutenticar;
import sg.sistemas.util.ConnectDB;
import sg.sistemas.util.ManejoSessiones;

/**
 *
 * @author Carlos
 */
@ManagedBean(name = "ctrlCentros")
@RequestScoped
public class controladorSgCentros implements Serializable{
    
    EntityManagerFactory emf;
    EntityManager em;
    
    SgAutenticar sga;
    ConnectDB db;
    
    @PostConstruct 
    public void init(){
        sga = new SgAutenticar();
        HttpSession ss = ManejoSessiones.getSession();
        sga = (SgAutenticar) ss.getAttribute("credenciales");
        
    }
    public controladorSgCentros() {
        
    }
    
    public List<sg.sistemas.entidades.Sgcentro> listadoCentros()
    {
        List<Sgcentro> result = null;
        try {
        db = new ConnectDB();
        emf = db.accesoDB(sga.getUser(), sga.getPass());
        em = emf.createEntityManager();
        em.getTransaction().begin();
        
        Query query = em.createQuery("Select s from Sgcentro s");
        result = query.getResultList();

        }
        
        catch (Exception e){
            System.err.println("Error en controlador SgCentro "+ e.getMessage());
        }
        finally{
            emf.close();
        }
        
        return result;
    }
}
