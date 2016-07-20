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
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgplan_procesos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgplanProcesos.findAll", query = "SELECT s FROM SgplanProcesos s"),
    @NamedQuery(name = "SgplanProcesos.findByIdsociedad", query = "SELECT s FROM SgplanProcesos s WHERE s.sgplanProcesosPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgplanProcesos.findByIdtipoPdireccion", query = "SELECT s FROM SgplanProcesos s WHERE s.sgplanProcesosPK.idtipoPdireccion = :idtipoPdireccion"),
    @NamedQuery(name = "SgplanProcesos.findByIddetalleDireccion", query = "SELECT s FROM SgplanProcesos s WHERE s.sgplanProcesosPK.iddetalleDireccion = :iddetalleDireccion")})
public class SgplanProcesos implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgplanProcesosPK sgplanProcesosPK;
    @Lob
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Lob
    @Column(name = "CONCLUSION")
    private String conclusion;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "IDTIPO_PDIRECCION", referencedColumnName = "IDTIPO_PDIRECCION", insertable = false, updatable = false),
        @JoinColumn(name = "IDDETALLE_DIRECCION", referencedColumnName = "IDDETALLE_DIRECCION", insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private SgdetalleRdireccion sgdetalleRdireccion;
    @JoinColumn(name = "IDCOD_PROCESO", referencedColumnName = "IDCOD_PROCESO")
    @ManyToOne
    private SgprocesosSistema idcodProceso;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgplanProcesos")
    private List<SganxplanProcess> sganxplanProcessList;

    public SgplanProcesos() {
    }

    public SgplanProcesos(SgplanProcesosPK sgplanProcesosPK) {
        this.sgplanProcesosPK = sgplanProcesosPK;
    }

    public SgplanProcesos(String idsociedad, String idtipoPdireccion, String iddetalleDireccion) {
        this.sgplanProcesosPK = new SgplanProcesosPK(idsociedad, idtipoPdireccion, iddetalleDireccion);
    }

    public SgplanProcesosPK getSgplanProcesosPK() {
        return sgplanProcesosPK;
    }

    public void setSgplanProcesosPK(SgplanProcesosPK sgplanProcesosPK) {
        this.sgplanProcesosPK = sgplanProcesosPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public SgdetalleRdireccion getSgdetalleRdireccion() {
        return sgdetalleRdireccion;
    }

    public void setSgdetalleRdireccion(SgdetalleRdireccion sgdetalleRdireccion) {
        this.sgdetalleRdireccion = sgdetalleRdireccion;
    }

    public SgprocesosSistema getIdcodProceso() {
        return idcodProceso;
    }

    public void setIdcodProceso(SgprocesosSistema idcodProceso) {
        this.idcodProceso = idcodProceso;
    }

    @XmlTransient
    public List<SganxplanProcess> getSganxplanProcessList() {
        return sganxplanProcessList;
    }

    public void setSganxplanProcessList(List<SganxplanProcess> sganxplanProcessList) {
        this.sganxplanProcessList = sganxplanProcessList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgplanProcesosPK != null ? sgplanProcesosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgplanProcesos)) {
            return false;
        }
        SgplanProcesos other = (SgplanProcesos) object;
        if ((this.sgplanProcesosPK == null && other.sgplanProcesosPK != null) || (this.sgplanProcesosPK != null && !this.sgplanProcesosPK.equals(other.sgplanProcesosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgplanProcesos[ sgplanProcesosPK=" + sgplanProcesosPK + " ]";
    }
    
}
