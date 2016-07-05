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
import sg.sistemas.entidades.SgprocesosRe;
import sg.sistemas.entidades.Sgcriticidad;
import sg.sistemas.entidades.Sgestado;
import sg.sistemas.entidades.SgUsuario;
import sg.sistemas.entidades.SgcorrecionNc;
import sg.sistemas.entidades.Sgcentro;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.Sgdepartamento;
import sg.sistemas.entidades.Sgcausa;
import sg.sistemas.entidades.Sgnc;
import sg.sistemas.entidades.SgncPK;
import sg.sistemas.entidades.Sgsigue;
import sg.sistemas.entidades.Sgverifica;

/**
 *
 * @author Misael
 */
public class SgncJpaController implements Serializable {

    public SgncJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sgnc sgnc) throws PreexistingEntityException, Exception {
        if (sgnc.getSgncPK() == null) {
            sgnc.setSgncPK(new SgncPK());
        }
        if (sgnc.getSgcentroList() == null) {
            sgnc.setSgcentroList(new ArrayList<Sgcentro>());
        }
        if (sgnc.getSgdepartamentoList() == null) {
            sgnc.setSgdepartamentoList(new ArrayList<Sgdepartamento>());
        }
        if (sgnc.getSgUsuarioList() == null) {
            sgnc.setSgUsuarioList(new ArrayList<SgUsuario>());
        }
        if (sgnc.getSgcausaList() == null) {
            sgnc.setSgcausaList(new ArrayList<Sgcausa>());
        }
        if (sgnc.getSgsigueList() == null) {
            sgnc.setSgsigueList(new ArrayList<Sgsigue>());
        }
        if (sgnc.getSgverificaList() == null) {
            sgnc.setSgverificaList(new ArrayList<Sgverifica>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgprocesosRe sgprocesosRe = sgnc.getSgprocesosRe();
            if (sgprocesosRe != null) {
                sgprocesosRe = em.getReference(sgprocesosRe.getClass(), sgprocesosRe.getSgprocesosRePK());
                sgnc.setSgprocesosRe(sgprocesosRe);
            }
            Sgcriticidad idcriticidad = sgnc.getIdcriticidad();
            if (idcriticidad != null) {
                idcriticidad = em.getReference(idcriticidad.getClass(), idcriticidad.getIdcriticidad());
                sgnc.setIdcriticidad(idcriticidad);
            }
            Sgestado idestado = sgnc.getIdestado();
            if (idestado != null) {
                idestado = em.getReference(idestado.getClass(), idestado.getIdestado());
                sgnc.setIdestado(idestado);
            }
            SgUsuario usuarioDescribe = sgnc.getUsuarioDescribe();
            if (usuarioDescribe != null) {
                usuarioDescribe = em.getReference(usuarioDescribe.getClass(), usuarioDescribe.getCodUsuario());
                sgnc.setUsuarioDescribe(usuarioDescribe);
            }
            SgcorrecionNc sgcorrecionNc = sgnc.getSgcorrecionNc();
            if (sgcorrecionNc != null) {
                sgcorrecionNc = em.getReference(sgcorrecionNc.getClass(), sgcorrecionNc.getSgcorrecionNcPK());
                sgnc.setSgcorrecionNc(sgcorrecionNc);
            }
            List<Sgcentro> attachedSgcentroList = new ArrayList<Sgcentro>();
            for (Sgcentro sgcentroListSgcentroToAttach : sgnc.getSgcentroList()) {
                sgcentroListSgcentroToAttach = em.getReference(sgcentroListSgcentroToAttach.getClass(), sgcentroListSgcentroToAttach.getIdcentro());
                attachedSgcentroList.add(sgcentroListSgcentroToAttach);
            }
            sgnc.setSgcentroList(attachedSgcentroList);
            List<Sgdepartamento> attachedSgdepartamentoList = new ArrayList<Sgdepartamento>();
            for (Sgdepartamento sgdepartamentoListSgdepartamentoToAttach : sgnc.getSgdepartamentoList()) {
                sgdepartamentoListSgdepartamentoToAttach = em.getReference(sgdepartamentoListSgdepartamentoToAttach.getClass(), sgdepartamentoListSgdepartamentoToAttach.getIddept());
                attachedSgdepartamentoList.add(sgdepartamentoListSgdepartamentoToAttach);
            }
            sgnc.setSgdepartamentoList(attachedSgdepartamentoList);
            List<SgUsuario> attachedSgUsuarioList = new ArrayList<SgUsuario>();
            for (SgUsuario sgUsuarioListSgUsuarioToAttach : sgnc.getSgUsuarioList()) {
                sgUsuarioListSgUsuarioToAttach = em.getReference(sgUsuarioListSgUsuarioToAttach.getClass(), sgUsuarioListSgUsuarioToAttach.getCodUsuario());
                attachedSgUsuarioList.add(sgUsuarioListSgUsuarioToAttach);
            }
            sgnc.setSgUsuarioList(attachedSgUsuarioList);
            List<Sgcausa> attachedSgcausaList = new ArrayList<Sgcausa>();
            for (Sgcausa sgcausaListSgcausaToAttach : sgnc.getSgcausaList()) {
                sgcausaListSgcausaToAttach = em.getReference(sgcausaListSgcausaToAttach.getClass(), sgcausaListSgcausaToAttach.getSgcausaPK());
                attachedSgcausaList.add(sgcausaListSgcausaToAttach);
            }
            sgnc.setSgcausaList(attachedSgcausaList);
            List<Sgsigue> attachedSgsigueList = new ArrayList<Sgsigue>();
            for (Sgsigue sgsigueListSgsigueToAttach : sgnc.getSgsigueList()) {
                sgsigueListSgsigueToAttach = em.getReference(sgsigueListSgsigueToAttach.getClass(), sgsigueListSgsigueToAttach.getSgsiguePK());
                attachedSgsigueList.add(sgsigueListSgsigueToAttach);
            }
            sgnc.setSgsigueList(attachedSgsigueList);
            List<Sgverifica> attachedSgverificaList = new ArrayList<Sgverifica>();
            for (Sgverifica sgverificaListSgverificaToAttach : sgnc.getSgverificaList()) {
                sgverificaListSgverificaToAttach = em.getReference(sgverificaListSgverificaToAttach.getClass(), sgverificaListSgverificaToAttach.getSgverificaPK());
                attachedSgverificaList.add(sgverificaListSgverificaToAttach);
            }
            sgnc.setSgverificaList(attachedSgverificaList);
            em.persist(sgnc);
            if (sgprocesosRe != null) {
                Sgnc oldSgncOfSgprocesosRe = sgprocesosRe.getSgnc();
                if (oldSgncOfSgprocesosRe != null) {
                    oldSgncOfSgprocesosRe.setSgprocesosRe(null);
                    oldSgncOfSgprocesosRe = em.merge(oldSgncOfSgprocesosRe);
                }
                sgprocesosRe.setSgnc(sgnc);
                sgprocesosRe = em.merge(sgprocesosRe);
            }
            if (idcriticidad != null) {
                idcriticidad.getSgncList().add(sgnc);
                idcriticidad = em.merge(idcriticidad);
            }
            if (idestado != null) {
                idestado.getSgncList().add(sgnc);
                idestado = em.merge(idestado);
            }
            if (usuarioDescribe != null) {
                usuarioDescribe.getSgncList().add(sgnc);
                usuarioDescribe = em.merge(usuarioDescribe);
            }
            if (sgcorrecionNc != null) {
                Sgnc oldSgncOfSgcorrecionNc = sgcorrecionNc.getSgnc();
                if (oldSgncOfSgcorrecionNc != null) {
                    oldSgncOfSgcorrecionNc.setSgcorrecionNc(null);
                    oldSgncOfSgcorrecionNc = em.merge(oldSgncOfSgcorrecionNc);
                }
                sgcorrecionNc.setSgnc(sgnc);
                sgcorrecionNc = em.merge(sgcorrecionNc);
            }
            for (Sgcentro sgcentroListSgcentro : sgnc.getSgcentroList()) {
                sgcentroListSgcentro.getSgncList().add(sgnc);
                sgcentroListSgcentro = em.merge(sgcentroListSgcentro);
            }
            for (Sgdepartamento sgdepartamentoListSgdepartamento : sgnc.getSgdepartamentoList()) {
                sgdepartamentoListSgdepartamento.getSgncList().add(sgnc);
                sgdepartamentoListSgdepartamento = em.merge(sgdepartamentoListSgdepartamento);
            }
            for (SgUsuario sgUsuarioListSgUsuario : sgnc.getSgUsuarioList()) {
                sgUsuarioListSgUsuario.getSgncList().add(sgnc);
                sgUsuarioListSgUsuario = em.merge(sgUsuarioListSgUsuario);
            }
            for (Sgcausa sgcausaListSgcausa : sgnc.getSgcausaList()) {
                Sgnc oldSgncOfSgcausaListSgcausa = sgcausaListSgcausa.getSgnc();
                sgcausaListSgcausa.setSgnc(sgnc);
                sgcausaListSgcausa = em.merge(sgcausaListSgcausa);
                if (oldSgncOfSgcausaListSgcausa != null) {
                    oldSgncOfSgcausaListSgcausa.getSgcausaList().remove(sgcausaListSgcausa);
                    oldSgncOfSgcausaListSgcausa = em.merge(oldSgncOfSgcausaListSgcausa);
                }
            }
            for (Sgsigue sgsigueListSgsigue : sgnc.getSgsigueList()) {
                Sgnc oldSgncOfSgsigueListSgsigue = sgsigueListSgsigue.getSgnc();
                sgsigueListSgsigue.setSgnc(sgnc);
                sgsigueListSgsigue = em.merge(sgsigueListSgsigue);
                if (oldSgncOfSgsigueListSgsigue != null) {
                    oldSgncOfSgsigueListSgsigue.getSgsigueList().remove(sgsigueListSgsigue);
                    oldSgncOfSgsigueListSgsigue = em.merge(oldSgncOfSgsigueListSgsigue);
                }
            }
            for (Sgverifica sgverificaListSgverifica : sgnc.getSgverificaList()) {
                Sgnc oldSgncOfSgverificaListSgverifica = sgverificaListSgverifica.getSgnc();
                sgverificaListSgverifica.setSgnc(sgnc);
                sgverificaListSgverifica = em.merge(sgverificaListSgverifica);
                if (oldSgncOfSgverificaListSgverifica != null) {
                    oldSgncOfSgverificaListSgverifica.getSgverificaList().remove(sgverificaListSgverifica);
                    oldSgncOfSgverificaListSgverifica = em.merge(oldSgncOfSgverificaListSgverifica);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgnc(sgnc.getSgncPK()) != null) {
                throw new PreexistingEntityException("Sgnc " + sgnc + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sgnc sgnc) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgnc persistentSgnc = em.find(Sgnc.class, sgnc.getSgncPK());
            SgprocesosRe sgprocesosReOld = persistentSgnc.getSgprocesosRe();
            SgprocesosRe sgprocesosReNew = sgnc.getSgprocesosRe();
            Sgcriticidad idcriticidadOld = persistentSgnc.getIdcriticidad();
            Sgcriticidad idcriticidadNew = sgnc.getIdcriticidad();
            Sgestado idestadoOld = persistentSgnc.getIdestado();
            Sgestado idestadoNew = sgnc.getIdestado();
            SgUsuario usuarioDescribeOld = persistentSgnc.getUsuarioDescribe();
            SgUsuario usuarioDescribeNew = sgnc.getUsuarioDescribe();
            SgcorrecionNc sgcorrecionNcOld = persistentSgnc.getSgcorrecionNc();
            SgcorrecionNc sgcorrecionNcNew = sgnc.getSgcorrecionNc();
            List<Sgcentro> sgcentroListOld = persistentSgnc.getSgcentroList();
            List<Sgcentro> sgcentroListNew = sgnc.getSgcentroList();
            List<Sgdepartamento> sgdepartamentoListOld = persistentSgnc.getSgdepartamentoList();
            List<Sgdepartamento> sgdepartamentoListNew = sgnc.getSgdepartamentoList();
            List<SgUsuario> sgUsuarioListOld = persistentSgnc.getSgUsuarioList();
            List<SgUsuario> sgUsuarioListNew = sgnc.getSgUsuarioList();
            List<Sgcausa> sgcausaListOld = persistentSgnc.getSgcausaList();
            List<Sgcausa> sgcausaListNew = sgnc.getSgcausaList();
            List<Sgsigue> sgsigueListOld = persistentSgnc.getSgsigueList();
            List<Sgsigue> sgsigueListNew = sgnc.getSgsigueList();
            List<Sgverifica> sgverificaListOld = persistentSgnc.getSgverificaList();
            List<Sgverifica> sgverificaListNew = sgnc.getSgverificaList();
            List<String> illegalOrphanMessages = null;
            if (sgprocesosReOld != null && !sgprocesosReOld.equals(sgprocesosReNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain SgprocesosRe " + sgprocesosReOld + " since its sgnc field is not nullable.");
            }
            if (sgcorrecionNcOld != null && !sgcorrecionNcOld.equals(sgcorrecionNcNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain SgcorrecionNc " + sgcorrecionNcOld + " since its sgnc field is not nullable.");
            }
            for (Sgcausa sgcausaListOldSgcausa : sgcausaListOld) {
                if (!sgcausaListNew.contains(sgcausaListOldSgcausa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sgcausa " + sgcausaListOldSgcausa + " since its sgnc field is not nullable.");
                }
            }
            for (Sgsigue sgsigueListOldSgsigue : sgsigueListOld) {
                if (!sgsigueListNew.contains(sgsigueListOldSgsigue)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sgsigue " + sgsigueListOldSgsigue + " since its sgnc field is not nullable.");
                }
            }
            for (Sgverifica sgverificaListOldSgverifica : sgverificaListOld) {
                if (!sgverificaListNew.contains(sgverificaListOldSgverifica)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sgverifica " + sgverificaListOldSgverifica + " since its sgnc field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sgprocesosReNew != null) {
                sgprocesosReNew = em.getReference(sgprocesosReNew.getClass(), sgprocesosReNew.getSgprocesosRePK());
                sgnc.setSgprocesosRe(sgprocesosReNew);
            }
            if (idcriticidadNew != null) {
                idcriticidadNew = em.getReference(idcriticidadNew.getClass(), idcriticidadNew.getIdcriticidad());
                sgnc.setIdcriticidad(idcriticidadNew);
            }
            if (idestadoNew != null) {
                idestadoNew = em.getReference(idestadoNew.getClass(), idestadoNew.getIdestado());
                sgnc.setIdestado(idestadoNew);
            }
            if (usuarioDescribeNew != null) {
                usuarioDescribeNew = em.getReference(usuarioDescribeNew.getClass(), usuarioDescribeNew.getCodUsuario());
                sgnc.setUsuarioDescribe(usuarioDescribeNew);
            }
            if (sgcorrecionNcNew != null) {
                sgcorrecionNcNew = em.getReference(sgcorrecionNcNew.getClass(), sgcorrecionNcNew.getSgcorrecionNcPK());
                sgnc.setSgcorrecionNc(sgcorrecionNcNew);
            }
            List<Sgcentro> attachedSgcentroListNew = new ArrayList<Sgcentro>();
            for (Sgcentro sgcentroListNewSgcentroToAttach : sgcentroListNew) {
                sgcentroListNewSgcentroToAttach = em.getReference(sgcentroListNewSgcentroToAttach.getClass(), sgcentroListNewSgcentroToAttach.getIdcentro());
                attachedSgcentroListNew.add(sgcentroListNewSgcentroToAttach);
            }
            sgcentroListNew = attachedSgcentroListNew;
            sgnc.setSgcentroList(sgcentroListNew);
            List<Sgdepartamento> attachedSgdepartamentoListNew = new ArrayList<Sgdepartamento>();
            for (Sgdepartamento sgdepartamentoListNewSgdepartamentoToAttach : sgdepartamentoListNew) {
                sgdepartamentoListNewSgdepartamentoToAttach = em.getReference(sgdepartamentoListNewSgdepartamentoToAttach.getClass(), sgdepartamentoListNewSgdepartamentoToAttach.getIddept());
                attachedSgdepartamentoListNew.add(sgdepartamentoListNewSgdepartamentoToAttach);
            }
            sgdepartamentoListNew = attachedSgdepartamentoListNew;
            sgnc.setSgdepartamentoList(sgdepartamentoListNew);
            List<SgUsuario> attachedSgUsuarioListNew = new ArrayList<SgUsuario>();
            for (SgUsuario sgUsuarioListNewSgUsuarioToAttach : sgUsuarioListNew) {
                sgUsuarioListNewSgUsuarioToAttach = em.getReference(sgUsuarioListNewSgUsuarioToAttach.getClass(), sgUsuarioListNewSgUsuarioToAttach.getCodUsuario());
                attachedSgUsuarioListNew.add(sgUsuarioListNewSgUsuarioToAttach);
            }
            sgUsuarioListNew = attachedSgUsuarioListNew;
            sgnc.setSgUsuarioList(sgUsuarioListNew);
            List<Sgcausa> attachedSgcausaListNew = new ArrayList<Sgcausa>();
            for (Sgcausa sgcausaListNewSgcausaToAttach : sgcausaListNew) {
                sgcausaListNewSgcausaToAttach = em.getReference(sgcausaListNewSgcausaToAttach.getClass(), sgcausaListNewSgcausaToAttach.getSgcausaPK());
                attachedSgcausaListNew.add(sgcausaListNewSgcausaToAttach);
            }
            sgcausaListNew = attachedSgcausaListNew;
            sgnc.setSgcausaList(sgcausaListNew);
            List<Sgsigue> attachedSgsigueListNew = new ArrayList<Sgsigue>();
            for (Sgsigue sgsigueListNewSgsigueToAttach : sgsigueListNew) {
                sgsigueListNewSgsigueToAttach = em.getReference(sgsigueListNewSgsigueToAttach.getClass(), sgsigueListNewSgsigueToAttach.getSgsiguePK());
                attachedSgsigueListNew.add(sgsigueListNewSgsigueToAttach);
            }
            sgsigueListNew = attachedSgsigueListNew;
            sgnc.setSgsigueList(sgsigueListNew);
            List<Sgverifica> attachedSgverificaListNew = new ArrayList<Sgverifica>();
            for (Sgverifica sgverificaListNewSgverificaToAttach : sgverificaListNew) {
                sgverificaListNewSgverificaToAttach = em.getReference(sgverificaListNewSgverificaToAttach.getClass(), sgverificaListNewSgverificaToAttach.getSgverificaPK());
                attachedSgverificaListNew.add(sgverificaListNewSgverificaToAttach);
            }
            sgverificaListNew = attachedSgverificaListNew;
            sgnc.setSgverificaList(sgverificaListNew);
            sgnc = em.merge(sgnc);
            if (sgprocesosReNew != null && !sgprocesosReNew.equals(sgprocesosReOld)) {
                Sgnc oldSgncOfSgprocesosRe = sgprocesosReNew.getSgnc();
                if (oldSgncOfSgprocesosRe != null) {
                    oldSgncOfSgprocesosRe.setSgprocesosRe(null);
                    oldSgncOfSgprocesosRe = em.merge(oldSgncOfSgprocesosRe);
                }
                sgprocesosReNew.setSgnc(sgnc);
                sgprocesosReNew = em.merge(sgprocesosReNew);
            }
            if (idcriticidadOld != null && !idcriticidadOld.equals(idcriticidadNew)) {
                idcriticidadOld.getSgncList().remove(sgnc);
                idcriticidadOld = em.merge(idcriticidadOld);
            }
            if (idcriticidadNew != null && !idcriticidadNew.equals(idcriticidadOld)) {
                idcriticidadNew.getSgncList().add(sgnc);
                idcriticidadNew = em.merge(idcriticidadNew);
            }
            if (idestadoOld != null && !idestadoOld.equals(idestadoNew)) {
                idestadoOld.getSgncList().remove(sgnc);
                idestadoOld = em.merge(idestadoOld);
            }
            if (idestadoNew != null && !idestadoNew.equals(idestadoOld)) {
                idestadoNew.getSgncList().add(sgnc);
                idestadoNew = em.merge(idestadoNew);
            }
            if (usuarioDescribeOld != null && !usuarioDescribeOld.equals(usuarioDescribeNew)) {
                usuarioDescribeOld.getSgncList().remove(sgnc);
                usuarioDescribeOld = em.merge(usuarioDescribeOld);
            }
            if (usuarioDescribeNew != null && !usuarioDescribeNew.equals(usuarioDescribeOld)) {
                usuarioDescribeNew.getSgncList().add(sgnc);
                usuarioDescribeNew = em.merge(usuarioDescribeNew);
            }
            if (sgcorrecionNcNew != null && !sgcorrecionNcNew.equals(sgcorrecionNcOld)) {
                Sgnc oldSgncOfSgcorrecionNc = sgcorrecionNcNew.getSgnc();
                if (oldSgncOfSgcorrecionNc != null) {
                    oldSgncOfSgcorrecionNc.setSgcorrecionNc(null);
                    oldSgncOfSgcorrecionNc = em.merge(oldSgncOfSgcorrecionNc);
                }
                sgcorrecionNcNew.setSgnc(sgnc);
                sgcorrecionNcNew = em.merge(sgcorrecionNcNew);
            }
            for (Sgcentro sgcentroListOldSgcentro : sgcentroListOld) {
                if (!sgcentroListNew.contains(sgcentroListOldSgcentro)) {
                    sgcentroListOldSgcentro.getSgncList().remove(sgnc);
                    sgcentroListOldSgcentro = em.merge(sgcentroListOldSgcentro);
                }
            }
            for (Sgcentro sgcentroListNewSgcentro : sgcentroListNew) {
                if (!sgcentroListOld.contains(sgcentroListNewSgcentro)) {
                    sgcentroListNewSgcentro.getSgncList().add(sgnc);
                    sgcentroListNewSgcentro = em.merge(sgcentroListNewSgcentro);
                }
            }
            for (Sgdepartamento sgdepartamentoListOldSgdepartamento : sgdepartamentoListOld) {
                if (!sgdepartamentoListNew.contains(sgdepartamentoListOldSgdepartamento)) {
                    sgdepartamentoListOldSgdepartamento.getSgncList().remove(sgnc);
                    sgdepartamentoListOldSgdepartamento = em.merge(sgdepartamentoListOldSgdepartamento);
                }
            }
            for (Sgdepartamento sgdepartamentoListNewSgdepartamento : sgdepartamentoListNew) {
                if (!sgdepartamentoListOld.contains(sgdepartamentoListNewSgdepartamento)) {
                    sgdepartamentoListNewSgdepartamento.getSgncList().add(sgnc);
                    sgdepartamentoListNewSgdepartamento = em.merge(sgdepartamentoListNewSgdepartamento);
                }
            }
            for (SgUsuario sgUsuarioListOldSgUsuario : sgUsuarioListOld) {
                if (!sgUsuarioListNew.contains(sgUsuarioListOldSgUsuario)) {
                    sgUsuarioListOldSgUsuario.getSgncList().remove(sgnc);
                    sgUsuarioListOldSgUsuario = em.merge(sgUsuarioListOldSgUsuario);
                }
            }
            for (SgUsuario sgUsuarioListNewSgUsuario : sgUsuarioListNew) {
                if (!sgUsuarioListOld.contains(sgUsuarioListNewSgUsuario)) {
                    sgUsuarioListNewSgUsuario.getSgncList().add(sgnc);
                    sgUsuarioListNewSgUsuario = em.merge(sgUsuarioListNewSgUsuario);
                }
            }
            for (Sgcausa sgcausaListNewSgcausa : sgcausaListNew) {
                if (!sgcausaListOld.contains(sgcausaListNewSgcausa)) {
                    Sgnc oldSgncOfSgcausaListNewSgcausa = sgcausaListNewSgcausa.getSgnc();
                    sgcausaListNewSgcausa.setSgnc(sgnc);
                    sgcausaListNewSgcausa = em.merge(sgcausaListNewSgcausa);
                    if (oldSgncOfSgcausaListNewSgcausa != null && !oldSgncOfSgcausaListNewSgcausa.equals(sgnc)) {
                        oldSgncOfSgcausaListNewSgcausa.getSgcausaList().remove(sgcausaListNewSgcausa);
                        oldSgncOfSgcausaListNewSgcausa = em.merge(oldSgncOfSgcausaListNewSgcausa);
                    }
                }
            }
            for (Sgsigue sgsigueListNewSgsigue : sgsigueListNew) {
                if (!sgsigueListOld.contains(sgsigueListNewSgsigue)) {
                    Sgnc oldSgncOfSgsigueListNewSgsigue = sgsigueListNewSgsigue.getSgnc();
                    sgsigueListNewSgsigue.setSgnc(sgnc);
                    sgsigueListNewSgsigue = em.merge(sgsigueListNewSgsigue);
                    if (oldSgncOfSgsigueListNewSgsigue != null && !oldSgncOfSgsigueListNewSgsigue.equals(sgnc)) {
                        oldSgncOfSgsigueListNewSgsigue.getSgsigueList().remove(sgsigueListNewSgsigue);
                        oldSgncOfSgsigueListNewSgsigue = em.merge(oldSgncOfSgsigueListNewSgsigue);
                    }
                }
            }
            for (Sgverifica sgverificaListNewSgverifica : sgverificaListNew) {
                if (!sgverificaListOld.contains(sgverificaListNewSgverifica)) {
                    Sgnc oldSgncOfSgverificaListNewSgverifica = sgverificaListNewSgverifica.getSgnc();
                    sgverificaListNewSgverifica.setSgnc(sgnc);
                    sgverificaListNewSgverifica = em.merge(sgverificaListNewSgverifica);
                    if (oldSgncOfSgverificaListNewSgverifica != null && !oldSgncOfSgverificaListNewSgverifica.equals(sgnc)) {
                        oldSgncOfSgverificaListNewSgverifica.getSgverificaList().remove(sgverificaListNewSgverifica);
                        oldSgncOfSgverificaListNewSgverifica = em.merge(oldSgncOfSgverificaListNewSgverifica);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgncPK id = sgnc.getSgncPK();
                if (findSgnc(id) == null) {
                    throw new NonexistentEntityException("The sgnc with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgncPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgnc sgnc;
            try {
                sgnc = em.getReference(Sgnc.class, id);
                sgnc.getSgncPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgnc with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            SgprocesosRe sgprocesosReOrphanCheck = sgnc.getSgprocesosRe();
            if (sgprocesosReOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sgnc (" + sgnc + ") cannot be destroyed since the SgprocesosRe " + sgprocesosReOrphanCheck + " in its sgprocesosRe field has a non-nullable sgnc field.");
            }
            SgcorrecionNc sgcorrecionNcOrphanCheck = sgnc.getSgcorrecionNc();
            if (sgcorrecionNcOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sgnc (" + sgnc + ") cannot be destroyed since the SgcorrecionNc " + sgcorrecionNcOrphanCheck + " in its sgcorrecionNc field has a non-nullable sgnc field.");
            }
            List<Sgcausa> sgcausaListOrphanCheck = sgnc.getSgcausaList();
            for (Sgcausa sgcausaListOrphanCheckSgcausa : sgcausaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sgnc (" + sgnc + ") cannot be destroyed since the Sgcausa " + sgcausaListOrphanCheckSgcausa + " in its sgcausaList field has a non-nullable sgnc field.");
            }
            List<Sgsigue> sgsigueListOrphanCheck = sgnc.getSgsigueList();
            for (Sgsigue sgsigueListOrphanCheckSgsigue : sgsigueListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sgnc (" + sgnc + ") cannot be destroyed since the Sgsigue " + sgsigueListOrphanCheckSgsigue + " in its sgsigueList field has a non-nullable sgnc field.");
            }
            List<Sgverifica> sgverificaListOrphanCheck = sgnc.getSgverificaList();
            for (Sgverifica sgverificaListOrphanCheckSgverifica : sgverificaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sgnc (" + sgnc + ") cannot be destroyed since the Sgverifica " + sgverificaListOrphanCheckSgverifica + " in its sgverificaList field has a non-nullable sgnc field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Sgcriticidad idcriticidad = sgnc.getIdcriticidad();
            if (idcriticidad != null) {
                idcriticidad.getSgncList().remove(sgnc);
                idcriticidad = em.merge(idcriticidad);
            }
            Sgestado idestado = sgnc.getIdestado();
            if (idestado != null) {
                idestado.getSgncList().remove(sgnc);
                idestado = em.merge(idestado);
            }
            SgUsuario usuarioDescribe = sgnc.getUsuarioDescribe();
            if (usuarioDescribe != null) {
                usuarioDescribe.getSgncList().remove(sgnc);
                usuarioDescribe = em.merge(usuarioDescribe);
            }
            List<Sgcentro> sgcentroList = sgnc.getSgcentroList();
            for (Sgcentro sgcentroListSgcentro : sgcentroList) {
                sgcentroListSgcentro.getSgncList().remove(sgnc);
                sgcentroListSgcentro = em.merge(sgcentroListSgcentro);
            }
            List<Sgdepartamento> sgdepartamentoList = sgnc.getSgdepartamentoList();
            for (Sgdepartamento sgdepartamentoListSgdepartamento : sgdepartamentoList) {
                sgdepartamentoListSgdepartamento.getSgncList().remove(sgnc);
                sgdepartamentoListSgdepartamento = em.merge(sgdepartamentoListSgdepartamento);
            }
            List<SgUsuario> sgUsuarioList = sgnc.getSgUsuarioList();
            for (SgUsuario sgUsuarioListSgUsuario : sgUsuarioList) {
                sgUsuarioListSgUsuario.getSgncList().remove(sgnc);
                sgUsuarioListSgUsuario = em.merge(sgUsuarioListSgUsuario);
            }
            em.remove(sgnc);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sgnc> findSgncEntities() {
        return findSgncEntities(true, -1, -1);
    }

    public List<Sgnc> findSgncEntities(int maxResults, int firstResult) {
        return findSgncEntities(false, maxResults, firstResult);
    }

    private List<Sgnc> findSgncEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sgnc.class));
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

    public Sgnc findSgnc(SgncPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sgnc.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgncCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sgnc> rt = cq.from(Sgnc.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
