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
@Table(name = "sgrol_auditor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgrolAuditor.findAll", query = "SELECT s FROM SgrolAuditor s"),
    @NamedQuery(name = "SgrolAuditor.findByIdrolAuditor", query = "SELECT s FROM SgrolAuditor s WHERE s.idrolAuditor = :idrolAuditor"),
    @NamedQuery(name = "SgrolAuditor.findByDescripcion", query = "SELECT s FROM SgrolAuditor s WHERE s.descripcion = :descripcion")})
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(
            name="SP_INSERT_SGROL_AUDITOR",
            procedureName = "SP_INSERT_SGROL_AUDITOR",
            parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class,name= "p_idrol_auditor"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class,name= "p_descripcion"),
                @StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class,name= "p_resultado")
            }
    ),
    @NamedStoredProcedureQuery(
            name="SP_UPDATE_SGROL_AUDITOR",
            procedureName = "SP_UPDATE_SGROL_AUDITOR",
            parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class,name= "p_idrol_auditor"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class,name= "p_descripcion"),
                @StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class,name= "p_resultado")
            }
    ),
    @NamedStoredProcedureQuery(
            name="SP_DELETE_SGROL_AUDITOR",
            procedureName = "SP_DELETE_SGROL_AUDITOR",
            parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class,name= "p_idrol_auditor"),
                @StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class,name= "p_resultado")
            }
    )
})
public class SgrolAuditor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDROL_AUDITOR")
    private String idrolAuditor;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(mappedBy = "idrolAuditor")
    private List<Sgusuario> sgusuarioList;

    public SgrolAuditor() {
    }

    public SgrolAuditor(String idrolAuditor) {
        this.idrolAuditor = idrolAuditor;
    }

    public String getIdrolAuditor() {
        return idrolAuditor;
    }

    public void setIdrolAuditor(String idrolAuditor) {
        this.idrolAuditor = idrolAuditor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Sgusuario> getSgusuarioList() {
        return sgusuarioList;
    }

    public void setSgusuarioList(List<Sgusuario> sgusuarioList) {
        this.sgusuarioList = sgusuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrolAuditor != null ? idrolAuditor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgrolAuditor)) {
            return false;
        }
        SgrolAuditor other = (SgrolAuditor) object;
        if ((this.idrolAuditor == null && other.idrolAuditor != null) || (this.idrolAuditor != null && !this.idrolAuditor.equals(other.idrolAuditor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgrolAuditor[ idrolAuditor=" + idrolAuditor + " ]";
    }

}
