/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgaccion_responsables")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgaccionResponsables.findAll", query = "SELECT s FROM SgaccionResponsables s"),
    @NamedQuery(name = "SgaccionResponsables.findByIdsociedad", query = "SELECT s FROM SgaccionResponsables s WHERE s.sgaccionResponsablesPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgaccionResponsables.findByNonc", query = "SELECT s FROM SgaccionResponsables s WHERE s.sgaccionResponsablesPK.nonc = :nonc"),
    @NamedQuery(name = "SgaccionResponsables.findByIdcausa", query = "SELECT s FROM SgaccionResponsables s WHERE s.sgaccionResponsablesPK.idcausa = :idcausa"),
    @NamedQuery(name = "SgaccionResponsables.findByIdaccion", query = "SELECT s FROM SgaccionResponsables s WHERE s.sgaccionResponsablesPK.idaccion = :idaccion"),
    @NamedQuery(name = "SgaccionResponsables.findByCodUsuario", query = "SELECT s FROM SgaccionResponsables s WHERE s.sgaccionResponsablesPK.codUsuario = :codUsuario"),
    @NamedQuery(name = "SgaccionResponsables.findByFechaCompromiso", query = "SELECT s FROM SgaccionResponsables s WHERE s.fechaCompromiso = :fechaCompromiso"),
    @NamedQuery(name = "SgaccionResponsables.findByEstado", query = "SELECT s FROM SgaccionResponsables s WHERE s.estado = :estado")})
public class SgaccionResponsables implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgaccionResponsablesPK sgaccionResponsablesPK;
    @Basic(optional = false)
    @Column(name = "FECHA_COMPROMISO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCompromiso;
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "NONC", referencedColumnName = "NONC", insertable = false, updatable = false),
        @JoinColumn(name = "IDCAUSA", referencedColumnName = "IDCAUSA", insertable = false, updatable = false),
        @JoinColumn(name = "IDACCION", referencedColumnName = "IDACCION", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private SgaccionCorrectiva sgaccionCorrectiva;

    public SgaccionResponsables() {
    }

    public SgaccionResponsables(SgaccionResponsablesPK sgaccionResponsablesPK) {
        this.sgaccionResponsablesPK = sgaccionResponsablesPK;
    }

    public SgaccionResponsables(SgaccionResponsablesPK sgaccionResponsablesPK, Date fechaCompromiso) {
        this.sgaccionResponsablesPK = sgaccionResponsablesPK;
        this.fechaCompromiso = fechaCompromiso;
    }

    public SgaccionResponsables(String idsociedad, String nonc, String idcausa, String idaccion, String codUsuario) {
        this.sgaccionResponsablesPK = new SgaccionResponsablesPK(idsociedad, nonc, idcausa, idaccion, codUsuario);
    }

    public SgaccionResponsablesPK getSgaccionResponsablesPK() {
        return sgaccionResponsablesPK;
    }

    public void setSgaccionResponsablesPK(SgaccionResponsablesPK sgaccionResponsablesPK) {
        this.sgaccionResponsablesPK = sgaccionResponsablesPK;
    }

    public Date getFechaCompromiso() {
        return fechaCompromiso;
    }

    public void setFechaCompromiso(Date fechaCompromiso) {
        this.fechaCompromiso = fechaCompromiso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public SgaccionCorrectiva getSgaccionCorrectiva() {
        return sgaccionCorrectiva;
    }

    public void setSgaccionCorrectiva(SgaccionCorrectiva sgaccionCorrectiva) {
        this.sgaccionCorrectiva = sgaccionCorrectiva;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgaccionResponsablesPK != null ? sgaccionResponsablesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgaccionResponsables)) {
            return false;
        }
        SgaccionResponsables other = (SgaccionResponsables) object;
        if ((this.sgaccionResponsablesPK == null && other.sgaccionResponsablesPK != null) || (this.sgaccionResponsablesPK != null && !this.sgaccionResponsablesPK.equals(other.sgaccionResponsablesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgaccionResponsables[ sgaccionResponsablesPK=" + sgaccionResponsablesPK + " ]";
    }
    
}
