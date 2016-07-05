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
public class SgseguimientoIndObjPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "IDSOCIEDAD")
    private String idsociedad;
    @Basic(optional = false)
    @Column(name = "IDOBJ_INDICADOR")
    private String idobjIndicador;
    @Basic(optional = false)
    @Column(name = "IDDETALLE_IO")
    private String iddetalleIo;
    @Basic(optional = false)
    @Column(name = "IDTIPO_ALERTA")
    private String idtipoAlerta;

    public SgseguimientoIndObjPK() {
    }

    public SgseguimientoIndObjPK(String idsociedad, String idobjIndicador, String iddetalleIo, String idtipoAlerta) {
        this.idsociedad = idsociedad;
        this.idobjIndicador = idobjIndicador;
        this.iddetalleIo = iddetalleIo;
        this.idtipoAlerta = idtipoAlerta;
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

    public String getIdtipoAlerta() {
        return idtipoAlerta;
    }

    public void setIdtipoAlerta(String idtipoAlerta) {
        this.idtipoAlerta = idtipoAlerta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsociedad != null ? idsociedad.hashCode() : 0);
        hash += (idobjIndicador != null ? idobjIndicador.hashCode() : 0);
        hash += (iddetalleIo != null ? iddetalleIo.hashCode() : 0);
        hash += (idtipoAlerta != null ? idtipoAlerta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgseguimientoIndObjPK)) {
            return false;
        }
        SgseguimientoIndObjPK other = (SgseguimientoIndObjPK) object;
        if ((this.idsociedad == null && other.idsociedad != null) || (this.idsociedad != null && !this.idsociedad.equals(other.idsociedad))) {
            return false;
        }
        if ((this.idobjIndicador == null && other.idobjIndicador != null) || (this.idobjIndicador != null && !this.idobjIndicador.equals(other.idobjIndicador))) {
            return false;
        }
        if ((this.iddetalleIo == null && other.iddetalleIo != null) || (this.iddetalleIo != null && !this.iddetalleIo.equals(other.iddetalleIo))) {
            return false;
        }
        if ((this.idtipoAlerta == null && other.idtipoAlerta != null) || (this.idtipoAlerta != null && !this.idtipoAlerta.equals(other.idtipoAlerta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgseguimientoIndObjPK[ idsociedad=" + idsociedad + ", idobjIndicador=" + idobjIndicador + ", iddetalleIo=" + iddetalleIo + ", idtipoAlerta=" + idtipoAlerta + " ]";
    }
    
}
