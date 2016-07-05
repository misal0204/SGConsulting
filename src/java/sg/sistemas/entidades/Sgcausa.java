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
@Table(name = "sgcausa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sgcausa.findAll", query = "SELECT s FROM Sgcausa s"),
    @NamedQuery(name = "Sgcausa.findByIdsociedad", query = "SELECT s FROM Sgcausa s WHERE s.sgcausaPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "Sgcausa.findByNonc", query = "SELECT s FROM Sgcausa s WHERE s.sgcausaPK.nonc = :nonc"),
    @NamedQuery(name = "Sgcausa.findByIdcausa", query = "SELECT s FROM Sgcausa s WHERE s.sgcausaPK.idcausa = :idcausa"),
    @NamedQuery(name = "Sgcausa.findByFechaInicio", query = "SELECT s FROM Sgcausa s WHERE s.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Sgcausa.findByFechaFin", query = "SELECT s FROM Sgcausa s WHERE s.fechaFin = :fechaFin"),
    @NamedQuery(name = "Sgcausa.findByFechaFinReal", query = "SELECT s FROM Sgcausa s WHERE s.fechaFinReal = :fechaFinReal")})
public class Sgcausa implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgcausaPK sgcausaPK;
    @Basic(optional = false)
    @Lob
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Column(name = "FECHA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Column(name = "FECHA_FIN_REAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinReal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgcausa")
    private List<SgaccionCorrectiva> sgaccionCorrectivaList;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "NONC", referencedColumnName = "NONC", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Sgnc sgnc;
    @JoinColumn(name = "COD_USUARIO", referencedColumnName = "COD_USUARIO")
    @ManyToOne
    private SgUsuario codUsuario;
    @JoinColumn(name = "IDTCAUSA", referencedColumnName = "IDTCAUSA")
    @ManyToOne(optional = false)
    private Sgtcausa idtcausa;

    public Sgcausa() {
    }

    public Sgcausa(SgcausaPK sgcausaPK) {
        this.sgcausaPK = sgcausaPK;
    }

    public Sgcausa(SgcausaPK sgcausaPK, String descripcion) {
        this.sgcausaPK = sgcausaPK;
        this.descripcion = descripcion;
    }

    public Sgcausa(String idsociedad, String nonc, String idcausa) {
        this.sgcausaPK = new SgcausaPK(idsociedad, nonc, idcausa);
    }

    public SgcausaPK getSgcausaPK() {
        return sgcausaPK;
    }

    public void setSgcausaPK(SgcausaPK sgcausaPK) {
        this.sgcausaPK = sgcausaPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaFinReal() {
        return fechaFinReal;
    }

    public void setFechaFinReal(Date fechaFinReal) {
        this.fechaFinReal = fechaFinReal;
    }

    @XmlTransient
    public List<SgaccionCorrectiva> getSgaccionCorrectivaList() {
        return sgaccionCorrectivaList;
    }

    public void setSgaccionCorrectivaList(List<SgaccionCorrectiva> sgaccionCorrectivaList) {
        this.sgaccionCorrectivaList = sgaccionCorrectivaList;
    }

    public Sgnc getSgnc() {
        return sgnc;
    }

    public void setSgnc(Sgnc sgnc) {
        this.sgnc = sgnc;
    }

    public SgUsuario getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(SgUsuario codUsuario) {
        this.codUsuario = codUsuario;
    }

    public Sgtcausa getIdtcausa() {
        return idtcausa;
    }

    public void setIdtcausa(Sgtcausa idtcausa) {
        this.idtcausa = idtcausa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgcausaPK != null ? sgcausaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sgcausa)) {
            return false;
        }
        Sgcausa other = (Sgcausa) object;
        if ((this.sgcausaPK == null && other.sgcausaPK != null) || (this.sgcausaPK != null && !this.sgcausaPK.equals(other.sgcausaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.Sgcausa[ sgcausaPK=" + sgcausaPK + " ]";
    }
    
}
