/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgcatalogo_indicadores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgcatalogoIndicadores.findAll", query = "SELECT s FROM SgcatalogoIndicadores s"),
    @NamedQuery(name = "SgcatalogoIndicadores.findByIdparametro", query = "SELECT s FROM SgcatalogoIndicadores s WHERE s.idparametro = :idparametro"),
    @NamedQuery(name = "SgcatalogoIndicadores.findByDenominacion", query = "SELECT s FROM SgcatalogoIndicadores s WHERE s.denominacion = :denominacion"),
    @NamedQuery(name = "SgcatalogoIndicadores.findByUnidadMedicion", query = "SELECT s FROM SgcatalogoIndicadores s WHERE s.unidadMedicion = :unidadMedicion"),
    @NamedQuery(name = "SgcatalogoIndicadores.findByIndicador", query = "SELECT s FROM SgcatalogoIndicadores s WHERE s.indicador = :indicador"),
    @NamedQuery(name = "SgcatalogoIndicadores.findByFechaCreacion", query = "SELECT s FROM SgcatalogoIndicadores s WHERE s.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "SgcatalogoIndicadores.findByVigencia", query = "SELECT s FROM SgcatalogoIndicadores s WHERE s.vigencia = :vigencia")})
public class SgcatalogoIndicadores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDPARAMETRO")
    private String idparametro;
    @Column(name = "DENOMINACION")
    private String denominacion;
    @Column(name = "UNIDAD_MEDICION")
    private String unidadMedicion;
    @Column(name = "INDICADOR")
    private String indicador;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "VIGENCIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vigencia;

    public SgcatalogoIndicadores() {
    }

    public SgcatalogoIndicadores(String idparametro) {
        this.idparametro = idparametro;
    }

    public String getIdparametro() {
        return idparametro;
    }

    public void setIdparametro(String idparametro) {
        this.idparametro = idparametro;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getUnidadMedicion() {
        return unidadMedicion;
    }

    public void setUnidadMedicion(String unidadMedicion) {
        this.unidadMedicion = unidadMedicion;
    }

    public String getIndicador() {
        return indicador;
    }

    public void setIndicador(String indicador) {
        this.indicador = indicador;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getVigencia() {
        return vigencia;
    }

    public void setVigencia(Date vigencia) {
        this.vigencia = vigencia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idparametro != null ? idparametro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgcatalogoIndicadores)) {
            return false;
        }
        SgcatalogoIndicadores other = (SgcatalogoIndicadores) object;
        if ((this.idparametro == null && other.idparametro != null) || (this.idparametro != null && !this.idparametro.equals(other.idparametro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgcatalogoIndicadores[ idparametro=" + idparametro + " ]";
    }
    
}
