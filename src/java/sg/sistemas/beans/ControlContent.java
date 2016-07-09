/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Misael
 */
@ManagedBean (name="controlContentBean")
@SessionScoped 
public class ControlContent {

    /**
     * Creates a new instance of ControlContent
     */
    public ControlContent() {
    }
   
    public void pruebaTrabajo()
    {
        System.out.println("Funciona la actulización");
        System.out.println("Ejemplo de actualización de github");
    }
    
}
