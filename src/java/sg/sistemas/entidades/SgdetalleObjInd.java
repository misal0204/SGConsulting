/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgdetalle_obj_ind")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgdetalleObjInd.findAll", query = "SELECT s FROM SgdetalleObjInd s"),
    @NamedQuery(name = "SgdetalleObjInd.findByIdsociedad", query = "SELECT s FROM SgdetalleObjInd s WHERE s.sgdetalleObjIndPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgdetalleObjInd.findByIdobjIndicador", query = "SELECT s FROM SgdetalleObjInd s WHERE s.sgdetalleObjIndPK.idobjIndicador = :idobjIndicador"),
    @NamedQuery(name = "SgdetalleObjInd.findByIddetalleIo", query = "SELECT s FROM SgdetalleObjInd s WHERE s.sgdetalleObjIndPK.iddetalleIo = :iddetalleIo"),
    @NamedQuery(name = "SgdetalleObjInd.findByIdtipoIo", query = "SELECT s FROM SgdetalleObjInd s WHERE s.idtipoIo = :idtipoIo"),
    @NamedQuery(name = "SgdetalleObjInd.findByPonderacion", query = "SELECT s FROM SgdetalleObjInd s WHERE s.ponderacion = :ponderacion"),
    @NamedQuery(name = "SgdetalleObjInd.findByMeta", query = "SELECT s FROM SgdetalleObjInd s WHERE s.meta = :meta"),
    @NamedQuery(name = "SgdetalleObjInd.findByFechaCalculo", query = "SELECT s FROM SgdetalleObjInd s WHERE s.fechaCalculo = :fechaCalculo"),
    @NamedQuery(name = "SgdetalleObjInd.findByAlertaRango1", query = "SELECT s FROM SgdetalleObjInd s WHERE s.alertaRango1 = :alertaRango1"),
    @NamedQuery(name = "SgdetalleObjInd.findByAlertaRango2", query = "SELECT s FROM SgdetalleObjInd s WHERE s.alertaRango2 = :alertaRango2"),
    @NamedQuery(name = "SgdetalleObjInd.findByComentario", query = "SELECT s FROM SgdetalleObjInd s WHERE s.comentario = :comentario")})
public class SgdetalleObjInd implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgdetalleObjIndPK sgdetalleObjIndPK;
    @Column(name = "IDTIPO_IO")
    private String idtipoIo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PONDERACION")
    private Double ponderacion;
    @Column(name = "META")
    private Double meta;
    @Column(name = "FECHA_CALCULO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCalculo;
    @Column(name = "ALERTA_RANGO1")
    private Double alertaRango1;
    @Column(name = "ALERTA_RANGO2")
    private Double alertaRango2;
    @Lob
    @Column(name = "OBSERVACION")
    private String observacion;
    @Column(name = "COMENTARIO")
    private String comentario;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "IDOBJ_INDICADOR", referencedColumnName = "IDOBJ_INDICADOR", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private SgobjetivoIndicadores sgobjetivoIndicadores;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgdetalleObjInd")
    private List<SgseguimientoIndObj> sgseguimientoIndObjList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "sgdetalleObjInd")
    private SgindicadorC sgindicadorC;

    public SgdetalleObjInd() {
    }

    public SgdetalleObjInd(SgdetalleObjIndPK sgdetalleObjIndPK) {
        this.sgdetalleObjIndPK = sgdetalleObjIndPK;
    }

    public SgdetalleObjInd(String idsociedad, String idobjIndicador, String iddetalleIo) {
        this.sgdetalleObjIndPK = new SgdetalleObjIndPK(idsociedad, idobjIndicador, iddetalleIo);
    }

    public SgdetalleObjIndPK getSgdetalleObjIndPK() {
        return sgdetalleObjIndPK;
    }

    public void setSgdetalleObjIndPK(SgdetalleObjIndPK sgdetalleObjIndPK) {
        this.sgdetalleObjIndPK = sgdetalleObjIndPK;
    }

    public String getIdtipoIo() {
        return idtipoIo;
    }

    public void setIdtipoIo(String idtipoIo) {
        this.idtipoIo = idtipoIo;
    }

    public Double getPonderacion() {
        return ponderacion;
    }

    public void setPonderacion(Double ponderacion) {
        this.ponderacion = ponderacion;
    }

    public Double getMeta() {
        return meta;
    }

    public void setMeta(Double meta) {
        this.meta = meta;
    }

    public Date getFechaCalculo() {
        return fechaCalculo;
    }

    public void setFechaCalculo(Date fechaCalculo) {
        this.fechaCalculo = fechaCalculo;
    }

    public Double getAlertaRango1() {
        return alertaRango1;
    }

    public void setAlertaRango1(Double alertaRango1) {
        this.alertaRango1 = alertaRango1;
    }

    public Double getAlertaRango2() {
        return alertaRango2;
    }

    public void setAlertaRango2(Double alertaRango2) {
        this.alertaRango2 = alertaRango2;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public SgobjetivoIndicadores getSgobjetivoIndicadores() {
        return sgobjetivoIndicadores;
    }

    public void setSgobjetivoIndicadores(SgobjetivoIndicadores sgobjetivoIndicadores) {
        this.sgobjetivoIndicadores = sgobjetivoIndicadores;
    }

    @XmlTransient
    public List<SgseguimientoIndObj> getSgseguimientoIndObjList() {
        return sgseguimientoIndObjList;
    }

    public void setSgseguimientoIndObjList(List<SgseguimientoIndObj> sgseguimientoIndObjList) {
        this.sgseguimientoIndObjList = sgseguimientoIndObjList;
    }

    public SgindicadorC getSgindicadorC() {
        return sgindicadorC;
    }

    public void setSgindicadorC(SgindicadorC sgindicadorC) {
        this.sgindicadorC = sgindicadorC;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgdetalleObjIndPK != null ? sgdetalleObjIndPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgdetalleObjInd)) {
            return false;
        }
        SgdetalleObjInd other = (SgdetalleObjInd) object;
        if ((this.sgdetalleObjIndPK == null && other.sgdetalleObjIndPK != null) || (this.sgdetalleObjIndPK != null && !this.sgdetalleObjIndPK.equals(other.sgdetalleObjIndPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgdetalleObjInd[ sgdetalleObjIndPK=" + sgdetalleObjIndPK + " ]";
    }
    
}
