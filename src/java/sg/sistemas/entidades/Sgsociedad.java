/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgsociedad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sgsociedad.findAll", query = "SELECT s FROM Sgsociedad s"),
    @NamedQuery(name = "Sgsociedad.findByIdsociedad", query = "SELECT s FROM Sgsociedad s WHERE s.idsociedad = :idsociedad"),
    @NamedQuery(name = "Sgsociedad.findByDescripcion", query = "SELECT s FROM Sgsociedad s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "Sgsociedad.findByIcon", query = "SELECT s FROM Sgsociedad s WHERE s.icon = :icon"),
    @NamedQuery(name = "Sgsociedad.findByImagen", query = "SELECT s FROM Sgsociedad s WHERE s.imagen = :imagen"),
    @NamedQuery(name = "Sgsociedad.findByLogo", query = "SELECT s FROM Sgsociedad s WHERE s.logo = :logo")})
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(
            name = "SP_INSERT_SGSOCIEDAD",
            procedureName = "SP_INSERT_SGSOCIEDAD",
            parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "p_idsociedad"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "p_descripcion"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "p_icon"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "p_imagen"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "p_logo"),
                @StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "p_resultado")
            }
    ),
    @NamedStoredProcedureQuery(
            name = "SP_UPDATE_SGSOCIEDAD",
            procedureName = "SP_UPDATE_SGSOCIEDAD",
            parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "p_idsociedad"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "p_descripcion"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "p_icon"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "p_imagen"),
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "p_logo"),
                @StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "p_resultado")
            }
    ),
    @NamedStoredProcedureQuery(
            name = "SP_DELETE_SGSOCIEDAD",
            procedureName = "SP_DELETE_SGSOCIEDAD",
            parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "p_idsociedad"),
                @StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "p_resultado")
            }
    )
})
public class Sgsociedad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDSOCIEDAD")
    private String idsociedad;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "ICON")
    private String icon;
    @Column(name = "IMAGEN")
    private String imagen;
    @Column(name = "LOGO")
    private String logo;
    @ManyToMany(mappedBy = "sgsociedadList")
    private List<Sgcentro> sgcentroList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgsociedad")
    private List<Sgproveedores> sgproveedoresList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgsociedad")
    private List<Sgcliente> sgclienteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgsociedad")
    private List<Sgparametros> sgparametrosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgsociedad")
    private List<Sgtareas> sgtareasList;

    public Sgsociedad() {
    }

    public Sgsociedad(String idsociedad) {
        this.idsociedad = idsociedad;
    }

    public String getIdsociedad() {
        return idsociedad;
    }

    public void setIdsociedad(String idsociedad) {
        this.idsociedad = idsociedad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @XmlTransient
    public List<Sgcentro> getSgcentroList() {
        return sgcentroList;
    }

    public void setSgcentroList(List<Sgcentro> sgcentroList) {
        this.sgcentroList = sgcentroList;
    }

    @XmlTransient
    public List<Sgproveedores> getSgproveedoresList() {
        return sgproveedoresList;
    }

    public void setSgproveedoresList(List<Sgproveedores> sgproveedoresList) {
        this.sgproveedoresList = sgproveedoresList;
    }

    @XmlTransient
    public List<Sgcliente> getSgclienteList() {
        return sgclienteList;
    }

    public void setSgclienteList(List<Sgcliente> sgclienteList) {
        this.sgclienteList = sgclienteList;
    }

    @XmlTransient
    public List<Sgparametros> getSgparametrosList() {
        return sgparametrosList;
    }

    public void setSgparametrosList(List<Sgparametros> sgparametrosList) {
        this.sgparametrosList = sgparametrosList;
    }

    @XmlTransient
    public List<Sgtareas> getSgtareasList() {
        return sgtareasList;
    }

    public void setSgtareasList(List<Sgtareas> sgtareasList) {
        this.sgtareasList = sgtareasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsociedad != null ? idsociedad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sgsociedad)) {
            return false;
        }
        Sgsociedad other = (Sgsociedad) object;
        if ((this.idsociedad == null && other.idsociedad != null) || (this.idsociedad != null && !this.idsociedad.equals(other.idsociedad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.Sgsociedad[ idsociedad=" + idsociedad + " ]";
    }

}
