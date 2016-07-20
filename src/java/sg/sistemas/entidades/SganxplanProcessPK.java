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
public class SganxplanProcessPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDSOCIEDAD")
    private String idsociedad;
    @Basic(optional = false)
    @Column(name = "IDTIPO_PDIRECCION")
    private String idtipoPdireccion;
    @Basic(optional = false)
    @Column(name = "IDDETALLE_DIRECCION")
    private String iddetalleDireccion;
    @Basic(optional = false)
    @Column(name = "IDCOD_PROCESO")
    private String idcodProceso;
    @Basic(optional = false)
    @Column(name = "NOMBRE_DOC")
    private String nombreDoc;

    public SganxplanProcessPK() {
    }

    public SganxplanProcessPK(String idsociedad, String idtipoPdireccion, String iddetalleDireccion, String idcodProceso, String nombreDoc) {
        this.idsociedad = idsociedad;
        this.idtipoPdireccion = idtipoPdireccion;
        this.iddetalleDireccion = iddetalleDireccion;
        this.idcodProceso = idcodProceso;
        this.nombreDoc = nombreDoc;
    }

    public String getIdsociedad() {
        return idsociedad;
    }

    public void setIdsociedad(String idsociedad) {
        this.idsociedad = idsociedad;
    }

    public String getIdtipoPdireccion() {
        return idtipoPdireccion;
    }

    public void setIdtipoPdireccion(String idtipoPdireccion) {
        this.idtipoPdireccion = idtipoPdireccion;
    }

    public String getIddetalleDireccion() {
        return iddetalleDireccion;
    }

    public void setIddetalleDireccion(String iddetalleDireccion) {
        this.iddetalleDireccion = iddetalleDireccion;
    }

    public String getIdcodProceso() {
        return idcodProceso;
    }

    public void setIdcodProceso(String idcodProceso) {
        this.idcodProceso = idcodProceso;
    }

    public String getNombreDoc() {
        return nombreDoc;
    }

    public void setNombreDoc(String nombreDoc) {
        this.nombreDoc = nombreDoc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsociedad != null ? idsociedad.hashCode() : 0);
        hash += (idtipoPdireccion != null ? idtipoPdireccion.hashCode() : 0);
        hash += (iddetalleDireccion != null ? iddetalleDireccion.hashCode() : 0);
        hash += (idcodProceso != null ? idcodProceso.hashCode() : 0);
        hash += (nombreDoc != null ? nombreDoc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SganxplanProcessPK)) {
            return false;
        }
        SganxplanProcessPK other = (SganxplanProcessPK) object;
        if ((this.idsociedad == null && other.idsociedad != null) || (this.idsociedad != null && !this.idsociedad.equals(other.idsociedad))) {
            return false;
        }
        if ((this.idtipoPdireccion == null && other.idtipoPdireccion != null) || (this.idtipoPdireccion != null && !this.idtipoPdireccion.equals(other.idtipoPdireccion))) {
            return false;
        }
        if ((this.iddetalleDireccion == null && other.iddetalleDireccion != null) || (this.iddetalleDireccion != null && !this.iddetalleDireccion.equals(other.iddetalleDireccion))) {
            return false;
        }
        if ((this.idcodProceso == null && other.idcodProceso != null) || (this.idcodProceso != null && !this.idcodProceso.equals(other.idcodProceso))) {
            return false;
        }
        if ((this.nombreDoc == null && other.nombreDoc != null) || (this.nombreDoc != null && !this.nombreDoc.equals(other.nombreDoc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SganxplanProcessPK[ idsociedad=" + idsociedad + ", idtipoPdireccion=" + idtipoPdireccion + ", iddetalleDireccion=" + iddetalleDireccion + ", idcodProceso=" + idcodProceso + ", nombreDoc=" + nombreDoc + " ]";
    }
    
}
