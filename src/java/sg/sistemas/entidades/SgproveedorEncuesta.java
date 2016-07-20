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
@Table(name = "sgproveedor_encuesta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgproveedorEncuesta.findAll", query = "SELECT s FROM SgproveedorEncuesta s"),
    @NamedQuery(name = "SgproveedorEncuesta.findByIdsociedad", query = "SELECT s FROM SgproveedorEncuesta s WHERE s.sgproveedorEncuestaPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgproveedorEncuesta.findByNoenc", query = "SELECT s FROM SgproveedorEncuesta s WHERE s.sgproveedorEncuestaPK.noenc = :noenc"),
    @NamedQuery(name = "SgproveedorEncuesta.findByIdproveedor", query = "SELECT s FROM SgproveedorEncuesta s WHERE s.sgproveedorEncuestaPK.idproveedor = :idproveedor")})
public class SgproveedorEncuesta implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgproveedorEncuestaPK sgproveedorEncuestaPK;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "NOENC", referencedColumnName = "NOENC", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private SgcabEncuesta sgcabEncuesta;

    public SgproveedorEncuesta() {
    }

    public SgproveedorEncuesta(SgproveedorEncuestaPK sgproveedorEncuestaPK) {
        this.sgproveedorEncuestaPK = sgproveedorEncuestaPK;
    }

    public SgproveedorEncuesta(String idsociedad, String noenc, String idproveedor) {
        this.sgproveedorEncuestaPK = new SgproveedorEncuestaPK(idsociedad, noenc, idproveedor);
    }

    public SgproveedorEncuestaPK getSgproveedorEncuestaPK() {
        return sgproveedorEncuestaPK;
    }

    public void setSgproveedorEncuestaPK(SgproveedorEncuestaPK sgproveedorEncuestaPK) {
        this.sgproveedorEncuestaPK = sgproveedorEncuestaPK;
    }

    public SgcabEncuesta getSgcabEncuesta() {
        return sgcabEncuesta;
    }

    public void setSgcabEncuesta(SgcabEncuesta sgcabEncuesta) {
        this.sgcabEncuesta = sgcabEncuesta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgproveedorEncuestaPK != null ? sgproveedorEncuestaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgproveedorEncuesta)) {
            return false;
        }
        SgproveedorEncuesta other = (SgproveedorEncuesta) object;
        if ((this.sgproveedorEncuestaPK == null && other.sgproveedorEncuestaPK != null) || (this.sgproveedorEncuestaPK != null && !this.sgproveedorEncuestaPK.equals(other.sgproveedorEncuestaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgproveedorEncuesta[ sgproveedorEncuestaPK=" + sgproveedorEncuestaPK + " ]";
    }
    
}
