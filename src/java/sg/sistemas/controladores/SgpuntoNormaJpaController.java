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
import sg.sistemas.entidades.Sgnorma;
import sg.sistemas.entidades.SgpuntoNorma;
import sg.sistemas.entidades.SgpuntoNormaPK;

/**
 *
 * @author Misael
 */
public class SgpuntoNormaJpaController implements Serializable {

    public SgpuntoNormaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgpuntoNorma sgpuntoNorma) throws PreexistingEntityException, Exception {
        if (sgpuntoNorma.getSgpuntoNormaPK() == null) {
            sgpuntoNorma.setSgpuntoNormaPK(new SgpuntoNormaPK());
        }
        sgpuntoNorma.getSgpuntoNormaPK().setIdnorma(sgpuntoNorma.getSgnorma().getIdnorma());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgnorma sgnorma = sgpuntoNorma.getSgnorma();
            if (sgnorma != null) {
                sgnorma = em.getReference(sgnorma.getClass(), sgnorma.getIdnorma());
                sgpuntoNorma.setSgnorma(sgnorma);
            }
            em.persist(sgpuntoNorma);
            if (sgnorma != null) {
                sgnorma.getSgpuntoNormaList().add(sgpuntoNorma);
                sgnorma = em.merge(sgnorma);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgpuntoNorma(sgpuntoNorma.getSgpuntoNormaPK()) != null) {
                throw new PreexistingEntityException("SgpuntoNorma " + sgpuntoNorma + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgpuntoNorma sgpuntoNorma) throws NonexistentEntityException, Exception {
        sgpuntoNorma.getSgpuntoNormaPK().setIdnorma(sgpuntoNorma.getSgnorma().getIdnorma());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgpuntoNorma persistentSgpuntoNorma = em.find(SgpuntoNorma.class, sgpuntoNorma.getSgpuntoNormaPK());
            Sgnorma sgnormaOld = persistentSgpuntoNorma.getSgnorma();
            Sgnorma sgnormaNew = sgpuntoNorma.getSgnorma();
            if (sgnormaNew != null) {
                sgnormaNew = em.getReference(sgnormaNew.getClass(), sgnormaNew.getIdnorma());
                sgpuntoNorma.setSgnorma(sgnormaNew);
            }
            sgpuntoNorma = em.merge(sgpuntoNorma);
            if (sgnormaOld != null && !sgnormaOld.equals(sgnormaNew)) {
                sgnormaOld.getSgpuntoNormaList().remove(sgpuntoNorma);
                sgnormaOld = em.merge(sgnormaOld);
            }
            if (sgnormaNew != null && !sgnormaNew.equals(sgnormaOld)) {
                sgnormaNew.getSgpuntoNormaList().add(sgpuntoNorma);
                sgnormaNew = em.merge(sgnormaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgpuntoNormaPK id = sgpuntoNorma.getSgpuntoNormaPK();
                if (findSgpuntoNorma(id) == null) {
                    throw new NonexistentEntityException("The sgpuntoNorma with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgpuntoNormaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgpuntoNorma sgpuntoNorma;
            try {
                sgpuntoNorma = em.getReference(SgpuntoNorma.class, id);
                sgpuntoNorma.getSgpuntoNormaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgpuntoNorma with id " + id + " no longer exists.", enfe);
            }
            Sgnorma sgnorma = sgpuntoNorma.getSgnorma();
            if (sgnorma != null) {
                sgnorma.getSgpuntoNormaList().remove(sgpuntoNorma);
                sgnorma = em.merge(sgnorma);
            }
            em.remove(sgpuntoNorma);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgpuntoNorma> findSgpuntoNormaEntities() {
        return findSgpuntoNormaEntities(true, -1, -1);
    }

    public List<SgpuntoNorma> findSgpuntoNormaEntities(int maxResults, int firstResult) {
        return findSgpuntoNormaEntities(false, maxResults, firstResult);
    }

    private List<SgpuntoNorma> findSgpuntoNormaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgpuntoNorma.class));
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

    public SgpuntoNorma findSgpuntoNorma(SgpuntoNormaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgpuntoNorma.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgpuntoNormaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgpuntoNorma> rt = cq.from(SgpuntoNorma.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
