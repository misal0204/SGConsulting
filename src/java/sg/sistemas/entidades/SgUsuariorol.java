/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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
@Table(name = "sg_usuariorol")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgUsuariorol.findAll", query = "SELECT s FROM SgUsuariorol s"),
    @NamedQuery(name = "SgUsuariorol.findByCodUsuario", query = "SELECT s FROM SgUsuariorol s WHERE s.sgUsuariorolPK.codUsuario = :codUsuario"),
    @NamedQuery(name = "SgUsuariorol.findByIdrol", query = "SELECT s FROM SgUsuariorol s WHERE s.sgUsuariorolPK.idrol = :idrol"),
    @NamedQuery(name = "SgUsuariorol.findByIdsociedad", query = "SELECT s FROM SgUsuariorol s WHERE s.idsociedad = :idsociedad")})
public class SgUsuariorol implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgUsuariorolPK sgUsuariorolPK;
    @Basic(optional = false)
    @Column(name = "IDSOCIEDAD")
    private String idsociedad;
    @JoinColumn(name = "COD_USUARIO", referencedColumnName = "COD_USUARIO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Sgusuario sgusuario;
    @JoinColumn(name = "IDROL", referencedColumnName = "IDROL", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SgRol sgRol;

    public SgUsuariorol() {
    }

    public SgUsuariorol(SgUsuariorolPK sgUsuariorolPK) {
        this.sgUsuariorolPK = sgUsuariorolPK;
    }

    public SgUsuariorol(SgUsuariorolPK sgUsuariorolPK, String idsociedad) {
        this.sgUsuariorolPK = sgUsuariorolPK;
        this.idsociedad = idsociedad;
    }

    public SgUsuariorol(String codUsuario, String idrol) {
        this.sgUsuariorolPK = new SgUsuariorolPK(codUsuario, idrol);
    }

    public SgUsuariorolPK getSgUsuariorolPK() {
        return sgUsuariorolPK;
    }

    public void setSgUsuariorolPK(SgUsuariorolPK sgUsuariorolPK) {
        this.sgUsuariorolPK = sgUsuariorolPK;
    }

    public String getIdsociedad() {
        return idsociedad;
    }

    public void setIdsociedad(String idsociedad) {
        this.idsociedad = idsociedad;
    }

    public Sgusuario getSgusuario() {
        return sgusuario;
    }

    public void setSgusuario(Sgusuario sgusuario) {
        this.sgusuario = sgusuario;
    }

    public SgRol getSgRol() {
        return sgRol;
    }

    public void setSgRol(SgRol sgRol) {
        this.sgRol = sgRol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgUsuariorolPK != null ? sgUsuariorolPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgUsuariorol)) {
            return false;
        }
        SgUsuariorol other = (SgUsuariorol) object;
        if ((this.sgUsuariorolPK == null && other.sgUsuariorolPK != null) || (this.sgUsuariorolPK != null && !this.sgUsuariorolPK.equals(other.sgUsuariorolPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgUsuariorol[ sgUsuariorolPK=" + sgUsuariorolPK + " ]";
    }
    
}
