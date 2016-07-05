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
@Table(name = "sgtipoplan_direccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgtipoplanDireccion.findAll", query = "SELECT s FROM SgtipoplanDireccion s"),
    @NamedQuery(name = "SgtipoplanDireccion.findByIdtipoPdireccion", query = "SELECT s FROM SgtipoplanDireccion s WHERE s.idtipoPdireccion = :idtipoPdireccion"),
    @NamedQuery(name = "SgtipoplanDireccion.findByDescripcion", query = "SELECT s FROM SgtipoplanDireccion s WHERE s.descripcion = :descripcion")})
public class SgtipoplanDireccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDTIPO_PDIRECCION")
    private String idtipoPdireccion;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgtipoplanDireccion")
    private List<SgrevisionDireccion> sgrevisionDireccionList;

    public SgtipoplanDireccion() {
    }

    public SgtipoplanDireccion(String idtipoPdireccion) {
        this.idtipoPdireccion = idtipoPdireccion;
    }

    public String getIdtipoPdireccion() {
        return idtipoPdireccion;
    }

    public void setIdtipoPdireccion(String idtipoPdireccion) {
        this.idtipoPdireccion = idtipoPdireccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<SgrevisionDireccion> getSgrevisionDireccionList() {
        return sgrevisionDireccionList;
    }

    public void setSgrevisionDireccionList(List<SgrevisionDireccion> sgrevisionDireccionList) {
        this.sgrevisionDireccionList = sgrevisionDireccionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipoPdireccion != null ? idtipoPdireccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgtipoplanDireccion)) {
            return false;
        }
        SgtipoplanDireccion other = (SgtipoplanDireccion) object;
        if ((this.idtipoPdireccion == null && other.idtipoPdireccion != null) || (this.idtipoPdireccion != null && !this.idtipoPdireccion.equals(other.idtipoPdireccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgtipoplanDireccion[ idtipoPdireccion=" + idtipoPdireccion + " ]";
    }
    
}
