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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
@Table(name = "sgmotivo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sgmotivo.findAll", query = "SELECT s FROM Sgmotivo s"),
    @NamedQuery(name = "Sgmotivo.findByIdmotivo", query = "SELECT s FROM Sgmotivo s WHERE s.idmotivo = :idmotivo"),
    @NamedQuery(name = "Sgmotivo.findByDescripcion", query = "SELECT s FROM Sgmotivo s WHERE s.descripcion = :descripcion")})
@NamedStoredProcedureQueries(
        {
            @NamedStoredProcedureQuery(
                    name = "SP_INSERT_SGMOTIVO",
                    procedureName = "SP_INSERT_SGMOTIVO",
                    parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_IDMOTIVO"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_DESCRIPCION"),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "P_RESULTADO")
                    }
            ),
            @NamedStoredProcedureQuery(
                    name = "SP_UPDATE_SGMOTIVO",
                    procedureName = "SP_UPDATE_SGMOTIVO",
                    parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_IDMOTIVO"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_DESCRIPCION"),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "P_RESULTADO")
                    }
            ),
            @NamedStoredProcedureQuery(
                    name = "SP_DELETE_SGMOTIVO",
                    procedureName = "SP_DELETE_SGMOTIVO",
                    parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_IDMOTIVO"),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "P_RESULTADO")
                    }
            )
        }
)
public class Sgmotivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDMOTIVO")
    private String idmotivo;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinTable(name = "sgmotivo_origen", joinColumns = {
        @JoinColumn(name = "IDMOTIVO", referencedColumnName = "IDMOTIVO")}, inverseJoinColumns = {
        @JoinColumn(name = "IDORIGEN", referencedColumnName = "IDORIGEN")})
    @ManyToMany
    private List<Sgorigen> sgorigenList;

    public Sgmotivo() {
    }

    public Sgmotivo(String idmotivo) {
        this.idmotivo = idmotivo;
    }

    public String getIdmotivo() {
        return idmotivo;
    }

    public void setIdmotivo(String idmotivo) {
        this.idmotivo = idmotivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Sgorigen> getSgorigenList() {
        return sgorigenList;
    }

    public void setSgorigenList(List<Sgorigen> sgorigenList) {
        this.sgorigenList = sgorigenList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmotivo != null ? idmotivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sgmotivo)) {
            return false;
        }
        Sgmotivo other = (Sgmotivo) object;
        if ((this.idmotivo == null && other.idmotivo != null) || (this.idmotivo != null && !this.idmotivo.equals(other.idmotivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.Sgmotivo[ idmotivo=" + idmotivo + " ]";
    }
    
}
