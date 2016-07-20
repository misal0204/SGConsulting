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
public class SgproveedorEncuestaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDSOCIEDAD")
    private String idsociedad;
    @Basic(optional = false)
    @Column(name = "NOENC")
    private String noenc;
    @Basic(optional = false)
    @Column(name = "IDPROVEEDOR")
    private String idproveedor;

    public SgproveedorEncuestaPK() {
    }

    public SgproveedorEncuestaPK(String idsociedad, String noenc, String idproveedor) {
        this.idsociedad = idsociedad;
        this.noenc = noenc;
        this.idproveedor = idproveedor;
    }

    public String getIdsociedad() {
        return idsociedad;
    }

    public void setIdsociedad(String idsociedad) {
        this.idsociedad = idsociedad;
    }

    public String getNoenc() {
        return noenc;
    }

    public void setNoenc(String noenc) {
        this.noenc = noenc;
    }

    public String getIdproveedor() {
        return idproveedor;
    }

    public void setIdproveedor(String idproveedor) {
        this.idproveedor = idproveedor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsociedad != null ? idsociedad.hashCode() : 0);
        hash += (noenc != null ? noenc.hashCode() : 0);
        hash += (idproveedor != null ? idproveedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgproveedorEncuestaPK)) {
            return false;
        }
        SgproveedorEncuestaPK other = (SgproveedorEncuestaPK) object;
        if ((this.idsociedad == null && other.idsociedad != null) || (this.idsociedad != null && !this.idsociedad.equals(other.idsociedad))) {
            return false;
        }
        if ((this.noenc == null && other.noenc != null) || (this.noenc != null && !this.noenc.equals(other.noenc))) {
            return false;
        }
        if ((this.idproveedor == null && other.idproveedor != null) || (this.idproveedor != null && !this.idproveedor.equals(other.idproveedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgproveedorEncuestaPK[ idsociedad=" + idsociedad + ", noenc=" + noenc + ", idproveedor=" + idproveedor + " ]";
    }
    
}
