/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgencuesta_detalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgencuestaDetalle.findAll", query = "SELECT s FROM SgencuestaDetalle s"),
    @NamedQuery(name = "SgencuestaDetalle.findByIdsociedad", query = "SELECT s FROM SgencuestaDetalle s WHERE s.sgencuestaDetallePK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgencuestaDetalle.findByNoenc", query = "SELECT s FROM SgencuestaDetalle s WHERE s.sgencuestaDetallePK.noenc = :noenc"),
    @NamedQuery(name = "SgencuestaDetalle.findByIdseccion", query = "SELECT s FROM SgencuestaDetalle s WHERE s.idseccion = :idseccion"),
    @NamedQuery(name = "SgencuestaDetalle.findByPregunta", query = "SELECT s FROM SgencuestaDetalle s WHERE s.pregunta = :pregunta"),
    @NamedQuery(name = "SgencuestaDetalle.findByRespNum", query = "SELECT s FROM SgencuestaDetalle s WHERE s.respNum = :respNum"),
    @NamedQuery(name = "SgencuestaDetalle.findByRespChar", query = "SELECT s FROM SgencuestaDetalle s WHERE s.respChar = :respChar"),
    @NamedQuery(name = "SgencuestaDetalle.findByRespDesc", query = "SELECT s FROM SgencuestaDetalle s WHERE s.respDesc = :respDesc")})
public class SgencuestaDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgencuestaDetallePK sgencuestaDetallePK;
    @Column(name = "IDSECCION")
    private String idseccion;
    @Column(name = "PREGUNTA")
    private String pregunta;
    @Basic(optional = false)
    @Column(name = "RESP_NUM")
    private double respNum;
    @Column(name = "RESP_CHAR")
    private String respChar;
    @Column(name = "RESP_DESC")
    private String respDesc;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "NOENC", referencedColumnName = "NOENC", insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private SgcabEncuesta sgcabEncuesta;

    public SgencuestaDetalle() {
    }

    public SgencuestaDetalle(SgencuestaDetallePK sgencuestaDetallePK) {
        this.sgencuestaDetallePK = sgencuestaDetallePK;
    }

    public SgencuestaDetalle(SgencuestaDetallePK sgencuestaDetallePK, double respNum) {
        this.sgencuestaDetallePK = sgencuestaDetallePK;
        this.respNum = respNum;
    }

    public SgencuestaDetalle(String idsociedad, String noenc) {
        this.sgencuestaDetallePK = new SgencuestaDetallePK(idsociedad, noenc);
    }

    public SgencuestaDetallePK getSgencuestaDetallePK() {
        return sgencuestaDetallePK;
    }

    public void setSgencuestaDetallePK(SgencuestaDetallePK sgencuestaDetallePK) {
        this.sgencuestaDetallePK = sgencuestaDetallePK;
    }

    public String getIdseccion() {
        return idseccion;
    }

    public void setIdseccion(String idseccion) {
        this.idseccion = idseccion;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public double getRespNum() {
        return respNum;
    }

    public void setRespNum(double respNum) {
        this.respNum = respNum;
    }

    public String getRespChar() {
        return respChar;
    }

    public void setRespChar(String respChar) {
        this.respChar = respChar;
    }

    public String getRespDesc() {
        return respDesc;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }

    public SgcabEncuesta getSgcabEncuesta() {
        return sgcabEncuesta;
    }

    public void setSgcabEncuesta(SgcabEncuesta sgcabEncuesta) {
        this.sgcabEncuesta = sgcabEncuesta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgencuestaDetallePK != null ? sgencuestaDetallePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgencuestaDetalle)) {
            return false;
        }
        SgencuestaDetalle other = (SgencuestaDetalle) object;
        if ((this.sgencuestaDetallePK == null && other.sgencuestaDetallePK != null) || (this.sgencuestaDetallePK != null && !this.sgencuestaDetallePK.equals(other.sgencuestaDetallePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgencuestaDetalle[ sgencuestaDetallePK=" + sgencuestaDetallePK + " ]";
    }
    
}
