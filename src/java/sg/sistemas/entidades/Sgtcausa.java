/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "sgtcausa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sgtcausa.findAll", query = "SELECT s FROM Sgtcausa s"),
    @NamedQuery(name = "Sgtcausa.findByIdtcausa", query = "SELECT s FROM Sgtcausa s WHERE s.idtcausa = :idtcausa"),
    @NamedQuery(name = "Sgtcausa.findByDescripcion", query = "SELECT s FROM Sgtcausa s WHERE s.descripcion = :descripcion")})
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(
            name="SP_INSERT_SGTCAUSA",
            procedureName = "SP_INSERT_SGTCAUSA",
            parameters ={
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_IDTCAUSA"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_DESCRIPCION"),
                @StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "P_RESULTADO"),
            }
    ),
    @NamedStoredProcedureQuery(
            name="SP_UPDATE_SGTCAUSA",
            procedureName = "SP_UPDATE_SGTCAUSA",
            parameters ={
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_IDTCAUSA"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_DESCRIPCION"),
                @StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "P_RESULTADO"),
            }
    ),
    @NamedStoredProcedureQuery(
            name="SP_DELETE_SGTCAUSA",
            procedureName = "SP_DELETE_SGTCAUSA",
            parameters ={
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_IDTCAUSA"),
                @StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "P_RESULTADO"),
            }
    )
})
public class Sgtcausa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDTCAUSA")
    private String idtcausa;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idtcausa")
    private List<Sgcausa> sgcausaList;

    public Sgtcausa() {
    }

    public Sgtcausa(String idtcausa) {
        this.idtcausa = idtcausa;
    }

    public String getIdtcausa() {
        return idtcausa;
    }

    public void setIdtcausa(String idtcausa) {
        this.idtcausa = idtcausa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Sgcausa> getSgcausaList() {
        return sgcausaList;
    }

    public void setSgcausaList(List<Sgcausa> sgcausaList) {
        this.sgcausaList = sgcausaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtcausa != null ? idtcausa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sgtcausa)) {
            return false;
        }
        Sgtcausa other = (Sgtcausa) object;
        if ((this.idtcausa == null && other.idtcausa != null) || (this.idtcausa != null && !this.idtcausa.equals(other.idtcausa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.Sgtcausa[ idtcausa=" + idtcausa + " ]";
    }

}
