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
public class SgriesgoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDSOCIEDAD")
    private String idsociedad;
    @Basic(optional = false)
    @Column(name = "IDRIESGO")
    private String idriesgo;

    public SgriesgoPK() {
    }

    public SgriesgoPK(String idsociedad, String idriesgo) {
        this.idsociedad = idsociedad;
        this.idriesgo = idriesgo;
    }

    public String getIdsociedad() {
        return idsociedad;
    }

    public void setIdsociedad(String idsociedad) {
        this.idsociedad = idsociedad;
    }

    public String getIdriesgo() {
        return idriesgo;
    }

    public void setIdriesgo(String idriesgo) {
        this.idriesgo = idriesgo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsociedad != null ? idsociedad.hashCode() : 0);
        hash += (idriesgo != null ? idriesgo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgriesgoPK)) {
            return false;
        }
        SgriesgoPK other = (SgriesgoPK) object;
        if ((this.idsociedad == null && other.idsociedad != null) || (this.idsociedad != null && !this.idsociedad.equals(other.idsociedad))) {
            return false;
        }
        if ((this.idriesgo == null && other.idriesgo != null) || (this.idriesgo != null && !this.idriesgo.equals(other.idriesgo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgriesgoPK[ idsociedad=" + idsociedad + ", idriesgo=" + idriesgo + " ]";
    }
    
}
