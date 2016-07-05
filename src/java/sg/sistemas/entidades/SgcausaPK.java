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
public class SgcausaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDSOCIEDAD")
    private String idsociedad;
    @Basic(optional = false)
    @Column(name = "NONC")
    private String nonc;
    @Basic(optional = false)
    @Column(name = "IDCAUSA")
    private String idcausa;

    public SgcausaPK() {
    }

    public SgcausaPK(String idsociedad, String nonc, String idcausa) {
        this.idsociedad = idsociedad;
        this.nonc = nonc;
        this.idcausa = idcausa;
    }

    public String getIdsociedad() {
        return idsociedad;
    }

    public void setIdsociedad(String idsociedad) {
        this.idsociedad = idsociedad;
    }

    public String getNonc() {
        return nonc;
    }

    public void setNonc(String nonc) {
        this.nonc = nonc;
    }

    public String getIdcausa() {
        return idcausa;
    }

    public void setIdcausa(String idcausa) {
        this.idcausa = idcausa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsociedad != null ? idsociedad.hashCode() : 0);
        hash += (nonc != null ? nonc.hashCode() : 0);
        hash += (idcausa != null ? idcausa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgcausaPK)) {
            return false;
        }
        SgcausaPK other = (SgcausaPK) object;
        if ((this.idsociedad == null && other.idsociedad != null) || (this.idsociedad != null && !this.idsociedad.equals(other.idsociedad))) {
            return false;
        }
        if ((this.nonc == null && other.nonc != null) || (this.nonc != null && !this.nonc.equals(other.nonc))) {
            return false;
        }
        if ((this.idcausa == null && other.idcausa != null) || (this.idcausa != null && !this.idcausa.equals(other.idcausa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgcausaPK[ idsociedad=" + idsociedad + ", nonc=" + nonc + ", idcausa=" + idcausa + " ]";
    }
    
}
