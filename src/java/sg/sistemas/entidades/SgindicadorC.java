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
import javax.persistence.Lob;
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
@Table(name = "sgindicador_c")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgindicadorC.findAll", query = "SELECT s FROM SgindicadorC s"),
    @NamedQuery(name = "SgindicadorC.findByIdsociedad", query = "SELECT s FROM SgindicadorC s WHERE s.sgindicadorCPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgindicadorC.findByIdobjIndicador", query = "SELECT s FROM SgindicadorC s WHERE s.sgindicadorCPK.idobjIndicador = :idobjIndicador"),
    @NamedQuery(name = "SgindicadorC.findByIddetalleIo", query = "SELECT s FROM SgindicadorC s WHERE s.sgindicadorCPK.iddetalleIo = :iddetalleIo"),
    @NamedQuery(name = "SgindicadorC.findByFecha", query = "SELECT s FROM SgindicadorC s WHERE s.fecha = :fecha"),
    @NamedQuery(name = "SgindicadorC.findByValor", query = "SELECT s FROM SgindicadorC s WHERE s.valor = :valor"),
    @NamedQuery(name = "SgindicadorC.findByComentario", query = "SELECT s FROM SgindicadorC s WHERE s.comentario = :comentario")})
public class SgindicadorC implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgindicadorCPK sgindicadorCPK;
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALOR")
    private Double valor;
    @Lob
    @Column(name = "OBSERVACION")
    private String observacion;
    @Column(name = "COMENTARIO")
    private String comentario;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "IDOBJ_INDICADOR", referencedColumnName = "IDOBJ_INDICADOR", insertable = false, updatable = false),
        @JoinColumn(name = "IDDETALLE_IO", referencedColumnName = "IDDETALLE_IO", insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private SgdetalleObjInd sgdetalleObjInd;

    public SgindicadorC() {
    }

    public SgindicadorC(SgindicadorCPK sgindicadorCPK) {
        this.sgindicadorCPK = sgindicadorCPK;
    }

    public SgindicadorC(String idsociedad, String idobjIndicador, String iddetalleIo) {
        this.sgindicadorCPK = new SgindicadorCPK(idsociedad, idobjIndicador, iddetalleIo);
    }

    public SgindicadorCPK getSgindicadorCPK() {
        return sgindicadorCPK;
    }

    public void setSgindicadorCPK(SgindicadorCPK sgindicadorCPK) {
        this.sgindicadorCPK = sgindicadorCPK;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
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

    public SgdetalleObjInd getSgdetalleObjInd() {
        return sgdetalleObjInd;
    }

    public void setSgdetalleObjInd(SgdetalleObjInd sgdetalleObjInd) {
        this.sgdetalleObjInd = sgdetalleObjInd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgindicadorCPK != null ? sgindicadorCPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgindicadorC)) {
            return false;
        }
        SgindicadorC other = (SgindicadorC) object;
        if ((this.sgindicadorCPK == null && other.sgindicadorCPK != null) || (this.sgindicadorCPK != null && !this.sgindicadorCPK.equals(other.sgindicadorCPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgindicadorC[ sgindicadorCPK=" + sgindicadorCPK + " ]";
    }
    
}
