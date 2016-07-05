/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgtipo_pregunta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgtipoPregunta.findAll", query = "SELECT s FROM SgtipoPregunta s"),
    @NamedQuery(name = "SgtipoPregunta.findByIdtipoPregunta", query = "SELECT s FROM SgtipoPregunta s WHERE s.idtipoPregunta = :idtipoPregunta"),
    @NamedQuery(name = "SgtipoPregunta.findByDescripcion", query = "SELECT s FROM SgtipoPregunta s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "SgtipoPregunta.findByTipoDato", query = "SELECT s FROM SgtipoPregunta s WHERE s.tipoDato = :tipoDato"),
    @NamedQuery(name = "SgtipoPregunta.findByResp1", query = "SELECT s FROM SgtipoPregunta s WHERE s.resp1 = :resp1"),
    @NamedQuery(name = "SgtipoPregunta.findByResp2", query = "SELECT s FROM SgtipoPregunta s WHERE s.resp2 = :resp2"),
    @NamedQuery(name = "SgtipoPregunta.findByResp3", query = "SELECT s FROM SgtipoPregunta s WHERE s.resp3 = :resp3"),
    @NamedQuery(name = "SgtipoPregunta.findByResp4", query = "SELECT s FROM SgtipoPregunta s WHERE s.resp4 = :resp4"),
    @NamedQuery(name = "SgtipoPregunta.findByResp5", query = "SELECT s FROM SgtipoPregunta s WHERE s.resp5 = :resp5")})
public class SgtipoPregunta implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "IDTIPO_PREGUNTA")
    private Double idtipoPregunta;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "TIPO_DATO")
    private String tipoDato;
    @Column(name = "RESP1")
    private String resp1;
    @Column(name = "RESP2")
    private String resp2;
    @Column(name = "RESP3")
    private String resp3;
    @Column(name = "RESP4")
    private String resp4;
    @Column(name = "RESP5")
    private String resp5;
    @OneToMany(mappedBy = "idtipoPregunta")
    private List<SgpEncuestaDetalle> sgpEncuestaDetalleList;

    public SgtipoPregunta() {
    }

    public SgtipoPregunta(Double idtipoPregunta) {
        this.idtipoPregunta = idtipoPregunta;
    }

    public Double getIdtipoPregunta() {
        return idtipoPregunta;
    }

    public void setIdtipoPregunta(Double idtipoPregunta) {
        this.idtipoPregunta = idtipoPregunta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    public String getResp1() {
        return resp1;
    }

    public void setResp1(String resp1) {
        this.resp1 = resp1;
    }

    public String getResp2() {
        return resp2;
    }

    public void setResp2(String resp2) {
        this.resp2 = resp2;
    }

    public String getResp3() {
        return resp3;
    }

    public void setResp3(String resp3) {
        this.resp3 = resp3;
    }

    public String getResp4() {
        return resp4;
    }

    public void setResp4(String resp4) {
        this.resp4 = resp4;
    }

    public String getResp5() {
        return resp5;
    }

    public void setResp5(String resp5) {
        this.resp5 = resp5;
    }

    @XmlTransient
    public List<SgpEncuestaDetalle> getSgpEncuestaDetalleList() {
        return sgpEncuestaDetalleList;
    }

    public void setSgpEncuestaDetalleList(List<SgpEncuestaDetalle> sgpEncuestaDetalleList) {
        this.sgpEncuestaDetalleList = sgpEncuestaDetalleList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipoPregunta != null ? idtipoPregunta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgtipoPregunta)) {
            return false;
        }
        SgtipoPregunta other = (SgtipoPregunta) object;
        if ((this.idtipoPregunta == null && other.idtipoPregunta != null) || (this.idtipoPregunta != null && !this.idtipoPregunta.equals(other.idtipoPregunta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgtipoPregunta[ idtipoPregunta=" + idtipoPregunta + " ]";
    }
    
}
