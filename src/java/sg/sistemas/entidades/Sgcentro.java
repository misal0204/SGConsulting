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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgcentro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sgcentro.findAll", query = "SELECT s FROM Sgcentro s"),
    @NamedQuery(name = "Sgcentro.findByIdcentro", query = "SELECT s FROM Sgcentro s WHERE s.idcentro = :idcentro"),
    @NamedQuery(name = "Sgcentro.findByDescripcion", query = "SELECT s FROM Sgcentro s WHERE s.descripcion = :descripcion")})
public class Sgcentro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDCENTRO")
    private String idcentro;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinTable(name = "sgdetcentro", joinColumns = {
        @JoinColumn(name = "IDCENTRO", referencedColumnName = "IDCENTRO")}, inverseJoinColumns = {
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD"),
        @JoinColumn(name = "NONC", referencedColumnName = "NONC")})
    @ManyToMany
    private List<Sgnc> sgncList;
    @JoinTable(name = "sgsociedad_detalle", joinColumns = {
        @JoinColumn(name = "IDCENTRO", referencedColumnName = "IDCENTRO")}, inverseJoinColumns = {
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD")})
    @ManyToMany
    private List<Sgsociedad> sgsociedadList;

    public Sgcentro() {
    }

    public Sgcentro(String idcentro) {
        this.idcentro = idcentro;
    }

    public String getIdcentro() {
        return idcentro;
    }

    public void setIdcentro(String idcentro) {
        this.idcentro = idcentro;
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

    @XmlTransient
    public List<Sgsociedad> getSgsociedadList() {
        return sgsociedadList;
    }

    public void setSgsociedadList(List<Sgsociedad> sgsociedadList) {
        this.sgsociedadList = sgsociedadList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcentro != null ? idcentro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sgcentro)) {
            return false;
        }
        Sgcentro other = (Sgcentro) object;
        if ((this.idcentro == null && other.idcentro != null) || (this.idcentro != null && !this.idcentro.equals(other.idcentro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.Sgcentro[ idcentro=" + idcentro + " ]";
    }
    
}
