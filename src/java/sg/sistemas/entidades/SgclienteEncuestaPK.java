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
public class SgclienteEncuestaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDSOCIEDAD")
    private String idsociedad;
    @Basic(optional = false)
    @Column(name = "NOENC")
    private String noenc;
    @Basic(optional = false)
    @Column(name = "IDCLIENTE")
    private String idcliente;

    public SgclienteEncuestaPK() {
    }

    public SgclienteEncuestaPK(String idsociedad, String noenc, String idcliente) {
        this.idsociedad = idsociedad;
        this.noenc = noenc;
        this.idcliente = idcliente;
    }

    public String getIdsociedad() {
        return idsociedad;
    }

    public void setIdsociedad(String idsociedad) {
        this.idsociedad = idsociedad;
    }

    public String getNoenc() {
        return noenc;
    }

    public void setNoenc(String noenc) {
        this.noenc = noenc;
    }

    public String getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(String idcliente) {
        this.idcliente = idcliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsociedad != null ? idsociedad.hashCode() : 0);
        hash += (noenc != null ? noenc.hashCode() : 0);
        hash += (idcliente != null ? idcliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgclienteEncuestaPK)) {
            return false;
        }
        SgclienteEncuestaPK other = (SgclienteEncuestaPK) object;
        if ((this.idsociedad == null && other.idsociedad != null) || (this.idsociedad != null && !this.idsociedad.equals(other.idsociedad))) {
            return false;
        }
        if ((this.noenc == null && other.noenc != null) || (this.noenc != null && !this.noenc.equals(other.noenc))) {
            return false;
        }
        if ((this.idcliente == null && other.idcliente != null) || (this.idcliente != null && !this.idcliente.equals(other.idcliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgclienteEncuestaPK[ idsociedad=" + idsociedad + ", noenc=" + noenc + ", idcliente=" + idcliente + " ]";
    }
    
}
