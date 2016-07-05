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
public class SgcapturaEncuestaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "NUMERO_ENCUESTA")
    private String numeroEncuesta;
    @Basic(optional = false)
    @Column(name = "IDENCUESTA")
    private String idencuesta;

    public SgcapturaEncuestaPK() {
    }

    public SgcapturaEncuestaPK(String numeroEncuesta, String idencuesta) {
        this.numeroEncuesta = numeroEncuesta;
        this.idencuesta = idencuesta;
    }

    public String getNumeroEncuesta() {
        return numeroEncuesta;
    }

    public void setNumeroEncuesta(String numeroEncuesta) {
        this.numeroEncuesta = numeroEncuesta;
    }

    public String getIdencuesta() {
        return idencuesta;
    }

    public void setIdencuesta(String idencuesta) {
        this.idencuesta = idencuesta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numeroEncuesta != null ? numeroEncuesta.hashCode() : 0);
        hash += (idencuesta != null ? idencuesta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgcapturaEncuestaPK)) {
            return false;
        }
        SgcapturaEncuestaPK other = (SgcapturaEncuestaPK) object;
        if ((this.numeroEncuesta == null && other.numeroEncuesta != null) || (this.numeroEncuesta != null && !this.numeroEncuesta.equals(other.numeroEncuesta))) {
            return false;
        }
        if ((this.idencuesta == null && other.idencuesta != null) || (this.idencuesta != null && !this.idencuesta.equals(other.idencuesta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgcapturaEncuestaPK[ numeroEncuesta=" + numeroEncuesta + ", idencuesta=" + idencuesta + " ]";
    }
    
}
