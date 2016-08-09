/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgorigen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sgorigen.findAll", query = "SELECT s FROM Sgorigen s"),
    @NamedQuery(name = "Sgorigen.findByIdorigen", query = "SELECT s FROM Sgorigen s WHERE s.idorigen = :idorigen"),
    @NamedQuery(name = "Sgorigen.findByDescripcion", query = "SELECT s FROM Sgorigen s WHERE s.descripcion = :descripcion")})
@NamedStoredProcedureQueries(
        {
            @NamedStoredProcedureQuery(
                    name = "SP_INSERT_SGORIGEN",
                    procedureName = "SP_INSERT_SGORIGEN",
                    parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_IDORIGEN"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_DESCRIPCION"),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "P_RESULTADO")
                    }
            ),
            @NamedStoredProcedureQuery(
                    name = "SP_UPDATE_SGORIGEN",
                    procedureName = "SP_UPDATE_SGORIGEN",
                    parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_IDORIGEN"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_DESCRIPCION"),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "P_RESULTADO")
                    }
            ),
            @NamedStoredProcedureQuery(
                    name = "SP_DELETE_SGORIGEN",
                    procedureName = "SP_DELETE_SGORIGEN",
                    parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_IDORIGEN"),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "P_RESULTADO")
                    }
            )
        }
)
public class Sgorigen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDORIGEN")
    private String idorigen;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @ManyToMany(mappedBy = "sgorigenList")
    private List<Sgmotivo> sgmotivoList;

    public Sgorigen() {
    }

    public Sgorigen(String idorigen) {
        this.idorigen = idorigen;
    }

    public String getIdorigen() {
        return idorigen;
    }

    public void setIdorigen(String idorigen) {
        this.idorigen = idorigen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Sgmotivo> getSgmotivoList() {
        return sgmotivoList;
    }

    public void setSgmotivoList(List<Sgmotivo> sgmotivoList) {
        this.sgmotivoList = sgmotivoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idorigen != null ? idorigen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sgorigen)) {
            return false;
        }
        Sgorigen other = (Sgorigen) object;
        if ((this.idorigen == null && other.idorigen != null) || (this.idorigen != null && !this.idorigen.equals(other.idorigen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.Sgorigen[ idorigen=" + idorigen + " ]";
    }
    
}
