/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgpuesto_laboral")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgpuestoLaboral.findAll", query = "SELECT s FROM SgpuestoLaboral s"),
    @NamedQuery(name = "SgpuestoLaboral.findByIdpuesto", query = "SELECT s FROM SgpuestoLaboral s WHERE s.idpuesto = :idpuesto"),
    @NamedQuery(name = "SgpuestoLaboral.findByPuesto", query = "SELECT s FROM SgpuestoLaboral s WHERE s.puesto = :puesto"),
    @NamedQuery(name = "SgpuestoLaboral.findByDescripcion", query = "SELECT s FROM SgpuestoLaboral s WHERE s.descripcion = :descripcion")})
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(
            name= "SP_INSERT_SGPUESTO_LABORAL",
            procedureName = "SP_INSERT_SGPUESTO_LABORAL",
            parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_IDPUESTO"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_PUESTO"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_DESCRIPCION"),
                @StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "P_RESULTADO")
            }
    ),
    @NamedStoredProcedureQuery(
            name= "SP_UPDATE_SGPUESTO_LABORAL",
            procedureName = "SP_UPDATE_SGPUESTO_LABORAL",
            parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_IDPUESTO"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_PUESTO"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_DESCRIPCION"),
                @StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "P_RESULTADO")
            }
    ),
    @NamedStoredProcedureQuery(
            name= "SP_DELETE_SGPUESTO_LABORAL",
            procedureName = "SP_DELETE_SGPUESTO_LABORAL",
            parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "P_IDPUESTO"),
                @StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "P_RESULTADO")
            }
    )
})
public class SgpuestoLaboral implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDPUESTO")
    private String idpuesto;
    @Column(name = "PUESTO")
    private String puesto;
    @Column(name = "DESCRIPCION")
    private String descripcion;

    public SgpuestoLaboral() {
    }

    public SgpuestoLaboral(String idpuesto) {
        this.idpuesto = idpuesto;
    }

    public String getIdpuesto() {
        return idpuesto;
    }

    public void setIdpuesto(String idpuesto) {
        this.idpuesto = idpuesto;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpuesto != null ? idpuesto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgpuestoLaboral)) {
            return false;
        }
        SgpuestoLaboral other = (SgpuestoLaboral) object;
        if ((this.idpuesto == null && other.idpuesto != null) || (this.idpuesto != null && !this.idpuesto.equals(other.idpuesto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgpuestoLaboral[ idpuesto=" + idpuesto + " ]";
    }
    
}
