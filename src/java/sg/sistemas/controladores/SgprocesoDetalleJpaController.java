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
import sg.sistemas.entidades.Sgprocesos;
import sg.sistemas.entidades.Sgworkflow;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgproccAuditoria;
import sg.sistemas.entidades.SgprocesoDetalle;
import sg.sistemas.entidades.SgprocesoDetallePK;

/**
 *
 * @author Misael
 */
public class SgprocesoDetalleJpaController implements Serializable {

    public SgprocesoDetalleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgprocesoDetalle sgprocesoDetalle) throws PreexistingEntityException, Exception {
        if (sgprocesoDetalle.getSgprocesoDetallePK() == null) {
            sgprocesoDetalle.setSgprocesoDetallePK(new SgprocesoDetallePK());
        }
        if (sgprocesoDetalle.getSgworkflowList() == null) {
            sgprocesoDetalle.setSgworkflowList(new ArrayList<Sgworkflow>());
        }
        if (sgprocesoDetalle.getSgproccAuditoriaList() == null) {
            sgprocesoDetalle.setSgproccAuditoriaList(new ArrayList<SgproccAuditoria>());
        }
        sgprocesoDetalle.getSgprocesoDetallePK().setIdprocesos(sgprocesoDetalle.getSgprocesos().getIdprocesos());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgprocesos sgprocesos = sgprocesoDetalle.getSgprocesos();
            if (sgprocesos != null) {
                sgprocesos = em.getReference(sgprocesos.getClass(), sgprocesos.getIdprocesos());
                sgprocesoDetalle.setSgprocesos(sgprocesos);
            }
            List<Sgworkflow> attachedSgworkflowList = new ArrayList<Sgworkflow>();
            for (Sgworkflow sgworkflowListSgworkflowToAttach : sgprocesoDetalle.getSgworkflowList()) {
                sgworkflowListSgworkflowToAttach = em.getReference(sgworkflowListSgworkflowToAttach.getClass(), sgworkflowListSgworkflowToAttach.getSgworkflowPK());
                attachedSgworkflowList.add(sgworkflowListSgworkflowToAttach);
            }
            sgprocesoDetalle.setSgworkflowList(attachedSgworkflowList);
            List<SgproccAuditoria> attachedSgproccAuditoriaList = new ArrayList<SgproccAuditoria>();
            for (SgproccAuditoria sgproccAuditoriaListSgproccAuditoriaToAttach : sgprocesoDetalle.getSgproccAuditoriaList()) {
                sgproccAuditoriaListSgproccAuditoriaToAttach = em.getReference(sgproccAuditoriaListSgproccAuditoriaToAttach.getClass(), sgproccAuditoriaListSgproccAuditoriaToAttach.getSgproccAuditoriaPK());
                attachedSgproccAuditoriaList.add(sgproccAuditoriaListSgproccAuditoriaToAttach);
            }
            sgprocesoDetalle.setSgproccAuditoriaList(attachedSgproccAuditoriaList);
            em.persist(sgprocesoDetalle);
            if (sgprocesos != null) {
                sgprocesos.getSgprocesoDetalleList().add(sgprocesoDetalle);
                sgprocesos = em.merge(sgprocesos);
            }
            for (Sgworkflow sgworkflowListSgworkflow : sgprocesoDetalle.getSgworkflowList()) {
                SgprocesoDetalle oldSgprocesoDetalleOfSgworkflowListSgworkflow = sgworkflowListSgworkflow.getSgprocesoDetalle();
                sgworkflowListSgworkflow.setSgprocesoDetalle(sgprocesoDetalle);
                sgworkflowListSgworkflow = em.merge(sgworkflowListSgworkflow);
                if (oldSgprocesoDetalleOfSgworkflowListSgworkflow != null) {
                    oldSgprocesoDetalleOfSgworkflowListSgworkflow.getSgworkflowList().remove(sgworkflowListSgworkflow);
                    oldSgprocesoDetalleOfSgworkflowListSgworkflow = em.merge(oldSgprocesoDetalleOfSgworkflowListSgworkflow);
                }
            }
            for (SgproccAuditoria sgproccAuditoriaListSgproccAuditoria : sgprocesoDetalle.getSgproccAuditoriaList()) {
                SgprocesoDetalle oldSgprocesoDetalleOfSgproccAuditoriaListSgproccAuditoria = sgproccAuditoriaListSgproccAuditoria.getSgprocesoDetalle();
                sgproccAuditoriaListSgproccAuditoria.setSgprocesoDetalle(sgprocesoDetalle);
                sgproccAuditoriaListSgproccAuditoria = em.merge(sgproccAuditoriaListSgproccAuditoria);
                if (oldSgprocesoDetalleOfSgproccAuditoriaListSgproccAuditoria != null) {
                    oldSgprocesoDetalleOfSgproccAuditoriaListSgproccAuditoria.getSgproccAuditoriaList().remove(sgproccAuditoriaListSgproccAuditoria);
                    oldSgprocesoDetalleOfSgproccAuditoriaListSgproccAuditoria = em.merge(oldSgprocesoDetalleOfSgproccAuditoriaListSgproccAuditoria);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgprocesoDetalle(sgprocesoDetalle.getSgprocesoDetallePK()) != null) {
                throw new PreexistingEntityException("SgprocesoDetalle " + sgprocesoDetalle + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgprocesoDetalle sgprocesoDetalle) throws IllegalOrphanException, NonexistentEntityException, Exception {
        sgprocesoDetalle.getSgprocesoDetallePK().setIdprocesos(sgprocesoDetalle.getSgprocesos().getIdprocesos());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgprocesoDetalle persistentSgprocesoDetalle = em.find(SgprocesoDetalle.class, sgprocesoDetalle.getSgprocesoDetallePK());
            Sgprocesos sgprocesosOld = persistentSgprocesoDetalle.getSgprocesos();
            Sgprocesos sgprocesosNew = sgprocesoDetalle.getSgprocesos();
            List<Sgworkflow> sgworkflowListOld = persistentSgprocesoDetalle.getSgworkflowList();
            List<Sgworkflow> sgworkflowListNew = sgprocesoDetalle.getSgworkflowList();
            List<SgproccAuditoria> sgproccAuditoriaListOld = persistentSgprocesoDetalle.getSgproccAuditoriaList();
            List<SgproccAuditoria> sgproccAuditoriaListNew = sgprocesoDetalle.getSgproccAuditoriaList();
            List<String> illegalOrphanMessages = null;
            for (Sgworkflow sgworkflowListOldSgworkflow : sgworkflowListOld) {
                if (!sgworkflowListNew.contains(sgworkflowListOldSgworkflow)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sgworkflow " + sgworkflowListOldSgworkflow + " since its sgprocesoDetalle field is not nullable.");
                }
            }
            for (SgproccAuditoria sgproccAuditoriaListOldSgproccAuditoria : sgproccAuditoriaListOld) {
                if (!sgproccAuditoriaListNew.contains(sgproccAuditoriaListOldSgproccAuditoria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SgproccAuditoria " + sgproccAuditoriaListOldSgproccAuditoria + " since its sgprocesoDetalle field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sgprocesosNew != null) {
                sgprocesosNew = em.getReference(sgprocesosNew.getClass(), sgprocesosNew.getIdprocesos());
                sgprocesoDetalle.setSgprocesos(sgprocesosNew);
            }
            List<Sgworkflow> attachedSgworkflowListNew = new ArrayList<Sgworkflow>();
            for (Sgworkflow sgworkflowListNewSgworkflowToAttach : sgworkflowListNew) {
                sgworkflowListNewSgworkflowToAttach = em.getReference(sgworkflowListNewSgworkflowToAttach.getClass(), sgworkflowListNewSgworkflowToAttach.getSgworkflowPK());
                attachedSgworkflowListNew.add(sgworkflowListNewSgworkflowToAttach);
            }
            sgworkflowListNew = attachedSgworkflowListNew;
            sgprocesoDetalle.setSgworkflowList(sgworkflowListNew);
            List<SgproccAuditoria> attachedSgproccAuditoriaListNew = new ArrayList<SgproccAuditoria>();
            for (SgproccAuditoria sgproccAuditoriaListNewSgproccAuditoriaToAttach : sgproccAuditoriaListNew) {
                sgproccAuditoriaListNewSgproccAuditoriaToAttach = em.getReference(sgproccAuditoriaListNewSgproccAuditoriaToAttach.getClass(), sgproccAuditoriaListNewSgproccAuditoriaToAttach.getSgproccAuditoriaPK());
                attachedSgproccAuditoriaListNew.add(sgproccAuditoriaListNewSgproccAuditoriaToAttach);
            }
            sgproccAuditoriaListNew = attachedSgproccAuditoriaListNew;
            sgprocesoDetalle.setSgproccAuditoriaList(sgproccAuditoriaListNew);
            sgprocesoDetalle = em.merge(sgprocesoDetalle);
            if (sgprocesosOld != null && !sgprocesosOld.equals(sgprocesosNew)) {
                sgprocesosOld.getSgprocesoDetalleList().remove(sgprocesoDetalle);
                sgprocesosOld = em.merge(sgprocesosOld);
            }
            if (sgprocesosNew != null && !sgprocesosNew.equals(sgprocesosOld)) {
                sgprocesosNew.getSgprocesoDetalleList().add(sgprocesoDetalle);
                sgprocesosNew = em.merge(sgprocesosNew);
            }
            for (Sgworkflow sgworkflowListNewSgworkflow : sgworkflowListNew) {
                if (!sgworkflowListOld.contains(sgworkflowListNewSgworkflow)) {
                    SgprocesoDetalle oldSgprocesoDetalleOfSgworkflowListNewSgworkflow = sgworkflowListNewSgworkflow.getSgprocesoDetalle();
                    sgworkflowListNewSgworkflow.setSgprocesoDetalle(sgprocesoDetalle);
                    sgworkflowListNewSgworkflow = em.merge(sgworkflowListNewSgworkflow);
                    if (oldSgprocesoDetalleOfSgworkflowListNewSgworkflow != null && !oldSgprocesoDetalleOfSgworkflowListNewSgworkflow.equals(sgprocesoDetalle)) {
                        oldSgprocesoDetalleOfSgworkflowListNewSgworkflow.getSgworkflowList().remove(sgworkflowListNewSgworkflow);
                        oldSgprocesoDetalleOfSgworkflowListNewSgworkflow = em.merge(oldSgprocesoDetalleOfSgworkflowListNewSgworkflow);
                    }
                }
            }
            for (SgproccAuditoria sgproccAuditoriaListNewSgproccAuditoria : sgproccAuditoriaListNew) {
                if (!sgproccAuditoriaListOld.contains(sgproccAuditoriaListNewSgproccAuditoria)) {
                    SgprocesoDetalle oldSgprocesoDetalleOfSgproccAuditoriaListNewSgproccAuditoria = sgproccAuditoriaListNewSgproccAuditoria.getSgprocesoDetalle();
                    sgproccAuditoriaListNewSgproccAuditoria.setSgprocesoDetalle(sgprocesoDetalle);
                    sgproccAuditoriaListNewSgproccAuditoria = em.merge(sgproccAuditoriaListNewSgproccAuditoria);
                    if (oldSgprocesoDetalleOfSgproccAuditoriaListNewSgproccAuditoria != null && !oldSgprocesoDetalleOfSgproccAuditoriaListNewSgproccAuditoria.equals(sgprocesoDetalle)) {
                        oldSgprocesoDetalleOfSgproccAuditoriaListNewSgproccAuditoria.getSgproccAuditoriaList().remove(sgproccAuditoriaListNewSgproccAuditoria);
                        oldSgprocesoDetalleOfSgproccAuditoriaListNewSgproccAuditoria = em.merge(oldSgprocesoDetalleOfSgproccAuditoriaListNewSgproccAuditoria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgprocesoDetallePK id = sgprocesoDetalle.getSgprocesoDetallePK();
                if (findSgprocesoDetalle(id) == null) {
                    throw new NonexistentEntityException("The sgprocesoDetalle with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgprocesoDetallePK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgprocesoDetalle sgprocesoDetalle;
            try {
                sgprocesoDetalle = em.getReference(SgprocesoDetalle.class, id);
                sgprocesoDetalle.getSgprocesoDetallePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgprocesoDetalle with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Sgworkflow> sgworkflowListOrphanCheck = sgprocesoDetalle.getSgworkflowList();
            for (Sgworkflow sgworkflowListOrphanCheckSgworkflow : sgworkflowListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SgprocesoDetalle (" + sgprocesoDetalle + ") cannot be destroyed since the Sgworkflow " + sgworkflowListOrphanCheckSgworkflow + " in its sgworkflowList field has a non-nullable sgprocesoDetalle field.");
            }
            List<SgproccAuditoria> sgproccAuditoriaListOrphanCheck = sgprocesoDetalle.getSgproccAuditoriaList();
            for (SgproccAuditoria sgproccAuditoriaListOrphanCheckSgproccAuditoria : sgproccAuditoriaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SgprocesoDetalle (" + sgprocesoDetalle + ") cannot be destroyed since the SgproccAuditoria " + sgproccAuditoriaListOrphanCheckSgproccAuditoria + " in its sgproccAuditoriaList field has a non-nullable sgprocesoDetalle field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Sgprocesos sgprocesos = sgprocesoDetalle.getSgprocesos();
            if (sgprocesos != null) {
                sgprocesos.getSgprocesoDetalleList().remove(sgprocesoDetalle);
                sgprocesos = em.merge(sgprocesos);
            }
            em.remove(sgprocesoDetalle);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgprocesoDetalle> findSgprocesoDetalleEntities() {
        return findSgprocesoDetalleEntities(true, -1, -1);
    }

    public List<SgprocesoDetalle> findSgprocesoDetalleEntities(int maxResults, int firstResult) {
        return findSgprocesoDetalleEntities(false, maxResults, firstResult);
    }

    private List<SgprocesoDetalle> findSgprocesoDetalleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgprocesoDetalle.class));
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

    public SgprocesoDetalle findSgprocesoDetalle(SgprocesoDetallePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgprocesoDetalle.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgprocesoDetalleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgprocesoDetalle> rt = cq.from(SgprocesoDetalle.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
