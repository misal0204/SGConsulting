/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgdetalle_documento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgdetalleDocumento.findAll", query = "SELECT s FROM SgdetalleDocumento s"),
    @NamedQuery(name = "SgdetalleDocumento.findByIdsociedad", query = "SELECT s FROM SgdetalleDocumento s WHERE s.sgdetalleDocumentoPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgdetalleDocumento.findByIddocumento", query = "SELECT s FROM SgdetalleDocumento s WHERE s.sgdetalleDocumentoPK.iddocumento = :iddocumento"),
    @NamedQuery(name = "SgdetalleDocumento.findByIdtipoDocumento", query = "SELECT s FROM SgdetalleDocumento s WHERE s.sgdetalleDocumentoPK.idtipoDocumento = :idtipoDocumento"),
    @NamedQuery(name = "SgdetalleDocumento.findByNombreDocumento", query = "SELECT s FROM SgdetalleDocumento s WHERE s.nombreDocumento = :nombreDocumento"),
    @NamedQuery(name = "SgdetalleDocumento.findBySize", query = "SELECT s FROM SgdetalleDocumento s WHERE s.size = :size"),
    @NamedQuery(name = "SgdetalleDocumento.findByVersion", query = "SELECT s FROM SgdetalleDocumento s WHERE s.version = :version")})
public class SgdetalleDocumento implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgdetalleDocumentoPK sgdetalleDocumentoPK;
    @Column(name = "NOMBRE_DOCUMENTO")
    private String nombreDocumento;
    @Lob
    @Column(name = "ARCHIVO")
    private byte[] archivo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SIZE")
    private Double size;
    @Column(name = "VERSION")
    private Double version;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "IDDOCUMENTO", referencedColumnName = "IDDOCUMENTO", insertable = false, updatable = false),
        @JoinColumn(name = "IDTIPO_DOCUMENTO", referencedColumnName = "IDTIPO_DOCUMENTO", insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private Sgdocumentos sgdocumentos;

    public SgdetalleDocumento() {
    }

    public SgdetalleDocumento(SgdetalleDocumentoPK sgdetalleDocumentoPK) {
        this.sgdetalleDocumentoPK = sgdetalleDocumentoPK;
    }

    public SgdetalleDocumento(String idsociedad, String iddocumento, String idtipoDocumento) {
        this.sgdetalleDocumentoPK = new SgdetalleDocumentoPK(idsociedad, iddocumento, idtipoDocumento);
    }

    public SgdetalleDocumentoPK getSgdetalleDocumentoPK() {
        return sgdetalleDocumentoPK;
    }

    public void setSgdetalleDocumentoPK(SgdetalleDocumentoPK sgdetalleDocumentoPK) {
        this.sgdetalleDocumentoPK = sgdetalleDocumentoPK;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
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

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }

    public Sgdocumentos getSgdocumentos() {
        return sgdocumentos;
    }

    public void setSgdocumentos(Sgdocumentos sgdocumentos) {
        this.sgdocumentos = sgdocumentos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgdetalleDocumentoPK != null ? sgdetalleDocumentoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgdetalleDocumento)) {
            return false;
        }
        SgdetalleDocumento other = (SgdetalleDocumento) object;
        if ((this.sgdetalleDocumentoPK == null && other.sgdetalleDocumentoPK != null) || (this.sgdetalleDocumentoPK != null && !this.sgdetalleDocumentoPK.equals(other.sgdetalleDocumentoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgdetalleDocumento[ sgdetalleDocumentoPK=" + sgdetalleDocumentoPK + " ]";
    }
    
}
