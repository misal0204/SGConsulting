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
@Table(name = "sgorganigrama")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgOrganigrama.findAll", query = "SELECT s FROM Sgorganigrama s"),
    @NamedQuery(name = "SgOrganigrama.findByCodUsuario", query = "SELECT s FROM Sgorganigrama s WHERE s.sgorganigramaPK.codusuario= :codUsuario"),
    @NamedQuery(name = "SgOrganigrama.findPuesto", query = "SELECT s FROM Sgorganigrama s WHERE s.sgorganigramaPK.idpuesto= :idpuesto"),
    @NamedQuery(name="SgOrganigrama.findJefe", query="SELECT s FROM Sgorganigrama s WHERE s.jefe = :jefe")
})
public class Sgorganigrama implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgorganigramaPK sgorganigramaPK;

    @Column(name = "JEFE")
    private String jefe;
    @JoinColumn(name = "COD_USUARIO", referencedColumnName = "COD_USUARIO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Sgusuario sgusario;
    @JoinColumn(name = "IDPUESTO", referencedColumnName = "IDPUESTO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SgpuestoLaboral SgpuestoLaboral;

    public SgorganigramaPK getSgorganigramaPK() {
        return sgorganigramaPK;
    }

    public void setSgorganigramaPK(SgorganigramaPK sgorganigramaPK) {
        this.sgorganigramaPK = sgorganigramaPK;
    }

    public String getJefe() {
        return jefe;
    }

    public void setJefe(String jefe) {
        this.jefe = jefe;
    }

    public Sgusuario getSgusario() {
        return sgusario;
    }

    public void setSgusario(Sgusuario sgusario) {
        this.sgusario = sgusario;
    }

    public SgpuestoLaboral getSgpuestoLaboral() {
        return SgpuestoLaboral;
    }

    public void setSgpuestoLaboral(SgpuestoLaboral SgpuestoLaboral) {
        this.SgpuestoLaboral = SgpuestoLaboral;
    }

}
