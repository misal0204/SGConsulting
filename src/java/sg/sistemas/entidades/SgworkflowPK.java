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
public class SgworkflowPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDSOCIEDAD")
    private String idsociedad;
    @Basic(optional = false)
    @Column(name = "IDPROCESOS")
    private String idprocesos;
    @Basic(optional = false)
    @Column(name = "SUBPROCESO")
    private String subproceso;

    public SgworkflowPK() {
    }

    public SgworkflowPK(String idsociedad, String idprocesos, String subproceso) {
        this.idsociedad = idsociedad;
        this.idprocesos = idprocesos;
        this.subproceso = subproceso;
    }

    public String getIdsociedad() {
        return idsociedad;
    }

    public void setIdsociedad(String idsociedad) {
        this.idsociedad = idsociedad;
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
        hash += (idprocesos != null ? idprocesos.hashCode() : 0);
        hash += (subproceso != null ? subproceso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgworkflowPK)) {
            return false;
        }
        SgworkflowPK other = (SgworkflowPK) object;
        if ((this.idsociedad == null && other.idsociedad != null) || (this.idsociedad != null && !this.idsociedad.equals(other.idsociedad))) {
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
        return "sg.sistemas.entidades.SgworkflowPK[ idsociedad=" + idsociedad + ", idprocesos=" + idprocesos + ", subproceso=" + subproceso + " ]";
    }
    
}
