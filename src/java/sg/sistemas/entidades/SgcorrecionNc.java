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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgcorrecion_nc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgcorrecionNc.findAll", query = "SELECT s FROM SgcorrecionNc s"),
    @NamedQuery(name = "SgcorrecionNc.findByIdsociedad", query = "SELECT s FROM SgcorrecionNc s WHERE s.sgcorrecionNcPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgcorrecionNc.findByNonc", query = "SELECT s FROM SgcorrecionNc s WHERE s.sgcorrecionNcPK.nonc = :nonc"),
    @NamedQuery(name = "SgcorrecionNc.findByFechaCompromiso", query = "SELECT s FROM SgcorrecionNc s WHERE s.fechaCompromiso = :fechaCompromiso"),
    @NamedQuery(name = "SgcorrecionNc.findByEstado", query = "SELECT s FROM SgcorrecionNc s WHERE s.estado = :estado")})
public class SgcorrecionNc implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgcorrecionNcPK sgcorrecionNcPK;
    @Lob
    @Column(name = "CORRECION")
    private String correcion;
    @Column(name = "FECHA_COMPROMISO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCompromiso;
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "NONC", referencedColumnName = "NONC", insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private Sgnc sgnc;
    @JoinColumn(name = "COD_USUARIO", referencedColumnName = "COD_USUARIO")
    @ManyToOne
    private Sgusuario codUsuario;

    public SgcorrecionNc() {
    }

    public SgcorrecionNc(SgcorrecionNcPK sgcorrecionNcPK) {
        this.sgcorrecionNcPK = sgcorrecionNcPK;
    }

    public SgcorrecionNc(String idsociedad, String nonc) {
        this.sgcorrecionNcPK = new SgcorrecionNcPK(idsociedad, nonc);
    }

    public SgcorrecionNcPK getSgcorrecionNcPK() {
        return sgcorrecionNcPK;
    }

    public void setSgcorrecionNcPK(SgcorrecionNcPK sgcorrecionNcPK) {
        this.sgcorrecionNcPK = sgcorrecionNcPK;
    }

    public String getCorrecion() {
        return correcion;
    }

    public void setCorrecion(String correcion) {
        this.correcion = correcion;
    }

    public Date getFechaCompromiso() {
        return fechaCompromiso;
    }

    public void setFechaCompromiso(Date fechaCompromiso) {
        this.fechaCompromiso = fechaCompromiso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
        hash += (sgcorrecionNcPK != null ? sgcorrecionNcPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgcorrecionNc)) {
            return false;
        }
        SgcorrecionNc other = (SgcorrecionNc) object;
        if ((this.sgcorrecionNcPK == null && other.sgcorrecionNcPK != null) || (this.sgcorrecionNcPK != null && !this.sgcorrecionNcPK.equals(other.sgcorrecionNcPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgcorrecionNc[ sgcorrecionNcPK=" + sgcorrecionNcPK + " ]";
    }
    
}
