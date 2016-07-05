/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgtipos_documentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgtiposDocumentos.findAll", query = "SELECT s FROM SgtiposDocumentos s"),
    @NamedQuery(name = "SgtiposDocumentos.findByIdtipoDocumento", query = "SELECT s FROM SgtiposDocumentos s WHERE s.idtipoDocumento = :idtipoDocumento"),
    @NamedQuery(name = "SgtiposDocumentos.findByDescripcion", query = "SELECT s FROM SgtiposDocumentos s WHERE s.descripcion = :descripcion")})
public class SgtiposDocumentos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDTIPO_DOCUMENTO")
    private String idtipoDocumento;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgtiposDocumentos")
    private List<Sgdocumentos> sgdocumentosList;

    public SgtiposDocumentos() {
    }

    public SgtiposDocumentos(String idtipoDocumento) {
        this.idtipoDocumento = idtipoDocumento;
    }

    public String getIdtipoDocumento() {
        return idtipoDocumento;
    }

    public void setIdtipoDocumento(String idtipoDocumento) {
        this.idtipoDocumento = idtipoDocumento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Sgdocumentos> getSgdocumentosList() {
        return sgdocumentosList;
    }

    public void setSgdocumentosList(List<Sgdocumentos> sgdocumentosList) {
        this.sgdocumentosList = sgdocumentosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipoDocumento != null ? idtipoDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgtiposDocumentos)) {
            return false;
        }
        SgtiposDocumentos other = (SgtiposDocumentos) object;
        if ((this.idtipoDocumento == null && other.idtipoDocumento != null) || (this.idtipoDocumento != null && !this.idtipoDocumento.equals(other.idtipoDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgtiposDocumentos[ idtipoDocumento=" + idtipoDocumento + " ]";
    }
    
}
