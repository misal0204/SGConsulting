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
public class SgpEncuestaDetallePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDENCUESTA")
    private String idencuesta;
    @Basic(optional = false)
    @Column(name = "IDDETALLE_ENCUESTA")
    private String iddetalleEncuesta;

    public SgpEncuestaDetallePK() {
    }

    public SgpEncuestaDetallePK(String idencuesta, String iddetalleEncuesta) {
        this.idencuesta = idencuesta;
        this.iddetalleEncuesta = iddetalleEncuesta;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idencuesta != null ? idencuesta.hashCode() : 0);
        hash += (iddetalleEncuesta != null ? iddetalleEncuesta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgpEncuestaDetallePK)) {
            return false;
        }
        SgpEncuestaDetallePK other = (SgpEncuestaDetallePK) object;
        if ((this.idencuesta == null && other.idencuesta != null) || (this.idencuesta != null && !this.idencuesta.equals(other.idencuesta))) {
            return false;
        }
        if ((this.iddetalleEncuesta == null && other.iddetalleEncuesta != null) || (this.iddetalleEncuesta != null && !this.iddetalleEncuesta.equals(other.iddetalleEncuesta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgpEncuestaDetallePK[ idencuesta=" + idencuesta + ", iddetalleEncuesta=" + iddetalleEncuesta + " ]";
    }
    
}
