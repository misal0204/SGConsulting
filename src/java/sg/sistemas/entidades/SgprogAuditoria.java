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
@Table(name = "sgprog_auditoria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgprogAuditoria.findAll", query = "SELECT s FROM SgprogAuditoria s"),
    @NamedQuery(name = "SgprogAuditoria.findByIdsociedad", query = "SELECT s FROM SgprogAuditoria s WHERE s.sgprogAuditoriaPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgprogAuditoria.findByIdprogAud", query = "SELECT s FROM SgprogAuditoria s WHERE s.sgprogAuditoriaPK.idprogAud = :idprogAud"),
    @NamedQuery(name = "SgprogAuditoria.findByFechaInicio", query = "SELECT s FROM SgprogAuditoria s WHERE s.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "SgprogAuditoria.findByFechaFin", query = "SELECT s FROM SgprogAuditoria s WHERE s.fechaFin = :fechaFin"),
    @NamedQuery(name = "SgprogAuditoria.findByObjetivo", query = "SELECT s FROM SgprogAuditoria s WHERE s.objetivo = :objetivo"),
    @NamedQuery(name = "SgprogAuditoria.findByAlcance", query = "SELECT s FROM SgprogAuditoria s WHERE s.alcance = :alcance"),
    @NamedQuery(name = "SgprogAuditoria.findByEstado", query = "SELECT s FROM SgprogAuditoria s WHERE s.estado = :estado"),
    @NamedQuery(name = "SgprogAuditoria.findByCodUsuario", query = "SELECT s FROM SgprogAuditoria s WHERE s.codUsuario = :codUsuario")})
public class SgprogAuditoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgprogAuditoriaPK sgprogAuditoriaPK;
    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Column(name = "FECHA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Column(name = "OBJETIVO")
    private String objetivo;
    @Column(name = "ALCANCE")
    private String alcance;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "COD_USUARIO")
    private String codUsuario;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "IDPLAN_AUD", referencedColumnName = "IDPLAN_AUD"),
        @JoinColumn(name = "IDDETALLE_AUD", referencedColumnName = "IDDETALLE_AUD")})
    @ManyToOne(optional = false)
    private SgdetalleAuditoria sgdetalleAuditoria;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgprogAuditoria")
    private List<SgproccAuditoria> sgproccAuditoriaList;

    public SgprogAuditoria() {
    }

    public SgprogAuditoria(SgprogAuditoriaPK sgprogAuditoriaPK) {
        this.sgprogAuditoriaPK = sgprogAuditoriaPK;
    }

    public SgprogAuditoria(String idsociedad, String idprogAud) {
        this.sgprogAuditoriaPK = new SgprogAuditoriaPK(idsociedad, idprogAud);
    }

    public SgprogAuditoriaPK getSgprogAuditoriaPK() {
        return sgprogAuditoriaPK;
    }

    public void setSgprogAuditoriaPK(SgprogAuditoriaPK sgprogAuditoriaPK) {
        this.sgprogAuditoriaPK = sgprogAuditoriaPK;
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

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getAlcance() {
        return alcance;
    }

    public void setAlcance(String alcance) {
        this.alcance = alcance;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(String codUsuario) {
        this.codUsuario = codUsuario;
    }

    public SgdetalleAuditoria getSgdetalleAuditoria() {
        return sgdetalleAuditoria;
    }

    public void setSgdetalleAuditoria(SgdetalleAuditoria sgdetalleAuditoria) {
        this.sgdetalleAuditoria = sgdetalleAuditoria;
    }

    @XmlTransient
    public List<SgproccAuditoria> getSgproccAuditoriaList() {
        return sgproccAuditoriaList;
    }

    public void setSgproccAuditoriaList(List<SgproccAuditoria> sgproccAuditoriaList) {
        this.sgproccAuditoriaList = sgproccAuditoriaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgprogAuditoriaPK != null ? sgprogAuditoriaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgprogAuditoria)) {
            return false;
        }
        SgprogAuditoria other = (SgprogAuditoria) object;
        if ((this.sgprogAuditoriaPK == null && other.sgprogAuditoriaPK != null) || (this.sgprogAuditoriaPK != null && !this.sgprogAuditoriaPK.equals(other.sgprogAuditoriaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgprogAuditoria[ sgprogAuditoriaPK=" + sgprogAuditoriaPK + " ]";
    }
    
}
