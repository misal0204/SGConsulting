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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Misael
 */
@Entity
@Table(name = "sg_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SgUsuario.findAll", query = "SELECT s FROM SgUsuario s"),
    @NamedQuery(name = "SgUsuario.findByCodUsuario", query = "SELECT s FROM SgUsuario s WHERE s.codUsuario = :codUsuario"),
    @NamedQuery(name = "SgUsuario.findByUsuario", query = "SELECT s FROM SgUsuario s WHERE s.usuario = :usuario"),
    @NamedQuery(name = "SgUsuario.findByTipo", query = "SELECT s FROM SgUsuario s WHERE s.tipo = :tipo"),
    @NamedQuery(name = "SgUsuario.findByEmail", query = "SELECT s FROM SgUsuario s WHERE s.email = :email"),
    @NamedQuery(name = "SgUsuario.findByEstado", query = "SELECT s FROM SgUsuario s WHERE s.estado = :estado"),
    @NamedQuery(name = "SgUsuario.findByIdarea", query = "SELECT s FROM SgUsuario s WHERE s.idarea = :idarea"),
    @NamedQuery(name = "SgUsuario.findByTelefono", query = "SELECT s FROM SgUsuario s WHERE s.telefono = :telefono"),
    @NamedQuery(name = "SgUsuario.findByExtension", query = "SELECT s FROM SgUsuario s WHERE s.extension = :extension"),
    @NamedQuery(name = "SgUsuario.findByDui", query = "SELECT s FROM SgUsuario s WHERE s.dui = :dui"),
    @NamedQuery(name = "SgUsuario.findByNit", query = "SELECT s FROM SgUsuario s WHERE s.nit = :nit"),
    @NamedQuery(name = "SgUsuario.findByAfp", query = "SELECT s FROM SgUsuario s WHERE s.afp = :afp")})
public class SgUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "COD_USUARIO")
    private String codUsuario;
    @Column(name = "USUARIO")
    private String usuario;
    @Column(name = "TIPO")
    private String tipo;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "IDAREA")
    private String idarea;
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
    @ManyToMany(mappedBy = "sgUsuarioList")
    private List<Sgdepartamento> sgdepartamentoList;
    @ManyToMany(mappedBy = "sgUsuarioList")
    private List<SgseguimientoIndObj> sgseguimientoIndObjList;
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
    @ManyToMany(mappedBy = "sgUsuarioList")
    private List<SgcabEncuesta> sgcabEncuestaList;
    @ManyToMany(mappedBy = "sgUsuarioList")
    private List<SgdetalleRdireccion> sgdetalleRdireccionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgUsuario")
    private List<SgUsuariorol> sgUsuariorolList;
    @OneToMany(mappedBy = "codUsuario")
    private List<SgdetalleRdireccion> sgdetalleRdireccionList1;
    @OneToMany(mappedBy = "codUsuario")
    private List<SgseguimientoDetalle> sgseguimientoDetalleList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioDescribe")
    private List<Sgnc> sgncList1;
    @OneToMany(mappedBy = "codUsuario")
    private List<Sgcausa> sgcausaList;
    @OneToMany(mappedBy = "codUsuario")
    private List<SgcapturaEncuesta> sgcapturaEncuestaList;
    @OneToMany(mappedBy = "codUsuario")
    private List<SgcorrecionNc> sgcorrecionNcList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgUsuario")
    private List<SgprogramaEmpleado> sgprogramaEmpleadoList;
    @OneToMany(mappedBy = "codUsuario")
    private List<SgrevisionDireccion> sgrevisionDireccionList;
    @OneToMany(mappedBy = "codUsuario")
    private List<SgaccionesMejora> sgaccionesMejoraList;
    @JoinColumn(name = "IDROL_AUDITOR", referencedColumnName = "IDROL_AUDITOR")
    @ManyToOne
    private SgrolAuditor idrolAuditor;
    @OneToMany(mappedBy = "codUsuario")
    private List<Sgriesgo> sgriesgoList;
    @OneToMany(mappedBy = "codUsuario")
    private List<SgcabEncuesta> sgcabEncuestaList1;
    @OneToMany(mappedBy = "codUsuario")
    private List<Sgauditado> sgauditadoList;
    @OneToMany(mappedBy = "codUsuario")
    private List<SgpCabEncuesta> sgpCabEncuestaList;
    @OneToMany(mappedBy = "codUsuario")
    private List<Sgsigue> sgsigueList;
    @OneToMany(mappedBy = "codUsuario")
    private List<Sgverifica> sgverificaList;

    public SgUsuario() {
    }

    public SgUsuario(String codUsuario) {
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public String getIdarea() {
        return idarea;
    }

    public void setIdarea(String idarea) {
        this.idarea = idarea;
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

    @XmlTransient
    public List<Sgdepartamento> getSgdepartamentoList() {
        return sgdepartamentoList;
    }

    public void setSgdepartamentoList(List<Sgdepartamento> sgdepartamentoList) {
        this.sgdepartamentoList = sgdepartamentoList;
    }

    @XmlTransient
    public List<SgseguimientoIndObj> getSgseguimientoIndObjList() {
        return sgseguimientoIndObjList;
    }

    public void setSgseguimientoIndObjList(List<SgseguimientoIndObj> sgseguimientoIndObjList) {
        this.sgseguimientoIndObjList = sgseguimientoIndObjList;
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
    public List<SgcabEncuesta> getSgcabEncuestaList() {
        return sgcabEncuestaList;
    }

    public void setSgcabEncuestaList(List<SgcabEncuesta> sgcabEncuestaList) {
        this.sgcabEncuestaList = sgcabEncuestaList;
    }

    @XmlTransient
    public List<SgdetalleRdireccion> getSgdetalleRdireccionList() {
        return sgdetalleRdireccionList;
    }

    public void setSgdetalleRdireccionList(List<SgdetalleRdireccion> sgdetalleRdireccionList) {
        this.sgdetalleRdireccionList = sgdetalleRdireccionList;
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
    public List<SgseguimientoDetalle> getSgseguimientoDetalleList() {
        return sgseguimientoDetalleList;
    }

    public void setSgseguimientoDetalleList(List<SgseguimientoDetalle> sgseguimientoDetalleList) {
        this.sgseguimientoDetalleList = sgseguimientoDetalleList;
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
    public List<SgaccionesMejora> getSgaccionesMejoraList() {
        return sgaccionesMejoraList;
    }

    public void setSgaccionesMejoraList(List<SgaccionesMejora> sgaccionesMejoraList) {
        this.sgaccionesMejoraList = sgaccionesMejoraList;
    }

    public SgrolAuditor getIdrolAuditor() {
        return idrolAuditor;
    }

    public void setIdrolAuditor(SgrolAuditor idrolAuditor) {
        this.idrolAuditor = idrolAuditor;
    }

    @XmlTransient
    public List<Sgriesgo> getSgriesgoList() {
        return sgriesgoList;
    }

    public void setSgriesgoList(List<Sgriesgo> sgriesgoList) {
        this.sgriesgoList = sgriesgoList;
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
    public List<SgpCabEncuesta> getSgpCabEncuestaList() {
        return sgpCabEncuestaList;
    }

    public void setSgpCabEncuestaList(List<SgpCabEncuesta> sgpCabEncuestaList) {
        this.sgpCabEncuestaList = sgpCabEncuestaList;
    }

    @XmlTransient
    public List<Sgsigue> getSgsigueList() {
        return sgsigueList;
    }

    public void setSgsigueList(List<Sgsigue> sgsigueList) {
        this.sgsigueList = sgsigueList;
    }

    @XmlTransient
    public List<Sgverifica> getSgverificaList() {
        return sgverificaList;
    }

    public void setSgverificaList(List<Sgverifica> sgverificaList) {
        this.sgverificaList = sgverificaList;
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
        if (!(object instanceof SgUsuario)) {
            return false;
        }
        SgUsuario other = (SgUsuario) object;
        if ((this.codUsuario == null && other.codUsuario != null) || (this.codUsuario != null && !this.codUsuario.equals(other.codUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.sistemas.entidades.SgUsuario[ codUsuario=" + codUsuario + " ]";
    }
    
}
