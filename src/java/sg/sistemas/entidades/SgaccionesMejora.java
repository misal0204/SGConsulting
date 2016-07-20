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
@Table(name = "sgacciones_mejora")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgaccionesMejora.findAll", query = "SELECT s FROM SgaccionesMejora s"),
    @NamedQuery(name = "SgaccionesMejora.findByIdsociedad", query = "SELECT s FROM SgaccionesMejora s WHERE s.sgaccionesMejoraPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgaccionesMejora.findByNoacc", query = "SELECT s FROM SgaccionesMejora s WHERE s.sgaccionesMejoraPK.noacc = :noacc"),
    @NamedQuery(name = "SgaccionesMejora.findByDescripcion", query = "SELECT s FROM SgaccionesMejora s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "SgaccionesMejora.findByFecha", query = "SELECT s FROM SgaccionesMejora s WHERE s.fecha = :fecha"),
    @NamedQuery(name = "SgaccionesMejora.findByFechaFin", query = "SELECT s FROM SgaccionesMejora s WHERE s.fechaFin = :fechaFin"),
    @NamedQuery(name = "SgaccionesMejora.findByFechaRealizada", query = "SELECT s FROM SgaccionesMejora s WHERE s.fechaRealizada = :fechaRealizada"),
    @NamedQuery(name = "SgaccionesMejora.findByEstado", query = "SELECT s FROM SgaccionesMejora s WHERE s.estado = :estado"),
    @NamedQuery(name = "SgaccionesMejora.findByBorrar", query = "SELECT s FROM SgaccionesMejora s WHERE s.borrar = :borrar")})
public class SgaccionesMejora implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgaccionesMejoraPK sgaccionesMejoraPK;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "FECHA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Column(name = "FECHA_REALIZADA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRealizada;
    @Column(name = "ESTADO")
    private String estado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "BORRAR")
    private Double borrar;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgaccionesMejora")
    private List<SgverificacionNoacc> sgverificacionNoaccList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgaccionesMejora")
    private List<SgseguimientoNoacc> sgseguimientoNoaccList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgaccionesMejora")
    private List<SganexoNoacc> sganexoNoaccList;
    @JoinColumn(name = "COD_USUARIO", referencedColumnName = "COD_USUARIO")
    @ManyToOne
    private Sgusuario codUsuario;

    public SgaccionesMejora() {
    }

    public SgaccionesMejora(SgaccionesMejoraPK sgaccionesMejoraPK) {
        this.sgaccionesMejoraPK = sgaccionesMejoraPK;
    }

    public SgaccionesMejora(String idsociedad, String noacc) {
        this.sgaccionesMejoraPK = new SgaccionesMejoraPK(idsociedad, noacc);
    }

    public SgaccionesMejoraPK getSgaccionesMejoraPK() {
        return sgaccionesMejoraPK;
    }

    public void setSgaccionesMejoraPK(SgaccionesMejoraPK sgaccionesMejoraPK) {
        this.sgaccionesMejoraPK = sgaccionesMejoraPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaRealizada() {
        return fechaRealizada;
    }

    public void setFechaRealizada(Date fechaRealizada) {
        this.fechaRealizada = fechaRealizada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getBorrar() {
        return borrar;
    }

    public void setBorrar(Double borrar) {
        this.borrar = borrar;
    }

    @XmlTransient
    public List<SgverificacionNoacc> getSgverificacionNoaccList() {
        return sgverificacionNoaccList;
    }

    public void setSgverificacionNoaccList(List<SgverificacionNoacc> sgverificacionNoaccList) {
        this.sgverificacionNoaccList = sgverificacionNoaccList;
    }

    @XmlTransient
    public List<SgseguimientoNoacc> getSgseguimientoNoaccList() {
        return sgseguimientoNoaccList;
    }

    public void setSgseguimientoNoaccList(List<SgseguimientoNoacc> sgseguimientoNoaccList) {
        this.sgseguimientoNoaccList = sgseguimientoNoaccList;
    }

    @XmlTransient
    public List<SganexoNoacc> getSganexoNoaccList() {
        return sganexoNoaccList;
    }

    public void setSganexoNoaccList(List<SganexoNoacc> sganexoNoaccList) {
        this.sganexoNoaccList = sganexoNoaccList;
    }

    public Sgusuario getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(Sgusuario codUsuario) {
        this.codUsuario = codUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgaccionesMejoraPK != null ? sgaccionesMejoraPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgaccionesMejora)) {
            return false;
        }
        SgaccionesMejora other = (SgaccionesMejora) object;
        if ((this.sgaccionesMejoraPK == null && other.sgaccionesMejoraPK != null) || (this.sgaccionesMejoraPK != null && !this.sgaccionesMejoraPK.equals(other.sgaccionesMejoraPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgaccionesMejora[ sgaccionesMejoraPK=" + sgaccionesMejoraPK + " ]";
    }
    
}
