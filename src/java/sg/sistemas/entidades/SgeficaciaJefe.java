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
@Table(name = "sgeficacia_jefe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgeficaciaJefe.findAll", query = "SELECT s FROM SgeficaciaJefe s"),
    @NamedQuery(name = "SgeficaciaJefe.findByIdsociedad", query = "SELECT s FROM SgeficaciaJefe s WHERE s.sgeficaciaJefePK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgeficaciaJefe.findByIdplanProg", query = "SELECT s FROM SgeficaciaJefe s WHERE s.sgeficaciaJefePK.idplanProg = :idplanProg"),
    @NamedQuery(name = "SgeficaciaJefe.findByFecha", query = "SELECT s FROM SgeficaciaJefe s WHERE s.sgeficaciaJefePK.fecha = :fecha"),
    @NamedQuery(name = "SgeficaciaJefe.findByCodUsuario", query = "SELECT s FROM SgeficaciaJefe s WHERE s.sgeficaciaJefePK.codUsuario = :codUsuario"),
    @NamedQuery(name = "SgeficaciaJefe.findByPregunta1", query = "SELECT s FROM SgeficaciaJefe s WHERE s.pregunta1 = :pregunta1"),
    @NamedQuery(name = "SgeficaciaJefe.findByObservacion1", query = "SELECT s FROM SgeficaciaJefe s WHERE s.observacion1 = :observacion1"),
    @NamedQuery(name = "SgeficaciaJefe.findByPregunta2", query = "SELECT s FROM SgeficaciaJefe s WHERE s.pregunta2 = :pregunta2"),
    @NamedQuery(name = "SgeficaciaJefe.findByObservacion2", query = "SELECT s FROM SgeficaciaJefe s WHERE s.observacion2 = :observacion2"),
    @NamedQuery(name = "SgeficaciaJefe.findByPregunta3", query = "SELECT s FROM SgeficaciaJefe s WHERE s.pregunta3 = :pregunta3"),
    @NamedQuery(name = "SgeficaciaJefe.findByObservacion3", query = "SELECT s FROM SgeficaciaJefe s WHERE s.observacion3 = :observacion3"),
    @NamedQuery(name = "SgeficaciaJefe.findByPregunta4", query = "SELECT s FROM SgeficaciaJefe s WHERE s.pregunta4 = :pregunta4"),
    @NamedQuery(name = "SgeficaciaJefe.findByObservacion4", query = "SELECT s FROM SgeficaciaJefe s WHERE s.observacion4 = :observacion4"),
    @NamedQuery(name = "SgeficaciaJefe.findByPregunta5", query = "SELECT s FROM SgeficaciaJefe s WHERE s.pregunta5 = :pregunta5"),
    @NamedQuery(name = "SgeficaciaJefe.findByObservacion5", query = "SELECT s FROM SgeficaciaJefe s WHERE s.observacion5 = :observacion5"),
    @NamedQuery(name = "SgeficaciaJefe.findByFechaRealizado", query = "SELECT s FROM SgeficaciaJefe s WHERE s.fechaRealizado = :fechaRealizado")})
public class SgeficaciaJefe implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgeficaciaJefePK sgeficaciaJefePK;
    @Column(name = "PREGUNTA_1")
    private String pregunta1;
    @Column(name = "OBSERVACION_1")
    private String observacion1;
    @Column(name = "PREGUNTA_2")
    private String pregunta2;
    @Column(name = "OBSERVACION_2")
    private String observacion2;
    @Column(name = "PREGUNTA_3")
    private String pregunta3;
    @Column(name = "OBSERVACION_3")
    private String observacion3;
    @Column(name = "PREGUNTA_4")
    private String pregunta4;
    @Column(name = "OBSERVACION_4")
    private String observacion4;
    @Column(name = "PREGUNTA_5")
    private String pregunta5;
    @Column(name = "OBSERVACION_5")
    private String observacion5;
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

    public SgeficaciaJefe() {
    }

    public SgeficaciaJefe(SgeficaciaJefePK sgeficaciaJefePK) {
        this.sgeficaciaJefePK = sgeficaciaJefePK;
    }

    public SgeficaciaJefe(String idsociedad, String idplanProg, Date fecha, String codUsuario) {
        this.sgeficaciaJefePK = new SgeficaciaJefePK(idsociedad, idplanProg, fecha, codUsuario);
    }

    public SgeficaciaJefePK getSgeficaciaJefePK() {
        return sgeficaciaJefePK;
    }

    public void setSgeficaciaJefePK(SgeficaciaJefePK sgeficaciaJefePK) {
        this.sgeficaciaJefePK = sgeficaciaJefePK;
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

    public String getPregunta3() {
        return pregunta3;
    }

    public void setPregunta3(String pregunta3) {
        this.pregunta3 = pregunta3;
    }

    public String getObservacion3() {
        return observacion3;
    }

    public void setObservacion3(String observacion3) {
        this.observacion3 = observacion3;
    }

    public String getPregunta4() {
        return pregunta4;
    }

    public void setPregunta4(String pregunta4) {
        this.pregunta4 = pregunta4;
    }

    public String getObservacion4() {
        return observacion4;
    }

    public void setObservacion4(String observacion4) {
        this.observacion4 = observacion4;
    }

    public String getPregunta5() {
        return pregunta5;
    }

    public void setPregunta5(String pregunta5) {
        this.pregunta5 = pregunta5;
    }

    public String getObservacion5() {
        return observacion5;
    }

    public void setObservacion5(String observacion5) {
        this.observacion5 = observacion5;
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
        hash += (sgeficaciaJefePK != null ? sgeficaciaJefePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgeficaciaJefe)) {
            return false;
        }
        SgeficaciaJefe other = (SgeficaciaJefe) object;
        if ((this.sgeficaciaJefePK == null && other.sgeficaciaJefePK != null) || (this.sgeficaciaJefePK != null && !this.sgeficaciaJefePK.equals(other.sgeficaciaJefePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgeficaciaJefe[ sgeficaciaJefePK=" + sgeficaciaJefePK + " ]";
    }
    
}
