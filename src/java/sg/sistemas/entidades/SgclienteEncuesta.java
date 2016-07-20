/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
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
@Table(name = "sgcliente_encuesta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgclienteEncuesta.findAll", query = "SELECT s FROM SgclienteEncuesta s"),
    @NamedQuery(name = "SgclienteEncuesta.findByIdsociedad", query = "SELECT s FROM SgclienteEncuesta s WHERE s.sgclienteEncuestaPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgclienteEncuesta.findByNoenc", query = "SELECT s FROM SgclienteEncuesta s WHERE s.sgclienteEncuestaPK.noenc = :noenc"),
    @NamedQuery(name = "SgclienteEncuesta.findByIdcliente", query = "SELECT s FROM SgclienteEncuesta s WHERE s.sgclienteEncuestaPK.idcliente = :idcliente")})
public class SgclienteEncuesta implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgclienteEncuestaPK sgclienteEncuestaPK;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "NOENC", referencedColumnName = "NOENC", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private SgcabEncuesta sgcabEncuesta;

    public SgclienteEncuesta() {
    }

    public SgclienteEncuesta(SgclienteEncuestaPK sgclienteEncuestaPK) {
        this.sgclienteEncuestaPK = sgclienteEncuestaPK;
    }

    public SgclienteEncuesta(String idsociedad, String noenc, String idcliente) {
        this.sgclienteEncuestaPK = new SgclienteEncuestaPK(idsociedad, noenc, idcliente);
    }

    public SgclienteEncuestaPK getSgclienteEncuestaPK() {
        return sgclienteEncuestaPK;
    }

    public void setSgclienteEncuestaPK(SgclienteEncuestaPK sgclienteEncuestaPK) {
        this.sgclienteEncuestaPK = sgclienteEncuestaPK;
    }

    public SgcabEncuesta getSgcabEncuesta() {
        return sgcabEncuesta;
    }

    public void setSgcabEncuesta(SgcabEncuesta sgcabEncuesta) {
        this.sgcabEncuesta = sgcabEncuesta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgclienteEncuestaPK != null ? sgclienteEncuestaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgclienteEncuesta)) {
            return false;
        }
        SgclienteEncuesta other = (SgclienteEncuesta) object;
        if ((this.sgclienteEncuestaPK == null && other.sgclienteEncuestaPK != null) || (this.sgclienteEncuestaPK != null && !this.sgclienteEncuestaPK.equals(other.sgclienteEncuestaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgclienteEncuesta[ sgclienteEncuestaPK=" + sgclienteEncuestaPK + " ]";
    }
    
}
