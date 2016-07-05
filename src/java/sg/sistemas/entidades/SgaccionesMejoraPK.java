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
public class SgaccionesMejoraPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDSOCIEDAD")
    private String idsociedad;
    @Basic(optional = false)
    @Column(name = "NOACC")
    private String noacc;

    public SgaccionesMejoraPK() {
    }

    public SgaccionesMejoraPK(String idsociedad, String noacc) {
        this.idsociedad = idsociedad;
        this.noacc = noacc;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsociedad != null ? idsociedad.hashCode() : 0);
        hash += (noacc != null ? noacc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgaccionesMejoraPK)) {
            return false;
        }
        SgaccionesMejoraPK other = (SgaccionesMejoraPK) object;
        if ((this.idsociedad == null && other.idsociedad != null) || (this.idsociedad != null && !this.idsociedad.equals(other.idsociedad))) {
            return false;
        }
        if ((this.noacc == null && other.noacc != null) || (this.noacc != null && !this.noacc.equals(other.noacc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgaccionesMejoraPK[ idsociedad=" + idsociedad + ", noacc=" + noacc + " ]";
    }
    
}
