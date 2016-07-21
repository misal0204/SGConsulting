/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sgusuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sgusuario.findAll", query = "SELECT s FROM Sgusuario s"),
    @NamedQuery(name = "Sgusuario.findByCodUsuario", query = "SELECT s FROM Sgusuario s WHERE s.codUsuario = :codUsuario"),
    @NamedQuery(name = "Sgusuario.findByUsuario", query = "SELECT s FROM Sgusuario s WHERE s.usuario = :usuario"),
    @NamedQuery(name = "Sgusuario.findByEmail", query = "SELECT s FROM Sgusuario s WHERE s.email = :email"),
    @NamedQuery(name = "Sgusuario.findByEstado", query = "SELECT s FROM Sgusuario s WHERE s.estado = :estado"),
    @NamedQuery(name = "Sgusuario.findByTelefono", query = "SELECT s FROM Sgusuario s WHERE s.telefono = :telefono"),
    @NamedQuery(name = "Sgusuario.findByExtension", query = "SELECT s FROM Sgusuario s WHERE s.extension = :extension"),
    @NamedQuery(name = "Sgusuario.findByDui", query = "SELECT s FROM Sgusuario s WHERE s.dui = :dui"),
    @NamedQuery(name = "Sgusuario.findByNit", query = "SELECT s FROM Sgusuario s WHERE s.nit = :nit"),
    @NamedQuery(name = "Sgusuario.findByAfp", query = "SELECT s FROM Sgusuario s WHERE s.afp = :afp"),
    @NamedQuery(name = "Sgusuario.findByBorrar", query = "SELECT s FROM Sgusuario s WHERE s.borrar = :borrar")})

@NamedStoredProcedureQuery(
        name = "SP_INSERT_SGUSUARIO",
        procedureName = "INSERT_SGUSUARIO",
        parameters = {
            @StoredProcedureParameter(mode = ParameterMode.IN,type = String.class, name= "p_cod_usuario"),
            @StoredProcedureParameter(mode = ParameterMode.IN,type = String.class, name= "p_idrol_auditor"),
            @StoredProcedureParameter(mode = ParameterMode.IN,type = String.class, name= "p_usuario"),
            @StoredProcedureParameter(mode = ParameterMode.IN,type = String.class, name= "p_email"),
            @StoredProcedureParameter(mode = ParameterMode.IN,type = String.class, name= "p_estado"),
            @StoredProcedureParameter(mode = ParameterMode.IN,type = String.class, name= "p_telefono"),
            @StoredProcedureParameter(mode = ParameterMode.IN,type = String.class, name= "p_extension"),
            @StoredProcedureParameter(mode = ParameterMode.IN,type = String.class, name= "p_dui"),
            @StoredProcedureParameter(mode = ParameterMode.IN,type = String.class, name= "p_nit"),
            @StoredProcedureParameter(mode = ParameterMode.IN,type = String.class, name= "p_afp")               
        } 
               
)
public class Sgusuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "COD_USUARIO")
    private String codUsuario;
    @Column(name = "USUARIO")
    private String usuario;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "TELEFONO")
    private String telefono;
    @Column(name = "EXTENSION")
    private String extension;
    @Column(name = "DUI")
    private String dui;
    @Column(name = "NIT")
    private String nit;
    @Column(name = "AFP")
    private String afp;
    @Lob
    @Column(name = "FOTO")
    private byte[] foto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "BORRAR")
    private Double borrar;
    @ManyToMany(mappedBy = "sgusuarioList")
    private List<SgseguimientoIndObj> sgseguimientoIndObjList;
    @ManyToMany(mappedBy = "sgusuarioList")
    private List<SgcabEncuesta> sgcabEncuestaList;
    @ManyToMany(mappedBy = "sgusuarioList")
    private List<Sgdepartamento> sgdepartamentoList;
    @JoinTable(name = "sg_privilegios", joinColumns = {
        @JoinColumn(name = "COD_USUARIO", referencedColumnName = "COD_USUARIO")}, inverseJoinColumns = {
        @JoinColumn(name = "IDPROGRAM", referencedColumnName = "IDPROGRAM"),
        @JoinColumn(name = "IDMENU", referencedColumnName = "IDMENU")})
    @ManyToMany
    private List<SgProgramas> sgProgramasList;
    @JoinTable(name = "sgdetasigna", joinColumns = {
        @JoinColumn(name = "COD_USUARIO", referencedColumnName = "COD_USUARIO")}, inverseJoinColumns = {
        @JoinColumn(name = "IDSOCIEDAD", referencedColumnName = "IDSOCIEDAD"),
        @JoinColumn(name = "NONC", referencedColumnName = "NONC")})
    @ManyToMany
    private List<Sgnc> sgncList;
    @ManyToMany(mappedBy = "sgusuarioList")
    private List<SgdetalleRdireccion> sgdetalleRdireccionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgusuario")
    private List<SgverificacionNoacc> sgverificacionNoaccList;
    @OneToMany(mappedBy = "codUsuario")
    private List<SgseguimientoDetalle> sgseguimientoDetalleList;
    @JoinColumn(name = "IDROL_AUDITOR", referencedColumnName = "IDROL_AUDITOR")
    @ManyToOne
    private SgrolAuditor idrolAuditor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioDescribe")
    private List<Sgnc> sgncList1;
    @OneToMany(mappedBy = "codUsuario")
    private List<Sgcausa> sgcausaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgusuario")
    private List<SgtramientoRiesgo> sgtramientoRiesgoList;
    @OneToMany(mappedBy = "codUsuario")
    private List<Sgriesgo> sgriesgoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgusuario")
    private List<SgseguimientoNoacc> sgseguimientoNoaccList;
    @OneToMany(mappedBy = "codUsuario")
    private List<SgpCabEncuesta> sgpCabEncuestaList;
    @OneToMany(mappedBy = "codUsuario")
    private List<Sgverifica> sgverificaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgusuario")
    private List<SgUsuariorol> sgUsuariorolList;
    @OneToMany(mappedBy = "codUsuario")
    private List<SgdetalleRdireccion> sgdetalleRdireccionList1;
    @OneToMany(mappedBy = "codUsuario")
    private List<SgcapturaEncuesta> sgcapturaEncuestaList;
    @OneToMany(mappedBy = "codUsuario")
    private List<SgcorrecionNc> sgcorrecionNcList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgusuario")
    private List<SgprogramaEmpleado> sgprogramaEmpleadoList;
    @OneToMany(mappedBy = "codUsuario")
    private List<SgrevisionDireccion> sgrevisionDireccionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgusuario")
    private List<SgevaluacionRiesgo> sgevaluacionRiesgoList;
    @OneToMany(mappedBy = "codUsuario")
    private List<SgaccionesMejora> sgaccionesMejoraList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgusuario")
    private List<SgresponsableEdicion> sgresponsableEdicionList;
    @OneToMany(mappedBy = "codUsuario")
    private List<SgcabEncuesta> sgcabEncuestaList1;
    @OneToMany(mappedBy = "codUsuario")
    private List<Sgauditado> sgauditadoList;
    @OneToMany(mappedBy = "codUsuario")
    private List<Sgsigue> sgsigueList;

    public Sgusuario() {
    }

    public Sgusuario(String codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(String codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getAfp() {
        return afp;
    }

    public void setAfp(String afp) {
        this.afp = afp;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Double getBorrar() {
        return borrar;
    }

    public void setBorrar(Double borrar) {
        this.borrar = borrar;
    }

    @XmlTransient
    public List<SgseguimientoIndObj> getSgseguimientoIndObjList() {
        return sgseguimientoIndObjList;
    }

    public void setSgseguimientoIndObjList(List<SgseguimientoIndObj> sgseguimientoIndObjList) {
        this.sgseguimientoIndObjList = sgseguimientoIndObjList;
    }

    @XmlTransient
    public List<SgcabEncuesta> getSgcabEncuestaList() {
        return sgcabEncuestaList;
    }

    public void setSgcabEncuestaList(List<SgcabEncuesta> sgcabEncuestaList) {
        this.sgcabEncuestaList = sgcabEncuestaList;
    }

    @XmlTransient
    public List<Sgdepartamento> getSgdepartamentoList() {
        return sgdepartamentoList;
    }

    public void setSgdepartamentoList(List<Sgdepartamento> sgdepartamentoList) {
        this.sgdepartamentoList = sgdepartamentoList;
    }

    @XmlTransient
    public List<SgProgramas> getSgProgramasList() {
        return sgProgramasList;
    }

    public void setSgProgramasList(List<SgProgramas> sgProgramasList) {
        this.sgProgramasList = sgProgramasList;
    }

    @XmlTransient
    public List<Sgnc> getSgncList() {
        return sgncList;
    }

    public void setSgncList(List<Sgnc> sgncList) {
        this.sgncList = sgncList;
    }

    @XmlTransient
    public List<SgdetalleRdireccion> getSgdetalleRdireccionList() {
        return sgdetalleRdireccionList;
    }

    public void setSgdetalleRdireccionList(List<SgdetalleRdireccion> sgdetalleRdireccionList) {
        this.sgdetalleRdireccionList = sgdetalleRdireccionList;
    }

    @XmlTransient
    public List<SgverificacionNoacc> getSgverificacionNoaccList() {
        return sgverificacionNoaccList;
    }

    public void setSgverificacionNoaccList(List<SgverificacionNoacc> sgverificacionNoaccList) {
        this.sgverificacionNoaccList = sgverificacionNoaccList;
    }

    @XmlTransient
    public List<SgseguimientoDetalle> getSgseguimientoDetalleList() {
        return sgseguimientoDetalleList;
    }

    public void setSgseguimientoDetalleList(List<SgseguimientoDetalle> sgseguimientoDetalleList) {
        this.sgseguimientoDetalleList = sgseguimientoDetalleList;
    }

    public SgrolAuditor getIdrolAuditor() {
        return idrolAuditor;
    }

    public void setIdrolAuditor(SgrolAuditor idrolAuditor) {
        this.idrolAuditor = idrolAuditor;
    }

    @XmlTransient
    public List<Sgnc> getSgncList1() {
        return sgncList1;
    }

    public void setSgncList1(List<Sgnc> sgncList1) {
        this.sgncList1 = sgncList1;
    }

    @XmlTransient
    public List<Sgcausa> getSgcausaList() {
        return sgcausaList;
    }

    public void setSgcausaList(List<Sgcausa> sgcausaList) {
        this.sgcausaList = sgcausaList;
    }

    @XmlTransient
    public List<SgtramientoRiesgo> getSgtramientoRiesgoList() {
        return sgtramientoRiesgoList;
    }

    public void setSgtramientoRiesgoList(List<SgtramientoRiesgo> sgtramientoRiesgoList) {
        this.sgtramientoRiesgoList = sgtramientoRiesgoList;
    }

    @XmlTransient
    public List<Sgriesgo> getSgriesgoList() {
        return sgriesgoList;
    }

    public void setSgriesgoList(List<Sgriesgo> sgriesgoList) {
        this.sgriesgoList = sgriesgoList;
    }

    @XmlTransient
    public List<SgseguimientoNoacc> getSgseguimientoNoaccList() {
        return sgseguimientoNoaccList;
    }

    public void setSgseguimientoNoaccList(List<SgseguimientoNoacc> sgseguimientoNoaccList) {
        this.sgseguimientoNoaccList = sgseguimientoNoaccList;
    }

    @XmlTransient
    public List<SgpCabEncuesta> getSgpCabEncuestaList() {
        return sgpCabEncuestaList;
    }

    public void setSgpCabEncuestaList(List<SgpCabEncuesta> sgpCabEncuestaList) {
        this.sgpCabEncuestaList = sgpCabEncuestaList;
    }

    @XmlTransient
    public List<Sgverifica> getSgverificaList() {
        return sgverificaList;
    }

    public void setSgverificaList(List<Sgverifica> sgverificaList) {
        this.sgverificaList = sgverificaList;
    }

    @XmlTransient
    public List<SgUsuariorol> getSgUsuariorolList() {
        return sgUsuariorolList;
    }

    public void setSgUsuariorolList(List<SgUsuariorol> sgUsuariorolList) {
        this.sgUsuariorolList = sgUsuariorolList;
    }

    @XmlTransient
    public List<SgdetalleRdireccion> getSgdetalleRdireccionList1() {
        return sgdetalleRdireccionList1;
    }

    public void setSgdetalleRdireccionList1(List<SgdetalleRdireccion> sgdetalleRdireccionList1) {
        this.sgdetalleRdireccionList1 = sgdetalleRdireccionList1;
    }

    @XmlTransient
    public List<SgcapturaEncuesta> getSgcapturaEncuestaList() {
        return sgcapturaEncuestaList;
    }

    public void setSgcapturaEncuestaList(List<SgcapturaEncuesta> sgcapturaEncuestaList) {
        this.sgcapturaEncuestaList = sgcapturaEncuestaList;
    }

    @XmlTransient
    public List<SgcorrecionNc> getSgcorrecionNcList() {
        return sgcorrecionNcList;
    }

    public void setSgcorrecionNcList(List<SgcorrecionNc> sgcorrecionNcList) {
        this.sgcorrecionNcList = sgcorrecionNcList;
    }

    @XmlTransient
    public List<SgprogramaEmpleado> getSgprogramaEmpleadoList() {
        return sgprogramaEmpleadoList;
    }

    public void setSgprogramaEmpleadoList(List<SgprogramaEmpleado> sgprogramaEmpleadoList) {
        this.sgprogramaEmpleadoList = sgprogramaEmpleadoList;
    }

    @XmlTransient
    public List<SgrevisionDireccion> getSgrevisionDireccionList() {
        return sgrevisionDireccionList;
    }

    public void setSgrevisionDireccionList(List<SgrevisionDireccion> sgrevisionDireccionList) {
        this.sgrevisionDireccionList = sgrevisionDireccionList;
    }

    @XmlTransient
    public List<SgevaluacionRiesgo> getSgevaluacionRiesgoList() {
        return sgevaluacionRiesgoList;
    }

    public void setSgevaluacionRiesgoList(List<SgevaluacionRiesgo> sgevaluacionRiesgoList) {
        this.sgevaluacionRiesgoList = sgevaluacionRiesgoList;
    }

    @XmlTransient
    public List<SgaccionesMejora> getSgaccionesMejoraList() {
        return sgaccionesMejoraList;
    }

    public void setSgaccionesMejoraList(List<SgaccionesMejora> sgaccionesMejoraList) {
        this.sgaccionesMejoraList = sgaccionesMejoraList;
    }

    @XmlTransient
    public List<SgresponsableEdicion> getSgresponsableEdicionList() {
        return sgresponsableEdicionList;
    }

    public void setSgresponsableEdicionList(List<SgresponsableEdicion> sgresponsableEdicionList) {
        this.sgresponsableEdicionList = sgresponsableEdicionList;
    }

    @XmlTransient
    public List<SgcabEncuesta> getSgcabEncuestaList1() {
        return sgcabEncuestaList1;
    }

    public void setSgcabEncuestaList1(List<SgcabEncuesta> sgcabEncuestaList1) {
        this.sgcabEncuestaList1 = sgcabEncuestaList1;
    }

    @XmlTransient
    public List<Sgauditado> getSgauditadoList() {
        return sgauditadoList;
    }

    public void setSgauditadoList(List<Sgauditado> sgauditadoList) {
        this.sgauditadoList = sgauditadoList;
    }

    @XmlTransient
    public List<Sgsigue> getSgsigueList() {
        return sgsigueList;
    }

    public void setSgsigueList(List<Sgsigue> sgsigueList) {
        this.sgsigueList = sgsigueList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codUsuario != null ? codUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sgusuario)) {
            return false;
        }
        Sgusuario other = (Sgusuario) object;
        if ((this.codUsuario == null && other.codUsuario != null) || (this.codUsuario != null && !this.codUsuario.equals(other.codUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.Sgusuario[ codUsuario=" + codUsuario + " ]";
    }

}
