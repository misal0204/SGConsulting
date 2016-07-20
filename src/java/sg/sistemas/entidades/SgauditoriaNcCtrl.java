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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "sgauditoria_nc_ctrl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgauditoriaNcCtrl.findAll", query = "SELECT s FROM SgauditoriaNcCtrl s"),
    @NamedQuery(name = "SgauditoriaNcCtrl.findByIdregistro", query = "SELECT s FROM SgauditoriaNcCtrl s WHERE s.idregistro = :idregistro"),
    @NamedQuery(name = "SgauditoriaNcCtrl.findByFecha", query = "SELECT s FROM SgauditoriaNcCtrl s WHERE s.fecha = :fecha"),
    @NamedQuery(name = "SgauditoriaNcCtrl.findByNombreTabla", query = "SELECT s FROM SgauditoriaNcCtrl s WHERE s.nombreTabla = :nombreTabla"),
    @NamedQuery(name = "SgauditoriaNcCtrl.findByNombreCampo", query = "SELECT s FROM SgauditoriaNcCtrl s WHERE s.nombreCampo = :nombreCampo"),
    @NamedQuery(name = "SgauditoriaNcCtrl.findByCreadoPor", query = "SELECT s FROM SgauditoriaNcCtrl s WHERE s.creadoPor = :creadoPor"),
    @NamedQuery(name = "SgauditoriaNcCtrl.findByRegistroCreado", query = "SELECT s FROM SgauditoriaNcCtrl s WHERE s.registroCreado = :registroCreado"),
    @NamedQuery(name = "SgauditoriaNcCtrl.findByFechaUpd", query = "SELECT s FROM SgauditoriaNcCtrl s WHERE s.fechaUpd = :fechaUpd"),
    @NamedQuery(name = "SgauditoriaNcCtrl.findByRealizadoPor", query = "SELECT s FROM SgauditoriaNcCtrl s WHERE s.realizadoPor = :realizadoPor")})
public class SgauditoriaNcCtrl implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "IDREGISTRO")
    private Double idregistro;
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "NOMBRE_TABLA")
    private String nombreTabla;
    @Column(name = "NOMBRE_CAMPO")
    private String nombreCampo;
    @Column(name = "CREADO_POR")
    private String creadoPor;
    @Column(name = "REGISTRO_CREADO")
    private String registroCreado;
    @Column(name = "FECHA_UPD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaUpd;
    @Column(name = "REALIZADO_POR")
    private String realizadoPor;
    @Lob
    @Column(name = "REGISTRO_ANTERIOR")
    private String registroAnterior;
    @Lob
    @Column(name = "REGISTRO_NUEVO")
    private String registroNuevo;

    public SgauditoriaNcCtrl() {
    }

    public SgauditoriaNcCtrl(Double idregistro) {
        this.idregistro = idregistro;
    }

    public SgauditoriaNcCtrl(Double idregistro, Date fecha) {
        this.idregistro = idregistro;
        this.fecha = fecha;
    }

    public Double getIdregistro() {
        return idregistro;
    }

    public void setIdregistro(Double idregistro) {
        this.idregistro = idregistro;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombreTabla() {
        return nombreTabla;
    }

    public void setNombreTabla(String nombreTabla) {
        this.nombreTabla = nombreTabla;
    }

    public String getNombreCampo() {
        return nombreCampo;
    }

    public void setNombreCampo(String nombreCampo) {
        this.nombreCampo = nombreCampo;
    }

    public String getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }

    public String getRegistroCreado() {
        return registroCreado;
    }

    public void setRegistroCreado(String registroCreado) {
        this.registroCreado = registroCreado;
    }

    public Date getFechaUpd() {
        return fechaUpd;
    }

    public void setFechaUpd(Date fechaUpd) {
        this.fechaUpd = fechaUpd;
    }

    public String getRealizadoPor() {
        return realizadoPor;
    }

    public void setRealizadoPor(String realizadoPor) {
        this.realizadoPor = realizadoPor;
    }

    public String getRegistroAnterior() {
        return registroAnterior;
    }

    public void setRegistroAnterior(String registroAnterior) {
        this.registroAnterior = registroAnterior;
    }

    public String getRegistroNuevo() {
        return registroNuevo;
    }

    public void setRegistroNuevo(String registroNuevo) {
        this.registroNuevo = registroNuevo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idregistro != null ? idregistro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgauditoriaNcCtrl)) {
            return false;
        }
        SgauditoriaNcCtrl other = (SgauditoriaNcCtrl) object;
        if ((this.idregistro == null && other.idregistro != null) || (this.idregistro != null && !this.idregistro.equals(other.idregistro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgauditoriaNcCtrl[ idregistro=" + idregistro + " ]";
    }
    
}
