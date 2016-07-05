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
@Table(name = "sgplan_forma")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgplanForma.findAll", query = "SELECT s FROM SgplanForma s"),
    @NamedQuery(name = "SgplanForma.findByIdsociedad", query = "SELECT s FROM SgplanForma s WHERE s.sgplanFormaPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgplanForma.findByIdplanForma", query = "SELECT s FROM SgplanForma s WHERE s.sgplanFormaPK.idplanForma = :idplanForma"),
    @NamedQuery(name = "SgplanForma.findByTitulo", query = "SELECT s FROM SgplanForma s WHERE s.titulo = :titulo"),
    @NamedQuery(name = "SgplanForma.findByDescripcion", query = "SELECT s FROM SgplanForma s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "SgplanForma.findByObjetivo", query = "SELECT s FROM SgplanForma s WHERE s.objetivo = :objetivo"),
    @NamedQuery(name = "SgplanForma.findByFechaInicio", query = "SELECT s FROM SgplanForma s WHERE s.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "SgplanForma.findByFechaFin", query = "SELECT s FROM SgplanForma s WHERE s.fechaFin = :fechaFin"),
    @NamedQuery(name = "SgplanForma.findByFechaCierre", query = "SELECT s FROM SgplanForma s WHERE s.fechaCierre = :fechaCierre"),
    @NamedQuery(name = "SgplanForma.findByEstado", query = "SELECT s FROM SgplanForma s WHERE s.estado = :estado")})
public class SgplanForma implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgplanFormaPK sgplanFormaPK;
    @Column(name = "TITULO")
    private String titulo;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "OBJETIVO")
    private String objetivo;
    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Column(name = "FECHA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Column(name = "FECHA_CIERRE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCierre;
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "IDTIPO_PLAN", referencedColumnName = "IDTIPO_PLAN")
    @ManyToOne
    private SgtipoPlan idtipoPlan;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgplanForma")
    private List<SgplanPrograma> sgplanProgramaList;

    public SgplanForma() {
    }

    public SgplanForma(SgplanFormaPK sgplanFormaPK) {
        this.sgplanFormaPK = sgplanFormaPK;
    }

    public SgplanForma(String idsociedad, String idplanForma) {
        this.sgplanFormaPK = new SgplanFormaPK(idsociedad, idplanForma);
    }

    public SgplanFormaPK getSgplanFormaPK() {
        return sgplanFormaPK;
    }

    public void setSgplanFormaPK(SgplanFormaPK sgplanFormaPK) {
        this.sgplanFormaPK = sgplanFormaPK;
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

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
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

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public SgtipoPlan getIdtipoPlan() {
        return idtipoPlan;
    }

    public void setIdtipoPlan(SgtipoPlan idtipoPlan) {
        this.idtipoPlan = idtipoPlan;
    }

    @XmlTransient
    public List<SgplanPrograma> getSgplanProgramaList() {
        return sgplanProgramaList;
    }

    public void setSgplanProgramaList(List<SgplanPrograma> sgplanProgramaList) {
        this.sgplanProgramaList = sgplanProgramaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgplanFormaPK != null ? sgplanFormaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgplanForma)) {
            return false;
        }
        SgplanForma other = (SgplanForma) object;
        if ((this.sgplanFormaPK == null && other.sgplanFormaPK != null) || (this.sgplanFormaPK != null && !this.sgplanFormaPK.equals(other.sgplanFormaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgplanForma[ sgplanFormaPK=" + sgplanFormaPK + " ]";
    }
    
}
