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
@Table(name = "sgcriticidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sgcriticidad.findAll", query = "SELECT s FROM Sgcriticidad s"),
    @NamedQuery(name = "Sgcriticidad.findByIdcriticidad", query = "SELECT s FROM Sgcriticidad s WHERE s.idcriticidad = :idcriticidad"),
    @NamedQuery(name = "Sgcriticidad.findByDescripcion", query = "SELECT s FROM Sgcriticidad s WHERE s.descripcion = :descripcion")})
public class Sgcriticidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDCRITICIDAD")
    private String idcriticidad;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(mappedBy = "idcriticidad")
    private List<Sgnc> sgncList;

    public Sgcriticidad() {
    }

    public Sgcriticidad(String idcriticidad) {
        this.idcriticidad = idcriticidad;
    }

    public String getIdcriticidad() {
        return idcriticidad;
    }

    public void setIdcriticidad(String idcriticidad) {
        this.idcriticidad = idcriticidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Sgnc> getSgncList() {
        return sgncList;
    }

    public void setSgncList(List<Sgnc> sgncList) {
        this.sgncList = sgncList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcriticidad != null ? idcriticidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sgcriticidad)) {
            return false;
        }
        Sgcriticidad other = (Sgcriticidad) object;
        if ((this.idcriticidad == null && other.idcriticidad != null) || (this.idcriticidad != null && !this.idcriticidad.equals(other.idcriticidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.Sgcriticidad[ idcriticidad=" + idcriticidad + " ]";
    }
    
}
