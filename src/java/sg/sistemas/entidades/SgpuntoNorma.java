/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "sgpunto_norma")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgpuntoNorma.findAll", query = "SELECT s FROM SgpuntoNorma s"),
    @NamedQuery(name = "SgpuntoNorma.findByIdnorma", query = "SELECT s FROM SgpuntoNorma s WHERE s.sgpuntoNormaPK.idnorma = :idnorma"),
    @NamedQuery(name = "SgpuntoNorma.findByIdpuntoNorma", query = "SELECT s FROM SgpuntoNorma s WHERE s.sgpuntoNormaPK.idpuntoNorma = :idpuntoNorma"),
    @NamedQuery(name = "SgpuntoNorma.findByNivel", query = "SELECT s FROM SgpuntoNorma s WHERE s.nivel = :nivel")})
public class SgpuntoNorma implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgpuntoNormaPK sgpuntoNormaPK;
    @Lob
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "NIVEL")
    private String nivel;
    @JoinColumn(name = "IDNORMA", referencedColumnName = "IDNORMA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Sgnorma sgnorma;

    public SgpuntoNorma() {
    }

    public SgpuntoNorma(SgpuntoNormaPK sgpuntoNormaPK) {
        this.sgpuntoNormaPK = sgpuntoNormaPK;
    }

    public SgpuntoNorma(String idnorma, String idpuntoNorma) {
        this.sgpuntoNormaPK = new SgpuntoNormaPK(idnorma, idpuntoNorma);
    }

    public SgpuntoNormaPK getSgpuntoNormaPK() {
        return sgpuntoNormaPK;
    }

    public void setSgpuntoNormaPK(SgpuntoNormaPK sgpuntoNormaPK) {
        this.sgpuntoNormaPK = sgpuntoNormaPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public Sgnorma getSgnorma() {
        return sgnorma;
    }

    public void setSgnorma(Sgnorma sgnorma) {
        this.sgnorma = sgnorma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgpuntoNormaPK != null ? sgpuntoNormaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgpuntoNorma)) {
            return false;
        }
        SgpuntoNorma other = (SgpuntoNorma) object;
        if ((this.sgpuntoNormaPK == null && other.sgpuntoNormaPK != null) || (this.sgpuntoNormaPK != null && !this.sgpuntoNormaPK.equals(other.sgpuntoNormaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgpuntoNorma[ sgpuntoNormaPK=" + sgpuntoNormaPK + " ]";
    }
    
}
