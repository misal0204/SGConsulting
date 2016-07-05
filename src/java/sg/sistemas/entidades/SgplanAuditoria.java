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
@Table(name = "sgplan_auditoria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgplanAuditoria.findAll", query = "SELECT s FROM SgplanAuditoria s"),
    @NamedQuery(name = "SgplanAuditoria.findByIdsociedad", query = "SELECT s FROM SgplanAuditoria s WHERE s.sgplanAuditoriaPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgplanAuditoria.findByIdplanAud", query = "SELECT s FROM SgplanAuditoria s WHERE s.sgplanAuditoriaPK.idplanAud = :idplanAud"),
    @NamedQuery(name = "SgplanAuditoria.findByIdtAuditoria", query = "SELECT s FROM SgplanAuditoria s WHERE s.idtAuditoria = :idtAuditoria"),
    @NamedQuery(name = "SgplanAuditoria.findByDescripcion", query = "SELECT s FROM SgplanAuditoria s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "SgplanAuditoria.findByFechaInicio", query = "SELECT s FROM SgplanAuditoria s WHERE s.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "SgplanAuditoria.findByFechaFin", query = "SELECT s FROM SgplanAuditoria s WHERE s.fechaFin = :fechaFin"),
    @NamedQuery(name = "SgplanAuditoria.findByEstado", query = "SELECT s FROM SgplanAuditoria s WHERE s.estado = :estado")})
public class SgplanAuditoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgplanAuditoriaPK sgplanAuditoriaPK;
    @Basic(optional = false)
    @Column(name = "IDT_AUDITORIA")
    private String idtAuditoria;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Basic(optional = false)
    @Column(name = "FECHA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Column(name = "ESTADO")
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgplanAuditoria")
    private List<SgdetalleAuditoria> sgdetalleAuditoriaList;

    public SgplanAuditoria() {
    }

    public SgplanAuditoria(SgplanAuditoriaPK sgplanAuditoriaPK) {
        this.sgplanAuditoriaPK = sgplanAuditoriaPK;
    }

    public SgplanAuditoria(SgplanAuditoriaPK sgplanAuditoriaPK, String idtAuditoria, Date fechaInicio, Date fechaFin) {
        this.sgplanAuditoriaPK = sgplanAuditoriaPK;
        this.idtAuditoria = idtAuditoria;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public SgplanAuditoria(String idsociedad, String idplanAud) {
        this.sgplanAuditoriaPK = new SgplanAuditoriaPK(idsociedad, idplanAud);
    }

    public SgplanAuditoriaPK getSgplanAuditoriaPK() {
        return sgplanAuditoriaPK;
    }

    public void setSgplanAuditoriaPK(SgplanAuditoriaPK sgplanAuditoriaPK) {
        this.sgplanAuditoriaPK = sgplanAuditoriaPK;
    }

    public String getIdtAuditoria() {
        return idtAuditoria;
    }

    public void setIdtAuditoria(String idtAuditoria) {
        this.idtAuditoria = idtAuditoria;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<SgdetalleAuditoria> getSgdetalleAuditoriaList() {
        return sgdetalleAuditoriaList;
    }

    public void setSgdetalleAuditoriaList(List<SgdetalleAuditoria> sgdetalleAuditoriaList) {
        this.sgdetalleAuditoriaList = sgdetalleAuditoriaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgplanAuditoriaPK != null ? sgplanAuditoriaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgplanAuditoria)) {
            return false;
        }
        SgplanAuditoria other = (SgplanAuditoria) object;
        if ((this.sgplanAuditoriaPK == null && other.sgplanAuditoriaPK != null) || (this.sgplanAuditoriaPK != null && !this.sgplanAuditoriaPK.equals(other.sgplanAuditoriaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgplanAuditoria[ sgplanAuditoriaPK=" + sgplanAuditoriaPK + " ]";
    }
    
}
