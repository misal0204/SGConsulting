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
import sg.sistemas.entidades.Sgsociedad;
import sg.sistemas.entidades.Sgtareas;
import sg.sistemas.entidades.SgtareasPK;
import sg.sistemas.entidades.Sgttarea;

/**
 *
 * @author Misael
 */
public class SgtareasJpaController implements Serializable {

    public SgtareasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sgtareas sgtareas) throws PreexistingEntityException, Exception {
        if (sgtareas.getSgtareasPK() == null) {
            sgtareas.setSgtareasPK(new SgtareasPK());
        }
        sgtareas.getSgtareasPK().setIdsociedad(sgtareas.getSgsociedad().getIdsociedad());
        sgtareas.getSgtareasPK().setIdtarea(sgtareas.getSgttarea().getIdtarea());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgsociedad sgsociedad = sgtareas.getSgsociedad();
            if (sgsociedad != null) {
                sgsociedad = em.getReference(sgsociedad.getClass(), sgsociedad.getIdsociedad());
                sgtareas.setSgsociedad(sgsociedad);
            }
            Sgttarea sgttarea = sgtareas.getSgttarea();
            if (sgttarea != null) {
                sgttarea = em.getReference(sgttarea.getClass(), sgttarea.getIdtarea());
                sgtareas.setSgttarea(sgttarea);
            }
            em.persist(sgtareas);
            if (sgsociedad != null) {
                sgsociedad.getSgtareasList().add(sgtareas);
                sgsociedad = em.merge(sgsociedad);
            }
            if (sgttarea != null) {
                sgttarea.getSgtareasList().add(sgtareas);
                sgttarea = em.merge(sgttarea);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgtareas(sgtareas.getSgtareasPK()) != null) {
                throw new PreexistingEntityException("Sgtareas " + sgtareas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sgtareas sgtareas) throws NonexistentEntityException, Exception {
        sgtareas.getSgtareasPK().setIdsociedad(sgtareas.getSgsociedad().getIdsociedad());
        sgtareas.getSgtareasPK().setIdtarea(sgtareas.getSgttarea().getIdtarea());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgtareas persistentSgtareas = em.find(Sgtareas.class, sgtareas.getSgtareasPK());
            Sgsociedad sgsociedadOld = persistentSgtareas.getSgsociedad();
            Sgsociedad sgsociedadNew = sgtareas.getSgsociedad();
            Sgttarea sgttareaOld = persistentSgtareas.getSgttarea();
            Sgttarea sgttareaNew = sgtareas.getSgttarea();
            if (sgsociedadNew != null) {
                sgsociedadNew = em.getReference(sgsociedadNew.getClass(), sgsociedadNew.getIdsociedad());
                sgtareas.setSgsociedad(sgsociedadNew);
            }
            if (sgttareaNew != null) {
                sgttareaNew = em.getReference(sgttareaNew.getClass(), sgttareaNew.getIdtarea());
                sgtareas.setSgttarea(sgttareaNew);
            }
            sgtareas = em.merge(sgtareas);
            if (sgsociedadOld != null && !sgsociedadOld.equals(sgsociedadNew)) {
                sgsociedadOld.getSgtareasList().remove(sgtareas);
                sgsociedadOld = em.merge(sgsociedadOld);
            }
            if (sgsociedadNew != null && !sgsociedadNew.equals(sgsociedadOld)) {
                sgsociedadNew.getSgtareasList().add(sgtareas);
                sgsociedadNew = em.merge(sgsociedadNew);
            }
            if (sgttareaOld != null && !sgttareaOld.equals(sgttareaNew)) {
                sgttareaOld.getSgtareasList().remove(sgtareas);
                sgttareaOld = em.merge(sgttareaOld);
            }
            if (sgttareaNew != null && !sgttareaNew.equals(sgttareaOld)) {
                sgttareaNew.getSgtareasList().add(sgtareas);
                sgttareaNew = em.merge(sgttareaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgtareasPK id = sgtareas.getSgtareasPK();
                if (findSgtareas(id) == null) {
                    throw new NonexistentEntityException("The sgtareas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgtareasPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgtareas sgtareas;
            try {
                sgtareas = em.getReference(Sgtareas.class, id);
                sgtareas.getSgtareasPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgtareas with id " + id + " no longer exists.", enfe);
            }
            Sgsociedad sgsociedad = sgtareas.getSgsociedad();
            if (sgsociedad != null) {
                sgsociedad.getSgtareasList().remove(sgtareas);
                sgsociedad = em.merge(sgsociedad);
            }
            Sgttarea sgttarea = sgtareas.getSgttarea();
            if (sgttarea != null) {
                sgttarea.getSgtareasList().remove(sgtareas);
                sgttarea = em.merge(sgttarea);
            }
            em.remove(sgtareas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sgtareas> findSgtareasEntities() {
        return findSgtareasEntities(true, -1, -1);
    }

    public List<Sgtareas> findSgtareasEntities(int maxResults, int firstResult) {
        return findSgtareasEntities(false, maxResults, firstResult);
    }

    private List<Sgtareas> findSgtareasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sgtareas.class));
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

    public Sgtareas findSgtareas(SgtareasPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sgtareas.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgtareasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sgtareas> rt = cq.from(Sgtareas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
