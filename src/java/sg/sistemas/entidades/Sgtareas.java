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
import javax.persistence.Lob;
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
@Table(name = "sgtareas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sgtareas.findAll", query = "SELECT s FROM Sgtareas s"),
    @NamedQuery(name = "Sgtareas.findByIdsociedad", query = "SELECT s FROM Sgtareas s WHERE s.sgtareasPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "Sgtareas.findByIdprocesos", query = "SELECT s FROM Sgtareas s WHERE s.sgtareasPK.idprocesos = :idprocesos"),
    @NamedQuery(name = "Sgtareas.findByIdtarea", query = "SELECT s FROM Sgtareas s WHERE s.sgtareasPK.idtarea = :idtarea"),
    @NamedQuery(name = "Sgtareas.findByEstado", query = "SELECT s FROM Sgtareas s WHERE s.estado = :estado"),
    @NamedQuery(name = "Sgtareas.findByAsignadoA", query = "SELECT s FROM Sgtareas s WHERE s.asignadoA = :asignadoA"),
    @NamedQuery(name = "Sgtareas.findByAsignadoPor", query = "SELECT s FROM Sgtareas s WHERE s.asignadoPor = :asignadoPor"),
    @NamedQuery(name = "Sgtareas.findByFechaCreacion", query = "SELECT s FROM Sgtareas s WHERE s.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Sgtareas.findByFechaFinalizacion", query = "SELECT s FROM Sgtareas s WHERE s.fechaFinalizacion = :fechaFinalizacion"),
    @NamedQuery(name = "Sgtareas.findByFechaRealizada", query = "SELECT s FROM Sgtareas s WHERE s.fechaRealizada = :fechaRealizada"),
    @NamedQuery(name = "Sgtareas.findByUrl", query = "SELECT s FROM Sgtareas s WHERE s.url = :url")})
public class Sgtareas implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgtareasPK sgtareasPK;
    @Lob
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "ESTADO")
    private String estado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ASIGNADO_A")
    private Double asignadoA;
    @Column(name = "ASIGNADO_POR")
    private Double asignadoPor;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "FECHA_FINALIZACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinalizacion;
    @Column(name = "FECHA_REALIZADA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRealizada;
    @Column(name = "URL")
    private String url;
    @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Sgsociedad sgsociedad;
    @JoinColumn(name = "IDTAREA", referencedColumnName = "IDTAREA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Sgttarea sgttarea;

    public Sgtareas() {
    }

    public Sgtareas(SgtareasPK sgtareasPK) {
        this.sgtareasPK = sgtareasPK;
    }

    public Sgtareas(String idsociedad, String idprocesos, String idtarea) {
        this.sgtareasPK = new SgtareasPK(idsociedad, idprocesos, idtarea);
    }

    public SgtareasPK getSgtareasPK() {
        return sgtareasPK;
    }

    public void setSgtareasPK(SgtareasPK sgtareasPK) {
        this.sgtareasPK = sgtareasPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getAsignadoA() {
        return asignadoA;
    }

    public void setAsignadoA(Double asignadoA) {
        this.asignadoA = asignadoA;
    }

    public Double getAsignadoPor() {
        return asignadoPor;
    }

    public void setAsignadoPor(Double asignadoPor) {
        this.asignadoPor = asignadoPor;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(Date fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public Date getFechaRealizada() {
        return fechaRealizada;
    }

    public void setFechaRealizada(Date fechaRealizada) {
        this.fechaRealizada = fechaRealizada;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Sgsociedad getSgsociedad() {
        return sgsociedad;
    }

    public void setSgsociedad(Sgsociedad sgsociedad) {
        this.sgsociedad = sgsociedad;
    }

    public Sgttarea getSgttarea() {
        return sgttarea;
    }

    public void setSgttarea(Sgttarea sgttarea) {
        this.sgttarea = sgttarea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgtareasPK != null ? sgtareasPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sgtareas)) {
            return false;
        }
        Sgtareas other = (Sgtareas) object;
        if ((this.sgtareasPK == null && other.sgtareasPK != null) || (this.sgtareasPK != null && !this.sgtareasPK.equals(other.sgtareasPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.Sgtareas[ sgtareasPK=" + sgtareasPK + " ]";
    }
    
}
