/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
@Table(name = "sgsegumiento_riesgo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgsegumientoRiesgo.findAll", query = "SELECT s FROM SgsegumientoRiesgo s"),
    @NamedQuery(name = "SgsegumientoRiesgo.findByIdsociedad", query = "SELECT s FROM SgsegumientoRiesgo s WHERE s.sgsegumientoRiesgoPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgsegumientoRiesgo.findByIdriesgo", query = "SELECT s FROM SgsegumientoRiesgo s WHERE s.sgsegumientoRiesgoPK.idriesgo = :idriesgo"),
    @NamedQuery(name = "SgsegumientoRiesgo.findByIdprocesoRiesgo", query = "SELECT s FROM SgsegumientoRiesgo s WHERE s.sgsegumientoRiesgoPK.idprocesoRiesgo = :idprocesoRiesgo"),
    @NamedQuery(name = "SgsegumientoRiesgo.findByIdprocesos", query = "SELECT s FROM SgsegumientoRiesgo s WHERE s.sgsegumientoRiesgoPK.idprocesos = :idprocesos"),
    @NamedQuery(name = "SgsegumientoRiesgo.findByCodUsuario", query = "SELECT s FROM SgsegumientoRiesgo s WHERE s.sgsegumientoRiesgoPK.codUsuario = :codUsuario"),
    @NamedQuery(name = "SgsegumientoRiesgo.findByFechaRealizado", query = "SELECT s FROM SgsegumientoRiesgo s WHERE s.sgsegumientoRiesgoPK.fechaRealizado = :fechaRealizado")})
public class SgsegumientoRiesgo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgsegumientoRiesgoPK sgsegumientoRiesgoPK;
    @Lob
    @Column(name = "CONCLUSION")
    private String conclusion;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "IDRIESGO", referencedColumnName = "IDRIESGO", insertable = false, updatable = false),
        @JoinColumn(name = "IDPROCESO_RIESGO", referencedColumnName = "IDPROCESO_RIESGO", insertable = false, updatable = false),
        @JoinColumn(name = "IDPROCESOS", referencedColumnName = "IDPROCESOS", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private SgprocesoRiesgo sgprocesoRiesgo;

    public SgsegumientoRiesgo() {
    }

    public SgsegumientoRiesgo(SgsegumientoRiesgoPK sgsegumientoRiesgoPK) {
        this.sgsegumientoRiesgoPK = sgsegumientoRiesgoPK;
    }

    public SgsegumientoRiesgo(String idsociedad, String idriesgo, String idprocesoRiesgo, String idprocesos, String codUsuario, Date fechaRealizado) {
        this.sgsegumientoRiesgoPK = new SgsegumientoRiesgoPK(idsociedad, idriesgo, idprocesoRiesgo, idprocesos, codUsuario, fechaRealizado);
    }

    public SgsegumientoRiesgoPK getSgsegumientoRiesgoPK() {
        return sgsegumientoRiesgoPK;
    }

    public void setSgsegumientoRiesgoPK(SgsegumientoRiesgoPK sgsegumientoRiesgoPK) {
        this.sgsegumientoRiesgoPK = sgsegumientoRiesgoPK;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public SgprocesoRiesgo getSgprocesoRiesgo() {
        return sgprocesoRiesgo;
    }

    public void setSgprocesoRiesgo(SgprocesoRiesgo sgprocesoRiesgo) {
        this.sgprocesoRiesgo = sgprocesoRiesgo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgsegumientoRiesgoPK != null ? sgsegumientoRiesgoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgsegumientoRiesgo)) {
            return false;
        }
        SgsegumientoRiesgo other = (SgsegumientoRiesgo) object;
        if ((this.sgsegumientoRiesgoPK == null && other.sgsegumientoRiesgoPK != null) || (this.sgsegumientoRiesgoPK != null && !this.sgsegumientoRiesgoPK.equals(other.sgsegumientoRiesgoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgsegumientoRiesgo[ sgsegumientoRiesgoPK=" + sgsegumientoRiesgoPK + " ]";
    }
    
}
