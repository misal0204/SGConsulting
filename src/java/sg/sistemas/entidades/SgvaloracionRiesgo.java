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
@Table(name = "sgvaloracion_riesgo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgvaloracionRiesgo.findAll", query = "SELECT s FROM SgvaloracionRiesgo s"),
    @NamedQuery(name = "SgvaloracionRiesgo.findByIdvaloracion", query = "SELECT s FROM SgvaloracionRiesgo s WHERE s.idvaloracion = :idvaloracion"),
    @NamedQuery(name = "SgvaloracionRiesgo.findByMinimo", query = "SELECT s FROM SgvaloracionRiesgo s WHERE s.minimo = :minimo"),
    @NamedQuery(name = "SgvaloracionRiesgo.findByMaximo", query = "SELECT s FROM SgvaloracionRiesgo s WHERE s.maximo = :maximo"),
    @NamedQuery(name = "SgvaloracionRiesgo.findByClasificacionRiesgo", query = "SELECT s FROM SgvaloracionRiesgo s WHERE s.clasificacionRiesgo = :clasificacionRiesgo")})
public class SgvaloracionRiesgo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDVALORACION")
    private String idvaloracion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "MINIMO")
    private Double minimo;
    @Column(name = "MAXIMO")
    private Double maximo;
    @Column(name = "CLASIFICACION_RIESGO")
    private String clasificacionRiesgo;
    @OneToMany(mappedBy = "idvaloracion")
    private List<SgprocesoRiesgo> sgprocesoRiesgoList;

    public SgvaloracionRiesgo() {
    }

    public SgvaloracionRiesgo(String idvaloracion) {
        this.idvaloracion = idvaloracion;
    }

    public String getIdvaloracion() {
        return idvaloracion;
    }

    public void setIdvaloracion(String idvaloracion) {
        this.idvaloracion = idvaloracion;
    }

    public Double getMinimo() {
        return minimo;
    }

    public void setMinimo(Double minimo) {
        this.minimo = minimo;
    }

    public Double getMaximo() {
        return maximo;
    }

    public void setMaximo(Double maximo) {
        this.maximo = maximo;
    }

    public String getClasificacionRiesgo() {
        return clasificacionRiesgo;
    }

    public void setClasificacionRiesgo(String clasificacionRiesgo) {
        this.clasificacionRiesgo = clasificacionRiesgo;
    }

    @XmlTransient
    public List<SgprocesoRiesgo> getSgprocesoRiesgoList() {
        return sgprocesoRiesgoList;
    }

    public void setSgprocesoRiesgoList(List<SgprocesoRiesgo> sgprocesoRiesgoList) {
        this.sgprocesoRiesgoList = sgprocesoRiesgoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idvaloracion != null ? idvaloracion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgvaloracionRiesgo)) {
            return false;
        }
        SgvaloracionRiesgo other = (SgvaloracionRiesgo) object;
        if ((this.idvaloracion == null && other.idvaloracion != null) || (this.idvaloracion != null && !this.idvaloracion.equals(other.idvaloracion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgvaloracionRiesgo[ idvaloracion=" + idvaloracion + " ]";
    }
    
}
