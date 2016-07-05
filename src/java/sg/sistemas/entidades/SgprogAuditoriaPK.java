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
public class SgprogAuditoriaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDSOCIEDAD")
    private String idsociedad;
    @Basic(optional = false)
    @Column(name = "IDPROG_AUD")
    private String idprogAud;

    public SgprogAuditoriaPK() {
    }

    public SgprogAuditoriaPK(String idsociedad, String idprogAud) {
        this.idsociedad = idsociedad;
        this.idprogAud = idprogAud;
    }

    public String getIdsociedad() {
        return idsociedad;
    }

    public void setIdsociedad(String idsociedad) {
        this.idsociedad = idsociedad;
    }

    public String getIdprogAud() {
        return idprogAud;
    }

    public void setIdprogAud(String idprogAud) {
        this.idprogAud = idprogAud;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsociedad != null ? idsociedad.hashCode() : 0);
        hash += (idprogAud != null ? idprogAud.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgprogAuditoriaPK)) {
            return false;
        }
        SgprogAuditoriaPK other = (SgprogAuditoriaPK) object;
        if ((this.idsociedad == null && other.idsociedad != null) || (this.idsociedad != null && !this.idsociedad.equals(other.idsociedad))) {
            return false;
        }
        if ((this.idprogAud == null && other.idprogAud != null) || (this.idprogAud != null && !this.idprogAud.equals(other.idprogAud))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgprogAuditoriaPK[ idsociedad=" + idsociedad + ", idprogAud=" + idprogAud + " ]";
    }
    
}
