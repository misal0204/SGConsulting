/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sganxplan_process")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SganxplanProcess.findAll", query = "SELECT s FROM SganxplanProcess s"),
    @NamedQuery(name = "SganxplanProcess.findByIdsociedad", query = "SELECT s FROM SganxplanProcess s WHERE s.sganxplanProcessPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SganxplanProcess.findByIdtipoPdireccion", query = "SELECT s FROM SganxplanProcess s WHERE s.sganxplanProcessPK.idtipoPdireccion = :idtipoPdireccion"),
    @NamedQuery(name = "SganxplanProcess.findByIddetalleDireccion", query = "SELECT s FROM SganxplanProcess s WHERE s.sganxplanProcessPK.iddetalleDireccion = :iddetalleDireccion"),
    @NamedQuery(name = "SganxplanProcess.findByIdcodProceso", query = "SELECT s FROM SganxplanProcess s WHERE s.sganxplanProcessPK.idcodProceso = :idcodProceso"),
    @NamedQuery(name = "SganxplanProcess.findByNombreDoc", query = "SELECT s FROM SganxplanProcess s WHERE s.sganxplanProcessPK.nombreDoc = :nombreDoc"),
    @NamedQuery(name = "SganxplanProcess.findBySize", query = "SELECT s FROM SganxplanProcess s WHERE s.size = :size"),
    @NamedQuery(name = "SganxplanProcess.findByFechaAdj", query = "SELECT s FROM SganxplanProcess s WHERE s.fechaAdj = :fechaAdj")})
public class SganxplanProcess implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SganxplanProcessPK sganxplanProcessPK;
    @Lob
    @Column(name = "ANEXO")
    private byte[] anexo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SIZE")
    private Double size;
    @Column(name = "FECHA_ADJ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAdj;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "IDTIPO_PDIRECCION", referencedColumnName = "IDTIPO_PDIRECCION", insertable = false, updatable = false),
        @JoinColumn(name = "IDDETALLE_DIRECCION", referencedColumnName = "IDDETALLE_DIRECCION", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private SgplanProcesos sgplanProcesos;

    public SganxplanProcess() {
    }

    public SganxplanProcess(SganxplanProcessPK sganxplanProcessPK) {
        this.sganxplanProcessPK = sganxplanProcessPK;
    }

    public SganxplanProcess(String idsociedad, String idtipoPdireccion, String iddetalleDireccion, String idcodProceso, String nombreDoc) {
        this.sganxplanProcessPK = new SganxplanProcessPK(idsociedad, idtipoPdireccion, iddetalleDireccion, idcodProceso, nombreDoc);
    }

    public SganxplanProcessPK getSganxplanProcessPK() {
        return sganxplanProcessPK;
    }

    public void setSganxplanProcessPK(SganxplanProcessPK sganxplanProcessPK) {
        this.sganxplanProcessPK = sganxplanProcessPK;
    }

    public byte[] getAnexo() {
        return anexo;
    }

    public void setAnexo(byte[] anexo) {
        this.anexo = anexo;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public Date getFechaAdj() {
        return fechaAdj;
    }

    public void setFechaAdj(Date fechaAdj) {
        this.fechaAdj = fechaAdj;
    }

    public SgplanProcesos getSgplanProcesos() {
        return sgplanProcesos;
    }

    public void setSgplanProcesos(SgplanProcesos sgplanProcesos) {
        this.sgplanProcesos = sgplanProcesos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sganxplanProcessPK != null ? sganxplanProcessPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SganxplanProcess)) {
            return false;
        }
        SganxplanProcess other = (SganxplanProcess) object;
        if ((this.sganxplanProcessPK == null && other.sganxplanProcessPK != null) || (this.sganxplanProcessPK != null && !this.sganxplanProcessPK.equals(other.sganxplanProcessPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SganxplanProcess[ sganxplanProcessPK=" + sganxplanProcessPK + " ]";
    }
    
}
