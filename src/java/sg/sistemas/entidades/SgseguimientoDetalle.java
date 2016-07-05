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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgseguimiento_detalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgseguimientoDetalle.findAll", query = "SELECT s FROM SgseguimientoDetalle s"),
    @NamedQuery(name = "SgseguimientoDetalle.findByIdsociedad", query = "SELECT s FROM SgseguimientoDetalle s WHERE s.sgseguimientoDetallePK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgseguimientoDetalle.findByNonc", query = "SELECT s FROM SgseguimientoDetalle s WHERE s.sgseguimientoDetallePK.nonc = :nonc"),
    @NamedQuery(name = "SgseguimientoDetalle.findByFecha", query = "SELECT s FROM SgseguimientoDetalle s WHERE s.sgseguimientoDetallePK.fecha = :fecha")})
public class SgseguimientoDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgseguimientoDetallePK sgseguimientoDetallePK;
    @Basic(optional = false)
    @Lob
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinColumn(name = "COD_USUARIO", referencedColumnName = "COD_USUARIO")
    @ManyToOne
    private SgUsuario codUsuario;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "NONC", referencedColumnName = "NONC", insertable = false, updatable = false),
        @JoinColumn(name = "FECHA", referencedColumnName = "FECHA", insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private Sgsigue sgsigue;

    public SgseguimientoDetalle() {
    }

    public SgseguimientoDetalle(SgseguimientoDetallePK sgseguimientoDetallePK) {
        this.sgseguimientoDetallePK = sgseguimientoDetallePK;
    }

    public SgseguimientoDetalle(SgseguimientoDetallePK sgseguimientoDetallePK, String descripcion) {
        this.sgseguimientoDetallePK = sgseguimientoDetallePK;
        this.descripcion = descripcion;
    }

    public SgseguimientoDetalle(String idsociedad, String nonc, Date fecha) {
        this.sgseguimientoDetallePK = new SgseguimientoDetallePK(idsociedad, nonc, fecha);
    }

    public SgseguimientoDetallePK getSgseguimientoDetallePK() {
        return sgseguimientoDetallePK;
    }

    public void setSgseguimientoDetallePK(SgseguimientoDetallePK sgseguimientoDetallePK) {
        this.sgseguimientoDetallePK = sgseguimientoDetallePK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public SgUsuario getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(SgUsuario codUsuario) {
        this.codUsuario = codUsuario;
    }

    public Sgsigue getSgsigue() {
        return sgsigue;
    }

    public void setSgsigue(Sgsigue sgsigue) {
        this.sgsigue = sgsigue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgseguimientoDetallePK != null ? sgseguimientoDetallePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgseguimientoDetalle)) {
            return false;
        }
        SgseguimientoDetalle other = (SgseguimientoDetalle) object;
        if ((this.sgseguimientoDetallePK == null && other.sgseguimientoDetallePK != null) || (this.sgseguimientoDetallePK != null && !this.sgseguimientoDetallePK.equals(other.sgseguimientoDetallePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgseguimientoDetalle[ sgseguimientoDetallePK=" + sgseguimientoDetallePK + " ]";
    }
    
}
