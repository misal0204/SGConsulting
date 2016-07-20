/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Misael
 */
@Embeddable
public class SgpEncuestaSeccionPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDENCUESTA")
    private String idencuesta;
    @Basic(optional = false)
    @Column(name = "IDDETALLE_ENCUESTA")
    private String iddetalleEncuesta;
    @Basic(optional = false)
    @Column(name = "IDSECCION")
    private String idseccion;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;

    public SgpEncuestaSeccionPK() {
    }

    public SgpEncuestaSeccionPK(String idencuesta, String iddetalleEncuesta, String idseccion, String descripcion) {
        this.idencuesta = idencuesta;
        this.iddetalleEncuesta = iddetalleEncuesta;
        this.idseccion = idseccion;
        this.descripcion = descripcion;
    }

    public String getIdencuesta() {
        return idencuesta;
    }

    public void setIdencuesta(String idencuesta) {
        this.idencuesta = idencuesta;
    }

    public String getIddetalleEncuesta() {
        return iddetalleEncuesta;
    }

    public void setIddetalleEncuesta(String iddetalleEncuesta) {
        this.iddetalleEncuesta = iddetalleEncuesta;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idencuesta != null ? idencuesta.hashCode() : 0);
        hash += (iddetalleEncuesta != null ? iddetalleEncuesta.hashCode() : 0);
        hash += (idseccion != null ? idseccion.hashCode() : 0);
        hash += (descripcion != null ? descripcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgpEncuestaSeccionPK)) {
            return false;
        }
        SgpEncuestaSeccionPK other = (SgpEncuestaSeccionPK) object;
        if ((this.idencuesta == null && other.idencuesta != null) || (this.idencuesta != null && !this.idencuesta.equals(other.idencuesta))) {
            return false;
        }
        if ((this.iddetalleEncuesta == null && other.iddetalleEncuesta != null) || (this.iddetalleEncuesta != null && !this.iddetalleEncuesta.equals(other.iddetalleEncuesta))) {
            return false;
        }
        if ((this.idseccion == null && other.idseccion != null) || (this.idseccion != null && !this.idseccion.equals(other.idseccion))) {
            return false;
        }
        if ((this.descripcion == null && other.descripcion != null) || (this.descripcion != null && !this.descripcion.equals(other.descripcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgpEncuestaSeccionPK[ idencuesta=" + idencuesta + ", iddetalleEncuesta=" + iddetalleEncuesta + ", idseccion=" + idseccion + ", descripcion=" + descripcion + " ]";
    }
    
}
