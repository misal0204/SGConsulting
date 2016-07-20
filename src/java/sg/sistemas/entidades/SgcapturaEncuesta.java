/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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
@Table(name = "sgcaptura_encuesta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgcapturaEncuesta.findAll", query = "SELECT s FROM SgcapturaEncuesta s"),
    @NamedQuery(name = "SgcapturaEncuesta.findByNumeroEncuesta", query = "SELECT s FROM SgcapturaEncuesta s WHERE s.sgcapturaEncuestaPK.numeroEncuesta = :numeroEncuesta"),
    @NamedQuery(name = "SgcapturaEncuesta.findByIdencuesta", query = "SELECT s FROM SgcapturaEncuesta s WHERE s.sgcapturaEncuestaPK.idencuesta = :idencuesta"),
    @NamedQuery(name = "SgcapturaEncuesta.findByFecha", query = "SELECT s FROM SgcapturaEncuesta s WHERE s.fecha = :fecha")})
public class SgcapturaEncuesta implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgcapturaEncuestaPK sgcapturaEncuestaPK;
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgcapturaEncuesta")
    private List<SgdetalleCapencuesta> sgdetalleCapencuestaList;
    @JoinColumn(name = "COD_USUARIO", referencedColumnName = "COD_USUARIO")
    @ManyToOne
    private Sgusuario codUsuario;

    public SgcapturaEncuesta() {
    }

    public SgcapturaEncuesta(SgcapturaEncuestaPK sgcapturaEncuestaPK) {
        this.sgcapturaEncuestaPK = sgcapturaEncuestaPK;
    }

    public SgcapturaEncuesta(String numeroEncuesta, String idencuesta) {
        this.sgcapturaEncuestaPK = new SgcapturaEncuestaPK(numeroEncuesta, idencuesta);
    }

    public SgcapturaEncuestaPK getSgcapturaEncuestaPK() {
        return sgcapturaEncuestaPK;
    }

    public void setSgcapturaEncuestaPK(SgcapturaEncuestaPK sgcapturaEncuestaPK) {
        this.sgcapturaEncuestaPK = sgcapturaEncuestaPK;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @XmlTransient
    public List<SgdetalleCapencuesta> getSgdetalleCapencuestaList() {
        return sgdetalleCapencuestaList;
    }

    public void setSgdetalleCapencuestaList(List<SgdetalleCapencuesta> sgdetalleCapencuestaList) {
        this.sgdetalleCapencuestaList = sgdetalleCapencuestaList;
    }

    public Sgusuario getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(Sgusuario codUsuario) {
        this.codUsuario = codUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgcapturaEncuestaPK != null ? sgcapturaEncuestaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgcapturaEncuesta)) {
            return false;
        }
        SgcapturaEncuesta other = (SgcapturaEncuesta) object;
        if ((this.sgcapturaEncuestaPK == null && other.sgcapturaEncuestaPK != null) || (this.sgcapturaEncuestaPK != null && !this.sgcapturaEncuestaPK.equals(other.sgcapturaEncuestaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgcapturaEncuesta[ sgcapturaEncuestaPK=" + sgcapturaEncuestaPK + " ]";
    }
    
}
