/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Misael
 */
@Embeddable
public class SgverificaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDSOCIEDAD")
    private String idsociedad;
    @Basic(optional = false)
    @Column(name = "NONC")
    private String nonc;
    @Basic(optional = false)
    @Column(name = "FECHA_ESTIPULADA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstipulada;

    public SgverificaPK() {
    }

    public SgverificaPK(String idsociedad, String nonc, Date fechaEstipulada) {
        this.idsociedad = idsociedad;
        this.nonc = nonc;
        this.fechaEstipulada = fechaEstipulada;
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

    public Date getFechaEstipulada() {
        return fechaEstipulada;
    }

    public void setFechaEstipulada(Date fechaEstipulada) {
        this.fechaEstipulada = fechaEstipulada;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsociedad != null ? idsociedad.hashCode() : 0);
        hash += (nonc != null ? nonc.hashCode() : 0);
        hash += (fechaEstipulada != null ? fechaEstipulada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgverificaPK)) {
            return false;
        }
        SgverificaPK other = (SgverificaPK) object;
        if ((this.idsociedad == null && other.idsociedad != null) || (this.idsociedad != null && !this.idsociedad.equals(other.idsociedad))) {
            return false;
        }
        if ((this.nonc == null && other.nonc != null) || (this.nonc != null && !this.nonc.equals(other.nonc))) {
            return false;
        }
        if ((this.fechaEstipulada == null && other.fechaEstipulada != null) || (this.fechaEstipulada != null && !this.fechaEstipulada.equals(other.fechaEstipulada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgverificaPK[ idsociedad=" + idsociedad + ", nonc=" + nonc + ", fechaEstipulada=" + fechaEstipulada + " ]";
    }
    
}
