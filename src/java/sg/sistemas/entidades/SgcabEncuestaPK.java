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
public class SgcabEncuestaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDSOCIEDAD")
    private String idsociedad;
    @Basic(optional = false)
    @Column(name = "NOENC")
    private String noenc;

    public SgcabEncuestaPK() {
    }

    public SgcabEncuestaPK(String idsociedad, String noenc) {
        this.idsociedad = idsociedad;
        this.noenc = noenc;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsociedad != null ? idsociedad.hashCode() : 0);
        hash += (noenc != null ? noenc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgcabEncuestaPK)) {
            return false;
        }
        SgcabEncuestaPK other = (SgcabEncuestaPK) object;
        if ((this.idsociedad == null && other.idsociedad != null) || (this.idsociedad != null && !this.idsociedad.equals(other.idsociedad))) {
            return false;
        }
        if ((this.noenc == null && other.noenc != null) || (this.noenc != null && !this.noenc.equals(other.noenc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgcabEncuestaPK[ idsociedad=" + idsociedad + ", noenc=" + noenc + " ]";
    }
    
}
