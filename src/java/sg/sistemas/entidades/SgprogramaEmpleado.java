/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgprograma_empleado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgprogramaEmpleado.findAll", query = "SELECT s FROM SgprogramaEmpleado s"),
    @NamedQuery(name = "SgprogramaEmpleado.findByIdsociedad", query = "SELECT s FROM SgprogramaEmpleado s WHERE s.sgprogramaEmpleadoPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgprogramaEmpleado.findByIdplanProg", query = "SELECT s FROM SgprogramaEmpleado s WHERE s.sgprogramaEmpleadoPK.idplanProg = :idplanProg"),
    @NamedQuery(name = "SgprogramaEmpleado.findByFecha", query = "SELECT s FROM SgprogramaEmpleado s WHERE s.sgprogramaEmpleadoPK.fecha = :fecha"),
    @NamedQuery(name = "SgprogramaEmpleado.findByCodUsuario", query = "SELECT s FROM SgprogramaEmpleado s WHERE s.sgprogramaEmpleadoPK.codUsuario = :codUsuario"),
    @NamedQuery(name = "SgprogramaEmpleado.findByEstado", query = "SELECT s FROM SgprogramaEmpleado s WHERE s.estado = :estado"),
    @NamedQuery(name = "SgprogramaEmpleado.findByFechaRealizado", query = "SELECT s FROM SgprogramaEmpleado s WHERE s.fechaRealizado = :fechaRealizado")})
public class SgprogramaEmpleado implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgprogramaEmpleadoPK sgprogramaEmpleadoPK;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "FECHA_REALIZADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRealizado;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "sgprogramaEmpleado")
    private SgeficaciaRrhh sgeficaciaRrhh;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "sgprogramaEmpleado")
    private SgeficaciaJefe sgeficaciaJefe;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "IDPLAN_PROG", referencedColumnName = "IDPLAN_PROG", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private SgplanPrograma sgplanPrograma;
    @JoinColumn(name = "COD_USUARIO", referencedColumnName = "COD_USUARIO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Sgusuario sgusuario;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "sgprogramaEmpleado")
    private SgeficaciaEmpleado sgeficaciaEmpleado;

    public SgprogramaEmpleado() {
    }

    public SgprogramaEmpleado(SgprogramaEmpleadoPK sgprogramaEmpleadoPK) {
        this.sgprogramaEmpleadoPK = sgprogramaEmpleadoPK;
    }

    public SgprogramaEmpleado(String idsociedad, String idplanProg, Date fecha, String codUsuario) {
        this.sgprogramaEmpleadoPK = new SgprogramaEmpleadoPK(idsociedad, idplanProg, fecha, codUsuario);
    }

    public SgprogramaEmpleadoPK getSgprogramaEmpleadoPK() {
        return sgprogramaEmpleadoPK;
    }

    public void setSgprogramaEmpleadoPK(SgprogramaEmpleadoPK sgprogramaEmpleadoPK) {
        this.sgprogramaEmpleadoPK = sgprogramaEmpleadoPK;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaRealizado() {
        return fechaRealizado;
    }

    public void setFechaRealizado(Date fechaRealizado) {
        this.fechaRealizado = fechaRealizado;
    }

    public SgeficaciaRrhh getSgeficaciaRrhh() {
        return sgeficaciaRrhh;
    }

    public void setSgeficaciaRrhh(SgeficaciaRrhh sgeficaciaRrhh) {
        this.sgeficaciaRrhh = sgeficaciaRrhh;
    }

    public SgeficaciaJefe getSgeficaciaJefe() {
        return sgeficaciaJefe;
    }

    public void setSgeficaciaJefe(SgeficaciaJefe sgeficaciaJefe) {
        this.sgeficaciaJefe = sgeficaciaJefe;
    }

    public SgplanPrograma getSgplanPrograma() {
        return sgplanPrograma;
    }

    public void setSgplanPrograma(SgplanPrograma sgplanPrograma) {
        this.sgplanPrograma = sgplanPrograma;
    }

    public Sgusuario getSgusuario() {
        return sgusuario;
    }

    public void setSgusuario(Sgusuario sgusuario) {
        this.sgusuario = sgusuario;
    }

    public SgeficaciaEmpleado getSgeficaciaEmpleado() {
        return sgeficaciaEmpleado;
    }

    public void setSgeficaciaEmpleado(SgeficaciaEmpleado sgeficaciaEmpleado) {
        this.sgeficaciaEmpleado = sgeficaciaEmpleado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgprogramaEmpleadoPK != null ? sgprogramaEmpleadoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgprogramaEmpleado)) {
            return false;
        }
        SgprogramaEmpleado other = (SgprogramaEmpleado) object;
        if ((this.sgprogramaEmpleadoPK == null && other.sgprogramaEmpleadoPK != null) || (this.sgprogramaEmpleadoPK != null && !this.sgprogramaEmpleadoPK.equals(other.sgprogramaEmpleadoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgprogramaEmpleado[ sgprogramaEmpleadoPK=" + sgprogramaEmpleadoPK + " ]";
    }
    
}
