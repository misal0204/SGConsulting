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
import javax.persistence.Lob;
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
@Table(name = "sgparametros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sgparametros.findAll", query = "SELECT s FROM Sgparametros s"),
    @NamedQuery(name = "Sgparametros.findByIdsociedad", query = "SELECT s FROM Sgparametros s WHERE s.sgparametrosPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "Sgparametros.findByParameter", query = "SELECT s FROM Sgparametros s WHERE s.sgparametrosPK.parameter = :parameter")})
public class Sgparametros implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgparametrosPK sgparametrosPK;
    @Lob
    @Column(name = "PARAMETER_DESC")
    private String parameterDesc;
    @Lob
    @Column(name = "VALUE")
    private String value;
    @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Sgsociedad sgsociedad;

    public Sgparametros() {
    }

    public Sgparametros(SgparametrosPK sgparametrosPK) {
        this.sgparametrosPK = sgparametrosPK;
    }

    public Sgparametros(String idsociedad, String parameter) {
        this.sgparametrosPK = new SgparametrosPK(idsociedad, parameter);
    }

    public SgparametrosPK getSgparametrosPK() {
        return sgparametrosPK;
    }

    public void setSgparametrosPK(SgparametrosPK sgparametrosPK) {
        this.sgparametrosPK = sgparametrosPK;
    }

    public String getParameterDesc() {
        return parameterDesc;
    }

    public void setParameterDesc(String parameterDesc) {
        this.parameterDesc = parameterDesc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Sgsociedad getSgsociedad() {
        return sgsociedad;
    }

    public void setSgsociedad(Sgsociedad sgsociedad) {
        this.sgsociedad = sgsociedad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgparametrosPK != null ? sgparametrosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sgparametros)) {
            return false;
        }
        Sgparametros other = (Sgparametros) object;
        if ((this.sgparametrosPK == null && other.sgparametrosPK != null) || (this.sgparametrosPK != null && !this.sgparametrosPK.equals(other.sgparametrosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.Sgparametros[ sgparametrosPK=" + sgparametrosPK + " ]";
    }
    
}
