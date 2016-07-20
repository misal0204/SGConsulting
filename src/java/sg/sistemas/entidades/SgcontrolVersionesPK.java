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
public class SgcontrolVersionesPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDSOCIEDAD")
    private String idsociedad;
    @Basic(optional = false)
    @Column(name = "IDDOCUMENTO")
    private String iddocumento;
    @Basic(optional = false)
    @Column(name = "IDTIPO_DOCUMENTO")
    private String idtipoDocumento;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;

    public SgcontrolVersionesPK() {
    }

    public SgcontrolVersionesPK(String idsociedad, String iddocumento, String idtipoDocumento, String nombre) {
        this.idsociedad = idsociedad;
        this.iddocumento = iddocumento;
        this.idtipoDocumento = idtipoDocumento;
        this.nombre = nombre;
    }

    public String getIdsociedad() {
        return idsociedad;
    }

    public void setIdsociedad(String idsociedad) {
        this.idsociedad = idsociedad;
    }

    public String getIddocumento() {
        return iddocumento;
    }

    public void setIddocumento(String iddocumento) {
        this.iddocumento = iddocumento;
    }

    public String getIdtipoDocumento() {
        return idtipoDocumento;
    }

    public void setIdtipoDocumento(String idtipoDocumento) {
        this.idtipoDocumento = idtipoDocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsociedad != null ? idsociedad.hashCode() : 0);
        hash += (iddocumento != null ? iddocumento.hashCode() : 0);
        hash += (idtipoDocumento != null ? idtipoDocumento.hashCode() : 0);
        hash += (nombre != null ? nombre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgcontrolVersionesPK)) {
            return false;
        }
        SgcontrolVersionesPK other = (SgcontrolVersionesPK) object;
        if ((this.idsociedad == null && other.idsociedad != null) || (this.idsociedad != null && !this.idsociedad.equals(other.idsociedad))) {
            return false;
        }
        if ((this.iddocumento == null && other.iddocumento != null) || (this.iddocumento != null && !this.iddocumento.equals(other.iddocumento))) {
            return false;
        }
        if ((this.idtipoDocumento == null && other.idtipoDocumento != null) || (this.idtipoDocumento != null && !this.idtipoDocumento.equals(other.idtipoDocumento))) {
            return false;
        }
        if ((this.nombre == null && other.nombre != null) || (this.nombre != null && !this.nombre.equals(other.nombre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgcontrolVersionesPK[ idsociedad=" + idsociedad + ", iddocumento=" + iddocumento + ", idtipoDocumento=" + idtipoDocumento + ", nombre=" + nombre + " ]";
    }
    
}
