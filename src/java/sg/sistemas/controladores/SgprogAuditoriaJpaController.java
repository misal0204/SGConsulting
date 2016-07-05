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
import sg.sistemas.entidades.SgdetalleAuditoria;
import sg.sistemas.entidades.SgproccAuditoria;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgprogAuditoria;
import sg.sistemas.entidades.SgprogAuditoriaPK;

/**
 *
 * @author Misael
 */
public class SgprogAuditoriaJpaController implements Serializable {

    public SgprogAuditoriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgprogAuditoria sgprogAuditoria) throws PreexistingEntityException, Exception {
        if (sgprogAuditoria.getSgprogAuditoriaPK() == null) {
            sgprogAuditoria.setSgprogAuditoriaPK(new SgprogAuditoriaPK());
        }
        if (sgprogAuditoria.getSgproccAuditoriaList() == null) {
            sgprogAuditoria.setSgproccAuditoriaList(new ArrayList<SgproccAuditoria>());
        }
        sgprogAuditoria.getSgprogAuditoriaPK().setIdsociedad(sgprogAuditoria.getSgdetalleAuditoria().getSgdetalleAuditoriaPK().getIdsociedad());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgdetalleAuditoria sgdetalleAuditoria = sgprogAuditoria.getSgdetalleAuditoria();
            if (sgdetalleAuditoria != null) {
                sgdetalleAuditoria = em.getReference(sgdetalleAuditoria.getClass(), sgdetalleAuditoria.getSgdetalleAuditoriaPK());
                sgprogAuditoria.setSgdetalleAuditoria(sgdetalleAuditoria);
            }
            List<SgproccAuditoria> attachedSgproccAuditoriaList = new ArrayList<SgproccAuditoria>();
            for (SgproccAuditoria sgproccAuditoriaListSgproccAuditoriaToAttach : sgprogAuditoria.getSgproccAuditoriaList()) {
                sgproccAuditoriaListSgproccAuditoriaToAttach = em.getReference(sgproccAuditoriaListSgproccAuditoriaToAttach.getClass(), sgproccAuditoriaListSgproccAuditoriaToAttach.getSgproccAuditoriaPK());
                attachedSgproccAuditoriaList.add(sgproccAuditoriaListSgproccAuditoriaToAttach);
            }
            sgprogAuditoria.setSgproccAuditoriaList(attachedSgproccAuditoriaList);
            em.persist(sgprogAuditoria);
            if (sgdetalleAuditoria != null) {
                sgdetalleAuditoria.getSgprogAuditoriaList().add(sgprogAuditoria);
                sgdetalleAuditoria = em.merge(sgdetalleAuditoria);
            }
            for (SgproccAuditoria sgproccAuditoriaListSgproccAuditoria : sgprogAuditoria.getSgproccAuditoriaList()) {
                SgprogAuditoria oldSgprogAuditoriaOfSgproccAuditoriaListSgproccAuditoria = sgproccAuditoriaListSgproccAuditoria.getSgprogAuditoria();
                sgproccAuditoriaListSgproccAuditoria.setSgprogAuditoria(sgprogAuditoria);
                sgproccAuditoriaListSgproccAuditoria = em.merge(sgproccAuditoriaListSgproccAuditoria);
                if (oldSgprogAuditoriaOfSgproccAuditoriaListSgproccAuditoria != null) {
                    oldSgprogAuditoriaOfSgproccAuditoriaListSgproccAuditoria.getSgproccAuditoriaList().remove(sgproccAuditoriaListSgproccAuditoria);
                    oldSgprogAuditoriaOfSgproccAuditoriaListSgproccAuditoria = em.merge(oldSgprogAuditoriaOfSgproccAuditoriaListSgproccAuditoria);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgprogAuditoria(sgprogAuditoria.getSgprogAuditoriaPK()) != null) {
                throw new PreexistingEntityException("SgprogAuditoria " + sgprogAuditoria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgprogAuditoria sgprogAuditoria) throws IllegalOrphanException, NonexistentEntityException, Exception {
        sgprogAuditoria.getSgprogAuditoriaPK().setIdsociedad(sgprogAuditoria.getSgdetalleAuditoria().getSgdetalleAuditoriaPK().getIdsociedad());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgprogAuditoria persistentSgprogAuditoria = em.find(SgprogAuditoria.class, sgprogAuditoria.getSgprogAuditoriaPK());
            SgdetalleAuditoria sgdetalleAuditoriaOld = persistentSgprogAuditoria.getSgdetalleAuditoria();
            SgdetalleAuditoria sgdetalleAuditoriaNew = sgprogAuditoria.getSgdetalleAuditoria();
            List<SgproccAuditoria> sgproccAuditoriaListOld = persistentSgprogAuditoria.getSgproccAuditoriaList();
            List<SgproccAuditoria> sgproccAuditoriaListNew = sgprogAuditoria.getSgproccAuditoriaList();
            List<String> illegalOrphanMessages = null;
            for (SgproccAuditoria sgproccAuditoriaListOldSgproccAuditoria : sgproccAuditoriaListOld) {
                if (!sgproccAuditoriaListNew.contains(sgproccAuditoriaListOldSgproccAuditoria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SgproccAuditoria " + sgproccAuditoriaListOldSgproccAuditoria + " since its sgprogAuditoria field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sgdetalleAuditoriaNew != null) {
                sgdetalleAuditoriaNew = em.getReference(sgdetalleAuditoriaNew.getClass(), sgdetalleAuditoriaNew.getSgdetalleAuditoriaPK());
                sgprogAuditoria.setSgdetalleAuditoria(sgdetalleAuditoriaNew);
            }
            List<SgproccAuditoria> attachedSgproccAuditoriaListNew = new ArrayList<SgproccAuditoria>();
            for (SgproccAuditoria sgproccAuditoriaListNewSgproccAuditoriaToAttach : sgproccAuditoriaListNew) {
                sgproccAuditoriaListNewSgproccAuditoriaToAttach = em.getReference(sgproccAuditoriaListNewSgproccAuditoriaToAttach.getClass(), sgproccAuditoriaListNewSgproccAuditoriaToAttach.getSgproccAuditoriaPK());
                attachedSgproccAuditoriaListNew.add(sgproccAuditoriaListNewSgproccAuditoriaToAttach);
            }
            sgproccAuditoriaListNew = attachedSgproccAuditoriaListNew;
            sgprogAuditoria.setSgproccAuditoriaList(sgproccAuditoriaListNew);
            sgprogAuditoria = em.merge(sgprogAuditoria);
            if (sgdetalleAuditoriaOld != null && !sgdetalleAuditoriaOld.equals(sgdetalleAuditoriaNew)) {
                sgdetalleAuditoriaOld.getSgprogAuditoriaList().remove(sgprogAuditoria);
                sgdetalleAuditoriaOld = em.merge(sgdetalleAuditoriaOld);
            }
            if (sgdetalleAuditoriaNew != null && !sgdetalleAuditoriaNew.equals(sgdetalleAuditoriaOld)) {
                sgdetalleAuditoriaNew.getSgprogAuditoriaList().add(sgprogAuditoria);
                sgdetalleAuditoriaNew = em.merge(sgdetalleAuditoriaNew);
            }
            for (SgproccAuditoria sgproccAuditoriaListNewSgproccAuditoria : sgproccAuditoriaListNew) {
                if (!sgproccAuditoriaListOld.contains(sgproccAuditoriaListNewSgproccAuditoria)) {
                    SgprogAuditoria oldSgprogAuditoriaOfSgproccAuditoriaListNewSgproccAuditoria = sgproccAuditoriaListNewSgproccAuditoria.getSgprogAuditoria();
                    sgproccAuditoriaListNewSgproccAuditoria.setSgprogAuditoria(sgprogAuditoria);
                    sgproccAuditoriaListNewSgproccAuditoria = em.merge(sgproccAuditoriaListNewSgproccAuditoria);
                    if (oldSgprogAuditoriaOfSgproccAuditoriaListNewSgproccAuditoria != null && !oldSgprogAuditoriaOfSgproccAuditoriaListNewSgproccAuditoria.equals(sgprogAuditoria)) {
                        oldSgprogAuditoriaOfSgproccAuditoriaListNewSgproccAuditoria.getSgproccAuditoriaList().remove(sgproccAuditoriaListNewSgproccAuditoria);
                        oldSgprogAuditoriaOfSgproccAuditoriaListNewSgproccAuditoria = em.merge(oldSgprogAuditoriaOfSgproccAuditoriaListNewSgproccAuditoria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgprogAuditoriaPK id = sgprogAuditoria.getSgprogAuditoriaPK();
                if (findSgprogAuditoria(id) == null) {
                    throw new NonexistentEntityException("The sgprogAuditoria with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgprogAuditoriaPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgprogAuditoria sgprogAuditoria;
            try {
                sgprogAuditoria = em.getReference(SgprogAuditoria.class, id);
                sgprogAuditoria.getSgprogAuditoriaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgprogAuditoria with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SgproccAuditoria> sgproccAuditoriaListOrphanCheck = sgprogAuditoria.getSgproccAuditoriaList();
            for (SgproccAuditoria sgproccAuditoriaListOrphanCheckSgproccAuditoria : sgproccAuditoriaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SgprogAuditoria (" + sgprogAuditoria + ") cannot be destroyed since the SgproccAuditoria " + sgproccAuditoriaListOrphanCheckSgproccAuditoria + " in its sgproccAuditoriaList field has a non-nullable sgprogAuditoria field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            SgdetalleAuditoria sgdetalleAuditoria = sgprogAuditoria.getSgdetalleAuditoria();
            if (sgdetalleAuditoria != null) {
                sgdetalleAuditoria.getSgprogAuditoriaList().remove(sgprogAuditoria);
                sgdetalleAuditoria = em.merge(sgdetalleAuditoria);
            }
            em.remove(sgprogAuditoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgprogAuditoria> findSgprogAuditoriaEntities() {
        return findSgprogAuditoriaEntities(true, -1, -1);
    }

    public List<SgprogAuditoria> findSgprogAuditoriaEntities(int maxResults, int firstResult) {
        return findSgprogAuditoriaEntities(false, maxResults, firstResult);
    }

    private List<SgprogAuditoria> findSgprogAuditoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgprogAuditoria.class));
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

    public SgprogAuditoria findSgprogAuditoria(SgprogAuditoriaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgprogAuditoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgprogAuditoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgprogAuditoria> rt = cq.from(SgprogAuditoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
