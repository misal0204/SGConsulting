/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "sgp_cab_encuesta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgpCabEncuesta.findAll", query = "SELECT s FROM SgpCabEncuesta s"),
    @NamedQuery(name = "SgpCabEncuesta.findByIdencuesta", query = "SELECT s FROM SgpCabEncuesta s WHERE s.idencuesta = :idencuesta"),
    @NamedQuery(name = "SgpCabEncuesta.findByDescripcion", query = "SELECT s FROM SgpCabEncuesta s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "SgpCabEncuesta.findByModalidad", query = "SELECT s FROM SgpCabEncuesta s WHERE s.modalidad = :modalidad"),
    @NamedQuery(name = "SgpCabEncuesta.findByFechaInicio", query = "SELECT s FROM SgpCabEncuesta s WHERE s.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "SgpCabEncuesta.findByFechaFin", query = "SELECT s FROM SgpCabEncuesta s WHERE s.fechaFin = :fechaFin"),
    @NamedQuery(name = "SgpCabEncuesta.findByEstado", query = "SELECT s FROM SgpCabEncuesta s WHERE s.estado = :estado")})
public class SgpCabEncuesta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDENCUESTA")
    private String idencuesta;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "MODALIDAD")
    private String modalidad;
    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Column(name = "FECHA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "COD_USUARIO", referencedColumnName = "COD_USUARIO")
    @ManyToOne
    private Sgusuario codUsuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgpCabEncuesta")
    private List<SgpEncuestaDetalle> sgpEncuestaDetalleList;
    @OneToMany(mappedBy = "idencuesta")
    private List<SgcabEncuesta> sgcabEncuestaList;

    public SgpCabEncuesta() {
    }

    public SgpCabEncuesta(String idencuesta) {
        this.idencuesta = idencuesta;
    }

    public String getIdencuesta() {
        return idencuesta;
    }

    public void setIdencuesta(String idencuesta) {
        this.idencuesta = idencuesta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Sgusuario getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(Sgusuario codUsuario) {
        this.codUsuario = codUsuario;
    }

    @XmlTransient
    public List<SgpEncuestaDetalle> getSgpEncuestaDetalleList() {
        return sgpEncuestaDetalleList;
    }

    public void setSgpEncuestaDetalleList(List<SgpEncuestaDetalle> sgpEncuestaDetalleList) {
        this.sgpEncuestaDetalleList = sgpEncuestaDetalleList;
    }

    @XmlTransient
    public List<SgcabEncuesta> getSgcabEncuestaList() {
        return sgcabEncuestaList;
    }

    public void setSgcabEncuestaList(List<SgcabEncuesta> sgcabEncuestaList) {
        this.sgcabEncuestaList = sgcabEncuestaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idencuesta != null ? idencuesta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgpCabEncuesta)) {
            return false;
        }
        SgpCabEncuesta other = (SgpCabEncuesta) object;
        if ((this.idencuesta == null && other.idencuesta != null) || (this.idencuesta != null && !this.idencuesta.equals(other.idencuesta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgpCabEncuesta[ idencuesta=" + idencuesta + " ]";
    }
    
}
