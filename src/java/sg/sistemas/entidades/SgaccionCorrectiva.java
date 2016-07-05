/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgaccion_correctiva")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgaccionCorrectiva.findAll", query = "SELECT s FROM SgaccionCorrectiva s"),
    @NamedQuery(name = "SgaccionCorrectiva.findByIdsociedad", query = "SELECT s FROM SgaccionCorrectiva s WHERE s.sgaccionCorrectivaPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgaccionCorrectiva.findByNonc", query = "SELECT s FROM SgaccionCorrectiva s WHERE s.sgaccionCorrectivaPK.nonc = :nonc"),
    @NamedQuery(name = "SgaccionCorrectiva.findByIdcausa", query = "SELECT s FROM SgaccionCorrectiva s WHERE s.sgaccionCorrectivaPK.idcausa = :idcausa"),
    @NamedQuery(name = "SgaccionCorrectiva.findByIdaccion", query = "SELECT s FROM SgaccionCorrectiva s WHERE s.sgaccionCorrectivaPK.idaccion = :idaccion"),
    @NamedQuery(name = "SgaccionCorrectiva.findByFechaCompromiso", query = "SELECT s FROM SgaccionCorrectiva s WHERE s.fechaCompromiso = :fechaCompromiso"),
    @NamedQuery(name = "SgaccionCorrectiva.findByEstado", query = "SELECT s FROM SgaccionCorrectiva s WHERE s.estado = :estado")})
public class SgaccionCorrectiva implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgaccionCorrectivaPK sgaccionCorrectivaPK;
    @Basic(optional = false)
    @Lob
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "FECHA_COMPROMISO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCompromiso;
    @Basic(optional = false)
    @Lob
    @Column(name = "RESPONSABLES")
    private String responsables;
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "NONC", referencedColumnName = "NONC", insertable = false, updatable = false),
        @JoinColumn(name = "IDCAUSA", referencedColumnName = "IDCAUSA", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Sgcausa sgcausa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgaccionCorrectiva")
    private List<SgaccionResponsables> sgaccionResponsablesList;

    public SgaccionCorrectiva() {
    }

    public SgaccionCorrectiva(SgaccionCorrectivaPK sgaccionCorrectivaPK) {
        this.sgaccionCorrectivaPK = sgaccionCorrectivaPK;
    }

    public SgaccionCorrectiva(SgaccionCorrectivaPK sgaccionCorrectivaPK, String descripcion, Date fechaCompromiso, String responsables) {
        this.sgaccionCorrectivaPK = sgaccionCorrectivaPK;
        this.descripcion = descripcion;
        this.fechaCompromiso = fechaCompromiso;
        this.responsables = responsables;
    }

    public SgaccionCorrectiva(String idsociedad, String nonc, String idcausa, String idaccion) {
        this.sgaccionCorrectivaPK = new SgaccionCorrectivaPK(idsociedad, nonc, idcausa, idaccion);
    }

    public SgaccionCorrectivaPK getSgaccionCorrectivaPK() {
        return sgaccionCorrectivaPK;
    }

    public void setSgaccionCorrectivaPK(SgaccionCorrectivaPK sgaccionCorrectivaPK) {
        this.sgaccionCorrectivaPK = sgaccionCorrectivaPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaCompromiso() {
        return fechaCompromiso;
    }

    public void setFechaCompromiso(Date fechaCompromiso) {
        this.fechaCompromiso = fechaCompromiso;
    }

    public String getResponsables() {
        return responsables;
    }

    public void setResponsables(String responsables) {
        this.responsables = responsables;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Sgcausa getSgcausa() {
        return sgcausa;
    }

    public void setSgcausa(Sgcausa sgcausa) {
        this.sgcausa = sgcausa;
    }

    @XmlTransient
    public List<SgaccionResponsables> getSgaccionResponsablesList() {
        return sgaccionResponsablesList;
    }

    public void setSgaccionResponsablesList(List<SgaccionResponsables> sgaccionResponsablesList) {
        this.sgaccionResponsablesList = sgaccionResponsablesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgaccionCorrectivaPK != null ? sgaccionCorrectivaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgaccionCorrectiva)) {
            return false;
        }
        SgaccionCorrectiva other = (SgaccionCorrectiva) object;
        if ((this.sgaccionCorrectivaPK == null && other.sgaccionCorrectivaPK != null) || (this.sgaccionCorrectivaPK != null && !this.sgaccionCorrectivaPK.equals(other.sgaccionCorrectivaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgaccionCorrectiva[ sgaccionCorrectivaPK=" + sgaccionCorrectivaPK + " ]";
    }
    
}
