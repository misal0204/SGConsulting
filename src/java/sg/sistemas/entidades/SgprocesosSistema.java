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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgprocesos_sistema")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgprocesosSistema.findAll", query = "SELECT s FROM SgprocesosSistema s"),
    @NamedQuery(name = "SgprocesosSistema.findByIdcodProceso", query = "SELECT s FROM SgprocesosSistema s WHERE s.idcodProceso = :idcodProceso"),
    @NamedQuery(name = "SgprocesosSistema.findByDescripcion", query = "SELECT s FROM SgprocesosSistema s WHERE s.descripcion = :descripcion")})
public class SgprocesosSistema implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDCOD_PROCESO")
    private String idcodProceso;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(mappedBy = "idcodProceso")
    private List<SgplanProcesos> sgplanProcesosList;

    public SgprocesosSistema() {
    }

    public SgprocesosSistema(String idcodProceso) {
        this.idcodProceso = idcodProceso;
    }

    public String getIdcodProceso() {
        return idcodProceso;
    }

    public void setIdcodProceso(String idcodProceso) {
        this.idcodProceso = idcodProceso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<SgplanProcesos> getSgplanProcesosList() {
        return sgplanProcesosList;
    }

    public void setSgplanProcesosList(List<SgplanProcesos> sgplanProcesosList) {
        this.sgplanProcesosList = sgplanProcesosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcodProceso != null ? idcodProceso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgprocesosSistema)) {
            return false;
        }
        SgprocesosSistema other = (SgprocesosSistema) object;
        if ((this.idcodProceso == null && other.idcodProceso != null) || (this.idcodProceso != null && !this.idcodProceso.equals(other.idcodProceso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgprocesosSistema[ idcodProceso=" + idcodProceso + " ]";
    }
    
}
