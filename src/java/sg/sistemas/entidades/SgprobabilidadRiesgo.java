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
@Table(name = "sgprobabilidad_riesgo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgprobabilidadRiesgo.findAll", query = "SELECT s FROM SgprobabilidadRiesgo s"),
    @NamedQuery(name = "SgprobabilidadRiesgo.findByIdprobabilidad", query = "SELECT s FROM SgprobabilidadRiesgo s WHERE s.idprobabilidad = :idprobabilidad"),
    @NamedQuery(name = "SgprobabilidadRiesgo.findByEscala", query = "SELECT s FROM SgprobabilidadRiesgo s WHERE s.escala = :escala")})
public class SgprobabilidadRiesgo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDPROBABILIDAD")
    private String idprobabilidad;
    @Column(name = "ESCALA")
    private String escala;
    @Lob
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(mappedBy = "idprobabilidad")
    private List<Sgriesgo> sgriesgoList;

    public SgprobabilidadRiesgo() {
    }

    public SgprobabilidadRiesgo(String idprobabilidad) {
        this.idprobabilidad = idprobabilidad;
    }

    public String getIdprobabilidad() {
        return idprobabilidad;
    }

    public void setIdprobabilidad(String idprobabilidad) {
        this.idprobabilidad = idprobabilidad;
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
        hash += (idprobabilidad != null ? idprobabilidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgprobabilidadRiesgo)) {
            return false;
        }
        SgprobabilidadRiesgo other = (SgprobabilidadRiesgo) object;
        if ((this.idprobabilidad == null && other.idprobabilidad != null) || (this.idprobabilidad != null && !this.idprobabilidad.equals(other.idprobabilidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgprobabilidadRiesgo[ idprobabilidad=" + idprobabilidad + " ]";
    }
    
}
