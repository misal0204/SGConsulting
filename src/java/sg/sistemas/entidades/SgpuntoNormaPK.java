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
public class SgpuntoNormaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDNORMA")
    private String idnorma;
    @Basic(optional = false)
    @Column(name = "IDPUNTO_NORMA")
    private String idpuntoNorma;

    public SgpuntoNormaPK() {
    }

    public SgpuntoNormaPK(String idnorma, String idpuntoNorma) {
        this.idnorma = idnorma;
        this.idpuntoNorma = idpuntoNorma;
    }

    public String getIdnorma() {
        return idnorma;
    }

    public void setIdnorma(String idnorma) {
        this.idnorma = idnorma;
    }

    public String getIdpuntoNorma() {
        return idpuntoNorma;
    }

    public void setIdpuntoNorma(String idpuntoNorma) {
        this.idpuntoNorma = idpuntoNorma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idnorma != null ? idnorma.hashCode() : 0);
        hash += (idpuntoNorma != null ? idpuntoNorma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgpuntoNormaPK)) {
            return false;
        }
        SgpuntoNormaPK other = (SgpuntoNormaPK) object;
        if ((this.idnorma == null && other.idnorma != null) || (this.idnorma != null && !this.idnorma.equals(other.idnorma))) {
            return false;
        }
        if ((this.idpuntoNorma == null && other.idpuntoNorma != null) || (this.idpuntoNorma != null && !this.idpuntoNorma.equals(other.idpuntoNorma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgpuntoNormaPK[ idnorma=" + idnorma + ", idpuntoNorma=" + idpuntoNorma + " ]";
    }
    
}
