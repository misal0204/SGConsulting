/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgseguimiento_ind_obj")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgseguimientoIndObj.findAll", query = "SELECT s FROM SgseguimientoIndObj s"),
    @NamedQuery(name = "SgseguimientoIndObj.findByIdsociedad", query = "SELECT s FROM SgseguimientoIndObj s WHERE s.sgseguimientoIndObjPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgseguimientoIndObj.findByIdobjIndicador", query = "SELECT s FROM SgseguimientoIndObj s WHERE s.sgseguimientoIndObjPK.idobjIndicador = :idobjIndicador"),
    @NamedQuery(name = "SgseguimientoIndObj.findByIddetalleIo", query = "SELECT s FROM SgseguimientoIndObj s WHERE s.sgseguimientoIndObjPK.iddetalleIo = :iddetalleIo"),
    @NamedQuery(name = "SgseguimientoIndObj.findByIdtipoAlerta", query = "SELECT s FROM SgseguimientoIndObj s WHERE s.sgseguimientoIndObjPK.idtipoAlerta = :idtipoAlerta")})
public class SgseguimientoIndObj implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgseguimientoIndObjPK sgseguimientoIndObjPK;
    @JoinTable(name = "sgresp_seguimiento", joinColumns = {
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD"),
        @JoinColumn(name = "IDOBJ_INDICADOR", referencedColumnName = "IDOBJ_INDICADOR"),
        @JoinColumn(name = "IDDETALLE_IO", referencedColumnName = "IDDETALLE_IO"),
        @JoinColumn(name = "IDTIPO_ALERTA", referencedColumnName = "IDTIPO_ALERTA")}, inverseJoinColumns = {
        @JoinColumn(name = "COD_USUARIO", referencedColumnName = "COD_USUARIO")})
    @ManyToMany
    private List<SgUsuario> sgUsuarioList;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "IDOBJ_INDICADOR", referencedColumnName = "IDOBJ_INDICADOR", insertable = false, updatable = false),
        @JoinColumn(name = "IDDETALLE_IO", referencedColumnName = "IDDETALLE_IO", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private SgdetalleObjInd sgdetalleObjInd;
    @JoinColumn(name = "IDTIPO_ALERTA", referencedColumnName = "IDTIPO_ALERTA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SgtipoAlerta sgtipoAlerta;

    public SgseguimientoIndObj() {
    }

    public SgseguimientoIndObj(SgseguimientoIndObjPK sgseguimientoIndObjPK) {
        this.sgseguimientoIndObjPK = sgseguimientoIndObjPK;
    }

    public SgseguimientoIndObj(String idsociedad, String idobjIndicador, String iddetalleIo, String idtipoAlerta) {
        this.sgseguimientoIndObjPK = new SgseguimientoIndObjPK(idsociedad, idobjIndicador, iddetalleIo, idtipoAlerta);
    }

    public SgseguimientoIndObjPK getSgseguimientoIndObjPK() {
        return sgseguimientoIndObjPK;
    }

    public void setSgseguimientoIndObjPK(SgseguimientoIndObjPK sgseguimientoIndObjPK) {
        this.sgseguimientoIndObjPK = sgseguimientoIndObjPK;
    }

    @XmlTransient
    public List<SgUsuario> getSgUsuarioList() {
        return sgUsuarioList;
    }

    public void setSgUsuarioList(List<SgUsuario> sgUsuarioList) {
        this.sgUsuarioList = sgUsuarioList;
    }

    public SgdetalleObjInd getSgdetalleObjInd() {
        return sgdetalleObjInd;
    }

    public void setSgdetalleObjInd(SgdetalleObjInd sgdetalleObjInd) {
        this.sgdetalleObjInd = sgdetalleObjInd;
    }

    public SgtipoAlerta getSgtipoAlerta() {
        return sgtipoAlerta;
    }

    public void setSgtipoAlerta(SgtipoAlerta sgtipoAlerta) {
        this.sgtipoAlerta = sgtipoAlerta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgseguimientoIndObjPK != null ? sgseguimientoIndObjPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgseguimientoIndObj)) {
            return false;
        }
        SgseguimientoIndObj other = (SgseguimientoIndObj) object;
        if ((this.sgseguimientoIndObjPK == null && other.sgseguimientoIndObjPK != null) || (this.sgseguimientoIndObjPK != null && !this.sgseguimientoIndObjPK.equals(other.sgseguimientoIndObjPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgseguimientoIndObj[ sgseguimientoIndObjPK=" + sgseguimientoIndObjPK + " ]";
    }
    
}
