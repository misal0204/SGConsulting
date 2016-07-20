/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgrevision_direccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgrevisionDireccion.findAll", query = "SELECT s FROM SgrevisionDireccion s"),
    @NamedQuery(name = "SgrevisionDireccion.findByIdsociedad", query = "SELECT s FROM SgrevisionDireccion s WHERE s.sgrevisionDireccionPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgrevisionDireccion.findByIdplanDireccion", query = "SELECT s FROM SgrevisionDireccion s WHERE s.sgrevisionDireccionPK.idplanDireccion = :idplanDireccion"),
    @NamedQuery(name = "SgrevisionDireccion.findByIdtipoPdireccion", query = "SELECT s FROM SgrevisionDireccion s WHERE s.sgrevisionDireccionPK.idtipoPdireccion = :idtipoPdireccion"),
    @NamedQuery(name = "SgrevisionDireccion.findByTituloPlan", query = "SELECT s FROM SgrevisionDireccion s WHERE s.tituloPlan = :tituloPlan"),
    @NamedQuery(name = "SgrevisionDireccion.findByDescripcion", query = "SELECT s FROM SgrevisionDireccion s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "SgrevisionDireccion.findByFechaCreacion", query = "SELECT s FROM SgrevisionDireccion s WHERE s.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "SgrevisionDireccion.findByObservaciones", query = "SELECT s FROM SgrevisionDireccion s WHERE s.observaciones = :observaciones"),
    @NamedQuery(name = "SgrevisionDireccion.findByEstado", query = "SELECT s FROM SgrevisionDireccion s WHERE s.estado = :estado")})
public class SgrevisionDireccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgrevisionDireccionPK sgrevisionDireccionPK;
    @Column(name = "TITULO_PLAN")
    private String tituloPlan;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @Column(name = "ESTADO")
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgrevisionDireccion")
    private List<SgdetalleRdireccion> sgdetalleRdireccionList;
    @JoinColumn(name = "IDTIPO_PDIRECCION", referencedColumnName = "IDTIPO_PDIRECCION", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SgtipoplanDireccion sgtipoplanDireccion;
    @JoinColumn(name = "COD_USUARIO", referencedColumnName = "COD_USUARIO")
    @ManyToOne
    private Sgusuario codUsuario;

    public SgrevisionDireccion() {
    }

    public SgrevisionDireccion(SgrevisionDireccionPK sgrevisionDireccionPK) {
        this.sgrevisionDireccionPK = sgrevisionDireccionPK;
    }

    public SgrevisionDireccion(String idsociedad, String idplanDireccion, String idtipoPdireccion) {
        this.sgrevisionDireccionPK = new SgrevisionDireccionPK(idsociedad, idplanDireccion, idtipoPdireccion);
    }

    public SgrevisionDireccionPK getSgrevisionDireccionPK() {
        return sgrevisionDireccionPK;
    }

    public void setSgrevisionDireccionPK(SgrevisionDireccionPK sgrevisionDireccionPK) {
        this.sgrevisionDireccionPK = sgrevisionDireccionPK;
    }

    public String getTituloPlan() {
        return tituloPlan;
    }

    public void setTituloPlan(String tituloPlan) {
        this.tituloPlan = tituloPlan;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<SgdetalleRdireccion> getSgdetalleRdireccionList() {
        return sgdetalleRdireccionList;
    }

    public void setSgdetalleRdireccionList(List<SgdetalleRdireccion> sgdetalleRdireccionList) {
        this.sgdetalleRdireccionList = sgdetalleRdireccionList;
    }

    public SgtipoplanDireccion getSgtipoplanDireccion() {
        return sgtipoplanDireccion;
    }

    public void setSgtipoplanDireccion(SgtipoplanDireccion sgtipoplanDireccion) {
        this.sgtipoplanDireccion = sgtipoplanDireccion;
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
        hash += (sgrevisionDireccionPK != null ? sgrevisionDireccionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgrevisionDireccion)) {
            return false;
        }
        SgrevisionDireccion other = (SgrevisionDireccion) object;
        if ((this.sgrevisionDireccionPK == null && other.sgrevisionDireccionPK != null) || (this.sgrevisionDireccionPK != null && !this.sgrevisionDireccionPK.equals(other.sgrevisionDireccionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgrevisionDireccion[ sgrevisionDireccionPK=" + sgrevisionDireccionPK + " ]";
    }
    
}
