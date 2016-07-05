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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgttarea")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sgttarea.findAll", query = "SELECT s FROM Sgttarea s"),
    @NamedQuery(name = "Sgttarea.findByIdtarea", query = "SELECT s FROM Sgttarea s WHERE s.idtarea = :idtarea"),
    @NamedQuery(name = "Sgttarea.findByDescripcion", query = "SELECT s FROM Sgttarea s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "Sgttarea.findByDias", query = "SELECT s FROM Sgttarea s WHERE s.dias = :dias"),
    @NamedQuery(name = "Sgttarea.findByDiasAviso", query = "SELECT s FROM Sgttarea s WHERE s.diasAviso = :diasAviso")})
public class Sgttarea implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDTAREA")
    private String idtarea;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "DIAS")
    private Double dias;
    @Column(name = "DIAS_AVISO")
    private Double diasAviso;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgttarea")
    private List<Sgtareas> sgtareasList;

    public Sgttarea() {
    }

    public Sgttarea(String idtarea) {
        this.idtarea = idtarea;
    }

    public String getIdtarea() {
        return idtarea;
    }

    public void setIdtarea(String idtarea) {
        this.idtarea = idtarea;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getDias() {
        return dias;
    }

    public void setDias(Double dias) {
        this.dias = dias;
    }

    public Double getDiasAviso() {
        return diasAviso;
    }

    public void setDiasAviso(Double diasAviso) {
        this.diasAviso = diasAviso;
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
        hash += (idtarea != null ? idtarea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sgttarea)) {
            return false;
        }
        Sgttarea other = (Sgttarea) object;
        if ((this.idtarea == null && other.idtarea != null) || (this.idtarea != null && !this.idtarea.equals(other.idtarea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.Sgttarea[ idtarea=" + idtarea + " ]";
    }
    
}
