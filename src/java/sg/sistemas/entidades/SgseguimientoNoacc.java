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
@Table(name = "sgseguimiento_noacc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgseguimientoNoacc.findAll", query = "SELECT s FROM SgseguimientoNoacc s"),
    @NamedQuery(name = "SgseguimientoNoacc.findByIdsociedad", query = "SELECT s FROM SgseguimientoNoacc s WHERE s.sgseguimientoNoaccPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgseguimientoNoacc.findByNoacc", query = "SELECT s FROM SgseguimientoNoacc s WHERE s.sgseguimientoNoaccPK.noacc = :noacc"),
    @NamedQuery(name = "SgseguimientoNoacc.findByCodUsuario", query = "SELECT s FROM SgseguimientoNoacc s WHERE s.sgseguimientoNoaccPK.codUsuario = :codUsuario"),
    @NamedQuery(name = "SgseguimientoNoacc.findByFecha", query = "SELECT s FROM SgseguimientoNoacc s WHERE s.sgseguimientoNoaccPK.fecha = :fecha"),
    @NamedQuery(name = "SgseguimientoNoacc.findByDescripcion", query = "SELECT s FROM SgseguimientoNoacc s WHERE s.descripcion = :descripcion")})
public class SgseguimientoNoacc implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgseguimientoNoaccPK sgseguimientoNoaccPK;
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

    public SgseguimientoNoacc() {
    }

    public SgseguimientoNoacc(SgseguimientoNoaccPK sgseguimientoNoaccPK) {
        this.sgseguimientoNoaccPK = sgseguimientoNoaccPK;
    }

    public SgseguimientoNoacc(String idsociedad, String noacc, String codUsuario, Date fecha) {
        this.sgseguimientoNoaccPK = new SgseguimientoNoaccPK(idsociedad, noacc, codUsuario, fecha);
    }

    public SgseguimientoNoaccPK getSgseguimientoNoaccPK() {
        return sgseguimientoNoaccPK;
    }

    public void setSgseguimientoNoaccPK(SgseguimientoNoaccPK sgseguimientoNoaccPK) {
        this.sgseguimientoNoaccPK = sgseguimientoNoaccPK;
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
        hash += (sgseguimientoNoaccPK != null ? sgseguimientoNoaccPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgseguimientoNoacc)) {
            return false;
        }
        SgseguimientoNoacc other = (SgseguimientoNoacc) object;
        if ((this.sgseguimientoNoaccPK == null && other.sgseguimientoNoaccPK != null) || (this.sgseguimientoNoaccPK != null && !this.sgseguimientoNoaccPK.equals(other.sgseguimientoNoaccPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgseguimientoNoacc[ sgseguimientoNoaccPK=" + sgseguimientoNoaccPK + " ]";
    }
    
}
