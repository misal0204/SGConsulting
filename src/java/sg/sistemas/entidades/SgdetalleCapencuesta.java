/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgdetalle_capencuesta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgdetalleCapencuesta.findAll", query = "SELECT s FROM SgdetalleCapencuesta s"),
    @NamedQuery(name = "SgdetalleCapencuesta.findByNumeroEncuesta", query = "SELECT s FROM SgdetalleCapencuesta s WHERE s.sgdetalleCapencuestaPK.numeroEncuesta = :numeroEncuesta"),
    @NamedQuery(name = "SgdetalleCapencuesta.findByIdencuesta", query = "SELECT s FROM SgdetalleCapencuesta s WHERE s.sgdetalleCapencuestaPK.idencuesta = :idencuesta"),
    @NamedQuery(name = "SgdetalleCapencuesta.findByPregunta", query = "SELECT s FROM SgdetalleCapencuesta s WHERE s.sgdetalleCapencuestaPK.pregunta = :pregunta"),
    @NamedQuery(name = "SgdetalleCapencuesta.findByIdtipoPregunta", query = "SELECT s FROM SgdetalleCapencuesta s WHERE s.idtipoPregunta = :idtipoPregunta"),
    @NamedQuery(name = "SgdetalleCapencuesta.findByRespuestaDesc", query = "SELECT s FROM SgdetalleCapencuesta s WHERE s.respuestaDesc = :respuestaDesc"),
    @NamedQuery(name = "SgdetalleCapencuesta.findByRespuestaNum", query = "SELECT s FROM SgdetalleCapencuesta s WHERE s.respuestaNum = :respuestaNum"),
    @NamedQuery(name = "SgdetalleCapencuesta.findByRespuestaSino", query = "SELECT s FROM SgdetalleCapencuesta s WHERE s.respuestaSino = :respuestaSino")})
public class SgdetalleCapencuesta implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgdetalleCapencuestaPK sgdetalleCapencuestaPK;
    @Column(name = "IDTIPO_PREGUNTA")
    private String idtipoPregunta;
    @Column(name = "RESPUESTA_DESC")
    private String respuestaDesc;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "RESPUESTA_NUM")
    private Double respuestaNum;
    @Column(name = "RESPUESTA_SINO")
    private String respuestaSino;
    @JoinColumns({
        @JoinColumn(name = "NUMERO_ENCUESTA", referencedColumnName = "NUMERO_ENCUESTA", insertable = false, updatable = false),
        @JoinColumn(name = "IDENCUESTA", referencedColumnName = "IDENCUESTA", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private SgcapturaEncuesta sgcapturaEncuesta;

    public SgdetalleCapencuesta() {
    }

    public SgdetalleCapencuesta(SgdetalleCapencuestaPK sgdetalleCapencuestaPK) {
        this.sgdetalleCapencuestaPK = sgdetalleCapencuestaPK;
    }

    public SgdetalleCapencuesta(String numeroEncuesta, String idencuesta, String pregunta) {
        this.sgdetalleCapencuestaPK = new SgdetalleCapencuestaPK(numeroEncuesta, idencuesta, pregunta);
    }

    public SgdetalleCapencuestaPK getSgdetalleCapencuestaPK() {
        return sgdetalleCapencuestaPK;
    }

    public void setSgdetalleCapencuestaPK(SgdetalleCapencuestaPK sgdetalleCapencuestaPK) {
        this.sgdetalleCapencuestaPK = sgdetalleCapencuestaPK;
    }

    public String getIdtipoPregunta() {
        return idtipoPregunta;
    }

    public void setIdtipoPregunta(String idtipoPregunta) {
        this.idtipoPregunta = idtipoPregunta;
    }

    public String getRespuestaDesc() {
        return respuestaDesc;
    }

    public void setRespuestaDesc(String respuestaDesc) {
        this.respuestaDesc = respuestaDesc;
    }

    public Double getRespuestaNum() {
        return respuestaNum;
    }

    public void setRespuestaNum(Double respuestaNum) {
        this.respuestaNum = respuestaNum;
    }

    public String getRespuestaSino() {
        return respuestaSino;
    }

    public void setRespuestaSino(String respuestaSino) {
        this.respuestaSino = respuestaSino;
    }

    public SgcapturaEncuesta getSgcapturaEncuesta() {
        return sgcapturaEncuesta;
    }

    public void setSgcapturaEncuesta(SgcapturaEncuesta sgcapturaEncuesta) {
        this.sgcapturaEncuesta = sgcapturaEncuesta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgdetalleCapencuestaPK != null ? sgdetalleCapencuestaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgdetalleCapencuesta)) {
            return false;
        }
        SgdetalleCapencuesta other = (SgdetalleCapencuesta) object;
        if ((this.sgdetalleCapencuestaPK == null && other.sgdetalleCapencuestaPK != null) || (this.sgdetalleCapencuestaPK != null && !this.sgdetalleCapencuestaPK.equals(other.sgdetalleCapencuestaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgdetalleCapencuesta[ sgdetalleCapencuestaPK=" + sgdetalleCapencuestaPK + " ]";
    }
    
}
