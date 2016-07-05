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
public class SgprocesoRiesgoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDSOCIEDAD")
    private String idsociedad;
    @Basic(optional = false)
    @Column(name = "IDRIESGO")
    private String idriesgo;
    @Basic(optional = false)
    @Column(name = "IDPROCESO_RIESGO")
    private String idprocesoRiesgo;
    @Basic(optional = false)
    @Column(name = "IDPROCESOS")
    private String idprocesos;

    public SgprocesoRiesgoPK() {
    }

    public SgprocesoRiesgoPK(String idsociedad, String idriesgo, String idprocesoRiesgo, String idprocesos) {
        this.idsociedad = idsociedad;
        this.idriesgo = idriesgo;
        this.idprocesoRiesgo = idprocesoRiesgo;
        this.idprocesos = idprocesos;
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

    public String getIdprocesoRiesgo() {
        return idprocesoRiesgo;
    }

    public void setIdprocesoRiesgo(String idprocesoRiesgo) {
        this.idprocesoRiesgo = idprocesoRiesgo;
    }

    public String getIdprocesos() {
        return idprocesos;
    }

    public void setIdprocesos(String idprocesos) {
        this.idprocesos = idprocesos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsociedad != null ? idsociedad.hashCode() : 0);
        hash += (idriesgo != null ? idriesgo.hashCode() : 0);
        hash += (idprocesoRiesgo != null ? idprocesoRiesgo.hashCode() : 0);
        hash += (idprocesos != null ? idprocesos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgprocesoRiesgoPK)) {
            return false;
        }
        SgprocesoRiesgoPK other = (SgprocesoRiesgoPK) object;
        if ((this.idsociedad == null && other.idsociedad != null) || (this.idsociedad != null && !this.idsociedad.equals(other.idsociedad))) {
            return false;
        }
        if ((this.idriesgo == null && other.idriesgo != null) || (this.idriesgo != null && !this.idriesgo.equals(other.idriesgo))) {
            return false;
        }
        if ((this.idprocesoRiesgo == null && other.idprocesoRiesgo != null) || (this.idprocesoRiesgo != null && !this.idprocesoRiesgo.equals(other.idprocesoRiesgo))) {
            return false;
        }
        if ((this.idprocesos == null && other.idprocesos != null) || (this.idprocesos != null && !this.idprocesos.equals(other.idprocesos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgprocesoRiesgoPK[ idsociedad=" + idsociedad + ", idriesgo=" + idriesgo + ", idprocesoRiesgo=" + idprocesoRiesgo + ", idprocesos=" + idprocesos + " ]";
    }
    
}
