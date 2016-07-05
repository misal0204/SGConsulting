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
import sg.sistemas.entidades.SgplanForma;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgtipoPlan;

/**
 *
 * @author Misael
 */
public class SgtipoPlanJpaController implements Serializable {

    public SgtipoPlanJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgtipoPlan sgtipoPlan) throws PreexistingEntityException, Exception {
        if (sgtipoPlan.getSgplanFormaList() == null) {
            sgtipoPlan.setSgplanFormaList(new ArrayList<SgplanForma>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SgplanForma> attachedSgplanFormaList = new ArrayList<SgplanForma>();
            for (SgplanForma sgplanFormaListSgplanFormaToAttach : sgtipoPlan.getSgplanFormaList()) {
                sgplanFormaListSgplanFormaToAttach = em.getReference(sgplanFormaListSgplanFormaToAttach.getClass(), sgplanFormaListSgplanFormaToAttach.getSgplanFormaPK());
                attachedSgplanFormaList.add(sgplanFormaListSgplanFormaToAttach);
            }
            sgtipoPlan.setSgplanFormaList(attachedSgplanFormaList);
            em.persist(sgtipoPlan);
            for (SgplanForma sgplanFormaListSgplanForma : sgtipoPlan.getSgplanFormaList()) {
                SgtipoPlan oldIdtipoPlanOfSgplanFormaListSgplanForma = sgplanFormaListSgplanForma.getIdtipoPlan();
                sgplanFormaListSgplanForma.setIdtipoPlan(sgtipoPlan);
                sgplanFormaListSgplanForma = em.merge(sgplanFormaListSgplanForma);
                if (oldIdtipoPlanOfSgplanFormaListSgplanForma != null) {
                    oldIdtipoPlanOfSgplanFormaListSgplanForma.getSgplanFormaList().remove(sgplanFormaListSgplanForma);
                    oldIdtipoPlanOfSgplanFormaListSgplanForma = em.merge(oldIdtipoPlanOfSgplanFormaListSgplanForma);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgtipoPlan(sgtipoPlan.getIdtipoPlan()) != null) {
                throw new PreexistingEntityException("SgtipoPlan " + sgtipoPlan + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgtipoPlan sgtipoPlan) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgtipoPlan persistentSgtipoPlan = em.find(SgtipoPlan.class, sgtipoPlan.getIdtipoPlan());
            List<SgplanForma> sgplanFormaListOld = persistentSgtipoPlan.getSgplanFormaList();
            List<SgplanForma> sgplanFormaListNew = sgtipoPlan.getSgplanFormaList();
            List<SgplanForma> attachedSgplanFormaListNew = new ArrayList<SgplanForma>();
            for (SgplanForma sgplanFormaListNewSgplanFormaToAttach : sgplanFormaListNew) {
                sgplanFormaListNewSgplanFormaToAttach = em.getReference(sgplanFormaListNewSgplanFormaToAttach.getClass(), sgplanFormaListNewSgplanFormaToAttach.getSgplanFormaPK());
                attachedSgplanFormaListNew.add(sgplanFormaListNewSgplanFormaToAttach);
            }
            sgplanFormaListNew = attachedSgplanFormaListNew;
            sgtipoPlan.setSgplanFormaList(sgplanFormaListNew);
            sgtipoPlan = em.merge(sgtipoPlan);
            for (SgplanForma sgplanFormaListOldSgplanForma : sgplanFormaListOld) {
                if (!sgplanFormaListNew.contains(sgplanFormaListOldSgplanForma)) {
                    sgplanFormaListOldSgplanForma.setIdtipoPlan(null);
                    sgplanFormaListOldSgplanForma = em.merge(sgplanFormaListOldSgplanForma);
                }
            }
            for (SgplanForma sgplanFormaListNewSgplanForma : sgplanFormaListNew) {
                if (!sgplanFormaListOld.contains(sgplanFormaListNewSgplanForma)) {
                    SgtipoPlan oldIdtipoPlanOfSgplanFormaListNewSgplanForma = sgplanFormaListNewSgplanForma.getIdtipoPlan();
                    sgplanFormaListNewSgplanForma.setIdtipoPlan(sgtipoPlan);
                    sgplanFormaListNewSgplanForma = em.merge(sgplanFormaListNewSgplanForma);
                    if (oldIdtipoPlanOfSgplanFormaListNewSgplanForma != null && !oldIdtipoPlanOfSgplanFormaListNewSgplanForma.equals(sgtipoPlan)) {
                        oldIdtipoPlanOfSgplanFormaListNewSgplanForma.getSgplanFormaList().remove(sgplanFormaListNewSgplanForma);
                        oldIdtipoPlanOfSgplanFormaListNewSgplanForma = em.merge(oldIdtipoPlanOfSgplanFormaListNewSgplanForma);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = sgtipoPlan.getIdtipoPlan();
                if (findSgtipoPlan(id) == null) {
                    throw new NonexistentEntityException("The sgtipoPlan with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgtipoPlan sgtipoPlan;
            try {
                sgtipoPlan = em.getReference(SgtipoPlan.class, id);
                sgtipoPlan.getIdtipoPlan();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgtipoPlan with id " + id + " no longer exists.", enfe);
            }
            List<SgplanForma> sgplanFormaList = sgtipoPlan.getSgplanFormaList();
            for (SgplanForma sgplanFormaListSgplanForma : sgplanFormaList) {
                sgplanFormaListSgplanForma.setIdtipoPlan(null);
                sgplanFormaListSgplanForma = em.merge(sgplanFormaListSgplanForma);
            }
            em.remove(sgtipoPlan);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgtipoPlan> findSgtipoPlanEntities() {
        return findSgtipoPlanEntities(true, -1, -1);
    }

    public List<SgtipoPlan> findSgtipoPlanEntities(int maxResults, int firstResult) {
        return findSgtipoPlanEntities(false, maxResults, firstResult);
    }

    private List<SgtipoPlan> findSgtipoPlanEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgtipoPlan.class));
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

    public SgtipoPlan findSgtipoPlan(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgtipoPlan.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgtipoPlanCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgtipoPlan> rt = cq.from(SgtipoPlan.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
