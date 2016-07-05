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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
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
@Table(name = "sgcab_encuesta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgcabEncuesta.findAll", query = "SELECT s FROM SgcabEncuesta s"),
    @NamedQuery(name = "SgcabEncuesta.findByIdsociedad", query = "SELECT s FROM SgcabEncuesta s WHERE s.sgcabEncuestaPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgcabEncuesta.findByNoenc", query = "SELECT s FROM SgcabEncuesta s WHERE s.sgcabEncuestaPK.noenc = :noenc"),
    @NamedQuery(name = "SgcabEncuesta.findByDescripcion", query = "SELECT s FROM SgcabEncuesta s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "SgcabEncuesta.findByModalidad", query = "SELECT s FROM SgcabEncuesta s WHERE s.modalidad = :modalidad"),
    @NamedQuery(name = "SgcabEncuesta.findByFechaInicio", query = "SELECT s FROM SgcabEncuesta s WHERE s.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "SgcabEncuesta.findByFechaFin", query = "SELECT s FROM SgcabEncuesta s WHERE s.fechaFin = :fechaFin"),
    @NamedQuery(name = "SgcabEncuesta.findByEstado", query = "SELECT s FROM SgcabEncuesta s WHERE s.estado = :estado")})
public class SgcabEncuesta implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgcabEncuestaPK sgcabEncuestaPK;
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
    @JoinTable(name = "sgpempleado_encuesta", joinColumns = {
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD"),
        @JoinColumn(name = "NOENC", referencedColumnName = "NOENC")}, inverseJoinColumns = {
        @JoinColumn(name = "COD_USUARIO", referencedColumnName = "COD_USUARIO")})
    @ManyToMany
    private List<SgUsuario> sgUsuarioList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "sgcabEncuesta")
    private SgencuestaDetalle sgencuestaDetalle;
    @JoinColumn(name = "IDENCUESTA", referencedColumnName = "IDENCUESTA")
    @ManyToOne
    private SgpCabEncuesta idencuesta;
    @JoinColumn(name = "COD_USUARIO", referencedColumnName = "COD_USUARIO")
    @ManyToOne
    private SgUsuario codUsuario;

    public SgcabEncuesta() {
    }

    public SgcabEncuesta(SgcabEncuestaPK sgcabEncuestaPK) {
        this.sgcabEncuestaPK = sgcabEncuestaPK;
    }

    public SgcabEncuesta(String idsociedad, String noenc) {
        this.sgcabEncuestaPK = new SgcabEncuestaPK(idsociedad, noenc);
    }

    public SgcabEncuestaPK getSgcabEncuestaPK() {
        return sgcabEncuestaPK;
    }

    public void setSgcabEncuestaPK(SgcabEncuestaPK sgcabEncuestaPK) {
        this.sgcabEncuestaPK = sgcabEncuestaPK;
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

    @XmlTransient
    public List<SgUsuario> getSgUsuarioList() {
        return sgUsuarioList;
    }

    public void setSgUsuarioList(List<SgUsuario> sgUsuarioList) {
        this.sgUsuarioList = sgUsuarioList;
    }

    public SgencuestaDetalle getSgencuestaDetalle() {
        return sgencuestaDetalle;
    }

    public void setSgencuestaDetalle(SgencuestaDetalle sgencuestaDetalle) {
        this.sgencuestaDetalle = sgencuestaDetalle;
    }

    public SgpCabEncuesta getIdencuesta() {
        return idencuesta;
    }

    public void setIdencuesta(SgpCabEncuesta idencuesta) {
        this.idencuesta = idencuesta;
    }

    public SgUsuario getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(SgUsuario codUsuario) {
        this.codUsuario = codUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgcabEncuestaPK != null ? sgcabEncuestaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgcabEncuesta)) {
            return false;
        }
        SgcabEncuesta other = (SgcabEncuesta) object;
        if ((this.sgcabEncuestaPK == null && other.sgcabEncuestaPK != null) || (this.sgcabEncuestaPK != null && !this.sgcabEncuestaPK.equals(other.sgcabEncuestaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgcabEncuesta[ sgcabEncuestaPK=" + sgcabEncuestaPK + " ]";
    }
    
}
