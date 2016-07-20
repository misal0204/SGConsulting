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
public class SganexoNoaccPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDSOCIEDAD")
    private String idsociedad;
    @Basic(optional = false)
    @Column(name = "NOACC")
    private String noacc;
    @Basic(optional = false)
    @Column(name = "IDPROCESOS")
    private String idprocesos;
    @Basic(optional = false)
    @Column(name = "SUBPROCESO")
    private String subproceso;
    @Basic(optional = false)
    @Column(name = "FILE_NAME")
    private String fileName;

    public SganexoNoaccPK() {
    }

    public SganexoNoaccPK(String idsociedad, String noacc, String idprocesos, String subproceso, String fileName) {
        this.idsociedad = idsociedad;
        this.noacc = noacc;
        this.idprocesos = idprocesos;
        this.subproceso = subproceso;
        this.fileName = fileName;
    }

    public String getIdsociedad() {
        return idsociedad;
    }

    public void setIdsociedad(String idsociedad) {
        this.idsociedad = idsociedad;
    }

    public String getNoacc() {
        return noacc;
    }

    public void setNoacc(String noacc) {
        this.noacc = noacc;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsociedad != null ? idsociedad.hashCode() : 0);
        hash += (noacc != null ? noacc.hashCode() : 0);
        hash += (idprocesos != null ? idprocesos.hashCode() : 0);
        hash += (subproceso != null ? subproceso.hashCode() : 0);
        hash += (fileName != null ? fileName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SganexoNoaccPK)) {
            return false;
        }
        SganexoNoaccPK other = (SganexoNoaccPK) object;
        if ((this.idsociedad == null && other.idsociedad != null) || (this.idsociedad != null && !this.idsociedad.equals(other.idsociedad))) {
            return false;
        }
        if ((this.noacc == null && other.noacc != null) || (this.noacc != null && !this.noacc.equals(other.noacc))) {
            return false;
        }
        if ((this.idprocesos == null && other.idprocesos != null) || (this.idprocesos != null && !this.idprocesos.equals(other.idprocesos))) {
            return false;
        }
        if ((this.subproceso == null && other.subproceso != null) || (this.subproceso != null && !this.subproceso.equals(other.subproceso))) {
            return false;
        }
        if ((this.fileName == null && other.fileName != null) || (this.fileName != null && !this.fileName.equals(other.fileName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SganexoNoaccPK[ idsociedad=" + idsociedad + ", noacc=" + noacc + ", idprocesos=" + idprocesos + ", subproceso=" + subproceso + ", fileName=" + fileName + " ]";
    }
    
}
