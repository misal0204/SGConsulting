/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Misael
 */
@Embeddable
public class SgsociedadDetallePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDSOCIEDAD")
    private String idsociedad;

    @Basic(optional = false)
    @Column(name = "IDCENTRO")
    private String idcentro;

    public SgsociedadDetallePK() {
    }

    public SgsociedadDetallePK(String idsociedad, String idcentro) {
        this.idsociedad = idsociedad;
        this.idcentro = idcentro;
    }

    public String getIdsociedad() {
        return idsociedad;
    }

    public void setIdsociedad(String idsociedad) {
        this.idsociedad = idsociedad;
    }

    public String getIdcentro() {
        return idcentro;
    }

    public void setIdcentro(String idcentro) {
        this.idcentro = idcentro;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgsociedadDetallePK";
    }
}
