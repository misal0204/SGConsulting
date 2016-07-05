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
import javax.persistence.Lob;
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
@Table(name = "sgriesgo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sgriesgo.findAll", query = "SELECT s FROM Sgriesgo s"),
    @NamedQuery(name = "Sgriesgo.findByIdsociedad", query = "SELECT s FROM Sgriesgo s WHERE s.sgriesgoPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "Sgriesgo.findByIdriesgo", query = "SELECT s FROM Sgriesgo s WHERE s.sgriesgoPK.idriesgo = :idriesgo"),
    @NamedQuery(name = "Sgriesgo.findByIdtipoRO", query = "SELECT s FROM Sgriesgo s WHERE s.idtipoRO = :idtipoRO"),
    @NamedQuery(name = "Sgriesgo.findByTitulo", query = "SELECT s FROM Sgriesgo s WHERE s.titulo = :titulo"),
    @NamedQuery(name = "Sgriesgo.findByFechaCreacion", query = "SELECT s FROM Sgriesgo s WHERE s.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Sgriesgo.findByCriticidad", query = "SELECT s FROM Sgriesgo s WHERE s.criticidad = :criticidad"),
    @NamedQuery(name = "Sgriesgo.findByFase", query = "SELECT s FROM Sgriesgo s WHERE s.fase = :fase"),
    @NamedQuery(name = "Sgriesgo.findByEstado", query = "SELECT s FROM Sgriesgo s WHERE s.estado = :estado")})
public class Sgriesgo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgriesgoPK sgriesgoPK;
    @Column(name = "IDTIPO_R_O")
    private String idtipoRO;
    @Column(name = "TITULO")
    private String titulo;
    @Lob
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Lob
    @Column(name = "CAUSA")
    private String causa;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "CRITICIDAD")
    private String criticidad;
    @Column(name = "FASE")
    private String fase;
    @Column(name = "ESTADO")
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgriesgo")
    private List<SgprocesoRiesgo> sgprocesoRiesgoList;
    @JoinColumn(name = "IDPROBABILIDAD", referencedColumnName = "IDPROBABILIDAD")
    @ManyToOne
    private SgprobabilidadRiesgo idprobabilidad;
    @JoinColumn(name = "IDTIPO_RIESGO", referencedColumnName = "IDTIPO_RIESGO")
    @ManyToOne
    private SgtipoRiesgo idtipoRiesgo;
    @JoinColumn(name = "COD_USUARIO", referencedColumnName = "COD_USUARIO")
    @ManyToOne
    private SgUsuario codUsuario;

    public Sgriesgo() {
    }

    public Sgriesgo(SgriesgoPK sgriesgoPK) {
        this.sgriesgoPK = sgriesgoPK;
    }

    public Sgriesgo(String idsociedad, String idriesgo) {
        this.sgriesgoPK = new SgriesgoPK(idsociedad, idriesgo);
    }

    public SgriesgoPK getSgriesgoPK() {
        return sgriesgoPK;
    }

    public void setSgriesgoPK(SgriesgoPK sgriesgoPK) {
        this.sgriesgoPK = sgriesgoPK;
    }

    public String getIdtipoRO() {
        return idtipoRO;
    }

    public void setIdtipoRO(String idtipoRO) {
        this.idtipoRO = idtipoRO;
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

    public String getCausa() {
        return causa;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getCriticidad() {
        return criticidad;
    }

    public void setCriticidad(String criticidad) {
        this.criticidad = criticidad;
    }

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<SgprocesoRiesgo> getSgprocesoRiesgoList() {
        return sgprocesoRiesgoList;
    }

    public void setSgprocesoRiesgoList(List<SgprocesoRiesgo> sgprocesoRiesgoList) {
        this.sgprocesoRiesgoList = sgprocesoRiesgoList;
    }

    public SgprobabilidadRiesgo getIdprobabilidad() {
        return idprobabilidad;
    }

    public void setIdprobabilidad(SgprobabilidadRiesgo idprobabilidad) {
        this.idprobabilidad = idprobabilidad;
    }

    public SgtipoRiesgo getIdtipoRiesgo() {
        return idtipoRiesgo;
    }

    public void setIdtipoRiesgo(SgtipoRiesgo idtipoRiesgo) {
        this.idtipoRiesgo = idtipoRiesgo;
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
        hash += (sgriesgoPK != null ? sgriesgoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sgriesgo)) {
            return false;
        }
        Sgriesgo other = (Sgriesgo) object;
        if ((this.sgriesgoPK == null && other.sgriesgoPK != null) || (this.sgriesgoPK != null && !this.sgriesgoPK.equals(other.sgriesgoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.Sgriesgo[ sgriesgoPK=" + sgriesgoPK + " ]";
    }
    
}
