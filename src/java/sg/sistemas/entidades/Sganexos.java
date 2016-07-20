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
@Table(name = "sganexos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sganexos.findAll", query = "SELECT s FROM Sganexos s"),
    @NamedQuery(name = "Sganexos.findByIdsociedad", query = "SELECT s FROM Sganexos s WHERE s.sganexosPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "Sganexos.findByNonc", query = "SELECT s FROM Sganexos s WHERE s.sganexosPK.nonc = :nonc"),
    @NamedQuery(name = "Sganexos.findByIdprocesos", query = "SELECT s FROM Sganexos s WHERE s.sganexosPK.idprocesos = :idprocesos"),
    @NamedQuery(name = "Sganexos.findBySubproceso", query = "SELECT s FROM Sganexos s WHERE s.sganexosPK.subproceso = :subproceso"),
    @NamedQuery(name = "Sganexos.findByNameFile", query = "SELECT s FROM Sganexos s WHERE s.sganexosPK.nameFile = :nameFile"),
    @NamedQuery(name = "Sganexos.findBySize", query = "SELECT s FROM Sganexos s WHERE s.size = :size"),
    @NamedQuery(name = "Sganexos.findByTypeFile", query = "SELECT s FROM Sganexos s WHERE s.typeFile = :typeFile"),
    @NamedQuery(name = "Sganexos.findByCodUsuario", query = "SELECT s FROM Sganexos s WHERE s.codUsuario = :codUsuario"),
    @NamedQuery(name = "Sganexos.findByDateCreate", query = "SELECT s FROM Sganexos s WHERE s.dateCreate = :dateCreate")})
public class Sganexos implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SganexosPK sganexosPK;
    @Lob
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Lob
    @Column(name = "FILE")
    private byte[] file;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SIZE")
    private Double size;
    @Column(name = "TYPE_FILE")
    private String typeFile;
    @Column(name = "COD_USUARIO")
    private String codUsuario;
    @Column(name = "DATE_CREATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreate;
    @JoinColumns({
        @JoinColumn(name = "IDPROCESOS", referencedColumnName = "IDPROCESOS", insertable = false, updatable = false),
        @JoinColumn(name = "SUBPROCESO", referencedColumnName = "SUBPROCESO", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private SgprocesoDetalle sgprocesoDetalle;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "NONC", referencedColumnName = "NONC", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Sgnc sgnc;

    public Sganexos() {
    }

    public Sganexos(SganexosPK sganexosPK) {
        this.sganexosPK = sganexosPK;
    }

    public Sganexos(String idsociedad, String nonc, String idprocesos, String subproceso, String nameFile) {
        this.sganexosPK = new SganexosPK(idsociedad, nonc, idprocesos, subproceso, nameFile);
    }

    public SganexosPK getSganexosPK() {
        return sganexosPK;
    }

    public void setSganexosPK(SganexosPK sganexosPK) {
        this.sganexosPK = sganexosPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getTypeFile() {
        return typeFile;
    }

    public void setTypeFile(String typeFile) {
        this.typeFile = typeFile;
    }

    public String getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(String codUsuario) {
        this.codUsuario = codUsuario;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public SgprocesoDetalle getSgprocesoDetalle() {
        return sgprocesoDetalle;
    }

    public void setSgprocesoDetalle(SgprocesoDetalle sgprocesoDetalle) {
        this.sgprocesoDetalle = sgprocesoDetalle;
    }

    public Sgnc getSgnc() {
        return sgnc;
    }

    public void setSgnc(Sgnc sgnc) {
        this.sgnc = sgnc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sganexosPK != null ? sganexosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sganexos)) {
            return false;
        }
        Sganexos other = (Sganexos) object;
        if ((this.sganexosPK == null && other.sganexosPK != null) || (this.sganexosPK != null && !this.sganexosPK.equals(other.sganexosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.Sganexos[ sganexosPK=" + sganexosPK + " ]";
    }
    
}
