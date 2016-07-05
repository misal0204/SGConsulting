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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgplanAuditoria;
import sg.sistemas.entidades.SgplanAuditoriaPK;

/**
 *
 * @author Misael
 */
public class SgplanAuditoriaJpaController implements Serializable {

    public SgplanAuditoriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgplanAuditoria sgplanAuditoria) throws PreexistingEntityException, Exception {
        if (sgplanAuditoria.getSgplanAuditoriaPK() == null) {
            sgplanAuditoria.setSgplanAuditoriaPK(new SgplanAuditoriaPK());
        }
        if (sgplanAuditoria.getSgdetalleAuditoriaList() == null) {
            sgplanAuditoria.setSgdetalleAuditoriaList(new ArrayList<SgdetalleAuditoria>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SgdetalleAuditoria> attachedSgdetalleAuditoriaList = new ArrayList<SgdetalleAuditoria>();
            for (SgdetalleAuditoria sgdetalleAuditoriaListSgdetalleAuditoriaToAttach : sgplanAuditoria.getSgdetalleAuditoriaList()) {
                sgdetalleAuditoriaListSgdetalleAuditoriaToAttach = em.getReference(sgdetalleAuditoriaListSgdetalleAuditoriaToAttach.getClass(), sgdetalleAuditoriaListSgdetalleAuditoriaToAttach.getSgdetalleAuditoriaPK());
                attachedSgdetalleAuditoriaList.add(sgdetalleAuditoriaListSgdetalleAuditoriaToAttach);
            }
            sgplanAuditoria.setSgdetalleAuditoriaList(attachedSgdetalleAuditoriaList);
            em.persist(sgplanAuditoria);
            for (SgdetalleAuditoria sgdetalleAuditoriaListSgdetalleAuditoria : sgplanAuditoria.getSgdetalleAuditoriaList()) {
                SgplanAuditoria oldSgplanAuditoriaOfSgdetalleAuditoriaListSgdetalleAuditoria = sgdetalleAuditoriaListSgdetalleAuditoria.getSgplanAuditoria();
                sgdetalleAuditoriaListSgdetalleAuditoria.setSgplanAuditoria(sgplanAuditoria);
                sgdetalleAuditoriaListSgdetalleAuditoria = em.merge(sgdetalleAuditoriaListSgdetalleAuditoria);
                if (oldSgplanAuditoriaOfSgdetalleAuditoriaListSgdetalleAuditoria != null) {
                    oldSgplanAuditoriaOfSgdetalleAuditoriaListSgdetalleAuditoria.getSgdetalleAuditoriaList().remove(sgdetalleAuditoriaListSgdetalleAuditoria);
                    oldSgplanAuditoriaOfSgdetalleAuditoriaListSgdetalleAuditoria = em.merge(oldSgplanAuditoriaOfSgdetalleAuditoriaListSgdetalleAuditoria);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgplanAuditoria(sgplanAuditoria.getSgplanAuditoriaPK()) != null) {
                throw new PreexistingEntityException("SgplanAuditoria " + sgplanAuditoria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgplanAuditoria sgplanAuditoria) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgplanAuditoria persistentSgplanAuditoria = em.find(SgplanAuditoria.class, sgplanAuditoria.getSgplanAuditoriaPK());
            List<SgdetalleAuditoria> sgdetalleAuditoriaListOld = persistentSgplanAuditoria.getSgdetalleAuditoriaList();
            List<SgdetalleAuditoria> sgdetalleAuditoriaListNew = sgplanAuditoria.getSgdetalleAuditoriaList();
            List<String> illegalOrphanMessages = null;
            for (SgdetalleAuditoria sgdetalleAuditoriaListOldSgdetalleAuditoria : sgdetalleAuditoriaListOld) {
                if (!sgdetalleAuditoriaListNew.contains(sgdetalleAuditoriaListOldSgdetalleAuditoria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SgdetalleAuditoria " + sgdetalleAuditoriaListOldSgdetalleAuditoria + " since its sgplanAuditoria field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<SgdetalleAuditoria> attachedSgdetalleAuditoriaListNew = new ArrayList<SgdetalleAuditoria>();
            for (SgdetalleAuditoria sgdetalleAuditoriaListNewSgdetalleAuditoriaToAttach : sgdetalleAuditoriaListNew) {
                sgdetalleAuditoriaListNewSgdetalleAuditoriaToAttach = em.getReference(sgdetalleAuditoriaListNewSgdetalleAuditoriaToAttach.getClass(), sgdetalleAuditoriaListNewSgdetalleAuditoriaToAttach.getSgdetalleAuditoriaPK());
                attachedSgdetalleAuditoriaListNew.add(sgdetalleAuditoriaListNewSgdetalleAuditoriaToAttach);
            }
            sgdetalleAuditoriaListNew = attachedSgdetalleAuditoriaListNew;
            sgplanAuditoria.setSgdetalleAuditoriaList(sgdetalleAuditoriaListNew);
            sgplanAuditoria = em.merge(sgplanAuditoria);
            for (SgdetalleAuditoria sgdetalleAuditoriaListNewSgdetalleAuditoria : sgdetalleAuditoriaListNew) {
                if (!sgdetalleAuditoriaListOld.contains(sgdetalleAuditoriaListNewSgdetalleAuditoria)) {
                    SgplanAuditoria oldSgplanAuditoriaOfSgdetalleAuditoriaListNewSgdetalleAuditoria = sgdetalleAuditoriaListNewSgdetalleAuditoria.getSgplanAuditoria();
                    sgdetalleAuditoriaListNewSgdetalleAuditoria.setSgplanAuditoria(sgplanAuditoria);
                    sgdetalleAuditoriaListNewSgdetalleAuditoria = em.merge(sgdetalleAuditoriaListNewSgdetalleAuditoria);
                    if (oldSgplanAuditoriaOfSgdetalleAuditoriaListNewSgdetalleAuditoria != null && !oldSgplanAuditoriaOfSgdetalleAuditoriaListNewSgdetalleAuditoria.equals(sgplanAuditoria)) {
                        oldSgplanAuditoriaOfSgdetalleAuditoriaListNewSgdetalleAuditoria.getSgdetalleAuditoriaList().remove(sgdetalleAuditoriaListNewSgdetalleAuditoria);
                        oldSgplanAuditoriaOfSgdetalleAuditoriaListNewSgdetalleAuditoria = em.merge(oldSgplanAuditoriaOfSgdetalleAuditoriaListNewSgdetalleAuditoria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgplanAuditoriaPK id = sgplanAuditoria.getSgplanAuditoriaPK();
                if (findSgplanAuditoria(id) == null) {
                    throw new NonexistentEntityException("The sgplanAuditoria with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgplanAuditoriaPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgplanAuditoria sgplanAuditoria;
            try {
                sgplanAuditoria = em.getReference(SgplanAuditoria.class, id);
                sgplanAuditoria.getSgplanAuditoriaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgplanAuditoria with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SgdetalleAuditoria> sgdetalleAuditoriaListOrphanCheck = sgplanAuditoria.getSgdetalleAuditoriaList();
            for (SgdetalleAuditoria sgdetalleAuditoriaListOrphanCheckSgdetalleAuditoria : sgdetalleAuditoriaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SgplanAuditoria (" + sgplanAuditoria + ") cannot be destroyed since the SgdetalleAuditoria " + sgdetalleAuditoriaListOrphanCheckSgdetalleAuditoria + " in its sgdetalleAuditoriaList field has a non-nullable sgplanAuditoria field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(sgplanAuditoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgplanAuditoria> findSgplanAuditoriaEntities() {
        return findSgplanAuditoriaEntities(true, -1, -1);
    }

    public List<SgplanAuditoria> findSgplanAuditoriaEntities(int maxResults, int firstResult) {
        return findSgplanAuditoriaEntities(false, maxResults, firstResult);
    }

    private List<SgplanAuditoria> findSgplanAuditoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgplanAuditoria.class));
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

    public SgplanAuditoria findSgplanAuditoria(SgplanAuditoriaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgplanAuditoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgplanAuditoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgplanAuditoria> rt = cq.from(SgplanAuditoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
