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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgprocesos_re")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgprocesosRe.findAll", query = "SELECT s FROM SgprocesosRe s"),
    @NamedQuery(name = "SgprocesosRe.findByIdsociedad", query = "SELECT s FROM SgprocesosRe s WHERE s.sgprocesosRePK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgprocesosRe.findByNonc", query = "SELECT s FROM SgprocesosRe s WHERE s.sgprocesosRePK.nonc = :nonc"),
    @NamedQuery(name = "SgprocesosRe.findByIdmotivoRechazo", query = "SELECT s FROM SgprocesosRe s WHERE s.idmotivoRechazo = :idmotivoRechazo"),
    @NamedQuery(name = "SgprocesosRe.findByIddestinoRechazo", query = "SELECT s FROM SgprocesosRe s WHERE s.iddestinoRechazo = :iddestinoRechazo"),
    @NamedQuery(name = "SgprocesosRe.findByCantidad", query = "SELECT s FROM SgprocesosRe s WHERE s.cantidad = :cantidad")})
public class SgprocesosRe implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgprocesosRePK sgprocesosRePK;
    @Column(name = "IDMOTIVO_RECHAZO")
    private String idmotivoRechazo;
    @Column(name = "IDDESTINO_RECHAZO")
    private String iddestinoRechazo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "CANTIDAD")
    private Double cantidad;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "NONC", referencedColumnName = "NONC", insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private Sgnc sgnc;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "IDCLIENTE", referencedColumnName = "IDCLIENTE")})
    @ManyToOne(optional = false)
    private Sgcliente sgcliente;
    @JoinColumn(name = "IDMATERIAL", referencedColumnName = "IDMATERIAL")
    @ManyToOne
    private Sgmaterial idmaterial;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "IDPROVEEDOR", referencedColumnName = "IDPROVEEDOR")})
    @ManyToOne(optional = false)
    private Sgproveedores sgproveedores;

    public SgprocesosRe() {
    }

    public SgprocesosRe(SgprocesosRePK sgprocesosRePK) {
        this.sgprocesosRePK = sgprocesosRePK;
    }

    public SgprocesosRe(String idsociedad, String nonc) {
        this.sgprocesosRePK = new SgprocesosRePK(idsociedad, nonc);
    }

    public SgprocesosRePK getSgprocesosRePK() {
        return sgprocesosRePK;
    }

    public void setSgprocesosRePK(SgprocesosRePK sgprocesosRePK) {
        this.sgprocesosRePK = sgprocesosRePK;
    }

    public String getIdmotivoRechazo() {
        return idmotivoRechazo;
    }

    public void setIdmotivoRechazo(String idmotivoRechazo) {
        this.idmotivoRechazo = idmotivoRechazo;
    }

    public String getIddestinoRechazo() {
        return iddestinoRechazo;
    }

    public void setIddestinoRechazo(String iddestinoRechazo) {
        this.iddestinoRechazo = iddestinoRechazo;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Sgnc getSgnc() {
        return sgnc;
    }

    public void setSgnc(Sgnc sgnc) {
        this.sgnc = sgnc;
    }

    public Sgcliente getSgcliente() {
        return sgcliente;
    }

    public void setSgcliente(Sgcliente sgcliente) {
        this.sgcliente = sgcliente;
    }

    public Sgmaterial getIdmaterial() {
        return idmaterial;
    }

    public void setIdmaterial(Sgmaterial idmaterial) {
        this.idmaterial = idmaterial;
    }

    public Sgproveedores getSgproveedores() {
        return sgproveedores;
    }

    public void setSgproveedores(Sgproveedores sgproveedores) {
        this.sgproveedores = sgproveedores;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgprocesosRePK != null ? sgprocesosRePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgprocesosRe)) {
            return false;
        }
        SgprocesosRe other = (SgprocesosRe) object;
        if ((this.sgprocesosRePK == null && other.sgprocesosRePK != null) || (this.sgprocesosRePK != null && !this.sgprocesosRePK.equals(other.sgprocesosRePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgprocesosRe[ sgprocesosRePK=" + sgprocesosRePK + " ]";
    }
    
}
