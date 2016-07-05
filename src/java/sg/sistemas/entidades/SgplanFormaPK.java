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
public class SgplanFormaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDSOCIEDAD")
    private String idsociedad;
    @Basic(optional = false)
    @Column(name = "IDPLAN_FORMA")
    private String idplanForma;

    public SgplanFormaPK() {
    }

    public SgplanFormaPK(String idsociedad, String idplanForma) {
        this.idsociedad = idsociedad;
        this.idplanForma = idplanForma;
    }

    public String getIdsociedad() {
        return idsociedad;
    }

    public void setIdsociedad(String idsociedad) {
        this.idsociedad = idsociedad;
    }

    public String getIdplanForma() {
        return idplanForma;
    }

    public void setIdplanForma(String idplanForma) {
        this.idplanForma = idplanForma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsociedad != null ? idsociedad.hashCode() : 0);
        hash += (idplanForma != null ? idplanForma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgplanFormaPK)) {
            return false;
        }
        SgplanFormaPK other = (SgplanFormaPK) object;
        if ((this.idsociedad == null && other.idsociedad != null) || (this.idsociedad != null && !this.idsociedad.equals(other.idsociedad))) {
            return false;
        }
        if ((this.idplanForma == null && other.idplanForma != null) || (this.idplanForma != null && !this.idplanForma.equals(other.idplanForma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgplanFormaPK[ idsociedad=" + idsociedad + ", idplanForma=" + idplanForma + " ]";
    }
    
}
