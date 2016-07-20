/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgsigue")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sgsigue.findAll", query = "SELECT s FROM Sgsigue s"),
    @NamedQuery(name = "Sgsigue.findByIdsociedad", query = "SELECT s FROM Sgsigue s WHERE s.sgsiguePK.idsociedad = :idsociedad"),
    @NamedQuery(name = "Sgsigue.findByNonc", query = "SELECT s FROM Sgsigue s WHERE s.sgsiguePK.nonc = :nonc"),
    @NamedQuery(name = "Sgsigue.findByFecha", query = "SELECT s FROM Sgsigue s WHERE s.sgsiguePK.fecha = :fecha")})
public class Sgsigue implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgsiguePK sgsiguePK;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "sgsigue")
    private SgseguimientoDetalle sgseguimientoDetalle;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "NONC", referencedColumnName = "NONC", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Sgnc sgnc;
    @JoinColumn(name = "COD_USUARIO", referencedColumnName = "COD_USUARIO")
    @ManyToOne
    private Sgusuario codUsuario;

    public Sgsigue() {
    }

    public Sgsigue(SgsiguePK sgsiguePK) {
        this.sgsiguePK = sgsiguePK;
    }

    public Sgsigue(String idsociedad, String nonc, Date fecha) {
        this.sgsiguePK = new SgsiguePK(idsociedad, nonc, fecha);
    }

    public SgsiguePK getSgsiguePK() {
        return sgsiguePK;
    }

    public void setSgsiguePK(SgsiguePK sgsiguePK) {
        this.sgsiguePK = sgsiguePK;
    }

    public SgseguimientoDetalle getSgseguimientoDetalle() {
        return sgseguimientoDetalle;
    }

    public void setSgseguimientoDetalle(SgseguimientoDetalle sgseguimientoDetalle) {
        this.sgseguimientoDetalle = sgseguimientoDetalle;
    }

    public Sgnc getSgnc() {
        return sgnc;
    }

    public void setSgnc(Sgnc sgnc) {
        this.sgnc = sgnc;
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
        hash += (sgsiguePK != null ? sgsiguePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sgsigue)) {
            return false;
        }
        Sgsigue other = (Sgsigue) object;
        if ((this.sgsiguePK == null && other.sgsiguePK != null) || (this.sgsiguePK != null && !this.sgsiguePK.equals(other.sgsiguePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.Sgsigue[ sgsiguePK=" + sgsiguePK + " ]";
    }
    
}
