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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
@Table(name = "sgplan_programa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgplanPrograma.findAll", query = "SELECT s FROM SgplanPrograma s"),
    @NamedQuery(name = "SgplanPrograma.findByIdsociedad", query = "SELECT s FROM SgplanPrograma s WHERE s.sgplanProgramaPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgplanPrograma.findByIdplanProg", query = "SELECT s FROM SgplanPrograma s WHERE s.sgplanProgramaPK.idplanProg = :idplanProg"),
    @NamedQuery(name = "SgplanPrograma.findByFechaInicio", query = "SELECT s FROM SgplanPrograma s WHERE s.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "SgplanPrograma.findByFechaFin", query = "SELECT s FROM SgplanPrograma s WHERE s.fechaFin = :fechaFin"),
    @NamedQuery(name = "SgplanPrograma.findByTitulo", query = "SELECT s FROM SgplanPrograma s WHERE s.titulo = :titulo"),
    @NamedQuery(name = "SgplanPrograma.findByDescripcion", query = "SELECT s FROM SgplanPrograma s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "SgplanPrograma.findByObjetivos", query = "SELECT s FROM SgplanPrograma s WHERE s.objetivos = :objetivos"),
    @NamedQuery(name = "SgplanPrograma.findByAlcance", query = "SELECT s FROM SgplanPrograma s WHERE s.alcance = :alcance"),
    @NamedQuery(name = "SgplanPrograma.findByFrecuencia", query = "SELECT s FROM SgplanPrograma s WHERE s.frecuencia = :frecuencia"),
    @NamedQuery(name = "SgplanPrograma.findByTipoFrecuencia", query = "SELECT s FROM SgplanPrograma s WHERE s.tipoFrecuencia = :tipoFrecuencia"),
    @NamedQuery(name = "SgplanPrograma.findByEstado", query = "SELECT s FROM SgplanPrograma s WHERE s.estado = :estado")})
public class SgplanPrograma implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgplanProgramaPK sgplanProgramaPK;
    @Basic(optional = false)
    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Basic(optional = false)
    @Column(name = "FECHA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Column(name = "TITULO")
    private String titulo;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "OBJETIVOS")
    private String objetivos;
    @Column(name = "ALCANCE")
    private String alcance;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "FRECUENCIA")
    private Double frecuencia;
    @Column(name = "TIPO_FRECUENCIA")
    private String tipoFrecuencia;
    @Column(name = "ESTADO")
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgplanPrograma")
    private List<SgprogDetalle> sgprogDetalleList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgplanPrograma")
    private List<SgprogramaEmpleado> sgprogramaEmpleadoList;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "IDPLAN_FORMA", referencedColumnName = "IDPLAN_FORMA")})
    @ManyToOne(optional = false)
    private SgplanForma sgplanForma;

    public SgplanPrograma() {
    }

    public SgplanPrograma(SgplanProgramaPK sgplanProgramaPK) {
        this.sgplanProgramaPK = sgplanProgramaPK;
    }

    public SgplanPrograma(SgplanProgramaPK sgplanProgramaPK, Date fechaInicio, Date fechaFin) {
        this.sgplanProgramaPK = sgplanProgramaPK;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public SgplanPrograma(String idsociedad, String idplanProg) {
        this.sgplanProgramaPK = new SgplanProgramaPK(idsociedad, idplanProg);
    }

    public SgplanProgramaPK getSgplanProgramaPK() {
        return sgplanProgramaPK;
    }

    public void setSgplanProgramaPK(SgplanProgramaPK sgplanProgramaPK) {
        this.sgplanProgramaPK = sgplanProgramaPK;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(String objetivos) {
        this.objetivos = objetivos;
    }

    public String getAlcance() {
        return alcance;
    }

    public void setAlcance(String alcance) {
        this.alcance = alcance;
    }

    public Double getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Double frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getTipoFrecuencia() {
        return tipoFrecuencia;
    }

    public void setTipoFrecuencia(String tipoFrecuencia) {
        this.tipoFrecuencia = tipoFrecuencia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<SgprogDetalle> getSgprogDetalleList() {
        return sgprogDetalleList;
    }

    public void setSgprogDetalleList(List<SgprogDetalle> sgprogDetalleList) {
        this.sgprogDetalleList = sgprogDetalleList;
    }

    @XmlTransient
    public List<SgprogramaEmpleado> getSgprogramaEmpleadoList() {
        return sgprogramaEmpleadoList;
    }

    public void setSgprogramaEmpleadoList(List<SgprogramaEmpleado> sgprogramaEmpleadoList) {
        this.sgprogramaEmpleadoList = sgprogramaEmpleadoList;
    }

    public SgplanForma getSgplanForma() {
        return sgplanForma;
    }

    public void setSgplanForma(SgplanForma sgplanForma) {
        this.sgplanForma = sgplanForma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgplanProgramaPK != null ? sgplanProgramaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgplanPrograma)) {
            return false;
        }
        SgplanPrograma other = (SgplanPrograma) object;
        if ((this.sgplanProgramaPK == null && other.sgplanProgramaPK != null) || (this.sgplanProgramaPK != null && !this.sgplanProgramaPK.equals(other.sgplanProgramaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgplanPrograma[ sgplanProgramaPK=" + sgplanProgramaPK + " ]";
    }
    
}
