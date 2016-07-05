/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgprog_detalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgprogDetalle.findAll", query = "SELECT s FROM SgprogDetalle s"),
    @NamedQuery(name = "SgprogDetalle.findByIdsociedad", query = "SELECT s FROM SgprogDetalle s WHERE s.sgprogDetallePK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgprogDetalle.findByIdplanProg", query = "SELECT s FROM SgprogDetalle s WHERE s.sgprogDetallePK.idplanProg = :idplanProg"),
    @NamedQuery(name = "SgprogDetalle.findByFecha", query = "SELECT s FROM SgprogDetalle s WHERE s.sgprogDetallePK.fecha = :fecha"),
    @NamedQuery(name = "SgprogDetalle.findByFechaInicio", query = "SELECT s FROM SgprogDetalle s WHERE s.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "SgprogDetalle.findByFechaFin", query = "SELECT s FROM SgprogDetalle s WHERE s.fechaFin = :fechaFin"),
    @NamedQuery(name = "SgprogDetalle.findByFechaCierre", query = "SELECT s FROM SgprogDetalle s WHERE s.fechaCierre = :fechaCierre"),
    @NamedQuery(name = "SgprogDetalle.findByEstado", query = "SELECT s FROM SgprogDetalle s WHERE s.estado = :estado")})
public class SgprogDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgprogDetallePK sgprogDetallePK;
    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Column(name = "FECHA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Column(name = "FECHA_CIERRE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCierre;
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "IDPLAN_PROG", referencedColumnName = "IDPLAN_PROG", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private SgplanPrograma sgplanPrograma;

    public SgprogDetalle() {
    }

    public SgprogDetalle(SgprogDetallePK sgprogDetallePK) {
        this.sgprogDetallePK = sgprogDetallePK;
    }

    public SgprogDetalle(String idsociedad, String idplanProg, Date fecha) {
        this.sgprogDetallePK = new SgprogDetallePK(idsociedad, idplanProg, fecha);
    }

    public SgprogDetallePK getSgprogDetallePK() {
        return sgprogDetallePK;
    }

    public void setSgprogDetallePK(SgprogDetallePK sgprogDetallePK) {
        this.sgprogDetallePK = sgprogDetallePK;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public SgplanPrograma getSgplanPrograma() {
        return sgplanPrograma;
    }

    public void setSgplanPrograma(SgplanPrograma sgplanPrograma) {
        this.sgplanPrograma = sgplanPrograma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgprogDetallePK != null ? sgprogDetallePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgprogDetalle)) {
            return false;
        }
        SgprogDetalle other = (SgprogDetalle) object;
        if ((this.sgprogDetallePK == null && other.sgprogDetallePK != null) || (this.sgprogDetallePK != null && !this.sgprogDetallePK.equals(other.sgprogDetallePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgprogDetalle[ sgprogDetallePK=" + sgprogDetallePK + " ]";
    }
    
}
