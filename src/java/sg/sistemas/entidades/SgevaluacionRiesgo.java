/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgevaluacion_riesgo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgevaluacionRiesgo.findAll", query = "SELECT s FROM SgevaluacionRiesgo s"),
    @NamedQuery(name = "SgevaluacionRiesgo.findByIdsociedad", query = "SELECT s FROM SgevaluacionRiesgo s WHERE s.sgevaluacionRiesgoPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgevaluacionRiesgo.findByIdriesgo", query = "SELECT s FROM SgevaluacionRiesgo s WHERE s.sgevaluacionRiesgoPK.idriesgo = :idriesgo"),
    @NamedQuery(name = "SgevaluacionRiesgo.findByCodUsuario", query = "SELECT s FROM SgevaluacionRiesgo s WHERE s.sgevaluacionRiesgoPK.codUsuario = :codUsuario"),
    @NamedQuery(name = "SgevaluacionRiesgo.findByFechaInicio", query = "SELECT s FROM SgevaluacionRiesgo s WHERE s.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "SgevaluacionRiesgo.findByFechaFin", query = "SELECT s FROM SgevaluacionRiesgo s WHERE s.fechaFin = :fechaFin"),
    @NamedQuery(name = "SgevaluacionRiesgo.findByEstado", query = "SELECT s FROM SgevaluacionRiesgo s WHERE s.estado = :estado"),
    @NamedQuery(name = "SgevaluacionRiesgo.findByDecision", query = "SELECT s FROM SgevaluacionRiesgo s WHERE s.decision = :decision"),
    @NamedQuery(name = "SgevaluacionRiesgo.findByCriticidad", query = "SELECT s FROM SgevaluacionRiesgo s WHERE s.criticidad = :criticidad")})
public class SgevaluacionRiesgo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgevaluacionRiesgoPK sgevaluacionRiesgoPK;
    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Column(name = "FECHA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "DECISION")
    private String decision;
    @Column(name = "CRITICIDAD")
    private String criticidad;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "IDRIESGO", referencedColumnName = "IDRIESGO", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Sgriesgo sgriesgo;
    @JoinColumn(name = "COD_USUARIO", referencedColumnName = "COD_USUARIO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Sgusuario sgusuario;

    public SgevaluacionRiesgo() {
    }

    public SgevaluacionRiesgo(SgevaluacionRiesgoPK sgevaluacionRiesgoPK) {
        this.sgevaluacionRiesgoPK = sgevaluacionRiesgoPK;
    }

    public SgevaluacionRiesgo(String idsociedad, String idriesgo, String descripcion, String codUsuario) {
        this.sgevaluacionRiesgoPK = new SgevaluacionRiesgoPK(idsociedad, idriesgo, descripcion, codUsuario);
    }

    public SgevaluacionRiesgoPK getSgevaluacionRiesgoPK() {
        return sgevaluacionRiesgoPK;
    }

    public void setSgevaluacionRiesgoPK(SgevaluacionRiesgoPK sgevaluacionRiesgoPK) {
        this.sgevaluacionRiesgoPK = sgevaluacionRiesgoPK;
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

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getCriticidad() {
        return criticidad;
    }

    public void setCriticidad(String criticidad) {
        this.criticidad = criticidad;
    }

    public Sgriesgo getSgriesgo() {
        return sgriesgo;
    }

    public void setSgriesgo(Sgriesgo sgriesgo) {
        this.sgriesgo = sgriesgo;
    }

    public Sgusuario getSgusuario() {
        return sgusuario;
    }

    public void setSgusuario(Sgusuario sgusuario) {
        this.sgusuario = sgusuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgevaluacionRiesgoPK != null ? sgevaluacionRiesgoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgevaluacionRiesgo)) {
            return false;
        }
        SgevaluacionRiesgo other = (SgevaluacionRiesgo) object;
        if ((this.sgevaluacionRiesgoPK == null && other.sgevaluacionRiesgoPK != null) || (this.sgevaluacionRiesgoPK != null && !this.sgevaluacionRiesgoPK.equals(other.sgevaluacionRiesgoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgevaluacionRiesgo[ sgevaluacionRiesgoPK=" + sgevaluacionRiesgoPK + " ]";
    }
    
}
