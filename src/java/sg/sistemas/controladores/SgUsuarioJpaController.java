/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.controladores;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import sg.sistemas.entidades.SgrolAuditor;
import sg.sistemas.entidades.Sgdepartamento;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgseguimientoIndObj;
import sg.sistemas.entidades.SgProgramas;
import sg.sistemas.entidades.SgUsuario;
import sg.sistemas.entidades.Sgnc;
import sg.sistemas.entidades.SgcabEncuesta;
import sg.sistemas.entidades.SgdetalleRdireccion;
import sg.sistemas.entidades.SgUsuariorol;
import sg.sistemas.entidades.SgseguimientoDetalle;
import sg.sistemas.entidades.Sgcausa;
import sg.sistemas.entidades.SgcapturaEncuesta;
import sg.sistemas.entidades.SgcorrecionNc;
import sg.sistemas.entidades.SgprogramaEmpleado;
import sg.sistemas.entidades.SgrevisionDireccion;
import sg.sistemas.entidades.SgaccionesMejora;
import sg.sistemas.entidades.Sgriesgo;
import sg.sistemas.entidades.Sgauditado;
import sg.sistemas.entidades.SgpCabEncuesta;
import sg.sistemas.entidades.Sgsigue;
import sg.sistemas.entidades.Sgverifica;

/**
 *
 * @author Misael
 */
public class SgUsuarioJpaController implements Serializable {

    public SgUsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgUsuario sgUsuario) throws PreexistingEntityException, Exception {
        if (sgUsuario.getSgdepartamentoList() == null) {
            sgUsuario.setSgdepartamentoList(new ArrayList<Sgdepartamento>());
        }
        if (sgUsuario.getSgseguimientoIndObjList() == null) {
            sgUsuario.setSgseguimientoIndObjList(new ArrayList<SgseguimientoIndObj>());
        }
        if (sgUsuario.getSgProgramasList() == null) {
            sgUsuario.setSgProgramasList(new ArrayList<SgProgramas>());
        }
        if (sgUsuario.getSgncList() == null) {
            sgUsuario.setSgncList(new ArrayList<Sgnc>());
        }
        if (sgUsuario.getSgcabEncuestaList() == null) {
            sgUsuario.setSgcabEncuestaList(new ArrayList<SgcabEncuesta>());
        }
        if (sgUsuario.getSgdetalleRdireccionList() == null) {
            sgUsuario.setSgdetalleRdireccionList(new ArrayList<SgdetalleRdireccion>());
        }
        if (sgUsuario.getSgUsuariorolList() == null) {
            sgUsuario.setSgUsuariorolList(new ArrayList<SgUsuariorol>());
        }
        if (sgUsuario.getSgdetalleRdireccionList1() == null) {
            sgUsuario.setSgdetalleRdireccionList1(new ArrayList<SgdetalleRdireccion>());
        }
        if (sgUsuario.getSgseguimientoDetalleList() == null) {
            sgUsuario.setSgseguimientoDetalleList(new ArrayList<SgseguimientoDetalle>());
        }
        if (sgUsuario.getSgncList1() == null) {
            sgUsuario.setSgncList1(new ArrayList<Sgnc>());
        }
        if (sgUsuario.getSgcausaList() == null) {
            sgUsuario.setSgcausaList(new ArrayList<Sgcausa>());
        }
        if (sgUsuario.getSgcapturaEncuestaList() == null) {
            sgUsuario.setSgcapturaEncuestaList(new ArrayList<SgcapturaEncuesta>());
        }
        if (sgUsuario.getSgcorrecionNcList() == null) {
            sgUsuario.setSgcorrecionNcList(new ArrayList<SgcorrecionNc>());
        }
        if (sgUsuario.getSgprogramaEmpleadoList() == null) {
            sgUsuario.setSgprogramaEmpleadoList(new ArrayList<SgprogramaEmpleado>());
        }
        if (sgUsuario.getSgrevisionDireccionList() == null) {
            sgUsuario.setSgrevisionDireccionList(new ArrayList<SgrevisionDireccion>());
        }
        if (sgUsuario.getSgaccionesMejoraList() == null) {
            sgUsuario.setSgaccionesMejoraList(new ArrayList<SgaccionesMejora>());
        }
        if (sgUsuario.getSgriesgoList() == null) {
            sgUsuario.setSgriesgoList(new ArrayList<Sgriesgo>());
        }
        if (sgUsuario.getSgcabEncuestaList1() == null) {
            sgUsuario.setSgcabEncuestaList1(new ArrayList<SgcabEncuesta>());
        }
        if (sgUsuario.getSgauditadoList() == null) {
            sgUsuario.setSgauditadoList(new ArrayList<Sgauditado>());
        }
        if (sgUsuario.getSgpCabEncuestaList() == null) {
            sgUsuario.setSgpCabEncuestaList(new ArrayList<SgpCabEncuesta>());
        }
        if (sgUsuario.getSgsigueList() == null) {
            sgUsuario.setSgsigueList(new ArrayList<Sgsigue>());
        }
        if (sgUsuario.getSgverificaList() == null) {
            sgUsuario.setSgverificaList(new ArrayList<Sgverifica>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgrolAuditor idrolAuditor = sgUsuario.getIdrolAuditor();
            if (idrolAuditor != null) {
                idrolAuditor = em.getReference(idrolAuditor.getClass(), idrolAuditor.getIdrolAuditor());
                sgUsuario.setIdrolAuditor(idrolAuditor);
            }
            List<Sgdepartamento> attachedSgdepartamentoList = new ArrayList<Sgdepartamento>();
            for (Sgdepartamento sgdepartamentoListSgdepartamentoToAttach : sgUsuario.getSgdepartamentoList()) {
                sgdepartamentoListSgdepartamentoToAttach = em.getReference(sgdepartamentoListSgdepartamentoToAttach.getClass(), sgdepartamentoListSgdepartamentoToAttach.getIddept());
                attachedSgdepartamentoList.add(sgdepartamentoListSgdepartamentoToAttach);
            }
            sgUsuario.setSgdepartamentoList(attachedSgdepartamentoList);
            List<SgseguimientoIndObj> attachedSgseguimientoIndObjList = new ArrayList<SgseguimientoIndObj>();
            for (SgseguimientoIndObj sgseguimientoIndObjListSgseguimientoIndObjToAttach : sgUsuario.getSgseguimientoIndObjList()) {
                sgseguimientoIndObjListSgseguimientoIndObjToAttach = em.getReference(sgseguimientoIndObjListSgseguimientoIndObjToAttach.getClass(), sgseguimientoIndObjListSgseguimientoIndObjToAttach.getSgseguimientoIndObjPK());
                attachedSgseguimientoIndObjList.add(sgseguimientoIndObjListSgseguimientoIndObjToAttach);
            }
            sgUsuario.setSgseguimientoIndObjList(attachedSgseguimientoIndObjList);
            List<SgProgramas> attachedSgProgramasList = new ArrayList<SgProgramas>();
            for (SgProgramas sgProgramasListSgProgramasToAttach : sgUsuario.getSgProgramasList()) {
                sgProgramasListSgProgramasToAttach = em.getReference(sgProgramasListSgProgramasToAttach.getClass(), sgProgramasListSgProgramasToAttach.getSgProgramasPK());
                attachedSgProgramasList.add(sgProgramasListSgProgramasToAttach);
            }
            sgUsuario.setSgProgramasList(attachedSgProgramasList);
            List<Sgnc> attachedSgncList = new ArrayList<Sgnc>();
            for (Sgnc sgncListSgncToAttach : sgUsuario.getSgncList()) {
                sgncListSgncToAttach = em.getReference(sgncListSgncToAttach.getClass(), sgncListSgncToAttach.getSgncPK());
                attachedSgncList.add(sgncListSgncToAttach);
            }
            sgUsuario.setSgncList(attachedSgncList);
            List<SgcabEncuesta> attachedSgcabEncuestaList = new ArrayList<SgcabEncuesta>();
            for (SgcabEncuesta sgcabEncuestaListSgcabEncuestaToAttach : sgUsuario.getSgcabEncuestaList()) {
                sgcabEncuestaListSgcabEncuestaToAttach = em.getReference(sgcabEncuestaListSgcabEncuestaToAttach.getClass(), sgcabEncuestaListSgcabEncuestaToAttach.getSgcabEncuestaPK());
                attachedSgcabEncuestaList.add(sgcabEncuestaListSgcabEncuestaToAttach);
            }
            sgUsuario.setSgcabEncuestaList(attachedSgcabEncuestaList);
            List<SgdetalleRdireccion> attachedSgdetalleRdireccionList = new ArrayList<SgdetalleRdireccion>();
            for (SgdetalleRdireccion sgdetalleRdireccionListSgdetalleRdireccionToAttach : sgUsuario.getSgdetalleRdireccionList()) {
                sgdetalleRdireccionListSgdetalleRdireccionToAttach = em.getReference(sgdetalleRdireccionListSgdetalleRdireccionToAttach.getClass(), sgdetalleRdireccionListSgdetalleRdireccionToAttach.getSgdetalleRdireccionPK());
                attachedSgdetalleRdireccionList.add(sgdetalleRdireccionListSgdetalleRdireccionToAttach);
            }
            sgUsuario.setSgdetalleRdireccionList(attachedSgdetalleRdireccionList);
            List<SgUsuariorol> attachedSgUsuariorolList = new ArrayList<SgUsuariorol>();
            for (SgUsuariorol sgUsuariorolListSgUsuariorolToAttach : sgUsuario.getSgUsuariorolList()) {
                sgUsuariorolListSgUsuariorolToAttach = em.getReference(sgUsuariorolListSgUsuariorolToAttach.getClass(), sgUsuariorolListSgUsuariorolToAttach.getSgUsuariorolPK());
                attachedSgUsuariorolList.add(sgUsuariorolListSgUsuariorolToAttach);
            }
            sgUsuario.setSgUsuariorolList(attachedSgUsuariorolList);
            List<SgdetalleRdireccion> attachedSgdetalleRdireccionList1 = new ArrayList<SgdetalleRdireccion>();
            for (SgdetalleRdireccion sgdetalleRdireccionList1SgdetalleRdireccionToAttach : sgUsuario.getSgdetalleRdireccionList1()) {
                sgdetalleRdireccionList1SgdetalleRdireccionToAttach = em.getReference(sgdetalleRdireccionList1SgdetalleRdireccionToAttach.getClass(), sgdetalleRdireccionList1SgdetalleRdireccionToAttach.getSgdetalleRdireccionPK());
                attachedSgdetalleRdireccionList1.add(sgdetalleRdireccionList1SgdetalleRdireccionToAttach);
            }
            sgUsuario.setSgdetalleRdireccionList1(attachedSgdetalleRdireccionList1);
            List<SgseguimientoDetalle> attachedSgseguimientoDetalleList = new ArrayList<SgseguimientoDetalle>();
            for (SgseguimientoDetalle sgseguimientoDetalleListSgseguimientoDetalleToAttach : sgUsuario.getSgseguimientoDetalleList()) {
                sgseguimientoDetalleListSgseguimientoDetalleToAttach = em.getReference(sgseguimientoDetalleListSgseguimientoDetalleToAttach.getClass(), sgseguimientoDetalleListSgseguimientoDetalleToAttach.getSgseguimientoDetallePK());
                attachedSgseguimientoDetalleList.add(sgseguimientoDetalleListSgseguimientoDetalleToAttach);
            }
            sgUsuario.setSgseguimientoDetalleList(attachedSgseguimientoDetalleList);
            List<Sgnc> attachedSgncList1 = new ArrayList<Sgnc>();
            for (Sgnc sgncList1SgncToAttach : sgUsuario.getSgncList1()) {
                sgncList1SgncToAttach = em.getReference(sgncList1SgncToAttach.getClass(), sgncList1SgncToAttach.getSgncPK());
                attachedSgncList1.add(sgncList1SgncToAttach);
            }
            sgUsuario.setSgncList1(attachedSgncList1);
            List<Sgcausa> attachedSgcausaList = new ArrayList<Sgcausa>();
            for (Sgcausa sgcausaListSgcausaToAttach : sgUsuario.getSgcausaList()) {
                sgcausaListSgcausaToAttach = em.getReference(sgcausaListSgcausaToAttach.getClass(), sgcausaListSgcausaToAttach.getSgcausaPK());
                attachedSgcausaList.add(sgcausaListSgcausaToAttach);
            }
            sgUsuario.setSgcausaList(attachedSgcausaList);
            List<SgcapturaEncuesta> attachedSgcapturaEncuestaList = new ArrayList<SgcapturaEncuesta>();
            for (SgcapturaEncuesta sgcapturaEncuestaListSgcapturaEncuestaToAttach : sgUsuario.getSgcapturaEncuestaList()) {
                sgcapturaEncuestaListSgcapturaEncuestaToAttach = em.getReference(sgcapturaEncuestaListSgcapturaEncuestaToAttach.getClass(), sgcapturaEncuestaListSgcapturaEncuestaToAttach.getSgcapturaEncuestaPK());
                attachedSgcapturaEncuestaList.add(sgcapturaEncuestaListSgcapturaEncuestaToAttach);
            }
            sgUsuario.setSgcapturaEncuestaList(attachedSgcapturaEncuestaList);
            List<SgcorrecionNc> attachedSgcorrecionNcList = new ArrayList<SgcorrecionNc>();
            for (SgcorrecionNc sgcorrecionNcListSgcorrecionNcToAttach : sgUsuario.getSgcorrecionNcList()) {
                sgcorrecionNcListSgcorrecionNcToAttach = em.getReference(sgcorrecionNcListSgcorrecionNcToAttach.getClass(), sgcorrecionNcListSgcorrecionNcToAttach.getSgcorrecionNcPK());
                attachedSgcorrecionNcList.add(sgcorrecionNcListSgcorrecionNcToAttach);
            }
            sgUsuario.setSgcorrecionNcList(attachedSgcorrecionNcList);
            List<SgprogramaEmpleado> attachedSgprogramaEmpleadoList = new ArrayList<SgprogramaEmpleado>();
            for (SgprogramaEmpleado sgprogramaEmpleadoListSgprogramaEmpleadoToAttach : sgUsuario.getSgprogramaEmpleadoList()) {
                sgprogramaEmpleadoListSgprogramaEmpleadoToAttach = em.getReference(sgprogramaEmpleadoListSgprogramaEmpleadoToAttach.getClass(), sgprogramaEmpleadoListSgprogramaEmpleadoToAttach.getSgprogramaEmpleadoPK());
                attachedSgprogramaEmpleadoList.add(sgprogramaEmpleadoListSgprogramaEmpleadoToAttach);
            }
            sgUsuario.setSgprogramaEmpleadoList(attachedSgprogramaEmpleadoList);
            List<SgrevisionDireccion> attachedSgrevisionDireccionList = new ArrayList<SgrevisionDireccion>();
            for (SgrevisionDireccion sgrevisionDireccionListSgrevisionDireccionToAttach : sgUsuario.getSgrevisionDireccionList()) {
                sgrevisionDireccionListSgrevisionDireccionToAttach = em.getReference(sgrevisionDireccionListSgrevisionDireccionToAttach.getClass(), sgrevisionDireccionListSgrevisionDireccionToAttach.getSgrevisionDireccionPK());
                attachedSgrevisionDireccionList.add(sgrevisionDireccionListSgrevisionDireccionToAttach);
            }
            sgUsuario.setSgrevisionDireccionList(attachedSgrevisionDireccionList);
            List<SgaccionesMejora> attachedSgaccionesMejoraList = new ArrayList<SgaccionesMejora>();
            for (SgaccionesMejora sgaccionesMejoraListSgaccionesMejoraToAttach : sgUsuario.getSgaccionesMejoraList()) {
                sgaccionesMejoraListSgaccionesMejoraToAttach = em.getReference(sgaccionesMejoraListSgaccionesMejoraToAttach.getClass(), sgaccionesMejoraListSgaccionesMejoraToAttach.getSgaccionesMejoraPK());
                attachedSgaccionesMejoraList.add(sgaccionesMejoraListSgaccionesMejoraToAttach);
            }
            sgUsuario.setSgaccionesMejoraList(attachedSgaccionesMejoraList);
            List<Sgriesgo> attachedSgriesgoList = new ArrayList<Sgriesgo>();
            for (Sgriesgo sgriesgoListSgriesgoToAttach : sgUsuario.getSgriesgoList()) {
                sgriesgoListSgriesgoToAttach = em.getReference(sgriesgoListSgriesgoToAttach.getClass(), sgriesgoListSgriesgoToAttach.getSgriesgoPK());
                attachedSgriesgoList.add(sgriesgoListSgriesgoToAttach);
            }
            sgUsuario.setSgriesgoList(attachedSgriesgoList);
            List<SgcabEncuesta> attachedSgcabEncuestaList1 = new ArrayList<SgcabEncuesta>();
            for (SgcabEncuesta sgcabEncuestaList1SgcabEncuestaToAttach : sgUsuario.getSgcabEncuestaList1()) {
                sgcabEncuestaList1SgcabEncuestaToAttach = em.getReference(sgcabEncuestaList1SgcabEncuestaToAttach.getClass(), sgcabEncuestaList1SgcabEncuestaToAttach.getSgcabEncuestaPK());
                attachedSgcabEncuestaList1.add(sgcabEncuestaList1SgcabEncuestaToAttach);
            }
            sgUsuario.setSgcabEncuestaList1(attachedSgcabEncuestaList1);
            List<Sgauditado> attachedSgauditadoList = new ArrayList<Sgauditado>();
            for (Sgauditado sgauditadoListSgauditadoToAttach : sgUsuario.getSgauditadoList()) {
                sgauditadoListSgauditadoToAttach = em.getReference(sgauditadoListSgauditadoToAttach.getClass(), sgauditadoListSgauditadoToAttach.getSgauditadoPK());
                attachedSgauditadoList.add(sgauditadoListSgauditadoToAttach);
            }
            sgUsuario.setSgauditadoList(attachedSgauditadoList);
            List<SgpCabEncuesta> attachedSgpCabEncuestaList = new ArrayList<SgpCabEncuesta>();
            for (SgpCabEncuesta sgpCabEncuestaListSgpCabEncuestaToAttach : sgUsuario.getSgpCabEncuestaList()) {
                sgpCabEncuestaListSgpCabEncuestaToAttach = em.getReference(sgpCabEncuestaListSgpCabEncuestaToAttach.getClass(), sgpCabEncuestaListSgpCabEncuestaToAttach.getIdencuesta());
                attachedSgpCabEncuestaList.add(sgpCabEncuestaListSgpCabEncuestaToAttach);
            }
            sgUsuario.setSgpCabEncuestaList(attachedSgpCabEncuestaList);
            List<Sgsigue> attachedSgsigueList = new ArrayList<Sgsigue>();
            for (Sgsigue sgsigueListSgsigueToAttach : sgUsuario.getSgsigueList()) {
                sgsigueListSgsigueToAttach = em.getReference(sgsigueListSgsigueToAttach.getClass(), sgsigueListSgsigueToAttach.getSgsiguePK());
                attachedSgsigueList.add(sgsigueListSgsigueToAttach);
            }
            sgUsuario.setSgsigueList(attachedSgsigueList);
            List<Sgverifica> attachedSgverificaList = new ArrayList<Sgverifica>();
            for (Sgverifica sgverificaListSgverificaToAttach : sgUsuario.getSgverificaList()) {
                sgverificaListSgverificaToAttach = em.getReference(sgverificaListSgverificaToAttach.getClass(), sgverificaListSgverificaToAttach.getSgverificaPK());
                attachedSgverificaList.add(sgverificaListSgverificaToAttach);
            }
            sgUsuario.setSgverificaList(attachedSgverificaList);
            em.persist(sgUsuario);
            if (idrolAuditor != null) {
                idrolAuditor.getSgUsuarioList().add(sgUsuario);
                idrolAuditor = em.merge(idrolAuditor);
            }
            for (Sgdepartamento sgdepartamentoListSgdepartamento : sgUsuario.getSgdepartamentoList()) {
                sgdepartamentoListSgdepartamento.getSgUsuarioList().add(sgUsuario);
                sgdepartamentoListSgdepartamento = em.merge(sgdepartamentoListSgdepartamento);
            }
            for (SgseguimientoIndObj sgseguimientoIndObjListSgseguimientoIndObj : sgUsuario.getSgseguimientoIndObjList()) {
                sgseguimientoIndObjListSgseguimientoIndObj.getSgUsuarioList().add(sgUsuario);
                sgseguimientoIndObjListSgseguimientoIndObj = em.merge(sgseguimientoIndObjListSgseguimientoIndObj);
            }
            for (SgProgramas sgProgramasListSgProgramas : sgUsuario.getSgProgramasList()) {
                sgProgramasListSgProgramas.getSgUsuarioList().add(sgUsuario);
                sgProgramasListSgProgramas = em.merge(sgProgramasListSgProgramas);
            }
            for (Sgnc sgncListSgnc : sgUsuario.getSgncList()) {
                sgncListSgnc.getSgUsuarioList().add(sgUsuario);
                sgncListSgnc = em.merge(sgncListSgnc);
            }
            for (SgcabEncuesta sgcabEncuestaListSgcabEncuesta : sgUsuario.getSgcabEncuestaList()) {
                sgcabEncuestaListSgcabEncuesta.getSgUsuarioList().add(sgUsuario);
                sgcabEncuestaListSgcabEncuesta = em.merge(sgcabEncuestaListSgcabEncuesta);
            }
            for (SgdetalleRdireccion sgdetalleRdireccionListSgdetalleRdireccion : sgUsuario.getSgdetalleRdireccionList()) {
                sgdetalleRdireccionListSgdetalleRdireccion.getSgUsuarioList().add(sgUsuario);
                sgdetalleRdireccionListSgdetalleRdireccion = em.merge(sgdetalleRdireccionListSgdetalleRdireccion);
            }
            for (SgUsuariorol sgUsuariorolListSgUsuariorol : sgUsuario.getSgUsuariorolList()) {
                SgUsuario oldSgUsuarioOfSgUsuariorolListSgUsuariorol = sgUsuariorolListSgUsuariorol.getSgUsuario();
                sgUsuariorolListSgUsuariorol.setSgUsuario(sgUsuario);
                sgUsuariorolListSgUsuariorol = em.merge(sgUsuariorolListSgUsuariorol);
                if (oldSgUsuarioOfSgUsuariorolListSgUsuariorol != null) {
                    oldSgUsuarioOfSgUsuariorolListSgUsuariorol.getSgUsuariorolList().remove(sgUsuariorolListSgUsuariorol);
                    oldSgUsuarioOfSgUsuariorolListSgUsuariorol = em.merge(oldSgUsuarioOfSgUsuariorolListSgUsuariorol);
                }
            }
            for (SgdetalleRdireccion sgdetalleRdireccionList1SgdetalleRdireccion : sgUsuario.getSgdetalleRdireccionList1()) {
                SgUsuario oldCodUsuarioOfSgdetalleRdireccionList1SgdetalleRdireccion = sgdetalleRdireccionList1SgdetalleRdireccion.getCodUsuario();
                sgdetalleRdireccionList1SgdetalleRdireccion.setCodUsuario(sgUsuario);
                sgdetalleRdireccionList1SgdetalleRdireccion = em.merge(sgdetalleRdireccionList1SgdetalleRdireccion);
                if (oldCodUsuarioOfSgdetalleRdireccionList1SgdetalleRdireccion != null) {
                    oldCodUsuarioOfSgdetalleRdireccionList1SgdetalleRdireccion.getSgdetalleRdireccionList1().remove(sgdetalleRdireccionList1SgdetalleRdireccion);
                    oldCodUsuarioOfSgdetalleRdireccionList1SgdetalleRdireccion = em.merge(oldCodUsuarioOfSgdetalleRdireccionList1SgdetalleRdireccion);
                }
            }
            for (SgseguimientoDetalle sgseguimientoDetalleListSgseguimientoDetalle : sgUsuario.getSgseguimientoDetalleList()) {
                SgUsuario oldCodUsuarioOfSgseguimientoDetalleListSgseguimientoDetalle = sgseguimientoDetalleListSgseguimientoDetalle.getCodUsuario();
                sgseguimientoDetalleListSgseguimientoDetalle.setCodUsuario(sgUsuario);
                sgseguimientoDetalleListSgseguimientoDetalle = em.merge(sgseguimientoDetalleListSgseguimientoDetalle);
                if (oldCodUsuarioOfSgseguimientoDetalleListSgseguimientoDetalle != null) {
                    oldCodUsuarioOfSgseguimientoDetalleListSgseguimientoDetalle.getSgseguimientoDetalleList().remove(sgseguimientoDetalleListSgseguimientoDetalle);
                    oldCodUsuarioOfSgseguimientoDetalleListSgseguimientoDetalle = em.merge(oldCodUsuarioOfSgseguimientoDetalleListSgseguimientoDetalle);
                }
            }
            for (Sgnc sgncList1Sgnc : sgUsuario.getSgncList1()) {
                SgUsuario oldUsuarioDescribeOfSgncList1Sgnc = sgncList1Sgnc.getUsuarioDescribe();
                sgncList1Sgnc.setUsuarioDescribe(sgUsuario);
                sgncList1Sgnc = em.merge(sgncList1Sgnc);
                if (oldUsuarioDescribeOfSgncList1Sgnc != null) {
                    oldUsuarioDescribeOfSgncList1Sgnc.getSgncList1().remove(sgncList1Sgnc);
                    oldUsuarioDescribeOfSgncList1Sgnc = em.merge(oldUsuarioDescribeOfSgncList1Sgnc);
                }
            }
            for (Sgcausa sgcausaListSgcausa : sgUsuario.getSgcausaList()) {
                SgUsuario oldCodUsuarioOfSgcausaListSgcausa = sgcausaListSgcausa.getCodUsuario();
                sgcausaListSgcausa.setCodUsuario(sgUsuario);
                sgcausaListSgcausa = em.merge(sgcausaListSgcausa);
                if (oldCodUsuarioOfSgcausaListSgcausa != null) {
                    oldCodUsuarioOfSgcausaListSgcausa.getSgcausaList().remove(sgcausaListSgcausa);
                    oldCodUsuarioOfSgcausaListSgcausa = em.merge(oldCodUsuarioOfSgcausaListSgcausa);
                }
            }
            for (SgcapturaEncuesta sgcapturaEncuestaListSgcapturaEncuesta : sgUsuario.getSgcapturaEncuestaList()) {
                SgUsuario oldCodUsuarioOfSgcapturaEncuestaListSgcapturaEncuesta = sgcapturaEncuestaListSgcapturaEncuesta.getCodUsuario();
                sgcapturaEncuestaListSgcapturaEncuesta.setCodUsuario(sgUsuario);
                sgcapturaEncuestaListSgcapturaEncuesta = em.merge(sgcapturaEncuestaListSgcapturaEncuesta);
                if (oldCodUsuarioOfSgcapturaEncuestaListSgcapturaEncuesta != null) {
                    oldCodUsuarioOfSgcapturaEncuestaListSgcapturaEncuesta.getSgcapturaEncuestaList().remove(sgcapturaEncuestaListSgcapturaEncuesta);
                    oldCodUsuarioOfSgcapturaEncuestaListSgcapturaEncuesta = em.merge(oldCodUsuarioOfSgcapturaEncuestaListSgcapturaEncuesta);
                }
            }
            for (SgcorrecionNc sgcorrecionNcListSgcorrecionNc : sgUsuario.getSgcorrecionNcList()) {
                SgUsuario oldCodUsuarioOfSgcorrecionNcListSgcorrecionNc = sgcorrecionNcListSgcorrecionNc.getCodUsuario();
                sgcorrecionNcListSgcorrecionNc.setCodUsuario(sgUsuario);
                sgcorrecionNcListSgcorrecionNc = em.merge(sgcorrecionNcListSgcorrecionNc);
                if (oldCodUsuarioOfSgcorrecionNcListSgcorrecionNc != null) {
                    oldCodUsuarioOfSgcorrecionNcListSgcorrecionNc.getSgcorrecionNcList().remove(sgcorrecionNcListSgcorrecionNc);
                    oldCodUsuarioOfSgcorrecionNcListSgcorrecionNc = em.merge(oldCodUsuarioOfSgcorrecionNcListSgcorrecionNc);
                }
            }
            for (SgprogramaEmpleado sgprogramaEmpleadoListSgprogramaEmpleado : sgUsuario.getSgprogramaEmpleadoList()) {
                SgUsuario oldSgUsuarioOfSgprogramaEmpleadoListSgprogramaEmpleado = sgprogramaEmpleadoListSgprogramaEmpleado.getSgUsuario();
                sgprogramaEmpleadoListSgprogramaEmpleado.setSgUsuario(sgUsuario);
                sgprogramaEmpleadoListSgprogramaEmpleado = em.merge(sgprogramaEmpleadoListSgprogramaEmpleado);
                if (oldSgUsuarioOfSgprogramaEmpleadoListSgprogramaEmpleado != null) {
                    oldSgUsuarioOfSgprogramaEmpleadoListSgprogramaEmpleado.getSgprogramaEmpleadoList().remove(sgprogramaEmpleadoListSgprogramaEmpleado);
                    oldSgUsuarioOfSgprogramaEmpleadoListSgprogramaEmpleado = em.merge(oldSgUsuarioOfSgprogramaEmpleadoListSgprogramaEmpleado);
                }
            }
            for (SgrevisionDireccion sgrevisionDireccionListSgrevisionDireccion : sgUsuario.getSgrevisionDireccionList()) {
                SgUsuario oldCodUsuarioOfSgrevisionDireccionListSgrevisionDireccion = sgrevisionDireccionListSgrevisionDireccion.getCodUsuario();
                sgrevisionDireccionListSgrevisionDireccion.setCodUsuario(sgUsuario);
                sgrevisionDireccionListSgrevisionDireccion = em.merge(sgrevisionDireccionListSgrevisionDireccion);
                if (oldCodUsuarioOfSgrevisionDireccionListSgrevisionDireccion != null) {
                    oldCodUsuarioOfSgrevisionDireccionListSgrevisionDireccion.getSgrevisionDireccionList().remove(sgrevisionDireccionListSgrevisionDireccion);
                    oldCodUsuarioOfSgrevisionDireccionListSgrevisionDireccion = em.merge(oldCodUsuarioOfSgrevisionDireccionListSgrevisionDireccion);
                }
            }
            for (SgaccionesMejora sgaccionesMejoraListSgaccionesMejora : sgUsuario.getSgaccionesMejoraList()) {
                SgUsuario oldCodUsuarioOfSgaccionesMejoraListSgaccionesMejora = sgaccionesMejoraListSgaccionesMejora.getCodUsuario();
                sgaccionesMejoraListSgaccionesMejora.setCodUsuario(sgUsuario);
                sgaccionesMejoraListSgaccionesMejora = em.merge(sgaccionesMejoraListSgaccionesMejora);
                if (oldCodUsuarioOfSgaccionesMejoraListSgaccionesMejora != null) {
                    oldCodUsuarioOfSgaccionesMejoraListSgaccionesMejora.getSgaccionesMejoraList().remove(sgaccionesMejoraListSgaccionesMejora);
                    oldCodUsuarioOfSgaccionesMejoraListSgaccionesMejora = em.merge(oldCodUsuarioOfSgaccionesMejoraListSgaccionesMejora);
                }
            }
            for (Sgriesgo sgriesgoListSgriesgo : sgUsuario.getSgriesgoList()) {
                SgUsuario oldCodUsuarioOfSgriesgoListSgriesgo = sgriesgoListSgriesgo.getCodUsuario();
                sgriesgoListSgriesgo.setCodUsuario(sgUsuario);
                sgriesgoListSgriesgo = em.merge(sgriesgoListSgriesgo);
                if (oldCodUsuarioOfSgriesgoListSgriesgo != null) {
                    oldCodUsuarioOfSgriesgoListSgriesgo.getSgriesgoList().remove(sgriesgoListSgriesgo);
                    oldCodUsuarioOfSgriesgoListSgriesgo = em.merge(oldCodUsuarioOfSgriesgoListSgriesgo);
                }
            }
            for (SgcabEncuesta sgcabEncuestaList1SgcabEncuesta : sgUsuario.getSgcabEncuestaList1()) {
                SgUsuario oldCodUsuarioOfSgcabEncuestaList1SgcabEncuesta = sgcabEncuestaList1SgcabEncuesta.getCodUsuario();
                sgcabEncuestaList1SgcabEncuesta.setCodUsuario(sgUsuario);
                sgcabEncuestaList1SgcabEncuesta = em.merge(sgcabEncuestaList1SgcabEncuesta);
                if (oldCodUsuarioOfSgcabEncuestaList1SgcabEncuesta != null) {
                    oldCodUsuarioOfSgcabEncuestaList1SgcabEncuesta.getSgcabEncuestaList1().remove(sgcabEncuestaList1SgcabEncuesta);
                    oldCodUsuarioOfSgcabEncuestaList1SgcabEncuesta = em.merge(oldCodUsuarioOfSgcabEncuestaList1SgcabEncuesta);
                }
            }
            for (Sgauditado sgauditadoListSgauditado : sgUsuario.getSgauditadoList()) {
                SgUsuario oldCodUsuarioOfSgauditadoListSgauditado = sgauditadoListSgauditado.getCodUsuario();
                sgauditadoListSgauditado.setCodUsuario(sgUsuario);
                sgauditadoListSgauditado = em.merge(sgauditadoListSgauditado);
                if (oldCodUsuarioOfSgauditadoListSgauditado != null) {
                    oldCodUsuarioOfSgauditadoListSgauditado.getSgauditadoList().remove(sgauditadoListSgauditado);
                    oldCodUsuarioOfSgauditadoListSgauditado = em.merge(oldCodUsuarioOfSgauditadoListSgauditado);
                }
            }
            for (SgpCabEncuesta sgpCabEncuestaListSgpCabEncuesta : sgUsuario.getSgpCabEncuestaList()) {
                SgUsuario oldCodUsuarioOfSgpCabEncuestaListSgpCabEncuesta = sgpCabEncuestaListSgpCabEncuesta.getCodUsuario();
                sgpCabEncuestaListSgpCabEncuesta.setCodUsuario(sgUsuario);
                sgpCabEncuestaListSgpCabEncuesta = em.merge(sgpCabEncuestaListSgpCabEncuesta);
                if (oldCodUsuarioOfSgpCabEncuestaListSgpCabEncuesta != null) {
                    oldCodUsuarioOfSgpCabEncuestaListSgpCabEncuesta.getSgpCabEncuestaList().remove(sgpCabEncuestaListSgpCabEncuesta);
                    oldCodUsuarioOfSgpCabEncuestaListSgpCabEncuesta = em.merge(oldCodUsuarioOfSgpCabEncuestaListSgpCabEncuesta);
                }
            }
            for (Sgsigue sgsigueListSgsigue : sgUsuario.getSgsigueList()) {
                SgUsuario oldCodUsuarioOfSgsigueListSgsigue = sgsigueListSgsigue.getCodUsuario();
                sgsigueListSgsigue.setCodUsuario(sgUsuario);
                sgsigueListSgsigue = em.merge(sgsigueListSgsigue);
                if (oldCodUsuarioOfSgsigueListSgsigue != null) {
                    oldCodUsuarioOfSgsigueListSgsigue.getSgsigueList().remove(sgsigueListSgsigue);
                    oldCodUsuarioOfSgsigueListSgsigue = em.merge(oldCodUsuarioOfSgsigueListSgsigue);
                }
            }
            for (Sgverifica sgverificaListSgverifica : sgUsuario.getSgverificaList()) {
                SgUsuario oldCodUsuarioOfSgverificaListSgverifica = sgverificaListSgverifica.getCodUsuario();
                sgverificaListSgverifica.setCodUsuario(sgUsuario);
                sgverificaListSgverifica = em.merge(sgverificaListSgverifica);
                if (oldCodUsuarioOfSgverificaListSgverifica != null) {
                    oldCodUsuarioOfSgverificaListSgverifica.getSgverificaList().remove(sgverificaListSgverifica);
                    oldCodUsuarioOfSgverificaListSgverifica = em.merge(oldCodUsuarioOfSgverificaListSgverifica);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgUsuario(sgUsuario.getCodUsuario()) != null) {
                throw new PreexistingEntityException("SgUsuario " + sgUsuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgUsuario sgUsuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgUsuario persistentSgUsuario = em.find(SgUsuario.class, sgUsuario.getCodUsuario());
            SgrolAuditor idrolAuditorOld = persistentSgUsuario.getIdrolAuditor();
            SgrolAuditor idrolAuditorNew = sgUsuario.getIdrolAuditor();
            List<Sgdepartamento> sgdepartamentoListOld = persistentSgUsuario.getSgdepartamentoList();
            List<Sgdepartamento> sgdepartamentoListNew = sgUsuario.getSgdepartamentoList();
            List<SgseguimientoIndObj> sgseguimientoIndObjListOld = persistentSgUsuario.getSgseguimientoIndObjList();
            List<SgseguimientoIndObj> sgseguimientoIndObjListNew = sgUsuario.getSgseguimientoIndObjList();
            List<SgProgramas> sgProgramasListOld = persistentSgUsuario.getSgProgramasList();
            List<SgProgramas> sgProgramasListNew = sgUsuario.getSgProgramasList();
            List<Sgnc> sgncListOld = persistentSgUsuario.getSgncList();
            List<Sgnc> sgncListNew = sgUsuario.getSgncList();
            List<SgcabEncuesta> sgcabEncuestaListOld = persistentSgUsuario.getSgcabEncuestaList();
            List<SgcabEncuesta> sgcabEncuestaListNew = sgUsuario.getSgcabEncuestaList();
            List<SgdetalleRdireccion> sgdetalleRdireccionListOld = persistentSgUsuario.getSgdetalleRdireccionList();
            List<SgdetalleRdireccion> sgdetalleRdireccionListNew = sgUsuario.getSgdetalleRdireccionList();
            List<SgUsuariorol> sgUsuariorolListOld = persistentSgUsuario.getSgUsuariorolList();
            List<SgUsuariorol> sgUsuariorolListNew = sgUsuario.getSgUsuariorolList();
            List<SgdetalleRdireccion> sgdetalleRdireccionList1Old = persistentSgUsuario.getSgdetalleRdireccionList1();
            List<SgdetalleRdireccion> sgdetalleRdireccionList1New = sgUsuario.getSgdetalleRdireccionList1();
            List<SgseguimientoDetalle> sgseguimientoDetalleListOld = persistentSgUsuario.getSgseguimientoDetalleList();
            List<SgseguimientoDetalle> sgseguimientoDetalleListNew = sgUsuario.getSgseguimientoDetalleList();
            List<Sgnc> sgncList1Old = persistentSgUsuario.getSgncList1();
            List<Sgnc> sgncList1New = sgUsuario.getSgncList1();
            List<Sgcausa> sgcausaListOld = persistentSgUsuario.getSgcausaList();
            List<Sgcausa> sgcausaListNew = sgUsuario.getSgcausaList();
            List<SgcapturaEncuesta> sgcapturaEncuestaListOld = persistentSgUsuario.getSgcapturaEncuestaList();
            List<SgcapturaEncuesta> sgcapturaEncuestaListNew = sgUsuario.getSgcapturaEncuestaList();
            List<SgcorrecionNc> sgcorrecionNcListOld = persistentSgUsuario.getSgcorrecionNcList();
            List<SgcorrecionNc> sgcorrecionNcListNew = sgUsuario.getSgcorrecionNcList();
            List<SgprogramaEmpleado> sgprogramaEmpleadoListOld = persistentSgUsuario.getSgprogramaEmpleadoList();
            List<SgprogramaEmpleado> sgprogramaEmpleadoListNew = sgUsuario.getSgprogramaEmpleadoList();
            List<SgrevisionDireccion> sgrevisionDireccionListOld = persistentSgUsuario.getSgrevisionDireccionList();
            List<SgrevisionDireccion> sgrevisionDireccionListNew = sgUsuario.getSgrevisionDireccionList();
            List<SgaccionesMejora> sgaccionesMejoraListOld = persistentSgUsuario.getSgaccionesMejoraList();
            List<SgaccionesMejora> sgaccionesMejoraListNew = sgUsuario.getSgaccionesMejoraList();
            List<Sgriesgo> sgriesgoListOld = persistentSgUsuario.getSgriesgoList();
            List<Sgriesgo> sgriesgoListNew = sgUsuario.getSgriesgoList();
            List<SgcabEncuesta> sgcabEncuestaList1Old = persistentSgUsuario.getSgcabEncuestaList1();
            List<SgcabEncuesta> sgcabEncuestaList1New = sgUsuario.getSgcabEncuestaList1();
            List<Sgauditado> sgauditadoListOld = persistentSgUsuario.getSgauditadoList();
            List<Sgauditado> sgauditadoListNew = sgUsuario.getSgauditadoList();
            List<SgpCabEncuesta> sgpCabEncuestaListOld = persistentSgUsuario.getSgpCabEncuestaList();
            List<SgpCabEncuesta> sgpCabEncuestaListNew = sgUsuario.getSgpCabEncuestaList();
            List<Sgsigue> sgsigueListOld = persistentSgUsuario.getSgsigueList();
            List<Sgsigue> sgsigueListNew = sgUsuario.getSgsigueList();
            List<Sgverifica> sgverificaListOld = persistentSgUsuario.getSgverificaList();
            List<Sgverifica> sgverificaListNew = sgUsuario.getSgverificaList();
            List<String> illegalOrphanMessages = null;
            for (SgUsuariorol sgUsuariorolListOldSgUsuariorol : sgUsuariorolListOld) {
                if (!sgUsuariorolListNew.contains(sgUsuariorolListOldSgUsuariorol)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SgUsuariorol " + sgUsuariorolListOldSgUsuariorol + " since its sgUsuario field is not nullable.");
                }
            }
            for (Sgnc sgncList1OldSgnc : sgncList1Old) {
                if (!sgncList1New.contains(sgncList1OldSgnc)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sgnc " + sgncList1OldSgnc + " since its usuarioDescribe field is not nullable.");
                }
            }
            for (SgprogramaEmpleado sgprogramaEmpleadoListOldSgprogramaEmpleado : sgprogramaEmpleadoListOld) {
                if (!sgprogramaEmpleadoListNew.contains(sgprogramaEmpleadoListOldSgprogramaEmpleado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SgprogramaEmpleado " + sgprogramaEmpleadoListOldSgprogramaEmpleado + " since its sgUsuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idrolAuditorNew != null) {
                idrolAuditorNew = em.getReference(idrolAuditorNew.getClass(), idrolAuditorNew.getIdrolAuditor());
                sgUsuario.setIdrolAuditor(idrolAuditorNew);
            }
            List<Sgdepartamento> attachedSgdepartamentoListNew = new ArrayList<Sgdepartamento>();
            for (Sgdepartamento sgdepartamentoListNewSgdepartamentoToAttach : sgdepartamentoListNew) {
                sgdepartamentoListNewSgdepartamentoToAttach = em.getReference(sgdepartamentoListNewSgdepartamentoToAttach.getClass(), sgdepartamentoListNewSgdepartamentoToAttach.getIddept());
                attachedSgdepartamentoListNew.add(sgdepartamentoListNewSgdepartamentoToAttach);
            }
            sgdepartamentoListNew = attachedSgdepartamentoListNew;
            sgUsuario.setSgdepartamentoList(sgdepartamentoListNew);
            List<SgseguimientoIndObj> attachedSgseguimientoIndObjListNew = new ArrayList<SgseguimientoIndObj>();
            for (SgseguimientoIndObj sgseguimientoIndObjListNewSgseguimientoIndObjToAttach : sgseguimientoIndObjListNew) {
                sgseguimientoIndObjListNewSgseguimientoIndObjToAttach = em.getReference(sgseguimientoIndObjListNewSgseguimientoIndObjToAttach.getClass(), sgseguimientoIndObjListNewSgseguimientoIndObjToAttach.getSgseguimientoIndObjPK());
                attachedSgseguimientoIndObjListNew.add(sgseguimientoIndObjListNewSgseguimientoIndObjToAttach);
            }
            sgseguimientoIndObjListNew = attachedSgseguimientoIndObjListNew;
            sgUsuario.setSgseguimientoIndObjList(sgseguimientoIndObjListNew);
            List<SgProgramas> attachedSgProgramasListNew = new ArrayList<SgProgramas>();
            for (SgProgramas sgProgramasListNewSgProgramasToAttach : sgProgramasListNew) {
                sgProgramasListNewSgProgramasToAttach = em.getReference(sgProgramasListNewSgProgramasToAttach.getClass(), sgProgramasListNewSgProgramasToAttach.getSgProgramasPK());
                attachedSgProgramasListNew.add(sgProgramasListNewSgProgramasToAttach);
            }
            sgProgramasListNew = attachedSgProgramasListNew;
            sgUsuario.setSgProgramasList(sgProgramasListNew);
            List<Sgnc> attachedSgncListNew = new ArrayList<Sgnc>();
            for (Sgnc sgncListNewSgncToAttach : sgncListNew) {
                sgncListNewSgncToAttach = em.getReference(sgncListNewSgncToAttach.getClass(), sgncListNewSgncToAttach.getSgncPK());
                attachedSgncListNew.add(sgncListNewSgncToAttach);
            }
            sgncListNew = attachedSgncListNew;
            sgUsuario.setSgncList(sgncListNew);
            List<SgcabEncuesta> attachedSgcabEncuestaListNew = new ArrayList<SgcabEncuesta>();
            for (SgcabEncuesta sgcabEncuestaListNewSgcabEncuestaToAttach : sgcabEncuestaListNew) {
                sgcabEncuestaListNewSgcabEncuestaToAttach = em.getReference(sgcabEncuestaListNewSgcabEncuestaToAttach.getClass(), sgcabEncuestaListNewSgcabEncuestaToAttach.getSgcabEncuestaPK());
                attachedSgcabEncuestaListNew.add(sgcabEncuestaListNewSgcabEncuestaToAttach);
            }
            sgcabEncuestaListNew = attachedSgcabEncuestaListNew;
            sgUsuario.setSgcabEncuestaList(sgcabEncuestaListNew);
            List<SgdetalleRdireccion> attachedSgdetalleRdireccionListNew = new ArrayList<SgdetalleRdireccion>();
            for (SgdetalleRdireccion sgdetalleRdireccionListNewSgdetalleRdireccionToAttach : sgdetalleRdireccionListNew) {
                sgdetalleRdireccionListNewSgdetalleRdireccionToAttach = em.getReference(sgdetalleRdireccionListNewSgdetalleRdireccionToAttach.getClass(), sgdetalleRdireccionListNewSgdetalleRdireccionToAttach.getSgdetalleRdireccionPK());
                attachedSgdetalleRdireccionListNew.add(sgdetalleRdireccionListNewSgdetalleRdireccionToAttach);
            }
            sgdetalleRdireccionListNew = attachedSgdetalleRdireccionListNew;
            sgUsuario.setSgdetalleRdireccionList(sgdetalleRdireccionListNew);
            List<SgUsuariorol> attachedSgUsuariorolListNew = new ArrayList<SgUsuariorol>();
            for (SgUsuariorol sgUsuariorolListNewSgUsuariorolToAttach : sgUsuariorolListNew) {
                sgUsuariorolListNewSgUsuariorolToAttach = em.getReference(sgUsuariorolListNewSgUsuariorolToAttach.getClass(), sgUsuariorolListNewSgUsuariorolToAttach.getSgUsuariorolPK());
                attachedSgUsuariorolListNew.add(sgUsuariorolListNewSgUsuariorolToAttach);
            }
            sgUsuariorolListNew = attachedSgUsuariorolListNew;
            sgUsuario.setSgUsuariorolList(sgUsuariorolListNew);
            List<SgdetalleRdireccion> attachedSgdetalleRdireccionList1New = new ArrayList<SgdetalleRdireccion>();
            for (SgdetalleRdireccion sgdetalleRdireccionList1NewSgdetalleRdireccionToAttach : sgdetalleRdireccionList1New) {
                sgdetalleRdireccionList1NewSgdetalleRdireccionToAttach = em.getReference(sgdetalleRdireccionList1NewSgdetalleRdireccionToAttach.getClass(), sgdetalleRdireccionList1NewSgdetalleRdireccionToAttach.getSgdetalleRdireccionPK());
                attachedSgdetalleRdireccionList1New.add(sgdetalleRdireccionList1NewSgdetalleRdireccionToAttach);
            }
            sgdetalleRdireccionList1New = attachedSgdetalleRdireccionList1New;
            sgUsuario.setSgdetalleRdireccionList1(sgdetalleRdireccionList1New);
            List<SgseguimientoDetalle> attachedSgseguimientoDetalleListNew = new ArrayList<SgseguimientoDetalle>();
            for (SgseguimientoDetalle sgseguimientoDetalleListNewSgseguimientoDetalleToAttach : sgseguimientoDetalleListNew) {
                sgseguimientoDetalleListNewSgseguimientoDetalleToAttach = em.getReference(sgseguimientoDetalleListNewSgseguimientoDetalleToAttach.getClass(), sgseguimientoDetalleListNewSgseguimientoDetalleToAttach.getSgseguimientoDetallePK());
                attachedSgseguimientoDetalleListNew.add(sgseguimientoDetalleListNewSgseguimientoDetalleToAttach);
            }
            sgseguimientoDetalleListNew = attachedSgseguimientoDetalleListNew;
            sgUsuario.setSgseguimientoDetalleList(sgseguimientoDetalleListNew);
            List<Sgnc> attachedSgncList1New = new ArrayList<Sgnc>();
            for (Sgnc sgncList1NewSgncToAttach : sgncList1New) {
                sgncList1NewSgncToAttach = em.getReference(sgncList1NewSgncToAttach.getClass(), sgncList1NewSgncToAttach.getSgncPK());
                attachedSgncList1New.add(sgncList1NewSgncToAttach);
            }
            sgncList1New = attachedSgncList1New;
            sgUsuario.setSgncList1(sgncList1New);
            List<Sgcausa> attachedSgcausaListNew = new ArrayList<Sgcausa>();
            for (Sgcausa sgcausaListNewSgcausaToAttach : sgcausaListNew) {
                sgcausaListNewSgcausaToAttach = em.getReference(sgcausaListNewSgcausaToAttach.getClass(), sgcausaListNewSgcausaToAttach.getSgcausaPK());
                attachedSgcausaListNew.add(sgcausaListNewSgcausaToAttach);
            }
            sgcausaListNew = attachedSgcausaListNew;
            sgUsuario.setSgcausaList(sgcausaListNew);
            List<SgcapturaEncuesta> attachedSgcapturaEncuestaListNew = new ArrayList<SgcapturaEncuesta>();
            for (SgcapturaEncuesta sgcapturaEncuestaListNewSgcapturaEncuestaToAttach : sgcapturaEncuestaListNew) {
                sgcapturaEncuestaListNewSgcapturaEncuestaToAttach = em.getReference(sgcapturaEncuestaListNewSgcapturaEncuestaToAttach.getClass(), sgcapturaEncuestaListNewSgcapturaEncuestaToAttach.getSgcapturaEncuestaPK());
                attachedSgcapturaEncuestaListNew.add(sgcapturaEncuestaListNewSgcapturaEncuestaToAttach);
            }
            sgcapturaEncuestaListNew = attachedSgcapturaEncuestaListNew;
            sgUsuario.setSgcapturaEncuestaList(sgcapturaEncuestaListNew);
            List<SgcorrecionNc> attachedSgcorrecionNcListNew = new ArrayList<SgcorrecionNc>();
            for (SgcorrecionNc sgcorrecionNcListNewSgcorrecionNcToAttach : sgcorrecionNcListNew) {
                sgcorrecionNcListNewSgcorrecionNcToAttach = em.getReference(sgcorrecionNcListNewSgcorrecionNcToAttach.getClass(), sgcorrecionNcListNewSgcorrecionNcToAttach.getSgcorrecionNcPK());
                attachedSgcorrecionNcListNew.add(sgcorrecionNcListNewSgcorrecionNcToAttach);
            }
            sgcorrecionNcListNew = attachedSgcorrecionNcListNew;
            sgUsuario.setSgcorrecionNcList(sgcorrecionNcListNew);
            List<SgprogramaEmpleado> attachedSgprogramaEmpleadoListNew = new ArrayList<SgprogramaEmpleado>();
            for (SgprogramaEmpleado sgprogramaEmpleadoListNewSgprogramaEmpleadoToAttach : sgprogramaEmpleadoListNew) {
                sgprogramaEmpleadoListNewSgprogramaEmpleadoToAttach = em.getReference(sgprogramaEmpleadoListNewSgprogramaEmpleadoToAttach.getClass(), sgprogramaEmpleadoListNewSgprogramaEmpleadoToAttach.getSgprogramaEmpleadoPK());
                attachedSgprogramaEmpleadoListNew.add(sgprogramaEmpleadoListNewSgprogramaEmpleadoToAttach);
            }
            sgprogramaEmpleadoListNew = attachedSgprogramaEmpleadoListNew;
            sgUsuario.setSgprogramaEmpleadoList(sgprogramaEmpleadoListNew);
            List<SgrevisionDireccion> attachedSgrevisionDireccionListNew = new ArrayList<SgrevisionDireccion>();
            for (SgrevisionDireccion sgrevisionDireccionListNewSgrevisionDireccionToAttach : sgrevisionDireccionListNew) {
                sgrevisionDireccionListNewSgrevisionDireccionToAttach = em.getReference(sgrevisionDireccionListNewSgrevisionDireccionToAttach.getClass(), sgrevisionDireccionListNewSgrevisionDireccionToAttach.getSgrevisionDireccionPK());
                attachedSgrevisionDireccionListNew.add(sgrevisionDireccionListNewSgrevisionDireccionToAttach);
            }
            sgrevisionDireccionListNew = attachedSgrevisionDireccionListNew;
            sgUsuario.setSgrevisionDireccionList(sgrevisionDireccionListNew);
            List<SgaccionesMejora> attachedSgaccionesMejoraListNew = new ArrayList<SgaccionesMejora>();
            for (SgaccionesMejora sgaccionesMejoraListNewSgaccionesMejoraToAttach : sgaccionesMejoraListNew) {
                sgaccionesMejoraListNewSgaccionesMejoraToAttach = em.getReference(sgaccionesMejoraListNewSgaccionesMejoraToAttach.getClass(), sgaccionesMejoraListNewSgaccionesMejoraToAttach.getSgaccionesMejoraPK());
                attachedSgaccionesMejoraListNew.add(sgaccionesMejoraListNewSgaccionesMejoraToAttach);
            }
            sgaccionesMejoraListNew = attachedSgaccionesMejoraListNew;
            sgUsuario.setSgaccionesMejoraList(sgaccionesMejoraListNew);
            List<Sgriesgo> attachedSgriesgoListNew = new ArrayList<Sgriesgo>();
            for (Sgriesgo sgriesgoListNewSgriesgoToAttach : sgriesgoListNew) {
                sgriesgoListNewSgriesgoToAttach = em.getReference(sgriesgoListNewSgriesgoToAttach.getClass(), sgriesgoListNewSgriesgoToAttach.getSgriesgoPK());
                attachedSgriesgoListNew.add(sgriesgoListNewSgriesgoToAttach);
            }
            sgriesgoListNew = attachedSgriesgoListNew;
            sgUsuario.setSgriesgoList(sgriesgoListNew);
            List<SgcabEncuesta> attachedSgcabEncuestaList1New = new ArrayList<SgcabEncuesta>();
            for (SgcabEncuesta sgcabEncuestaList1NewSgcabEncuestaToAttach : sgcabEncuestaList1New) {
                sgcabEncuestaList1NewSgcabEncuestaToAttach = em.getReference(sgcabEncuestaList1NewSgcabEncuestaToAttach.getClass(), sgcabEncuestaList1NewSgcabEncuestaToAttach.getSgcabEncuestaPK());
                attachedSgcabEncuestaList1New.add(sgcabEncuestaList1NewSgcabEncuestaToAttach);
            }
            sgcabEncuestaList1New = attachedSgcabEncuestaList1New;
            sgUsuario.setSgcabEncuestaList1(sgcabEncuestaList1New);
            List<Sgauditado> attachedSgauditadoListNew = new ArrayList<Sgauditado>();
            for (Sgauditado sgauditadoListNewSgauditadoToAttach : sgauditadoListNew) {
                sgauditadoListNewSgauditadoToAttach = em.getReference(sgauditadoListNewSgauditadoToAttach.getClass(), sgauditadoListNewSgauditadoToAttach.getSgauditadoPK());
                attachedSgauditadoListNew.add(sgauditadoListNewSgauditadoToAttach);
            }
            sgauditadoListNew = attachedSgauditadoListNew;
            sgUsuario.setSgauditadoList(sgauditadoListNew);
            List<SgpCabEncuesta> attachedSgpCabEncuestaListNew = new ArrayList<SgpCabEncuesta>();
            for (SgpCabEncuesta sgpCabEncuestaListNewSgpCabEncuestaToAttach : sgpCabEncuestaListNew) {
                sgpCabEncuestaListNewSgpCabEncuestaToAttach = em.getReference(sgpCabEncuestaListNewSgpCabEncuestaToAttach.getClass(), sgpCabEncuestaListNewSgpCabEncuestaToAttach.getIdencuesta());
                attachedSgpCabEncuestaListNew.add(sgpCabEncuestaListNewSgpCabEncuestaToAttach);
            }
            sgpCabEncuestaListNew = attachedSgpCabEncuestaListNew;
            sgUsuario.setSgpCabEncuestaList(sgpCabEncuestaListNew);
            List<Sgsigue> attachedSgsigueListNew = new ArrayList<Sgsigue>();
            for (Sgsigue sgsigueListNewSgsigueToAttach : sgsigueListNew) {
                sgsigueListNewSgsigueToAttach = em.getReference(sgsigueListNewSgsigueToAttach.getClass(), sgsigueListNewSgsigueToAttach.getSgsiguePK());
                attachedSgsigueListNew.add(sgsigueListNewSgsigueToAttach);
            }
            sgsigueListNew = attachedSgsigueListNew;
            sgUsuario.setSgsigueList(sgsigueListNew);
            List<Sgverifica> attachedSgverificaListNew = new ArrayList<Sgverifica>();
            for (Sgverifica sgverificaListNewSgverificaToAttach : sgverificaListNew) {
                sgverificaListNewSgverificaToAttach = em.getReference(sgverificaListNewSgverificaToAttach.getClass(), sgverificaListNewSgverificaToAttach.getSgverificaPK());
                attachedSgverificaListNew.add(sgverificaListNewSgverificaToAttach);
            }
            sgverificaListNew = attachedSgverificaListNew;
            sgUsuario.setSgverificaList(sgverificaListNew);
            sgUsuario = em.merge(sgUsuario);
            if (idrolAuditorOld != null && !idrolAuditorOld.equals(idrolAuditorNew)) {
                idrolAuditorOld.getSgUsuarioList().remove(sgUsuario);
                idrolAuditorOld = em.merge(idrolAuditorOld);
            }
            if (idrolAuditorNew != null && !idrolAuditorNew.equals(idrolAuditorOld)) {
                idrolAuditorNew.getSgUsuarioList().add(sgUsuario);
                idrolAuditorNew = em.merge(idrolAuditorNew);
            }
            for (Sgdepartamento sgdepartamentoListOldSgdepartamento : sgdepartamentoListOld) {
                if (!sgdepartamentoListNew.contains(sgdepartamentoListOldSgdepartamento)) {
                    sgdepartamentoListOldSgdepartamento.getSgUsuarioList().remove(sgUsuario);
                    sgdepartamentoListOldSgdepartamento = em.merge(sgdepartamentoListOldSgdepartamento);
                }
            }
            for (Sgdepartamento sgdepartamentoListNewSgdepartamento : sgdepartamentoListNew) {
                if (!sgdepartamentoListOld.contains(sgdepartamentoListNewSgdepartamento)) {
                    sgdepartamentoListNewSgdepartamento.getSgUsuarioList().add(sgUsuario);
                    sgdepartamentoListNewSgdepartamento = em.merge(sgdepartamentoListNewSgdepartamento);
                }
            }
            for (SgseguimientoIndObj sgseguimientoIndObjListOldSgseguimientoIndObj : sgseguimientoIndObjListOld) {
                if (!sgseguimientoIndObjListNew.contains(sgseguimientoIndObjListOldSgseguimientoIndObj)) {
                    sgseguimientoIndObjListOldSgseguimientoIndObj.getSgUsuarioList().remove(sgUsuario);
                    sgseguimientoIndObjListOldSgseguimientoIndObj = em.merge(sgseguimientoIndObjListOldSgseguimientoIndObj);
                }
            }
            for (SgseguimientoIndObj sgseguimientoIndObjListNewSgseguimientoIndObj : sgseguimientoIndObjListNew) {
                if (!sgseguimientoIndObjListOld.contains(sgseguimientoIndObjListNewSgseguimientoIndObj)) {
                    sgseguimientoIndObjListNewSgseguimientoIndObj.getSgUsuarioList().add(sgUsuario);
                    sgseguimientoIndObjListNewSgseguimientoIndObj = em.merge(sgseguimientoIndObjListNewSgseguimientoIndObj);
                }
            }
            for (SgProgramas sgProgramasListOldSgProgramas : sgProgramasListOld) {
                if (!sgProgramasListNew.contains(sgProgramasListOldSgProgramas)) {
                    sgProgramasListOldSgProgramas.getSgUsuarioList().remove(sgUsuario);
                    sgProgramasListOldSgProgramas = em.merge(sgProgramasListOldSgProgramas);
                }
            }
            for (SgProgramas sgProgramasListNewSgProgramas : sgProgramasListNew) {
                if (!sgProgramasListOld.contains(sgProgramasListNewSgProgramas)) {
                    sgProgramasListNewSgProgramas.getSgUsuarioList().add(sgUsuario);
                    sgProgramasListNewSgProgramas = em.merge(sgProgramasListNewSgProgramas);
                }
            }
            for (Sgnc sgncListOldSgnc : sgncListOld) {
                if (!sgncListNew.contains(sgncListOldSgnc)) {
                    sgncListOldSgnc.getSgUsuarioList().remove(sgUsuario);
                    sgncListOldSgnc = em.merge(sgncListOldSgnc);
                }
            }
            for (Sgnc sgncListNewSgnc : sgncListNew) {
                if (!sgncListOld.contains(sgncListNewSgnc)) {
                    sgncListNewSgnc.getSgUsuarioList().add(sgUsuario);
                    sgncListNewSgnc = em.merge(sgncListNewSgnc);
                }
            }
            for (SgcabEncuesta sgcabEncuestaListOldSgcabEncuesta : sgcabEncuestaListOld) {
                if (!sgcabEncuestaListNew.contains(sgcabEncuestaListOldSgcabEncuesta)) {
                    sgcabEncuestaListOldSgcabEncuesta.getSgUsuarioList().remove(sgUsuario);
                    sgcabEncuestaListOldSgcabEncuesta = em.merge(sgcabEncuestaListOldSgcabEncuesta);
                }
            }
            for (SgcabEncuesta sgcabEncuestaListNewSgcabEncuesta : sgcabEncuestaListNew) {
                if (!sgcabEncuestaListOld.contains(sgcabEncuestaListNewSgcabEncuesta)) {
                    sgcabEncuestaListNewSgcabEncuesta.getSgUsuarioList().add(sgUsuario);
                    sgcabEncuestaListNewSgcabEncuesta = em.merge(sgcabEncuestaListNewSgcabEncuesta);
                }
            }
            for (SgdetalleRdireccion sgdetalleRdireccionListOldSgdetalleRdireccion : sgdetalleRdireccionListOld) {
                if (!sgdetalleRdireccionListNew.contains(sgdetalleRdireccionListOldSgdetalleRdireccion)) {
                    sgdetalleRdireccionListOldSgdetalleRdireccion.getSgUsuarioList().remove(sgUsuario);
                    sgdetalleRdireccionListOldSgdetalleRdireccion = em.merge(sgdetalleRdireccionListOldSgdetalleRdireccion);
                }
            }
            for (SgdetalleRdireccion sgdetalleRdireccionListNewSgdetalleRdireccion : sgdetalleRdireccionListNew) {
                if (!sgdetalleRdireccionListOld.contains(sgdetalleRdireccionListNewSgdetalleRdireccion)) {
                    sgdetalleRdireccionListNewSgdetalleRdireccion.getSgUsuarioList().add(sgUsuario);
                    sgdetalleRdireccionListNewSgdetalleRdireccion = em.merge(sgdetalleRdireccionListNewSgdetalleRdireccion);
                }
            }
            for (SgUsuariorol sgUsuariorolListNewSgUsuariorol : sgUsuariorolListNew) {
                if (!sgUsuariorolListOld.contains(sgUsuariorolListNewSgUsuariorol)) {
                    SgUsuario oldSgUsuarioOfSgUsuariorolListNewSgUsuariorol = sgUsuariorolListNewSgUsuariorol.getSgUsuario();
                    sgUsuariorolListNewSgUsuariorol.setSgUsuario(sgUsuario);
                    sgUsuariorolListNewSgUsuariorol = em.merge(sgUsuariorolListNewSgUsuariorol);
                    if (oldSgUsuarioOfSgUsuariorolListNewSgUsuariorol != null && !oldSgUsuarioOfSgUsuariorolListNewSgUsuariorol.equals(sgUsuario)) {
                        oldSgUsuarioOfSgUsuariorolListNewSgUsuariorol.getSgUsuariorolList().remove(sgUsuariorolListNewSgUsuariorol);
                        oldSgUsuarioOfSgUsuariorolListNewSgUsuariorol = em.merge(oldSgUsuarioOfSgUsuariorolListNewSgUsuariorol);
                    }
                }
            }
            for (SgdetalleRdireccion sgdetalleRdireccionList1OldSgdetalleRdireccion : sgdetalleRdireccionList1Old) {
                if (!sgdetalleRdireccionList1New.contains(sgdetalleRdireccionList1OldSgdetalleRdireccion)) {
                    sgdetalleRdireccionList1OldSgdetalleRdireccion.setCodUsuario(null);
                    sgdetalleRdireccionList1OldSgdetalleRdireccion = em.merge(sgdetalleRdireccionList1OldSgdetalleRdireccion);
                }
            }
            for (SgdetalleRdireccion sgdetalleRdireccionList1NewSgdetalleRdireccion : sgdetalleRdireccionList1New) {
                if (!sgdetalleRdireccionList1Old.contains(sgdetalleRdireccionList1NewSgdetalleRdireccion)) {
                    SgUsuario oldCodUsuarioOfSgdetalleRdireccionList1NewSgdetalleRdireccion = sgdetalleRdireccionList1NewSgdetalleRdireccion.getCodUsuario();
                    sgdetalleRdireccionList1NewSgdetalleRdireccion.setCodUsuario(sgUsuario);
                    sgdetalleRdireccionList1NewSgdetalleRdireccion = em.merge(sgdetalleRdireccionList1NewSgdetalleRdireccion);
                    if (oldCodUsuarioOfSgdetalleRdireccionList1NewSgdetalleRdireccion != null && !oldCodUsuarioOfSgdetalleRdireccionList1NewSgdetalleRdireccion.equals(sgUsuario)) {
                        oldCodUsuarioOfSgdetalleRdireccionList1NewSgdetalleRdireccion.getSgdetalleRdireccionList1().remove(sgdetalleRdireccionList1NewSgdetalleRdireccion);
                        oldCodUsuarioOfSgdetalleRdireccionList1NewSgdetalleRdireccion = em.merge(oldCodUsuarioOfSgdetalleRdireccionList1NewSgdetalleRdireccion);
                    }
                }
            }
            for (SgseguimientoDetalle sgseguimientoDetalleListOldSgseguimientoDetalle : sgseguimientoDetalleListOld) {
                if (!sgseguimientoDetalleListNew.contains(sgseguimientoDetalleListOldSgseguimientoDetalle)) {
                    sgseguimientoDetalleListOldSgseguimientoDetalle.setCodUsuario(null);
                    sgseguimientoDetalleListOldSgseguimientoDetalle = em.merge(sgseguimientoDetalleListOldSgseguimientoDetalle);
                }
            }
            for (SgseguimientoDetalle sgseguimientoDetalleListNewSgseguimientoDetalle : sgseguimientoDetalleListNew) {
                if (!sgseguimientoDetalleListOld.contains(sgseguimientoDetalleListNewSgseguimientoDetalle)) {
                    SgUsuario oldCodUsuarioOfSgseguimientoDetalleListNewSgseguimientoDetalle = sgseguimientoDetalleListNewSgseguimientoDetalle.getCodUsuario();
                    sgseguimientoDetalleListNewSgseguimientoDetalle.setCodUsuario(sgUsuario);
                    sgseguimientoDetalleListNewSgseguimientoDetalle = em.merge(sgseguimientoDetalleListNewSgseguimientoDetalle);
                    if (oldCodUsuarioOfSgseguimientoDetalleListNewSgseguimientoDetalle != null && !oldCodUsuarioOfSgseguimientoDetalleListNewSgseguimientoDetalle.equals(sgUsuario)) {
                        oldCodUsuarioOfSgseguimientoDetalleListNewSgseguimientoDetalle.getSgseguimientoDetalleList().remove(sgseguimientoDetalleListNewSgseguimientoDetalle);
                        oldCodUsuarioOfSgseguimientoDetalleListNewSgseguimientoDetalle = em.merge(oldCodUsuarioOfSgseguimientoDetalleListNewSgseguimientoDetalle);
                    }
                }
            }
            for (Sgnc sgncList1NewSgnc : sgncList1New) {
                if (!sgncList1Old.contains(sgncList1NewSgnc)) {
                    SgUsuario oldUsuarioDescribeOfSgncList1NewSgnc = sgncList1NewSgnc.getUsuarioDescribe();
                    sgncList1NewSgnc.setUsuarioDescribe(sgUsuario);
                    sgncList1NewSgnc = em.merge(sgncList1NewSgnc);
                    if (oldUsuarioDescribeOfSgncList1NewSgnc != null && !oldUsuarioDescribeOfSgncList1NewSgnc.equals(sgUsuario)) {
                        oldUsuarioDescribeOfSgncList1NewSgnc.getSgncList1().remove(sgncList1NewSgnc);
                        oldUsuarioDescribeOfSgncList1NewSgnc = em.merge(oldUsuarioDescribeOfSgncList1NewSgnc);
                    }
                }
            }
            for (Sgcausa sgcausaListOldSgcausa : sgcausaListOld) {
                if (!sgcausaListNew.contains(sgcausaListOldSgcausa)) {
                    sgcausaListOldSgcausa.setCodUsuario(null);
                    sgcausaListOldSgcausa = em.merge(sgcausaListOldSgcausa);
                }
            }
            for (Sgcausa sgcausaListNewSgcausa : sgcausaListNew) {
                if (!sgcausaListOld.contains(sgcausaListNewSgcausa)) {
                    SgUsuario oldCodUsuarioOfSgcausaListNewSgcausa = sgcausaListNewSgcausa.getCodUsuario();
                    sgcausaListNewSgcausa.setCodUsuario(sgUsuario);
                    sgcausaListNewSgcausa = em.merge(sgcausaListNewSgcausa);
                    if (oldCodUsuarioOfSgcausaListNewSgcausa != null && !oldCodUsuarioOfSgcausaListNewSgcausa.equals(sgUsuario)) {
                        oldCodUsuarioOfSgcausaListNewSgcausa.getSgcausaList().remove(sgcausaListNewSgcausa);
                        oldCodUsuarioOfSgcausaListNewSgcausa = em.merge(oldCodUsuarioOfSgcausaListNewSgcausa);
                    }
                }
            }
            for (SgcapturaEncuesta sgcapturaEncuestaListOldSgcapturaEncuesta : sgcapturaEncuestaListOld) {
                if (!sgcapturaEncuestaListNew.contains(sgcapturaEncuestaListOldSgcapturaEncuesta)) {
                    sgcapturaEncuestaListOldSgcapturaEncuesta.setCodUsuario(null);
                    sgcapturaEncuestaListOldSgcapturaEncuesta = em.merge(sgcapturaEncuestaListOldSgcapturaEncuesta);
                }
            }
            for (SgcapturaEncuesta sgcapturaEncuestaListNewSgcapturaEncuesta : sgcapturaEncuestaListNew) {
                if (!sgcapturaEncuestaListOld.contains(sgcapturaEncuestaListNewSgcapturaEncuesta)) {
                    SgUsuario oldCodUsuarioOfSgcapturaEncuestaListNewSgcapturaEncuesta = sgcapturaEncuestaListNewSgcapturaEncuesta.getCodUsuario();
                    sgcapturaEncuestaListNewSgcapturaEncuesta.setCodUsuario(sgUsuario);
                    sgcapturaEncuestaListNewSgcapturaEncuesta = em.merge(sgcapturaEncuestaListNewSgcapturaEncuesta);
                    if (oldCodUsuarioOfSgcapturaEncuestaListNewSgcapturaEncuesta != null && !oldCodUsuarioOfSgcapturaEncuestaListNewSgcapturaEncuesta.equals(sgUsuario)) {
                        oldCodUsuarioOfSgcapturaEncuestaListNewSgcapturaEncuesta.getSgcapturaEncuestaList().remove(sgcapturaEncuestaListNewSgcapturaEncuesta);
                        oldCodUsuarioOfSgcapturaEncuestaListNewSgcapturaEncuesta = em.merge(oldCodUsuarioOfSgcapturaEncuestaListNewSgcapturaEncuesta);
                    }
                }
            }
            for (SgcorrecionNc sgcorrecionNcListOldSgcorrecionNc : sgcorrecionNcListOld) {
                if (!sgcorrecionNcListNew.contains(sgcorrecionNcListOldSgcorrecionNc)) {
                    sgcorrecionNcListOldSgcorrecionNc.setCodUsuario(null);
                    sgcorrecionNcListOldSgcorrecionNc = em.merge(sgcorrecionNcListOldSgcorrecionNc);
                }
            }
            for (SgcorrecionNc sgcorrecionNcListNewSgcorrecionNc : sgcorrecionNcListNew) {
                if (!sgcorrecionNcListOld.contains(sgcorrecionNcListNewSgcorrecionNc)) {
                    SgUsuario oldCodUsuarioOfSgcorrecionNcListNewSgcorrecionNc = sgcorrecionNcListNewSgcorrecionNc.getCodUsuario();
                    sgcorrecionNcListNewSgcorrecionNc.setCodUsuario(sgUsuario);
                    sgcorrecionNcListNewSgcorrecionNc = em.merge(sgcorrecionNcListNewSgcorrecionNc);
                    if (oldCodUsuarioOfSgcorrecionNcListNewSgcorrecionNc != null && !oldCodUsuarioOfSgcorrecionNcListNewSgcorrecionNc.equals(sgUsuario)) {
                        oldCodUsuarioOfSgcorrecionNcListNewSgcorrecionNc.getSgcorrecionNcList().remove(sgcorrecionNcListNewSgcorrecionNc);
                        oldCodUsuarioOfSgcorrecionNcListNewSgcorrecionNc = em.merge(oldCodUsuarioOfSgcorrecionNcListNewSgcorrecionNc);
                    }
                }
            }
            for (SgprogramaEmpleado sgprogramaEmpleadoListNewSgprogramaEmpleado : sgprogramaEmpleadoListNew) {
                if (!sgprogramaEmpleadoListOld.contains(sgprogramaEmpleadoListNewSgprogramaEmpleado)) {
                    SgUsuario oldSgUsuarioOfSgprogramaEmpleadoListNewSgprogramaEmpleado = sgprogramaEmpleadoListNewSgprogramaEmpleado.getSgUsuario();
                    sgprogramaEmpleadoListNewSgprogramaEmpleado.setSgUsuario(sgUsuario);
                    sgprogramaEmpleadoListNewSgprogramaEmpleado = em.merge(sgprogramaEmpleadoListNewSgprogramaEmpleado);
                    if (oldSgUsuarioOfSgprogramaEmpleadoListNewSgprogramaEmpleado != null && !oldSgUsuarioOfSgprogramaEmpleadoListNewSgprogramaEmpleado.equals(sgUsuario)) {
                        oldSgUsuarioOfSgprogramaEmpleadoListNewSgprogramaEmpleado.getSgprogramaEmpleadoList().remove(sgprogramaEmpleadoListNewSgprogramaEmpleado);
                        oldSgUsuarioOfSgprogramaEmpleadoListNewSgprogramaEmpleado = em.merge(oldSgUsuarioOfSgprogramaEmpleadoListNewSgprogramaEmpleado);
                    }
                }
            }
            for (SgrevisionDireccion sgrevisionDireccionListOldSgrevisionDireccion : sgrevisionDireccionListOld) {
                if (!sgrevisionDireccionListNew.contains(sgrevisionDireccionListOldSgrevisionDireccion)) {
                    sgrevisionDireccionListOldSgrevisionDireccion.setCodUsuario(null);
                    sgrevisionDireccionListOldSgrevisionDireccion = em.merge(sgrevisionDireccionListOldSgrevisionDireccion);
                }
            }
            for (SgrevisionDireccion sgrevisionDireccionListNewSgrevisionDireccion : sgrevisionDireccionListNew) {
                if (!sgrevisionDireccionListOld.contains(sgrevisionDireccionListNewSgrevisionDireccion)) {
                    SgUsuario oldCodUsuarioOfSgrevisionDireccionListNewSgrevisionDireccion = sgrevisionDireccionListNewSgrevisionDireccion.getCodUsuario();
                    sgrevisionDireccionListNewSgrevisionDireccion.setCodUsuario(sgUsuario);
                    sgrevisionDireccionListNewSgrevisionDireccion = em.merge(sgrevisionDireccionListNewSgrevisionDireccion);
                    if (oldCodUsuarioOfSgrevisionDireccionListNewSgrevisionDireccion != null && !oldCodUsuarioOfSgrevisionDireccionListNewSgrevisionDireccion.equals(sgUsuario)) {
                        oldCodUsuarioOfSgrevisionDireccionListNewSgrevisionDireccion.getSgrevisionDireccionList().remove(sgrevisionDireccionListNewSgrevisionDireccion);
                        oldCodUsuarioOfSgrevisionDireccionListNewSgrevisionDireccion = em.merge(oldCodUsuarioOfSgrevisionDireccionListNewSgrevisionDireccion);
                    }
                }
            }
            for (SgaccionesMejora sgaccionesMejoraListOldSgaccionesMejora : sgaccionesMejoraListOld) {
                if (!sgaccionesMejoraListNew.contains(sgaccionesMejoraListOldSgaccionesMejora)) {
                    sgaccionesMejoraListOldSgaccionesMejora.setCodUsuario(null);
                    sgaccionesMejoraListOldSgaccionesMejora = em.merge(sgaccionesMejoraListOldSgaccionesMejora);
                }
            }
            for (SgaccionesMejora sgaccionesMejoraListNewSgaccionesMejora : sgaccionesMejoraListNew) {
                if (!sgaccionesMejoraListOld.contains(sgaccionesMejoraListNewSgaccionesMejora)) {
                    SgUsuario oldCodUsuarioOfSgaccionesMejoraListNewSgaccionesMejora = sgaccionesMejoraListNewSgaccionesMejora.getCodUsuario();
                    sgaccionesMejoraListNewSgaccionesMejora.setCodUsuario(sgUsuario);
                    sgaccionesMejoraListNewSgaccionesMejora = em.merge(sgaccionesMejoraListNewSgaccionesMejora);
                    if (oldCodUsuarioOfSgaccionesMejoraListNewSgaccionesMejora != null && !oldCodUsuarioOfSgaccionesMejoraListNewSgaccionesMejora.equals(sgUsuario)) {
                        oldCodUsuarioOfSgaccionesMejoraListNewSgaccionesMejora.getSgaccionesMejoraList().remove(sgaccionesMejoraListNewSgaccionesMejora);
                        oldCodUsuarioOfSgaccionesMejoraListNewSgaccionesMejora = em.merge(oldCodUsuarioOfSgaccionesMejoraListNewSgaccionesMejora);
                    }
                }
            }
            for (Sgriesgo sgriesgoListOldSgriesgo : sgriesgoListOld) {
                if (!sgriesgoListNew.contains(sgriesgoListOldSgriesgo)) {
                    sgriesgoListOldSgriesgo.setCodUsuario(null);
                    sgriesgoListOldSgriesgo = em.merge(sgriesgoListOldSgriesgo);
                }
            }
            for (Sgriesgo sgriesgoListNewSgriesgo : sgriesgoListNew) {
                if (!sgriesgoListOld.contains(sgriesgoListNewSgriesgo)) {
                    SgUsuario oldCodUsuarioOfSgriesgoListNewSgriesgo = sgriesgoListNewSgriesgo.getCodUsuario();
                    sgriesgoListNewSgriesgo.setCodUsuario(sgUsuario);
                    sgriesgoListNewSgriesgo = em.merge(sgriesgoListNewSgriesgo);
                    if (oldCodUsuarioOfSgriesgoListNewSgriesgo != null && !oldCodUsuarioOfSgriesgoListNewSgriesgo.equals(sgUsuario)) {
                        oldCodUsuarioOfSgriesgoListNewSgriesgo.getSgriesgoList().remove(sgriesgoListNewSgriesgo);
                        oldCodUsuarioOfSgriesgoListNewSgriesgo = em.merge(oldCodUsuarioOfSgriesgoListNewSgriesgo);
                    }
                }
            }
            for (SgcabEncuesta sgcabEncuestaList1OldSgcabEncuesta : sgcabEncuestaList1Old) {
                if (!sgcabEncuestaList1New.contains(sgcabEncuestaList1OldSgcabEncuesta)) {
                    sgcabEncuestaList1OldSgcabEncuesta.setCodUsuario(null);
                    sgcabEncuestaList1OldSgcabEncuesta = em.merge(sgcabEncuestaList1OldSgcabEncuesta);
                }
            }
            for (SgcabEncuesta sgcabEncuestaList1NewSgcabEncuesta : sgcabEncuestaList1New) {
                if (!sgcabEncuestaList1Old.contains(sgcabEncuestaList1NewSgcabEncuesta)) {
                    SgUsuario oldCodUsuarioOfSgcabEncuestaList1NewSgcabEncuesta = sgcabEncuestaList1NewSgcabEncuesta.getCodUsuario();
                    sgcabEncuestaList1NewSgcabEncuesta.setCodUsuario(sgUsuario);
                    sgcabEncuestaList1NewSgcabEncuesta = em.merge(sgcabEncuestaList1NewSgcabEncuesta);
                    if (oldCodUsuarioOfSgcabEncuestaList1NewSgcabEncuesta != null && !oldCodUsuarioOfSgcabEncuestaList1NewSgcabEncuesta.equals(sgUsuario)) {
                        oldCodUsuarioOfSgcabEncuestaList1NewSgcabEncuesta.getSgcabEncuestaList1().remove(sgcabEncuestaList1NewSgcabEncuesta);
                        oldCodUsuarioOfSgcabEncuestaList1NewSgcabEncuesta = em.merge(oldCodUsuarioOfSgcabEncuestaList1NewSgcabEncuesta);
                    }
                }
            }
            for (Sgauditado sgauditadoListOldSgauditado : sgauditadoListOld) {
                if (!sgauditadoListNew.contains(sgauditadoListOldSgauditado)) {
                    sgauditadoListOldSgauditado.setCodUsuario(null);
                    sgauditadoListOldSgauditado = em.merge(sgauditadoListOldSgauditado);
                }
            }
            for (Sgauditado sgauditadoListNewSgauditado : sgauditadoListNew) {
                if (!sgauditadoListOld.contains(sgauditadoListNewSgauditado)) {
                    SgUsuario oldCodUsuarioOfSgauditadoListNewSgauditado = sgauditadoListNewSgauditado.getCodUsuario();
                    sgauditadoListNewSgauditado.setCodUsuario(sgUsuario);
                    sgauditadoListNewSgauditado = em.merge(sgauditadoListNewSgauditado);
                    if (oldCodUsuarioOfSgauditadoListNewSgauditado != null && !oldCodUsuarioOfSgauditadoListNewSgauditado.equals(sgUsuario)) {
                        oldCodUsuarioOfSgauditadoListNewSgauditado.getSgauditadoList().remove(sgauditadoListNewSgauditado);
                        oldCodUsuarioOfSgauditadoListNewSgauditado = em.merge(oldCodUsuarioOfSgauditadoListNewSgauditado);
                    }
                }
            }
            for (SgpCabEncuesta sgpCabEncuestaListOldSgpCabEncuesta : sgpCabEncuestaListOld) {
                if (!sgpCabEncuestaListNew.contains(sgpCabEncuestaListOldSgpCabEncuesta)) {
                    sgpCabEncuestaListOldSgpCabEncuesta.setCodUsuario(null);
                    sgpCabEncuestaListOldSgpCabEncuesta = em.merge(sgpCabEncuestaListOldSgpCabEncuesta);
                }
            }
            for (SgpCabEncuesta sgpCabEncuestaListNewSgpCabEncuesta : sgpCabEncuestaListNew) {
                if (!sgpCabEncuestaListOld.contains(sgpCabEncuestaListNewSgpCabEncuesta)) {
                    SgUsuario oldCodUsuarioOfSgpCabEncuestaListNewSgpCabEncuesta = sgpCabEncuestaListNewSgpCabEncuesta.getCodUsuario();
                    sgpCabEncuestaListNewSgpCabEncuesta.setCodUsuario(sgUsuario);
                    sgpCabEncuestaListNewSgpCabEncuesta = em.merge(sgpCabEncuestaListNewSgpCabEncuesta);
                    if (oldCodUsuarioOfSgpCabEncuestaListNewSgpCabEncuesta != null && !oldCodUsuarioOfSgpCabEncuestaListNewSgpCabEncuesta.equals(sgUsuario)) {
                        oldCodUsuarioOfSgpCabEncuestaListNewSgpCabEncuesta.getSgpCabEncuestaList().remove(sgpCabEncuestaListNewSgpCabEncuesta);
                        oldCodUsuarioOfSgpCabEncuestaListNewSgpCabEncuesta = em.merge(oldCodUsuarioOfSgpCabEncuestaListNewSgpCabEncuesta);
                    }
                }
            }
            for (Sgsigue sgsigueListOldSgsigue : sgsigueListOld) {
                if (!sgsigueListNew.contains(sgsigueListOldSgsigue)) {
                    sgsigueListOldSgsigue.setCodUsuario(null);
                    sgsigueListOldSgsigue = em.merge(sgsigueListOldSgsigue);
                }
            }
            for (Sgsigue sgsigueListNewSgsigue : sgsigueListNew) {
                if (!sgsigueListOld.contains(sgsigueListNewSgsigue)) {
                    SgUsuario oldCodUsuarioOfSgsigueListNewSgsigue = sgsigueListNewSgsigue.getCodUsuario();
                    sgsigueListNewSgsigue.setCodUsuario(sgUsuario);
                    sgsigueListNewSgsigue = em.merge(sgsigueListNewSgsigue);
                    if (oldCodUsuarioOfSgsigueListNewSgsigue != null && !oldCodUsuarioOfSgsigueListNewSgsigue.equals(sgUsuario)) {
                        oldCodUsuarioOfSgsigueListNewSgsigue.getSgsigueList().remove(sgsigueListNewSgsigue);
                        oldCodUsuarioOfSgsigueListNewSgsigue = em.merge(oldCodUsuarioOfSgsigueListNewSgsigue);
                    }
                }
            }
            for (Sgverifica sgverificaListOldSgverifica : sgverificaListOld) {
                if (!sgverificaListNew.contains(sgverificaListOldSgverifica)) {
                    sgverificaListOldSgverifica.setCodUsuario(null);
                    sgverificaListOldSgverifica = em.merge(sgverificaListOldSgverifica);
                }
            }
            for (Sgverifica sgverificaListNewSgverifica : sgverificaListNew) {
                if (!sgverificaListOld.contains(sgverificaListNewSgverifica)) {
                    SgUsuario oldCodUsuarioOfSgverificaListNewSgverifica = sgverificaListNewSgverifica.getCodUsuario();
                    sgverificaListNewSgverifica.setCodUsuario(sgUsuario);
                    sgverificaListNewSgverifica = em.merge(sgverificaListNewSgverifica);
                    if (oldCodUsuarioOfSgverificaListNewSgverifica != null && !oldCodUsuarioOfSgverificaListNewSgverifica.equals(sgUsuario)) {
                        oldCodUsuarioOfSgverificaListNewSgverifica.getSgverificaList().remove(sgverificaListNewSgverifica);
                        oldCodUsuarioOfSgverificaListNewSgverifica = em.merge(oldCodUsuarioOfSgverificaListNewSgverifica);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = sgUsuario.getCodUsuario();
                if (findSgUsuario(id) == null) {
                    throw new NonexistentEntityException("The sgUsuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgUsuario sgUsuario;
            try {
                sgUsuario = em.getReference(SgUsuario.class, id);
                sgUsuario.getCodUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgUsuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SgUsuariorol> sgUsuariorolListOrphanCheck = sgUsuario.getSgUsuariorolList();
            for (SgUsuariorol sgUsuariorolListOrphanCheckSgUsuariorol : sgUsuariorolListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SgUsuario (" + sgUsuario + ") cannot be destroyed since the SgUsuariorol " + sgUsuariorolListOrphanCheckSgUsuariorol + " in its sgUsuariorolList field has a non-nullable sgUsuario field.");
            }
            List<Sgnc> sgncList1OrphanCheck = sgUsuario.getSgncList1();
            for (Sgnc sgncList1OrphanCheckSgnc : sgncList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SgUsuario (" + sgUsuario + ") cannot be destroyed since the Sgnc " + sgncList1OrphanCheckSgnc + " in its sgncList1 field has a non-nullable usuarioDescribe field.");
            }
            List<SgprogramaEmpleado> sgprogramaEmpleadoListOrphanCheck = sgUsuario.getSgprogramaEmpleadoList();
            for (SgprogramaEmpleado sgprogramaEmpleadoListOrphanCheckSgprogramaEmpleado : sgprogramaEmpleadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SgUsuario (" + sgUsuario + ") cannot be destroyed since the SgprogramaEmpleado " + sgprogramaEmpleadoListOrphanCheckSgprogramaEmpleado + " in its sgprogramaEmpleadoList field has a non-nullable sgUsuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            SgrolAuditor idrolAuditor = sgUsuario.getIdrolAuditor();
            if (idrolAuditor != null) {
                idrolAuditor.getSgUsuarioList().remove(sgUsuario);
                idrolAuditor = em.merge(idrolAuditor);
            }
            List<Sgdepartamento> sgdepartamentoList = sgUsuario.getSgdepartamentoList();
            for (Sgdepartamento sgdepartamentoListSgdepartamento : sgdepartamentoList) {
                sgdepartamentoListSgdepartamento.getSgUsuarioList().remove(sgUsuario);
                sgdepartamentoListSgdepartamento = em.merge(sgdepartamentoListSgdepartamento);
            }
            List<SgseguimientoIndObj> sgseguimientoIndObjList = sgUsuario.getSgseguimientoIndObjList();
            for (SgseguimientoIndObj sgseguimientoIndObjListSgseguimientoIndObj : sgseguimientoIndObjList) {
                sgseguimientoIndObjListSgseguimientoIndObj.getSgUsuarioList().remove(sgUsuario);
                sgseguimientoIndObjListSgseguimientoIndObj = em.merge(sgseguimientoIndObjListSgseguimientoIndObj);
            }
            List<SgProgramas> sgProgramasList = sgUsuario.getSgProgramasList();
            for (SgProgramas sgProgramasListSgProgramas : sgProgramasList) {
                sgProgramasListSgProgramas.getSgUsuarioList().remove(sgUsuario);
                sgProgramasListSgProgramas = em.merge(sgProgramasListSgProgramas);
            }
            List<Sgnc> sgncList = sgUsuario.getSgncList();
            for (Sgnc sgncListSgnc : sgncList) {
                sgncListSgnc.getSgUsuarioList().remove(sgUsuario);
                sgncListSgnc = em.merge(sgncListSgnc);
            }
            List<SgcabEncuesta> sgcabEncuestaList = sgUsuario.getSgcabEncuestaList();
            for (SgcabEncuesta sgcabEncuestaListSgcabEncuesta : sgcabEncuestaList) {
                sgcabEncuestaListSgcabEncuesta.getSgUsuarioList().remove(sgUsuario);
                sgcabEncuestaListSgcabEncuesta = em.merge(sgcabEncuestaListSgcabEncuesta);
            }
            List<SgdetalleRdireccion> sgdetalleRdireccionList = sgUsuario.getSgdetalleRdireccionList();
            for (SgdetalleRdireccion sgdetalleRdireccionListSgdetalleRdireccion : sgdetalleRdireccionList) {
                sgdetalleRdireccionListSgdetalleRdireccion.getSgUsuarioList().remove(sgUsuario);
                sgdetalleRdireccionListSgdetalleRdireccion = em.merge(sgdetalleRdireccionListSgdetalleRdireccion);
            }
            List<SgdetalleRdireccion> sgdetalleRdireccionList1 = sgUsuario.getSgdetalleRdireccionList1();
            for (SgdetalleRdireccion sgdetalleRdireccionList1SgdetalleRdireccion : sgdetalleRdireccionList1) {
                sgdetalleRdireccionList1SgdetalleRdireccion.setCodUsuario(null);
                sgdetalleRdireccionList1SgdetalleRdireccion = em.merge(sgdetalleRdireccionList1SgdetalleRdireccion);
            }
            List<SgseguimientoDetalle> sgseguimientoDetalleList = sgUsuario.getSgseguimientoDetalleList();
            for (SgseguimientoDetalle sgseguimientoDetalleListSgseguimientoDetalle : sgseguimientoDetalleList) {
                sgseguimientoDetalleListSgseguimientoDetalle.setCodUsuario(null);
                sgseguimientoDetalleListSgseguimientoDetalle = em.merge(sgseguimientoDetalleListSgseguimientoDetalle);
            }
            List<Sgcausa> sgcausaList = sgUsuario.getSgcausaList();
            for (Sgcausa sgcausaListSgcausa : sgcausaList) {
                sgcausaListSgcausa.setCodUsuario(null);
                sgcausaListSgcausa = em.merge(sgcausaListSgcausa);
            }
            List<SgcapturaEncuesta> sgcapturaEncuestaList = sgUsuario.getSgcapturaEncuestaList();
            for (SgcapturaEncuesta sgcapturaEncuestaListSgcapturaEncuesta : sgcapturaEncuestaList) {
                sgcapturaEncuestaListSgcapturaEncuesta.setCodUsuario(null);
                sgcapturaEncuestaListSgcapturaEncuesta = em.merge(sgcapturaEncuestaListSgcapturaEncuesta);
            }
            List<SgcorrecionNc> sgcorrecionNcList = sgUsuario.getSgcorrecionNcList();
            for (SgcorrecionNc sgcorrecionNcListSgcorrecionNc : sgcorrecionNcList) {
                sgcorrecionNcListSgcorrecionNc.setCodUsuario(null);
                sgcorrecionNcListSgcorrecionNc = em.merge(sgcorrecionNcListSgcorrecionNc);
            }
            List<SgrevisionDireccion> sgrevisionDireccionList = sgUsuario.getSgrevisionDireccionList();
            for (SgrevisionDireccion sgrevisionDireccionListSgrevisionDireccion : sgrevisionDireccionList) {
                sgrevisionDireccionListSgrevisionDireccion.setCodUsuario(null);
                sgrevisionDireccionListSgrevisionDireccion = em.merge(sgrevisionDireccionListSgrevisionDireccion);
            }
            List<SgaccionesMejora> sgaccionesMejoraList = sgUsuario.getSgaccionesMejoraList();
            for (SgaccionesMejora sgaccionesMejoraListSgaccionesMejora : sgaccionesMejoraList) {
                sgaccionesMejoraListSgaccionesMejora.setCodUsuario(null);
                sgaccionesMejoraListSgaccionesMejora = em.merge(sgaccionesMejoraListSgaccionesMejora);
            }
            List<Sgriesgo> sgriesgoList = sgUsuario.getSgriesgoList();
            for (Sgriesgo sgriesgoListSgriesgo : sgriesgoList) {
                sgriesgoListSgriesgo.setCodUsuario(null);
                sgriesgoListSgriesgo = em.merge(sgriesgoListSgriesgo);
            }
            List<SgcabEncuesta> sgcabEncuestaList1 = sgUsuario.getSgcabEncuestaList1();
            for (SgcabEncuesta sgcabEncuestaList1SgcabEncuesta : sgcabEncuestaList1) {
                sgcabEncuestaList1SgcabEncuesta.setCodUsuario(null);
                sgcabEncuestaList1SgcabEncuesta = em.merge(sgcabEncuestaList1SgcabEncuesta);
            }
            List<Sgauditado> sgauditadoList = sgUsuario.getSgauditadoList();
            for (Sgauditado sgauditadoListSgauditado : sgauditadoList) {
                sgauditadoListSgauditado.setCodUsuario(null);
                sgauditadoListSgauditado = em.merge(sgauditadoListSgauditado);
            }
            List<SgpCabEncuesta> sgpCabEncuestaList = sgUsuario.getSgpCabEncuestaList();
            for (SgpCabEncuesta sgpCabEncuestaListSgpCabEncuesta : sgpCabEncuestaList) {
                sgpCabEncuestaListSgpCabEncuesta.setCodUsuario(null);
                sgpCabEncuestaListSgpCabEncuesta = em.merge(sgpCabEncuestaListSgpCabEncuesta);
            }
            List<Sgsigue> sgsigueList = sgUsuario.getSgsigueList();
            for (Sgsigue sgsigueListSgsigue : sgsigueList) {
                sgsigueListSgsigue.setCodUsuario(null);
                sgsigueListSgsigue = em.merge(sgsigueListSgsigue);
            }
            List<Sgverifica> sgverificaList = sgUsuario.getSgverificaList();
            for (Sgverifica sgverificaListSgverifica : sgverificaList) {
                sgverificaListSgverifica.setCodUsuario(null);
                sgverificaListSgverifica = em.merge(sgverificaListSgverifica);
            }
            em.remove(sgUsuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgUsuario> findSgUsuarioEntities() {
        return findSgUsuarioEntities(true, -1, -1);
    }

    public List<SgUsuario> findSgUsuarioEntities(int maxResults, int firstResult) {
        return findSgUsuarioEntities(false, maxResults, firstResult);
    }

    private List<SgUsuario> findSgUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgUsuario.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public SgUsuario findSgUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgUsuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgUsuario> rt = cq.from(SgUsuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
