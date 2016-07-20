/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgverificacion_noacc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgverificacionNoacc.findAll", query = "SELECT s FROM SgverificacionNoacc s"),
    @NamedQuery(name = "SgverificacionNoacc.findByIdsociedad", query = "SELECT s FROM SgverificacionNoacc s WHERE s.sgverificacionNoaccPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgverificacionNoacc.findByNoacc", query = "SELECT s FROM SgverificacionNoacc s WHERE s.sgverificacionNoaccPK.noacc = :noacc"),
    @NamedQuery(name = "SgverificacionNoacc.findByFecha", query = "SELECT s FROM SgverificacionNoacc s WHERE s.sgverificacionNoaccPK.fecha = :fecha"),
    @NamedQuery(name = "SgverificacionNoacc.findByCodUsuario", query = "SELECT s FROM SgverificacionNoacc s WHERE s.sgverificacionNoaccPK.codUsuario = :codUsuario"),
    @NamedQuery(name = "SgverificacionNoacc.findByDescripcion", query = "SELECT s FROM SgverificacionNoacc s WHERE s.descripcion = :descripcion")})
public class SgverificacionNoacc implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgverificacionNoaccPK sgverificacionNoaccPK;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinColumn(name = "COD_USUARIO", referencedColumnName = "COD_USUARIO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Sgusuario sgusuario;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "NOACC", referencedColumnName = "NOACC", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private SgaccionesMejora sgaccionesMejora;

    public SgverificacionNoacc() {
    }

    public SgverificacionNoacc(SgverificacionNoaccPK sgverificacionNoaccPK) {
        this.sgverificacionNoaccPK = sgverificacionNoaccPK;
    }

    public SgverificacionNoacc(String idsociedad, String noacc, Date fecha, String codUsuario) {
        this.sgverificacionNoaccPK = new SgverificacionNoaccPK(idsociedad, noacc, fecha, codUsuario);
    }

    public SgverificacionNoaccPK getSgverificacionNoaccPK() {
        return sgverificacionNoaccPK;
    }

    public void setSgverificacionNoaccPK(SgverificacionNoaccPK sgverificacionNoaccPK) {
        this.sgverificacionNoaccPK = sgverificacionNoaccPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Sgusuario getSgusuario() {
        return sgusuario;
    }

    public void setSgusuario(Sgusuario sgusuario) {
        this.sgusuario = sgusuario;
    }

    public SgaccionesMejora getSgaccionesMejora() {
        return sgaccionesMejora;
    }

    public void setSgaccionesMejora(SgaccionesMejora sgaccionesMejora) {
        this.sgaccionesMejora = sgaccionesMejora;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgverificacionNoaccPK != null ? sgverificacionNoaccPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgverificacionNoacc)) {
            return false;
        }
        SgverificacionNoacc other = (SgverificacionNoacc) object;
        if ((this.sgverificacionNoaccPK == null && other.sgverificacionNoaccPK != null) || (this.sgverificacionNoaccPK != null && !this.sgverificacionNoaccPK.equals(other.sgverificacionNoaccPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgverificacionNoacc[ sgverificacionNoaccPK=" + sgverificacionNoaccPK + " ]";
    }
    
}
