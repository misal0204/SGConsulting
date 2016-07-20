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
@Table(name = "sgdepartamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sgdepartamento.findAll", query = "SELECT s FROM Sgdepartamento s"),
    @NamedQuery(name = "Sgdepartamento.findByIddept", query = "SELECT s FROM Sgdepartamento s WHERE s.iddept = :iddept"),
    @NamedQuery(name = "Sgdepartamento.findByDescripcion", query = "SELECT s FROM Sgdepartamento s WHERE s.descripcion = :descripcion")})
public class Sgdepartamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDDEPT")
    private String iddept;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinTable(name = "sgdepartamento_nc", joinColumns = {
        @JoinColumn(name = "IDDEPT", referencedColumnName = "IDDEPT")}, inverseJoinColumns = {
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD"),
        @JoinColumn(name = "NONC", referencedColumnName = "NONC")})
    @ManyToMany
    private List<Sgnc> sgncList;
    @JoinTable(name = "sgdetp_user", joinColumns = {
        @JoinColumn(name = "IDDEPT", referencedColumnName = "IDDEPT")}, inverseJoinColumns = {
        @JoinColumn(name = "COD_USUARIO", referencedColumnName = "COD_USUARIO")})
    @ManyToMany
    private List<Sgusuario> sgusuarioList;

    public Sgdepartamento() {
    }

    public Sgdepartamento(String iddept) {
        this.iddept = iddept;
    }

    public String getIddept() {
        return iddept;
    }

    public void setIddept(String iddept) {
        this.iddept = iddept;
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
    public List<Sgusuario> getSgusuarioList() {
        return sgusuarioList;
    }

    public void setSgusuarioList(List<Sgusuario> sgusuarioList) {
        this.sgusuarioList = sgusuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddept != null ? iddept.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sgdepartamento)) {
            return false;
        }
        Sgdepartamento other = (Sgdepartamento) object;
        if ((this.iddept == null && other.iddept != null) || (this.iddept != null && !this.iddept.equals(other.iddept))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.Sgdepartamento[ iddept=" + iddept + " ]";
    }
    
}
