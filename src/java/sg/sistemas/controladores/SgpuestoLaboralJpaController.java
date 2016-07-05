/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.controladores;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgpuestoLaboral;

/**
 *
 * @author Misael
 */
public class SgpuestoLaboralJpaController implements Serializable {

    public SgpuestoLaboralJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgpuestoLaboral sgpuestoLaboral) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(sgpuestoLaboral);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgpuestoLaboral(sgpuestoLaboral.getIdpuesto()) != null) {
                throw new PreexistingEntityException("SgpuestoLaboral " + sgpuestoLaboral + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgpuestoLaboral sgpuestoLaboral) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            sgpuestoLaboral = em.merge(sgpuestoLaboral);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = sgpuestoLaboral.getIdpuesto();
                if (findSgpuestoLaboral(id) == null) {
                    throw new NonexistentEntityException("The sgpuestoLaboral with id " + id + " no longer exists.");
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
            SgpuestoLaboral sgpuestoLaboral;
            try {
                sgpuestoLaboral = em.getReference(SgpuestoLaboral.class, id);
                sgpuestoLaboral.getIdpuesto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgpuestoLaboral with id " + id + " no longer exists.", enfe);
            }
            em.remove(sgpuestoLaboral);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgpuestoLaboral> findSgpuestoLaboralEntities() {
        return findSgpuestoLaboralEntities(true, -1, -1);
    }

    public List<SgpuestoLaboral> findSgpuestoLaboralEntities(int maxResults, int firstResult) {
        return findSgpuestoLaboralEntities(false, maxResults, firstResult);
    }

    private List<SgpuestoLaboral> findSgpuestoLaboralEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgpuestoLaboral.class));
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

    public SgpuestoLaboral findSgpuestoLaboral(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgpuestoLaboral.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgpuestoLaboralCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgpuestoLaboral> rt = cq.from(SgpuestoLaboral.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
