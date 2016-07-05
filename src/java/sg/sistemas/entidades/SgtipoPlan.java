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
@Table(name = "sgtipo_plan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgtipoPlan.findAll", query = "SELECT s FROM SgtipoPlan s"),
    @NamedQuery(name = "SgtipoPlan.findByIdtipoPlan", query = "SELECT s FROM SgtipoPlan s WHERE s.idtipoPlan = :idtipoPlan"),
    @NamedQuery(name = "SgtipoPlan.findByDescripcion", query = "SELECT s FROM SgtipoPlan s WHERE s.descripcion = :descripcion")})
public class SgtipoPlan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDTIPO_PLAN")
    private String idtipoPlan;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(mappedBy = "idtipoPlan")
    private List<SgplanForma> sgplanFormaList;

    public SgtipoPlan() {
    }

    public SgtipoPlan(String idtipoPlan) {
        this.idtipoPlan = idtipoPlan;
    }

    public String getIdtipoPlan() {
        return idtipoPlan;
    }

    public void setIdtipoPlan(String idtipoPlan) {
        this.idtipoPlan = idtipoPlan;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<SgplanForma> getSgplanFormaList() {
        return sgplanFormaList;
    }

    public void setSgplanFormaList(List<SgplanForma> sgplanFormaList) {
        this.sgplanFormaList = sgplanFormaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipoPlan != null ? idtipoPlan.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgtipoPlan)) {
            return false;
        }
        SgtipoPlan other = (SgtipoPlan) object;
        if ((this.idtipoPlan == null && other.idtipoPlan != null) || (this.idtipoPlan != null && !this.idtipoPlan.equals(other.idtipoPlan))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgtipoPlan[ idtipoPlan=" + idtipoPlan + " ]";
    }
    
}
