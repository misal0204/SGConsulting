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
public class SganexosPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDSOCIEDAD")
    private String idsociedad;
    @Basic(optional = false)
    @Column(name = "NONC")
    private String nonc;
    @Basic(optional = false)
    @Column(name = "IDPROCESOS")
    private String idprocesos;
    @Basic(optional = false)
    @Column(name = "SUBPROCESO")
    private String subproceso;
    @Basic(optional = false)
    @Column(name = "NAME_FILE")
    private String nameFile;

    public SganexosPK() {
    }

    public SganexosPK(String idsociedad, String nonc, String idprocesos, String subproceso, String nameFile) {
        this.idsociedad = idsociedad;
        this.nonc = nonc;
        this.idprocesos = idprocesos;
        this.subproceso = subproceso;
        this.nameFile = nameFile;
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

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsociedad != null ? idsociedad.hashCode() : 0);
        hash += (nonc != null ? nonc.hashCode() : 0);
        hash += (idprocesos != null ? idprocesos.hashCode() : 0);
        hash += (subproceso != null ? subproceso.hashCode() : 0);
        hash += (nameFile != null ? nameFile.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SganexosPK)) {
            return false;
        }
        SganexosPK other = (SganexosPK) object;
        if ((this.idsociedad == null && other.idsociedad != null) || (this.idsociedad != null && !this.idsociedad.equals(other.idsociedad))) {
            return false;
        }
        if ((this.nonc == null && other.nonc != null) || (this.nonc != null && !this.nonc.equals(other.nonc))) {
            return false;
        }
        if ((this.idprocesos == null && other.idprocesos != null) || (this.idprocesos != null && !this.idprocesos.equals(other.idprocesos))) {
            return false;
        }
        if ((this.subproceso == null && other.subproceso != null) || (this.subproceso != null && !this.subproceso.equals(other.subproceso))) {
            return false;
        }
        if ((this.nameFile == null && other.nameFile != null) || (this.nameFile != null && !this.nameFile.equals(other.nameFile))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SganexosPK[ idsociedad=" + idsociedad + ", nonc=" + nonc + ", idprocesos=" + idprocesos + ", subproceso=" + subproceso + ", nameFile=" + nameFile + " ]";
    }
    
}
