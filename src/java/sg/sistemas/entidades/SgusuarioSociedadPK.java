/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Misael
 */
@Embeddable
public class SgusuarioSociedadPK implements Serializable{
    
    @Basic(optional = false)
    @Column(name = "COD_USUARIO")
    private String codusuario;
    
    @Basic(optional = false)
    @Column(name = "IDSOCIEDAD")
    private String idsociedad;

    public SgusuarioSociedadPK() {
    }

    public SgusuarioSociedadPK(String codusuario, String idsociedad) {
        this.codusuario = codusuario;
        this.idsociedad = idsociedad;
    }

    public String getCodusuario() {
        return codusuario;
    }

    public void setCodusuario(String codusuario) {
        this.codusuario = codusuario;
    }

    public String getIdsociedad() {
        return idsociedad;
    }

    public void setIdsociedad(String idsociedad) {
        this.idsociedad = idsociedad;
    }

    @Override
    public String toString() {
        return "SgusuarioSociedadPK{" + "codusuario=" + codusuario + ", idsociedad=" + idsociedad + '}';
    }
    
    
    
}
