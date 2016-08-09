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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
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
@Table(name = "sgestado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sgestado.findAll", query = "SELECT s FROM Sgestado s"),
    @NamedQuery(name = "Sgestado.findByIdestado", query = "SELECT s FROM Sgestado s WHERE s.idestado = :idestado"),
    @NamedQuery(name = "Sgestado.findByDescripcion", query = "SELECT s FROM Sgestado s WHERE s.descripcion = :descripcion")})
@NamedStoredProcedureQueries(
        {
            @NamedStoredProcedureQuery(
                    name = "SP_INSERT_SGESTADO",
                    procedureName = "SP_INSERT_SGESTADO",
                    parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_IDESTADO"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_DESCRIPCION"),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "P_RESULTADO")
                    }
            ),
            @NamedStoredProcedureQuery(
                    name = "SP_UPDATE_SGESTADO",
                    procedureName = "SP_UPDATE_SGESTADO",
                    parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_IDESTADO"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_DESCRIPCION"),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "P_RESULTADO")
                    }
            ),
            @NamedStoredProcedureQuery(
                    name = "SP_DELETE_SGESTADO",
                    procedureName = "SP_DELETE_SGESTADO",
                    parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_IDESTADO"),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "P_RESULTADO")
                    }
            )
        }
)
public class Sgestado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDESTADO")
    private String idestado;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(mappedBy = "idestado")
    private List<Sgnc> sgncList;

    public Sgestado() {
    }

    public Sgestado(String idestado) {
        this.idestado = idestado;
    }

    public String getIdestado() {
        return idestado;
    }

    public void setIdestado(String idestado) {
        this.idestado = idestado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Sgnc> getSgncList() {
        return sgncList;
    }

    public void setSgncList(List<Sgnc> sgncList) {
        this.sgncList = sgncList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idestado != null ? idestado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sgestado)) {
            return false;
        }
        Sgestado other = (Sgestado) object;
        if ((this.idestado == null && other.idestado != null) || (this.idestado != null && !this.idestado.equals(other.idestado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.Sgestado[ idestado=" + idestado + " ]";
    }
    
}
