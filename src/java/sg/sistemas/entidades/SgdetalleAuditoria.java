/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
@Table(name = "sgdetalle_auditoria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgdetalleAuditoria.findAll", query = "SELECT s FROM SgdetalleAuditoria s"),
    @NamedQuery(name = "SgdetalleAuditoria.findByIdsociedad", query = "SELECT s FROM SgdetalleAuditoria s WHERE s.sgdetalleAuditoriaPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgdetalleAuditoria.findByIdplanAud", query = "SELECT s FROM SgdetalleAuditoria s WHERE s.sgdetalleAuditoriaPK.idplanAud = :idplanAud"),
    @NamedQuery(name = "SgdetalleAuditoria.findByIddetalleAud", query = "SELECT s FROM SgdetalleAuditoria s WHERE s.sgdetalleAuditoriaPK.iddetalleAud = :iddetalleAud"),
    @NamedQuery(name = "SgdetalleAuditoria.findByIdnorma", query = "SELECT s FROM SgdetalleAuditoria s WHERE s.idnorma = :idnorma"),
    @NamedQuery(name = "SgdetalleAuditoria.findByDescripcion", query = "SELECT s FROM SgdetalleAuditoria s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "SgdetalleAuditoria.findByEstado", query = "SELECT s FROM SgdetalleAuditoria s WHERE s.estado = :estado")})
public class SgdetalleAuditoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgdetalleAuditoriaPK sgdetalleAuditoriaPK;
    @Basic(optional = false)
    @Column(name = "IDNORMA")
    private String idnorma;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "IDPLAN_AUD", referencedColumnName = "IDPLAN_AUD", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private SgplanAuditoria sgplanAuditoria;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgdetalleAuditoria")
    private List<SgprogAuditoria> sgprogAuditoriaList;

    public SgdetalleAuditoria() {
    }

    public SgdetalleAuditoria(SgdetalleAuditoriaPK sgdetalleAuditoriaPK) {
        this.sgdetalleAuditoriaPK = sgdetalleAuditoriaPK;
    }

    public SgdetalleAuditoria(SgdetalleAuditoriaPK sgdetalleAuditoriaPK, String idnorma) {
        this.sgdetalleAuditoriaPK = sgdetalleAuditoriaPK;
        this.idnorma = idnorma;
    }

    public SgdetalleAuditoria(String idsociedad, String idplanAud, String iddetalleAud) {
        this.sgdetalleAuditoriaPK = new SgdetalleAuditoriaPK(idsociedad, idplanAud, iddetalleAud);
    }

    public SgdetalleAuditoriaPK getSgdetalleAuditoriaPK() {
        return sgdetalleAuditoriaPK;
    }

    public void setSgdetalleAuditoriaPK(SgdetalleAuditoriaPK sgdetalleAuditoriaPK) {
        this.sgdetalleAuditoriaPK = sgdetalleAuditoriaPK;
    }

    public String getIdnorma() {
        return idnorma;
    }

    public void setIdnorma(String idnorma) {
        this.idnorma = idnorma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public SgplanAuditoria getSgplanAuditoria() {
        return sgplanAuditoria;
    }

    public void setSgplanAuditoria(SgplanAuditoria sgplanAuditoria) {
        this.sgplanAuditoria = sgplanAuditoria;
    }

    @XmlTransient
    public List<SgprogAuditoria> getSgprogAuditoriaList() {
        return sgprogAuditoriaList;
    }

    public void setSgprogAuditoriaList(List<SgprogAuditoria> sgprogAuditoriaList) {
        this.sgprogAuditoriaList = sgprogAuditoriaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgdetalleAuditoriaPK != null ? sgdetalleAuditoriaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgdetalleAuditoria)) {
            return false;
        }
        SgdetalleAuditoria other = (SgdetalleAuditoria) object;
        if ((this.sgdetalleAuditoriaPK == null && other.sgdetalleAuditoriaPK != null) || (this.sgdetalleAuditoriaPK != null && !this.sgdetalleAuditoriaPK.equals(other.sgdetalleAuditoriaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgdetalleAuditoria[ sgdetalleAuditoriaPK=" + sgdetalleAuditoriaPK + " ]";
    }
    
}
