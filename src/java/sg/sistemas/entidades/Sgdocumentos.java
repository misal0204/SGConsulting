/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgdocumentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sgdocumentos.findAll", query = "SELECT s FROM Sgdocumentos s"),
    @NamedQuery(name = "Sgdocumentos.findByIdsociedad", query = "SELECT s FROM Sgdocumentos s WHERE s.sgdocumentosPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "Sgdocumentos.findByIddocumento", query = "SELECT s FROM Sgdocumentos s WHERE s.sgdocumentosPK.iddocumento = :iddocumento"),
    @NamedQuery(name = "Sgdocumentos.findByIdtipoDocumento", query = "SELECT s FROM Sgdocumentos s WHERE s.sgdocumentosPK.idtipoDocumento = :idtipoDocumento"),
    @NamedQuery(name = "Sgdocumentos.findByObjetivo", query = "SELECT s FROM Sgdocumentos s WHERE s.objetivo = :objetivo"),
    @NamedQuery(name = "Sgdocumentos.findByAlcance", query = "SELECT s FROM Sgdocumentos s WHERE s.alcance = :alcance"),
    @NamedQuery(name = "Sgdocumentos.findByFechaCreacion", query = "SELECT s FROM Sgdocumentos s WHERE s.fechaCreacion = :fechaCreacion")})
public class Sgdocumentos implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgdocumentosPK sgdocumentosPK;
    @Column(name = "OBJETIVO")
    private String objetivo;
    @Column(name = "ALCANCE")
    private String alcance;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "sgdocumentos")
    private SgdetalleDocumento sgdetalleDocumento;
    @JoinColumn(name = "IDTIPO_DOCUMENTO", referencedColumnName = "IDTIPO_DOCUMENTO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SgtiposDocumentos sgtiposDocumentos;

    public Sgdocumentos() {
    }

    public Sgdocumentos(SgdocumentosPK sgdocumentosPK) {
        this.sgdocumentosPK = sgdocumentosPK;
    }

    public Sgdocumentos(String idsociedad, String iddocumento, String idtipoDocumento) {
        this.sgdocumentosPK = new SgdocumentosPK(idsociedad, iddocumento, idtipoDocumento);
    }

    public SgdocumentosPK getSgdocumentosPK() {
        return sgdocumentosPK;
    }

    public void setSgdocumentosPK(SgdocumentosPK sgdocumentosPK) {
        this.sgdocumentosPK = sgdocumentosPK;
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

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public SgdetalleDocumento getSgdetalleDocumento() {
        return sgdetalleDocumento;
    }

    public void setSgdetalleDocumento(SgdetalleDocumento sgdetalleDocumento) {
        this.sgdetalleDocumento = sgdetalleDocumento;
    }

    public SgtiposDocumentos getSgtiposDocumentos() {
        return sgtiposDocumentos;
    }

    public void setSgtiposDocumentos(SgtiposDocumentos sgtiposDocumentos) {
        this.sgtiposDocumentos = sgtiposDocumentos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgdocumentosPK != null ? sgdocumentosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sgdocumentos)) {
            return false;
        }
        Sgdocumentos other = (Sgdocumentos) object;
        if ((this.sgdocumentosPK == null && other.sgdocumentosPK != null) || (this.sgdocumentosPK != null && !this.sgdocumentosPK.equals(other.sgdocumentosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.Sgdocumentos[ sgdocumentosPK=" + sgdocumentosPK + " ]";
    }
    
}
