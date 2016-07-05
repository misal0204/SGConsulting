/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "sgcliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sgcliente.findAll", query = "SELECT s FROM Sgcliente s"),
    @NamedQuery(name = "Sgcliente.findByIdsociedad", query = "SELECT s FROM Sgcliente s WHERE s.sgclientePK.idsociedad = :idsociedad"),
    @NamedQuery(name = "Sgcliente.findByIdcliente", query = "SELECT s FROM Sgcliente s WHERE s.sgclientePK.idcliente = :idcliente"),
    @NamedQuery(name = "Sgcliente.findByAlias", query = "SELECT s FROM Sgcliente s WHERE s.alias = :alias"),
    @NamedQuery(name = "Sgcliente.findByNombre", query = "SELECT s FROM Sgcliente s WHERE s.nombre = :nombre"),
    @NamedQuery(name = "Sgcliente.findByDireccion", query = "SELECT s FROM Sgcliente s WHERE s.direccion = :direccion"),
    @NamedQuery(name = "Sgcliente.findByTelefono1", query = "SELECT s FROM Sgcliente s WHERE s.telefono1 = :telefono1"),
    @NamedQuery(name = "Sgcliente.findByTelefono2", query = "SELECT s FROM Sgcliente s WHERE s.telefono2 = :telefono2"),
    @NamedQuery(name = "Sgcliente.findByFax", query = "SELECT s FROM Sgcliente s WHERE s.fax = :fax")})
public class Sgcliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgclientePK sgclientePK;
    @Column(name = "ALIAS")
    private String alias;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "TELEFONO1")
    private String telefono1;
    @Column(name = "TELEFONO2")
    private String telefono2;
    @Column(name = "FAX")
    private String fax;
    @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Sgsociedad sgsociedad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgcliente")
    private List<SgprocesosRe> sgprocesosReList;

    public Sgcliente() {
    }

    public Sgcliente(SgclientePK sgclientePK) {
        this.sgclientePK = sgclientePK;
    }

    public Sgcliente(String idsociedad, String idcliente) {
        this.sgclientePK = new SgclientePK(idsociedad, idcliente);
    }

    public SgclientePK getSgclientePK() {
        return sgclientePK;
    }

    public void setSgclientePK(SgclientePK sgclientePK) {
        this.sgclientePK = sgclientePK;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Sgsociedad getSgsociedad() {
        return sgsociedad;
    }

    public void setSgsociedad(Sgsociedad sgsociedad) {
        this.sgsociedad = sgsociedad;
    }

    @XmlTransient
    public List<SgprocesosRe> getSgprocesosReList() {
        return sgprocesosReList;
    }

    public void setSgprocesosReList(List<SgprocesosRe> sgprocesosReList) {
        this.sgprocesosReList = sgprocesosReList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgclientePK != null ? sgclientePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sgcliente)) {
            return false;
        }
        Sgcliente other = (Sgcliente) object;
        if ((this.sgclientePK == null && other.sgclientePK != null) || (this.sgclientePK != null && !this.sgclientePK.equals(other.sgclientePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.Sgcliente[ sgclientePK=" + sgclientePK + " ]";
    }
    
}
