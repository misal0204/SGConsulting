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
@Table(name = "sgeficacia_rrhh")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgeficaciaRrhh.findAll", query = "SELECT s FROM SgeficaciaRrhh s"),
    @NamedQuery(name = "SgeficaciaRrhh.findByIdsociedad", query = "SELECT s FROM SgeficaciaRrhh s WHERE s.sgeficaciaRrhhPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgeficaciaRrhh.findByIdplanProg", query = "SELECT s FROM SgeficaciaRrhh s WHERE s.sgeficaciaRrhhPK.idplanProg = :idplanProg"),
    @NamedQuery(name = "SgeficaciaRrhh.findByFecha", query = "SELECT s FROM SgeficaciaRrhh s WHERE s.sgeficaciaRrhhPK.fecha = :fecha"),
    @NamedQuery(name = "SgeficaciaRrhh.findByCodUsuario", query = "SELECT s FROM SgeficaciaRrhh s WHERE s.sgeficaciaRrhhPK.codUsuario = :codUsuario"),
    @NamedQuery(name = "SgeficaciaRrhh.findByPregunta1", query = "SELECT s FROM SgeficaciaRrhh s WHERE s.pregunta1 = :pregunta1"),
    @NamedQuery(name = "SgeficaciaRrhh.findByObservacion1", query = "SELECT s FROM SgeficaciaRrhh s WHERE s.observacion1 = :observacion1"),
    @NamedQuery(name = "SgeficaciaRrhh.findByPregunta2", query = "SELECT s FROM SgeficaciaRrhh s WHERE s.pregunta2 = :pregunta2"),
    @NamedQuery(name = "SgeficaciaRrhh.findByObservacion2", query = "SELECT s FROM SgeficaciaRrhh s WHERE s.observacion2 = :observacion2"),
    @NamedQuery(name = "SgeficaciaRrhh.findByFechaRealizado", query = "SELECT s FROM SgeficaciaRrhh s WHERE s.fechaRealizado = :fechaRealizado")})
public class SgeficaciaRrhh implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgeficaciaRrhhPK sgeficaciaRrhhPK;
    @Column(name = "PREGUNTA_1")
    private String pregunta1;
    @Column(name = "OBSERVACION_1")
    private String observacion1;
    @Column(name = "PREGUNTA_2")
    private String pregunta2;
    @Column(name = "OBSERVACION_2")
    private String observacion2;
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

    public SgeficaciaRrhh() {
    }

    public SgeficaciaRrhh(SgeficaciaRrhhPK sgeficaciaRrhhPK) {
        this.sgeficaciaRrhhPK = sgeficaciaRrhhPK;
    }

    public SgeficaciaRrhh(String idsociedad, String idplanProg, Date fecha, String codUsuario) {
        this.sgeficaciaRrhhPK = new SgeficaciaRrhhPK(idsociedad, idplanProg, fecha, codUsuario);
    }

    public SgeficaciaRrhhPK getSgeficaciaRrhhPK() {
        return sgeficaciaRrhhPK;
    }

    public void setSgeficaciaRrhhPK(SgeficaciaRrhhPK sgeficaciaRrhhPK) {
        this.sgeficaciaRrhhPK = sgeficaciaRrhhPK;
    }

    public String getPregunta1() {
        return pregunta1;
    }

    public void setPregunta1(String pregunta1) {
        this.pregunta1 = pregunta1;
    }

    public String getObservacion1() {
        return observacion1;
    }

    public void setObservacion1(String observacion1) {
        this.observacion1 = observacion1;
    }

    public String getPregunta2() {
        return pregunta2;
    }

    public void setPregunta2(String pregunta2) {
        this.pregunta2 = pregunta2;
    }

    public String getObservacion2() {
        return observacion2;
    }

    public void setObservacion2(String observacion2) {
        this.observacion2 = observacion2;
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
        hash += (sgeficaciaRrhhPK != null ? sgeficaciaRrhhPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgeficaciaRrhh)) {
            return false;
        }
        SgeficaciaRrhh other = (SgeficaciaRrhh) object;
        if ((this.sgeficaciaRrhhPK == null && other.sgeficaciaRrhhPK != null) || (this.sgeficaciaRrhhPK != null && !this.sgeficaciaRrhhPK.equals(other.sgeficaciaRrhhPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgeficaciaRrhh[ sgeficaciaRrhhPK=" + sgeficaciaRrhhPK + " ]";
    }
    
}
