/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Misael
 */
@Embeddable
public class SgprogDetallePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDSOCIEDAD")
    private String idsociedad;
    @Basic(optional = false)
    @Column(name = "IDPLAN_PROG")
    private String idplanProg;
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    public SgprogDetallePK() {
    }

    public SgprogDetallePK(String idsociedad, String idplanProg, Date fecha) {
        this.idsociedad = idsociedad;
        this.idplanProg = idplanProg;
        this.fecha = fecha;
    }

    public String getIdsociedad() {
        return idsociedad;
    }

    public void setIdsociedad(String idsociedad) {
        this.idsociedad = idsociedad;
    }

    public String getIdplanProg() {
        return idplanProg;
    }

    public void setIdplanProg(String idplanProg) {
        this.idplanProg = idplanProg;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsociedad != null ? idsociedad.hashCode() : 0);
        hash += (idplanProg != null ? idplanProg.hashCode() : 0);
        hash += (fecha != null ? fecha.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgprogDetallePK)) {
            return false;
        }
        SgprogDetallePK other = (SgprogDetallePK) object;
        if ((this.idsociedad == null && other.idsociedad != null) || (this.idsociedad != null && !this.idsociedad.equals(other.idsociedad))) {
            return false;
        }
        if ((this.idplanProg == null && other.idplanProg != null) || (this.idplanProg != null && !this.idplanProg.equals(other.idplanProg))) {
            return false;
        }
        if ((this.fecha == null && other.fecha != null) || (this.fecha != null && !this.fecha.equals(other.fecha))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgprogDetallePK[ idsociedad=" + idsociedad + ", idplanProg=" + idplanProg + ", fecha=" + fecha + " ]";
    }
    
}
