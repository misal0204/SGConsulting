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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sganexo_noacc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SganexoNoacc.findAll", query = "SELECT s FROM SganexoNoacc s"),
    @NamedQuery(name = "SganexoNoacc.findByIdsociedad", query = "SELECT s FROM SganexoNoacc s WHERE s.sganexoNoaccPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SganexoNoacc.findByNoacc", query = "SELECT s FROM SganexoNoacc s WHERE s.sganexoNoaccPK.noacc = :noacc"),
    @NamedQuery(name = "SganexoNoacc.findByIdprocesos", query = "SELECT s FROM SganexoNoacc s WHERE s.sganexoNoaccPK.idprocesos = :idprocesos"),
    @NamedQuery(name = "SganexoNoacc.findBySubproceso", query = "SELECT s FROM SganexoNoacc s WHERE s.sganexoNoaccPK.subproceso = :subproceso"),
    @NamedQuery(name = "SganexoNoacc.findByFileName", query = "SELECT s FROM SganexoNoacc s WHERE s.sganexoNoaccPK.fileName = :fileName"),
    @NamedQuery(name = "SganexoNoacc.findByDescripcion", query = "SELECT s FROM SganexoNoacc s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "SganexoNoacc.findBySizeFile", query = "SELECT s FROM SganexoNoacc s WHERE s.sizeFile = :sizeFile")})
public class SganexoNoacc implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SganexoNoaccPK sganexoNoaccPK;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Lob
    @Column(name = "FILE")
    private byte[] file;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SIZE_FILE")
    private Double sizeFile;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "NOACC", referencedColumnName = "NOACC", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private SgaccionesMejora sgaccionesMejora;
    @JoinColumns({
        @JoinColumn(name = "IDPROCESOS", referencedColumnName = "IDPROCESOS", insertable = false, updatable = false),
        @JoinColumn(name = "SUBPROCESO", referencedColumnName = "SUBPROCESO", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private SgprocesoDetalle sgprocesoDetalle;

    public SganexoNoacc() {
    }

    public SganexoNoacc(SganexoNoaccPK sganexoNoaccPK) {
        this.sganexoNoaccPK = sganexoNoaccPK;
    }

    public SganexoNoacc(String idsociedad, String noacc, String idprocesos, String subproceso, String fileName) {
        this.sganexoNoaccPK = new SganexoNoaccPK(idsociedad, noacc, idprocesos, subproceso, fileName);
    }

    public SganexoNoaccPK getSganexoNoaccPK() {
        return sganexoNoaccPK;
    }

    public void setSganexoNoaccPK(SganexoNoaccPK sganexoNoaccPK) {
        this.sganexoNoaccPK = sganexoNoaccPK;
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

    public Double getSizeFile() {
        return sizeFile;
    }

    public void setSizeFile(Double sizeFile) {
        this.sizeFile = sizeFile;
    }

    public SgaccionesMejora getSgaccionesMejora() {
        return sgaccionesMejora;
    }

    public void setSgaccionesMejora(SgaccionesMejora sgaccionesMejora) {
        this.sgaccionesMejora = sgaccionesMejora;
    }

    public SgprocesoDetalle getSgprocesoDetalle() {
        return sgprocesoDetalle;
    }

    public void setSgprocesoDetalle(SgprocesoDetalle sgprocesoDetalle) {
        this.sgprocesoDetalle = sgprocesoDetalle;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sganexoNoaccPK != null ? sganexoNoaccPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SganexoNoacc)) {
            return false;
        }
        SganexoNoacc other = (SganexoNoacc) object;
        if ((this.sganexoNoaccPK == null && other.sganexoNoaccPK != null) || (this.sganexoNoaccPK != null && !this.sganexoNoaccPK.equals(other.sganexoNoaccPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SganexoNoacc[ sganexoNoaccPK=" + sganexoNoaccPK + " ]";
    }
    
}
