/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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
@Table(name = "sgp_encuesta_detalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgpEncuestaDetalle.findAll", query = "SELECT s FROM SgpEncuestaDetalle s"),
    @NamedQuery(name = "SgpEncuestaDetalle.findByIdencuesta", query = "SELECT s FROM SgpEncuestaDetalle s WHERE s.sgpEncuestaDetallePK.idencuesta = :idencuesta"),
    @NamedQuery(name = "SgpEncuestaDetalle.findByIddetalleEncuesta", query = "SELECT s FROM SgpEncuestaDetalle s WHERE s.sgpEncuestaDetallePK.iddetalleEncuesta = :iddetalleEncuesta"),
    @NamedQuery(name = "SgpEncuestaDetalle.findByPregunta", query = "SELECT s FROM SgpEncuestaDetalle s WHERE s.pregunta = :pregunta")})
public class SgpEncuestaDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgpEncuestaDetallePK sgpEncuestaDetallePK;
    @Basic(optional = false)
    @Column(name = "PREGUNTA")
    private String pregunta;
    @JoinColumn(name = "IDSECCION", referencedColumnName = "IDSECCION")
    @ManyToOne
    private SgpSeccionesEncuesta idseccion;
    @JoinColumn(name = "IDENCUESTA", referencedColumnName = "IDENCUESTA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SgpCabEncuesta sgpCabEncuesta;
    @JoinColumn(name = "IDTIPO_PREGUNTA", referencedColumnName = "IDTIPO_PREGUNTA")
    @ManyToOne
    private SgtipoPregunta idtipoPregunta;

    public SgpEncuestaDetalle() {
    }

    public SgpEncuestaDetalle(SgpEncuestaDetallePK sgpEncuestaDetallePK) {
        this.sgpEncuestaDetallePK = sgpEncuestaDetallePK;
    }

    public SgpEncuestaDetalle(SgpEncuestaDetallePK sgpEncuestaDetallePK, String pregunta) {
        this.sgpEncuestaDetallePK = sgpEncuestaDetallePK;
        this.pregunta = pregunta;
    }

    public SgpEncuestaDetalle(String idencuesta, String iddetalleEncuesta) {
        this.sgpEncuestaDetallePK = new SgpEncuestaDetallePK(idencuesta, iddetalleEncuesta);
    }

    public SgpEncuestaDetallePK getSgpEncuestaDetallePK() {
        return sgpEncuestaDetallePK;
    }

    public void setSgpEncuestaDetallePK(SgpEncuestaDetallePK sgpEncuestaDetallePK) {
        this.sgpEncuestaDetallePK = sgpEncuestaDetallePK;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public SgpSeccionesEncuesta getIdseccion() {
        return idseccion;
    }

    public void setIdseccion(SgpSeccionesEncuesta idseccion) {
        this.idseccion = idseccion;
    }

    public SgpCabEncuesta getSgpCabEncuesta() {
        return sgpCabEncuesta;
    }

    public void setSgpCabEncuesta(SgpCabEncuesta sgpCabEncuesta) {
        this.sgpCabEncuesta = sgpCabEncuesta;
    }

    public SgtipoPregunta getIdtipoPregunta() {
        return idtipoPregunta;
    }

    public void setIdtipoPregunta(SgtipoPregunta idtipoPregunta) {
        this.idtipoPregunta = idtipoPregunta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgpEncuestaDetallePK != null ? sgpEncuestaDetallePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgpEncuestaDetalle)) {
            return false;
        }
        SgpEncuestaDetalle other = (SgpEncuestaDetalle) object;
        if ((this.sgpEncuestaDetallePK == null && other.sgpEncuestaDetallePK != null) || (this.sgpEncuestaDetallePK != null && !this.sgpEncuestaDetallePK.equals(other.sgpEncuestaDetallePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgpEncuestaDetalle[ sgpEncuestaDetallePK=" + sgpEncuestaDetallePK + " ]";
    }
    
}
