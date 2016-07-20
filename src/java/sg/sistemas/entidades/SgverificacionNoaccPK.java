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
public class SgverificacionNoaccPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDSOCIEDAD")
    private String idsociedad;
    @Basic(optional = false)
    @Column(name = "NOACC")
    private String noacc;
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "COD_USUARIO")
    private String codUsuario;

    public SgverificacionNoaccPK() {
    }

    public SgverificacionNoaccPK(String idsociedad, String noacc, Date fecha, String codUsuario) {
        this.idsociedad = idsociedad;
        this.noacc = noacc;
        this.fecha = fecha;
        this.codUsuario = codUsuario;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(String codUsuario) {
        this.codUsuario = codUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsociedad != null ? idsociedad.hashCode() : 0);
        hash += (noacc != null ? noacc.hashCode() : 0);
        hash += (fecha != null ? fecha.hashCode() : 0);
        hash += (codUsuario != null ? codUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgverificacionNoaccPK)) {
            return false;
        }
        SgverificacionNoaccPK other = (SgverificacionNoaccPK) object;
        if ((this.idsociedad == null && other.idsociedad != null) || (this.idsociedad != null && !this.idsociedad.equals(other.idsociedad))) {
            return false;
        }
        if ((this.noacc == null && other.noacc != null) || (this.noacc != null && !this.noacc.equals(other.noacc))) {
            return false;
        }
        if ((this.fecha == null && other.fecha != null) || (this.fecha != null && !this.fecha.equals(other.fecha))) {
            return false;
        }
        if ((this.codUsuario == null && other.codUsuario != null) || (this.codUsuario != null && !this.codUsuario.equals(other.codUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgverificacionNoaccPK[ idsociedad=" + idsociedad + ", noacc=" + noacc + ", fecha=" + fecha + ", codUsuario=" + codUsuario + " ]";
    }
    
}
