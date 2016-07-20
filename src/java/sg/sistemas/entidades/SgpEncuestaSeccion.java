/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgp_encuesta_seccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgpEncuestaSeccion.findAll", query = "SELECT s FROM SgpEncuestaSeccion s"),
    @NamedQuery(name = "SgpEncuestaSeccion.findByIdencuesta", query = "SELECT s FROM SgpEncuestaSeccion s WHERE s.sgpEncuestaSeccionPK.idencuesta = :idencuesta"),
    @NamedQuery(name = "SgpEncuestaSeccion.findByIddetalleEncuesta", query = "SELECT s FROM SgpEncuestaSeccion s WHERE s.sgpEncuestaSeccionPK.iddetalleEncuesta = :iddetalleEncuesta"),
    @NamedQuery(name = "SgpEncuestaSeccion.findByIdseccion", query = "SELECT s FROM SgpEncuestaSeccion s WHERE s.sgpEncuestaSeccionPK.idseccion = :idseccion"),
    @NamedQuery(name = "SgpEncuestaSeccion.findByDescripcion", query = "SELECT s FROM SgpEncuestaSeccion s WHERE s.sgpEncuestaSeccionPK.descripcion = :descripcion")})
public class SgpEncuestaSeccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgpEncuestaSeccionPK sgpEncuestaSeccionPK;
    @JoinColumn(name = "IDSECCION", referencedColumnName = "IDSECCION", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SgpSeccionesEncuesta sgpSeccionesEncuesta;
    @JoinColumns({
        @JoinColumn(name = "IDENCUESTA", referencedColumnName = "IDENCUESTA", insertable = false, updatable = false),
        @JoinColumn(name = "IDDETALLE_ENCUESTA", referencedColumnName = "IDDETALLE_ENCUESTA", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private SgpEncuestaDetalle sgpEncuestaDetalle;

    public SgpEncuestaSeccion() {
    }

    public SgpEncuestaSeccion(SgpEncuestaSeccionPK sgpEncuestaSeccionPK) {
        this.sgpEncuestaSeccionPK = sgpEncuestaSeccionPK;
    }

    public SgpEncuestaSeccion(String idencuesta, String iddetalleEncuesta, String idseccion, String descripcion) {
        this.sgpEncuestaSeccionPK = new SgpEncuestaSeccionPK(idencuesta, iddetalleEncuesta, idseccion, descripcion);
    }

    public SgpEncuestaSeccionPK getSgpEncuestaSeccionPK() {
        return sgpEncuestaSeccionPK;
    }

    public void setSgpEncuestaSeccionPK(SgpEncuestaSeccionPK sgpEncuestaSeccionPK) {
        this.sgpEncuestaSeccionPK = sgpEncuestaSeccionPK;
    }

    public SgpSeccionesEncuesta getSgpSeccionesEncuesta() {
        return sgpSeccionesEncuesta;
    }

    public void setSgpSeccionesEncuesta(SgpSeccionesEncuesta sgpSeccionesEncuesta) {
        this.sgpSeccionesEncuesta = sgpSeccionesEncuesta;
    }

    public SgpEncuestaDetalle getSgpEncuestaDetalle() {
        return sgpEncuestaDetalle;
    }

    public void setSgpEncuestaDetalle(SgpEncuestaDetalle sgpEncuestaDetalle) {
        this.sgpEncuestaDetalle = sgpEncuestaDetalle;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgpEncuestaSeccionPK != null ? sgpEncuestaSeccionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgpEncuestaSeccion)) {
            return false;
        }
        SgpEncuestaSeccion other = (SgpEncuestaSeccion) object;
        if ((this.sgpEncuestaSeccionPK == null && other.sgpEncuestaSeccionPK != null) || (this.sgpEncuestaSeccionPK != null && !this.sgpEncuestaSeccionPK.equals(other.sgpEncuestaSeccionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgpEncuestaSeccion[ sgpEncuestaSeccionPK=" + sgpEncuestaSeccionPK + " ]";
    }
    
}
