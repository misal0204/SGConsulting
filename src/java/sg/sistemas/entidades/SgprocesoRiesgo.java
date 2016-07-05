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
import javax.persistence.Lob;
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
@Table(name = "sgproceso_riesgo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgprocesoRiesgo.findAll", query = "SELECT s FROM SgprocesoRiesgo s"),
    @NamedQuery(name = "SgprocesoRiesgo.findByIdsociedad", query = "SELECT s FROM SgprocesoRiesgo s WHERE s.sgprocesoRiesgoPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgprocesoRiesgo.findByIdriesgo", query = "SELECT s FROM SgprocesoRiesgo s WHERE s.sgprocesoRiesgoPK.idriesgo = :idriesgo"),
    @NamedQuery(name = "SgprocesoRiesgo.findByIdprocesoRiesgo", query = "SELECT s FROM SgprocesoRiesgo s WHERE s.sgprocesoRiesgoPK.idprocesoRiesgo = :idprocesoRiesgo"),
    @NamedQuery(name = "SgprocesoRiesgo.findByIdprocesos", query = "SELECT s FROM SgprocesoRiesgo s WHERE s.sgprocesoRiesgoPK.idprocesos = :idprocesos"),
    @NamedQuery(name = "SgprocesoRiesgo.findByEstado", query = "SELECT s FROM SgprocesoRiesgo s WHERE s.estado = :estado"),
    @NamedQuery(name = "SgprocesoRiesgo.findByFechaInicio", query = "SELECT s FROM SgprocesoRiesgo s WHERE s.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "SgprocesoRiesgo.findByFechaFin", query = "SELECT s FROM SgprocesoRiesgo s WHERE s.fechaFin = :fechaFin"),
    @NamedQuery(name = "SgprocesoRiesgo.findByValor", query = "SELECT s FROM SgprocesoRiesgo s WHERE s.valor = :valor")})
public class SgprocesoRiesgo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgprocesoRiesgoPK sgprocesoRiesgoPK;
    @Lob
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Lob
    @Column(name = "RESPONSABLES")
    private String responsables;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Column(name = "FECHA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALOR")
    private Double valor;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "IDRIESGO", referencedColumnName = "IDRIESGO", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Sgriesgo sgriesgo;
    @JoinColumn(name = "IDVALORACION", referencedColumnName = "IDVALORACION")
    @ManyToOne
    private SgvaloracionRiesgo idvaloracion;

    public SgprocesoRiesgo() {
    }

    public SgprocesoRiesgo(SgprocesoRiesgoPK sgprocesoRiesgoPK) {
        this.sgprocesoRiesgoPK = sgprocesoRiesgoPK;
    }

    public SgprocesoRiesgo(String idsociedad, String idriesgo, String idprocesoRiesgo, String idprocesos) {
        this.sgprocesoRiesgoPK = new SgprocesoRiesgoPK(idsociedad, idriesgo, idprocesoRiesgo, idprocesos);
    }

    public SgprocesoRiesgoPK getSgprocesoRiesgoPK() {
        return sgprocesoRiesgoPK;
    }

    public void setSgprocesoRiesgoPK(SgprocesoRiesgoPK sgprocesoRiesgoPK) {
        this.sgprocesoRiesgoPK = sgprocesoRiesgoPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getResponsables() {
        return responsables;
    }

    public void setResponsables(String responsables) {
        this.responsables = responsables;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Sgriesgo getSgriesgo() {
        return sgriesgo;
    }

    public void setSgriesgo(Sgriesgo sgriesgo) {
        this.sgriesgo = sgriesgo;
    }

    public SgvaloracionRiesgo getIdvaloracion() {
        return idvaloracion;
    }

    public void setIdvaloracion(SgvaloracionRiesgo idvaloracion) {
        this.idvaloracion = idvaloracion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgprocesoRiesgoPK != null ? sgprocesoRiesgoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgprocesoRiesgo)) {
            return false;
        }
        SgprocesoRiesgo other = (SgprocesoRiesgo) object;
        if ((this.sgprocesoRiesgoPK == null && other.sgprocesoRiesgoPK != null) || (this.sgprocesoRiesgoPK != null && !this.sgprocesoRiesgoPK.equals(other.sgprocesoRiesgoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgprocesoRiesgo[ sgprocesoRiesgoPK=" + sgprocesoRiesgoPK + " ]";
    }
    
}
