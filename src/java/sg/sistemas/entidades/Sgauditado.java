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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgauditado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sgauditado.findAll", query = "SELECT s FROM Sgauditado s"),
    @NamedQuery(name = "Sgauditado.findByIdsociedad", query = "SELECT s FROM Sgauditado s WHERE s.sgauditadoPK.idsociedad = :idsociedad"),
    @NamedQuery(name = "Sgauditado.findByIdprogAud", query = "SELECT s FROM Sgauditado s WHERE s.sgauditadoPK.idprogAud = :idprogAud"),
    @NamedQuery(name = "Sgauditado.findByIdprocesos", query = "SELECT s FROM Sgauditado s WHERE s.sgauditadoPK.idprocesos = :idprocesos"),
    @NamedQuery(name = "Sgauditado.findBySubproceso", query = "SELECT s FROM Sgauditado s WHERE s.sgauditadoPK.subproceso = :subproceso"),
    @NamedQuery(name = "Sgauditado.findByHoraInicio", query = "SELECT s FROM Sgauditado s WHERE s.horaInicio = :horaInicio"),
    @NamedQuery(name = "Sgauditado.findByHoraFin", query = "SELECT s FROM Sgauditado s WHERE s.horaFin = :horaFin")})
public class Sgauditado implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SgauditadoPK sgauditadoPK;
    @Column(name = "HORA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaInicio;
    @Column(name = "HORA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaFin;
    @JoinColumns({
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD", insertable = false, updatable = false),
        @JoinColumn(name = "IDPROG_AUD", referencedColumnName = "IDPROG_AUD", insertable = false, updatable = false),
        @JoinColumn(name = "IDPROCESOS", referencedColumnName = "IDPROCESOS", insertable = false, updatable = false),
        @JoinColumn(name = "SUBPROCESO", referencedColumnName = "SUBPROCESO", insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private SgproccAuditoria sgproccAuditoria;
    @JoinColumn(name = "COD_USUARIO", referencedColumnName = "COD_USUARIO")
    @ManyToOne
    private Sgusuario codUsuario;

    public Sgauditado() {
    }

    public Sgauditado(SgauditadoPK sgauditadoPK) {
        this.sgauditadoPK = sgauditadoPK;
    }

    public Sgauditado(String idsociedad, String idprogAud, String idprocesos, String subproceso) {
        this.sgauditadoPK = new SgauditadoPK(idsociedad, idprogAud, idprocesos, subproceso);
    }

    public SgauditadoPK getSgauditadoPK() {
        return sgauditadoPK;
    }

    public void setSgauditadoPK(SgauditadoPK sgauditadoPK) {
        this.sgauditadoPK = sgauditadoPK;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    public SgproccAuditoria getSgproccAuditoria() {
        return sgproccAuditoria;
    }

    public void setSgproccAuditoria(SgproccAuditoria sgproccAuditoria) {
        this.sgproccAuditoria = sgproccAuditoria;
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
        hash += (sgauditadoPK != null ? sgauditadoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sgauditado)) {
            return false;
        }
        Sgauditado other = (Sgauditado) object;
        if ((this.sgauditadoPK == null && other.sgauditadoPK != null) || (this.sgauditadoPK != null && !this.sgauditadoPK.equals(other.sgauditadoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.Sgauditado[ sgauditadoPK=" + sgauditadoPK + " ]";
    }
    
}
