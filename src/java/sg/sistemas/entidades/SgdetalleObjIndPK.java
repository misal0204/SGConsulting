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
public class SgdetalleObjIndPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDSOCIEDAD")
    private String idsociedad;
    @Basic(optional = false)
    @Column(name = "IDOBJ_INDICADOR")
    private String idobjIndicador;
    @Basic(optional = false)
    @Column(name = "IDDETALLE_IO")
    private String iddetalleIo;

    public SgdetalleObjIndPK() {
    }

    public SgdetalleObjIndPK(String idsociedad, String idobjIndicador, String iddetalleIo) {
        this.idsociedad = idsociedad;
        this.idobjIndicador = idobjIndicador;
        this.iddetalleIo = iddetalleIo;
    }

    public String getIdsociedad() {
        return idsociedad;
    }

    public void setIdsociedad(String idsociedad) {
        this.idsociedad = idsociedad;
    }

    public String getIdobjIndicador() {
        return idobjIndicador;
    }

    public void setIdobjIndicador(String idobjIndicador) {
        this.idobjIndicador = idobjIndicador;
    }

    public String getIddetalleIo() {
        return iddetalleIo;
    }

    public void setIddetalleIo(String iddetalleIo) {
        this.iddetalleIo = iddetalleIo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsociedad != null ? idsociedad.hashCode() : 0);
        hash += (idobjIndicador != null ? idobjIndicador.hashCode() : 0);
        hash += (iddetalleIo != null ? iddetalleIo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgdetalleObjIndPK)) {
            return false;
        }
        SgdetalleObjIndPK other = (SgdetalleObjIndPK) object;
        if ((this.idsociedad == null && other.idsociedad != null) || (this.idsociedad != null && !this.idsociedad.equals(other.idsociedad))) {
            return false;
        }
        if ((this.idobjIndicador == null && other.idobjIndicador != null) || (this.idobjIndicador != null && !this.idobjIndicador.equals(other.idobjIndicador))) {
            return false;
        }
        if ((this.iddetalleIo == null && other.iddetalleIo != null) || (this.iddetalleIo != null && !this.iddetalleIo.equals(other.iddetalleIo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgdetalleObjIndPK[ idsociedad=" + idsociedad + ", idobjIndicador=" + idobjIndicador + ", iddetalleIo=" + iddetalleIo + " ]";
    }
    
}
