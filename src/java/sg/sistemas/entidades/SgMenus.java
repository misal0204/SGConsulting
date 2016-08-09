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
@Table(name = "sg_menus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgMenus.findAll", query = "SELECT s FROM SgMenus s"),
    @NamedQuery(name = "SgMenus.findByIdmenu", query = "SELECT s FROM SgMenus s WHERE s.idmenu = :idmenu"),
    @NamedQuery(name = "SgMenus.findByMenuName", query = "SELECT s FROM SgMenus s WHERE s.menuName = :menuName"),
    @NamedQuery(name = "SgMenus.findByIcon", query = "SELECT s FROM SgMenus s WHERE s.icon = :icon")})

@NamedStoredProcedureQueries(
        {
            @NamedStoredProcedureQuery(
                    name = "SP_INSERT_SG_MENUS",
                    procedureName = "SP_INSERT_SG_MENUS",
                    parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Double.class, name = "p_idmenu"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "p_menu_name"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "p_icon"),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "p_resultado")
                    }
            ),
            @NamedStoredProcedureQuery(
                    name = "SP_UPDATE_SG_MENUS",
                    procedureName = "SP_UPDATE_SG_MENUS",
                    parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Double.class, name = "p_idmenu"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "p_menu_name"),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "p_icon"),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "p_resultado")
                    }
            ),
            @NamedStoredProcedureQuery(
                    name = "SP_DELETE_SG_MENUS",
                    procedureName = "SP_DELETE_SG_MENUS",
                    parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Double.class, name = "p_idmenu"),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "p_resultado")
                    }
            )
        }
)
public class SgMenus implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "IDMENU")
    private Double idmenu;
    @Basic(optional = false)
    @Column(name = "MENU_NAME")
    private String menuName;
    @Column(name = "ICON")
    private String icon;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgMenus")
    private List<SgProgramas> sgProgramasList;

    public SgMenus() {
    }

    public SgMenus(Double idmenu) {
        this.idmenu = idmenu;
    }

    public SgMenus(Double idmenu, String menuName) {
        this.idmenu = idmenu;
        this.menuName = menuName;
    }

    public Double getIdmenu() {
        return idmenu;
    }

    public void setIdmenu(Double idmenu) {
        this.idmenu = idmenu;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @XmlTransient
    public List<SgProgramas> getSgProgramasList() {
        return sgProgramasList;
    }

    public void setSgProgramasList(List<SgProgramas> sgProgramasList) {
        this.sgProgramasList = sgProgramasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmenu != null ? idmenu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgMenus)) {
            return false;
        }
        SgMenus other = (SgMenus) object;
        if ((this.idmenu == null && other.idmenu != null) || (this.idmenu != null && !this.idmenu.equals(other.idmenu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgMenus[ idmenu=" + idmenu + " ]";
    }

}
