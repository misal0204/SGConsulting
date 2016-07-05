/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import java.util.Date;
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
@Table(name = "sgprocc_auditoria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgproccAuditoria.findAll", query = "SELECT s FROM SgproccAuditoria s"),
    @NamedQuery(name = "SgproccAuditoria.findByIdsociedad", query = "SELECT s FROM SgproccAuditoria s WHERE s.sgproccAuditoriaPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgproccAuditoria.findByIdprogAud", query = "SELECT s FROM SgproccAuditoria s WHERE s.sgproccAuditoriaPK.idprogAud = :idprogAud"),
    @NamedQuery(name = "SgproccAuditoria.findByIdprocesos", query = "SELECT s FROM SgproccAuditoria s WHERE s.sgproccAuditoriaPK.idprocesos = :idprocesos"),
    @NamedQuery(name = "SgproccAuditoria.findBySubproceso", query = "SELECT s FROM SgproccAuditoria s WHERE s.sgproccAuditoriaPK.subproceso = :subproceso"),
    @NamedQuery(name = "SgproccAuditoria.findByFechaInicio", query = "SELECT s FROM SgproccAuditoria s WHERE s.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "SgproccAuditoria.findByFechaFin", query = "SELECT s FROM SgproccAuditoria s WHERE s.fechaFin = :fechaFin")})
public class SgproccAuditoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgproccAuditoriaPK sgproccAuditoriaPK;
    @Basic(optional = false)
    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Basic(optional = false)
    @Column(name = "FECHA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @JoinColumns({
        @JoinColumn(name = "IDPROCESOS", referencedColumnName = "IDPROCESOS", insertable = false, updatable = false),
        @JoinColumn(name = "SUBPROCESO", referencedColumnName = "SUBPROCESO", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private SgprocesoDetalle sgprocesoDetalle;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "IDPROG_AUD", referencedColumnName = "IDPROG_AUD", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private SgprogAuditoria sgprogAuditoria;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "sgproccAuditoria")
    private Sgauditado sgauditado;

    public SgproccAuditoria() {
    }

    public SgproccAuditoria(SgproccAuditoriaPK sgproccAuditoriaPK) {
        this.sgproccAuditoriaPK = sgproccAuditoriaPK;
    }

    public SgproccAuditoria(SgproccAuditoriaPK sgproccAuditoriaPK, Date fechaInicio, Date fechaFin) {
        this.sgproccAuditoriaPK = sgproccAuditoriaPK;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public SgproccAuditoria(String idsociedad, String idprogAud, String idprocesos, String subproceso) {
        this.sgproccAuditoriaPK = new SgproccAuditoriaPK(idsociedad, idprogAud, idprocesos, subproceso);
    }

    public SgproccAuditoriaPK getSgproccAuditoriaPK() {
        return sgproccAuditoriaPK;
    }

    public void setSgproccAuditoriaPK(SgproccAuditoriaPK sgproccAuditoriaPK) {
        this.sgproccAuditoriaPK = sgproccAuditoriaPK;
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

    public SgprocesoDetalle getSgprocesoDetalle() {
        return sgprocesoDetalle;
    }

    public void setSgprocesoDetalle(SgprocesoDetalle sgprocesoDetalle) {
        this.sgprocesoDetalle = sgprocesoDetalle;
    }

    public SgprogAuditoria getSgprogAuditoria() {
        return sgprogAuditoria;
    }

    public void setSgprogAuditoria(SgprogAuditoria sgprogAuditoria) {
        this.sgprogAuditoria = sgprogAuditoria;
    }

    public Sgauditado getSgauditado() {
        return sgauditado;
    }

    public void setSgauditado(Sgauditado sgauditado) {
        this.sgauditado = sgauditado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgproccAuditoriaPK != null ? sgproccAuditoriaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgproccAuditoria)) {
            return false;
        }
        SgproccAuditoria other = (SgproccAuditoria) object;
        if ((this.sgproccAuditoriaPK == null && other.sgproccAuditoriaPK != null) || (this.sgproccAuditoriaPK != null && !this.sgproccAuditoriaPK.equals(other.sgproccAuditoriaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgproccAuditoria[ sgproccAuditoriaPK=" + sgproccAuditoriaPK + " ]";
    }
    
}
