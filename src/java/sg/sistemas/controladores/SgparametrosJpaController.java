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
import sg.sistemas.entidades.Sgparametros;
import sg.sistemas.entidades.SgparametrosPK;
import sg.sistemas.entidades.Sgsociedad;

/**
 *
 * @author Misael
 */
public class SgparametrosJpaController implements Serializable {

    public SgparametrosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sgparametros sgparametros) throws PreexistingEntityException, Exception {
        if (sgparametros.getSgparametrosPK() == null) {
            sgparametros.setSgparametrosPK(new SgparametrosPK());
        }
        sgparametros.getSgparametrosPK().setIdsociedad(sgparametros.getSgsociedad().getIdsociedad());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgsociedad sgsociedad = sgparametros.getSgsociedad();
            if (sgsociedad != null) {
                sgsociedad = em.getReference(sgsociedad.getClass(), sgsociedad.getIdsociedad());
                sgparametros.setSgsociedad(sgsociedad);
            }
            em.persist(sgparametros);
            if (sgsociedad != null) {
                sgsociedad.getSgparametrosList().add(sgparametros);
                sgsociedad = em.merge(sgsociedad);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgparametros(sgparametros.getSgparametrosPK()) != null) {
                throw new PreexistingEntityException("Sgparametros " + sgparametros + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sgparametros sgparametros) throws NonexistentEntityException, Exception {
        sgparametros.getSgparametrosPK().setIdsociedad(sgparametros.getSgsociedad().getIdsociedad());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgparametros persistentSgparametros = em.find(Sgparametros.class, sgparametros.getSgparametrosPK());
            Sgsociedad sgsociedadOld = persistentSgparametros.getSgsociedad();
            Sgsociedad sgsociedadNew = sgparametros.getSgsociedad();
            if (sgsociedadNew != null) {
                sgsociedadNew = em.getReference(sgsociedadNew.getClass(), sgsociedadNew.getIdsociedad());
                sgparametros.setSgsociedad(sgsociedadNew);
            }
            sgparametros = em.merge(sgparametros);
            if (sgsociedadOld != null && !sgsociedadOld.equals(sgsociedadNew)) {
                sgsociedadOld.getSgparametrosList().remove(sgparametros);
                sgsociedadOld = em.merge(sgsociedadOld);
            }
            if (sgsociedadNew != null && !sgsociedadNew.equals(sgsociedadOld)) {
                sgsociedadNew.getSgparametrosList().add(sgparametros);
                sgsociedadNew = em.merge(sgsociedadNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgparametrosPK id = sgparametros.getSgparametrosPK();
                if (findSgparametros(id) == null) {
                    throw new NonexistentEntityException("The sgparametros with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgparametrosPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgparametros sgparametros;
            try {
                sgparametros = em.getReference(Sgparametros.class, id);
                sgparametros.getSgparametrosPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgparametros with id " + id + " no longer exists.", enfe);
            }
            Sgsociedad sgsociedad = sgparametros.getSgsociedad();
            if (sgsociedad != null) {
                sgsociedad.getSgparametrosList().remove(sgparametros);
                sgsociedad = em.merge(sgsociedad);
            }
            em.remove(sgparametros);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sgparametros> findSgparametrosEntities() {
        return findSgparametrosEntities(true, -1, -1);
    }

    public List<Sgparametros> findSgparametrosEntities(int maxResults, int firstResult) {
        return findSgparametrosEntities(false, maxResults, firstResult);
    }

    private List<Sgparametros> findSgparametrosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sgparametros.class));
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

    public Sgparametros findSgparametros(SgparametrosPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sgparametros.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgparametrosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sgparametros> rt = cq.from(Sgparametros.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
