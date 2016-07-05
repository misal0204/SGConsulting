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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "sgnc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sgnc.findAll", query = "SELECT s FROM Sgnc s"),
    @NamedQuery(name = "Sgnc.findByIdsociedad", query = "SELECT s FROM Sgnc s WHERE s.sgncPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "Sgnc.findByNonc", query = "SELECT s FROM Sgnc s WHERE s.sgncPK.nonc = :nonc"),
    @NamedQuery(name = "Sgnc.findByFecha", query = "SELECT s FROM Sgnc s WHERE s.fecha = :fecha"),
    @NamedQuery(name = "Sgnc.findByFechaCompromiso", query = "SELECT s FROM Sgnc s WHERE s.fechaCompromiso = :fechaCompromiso"),
    @NamedQuery(name = "Sgnc.findByFechaImplementacion", query = "SELECT s FROM Sgnc s WHERE s.fechaImplementacion = :fechaImplementacion"),
    @NamedQuery(name = "Sgnc.findByFechaVerificacion", query = "SELECT s FROM Sgnc s WHERE s.fechaVerificacion = :fechaVerificacion"),
    @NamedQuery(name = "Sgnc.findByFechaCierre", query = "SELECT s FROM Sgnc s WHERE s.fechaCierre = :fechaCierre"),
    @NamedQuery(name = "Sgnc.findByFechaDescribe", query = "SELECT s FROM Sgnc s WHERE s.fechaDescribe = :fechaDescribe"),
    @NamedQuery(name = "Sgnc.findByBorrar", query = "SELECT s FROM Sgnc s WHERE s.borrar = :borrar")})
public class Sgnc implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgncPK sgncPK;
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Lob
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "FECHA_COMPROMISO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCompromiso;
    @Column(name = "FECHA_IMPLEMENTACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaImplementacion;
    @Column(name = "FECHA_VERIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVerificacion;
    @Column(name = "FECHA_CIERRE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCierre;
    @Basic(optional = false)
    @Column(name = "FECHA_DESCRIBE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDescribe;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "BORRAR")
    private Double borrar;
    @ManyToMany(mappedBy = "sgncList")
    private List<Sgcentro> sgcentroList;
    @ManyToMany(mappedBy = "sgncList")
    private List<Sgdepartamento> sgdepartamentoList;
    @ManyToMany(mappedBy = "sgncList")
    private List<SgUsuario> sgUsuarioList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "sgnc")
    private SgprocesosRe sgprocesosRe;
    @JoinColumn(name = "IDCRITICIDAD", referencedColumnName = "IDCRITICIDAD")
    @ManyToOne
    private Sgcriticidad idcriticidad;
    @JoinColumn(name = "IDESTADO", referencedColumnName = "IDESTADO")
    @ManyToOne
    private Sgestado idestado;
    @JoinColumn(name = "USUARIO_DESCRIBE", referencedColumnName = "COD_USUARIO")
    @ManyToOne(optional = false)
    private SgUsuario usuarioDescribe;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgnc")
    private List<Sgcausa> sgcausaList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "sgnc")
    private SgcorrecionNc sgcorrecionNc;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgnc")
    private List<Sgsigue> sgsigueList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgnc")
    private List<Sgverifica> sgverificaList;

    public Sgnc() {
    }

    public Sgnc(SgncPK sgncPK) {
        this.sgncPK = sgncPK;
    }

    public Sgnc(SgncPK sgncPK, Date fecha, Date fechaDescribe) {
        this.sgncPK = sgncPK;
        this.fecha = fecha;
        this.fechaDescribe = fechaDescribe;
    }

    public Sgnc(String idsociedad, String nonc) {
        this.sgncPK = new SgncPK(idsociedad, nonc);
    }

    public SgncPK getSgncPK() {
        return sgncPK;
    }

    public void setSgncPK(SgncPK sgncPK) {
        this.sgncPK = sgncPK;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaCompromiso() {
        return fechaCompromiso;
    }

    public void setFechaCompromiso(Date fechaCompromiso) {
        this.fechaCompromiso = fechaCompromiso;
    }

    public Date getFechaImplementacion() {
        return fechaImplementacion;
    }

    public void setFechaImplementacion(Date fechaImplementacion) {
        this.fechaImplementacion = fechaImplementacion;
    }

    public Date getFechaVerificacion() {
        return fechaVerificacion;
    }

    public void setFechaVerificacion(Date fechaVerificacion) {
        this.fechaVerificacion = fechaVerificacion;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public Date getFechaDescribe() {
        return fechaDescribe;
    }

    public void setFechaDescribe(Date fechaDescribe) {
        this.fechaDescribe = fechaDescribe;
    }

    public Double getBorrar() {
        return borrar;
    }

    public void setBorrar(Double borrar) {
        this.borrar = borrar;
    }

    @XmlTransient
    public List<Sgcentro> getSgcentroList() {
        return sgcentroList;
    }

    public void setSgcentroList(List<Sgcentro> sgcentroList) {
        this.sgcentroList = sgcentroList;
    }

    @XmlTransient
    public List<Sgdepartamento> getSgdepartamentoList() {
        return sgdepartamentoList;
    }

    public void setSgdepartamentoList(List<Sgdepartamento> sgdepartamentoList) {
        this.sgdepartamentoList = sgdepartamentoList;
    }

    @XmlTransient
    public List<SgUsuario> getSgUsuarioList() {
        return sgUsuarioList;
    }

    public void setSgUsuarioList(List<SgUsuario> sgUsuarioList) {
        this.sgUsuarioList = sgUsuarioList;
    }

    public SgprocesosRe getSgprocesosRe() {
        return sgprocesosRe;
    }

    public void setSgprocesosRe(SgprocesosRe sgprocesosRe) {
        this.sgprocesosRe = sgprocesosRe;
    }

    public Sgcriticidad getIdcriticidad() {
        return idcriticidad;
    }

    public void setIdcriticidad(Sgcriticidad idcriticidad) {
        this.idcriticidad = idcriticidad;
    }

    public Sgestado getIdestado() {
        return idestado;
    }

    public void setIdestado(Sgestado idestado) {
        this.idestado = idestado;
    }

    public SgUsuario getUsuarioDescribe() {
        return usuarioDescribe;
    }

    public void setUsuarioDescribe(SgUsuario usuarioDescribe) {
        this.usuarioDescribe = usuarioDescribe;
    }

    @XmlTransient
    public List<Sgcausa> getSgcausaList() {
        return sgcausaList;
    }

    public void setSgcausaList(List<Sgcausa> sgcausaList) {
        this.sgcausaList = sgcausaList;
    }

    public SgcorrecionNc getSgcorrecionNc() {
        return sgcorrecionNc;
    }

    public void setSgcorrecionNc(SgcorrecionNc sgcorrecionNc) {
        this.sgcorrecionNc = sgcorrecionNc;
    }

    @XmlTransient
    public List<Sgsigue> getSgsigueList() {
        return sgsigueList;
    }

    public void setSgsigueList(List<Sgsigue> sgsigueList) {
        this.sgsigueList = sgsigueList;
    }

    @XmlTransient
    public List<Sgverifica> getSgverificaList() {
        return sgverificaList;
    }

    public void setSgverificaList(List<Sgverifica> sgverificaList) {
        this.sgverificaList = sgverificaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgncPK != null ? sgncPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sgnc)) {
            return false;
        }
        Sgnc other = (Sgnc) object;
        if ((this.sgncPK == null && other.sgncPK != null) || (this.sgncPK != null && !this.sgncPK.equals(other.sgncPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.Sgnc[ sgncPK=" + sgncPK + " ]";
    }
    
}
