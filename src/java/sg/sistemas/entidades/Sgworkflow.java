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
@Table(name = "sgworkflow")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sgworkflow.findAll", query = "SELECT s FROM Sgworkflow s"),
    @NamedQuery(name = "Sgworkflow.findByIdsociedad", query = "SELECT s FROM Sgworkflow s WHERE s.sgworkflowPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "Sgworkflow.findByIdprocesos", query = "SELECT s FROM Sgworkflow s WHERE s.sgworkflowPK.idprocesos = :idprocesos"),
    @NamedQuery(name = "Sgworkflow.findBySubproceso", query = "SELECT s FROM Sgworkflow s WHERE s.sgworkflowPK.subproceso = :subproceso"),
    @NamedQuery(name = "Sgworkflow.findByIdtarea", query = "SELECT s FROM Sgworkflow s WHERE s.idtarea = :idtarea"),
    @NamedQuery(name = "Sgworkflow.findBySubprocesoAnterior", query = "SELECT s FROM Sgworkflow s WHERE s.subprocesoAnterior = :subprocesoAnterior"),
    @NamedQuery(name = "Sgworkflow.findBySubprocesoSiguiente", query = "SELECT s FROM Sgworkflow s WHERE s.subprocesoSiguiente = :subprocesoSiguiente")})
public class Sgworkflow implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgworkflowPK sgworkflowPK;
    @Column(name = "IDTAREA")
    private String idtarea;
    @Column(name = "SUBPROCESO_ANTERIOR")
    private String subprocesoAnterior;
    @Column(name = "SUBPROCESO_SIGUIENTE")
    private String subprocesoSiguiente;
    @JoinColumns({
        @JoinColumn(name = "IDPROCESOS", referencedColumnName = "IDPROCESOS", insertable = false, updatable = false),
        @JoinColumn(name = "SUBPROCESO", referencedColumnName = "SUBPROCESO", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private SgprocesoDetalle sgprocesoDetalle;

    public Sgworkflow() {
    }

    public Sgworkflow(SgworkflowPK sgworkflowPK) {
        this.sgworkflowPK = sgworkflowPK;
    }

    public Sgworkflow(String idsociedad, String idprocesos, String subproceso) {
        this.sgworkflowPK = new SgworkflowPK(idsociedad, idprocesos, subproceso);
    }

    public SgworkflowPK getSgworkflowPK() {
        return sgworkflowPK;
    }

    public void setSgworkflowPK(SgworkflowPK sgworkflowPK) {
        this.sgworkflowPK = sgworkflowPK;
    }

    public String getIdtarea() {
        return idtarea;
    }

    public void setIdtarea(String idtarea) {
        this.idtarea = idtarea;
    }

    public String getSubprocesoAnterior() {
        return subprocesoAnterior;
    }

    public void setSubprocesoAnterior(String subprocesoAnterior) {
        this.subprocesoAnterior = subprocesoAnterior;
    }

    public String getSubprocesoSiguiente() {
        return subprocesoSiguiente;
    }

    public void setSubprocesoSiguiente(String subprocesoSiguiente) {
        this.subprocesoSiguiente = subprocesoSiguiente;
    }

    public SgprocesoDetalle getSgprocesoDetalle() {
        return sgprocesoDetalle;
    }

    public void setSgprocesoDetalle(SgprocesoDetalle sgprocesoDetalle) {
        this.sgprocesoDetalle = sgprocesoDetalle;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgworkflowPK != null ? sgworkflowPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sgworkflow)) {
            return false;
        }
        Sgworkflow other = (Sgworkflow) object;
        if ((this.sgworkflowPK == null && other.sgworkflowPK != null) || (this.sgworkflowPK != null && !this.sgworkflowPK.equals(other.sgworkflowPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.Sgworkflow[ sgworkflowPK=" + sgworkflowPK + " ]";
    }
    
}
