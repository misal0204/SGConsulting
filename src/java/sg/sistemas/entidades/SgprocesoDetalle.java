/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgproceso_detalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgprocesoDetalle.findAll", query = "SELECT s FROM SgprocesoDetalle s"),
    @NamedQuery(name = "SgprocesoDetalle.findByIdprocesos", query = "SELECT s FROM SgprocesoDetalle s WHERE s.sgprocesoDetallePK.idprocesos = :idprocesos"),
    @NamedQuery(name = "SgprocesoDetalle.findBySubproceso", query = "SELECT s FROM SgprocesoDetalle s WHERE s.sgprocesoDetallePK.subproceso = :subproceso")})
public class SgprocesoDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgprocesoDetallePK sgprocesoDetallePK;
    @Lob
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgprocesoDetalle")
    private List<SgproccAuditoria> sgproccAuditoriaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgprocesoDetalle")
    private List<Sganexos> sganexosList;
    @JoinColumn(name = "IDPROCESOS", referencedColumnName = "IDPROCESOS", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Sgprocesos sgprocesos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgprocesoDetalle")
    private List<SganexoNoacc> sganexoNoaccList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgprocesoDetalle")
    private List<Sgworkflow> sgworkflowList;

    public SgprocesoDetalle() {
    }

    public SgprocesoDetalle(SgprocesoDetallePK sgprocesoDetallePK) {
        this.sgprocesoDetallePK = sgprocesoDetallePK;
    }

    public SgprocesoDetalle(String idprocesos, String subproceso) {
        this.sgprocesoDetallePK = new SgprocesoDetallePK(idprocesos, subproceso);
    }

    public SgprocesoDetallePK getSgprocesoDetallePK() {
        return sgprocesoDetallePK;
    }

    public void setSgprocesoDetallePK(SgprocesoDetallePK sgprocesoDetallePK) {
        this.sgprocesoDetallePK = sgprocesoDetallePK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<SgproccAuditoria> getSgproccAuditoriaList() {
        return sgproccAuditoriaList;
    }

    public void setSgproccAuditoriaList(List<SgproccAuditoria> sgproccAuditoriaList) {
        this.sgproccAuditoriaList = sgproccAuditoriaList;
    }

    @XmlTransient
    public List<Sganexos> getSganexosList() {
        return sganexosList;
    }

    public void setSganexosList(List<Sganexos> sganexosList) {
        this.sganexosList = sganexosList;
    }

    public Sgprocesos getSgprocesos() {
        return sgprocesos;
    }

    public void setSgprocesos(Sgprocesos sgprocesos) {
        this.sgprocesos = sgprocesos;
    }

    @XmlTransient
    public List<SganexoNoacc> getSganexoNoaccList() {
        return sganexoNoaccList;
    }

    public void setSganexoNoaccList(List<SganexoNoacc> sganexoNoaccList) {
        this.sganexoNoaccList = sganexoNoaccList;
    }

    @XmlTransient
    public List<Sgworkflow> getSgworkflowList() {
        return sgworkflowList;
    }

    public void setSgworkflowList(List<Sgworkflow> sgworkflowList) {
        this.sgworkflowList = sgworkflowList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgprocesoDetallePK != null ? sgprocesoDetallePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgprocesoDetalle)) {
            return false;
        }
        SgprocesoDetalle other = (SgprocesoDetalle) object;
        if ((this.sgprocesoDetallePK == null && other.sgprocesoDetallePK != null) || (this.sgprocesoDetallePK != null && !this.sgprocesoDetallePK.equals(other.sgprocesoDetallePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgprocesoDetalle[ sgprocesoDetallePK=" + sgprocesoDetallePK + " ]";
    }
    
}
