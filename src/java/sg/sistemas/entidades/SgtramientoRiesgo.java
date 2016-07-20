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
@Table(name = "sgtramiento_riesgo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgtramientoRiesgo.findAll", query = "SELECT s FROM SgtramientoRiesgo s"),
    @NamedQuery(name = "SgtramientoRiesgo.findByIdsociedad", query = "SELECT s FROM SgtramientoRiesgo s WHERE s.sgtramientoRiesgoPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgtramientoRiesgo.findByIdriesgo", query = "SELECT s FROM SgtramientoRiesgo s WHERE s.sgtramientoRiesgoPK.idriesgo = :idriesgo"),
    @NamedQuery(name = "SgtramientoRiesgo.findByIdprocesoRiesgo", query = "SELECT s FROM SgtramientoRiesgo s WHERE s.sgtramientoRiesgoPK.idprocesoRiesgo = :idprocesoRiesgo"),
    @NamedQuery(name = "SgtramientoRiesgo.findByIdprocesos", query = "SELECT s FROM SgtramientoRiesgo s WHERE s.sgtramientoRiesgoPK.idprocesos = :idprocesos"),
    @NamedQuery(name = "SgtramientoRiesgo.findByCodUsuario", query = "SELECT s FROM SgtramientoRiesgo s WHERE s.sgtramientoRiesgoPK.codUsuario = :codUsuario"),
    @NamedQuery(name = "SgtramientoRiesgo.findByFeChaPrevista", query = "SELECT s FROM SgtramientoRiesgo s WHERE s.feChaPrevista = :feChaPrevista"),
    @NamedQuery(name = "SgtramientoRiesgo.findByFechaRealizada", query = "SELECT s FROM SgtramientoRiesgo s WHERE s.fechaRealizada = :fechaRealizada"),
    @NamedQuery(name = "SgtramientoRiesgo.findByFechaFinalizada", query = "SELECT s FROM SgtramientoRiesgo s WHERE s.fechaFinalizada = :fechaFinalizada"),
    @NamedQuery(name = "SgtramientoRiesgo.findByEstado", query = "SELECT s FROM SgtramientoRiesgo s WHERE s.estado = :estado")})
public class SgtramientoRiesgo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgtramientoRiesgoPK sgtramientoRiesgoPK;
    @Column(name = "FE_CHA_PREVISTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date feChaPrevista;
    @Column(name = "FECHA_REALIZADA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRealizada;
    @Column(name = "FECHA_FINALIZADA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinalizada;
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "IDRIESGO", referencedColumnName = "IDRIESGO", insertable = false, updatable = false),
        @JoinColumn(name = "IDPROCESO_RIESGO", referencedColumnName = "IDPROCESO_RIESGO", insertable = false, updatable = false),
        @JoinColumn(name = "IDPROCESOS", referencedColumnName = "IDPROCESOS", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private SgprocesoRiesgo sgprocesoRiesgo;
    @JoinColumn(name = "COD_USUARIO", referencedColumnName = "COD_USUARIO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Sgusuario sgusuario;

    public SgtramientoRiesgo() {
    }

    public SgtramientoRiesgo(SgtramientoRiesgoPK sgtramientoRiesgoPK) {
        this.sgtramientoRiesgoPK = sgtramientoRiesgoPK;
    }

    public SgtramientoRiesgo(String idsociedad, String idriesgo, String idprocesoRiesgo, String idprocesos, String codUsuario, String tratamiento) {
        this.sgtramientoRiesgoPK = new SgtramientoRiesgoPK(idsociedad, idriesgo, idprocesoRiesgo, idprocesos, codUsuario, tratamiento);
    }

    public SgtramientoRiesgoPK getSgtramientoRiesgoPK() {
        return sgtramientoRiesgoPK;
    }

    public void setSgtramientoRiesgoPK(SgtramientoRiesgoPK sgtramientoRiesgoPK) {
        this.sgtramientoRiesgoPK = sgtramientoRiesgoPK;
    }

    public Date getFeChaPrevista() {
        return feChaPrevista;
    }

    public void setFeChaPrevista(Date feChaPrevista) {
        this.feChaPrevista = feChaPrevista;
    }

    public Date getFechaRealizada() {
        return fechaRealizada;
    }

    public void setFechaRealizada(Date fechaRealizada) {
        this.fechaRealizada = fechaRealizada;
    }

    public Date getFechaFinalizada() {
        return fechaFinalizada;
    }

    public void setFechaFinalizada(Date fechaFinalizada) {
        this.fechaFinalizada = fechaFinalizada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public SgprocesoRiesgo getSgprocesoRiesgo() {
        return sgprocesoRiesgo;
    }

    public void setSgprocesoRiesgo(SgprocesoRiesgo sgprocesoRiesgo) {
        this.sgprocesoRiesgo = sgprocesoRiesgo;
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
        hash += (sgtramientoRiesgoPK != null ? sgtramientoRiesgoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgtramientoRiesgo)) {
            return false;
        }
        SgtramientoRiesgo other = (SgtramientoRiesgo) object;
        if ((this.sgtramientoRiesgoPK == null && other.sgtramientoRiesgoPK != null) || (this.sgtramientoRiesgoPK != null && !this.sgtramientoRiesgoPK.equals(other.sgtramientoRiesgoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgtramientoRiesgo[ sgtramientoRiesgoPK=" + sgtramientoRiesgoPK + " ]";
    }
    
}
