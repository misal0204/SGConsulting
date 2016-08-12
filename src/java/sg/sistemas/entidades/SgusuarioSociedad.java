/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Misael
 */
@Entity
@Table (name = "sgusuario_sociedad")
@XmlRootElement
public class SgusuarioSociedad implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected SgusuarioSociedadPK sgusuarioSociedadPK;
    @JoinColumn(name = "COD_USUARIO", referencedColumnName = "COD_USUARIO", insertable=false, updatable=false)
    @ManyToOne(optional = false)
    private Sgusuario sgusuario;
    
    @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable=false, updatable=false)
    @ManyToOne(optional = false)
    private Sgsociedad sgsociedad;

    public SgusuarioSociedad() {
    }

    public SgusuarioSociedad(SgusuarioSociedadPK sgusuarioSociedadPK) {
        this.sgusuarioSociedadPK = sgusuarioSociedadPK;
    }

    public SgusuarioSociedad(Sgusuario sgusuario, Sgsociedad sgsociedad) {
        this.sgusuario = sgusuario;
        this.sgsociedad = sgsociedad;
    }

    public SgusuarioSociedadPK getSgusuarioSociedadPK() {
        return sgusuarioSociedadPK;
    }

    public void setSgusuarioSociedadPK(SgusuarioSociedadPK sgusuarioSociedadPK) {
        this.sgusuarioSociedadPK = sgusuarioSociedadPK;
    }

    public Sgusuario getSgusuario() {
        return sgusuario;
    }

    public void setSgusuario(Sgusuario sgusuario) {
        this.sgusuario = sgusuario;
    }

    public Sgsociedad getSgsociedad() {
        return sgsociedad;
    }

    public void setSgsociedad(Sgsociedad sgsociedad) {
        this.sgsociedad = sgsociedad;
    }
    
    
}
