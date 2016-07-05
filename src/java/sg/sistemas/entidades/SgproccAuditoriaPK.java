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
public class SgproccAuditoriaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDSOCIEDAD")
    private String idsociedad;
    @Basic(optional = false)
    @Column(name = "IDPROG_AUD")
    private String idprogAud;
    @Basic(optional = false)
    @Column(name = "IDPROCESOS")
    private String idprocesos;
    @Basic(optional = false)
    @Column(name = "SUBPROCESO")
    private String subproceso;

    public SgproccAuditoriaPK() {
    }

    public SgproccAuditoriaPK(String idsociedad, String idprogAud, String idprocesos, String subproceso) {
        this.idsociedad = idsociedad;
        this.idprogAud = idprogAud;
        this.idprocesos = idprocesos;
        this.subproceso = subproceso;
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

    public String getIdprocesos() {
        return idprocesos;
    }

    public void setIdprocesos(String idprocesos) {
        this.idprocesos = idprocesos;
    }

    public String getSubproceso() {
        return subproceso;
    }

    public void setSubproceso(String subproceso) {
        this.subproceso = subproceso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsociedad != null ? idsociedad.hashCode() : 0);
        hash += (idprogAud != null ? idprogAud.hashCode() : 0);
        hash += (idprocesos != null ? idprocesos.hashCode() : 0);
        hash += (subproceso != null ? subproceso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgproccAuditoriaPK)) {
            return false;
        }
        SgproccAuditoriaPK other = (SgproccAuditoriaPK) object;
        if ((this.idsociedad == null && other.idsociedad != null) || (this.idsociedad != null && !this.idsociedad.equals(other.idsociedad))) {
            return false;
        }
        if ((this.idprogAud == null && other.idprogAud != null) || (this.idprogAud != null && !this.idprogAud.equals(other.idprogAud))) {
            return false;
        }
        if ((this.idprocesos == null && other.idprocesos != null) || (this.idprocesos != null && !this.idprocesos.equals(other.idprocesos))) {
            return false;
        }
        if ((this.subproceso == null && other.subproceso != null) || (this.subproceso != null && !this.subproceso.equals(other.subproceso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgproccAuditoriaPK[ idsociedad=" + idsociedad + ", idprogAud=" + idprogAud + ", idprocesos=" + idprocesos + ", subproceso=" + subproceso + " ]";
    }
    
}
