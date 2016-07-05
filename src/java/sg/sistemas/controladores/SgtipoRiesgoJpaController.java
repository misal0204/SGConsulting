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
import sg.sistemas.entidades.Sgriesgo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgtipoRiesgo;

/**
 *
 * @author Misael
 */
public class SgtipoRiesgoJpaController implements Serializable {

    public SgtipoRiesgoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgtipoRiesgo sgtipoRiesgo) throws PreexistingEntityException, Exception {
        if (sgtipoRiesgo.getSgriesgoList() == null) {
            sgtipoRiesgo.setSgriesgoList(new ArrayList<Sgriesgo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Sgriesgo> attachedSgriesgoList = new ArrayList<Sgriesgo>();
            for (Sgriesgo sgriesgoListSgriesgoToAttach : sgtipoRiesgo.getSgriesgoList()) {
                sgriesgoListSgriesgoToAttach = em.getReference(sgriesgoListSgriesgoToAttach.getClass(), sgriesgoListSgriesgoToAttach.getSgriesgoPK());
                attachedSgriesgoList.add(sgriesgoListSgriesgoToAttach);
            }
            sgtipoRiesgo.setSgriesgoList(attachedSgriesgoList);
            em.persist(sgtipoRiesgo);
            for (Sgriesgo sgriesgoListSgriesgo : sgtipoRiesgo.getSgriesgoList()) {
                SgtipoRiesgo oldIdtipoRiesgoOfSgriesgoListSgriesgo = sgriesgoListSgriesgo.getIdtipoRiesgo();
                sgriesgoListSgriesgo.setIdtipoRiesgo(sgtipoRiesgo);
                sgriesgoListSgriesgo = em.merge(sgriesgoListSgriesgo);
                if (oldIdtipoRiesgoOfSgriesgoListSgriesgo != null) {
                    oldIdtipoRiesgoOfSgriesgoListSgriesgo.getSgriesgoList().remove(sgriesgoListSgriesgo);
                    oldIdtipoRiesgoOfSgriesgoListSgriesgo = em.merge(oldIdtipoRiesgoOfSgriesgoListSgriesgo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgtipoRiesgo(sgtipoRiesgo.getIdtipoRiesgo()) != null) {
                throw new PreexistingEntityException("SgtipoRiesgo " + sgtipoRiesgo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgtipoRiesgo sgtipoRiesgo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgtipoRiesgo persistentSgtipoRiesgo = em.find(SgtipoRiesgo.class, sgtipoRiesgo.getIdtipoRiesgo());
            List<Sgriesgo> sgriesgoListOld = persistentSgtipoRiesgo.getSgriesgoList();
            List<Sgriesgo> sgriesgoListNew = sgtipoRiesgo.getSgriesgoList();
            List<Sgriesgo> attachedSgriesgoListNew = new ArrayList<Sgriesgo>();
            for (Sgriesgo sgriesgoListNewSgriesgoToAttach : sgriesgoListNew) {
                sgriesgoListNewSgriesgoToAttach = em.getReference(sgriesgoListNewSgriesgoToAttach.getClass(), sgriesgoListNewSgriesgoToAttach.getSgriesgoPK());
                attachedSgriesgoListNew.add(sgriesgoListNewSgriesgoToAttach);
            }
            sgriesgoListNew = attachedSgriesgoListNew;
            sgtipoRiesgo.setSgriesgoList(sgriesgoListNew);
            sgtipoRiesgo = em.merge(sgtipoRiesgo);
            for (Sgriesgo sgriesgoListOldSgriesgo : sgriesgoListOld) {
                if (!sgriesgoListNew.contains(sgriesgoListOldSgriesgo)) {
                    sgriesgoListOldSgriesgo.setIdtipoRiesgo(null);
                    sgriesgoListOldSgriesgo = em.merge(sgriesgoListOldSgriesgo);
                }
            }
            for (Sgriesgo sgriesgoListNewSgriesgo : sgriesgoListNew) {
                if (!sgriesgoListOld.contains(sgriesgoListNewSgriesgo)) {
                    SgtipoRiesgo oldIdtipoRiesgoOfSgriesgoListNewSgriesgo = sgriesgoListNewSgriesgo.getIdtipoRiesgo();
                    sgriesgoListNewSgriesgo.setIdtipoRiesgo(sgtipoRiesgo);
                    sgriesgoListNewSgriesgo = em.merge(sgriesgoListNewSgriesgo);
                    if (oldIdtipoRiesgoOfSgriesgoListNewSgriesgo != null && !oldIdtipoRiesgoOfSgriesgoListNewSgriesgo.equals(sgtipoRiesgo)) {
                        oldIdtipoRiesgoOfSgriesgoListNewSgriesgo.getSgriesgoList().remove(sgriesgoListNewSgriesgo);
                        oldIdtipoRiesgoOfSgriesgoListNewSgriesgo = em.merge(oldIdtipoRiesgoOfSgriesgoListNewSgriesgo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = sgtipoRiesgo.getIdtipoRiesgo();
                if (findSgtipoRiesgo(id) == null) {
                    throw new NonexistentEntityException("The sgtipoRiesgo with id " + id + " no longer exists.");
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
            SgtipoRiesgo sgtipoRiesgo;
            try {
                sgtipoRiesgo = em.getReference(SgtipoRiesgo.class, id);
                sgtipoRiesgo.getIdtipoRiesgo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgtipoRiesgo with id " + id + " no longer exists.", enfe);
            }
            List<Sgriesgo> sgriesgoList = sgtipoRiesgo.getSgriesgoList();
            for (Sgriesgo sgriesgoListSgriesgo : sgriesgoList) {
                sgriesgoListSgriesgo.setIdtipoRiesgo(null);
                sgriesgoListSgriesgo = em.merge(sgriesgoListSgriesgo);
            }
            em.remove(sgtipoRiesgo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgtipoRiesgo> findSgtipoRiesgoEntities() {
        return findSgtipoRiesgoEntities(true, -1, -1);
    }

    public List<SgtipoRiesgo> findSgtipoRiesgoEntities(int maxResults, int firstResult) {
        return findSgtipoRiesgoEntities(false, maxResults, firstResult);
    }

    private List<SgtipoRiesgo> findSgtipoRiesgoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgtipoRiesgo.class));
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

    public SgtipoRiesgo findSgtipoRiesgo(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgtipoRiesgo.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgtipoRiesgoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgtipoRiesgo> rt = cq.from(SgtipoRiesgo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
