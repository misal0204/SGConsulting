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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgtipo_alerta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgtipoAlerta.findAll", query = "SELECT s FROM SgtipoAlerta s"),
    @NamedQuery(name = "SgtipoAlerta.findByIdtipoAlerta", query = "SELECT s FROM SgtipoAlerta s WHERE s.idtipoAlerta = :idtipoAlerta"),
    @NamedQuery(name = "SgtipoAlerta.findByDescripcion", query = "SELECT s FROM SgtipoAlerta s WHERE s.descripcion = :descripcion")})
public class SgtipoAlerta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDTIPO_ALERTA")
    private String idtipoAlerta;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgtipoAlerta")
    private List<SgseguimientoIndObj> sgseguimientoIndObjList;

    public SgtipoAlerta() {
    }

    public SgtipoAlerta(String idtipoAlerta) {
        this.idtipoAlerta = idtipoAlerta;
    }

    public String getIdtipoAlerta() {
        return idtipoAlerta;
    }

    public void setIdtipoAlerta(String idtipoAlerta) {
        this.idtipoAlerta = idtipoAlerta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<SgseguimientoIndObj> getSgseguimientoIndObjList() {
        return sgseguimientoIndObjList;
    }

    public void setSgseguimientoIndObjList(List<SgseguimientoIndObj> sgseguimientoIndObjList) {
        this.sgseguimientoIndObjList = sgseguimientoIndObjList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipoAlerta != null ? idtipoAlerta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgtipoAlerta)) {
            return false;
        }
        SgtipoAlerta other = (SgtipoAlerta) object;
        if ((this.idtipoAlerta == null && other.idtipoAlerta != null) || (this.idtipoAlerta != null && !this.idtipoAlerta.equals(other.idtipoAlerta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgtipoAlerta[ idtipoAlerta=" + idtipoAlerta + " ]";
    }
    
}
