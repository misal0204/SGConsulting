/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "sgplan_anexos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgplanAnexos.findAll", query = "SELECT s FROM SgplanAnexos s"),
    @NamedQuery(name = "SgplanAnexos.findByIdsociedad", query = "SELECT s FROM SgplanAnexos s WHERE s.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgplanAnexos.findByIdsubprocess", query = "SELECT s FROM SgplanAnexos s WHERE s.idsubprocess = :idsubprocess"),
    @NamedQuery(name = "SgplanAnexos.findByNombreArchivo", query = "SELECT s FROM SgplanAnexos s WHERE s.nombreArchivo = :nombreArchivo"),
    @NamedQuery(name = "SgplanAnexos.findBySize", query = "SELECT s FROM SgplanAnexos s WHERE s.size = :size"),
    @NamedQuery(name = "SgplanAnexos.findByTipoArchivo", query = "SELECT s FROM SgplanAnexos s WHERE s.tipoArchivo = :tipoArchivo"),
    @NamedQuery(name = "SgplanAnexos.findByUsuarioArchivo", query = "SELECT s FROM SgplanAnexos s WHERE s.usuarioArchivo = :usuarioArchivo"),
    @NamedQuery(name = "SgplanAnexos.findByFechaCreacion", query = "SELECT s FROM SgplanAnexos s WHERE s.fechaCreacion = :fechaCreacion")})
public class SgplanAnexos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDSOCIEDAD")
    private String idsociedad;
    @Column(name = "IDSUBPROCESS")
    private String idsubprocess;
    @Column(name = "NOMBRE__ARCHIVO")
    private String nombreArchivo;
    @Lob
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Lob
    @Column(name = "ARCHIVO")
    private byte[] archivo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SIZE")
    private Double size;
    @Column(name = "TIPO_ARCHIVO")
    private String tipoArchivo;
    @Column(name = "USUARIO_ARCHIVO")
    private String usuarioArchivo;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    public SgplanAnexos() {
    }

    public SgplanAnexos(String idsociedad) {
        this.idsociedad = idsociedad;
    }

    public String getIdsociedad() {
        return idsociedad;
    }

    public void setIdsociedad(String idsociedad) {
        this.idsociedad = idsociedad;
    }

    public String getIdsubprocess() {
        return idsubprocess;
    }

    public void setIdsubprocess(String idsubprocess) {
        this.idsubprocess = idsubprocess;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    public String getUsuarioArchivo() {
        return usuarioArchivo;
    }

    public void setUsuarioArchivo(String usuarioArchivo) {
        this.usuarioArchivo = usuarioArchivo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsociedad != null ? idsociedad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgplanAnexos)) {
            return false;
        }
        SgplanAnexos other = (SgplanAnexos) object;
        if ((this.idsociedad == null && other.idsociedad != null) || (this.idsociedad != null && !this.idsociedad.equals(other.idsociedad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgplanAnexos[ idsociedad=" + idsociedad + " ]";
    }
    
}
