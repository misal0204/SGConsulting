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
import javax.persistence.Parameter;
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
@Table(name = "sgprocesos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sgprocesos.findAll", query = "SELECT s FROM Sgprocesos s"),
    @NamedQuery(name = "Sgprocesos.findByIdprocesos", query = "SELECT s FROM Sgprocesos s WHERE s.idprocesos = :idprocesos"),
    @NamedQuery(name = "Sgprocesos.findByDescripcion", query = "SELECT s FROM Sgprocesos s WHERE s.descripcion = :descripcion")})
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(
            name = "SP_INSERT_SGPROCESOS",
            procedureName = "SP_INSERT_SGPROCESOS",
            parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "p_idprocesos"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "p_descripcion"),
                @StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "p_resultado")
            }
    ),
    @NamedStoredProcedureQuery(
            name = "SP_UPDATE_SGPROCESOS",
            procedureName = "SP_UPDATE_SGPROCESOS",
            parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "p_idprocesos"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "p_descripcion"),
                @StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "p_resultado")
            }
    ),
    @NamedStoredProcedureQuery(
            name = "SP_DELETE_SGPROCESOS",
            procedureName = "SP_DELETE_SGPROCESOS",
            parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "p_idprocesos"),
                @StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "p_resultado")
            }
    )
})
public class Sgprocesos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDPROCESOS")
    private String idprocesos;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgprocesos")
    private List<SgprocesoDetalle> sgprocesoDetalleList;

    public Sgprocesos() {
    }

    public Sgprocesos(String idprocesos) {
        this.idprocesos = idprocesos;
    }

    public String getIdprocesos() {
        return idprocesos;
    }

    public void setIdprocesos(String idprocesos) {
        this.idprocesos = idprocesos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<SgprocesoDetalle> getSgprocesoDetalleList() {
        return sgprocesoDetalleList;
    }

    public void setSgprocesoDetalleList(List<SgprocesoDetalle> sgprocesoDetalleList) {
        this.sgprocesoDetalleList = sgprocesoDetalleList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idprocesos != null ? idprocesos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sgprocesos)) {
            return false;
        }
        Sgprocesos other = (Sgprocesos) object;
        if ((this.idprocesos == null && other.idprocesos != null) || (this.idprocesos != null && !this.idprocesos.equals(other.idprocesos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.Sgprocesos[ idprocesos=" + idprocesos + " ]";
    }
    
}
