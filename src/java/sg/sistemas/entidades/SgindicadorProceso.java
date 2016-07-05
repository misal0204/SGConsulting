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
@Table(name = "sgindicador_proceso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgindicadorProceso.findAll", query = "SELECT s FROM SgindicadorProceso s"),
    @NamedQuery(name = "SgindicadorProceso.findByIdsociedad", query = "SELECT s FROM SgindicadorProceso s WHERE s.sgindicadorProcesoPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgindicadorProceso.findByIdobjIndicador", query = "SELECT s FROM SgindicadorProceso s WHERE s.sgindicadorProcesoPK.idobjIndicador = :idobjIndicador"),
    @NamedQuery(name = "SgindicadorProceso.findByIddept", query = "SELECT s FROM SgindicadorProceso s WHERE s.iddept = :iddept")})
public class SgindicadorProceso implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgindicadorProcesoPK sgindicadorProcesoPK;
    @Column(name = "IDDEPT")
    private String iddept;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "IDOBJ_INDICADOR", referencedColumnName = "IDOBJ_INDICADOR", insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private SgobjetivoIndicadores sgobjetivoIndicadores;

    public SgindicadorProceso() {
    }

    public SgindicadorProceso(SgindicadorProcesoPK sgindicadorProcesoPK) {
        this.sgindicadorProcesoPK = sgindicadorProcesoPK;
    }

    public SgindicadorProceso(String idsociedad, String idobjIndicador) {
        this.sgindicadorProcesoPK = new SgindicadorProcesoPK(idsociedad, idobjIndicador);
    }

    public SgindicadorProcesoPK getSgindicadorProcesoPK() {
        return sgindicadorProcesoPK;
    }

    public void setSgindicadorProcesoPK(SgindicadorProcesoPK sgindicadorProcesoPK) {
        this.sgindicadorProcesoPK = sgindicadorProcesoPK;
    }

    public String getIddept() {
        return iddept;
    }

    public void setIddept(String iddept) {
        this.iddept = iddept;
    }

    public SgobjetivoIndicadores getSgobjetivoIndicadores() {
        return sgobjetivoIndicadores;
    }

    public void setSgobjetivoIndicadores(SgobjetivoIndicadores sgobjetivoIndicadores) {
        this.sgobjetivoIndicadores = sgobjetivoIndicadores;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgindicadorProcesoPK != null ? sgindicadorProcesoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgindicadorProceso)) {
            return false;
        }
        SgindicadorProceso other = (SgindicadorProceso) object;
        if ((this.sgindicadorProcesoPK == null && other.sgindicadorProcesoPK != null) || (this.sgindicadorProcesoPK != null && !this.sgindicadorProcesoPK.equals(other.sgindicadorProcesoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgindicadorProceso[ sgindicadorProcesoPK=" + sgindicadorProcesoPK + " ]";
    }
    
}
