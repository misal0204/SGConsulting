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
public class SgrevisionDireccionPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDSOCIEDAD")
    private String idsociedad;
    @Basic(optional = false)
    @Column(name = "IDPLAN_DIRECCION")
    private String idplanDireccion;
    @Basic(optional = false)
    @Column(name = "IDTIPO_PDIRECCION")
    private String idtipoPdireccion;

    public SgrevisionDireccionPK() {
    }

    public SgrevisionDireccionPK(String idsociedad, String idplanDireccion, String idtipoPdireccion) {
        this.idsociedad = idsociedad;
        this.idplanDireccion = idplanDireccion;
        this.idtipoPdireccion = idtipoPdireccion;
    }

    public String getIdsociedad() {
        return idsociedad;
    }

    public void setIdsociedad(String idsociedad) {
        this.idsociedad = idsociedad;
    }

    public String getIdplanDireccion() {
        return idplanDireccion;
    }

    public void setIdplanDireccion(String idplanDireccion) {
        this.idplanDireccion = idplanDireccion;
    }

    public String getIdtipoPdireccion() {
        return idtipoPdireccion;
    }

    public void setIdtipoPdireccion(String idtipoPdireccion) {
        this.idtipoPdireccion = idtipoPdireccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsociedad != null ? idsociedad.hashCode() : 0);
        hash += (idplanDireccion != null ? idplanDireccion.hashCode() : 0);
        hash += (idtipoPdireccion != null ? idtipoPdireccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgrevisionDireccionPK)) {
            return false;
        }
        SgrevisionDireccionPK other = (SgrevisionDireccionPK) object;
        if ((this.idsociedad == null && other.idsociedad != null) || (this.idsociedad != null && !this.idsociedad.equals(other.idsociedad))) {
            return false;
        }
        if ((this.idplanDireccion == null && other.idplanDireccion != null) || (this.idplanDireccion != null && !this.idplanDireccion.equals(other.idplanDireccion))) {
            return false;
        }
        if ((this.idtipoPdireccion == null && other.idtipoPdireccion != null) || (this.idtipoPdireccion != null && !this.idtipoPdireccion.equals(other.idtipoPdireccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgrevisionDireccionPK[ idsociedad=" + idsociedad + ", idplanDireccion=" + idplanDireccion + ", idtipoPdireccion=" + idtipoPdireccion + " ]";
    }
    
}
