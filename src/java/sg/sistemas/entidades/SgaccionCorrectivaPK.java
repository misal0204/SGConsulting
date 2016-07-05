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
public class SgaccionCorrectivaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDSOCIEDAD")
    private String idsociedad;
    @Basic(optional = false)
    @Column(name = "NONC")
    private String nonc;
    @Basic(optional = false)
    @Column(name = "IDCAUSA")
    private String idcausa;
    @Basic(optional = false)
    @Column(name = "IDACCION")
    private String idaccion;

    public SgaccionCorrectivaPK() {
    }

    public SgaccionCorrectivaPK(String idsociedad, String nonc, String idcausa, String idaccion) {
        this.idsociedad = idsociedad;
        this.nonc = nonc;
        this.idcausa = idcausa;
        this.idaccion = idaccion;
    }

    public String getIdsociedad() {
        return idsociedad;
    }

    public void setIdsociedad(String idsociedad) {
        this.idsociedad = idsociedad;
    }

    public String getNonc() {
        return nonc;
    }

    public void setNonc(String nonc) {
        this.nonc = nonc;
    }

    public String getIdcausa() {
        return idcausa;
    }

    public void setIdcausa(String idcausa) {
        this.idcausa = idcausa;
    }

    public String getIdaccion() {
        return idaccion;
    }

    public void setIdaccion(String idaccion) {
        this.idaccion = idaccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsociedad != null ? idsociedad.hashCode() : 0);
        hash += (nonc != null ? nonc.hashCode() : 0);
        hash += (idcausa != null ? idcausa.hashCode() : 0);
        hash += (idaccion != null ? idaccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgaccionCorrectivaPK)) {
            return false;
        }
        SgaccionCorrectivaPK other = (SgaccionCorrectivaPK) object;
        if ((this.idsociedad == null && other.idsociedad != null) || (this.idsociedad != null && !this.idsociedad.equals(other.idsociedad))) {
            return false;
        }
        if ((this.nonc == null && other.nonc != null) || (this.nonc != null && !this.nonc.equals(other.nonc))) {
            return false;
        }
        if ((this.idcausa == null && other.idcausa != null) || (this.idcausa != null && !this.idcausa.equals(other.idcausa))) {
            return false;
        }
        if ((this.idaccion == null && other.idaccion != null) || (this.idaccion != null && !this.idaccion.equals(other.idaccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgaccionCorrectivaPK[ idsociedad=" + idsociedad + ", nonc=" + nonc + ", idcausa=" + idcausa + ", idaccion=" + idaccion + " ]";
    }
    
}
