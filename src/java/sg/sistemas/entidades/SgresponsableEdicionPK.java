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
public class SgresponsableEdicionPK implements Serializable {

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
    @Column(name = "COD_USUARIO")
    private String codUsuario;

    public SgresponsableEdicionPK() {
    }

    public SgresponsableEdicionPK(String idsociedad, String iddocumento, String idtipoDocumento, String codUsuario) {
        this.idsociedad = idsociedad;
        this.iddocumento = iddocumento;
        this.idtipoDocumento = idtipoDocumento;
        this.codUsuario = codUsuario;
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
        hash += (iddocumento != null ? iddocumento.hashCode() : 0);
        hash += (idtipoDocumento != null ? idtipoDocumento.hashCode() : 0);
        hash += (codUsuario != null ? codUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgresponsableEdicionPK)) {
            return false;
        }
        SgresponsableEdicionPK other = (SgresponsableEdicionPK) object;
        if ((this.idsociedad == null && other.idsociedad != null) || (this.idsociedad != null && !this.idsociedad.equals(other.idsociedad))) {
            return false;
        }
        if ((this.iddocumento == null && other.iddocumento != null) || (this.iddocumento != null && !this.iddocumento.equals(other.iddocumento))) {
            return false;
        }
        if ((this.idtipoDocumento == null && other.idtipoDocumento != null) || (this.idtipoDocumento != null && !this.idtipoDocumento.equals(other.idtipoDocumento))) {
            return false;
        }
        if ((this.codUsuario == null && other.codUsuario != null) || (this.codUsuario != null && !this.codUsuario.equals(other.codUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgresponsableEdicionPK[ idsociedad=" + idsociedad + ", iddocumento=" + iddocumento + ", idtipoDocumento=" + idtipoDocumento + ", codUsuario=" + codUsuario + " ]";
    }
    
}
