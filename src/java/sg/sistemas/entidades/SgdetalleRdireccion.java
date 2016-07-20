/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgdetalle_rdireccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgdetalleRdireccion.findAll", query = "SELECT s FROM SgdetalleRdireccion s"),
    @NamedQuery(name = "SgdetalleRdireccion.findByIdsociedad", query = "SELECT s FROM SgdetalleRdireccion s WHERE s.sgdetalleRdireccionPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "SgdetalleRdireccion.findByIdtipoPdireccion", query = "SELECT s FROM SgdetalleRdireccion s WHERE s.sgdetalleRdireccionPK.idtipoPdireccion = :idtipoPdireccion"),
    @NamedQuery(name = "SgdetalleRdireccion.findByIddetalleDireccion", query = "SELECT s FROM SgdetalleRdireccion s WHERE s.sgdetalleRdireccionPK.iddetalleDireccion = :iddetalleDireccion"),
    @NamedQuery(name = "SgdetalleRdireccion.findByFechaPrevistaInicio", query = "SELECT s FROM SgdetalleRdireccion s WHERE s.fechaPrevistaInicio = :fechaPrevistaInicio"),
    @NamedQuery(name = "SgdetalleRdireccion.findByFechaPrevistaFin", query = "SELECT s FROM SgdetalleRdireccion s WHERE s.fechaPrevistaFin = :fechaPrevistaFin"),
    @NamedQuery(name = "SgdetalleRdireccion.findByHorario", query = "SELECT s FROM SgdetalleRdireccion s WHERE s.horario = :horario"),
    @NamedQuery(name = "SgdetalleRdireccion.findByLugar", query = "SELECT s FROM SgdetalleRdireccion s WHERE s.lugar = :lugar")})
public class SgdetalleRdireccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgdetalleRdireccionPK sgdetalleRdireccionPK;
    @Lob
    @Column(name = "ALCANCE_PROCESO")
    private String alcanceProceso;
    @Lob
    @Column(name = "OBJETIVO")
    private String objetivo;
    @Lob
    @Column(name = "CONCLUSION")
    private String conclusion;
    @Column(name = "FECHA_PREVISTA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPrevistaInicio;
    @Column(name = "FECHA_PREVISTA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPrevistaFin;
    @Column(name = "HORARIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horario;
    @Column(name = "LUGAR")
    private String lugar;
    @JoinTable(name = "sgparticipantes_plan", joinColumns = {
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD"),
        @JoinColumn(name = "IDTIPO_PDIRECCION", referencedColumnName = "IDTIPO_PDIRECCION"),
        @JoinColumn(name = "IDDETALLE_DIRECCION", referencedColumnName = "IDDETALLE_DIRECCION")}, inverseJoinColumns = {
        @JoinColumn(name = "COD_USUARIO", referencedColumnName = "COD_USUARIO")})
    @ManyToMany
    private List<Sgusuario> sgusuarioList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "sgdetalleRdireccion")
    private SgplanProcesos sgplanProcesos;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "IDPLAN_DIRECCION", referencedColumnName = "IDPLAN_DIRECCION"),
        @JoinColumn(name = "IDTIPO_PDIRECCION", referencedColumnName = "IDTIPO_PDIRECCION", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private SgrevisionDireccion sgrevisionDireccion;
    @JoinColumn(name = "COD_USUARIO", referencedColumnName = "COD_USUARIO")
    @ManyToOne
    private Sgusuario codUsuario;

    public SgdetalleRdireccion() {
    }

    public SgdetalleRdireccion(SgdetalleRdireccionPK sgdetalleRdireccionPK) {
        this.sgdetalleRdireccionPK = sgdetalleRdireccionPK;
    }

    public SgdetalleRdireccion(String idsociedad, String idtipoPdireccion, String iddetalleDireccion) {
        this.sgdetalleRdireccionPK = new SgdetalleRdireccionPK(idsociedad, idtipoPdireccion, iddetalleDireccion);
    }

    public SgdetalleRdireccionPK getSgdetalleRdireccionPK() {
        return sgdetalleRdireccionPK;
    }

    public void setSgdetalleRdireccionPK(SgdetalleRdireccionPK sgdetalleRdireccionPK) {
        this.sgdetalleRdireccionPK = sgdetalleRdireccionPK;
    }

    public String getAlcanceProceso() {
        return alcanceProceso;
    }

    public void setAlcanceProceso(String alcanceProceso) {
        this.alcanceProceso = alcanceProceso;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public Date getFechaPrevistaInicio() {
        return fechaPrevistaInicio;
    }

    public void setFechaPrevistaInicio(Date fechaPrevistaInicio) {
        this.fechaPrevistaInicio = fechaPrevistaInicio;
    }

    public Date getFechaPrevistaFin() {
        return fechaPrevistaFin;
    }

    public void setFechaPrevistaFin(Date fechaPrevistaFin) {
        this.fechaPrevistaFin = fechaPrevistaFin;
    }

    public Date getHorario() {
        return horario;
    }

    public void setHorario(Date horario) {
        this.horario = horario;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    @XmlTransient
    public List<Sgusuario> getSgusuarioList() {
        return sgusuarioList;
    }

    public void setSgusuarioList(List<Sgusuario> sgusuarioList) {
        this.sgusuarioList = sgusuarioList;
    }

    public SgplanProcesos getSgplanProcesos() {
        return sgplanProcesos;
    }

    public void setSgplanProcesos(SgplanProcesos sgplanProcesos) {
        this.sgplanProcesos = sgplanProcesos;
    }

    public SgrevisionDireccion getSgrevisionDireccion() {
        return sgrevisionDireccion;
    }

    public void setSgrevisionDireccion(SgrevisionDireccion sgrevisionDireccion) {
        this.sgrevisionDireccion = sgrevisionDireccion;
    }

    public Sgusuario getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(Sgusuario codUsuario) {
        this.codUsuario = codUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgdetalleRdireccionPK != null ? sgdetalleRdireccionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgdetalleRdireccion)) {
            return false;
        }
        SgdetalleRdireccion other = (SgdetalleRdireccion) object;
        if ((this.sgdetalleRdireccionPK == null && other.sgdetalleRdireccionPK != null) || (this.sgdetalleRdireccionPK != null && !this.sgdetalleRdireccionPK.equals(other.sgdetalleRdireccionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgdetalleRdireccion[ sgdetalleRdireccionPK=" + sgdetalleRdireccionPK + " ]";
    }
    
}
