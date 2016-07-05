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
public class SgProgramasPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDPROGRAM")
    private double idprogram;
    @Basic(optional = false)
    @Column(name = "IDMENU")
    private double idmenu;

    public SgProgramasPK() {
    }

    public SgProgramasPK(double idprogram, double idmenu) {
        this.idprogram = idprogram;
        this.idmenu = idmenu;
    }

    public double getIdprogram() {
        return idprogram;
    }

    public void setIdprogram(double idprogram) {
        this.idprogram = idprogram;
    }

    public double getIdmenu() {
        return idmenu;
    }

    public void setIdmenu(double idmenu) {
        this.idmenu = idmenu;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idprogram;
        hash += (int) idmenu;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgProgramasPK)) {
            return false;
        }
        SgProgramasPK other = (SgProgramasPK) object;
        if (this.idprogram != other.idprogram) {
            return false;
        }
        if (this.idmenu != other.idmenu) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgProgramasPK[ idprogram=" + idprogram + ", idmenu=" + idmenu + " ]";
    }
    
}
