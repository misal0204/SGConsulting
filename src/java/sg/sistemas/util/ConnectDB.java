/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.util;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Misael
 */
public class ConnectDB {
    private EntityManagerFactory emf;
    private EntityManager em;
    public static String namePersistenceUnit = "SGSistemasPU";
    
    public EntityManagerFactory accesoDB(String usuario,String pass)
    {
        Map properties = PropertiesPersistence(usuario, pass);
        emf= Persistence.createEntityManagerFactory(namePersistenceUnit, properties);
        return emf;
    }
    
    private Map PropertiesPersistence(String usuario, String pass){
        Map properties = new HashMap();
        properties.put("javax.persistence.jdbc.user", usuario);
        properties.put("javax.persistence.jdbc.password", pass);
        return properties;
    }
}
