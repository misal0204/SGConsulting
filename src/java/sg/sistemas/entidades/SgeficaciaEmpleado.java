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
@Table(name = "sgeficacia_empleado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgeficaciaEmpleado.findAll", query = "SELECT s FROM SgeficaciaEmpleado s"),
    @NamedQuery(name = "SgeficaciaEmpleado.findByIdsociedad", query = "SELECT s FROM SgeficaciaEmpleado s WHERE s.sgeficaciaEmpleadoPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgeficaciaEmpleado.findByIdplanProg", query = "SELECT s FROM SgeficaciaEmpleado s WHERE s.sgeficaciaEmpleadoPK.idplanProg = :idplanProg"),
    @NamedQuery(name = "SgeficaciaEmpleado.findByFecha", query = "SELECT s FROM SgeficaciaEmpleado s WHERE s.sgeficaciaEmpleadoPK.fecha = :fecha"),
    @NamedQuery(name = "SgeficaciaEmpleado.findByCodUsuario", query = "SELECT s FROM SgeficaciaEmpleado s WHERE s.sgeficaciaEmpleadoPK.codUsuario = :codUsuario"),
    @NamedQuery(name = "SgeficaciaEmpleado.findByPregunta1", query = "SELECT s FROM SgeficaciaEmpleado s WHERE s.pregunta1 = :pregunta1"),
    @NamedQuery(name = "SgeficaciaEmpleado.findByAntes1", query = "SELECT s FROM SgeficaciaEmpleado s WHERE s.antes1 = :antes1"),
    @NamedQuery(name = "SgeficaciaEmpleado.findByDespues1", query = "SELECT s FROM SgeficaciaEmpleado s WHERE s.despues1 = :despues1"),
    @NamedQuery(name = "SgeficaciaEmpleado.findByPregunta2", query = "SELECT s FROM SgeficaciaEmpleado s WHERE s.pregunta2 = :pregunta2"),
    @NamedQuery(name = "SgeficaciaEmpleado.findByAntes2", query = "SELECT s FROM SgeficaciaEmpleado s WHERE s.antes2 = :antes2"),
    @NamedQuery(name = "SgeficaciaEmpleado.findByDespues2", query = "SELECT s FROM SgeficaciaEmpleado s WHERE s.despues2 = :despues2"),
    @NamedQuery(name = "SgeficaciaEmpleado.findByFechaRealizado", query = "SELECT s FROM SgeficaciaEmpleado s WHERE s.fechaRealizado = :fechaRealizado")})
public class SgeficaciaEmpleado implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgeficaciaEmpleadoPK sgeficaciaEmpleadoPK;
    @Column(name = "PREGUNTA_1")
    private String pregunta1;
    @Column(name = "ANTES_1")
    private String antes1;
    @Column(name = "DESPUES_1")
    private String despues1;
    @Column(name = "PREGUNTA_2")
    private String pregunta2;
    @Column(name = "ANTES_2")
    private String antes2;
    @Column(name = "DESPUES_2")
    private String despues2;
    @Column(name = "FECHA_REALIZADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRealizado;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "IDPLAN_PROG", referencedColumnName = "IDPLAN_PROG", insertable = false, updatable = false),
        @JoinColumn(name = "FECHA", referencedColumnName = "FECHA", insertable = false, updatable = false),
        @JoinColumn(name = "COD_USUARIO", referencedColumnName = "COD_USUARIO", insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private SgprogramaEmpleado sgprogramaEmpleado;

    public SgeficaciaEmpleado() {
    }

    public SgeficaciaEmpleado(SgeficaciaEmpleadoPK sgeficaciaEmpleadoPK) {
        this.sgeficaciaEmpleadoPK = sgeficaciaEmpleadoPK;
    }

    public SgeficaciaEmpleado(String idsociedad, String idplanProg, Date fecha, String codUsuario) {
        this.sgeficaciaEmpleadoPK = new SgeficaciaEmpleadoPK(idsociedad, idplanProg, fecha, codUsuario);
    }

    public SgeficaciaEmpleadoPK getSgeficaciaEmpleadoPK() {
        return sgeficaciaEmpleadoPK;
    }

    public void setSgeficaciaEmpleadoPK(SgeficaciaEmpleadoPK sgeficaciaEmpleadoPK) {
        this.sgeficaciaEmpleadoPK = sgeficaciaEmpleadoPK;
    }

    public String getPregunta1() {
        return pregunta1;
    }

    public void setPregunta1(String pregunta1) {
        this.pregunta1 = pregunta1;
    }

    public String getAntes1() {
        return antes1;
    }

    public void setAntes1(String antes1) {
        this.antes1 = antes1;
    }

    public String getDespues1() {
        return despues1;
    }

    public void setDespues1(String despues1) {
        this.despues1 = despues1;
    }

    public String getPregunta2() {
        return pregunta2;
    }

    public void setPregunta2(String pregunta2) {
        this.pregunta2 = pregunta2;
    }

    public String getAntes2() {
        return antes2;
    }

    public void setAntes2(String antes2) {
        this.antes2 = antes2;
    }

    public String getDespues2() {
        return despues2;
    }

    public void setDespues2(String despues2) {
        this.despues2 = despues2;
    }

    public Date getFechaRealizado() {
        return fechaRealizado;
    }

    public void setFechaRealizado(Date fechaRealizado) {
        this.fechaRealizado = fechaRealizado;
    }

    public SgprogramaEmpleado getSgprogramaEmpleado() {
        return sgprogramaEmpleado;
    }

    public void setSgprogramaEmpleado(SgprogramaEmpleado sgprogramaEmpleado) {
        this.sgprogramaEmpleado = sgprogramaEmpleado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgeficaciaEmpleadoPK != null ? sgeficaciaEmpleadoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgeficaciaEmpleado)) {
            return false;
        }
        SgeficaciaEmpleado other = (SgeficaciaEmpleado) object;
        if ((this.sgeficaciaEmpleadoPK == null && other.sgeficaciaEmpleadoPK != null) || (this.sgeficaciaEmpleadoPK != null && !this.sgeficaciaEmpleadoPK.equals(other.sgeficaciaEmpleadoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgeficaciaEmpleado[ sgeficaciaEmpleadoPK=" + sgeficaciaEmpleadoPK + " ]";
    }
    
}
