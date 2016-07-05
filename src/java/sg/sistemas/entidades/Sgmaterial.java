/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "sgmaterial")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sgmaterial.findAll", query = "SELECT s FROM Sgmaterial s"),
    @NamedQuery(name = "Sgmaterial.findByIdmaterial", query = "SELECT s FROM Sgmaterial s WHERE s.idmaterial = :idmaterial"),
    @NamedQuery(name = "Sgmaterial.findByMaterial", query = "SELECT s FROM Sgmaterial s WHERE s.material = :material"),
    @NamedQuery(name = "Sgmaterial.findByDescripcion", query = "SELECT s FROM Sgmaterial s WHERE s.descripcion = :descripcion")})
public class Sgmaterial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDMATERIAL")
    private String idmaterial;
    @Column(name = "MATERIAL")
    private String material;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(mappedBy = "idmaterial")
    private List<SgprocesosRe> sgprocesosReList;

    public Sgmaterial() {
    }

    public Sgmaterial(String idmaterial) {
        this.idmaterial = idmaterial;
    }

    public String getIdmaterial() {
        return idmaterial;
    }

    public void setIdmaterial(String idmaterial) {
        this.idmaterial = idmaterial;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<SgprocesosRe> getSgprocesosReList() {
        return sgprocesosReList;
    }

    public void setSgprocesosReList(List<SgprocesosRe> sgprocesosReList) {
        this.sgprocesosReList = sgprocesosReList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmaterial != null ? idmaterial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sgmaterial)) {
            return false;
        }
        Sgmaterial other = (Sgmaterial) object;
        if ((this.idmaterial == null && other.idmaterial != null) || (this.idmaterial != null && !this.idmaterial.equals(other.idmaterial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.Sgmaterial[ idmaterial=" + idmaterial + " ]";
    }
    
}
