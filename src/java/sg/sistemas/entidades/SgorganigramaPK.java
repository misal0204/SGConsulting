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
public class SgorganigramaPK  implements Serializable{
    
    @Basic (optional=false)
    @Column (name="COD_USUARIO")
    private String codusuario;
    @Basic (optional=false)
    @Column (name="IDPUESTO")
    private String idpuesto;

    public SgorganigramaPK() {
    }

    public SgorganigramaPK(String codusuario, String idpuesto) {
        this.codusuario = codusuario;
        this.idpuesto = idpuesto;
    }

    public String getCodusuario() {
        return codusuario;
    }

    public void setCodusuario(String codusuario) {
        this.codusuario = codusuario;
    }

    public String getIdpuesto() {
        return idpuesto;
    }

    public void setIdpuesto(String idpuesto) {
        this.idpuesto = idpuesto;
    }
    
    @Override
    public String toString(){
        return "";
    }
}
