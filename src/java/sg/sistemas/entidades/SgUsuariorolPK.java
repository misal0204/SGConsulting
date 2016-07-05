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
public class SgUsuariorolPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "COD_USUARIO")
    private String codUsuario;
    @Basic(optional = false)
    @Column(name = "IDROL")
    private String idrol;

    public SgUsuariorolPK() {
    }

    public SgUsuariorolPK(String codUsuario, String idrol) {
        this.codUsuario = codUsuario;
        this.idrol = idrol;
    }

    public String getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(String codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getIdrol() {
        return idrol;
    }

    public void setIdrol(String idrol) {
        this.idrol = idrol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codUsuario != null ? codUsuario.hashCode() : 0);
        hash += (idrol != null ? idrol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgUsuariorolPK)) {
            return false;
        }
        SgUsuariorolPK other = (SgUsuariorolPK) object;
        if ((this.codUsuario == null && other.codUsuario != null) || (this.codUsuario != null && !this.codUsuario.equals(other.codUsuario))) {
            return false;
        }
        if ((this.idrol == null && other.idrol != null) || (this.idrol != null && !this.idrol.equals(other.idrol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgUsuariorolPK[ codUsuario=" + codUsuario + ", idrol=" + idrol + " ]";
    }
    
}
