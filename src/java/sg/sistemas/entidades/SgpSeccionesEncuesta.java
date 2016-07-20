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
@Table(name = "sgp_secciones_encuesta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgpSeccionesEncuesta.findAll", query = "SELECT s FROM SgpSeccionesEncuesta s"),
    @NamedQuery(name = "SgpSeccionesEncuesta.findByIdseccion", query = "SELECT s FROM SgpSeccionesEncuesta s WHERE s.idseccion = :idseccion"),
    @NamedQuery(name = "SgpSeccionesEncuesta.findByDescripcion", query = "SELECT s FROM SgpSeccionesEncuesta s WHERE s.descripcion = :descripcion")})
public class SgpSeccionesEncuesta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDSECCION")
    private String idseccion;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgpSeccionesEncuesta")
    private List<SgpEncuestaSeccion> sgpEncuestaSeccionList;
    @OneToMany(mappedBy = "idseccion")
    private List<SgpEncuestaDetalle> sgpEncuestaDetalleList;

    public SgpSeccionesEncuesta() {
    }

    public SgpSeccionesEncuesta(String idseccion) {
        this.idseccion = idseccion;
    }

    public String getIdseccion() {
        return idseccion;
    }

    public void setIdseccion(String idseccion) {
        this.idseccion = idseccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<SgpEncuestaSeccion> getSgpEncuestaSeccionList() {
        return sgpEncuestaSeccionList;
    }

    public void setSgpEncuestaSeccionList(List<SgpEncuestaSeccion> sgpEncuestaSeccionList) {
        this.sgpEncuestaSeccionList = sgpEncuestaSeccionList;
    }

    @XmlTransient
    public List<SgpEncuestaDetalle> getSgpEncuestaDetalleList() {
        return sgpEncuestaDetalleList;
    }

    public void setSgpEncuestaDetalleList(List<SgpEncuestaDetalle> sgpEncuestaDetalleList) {
        this.sgpEncuestaDetalleList = sgpEncuestaDetalleList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idseccion != null ? idseccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgpSeccionesEncuesta)) {
            return false;
        }
        SgpSeccionesEncuesta other = (SgpSeccionesEncuesta) object;
        if ((this.idseccion == null && other.idseccion != null) || (this.idseccion != null && !this.idseccion.equals(other.idseccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgpSeccionesEncuesta[ idseccion=" + idseccion + " ]";
    }
    
}
