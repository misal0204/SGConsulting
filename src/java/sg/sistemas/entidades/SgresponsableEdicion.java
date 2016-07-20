/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
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
@Table(name = "sgresponsable_edicion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgresponsableEdicion.findAll", query = "SELECT s FROM SgresponsableEdicion s"),
    @NamedQuery(name = "SgresponsableEdicion.findByIdsociedad", query = "SELECT s FROM SgresponsableEdicion s WHERE s.sgresponsableEdicionPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgresponsableEdicion.findByIddocumento", query = "SELECT s FROM SgresponsableEdicion s WHERE s.sgresponsableEdicionPK.iddocumento = :iddocumento"),
    @NamedQuery(name = "SgresponsableEdicion.findByIdtipoDocumento", query = "SELECT s FROM SgresponsableEdicion s WHERE s.sgresponsableEdicionPK.idtipoDocumento = :idtipoDocumento"),
    @NamedQuery(name = "SgresponsableEdicion.findByCodUsuario", query = "SELECT s FROM SgresponsableEdicion s WHERE s.sgresponsableEdicionPK.codUsuario = :codUsuario"),
    @NamedQuery(name = "SgresponsableEdicion.findByTipoAlcance", query = "SELECT s FROM SgresponsableEdicion s WHERE s.tipoAlcance = :tipoAlcance")})
public class SgresponsableEdicion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgresponsableEdicionPK sgresponsableEdicionPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TIPO_ALCANCE")
    private Double tipoAlcance;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "IDDOCUMENTO", referencedColumnName = "IDDOCUMENTO", insertable = false, updatable = false),
        @JoinColumn(name = "IDTIPO_DOCUMENTO", referencedColumnName = "IDTIPO_DOCUMENTO", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private SgdetalleDocumento sgdetalleDocumento;
    @JoinColumn(name = "COD_USUARIO", referencedColumnName = "COD_USUARIO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Sgusuario sgusuario;

    public SgresponsableEdicion() {
    }

    public SgresponsableEdicion(SgresponsableEdicionPK sgresponsableEdicionPK) {
        this.sgresponsableEdicionPK = sgresponsableEdicionPK;
    }

    public SgresponsableEdicion(String idsociedad, String iddocumento, String idtipoDocumento, String codUsuario) {
        this.sgresponsableEdicionPK = new SgresponsableEdicionPK(idsociedad, iddocumento, idtipoDocumento, codUsuario);
    }

    public SgresponsableEdicionPK getSgresponsableEdicionPK() {
        return sgresponsableEdicionPK;
    }

    public void setSgresponsableEdicionPK(SgresponsableEdicionPK sgresponsableEdicionPK) {
        this.sgresponsableEdicionPK = sgresponsableEdicionPK;
    }

    public Double getTipoAlcance() {
        return tipoAlcance;
    }

    public void setTipoAlcance(Double tipoAlcance) {
        this.tipoAlcance = tipoAlcance;
    }

    public SgdetalleDocumento getSgdetalleDocumento() {
        return sgdetalleDocumento;
    }

    public void setSgdetalleDocumento(SgdetalleDocumento sgdetalleDocumento) {
        this.sgdetalleDocumento = sgdetalleDocumento;
    }

    public Sgusuario getSgusuario() {
        return sgusuario;
    }

    public void setSgusuario(Sgusuario sgusuario) {
        this.sgusuario = sgusuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgresponsableEdicionPK != null ? sgresponsableEdicionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgresponsableEdicion)) {
            return false;
        }
        SgresponsableEdicion other = (SgresponsableEdicion) object;
        if ((this.sgresponsableEdicionPK == null && other.sgresponsableEdicionPK != null) || (this.sgresponsableEdicionPK != null && !this.sgresponsableEdicionPK.equals(other.sgresponsableEdicionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgresponsableEdicion[ sgresponsableEdicionPK=" + sgresponsableEdicionPK + " ]";
    }
    
}
