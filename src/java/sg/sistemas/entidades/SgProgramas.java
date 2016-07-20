/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sg_programas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgProgramas.findAll", query = "SELECT s FROM SgProgramas s"),
    @NamedQuery(name = "SgProgramas.findByIdprogram", query = "SELECT s FROM SgProgramas s WHERE s.sgProgramasPK.idprogram = :idprogram"),
    @NamedQuery(name = "SgProgramas.findByIdmenu", query = "SELECT s FROM SgProgramas s WHERE s.sgProgramasPK.idmenu = :idmenu"),
    @NamedQuery(name = "SgProgramas.findByForma", query = "SELECT s FROM SgProgramas s WHERE s.forma = :forma"),
    @NamedQuery(name = "SgProgramas.findByProgramName", query = "SELECT s FROM SgProgramas s WHERE s.programName = :programName"),
    @NamedQuery(name = "SgProgramas.findByIcon", query = "SELECT s FROM SgProgramas s WHERE s.icon = :icon")})
public class SgProgramas implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgProgramasPK sgProgramasPK;
    @Column(name = "FORMA")
    private String forma;
    @Column(name = "PROGRAM_NAME")
    private String programName;
    @Column(name = "ICON")
    private String icon;
    @ManyToMany(mappedBy = "sgProgramasList")
    private List<Sgusuario> sgusuarioList;
    @JoinColumn(name = "IDMENU", referencedColumnName = "IDMENU", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SgMenus sgMenus;

    public SgProgramas() {
    }

    public SgProgramas(SgProgramasPK sgProgramasPK) {
        this.sgProgramasPK = sgProgramasPK;
    }

    public SgProgramas(double idprogram, double idmenu) {
        this.sgProgramasPK = new SgProgramasPK(idprogram, idmenu);
    }

    public SgProgramasPK getSgProgramasPK() {
        return sgProgramasPK;
    }

    public void setSgProgramasPK(SgProgramasPK sgProgramasPK) {
        this.sgProgramasPK = sgProgramasPK;
    }

    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @XmlTransient
    public List<Sgusuario> getSgusuarioList() {
        return sgusuarioList;
    }

    public void setSgusuarioList(List<Sgusuario> sgusuarioList) {
        this.sgusuarioList = sgusuarioList;
    }

    public SgMenus getSgMenus() {
        return sgMenus;
    }

    public void setSgMenus(SgMenus sgMenus) {
        this.sgMenus = sgMenus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgProgramasPK != null ? sgProgramasPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgProgramas)) {
            return false;
        }
        SgProgramas other = (SgProgramas) object;
        if ((this.sgProgramasPK == null && other.sgProgramasPK != null) || (this.sgProgramasPK != null && !this.sgProgramasPK.equals(other.sgProgramasPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgProgramas[ sgProgramasPK=" + sgProgramasPK + " ]";
    }
    
}
