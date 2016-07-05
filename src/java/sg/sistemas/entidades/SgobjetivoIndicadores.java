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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "sgobjetivo_indicadores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgobjetivoIndicadores.findAll", query = "SELECT s FROM SgobjetivoIndicadores s"),
    @NamedQuery(name = "SgobjetivoIndicadores.findByIdsociedad", query = "SELECT s FROM SgobjetivoIndicadores s WHERE s.sgobjetivoIndicadoresPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgobjetivoIndicadores.findByIdobjIndicador", query = "SELECT s FROM SgobjetivoIndicadores s WHERE s.sgobjetivoIndicadoresPK.idobjIndicador = :idobjIndicador"),
    @NamedQuery(name = "SgobjetivoIndicadores.findByDescripcion", query = "SELECT s FROM SgobjetivoIndicadores s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "SgobjetivoIndicadores.findByFechaInicio", query = "SELECT s FROM SgobjetivoIndicadores s WHERE s.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "SgobjetivoIndicadores.findByFechaFin", query = "SELECT s FROM SgobjetivoIndicadores s WHERE s.fechaFin = :fechaFin"),
    @NamedQuery(name = "SgobjetivoIndicadores.findByPonderacion", query = "SELECT s FROM SgobjetivoIndicadores s WHERE s.ponderacion = :ponderacion")})
public class SgobjetivoIndicadores implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgobjetivoIndicadoresPK sgobjetivoIndicadoresPK;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Column(name = "FECHA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PONDERACION")
    private Double ponderacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgobjetivoIndicadores")
    private List<SgdetalleObjInd> sgdetalleObjIndList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "sgobjetivoIndicadores")
    private SgindicadorProceso sgindicadorProceso;

    public SgobjetivoIndicadores() {
    }

    public SgobjetivoIndicadores(SgobjetivoIndicadoresPK sgobjetivoIndicadoresPK) {
        this.sgobjetivoIndicadoresPK = sgobjetivoIndicadoresPK;
    }

    public SgobjetivoIndicadores(String idsociedad, String idobjIndicador) {
        this.sgobjetivoIndicadoresPK = new SgobjetivoIndicadoresPK(idsociedad, idobjIndicador);
    }

    public SgobjetivoIndicadoresPK getSgobjetivoIndicadoresPK() {
        return sgobjetivoIndicadoresPK;
    }

    public void setSgobjetivoIndicadoresPK(SgobjetivoIndicadoresPK sgobjetivoIndicadoresPK) {
        this.sgobjetivoIndicadoresPK = sgobjetivoIndicadoresPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public Double getPonderacion() {
        return ponderacion;
    }

    public void setPonderacion(Double ponderacion) {
        this.ponderacion = ponderacion;
    }

    @XmlTransient
    public List<SgdetalleObjInd> getSgdetalleObjIndList() {
        return sgdetalleObjIndList;
    }

    public void setSgdetalleObjIndList(List<SgdetalleObjInd> sgdetalleObjIndList) {
        this.sgdetalleObjIndList = sgdetalleObjIndList;
    }

    public SgindicadorProceso getSgindicadorProceso() {
        return sgindicadorProceso;
    }

    public void setSgindicadorProceso(SgindicadorProceso sgindicadorProceso) {
        this.sgindicadorProceso = sgindicadorProceso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgobjetivoIndicadoresPK != null ? sgobjetivoIndicadoresPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgobjetivoIndicadores)) {
            return false;
        }
        SgobjetivoIndicadores other = (SgobjetivoIndicadores) object;
        if ((this.sgobjetivoIndicadoresPK == null && other.sgobjetivoIndicadoresPK != null) || (this.sgobjetivoIndicadoresPK != null && !this.sgobjetivoIndicadoresPK.equals(other.sgobjetivoIndicadoresPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgobjetivoIndicadores[ sgobjetivoIndicadoresPK=" + sgobjetivoIndicadoresPK + " ]";
    }
    
}
