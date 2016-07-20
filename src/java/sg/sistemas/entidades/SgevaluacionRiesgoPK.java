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
import javax.persistence.Lob;

/**
 *
 * @author Misael
 */
@Embeddable
public class SgevaluacionRiesgoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDSOCIEDAD")
    private String idsociedad;
    @Basic(optional = false)
    @Column(name = "IDRIESGO")
    private String idriesgo;
    @Basic(optional = false)
    @Lob
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "COD_USUARIO")
    private String codUsuario;

    public SgevaluacionRiesgoPK() {
    }

    public SgevaluacionRiesgoPK(String idsociedad, String idriesgo, String descripcion, String codUsuario) {
        this.idsociedad = idsociedad;
        this.idriesgo = idriesgo;
        this.descripcion = descripcion;
        this.codUsuario = codUsuario;
    }

    public String getIdsociedad() {
        return idsociedad;
    }

    public void setIdsociedad(String idsociedad) {
        this.idsociedad = idsociedad;
    }

    public String getIdriesgo() {
        return idriesgo;
    }

    public void setIdriesgo(String idriesgo) {
        this.idriesgo = idriesgo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(String codUsuario) {
        this.codUsuario = codUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsociedad != null ? idsociedad.hashCode() : 0);
        hash += (idriesgo != null ? idriesgo.hashCode() : 0);
        hash += (descripcion != null ? descripcion.hashCode() : 0);
        hash += (codUsuario != null ? codUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgevaluacionRiesgoPK)) {
            return false;
        }
        SgevaluacionRiesgoPK other = (SgevaluacionRiesgoPK) object;
        if ((this.idsociedad == null && other.idsociedad != null) || (this.idsociedad != null && !this.idsociedad.equals(other.idsociedad))) {
            return false;
        }
        if ((this.idriesgo == null && other.idriesgo != null) || (this.idriesgo != null && !this.idriesgo.equals(other.idriesgo))) {
            return false;
        }
        if ((this.descripcion == null && other.descripcion != null) || (this.descripcion != null && !this.descripcion.equals(other.descripcion))) {
            return false;
        }
        if ((this.codUsuario == null && other.codUsuario != null) || (this.codUsuario != null && !this.codUsuario.equals(other.codUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgevaluacionRiesgoPK[ idsociedad=" + idsociedad + ", idriesgo=" + idriesgo + ", descripcion=" + descripcion + ", codUsuario=" + codUsuario + " ]";
    }
    
}
