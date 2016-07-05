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
import javax.persistence.Lob;
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
@Table(name = "sgtipo_riesgo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgtipoRiesgo.findAll", query = "SELECT s FROM SgtipoRiesgo s"),
    @NamedQuery(name = "SgtipoRiesgo.findByIdtipoRiesgo", query = "SELECT s FROM SgtipoRiesgo s WHERE s.idtipoRiesgo = :idtipoRiesgo"),
    @NamedQuery(name = "SgtipoRiesgo.findByEscala", query = "SELECT s FROM SgtipoRiesgo s WHERE s.escala = :escala")})
public class SgtipoRiesgo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDTIPO_RIESGO")
    private String idtipoRiesgo;
    @Column(name = "ESCALA")
    private String escala;
    @Lob
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(mappedBy = "idtipoRiesgo")
    private List<Sgriesgo> sgriesgoList;

    public SgtipoRiesgo() {
    }

    public SgtipoRiesgo(String idtipoRiesgo) {
        this.idtipoRiesgo = idtipoRiesgo;
    }

    public String getIdtipoRiesgo() {
        return idtipoRiesgo;
    }

    public void setIdtipoRiesgo(String idtipoRiesgo) {
        this.idtipoRiesgo = idtipoRiesgo;
    }

    public String getEscala() {
        return escala;
    }

    public void setEscala(String escala) {
        this.escala = escala;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Sgriesgo> getSgriesgoList() {
        return sgriesgoList;
    }

    public void setSgriesgoList(List<Sgriesgo> sgriesgoList) {
        this.sgriesgoList = sgriesgoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipoRiesgo != null ? idtipoRiesgo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgtipoRiesgo)) {
            return false;
        }
        SgtipoRiesgo other = (SgtipoRiesgo) object;
        if ((this.idtipoRiesgo == null && other.idtipoRiesgo != null) || (this.idtipoRiesgo != null && !this.idtipoRiesgo.equals(other.idtipoRiesgo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgtipoRiesgo[ idtipoRiesgo=" + idtipoRiesgo + " ]";
    }
    
}
