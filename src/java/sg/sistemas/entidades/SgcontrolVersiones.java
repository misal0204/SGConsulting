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
@Table(name = "sgcontrol_versiones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgcontrolVersiones.findAll", query = "SELECT s FROM SgcontrolVersiones s"),
    @NamedQuery(name = "SgcontrolVersiones.findByIdsociedad", query = "SELECT s FROM SgcontrolVersiones s WHERE s.sgcontrolVersionesPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgcontrolVersiones.findByIddocumento", query = "SELECT s FROM SgcontrolVersiones s WHERE s.sgcontrolVersionesPK.iddocumento = :iddocumento"),
    @NamedQuery(name = "SgcontrolVersiones.findByIdtipoDocumento", query = "SELECT s FROM SgcontrolVersiones s WHERE s.sgcontrolVersionesPK.idtipoDocumento = :idtipoDocumento"),
    @NamedQuery(name = "SgcontrolVersiones.findByNombre", query = "SELECT s FROM SgcontrolVersiones s WHERE s.sgcontrolVersionesPK.nombre = :nombre"),
    @NamedQuery(name = "SgcontrolVersiones.findByVersion", query = "SELECT s FROM SgcontrolVersiones s WHERE s.version = :version"),
    @NamedQuery(name = "SgcontrolVersiones.findBySize", query = "SELECT s FROM SgcontrolVersiones s WHERE s.size = :size"),
    @NamedQuery(name = "SgcontrolVersiones.findByFechaVersion", query = "SELECT s FROM SgcontrolVersiones s WHERE s.fechaVersion = :fechaVersion"),
    @NamedQuery(name = "SgcontrolVersiones.findByFechaPublicacion", query = "SELECT s FROM SgcontrolVersiones s WHERE s.fechaPublicacion = :fechaPublicacion"),
    @NamedQuery(name = "SgcontrolVersiones.findByEditadoPor", query = "SELECT s FROM SgcontrolVersiones s WHERE s.editadoPor = :editadoPor"),
    @NamedQuery(name = "SgcontrolVersiones.findByRevisadoPor", query = "SELECT s FROM SgcontrolVersiones s WHERE s.revisadoPor = :revisadoPor"),
    @NamedQuery(name = "SgcontrolVersiones.findByAprobadoPor", query = "SELECT s FROM SgcontrolVersiones s WHERE s.aprobadoPor = :aprobadoPor"),
    @NamedQuery(name = "SgcontrolVersiones.findByEstado", query = "SELECT s FROM SgcontrolVersiones s WHERE s.estado = :estado")})
public class SgcontrolVersiones implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgcontrolVersionesPK sgcontrolVersionesPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VERSION")
    private Double version;
    @Lob
    @Column(name = "COPIA_ARCHIVO")
    private byte[] copiaArchivo;
    @Column(name = "SIZE")
    private Double size;
    @Column(name = "FECHA_VERSION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVersion;
    @Column(name = "FECHA_PUBLICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPublicacion;
    @Column(name = "EDITADO_POR")
    private String editadoPor;
    @Column(name = "REVISADO_POR")
    private String revisadoPor;
    @Column(name = "APROBADO_POR")
    private String aprobadoPor;
    @Column(name = "ESTADO")
    private String estado;
    @Lob
    @Column(name = "OBSERVACION")
    private String observacion;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "IDDOCUMENTO", referencedColumnName = "IDDOCUMENTO", insertable = false, updatable = false),
        @JoinColumn(name = "IDTIPO_DOCUMENTO", referencedColumnName = "IDTIPO_DOCUMENTO", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private SgdetalleDocumento sgdetalleDocumento;

    public SgcontrolVersiones() {
    }

    public SgcontrolVersiones(SgcontrolVersionesPK sgcontrolVersionesPK) {
        this.sgcontrolVersionesPK = sgcontrolVersionesPK;
    }

    public SgcontrolVersiones(String idsociedad, String iddocumento, String idtipoDocumento, String nombre) {
        this.sgcontrolVersionesPK = new SgcontrolVersionesPK(idsociedad, iddocumento, idtipoDocumento, nombre);
    }

    public SgcontrolVersionesPK getSgcontrolVersionesPK() {
        return sgcontrolVersionesPK;
    }

    public void setSgcontrolVersionesPK(SgcontrolVersionesPK sgcontrolVersionesPK) {
        this.sgcontrolVersionesPK = sgcontrolVersionesPK;
    }

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }

    public byte[] getCopiaArchivo() {
        return copiaArchivo;
    }

    public void setCopiaArchivo(byte[] copiaArchivo) {
        this.copiaArchivo = copiaArchivo;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public Date getFechaVersion() {
        return fechaVersion;
    }

    public void setFechaVersion(Date fechaVersion) {
        this.fechaVersion = fechaVersion;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getEditadoPor() {
        return editadoPor;
    }

    public void setEditadoPor(String editadoPor) {
        this.editadoPor = editadoPor;
    }

    public String getRevisadoPor() {
        return revisadoPor;
    }

    public void setRevisadoPor(String revisadoPor) {
        this.revisadoPor = revisadoPor;
    }

    public String getAprobadoPor() {
        return aprobadoPor;
    }

    public void setAprobadoPor(String aprobadoPor) {
        this.aprobadoPor = aprobadoPor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public SgdetalleDocumento getSgdetalleDocumento() {
        return sgdetalleDocumento;
    }

    public void setSgdetalleDocumento(SgdetalleDocumento sgdetalleDocumento) {
        this.sgdetalleDocumento = sgdetalleDocumento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgcontrolVersionesPK != null ? sgcontrolVersionesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgcontrolVersiones)) {
            return false;
        }
        SgcontrolVersiones other = (SgcontrolVersiones) object;
        if ((this.sgcontrolVersionesPK == null && other.sgcontrolVersionesPK != null) || (this.sgcontrolVersionesPK != null && !this.sgcontrolVersionesPK.equals(other.sgcontrolVersionesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgcontrolVersiones[ sgcontrolVersionesPK=" + sgcontrolVersionesPK + " ]";
    }
    
}
