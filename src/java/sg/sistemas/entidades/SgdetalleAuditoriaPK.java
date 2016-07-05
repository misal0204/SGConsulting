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
public class SgdetalleAuditoriaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDSOCIEDAD")
    private String idsociedad;
    @Basic(optional = false)
    @Column(name = "IDPLAN_AUD")
    private String idplanAud;
    @Basic(optional = false)
    @Column(name = "IDDETALLE_AUD")
    private String iddetalleAud;

    public SgdetalleAuditoriaPK() {
    }

    public SgdetalleAuditoriaPK(String idsociedad, String idplanAud, String iddetalleAud) {
        this.idsociedad = idsociedad;
        this.idplanAud = idplanAud;
        this.iddetalleAud = iddetalleAud;
    }

    public String getIdsociedad() {
        return idsociedad;
    }

    public void setIdsociedad(String idsociedad) {
        this.idsociedad = idsociedad;
    }

    public String getIdplanAud() {
        return idplanAud;
    }

    public void setIdplanAud(String idplanAud) {
        this.idplanAud = idplanAud;
    }

    public String getIddetalleAud() {
        return iddetalleAud;
    }

    public void setIddetalleAud(String iddetalleAud) {
        this.iddetalleAud = iddetalleAud;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsociedad != null ? idsociedad.hashCode() : 0);
        hash += (idplanAud != null ? idplanAud.hashCode() : 0);
        hash += (iddetalleAud != null ? iddetalleAud.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgdetalleAuditoriaPK)) {
            return false;
        }
        SgdetalleAuditoriaPK other = (SgdetalleAuditoriaPK) object;
        if ((this.idsociedad == null && other.idsociedad != null) || (this.idsociedad != null && !this.idsociedad.equals(other.idsociedad))) {
            return false;
        }
        if ((this.idplanAud == null && other.idplanAud != null) || (this.idplanAud != null && !this.idplanAud.equals(other.idplanAud))) {
            return false;
        }
        if ((this.iddetalleAud == null && other.iddetalleAud != null) || (this.iddetalleAud != null && !this.iddetalleAud.equals(other.iddetalleAud))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgdetalleAuditoriaPK[ idsociedad=" + idsociedad + ", idplanAud=" + idplanAud + ", iddetalleAud=" + iddetalleAud + " ]";
    }
    
}
