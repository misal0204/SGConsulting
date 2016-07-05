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
import sg.sistemas.entidades.SgplanAuditoria;
import sg.sistemas.entidades.SgprogAuditoria;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgdetalleAuditoria;
import sg.sistemas.entidades.SgdetalleAuditoriaPK;

/**
 *
 * @author Misael
 */
public class SgdetalleAuditoriaJpaController implements Serializable {

    public SgdetalleAuditoriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgdetalleAuditoria sgdetalleAuditoria) throws PreexistingEntityException, Exception {
        if (sgdetalleAuditoria.getSgdetalleAuditoriaPK() == null) {
            sgdetalleAuditoria.setSgdetalleAuditoriaPK(new SgdetalleAuditoriaPK());
        }
        if (sgdetalleAuditoria.getSgprogAuditoriaList() == null) {
            sgdetalleAuditoria.setSgprogAuditoriaList(new ArrayList<SgprogAuditoria>());
        }
        sgdetalleAuditoria.getSgdetalleAuditoriaPK().setIdsociedad(sgdetalleAuditoria.getSgplanAuditoria().getSgplanAuditoriaPK().getIdsociedad());
        sgdetalleAuditoria.getSgdetalleAuditoriaPK().setIdplanAud(sgdetalleAuditoria.getSgplanAuditoria().getSgplanAuditoriaPK().getIdplanAud());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgplanAuditoria sgplanAuditoria = sgdetalleAuditoria.getSgplanAuditoria();
            if (sgplanAuditoria != null) {
                sgplanAuditoria = em.getReference(sgplanAuditoria.getClass(), sgplanAuditoria.getSgplanAuditoriaPK());
                sgdetalleAuditoria.setSgplanAuditoria(sgplanAuditoria);
            }
            List<SgprogAuditoria> attachedSgprogAuditoriaList = new ArrayList<SgprogAuditoria>();
            for (SgprogAuditoria sgprogAuditoriaListSgprogAuditoriaToAttach : sgdetalleAuditoria.getSgprogAuditoriaList()) {
                sgprogAuditoriaListSgprogAuditoriaToAttach = em.getReference(sgprogAuditoriaListSgprogAuditoriaToAttach.getClass(), sgprogAuditoriaListSgprogAuditoriaToAttach.getSgprogAuditoriaPK());
                attachedSgprogAuditoriaList.add(sgprogAuditoriaListSgprogAuditoriaToAttach);
            }
            sgdetalleAuditoria.setSgprogAuditoriaList(attachedSgprogAuditoriaList);
            em.persist(sgdetalleAuditoria);
            if (sgplanAuditoria != null) {
                sgplanAuditoria.getSgdetalleAuditoriaList().add(sgdetalleAuditoria);
                sgplanAuditoria = em.merge(sgplanAuditoria);
            }
            for (SgprogAuditoria sgprogAuditoriaListSgprogAuditoria : sgdetalleAuditoria.getSgprogAuditoriaList()) {
                SgdetalleAuditoria oldSgdetalleAuditoriaOfSgprogAuditoriaListSgprogAuditoria = sgprogAuditoriaListSgprogAuditoria.getSgdetalleAuditoria();
                sgprogAuditoriaListSgprogAuditoria.setSgdetalleAuditoria(sgdetalleAuditoria);
                sgprogAuditoriaListSgprogAuditoria = em.merge(sgprogAuditoriaListSgprogAuditoria);
                if (oldSgdetalleAuditoriaOfSgprogAuditoriaListSgprogAuditoria != null) {
                    oldSgdetalleAuditoriaOfSgprogAuditoriaListSgprogAuditoria.getSgprogAuditoriaList().remove(sgprogAuditoriaListSgprogAuditoria);
                    oldSgdetalleAuditoriaOfSgprogAuditoriaListSgprogAuditoria = em.merge(oldSgdetalleAuditoriaOfSgprogAuditoriaListSgprogAuditoria);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgdetalleAuditoria(sgdetalleAuditoria.getSgdetalleAuditoriaPK()) != null) {
                throw new PreexistingEntityException("SgdetalleAuditoria " + sgdetalleAuditoria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgdetalleAuditoria sgdetalleAuditoria) throws IllegalOrphanException, NonexistentEntityException, Exception {
        sgdetalleAuditoria.getSgdetalleAuditoriaPK().setIdsociedad(sgdetalleAuditoria.getSgplanAuditoria().getSgplanAuditoriaPK().getIdsociedad());
        sgdetalleAuditoria.getSgdetalleAuditoriaPK().setIdplanAud(sgdetalleAuditoria.getSgplanAuditoria().getSgplanAuditoriaPK().getIdplanAud());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgdetalleAuditoria persistentSgdetalleAuditoria = em.find(SgdetalleAuditoria.class, sgdetalleAuditoria.getSgdetalleAuditoriaPK());
            SgplanAuditoria sgplanAuditoriaOld = persistentSgdetalleAuditoria.getSgplanAuditoria();
            SgplanAuditoria sgplanAuditoriaNew = sgdetalleAuditoria.getSgplanAuditoria();
            List<SgprogAuditoria> sgprogAuditoriaListOld = persistentSgdetalleAuditoria.getSgprogAuditoriaList();
            List<SgprogAuditoria> sgprogAuditoriaListNew = sgdetalleAuditoria.getSgprogAuditoriaList();
            List<String> illegalOrphanMessages = null;
            for (SgprogAuditoria sgprogAuditoriaListOldSgprogAuditoria : sgprogAuditoriaListOld) {
                if (!sgprogAuditoriaListNew.contains(sgprogAuditoriaListOldSgprogAuditoria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SgprogAuditoria " + sgprogAuditoriaListOldSgprogAuditoria + " since its sgdetalleAuditoria field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sgplanAuditoriaNew != null) {
                sgplanAuditoriaNew = em.getReference(sgplanAuditoriaNew.getClass(), sgplanAuditoriaNew.getSgplanAuditoriaPK());
                sgdetalleAuditoria.setSgplanAuditoria(sgplanAuditoriaNew);
            }
            List<SgprogAuditoria> attachedSgprogAuditoriaListNew = new ArrayList<SgprogAuditoria>();
            for (SgprogAuditoria sgprogAuditoriaListNewSgprogAuditoriaToAttach : sgprogAuditoriaListNew) {
                sgprogAuditoriaListNewSgprogAuditoriaToAttach = em.getReference(sgprogAuditoriaListNewSgprogAuditoriaToAttach.getClass(), sgprogAuditoriaListNewSgprogAuditoriaToAttach.getSgprogAuditoriaPK());
                attachedSgprogAuditoriaListNew.add(sgprogAuditoriaListNewSgprogAuditoriaToAttach);
            }
            sgprogAuditoriaListNew = attachedSgprogAuditoriaListNew;
            sgdetalleAuditoria.setSgprogAuditoriaList(sgprogAuditoriaListNew);
            sgdetalleAuditoria = em.merge(sgdetalleAuditoria);
            if (sgplanAuditoriaOld != null && !sgplanAuditoriaOld.equals(sgplanAuditoriaNew)) {
                sgplanAuditoriaOld.getSgdetalleAuditoriaList().remove(sgdetalleAuditoria);
                sgplanAuditoriaOld = em.merge(sgplanAuditoriaOld);
            }
            if (sgplanAuditoriaNew != null && !sgplanAuditoriaNew.equals(sgplanAuditoriaOld)) {
                sgplanAuditoriaNew.getSgdetalleAuditoriaList().add(sgdetalleAuditoria);
                sgplanAuditoriaNew = em.merge(sgplanAuditoriaNew);
            }
            for (SgprogAuditoria sgprogAuditoriaListNewSgprogAuditoria : sgprogAuditoriaListNew) {
                if (!sgprogAuditoriaListOld.contains(sgprogAuditoriaListNewSgprogAuditoria)) {
                    SgdetalleAuditoria oldSgdetalleAuditoriaOfSgprogAuditoriaListNewSgprogAuditoria = sgprogAuditoriaListNewSgprogAuditoria.getSgdetalleAuditoria();
                    sgprogAuditoriaListNewSgprogAuditoria.setSgdetalleAuditoria(sgdetalleAuditoria);
                    sgprogAuditoriaListNewSgprogAuditoria = em.merge(sgprogAuditoriaListNewSgprogAuditoria);
                    if (oldSgdetalleAuditoriaOfSgprogAuditoriaListNewSgprogAuditoria != null && !oldSgdetalleAuditoriaOfSgprogAuditoriaListNewSgprogAuditoria.equals(sgdetalleAuditoria)) {
                        oldSgdetalleAuditoriaOfSgprogAuditoriaListNewSgprogAuditoria.getSgprogAuditoriaList().remove(sgprogAuditoriaListNewSgprogAuditoria);
                        oldSgdetalleAuditoriaOfSgprogAuditoriaListNewSgprogAuditoria = em.merge(oldSgdetalleAuditoriaOfSgprogAuditoriaListNewSgprogAuditoria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgdetalleAuditoriaPK id = sgdetalleAuditoria.getSgdetalleAuditoriaPK();
                if (findSgdetalleAuditoria(id) == null) {
                    throw new NonexistentEntityException("The sgdetalleAuditoria with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgdetalleAuditoriaPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgdetalleAuditoria sgdetalleAuditoria;
            try {
                sgdetalleAuditoria = em.getReference(SgdetalleAuditoria.class, id);
                sgdetalleAuditoria.getSgdetalleAuditoriaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgdetalleAuditoria with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SgprogAuditoria> sgprogAuditoriaListOrphanCheck = sgdetalleAuditoria.getSgprogAuditoriaList();
            for (SgprogAuditoria sgprogAuditoriaListOrphanCheckSgprogAuditoria : sgprogAuditoriaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SgdetalleAuditoria (" + sgdetalleAuditoria + ") cannot be destroyed since the SgprogAuditoria " + sgprogAuditoriaListOrphanCheckSgprogAuditoria + " in its sgprogAuditoriaList field has a non-nullable sgdetalleAuditoria field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            SgplanAuditoria sgplanAuditoria = sgdetalleAuditoria.getSgplanAuditoria();
            if (sgplanAuditoria != null) {
                sgplanAuditoria.getSgdetalleAuditoriaList().remove(sgdetalleAuditoria);
                sgplanAuditoria = em.merge(sgplanAuditoria);
            }
            em.remove(sgdetalleAuditoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgdetalleAuditoria> findSgdetalleAuditoriaEntities() {
        return findSgdetalleAuditoriaEntities(true, -1, -1);
    }

    public List<SgdetalleAuditoria> findSgdetalleAuditoriaEntities(int maxResults, int firstResult) {
        return findSgdetalleAuditoriaEntities(false, maxResults, firstResult);
    }

    private List<SgdetalleAuditoria> findSgdetalleAuditoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgdetalleAuditoria.class));
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

    public SgdetalleAuditoria findSgdetalleAuditoria(SgdetalleAuditoriaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgdetalleAuditoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgdetalleAuditoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgdetalleAuditoria> rt = cq.from(SgdetalleAuditoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
