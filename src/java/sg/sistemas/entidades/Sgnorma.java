/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgnorma")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sgnorma.findAll", query = "SELECT s FROM Sgnorma s"),
    @NamedQuery(name = "Sgnorma.findByIdnorma", query = "SELECT s FROM Sgnorma s WHERE s.idnorma = :idnorma"),
    @NamedQuery(name = "Sgnorma.findByDescripcion", query = "SELECT s FROM Sgnorma s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "Sgnorma.findByVersion", query = "SELECT s FROM Sgnorma s WHERE s.version = :version"),
    @NamedQuery(name = "Sgnorma.findByVigente", query = "SELECT s FROM Sgnorma s WHERE s.vigente = :vigente"),
    @NamedQuery(name = "Sgnorma.findByFechaFin", query = "SELECT s FROM Sgnorma s WHERE s.fechaFin = :fechaFin")})
public class Sgnorma implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDNORMA")
    private String idnorma;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "VERSION")
    private String version;
    @Column(name = "VIGENTE")
    private String vigente;
    @Column(name = "FECHA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgnorma")
    private List<SgpuntoNorma> sgpuntoNormaList;

    public Sgnorma() {
    }

    public Sgnorma(String idnorma) {
        this.idnorma = idnorma;
    }

    public String getIdnorma() {
        return idnorma;
    }

    public void setIdnorma(String idnorma) {
        this.idnorma = idnorma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVigente() {
        return vigente;
    }

    public void setVigente(String vigente) {
        this.vigente = vigente;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    @XmlTransient
    public List<SgpuntoNorma> getSgpuntoNormaList() {
        return sgpuntoNormaList;
    }

    public void setSgpuntoNormaList(List<SgpuntoNorma> sgpuntoNormaList) {
        this.sgpuntoNormaList = sgpuntoNormaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idnorma != null ? idnorma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sgnorma)) {
            return false;
        }
        Sgnorma other = (Sgnorma) object;
        if ((this.idnorma == null && other.idnorma != null) || (this.idnorma != null && !this.idnorma.equals(other.idnorma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.Sgnorma[ idnorma=" + idnorma + " ]";
    }
    
}
