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
@Table(name = "sg_rol")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgRol.findAll", query = "SELECT s FROM SgRol s"),
    @NamedQuery(name = "SgRol.findByIdrol", query = "SELECT s FROM SgRol s WHERE s.idrol = :idrol"),
    @NamedQuery(name = "SgRol.findByNomber", query = "SELECT s FROM SgRol s WHERE s.nomber = :nomber")})
public class SgRol implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDROL")
    private String idrol;
    @Basic(optional = false)
    @Column(name = "NOMBER")
    private String nomber;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgRol")
    private List<SgUsuariorol> sgUsuariorolList;

    public SgRol() {
    }

    public SgRol(String idrol) {
        this.idrol = idrol;
    }

    public SgRol(String idrol, String nomber) {
        this.idrol = idrol;
        this.nomber = nomber;
    }

    public String getIdrol() {
        return idrol;
    }

    public void setIdrol(String idrol) {
        this.idrol = idrol;
    }

    public String getNomber() {
        return nomber;
    }

    public void setNomber(String nomber) {
        this.nomber = nomber;
    }

    @XmlTransient
    public List<SgUsuariorol> getSgUsuariorolList() {
        return sgUsuariorolList;
    }

    public void setSgUsuariorolList(List<SgUsuariorol> sgUsuariorolList) {
        this.sgUsuariorolList = sgUsuariorolList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrol != null ? idrol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgRol)) {
            return false;
        }
        SgRol other = (SgRol) object;
        if ((this.idrol == null && other.idrol != null) || (this.idrol != null && !this.idrol.equals(other.idrol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgRol[ idrol=" + idrol + " ]";
    }
    
}
