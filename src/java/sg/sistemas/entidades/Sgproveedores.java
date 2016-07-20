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
@Table(name = "sgproveedores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sgproveedores.findAll", query = "SELECT s FROM Sgproveedores s"),
    @NamedQuery(name = "Sgproveedores.findByIdsociedad", query = "SELECT s FROM Sgproveedores s WHERE s.sgproveedoresPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "Sgproveedores.findByIdproveedor", query = "SELECT s FROM Sgproveedores s WHERE s.sgproveedoresPK.idproveedor = :idproveedor"),
    @NamedQuery(name = "Sgproveedores.findByAlias", query = "SELECT s FROM Sgproveedores s WHERE s.alias = :alias"),
    @NamedQuery(name = "Sgproveedores.findByNombre", query = "SELECT s FROM Sgproveedores s WHERE s.nombre = :nombre"),
    @NamedQuery(name = "Sgproveedores.findByDireccion", query = "SELECT s FROM Sgproveedores s WHERE s.direccion = :direccion"),
    @NamedQuery(name = "Sgproveedores.findByTelefono1", query = "SELECT s FROM Sgproveedores s WHERE s.telefono1 = :telefono1"),
    @NamedQuery(name = "Sgproveedores.findByTelefono2", query = "SELECT s FROM Sgproveedores s WHERE s.telefono2 = :telefono2"),
    @NamedQuery(name = "Sgproveedores.findByFax", query = "SELECT s FROM Sgproveedores s WHERE s.fax = :fax")})
public class Sgproveedores implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgproveedoresPK sgproveedoresPK;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgproveedores")
    private List<SgprocesosRe> sgprocesosReList;

    public Sgproveedores() {
    }

    public Sgproveedores(SgproveedoresPK sgproveedoresPK) {
        this.sgproveedoresPK = sgproveedoresPK;
    }

    public Sgproveedores(String idsociedad, String idproveedor) {
        this.sgproveedoresPK = new SgproveedoresPK(idsociedad, idproveedor);
    }

    public SgproveedoresPK getSgproveedoresPK() {
        return sgproveedoresPK;
    }

    public void setSgproveedoresPK(SgproveedoresPK sgproveedoresPK) {
        this.sgproveedoresPK = sgproveedoresPK;
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
        hash += (sgproveedoresPK != null ? sgproveedoresPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sgproveedores)) {
            return false;
        }
        Sgproveedores other = (Sgproveedores) object;
        if ((this.sgproveedoresPK == null && other.sgproveedoresPK != null) || (this.sgproveedoresPK != null && !this.sgproveedoresPK.equals(other.sgproveedoresPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.Sgproveedores[ sgproveedoresPK=" + sgproveedoresPK + " ]";
    }
    
}
