/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
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
@Table (name= "sgsociedad_detalle")
@XmlRootElement
public class SgsociedadDetalle implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected SgsociedadDetallePK sgsociedadDetallePK;
    @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable =false, updatable = false)
    @ManyToOne(optional = false)
    private Sgsociedad sgsociedad;
    
    @JoinColumn(name = "IDCENTRO", referencedColumnName = "IDCENTRO", insertable =false, updatable = false)
    @ManyToOne(optional = false)
    private Sgcentro sgcentro;

    public SgsociedadDetalle() {
    }

    public SgsociedadDetalle(SgsociedadDetallePK sgsociedadDetallePK) {
        this.sgsociedadDetallePK = sgsociedadDetallePK;
    }

    public SgsociedadDetalle(Sgsociedad sgsociedad, Sgcentro sgcentro) {
        this.sgsociedad = sgsociedad;
        this.sgcentro = sgcentro;
    }

    public SgsociedadDetallePK getSgsociedadDetallePK() {
        return sgsociedadDetallePK;
    }

    public void setSgsociedadDetallePK(SgsociedadDetallePK sgsociedadDetallePK) {
        this.sgsociedadDetallePK = sgsociedadDetallePK;
    }

    public Sgsociedad getSgsociedad() {
        return sgsociedad;
    }

    public void setSgsociedad(Sgsociedad sgsociedad) {
        this.sgsociedad = sgsociedad;
    }

    public Sgcentro getSgcentro() {
        return sgcentro;
    }

    public void setSgcentro(Sgcentro sgcentro) {
        this.sgcentro = sgcentro;
    }
    
    
}
