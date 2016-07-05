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
public class SgplanProgramaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDSOCIEDAD")
    private String idsociedad;
    @Basic(optional = false)
    @Column(name = "IDPLAN_PROG")
    private String idplanProg;

    public SgplanProgramaPK() {
    }

    public SgplanProgramaPK(String idsociedad, String idplanProg) {
        this.idsociedad = idsociedad;
        this.idplanProg = idplanProg;
    }

    public String getIdsociedad() {
        return idsociedad;
    }

    public void setIdsociedad(String idsociedad) {
        this.idsociedad = idsociedad;
    }

    public String getIdplanProg() {
        return idplanProg;
    }

    public void setIdplanProg(String idplanProg) {
        this.idplanProg = idplanProg;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsociedad != null ? idsociedad.hashCode() : 0);
        hash += (idplanProg != null ? idplanProg.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgplanProgramaPK)) {
            return false;
        }
        SgplanProgramaPK other = (SgplanProgramaPK) object;
        if ((this.idsociedad == null && other.idsociedad != null) || (this.idsociedad != null && !this.idsociedad.equals(other.idsociedad))) {
            return false;
        }
        if ((this.idplanProg == null && other.idplanProg != null) || (this.idplanProg != null && !this.idplanProg.equals(other.idplanProg))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgplanProgramaPK[ idsociedad=" + idsociedad + ", idplanProg=" + idplanProg + " ]";
    }
    
}
