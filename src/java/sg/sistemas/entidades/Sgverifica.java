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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgverifica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sgverifica.findAll", query = "SELECT s FROM Sgverifica s"),
    @NamedQuery(name = "Sgverifica.findByIdsociedad", query = "SELECT s FROM Sgverifica s WHERE s.sgverificaPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "Sgverifica.findByNonc", query = "SELECT s FROM Sgverifica s WHERE s.sgverificaPK.nonc = :nonc"),
    @NamedQuery(name = "Sgverifica.findByFechaEstipulada", query = "SELECT s FROM Sgverifica s WHERE s.sgverificaPK.fechaEstipulada = :fechaEstipulada"),
    @NamedQuery(name = "Sgverifica.findByFechaRealizada", query = "SELECT s FROM Sgverifica s WHERE s.fechaRealizada = :fechaRealizada")})
public class Sgverifica implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgverificaPK sgverificaPK;
    @Basic(optional = false)
    @Lob
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "FECHA_REALIZADA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRealizada;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "NONC", referencedColumnName = "NONC", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Sgnc sgnc;
    @JoinColumn(name = "COD_USUARIO", referencedColumnName = "COD_USUARIO")
    @ManyToOne
    private Sgusuario codUsuario;

    public Sgverifica() {
    }

    public Sgverifica(SgverificaPK sgverificaPK) {
        this.sgverificaPK = sgverificaPK;
    }

    public Sgverifica(SgverificaPK sgverificaPK, String descripcion) {
        this.sgverificaPK = sgverificaPK;
        this.descripcion = descripcion;
    }

    public Sgverifica(String idsociedad, String nonc, Date fechaEstipulada) {
        this.sgverificaPK = new SgverificaPK(idsociedad, nonc, fechaEstipulada);
    }

    public SgverificaPK getSgverificaPK() {
        return sgverificaPK;
    }

    public void setSgverificaPK(SgverificaPK sgverificaPK) {
        this.sgverificaPK = sgverificaPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaRealizada() {
        return fechaRealizada;
    }

    public void setFechaRealizada(Date fechaRealizada) {
        this.fechaRealizada = fechaRealizada;
    }

    public Sgnc getSgnc() {
        return sgnc;
    }

    public void setSgnc(Sgnc sgnc) {
        this.sgnc = sgnc;
    }

    public Sgusuario getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(Sgusuario codUsuario) {
        this.codUsuario = codUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgverificaPK != null ? sgverificaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sgverifica)) {
            return false;
        }
        Sgverifica other = (Sgverifica) object;
        if ((this.sgverificaPK == null && other.sgverificaPK != null) || (this.sgverificaPK != null && !this.sgverificaPK.equals(other.sgverificaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.Sgverifica[ sgverificaPK=" + sgverificaPK + " ]";
    }
    
}
