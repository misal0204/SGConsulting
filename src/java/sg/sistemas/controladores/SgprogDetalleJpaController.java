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
import sg.sistemas.entidades.SgplanPrograma;
import sg.sistemas.entidades.SgprogDetalle;
import sg.sistemas.entidades.SgprogDetallePK;

/**
 *
 * @author Misael
 */
public class SgprogDetalleJpaController implements Serializable {

    public SgprogDetalleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgprogDetalle sgprogDetalle) throws PreexistingEntityException, Exception {
        if (sgprogDetalle.getSgprogDetallePK() == null) {
            sgprogDetalle.setSgprogDetallePK(new SgprogDetallePK());
        }
        sgprogDetalle.getSgprogDetallePK().setIdplanProg(sgprogDetalle.getSgplanPrograma().getSgplanProgramaPK().getIdplanProg());
        sgprogDetalle.getSgprogDetallePK().setIdsociedad(sgprogDetalle.getSgplanPrograma().getSgplanProgramaPK().getIdsociedad());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgplanPrograma sgplanPrograma = sgprogDetalle.getSgplanPrograma();
            if (sgplanPrograma != null) {
                sgplanPrograma = em.getReference(sgplanPrograma.getClass(), sgplanPrograma.getSgplanProgramaPK());
                sgprogDetalle.setSgplanPrograma(sgplanPrograma);
            }
            em.persist(sgprogDetalle);
            if (sgplanPrograma != null) {
                sgplanPrograma.getSgprogDetalleList().add(sgprogDetalle);
                sgplanPrograma = em.merge(sgplanPrograma);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgprogDetalle(sgprogDetalle.getSgprogDetallePK()) != null) {
                throw new PreexistingEntityException("SgprogDetalle " + sgprogDetalle + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgprogDetalle sgprogDetalle) throws NonexistentEntityException, Exception {
        sgprogDetalle.getSgprogDetallePK().setIdplanProg(sgprogDetalle.getSgplanPrograma().getSgplanProgramaPK().getIdplanProg());
        sgprogDetalle.getSgprogDetallePK().setIdsociedad(sgprogDetalle.getSgplanPrograma().getSgplanProgramaPK().getIdsociedad());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgprogDetalle persistentSgprogDetalle = em.find(SgprogDetalle.class, sgprogDetalle.getSgprogDetallePK());
            SgplanPrograma sgplanProgramaOld = persistentSgprogDetalle.getSgplanPrograma();
            SgplanPrograma sgplanProgramaNew = sgprogDetalle.getSgplanPrograma();
            if (sgplanProgramaNew != null) {
                sgplanProgramaNew = em.getReference(sgplanProgramaNew.getClass(), sgplanProgramaNew.getSgplanProgramaPK());
                sgprogDetalle.setSgplanPrograma(sgplanProgramaNew);
            }
            sgprogDetalle = em.merge(sgprogDetalle);
            if (sgplanProgramaOld != null && !sgplanProgramaOld.equals(sgplanProgramaNew)) {
                sgplanProgramaOld.getSgprogDetalleList().remove(sgprogDetalle);
                sgplanProgramaOld = em.merge(sgplanProgramaOld);
            }
            if (sgplanProgramaNew != null && !sgplanProgramaNew.equals(sgplanProgramaOld)) {
                sgplanProgramaNew.getSgprogDetalleList().add(sgprogDetalle);
                sgplanProgramaNew = em.merge(sgplanProgramaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgprogDetallePK id = sgprogDetalle.getSgprogDetallePK();
                if (findSgprogDetalle(id) == null) {
                    throw new NonexistentEntityException("The sgprogDetalle with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgprogDetallePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgprogDetalle sgprogDetalle;
            try {
                sgprogDetalle = em.getReference(SgprogDetalle.class, id);
                sgprogDetalle.getSgprogDetallePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgprogDetalle with id " + id + " no longer exists.", enfe);
            }
            SgplanPrograma sgplanPrograma = sgprogDetalle.getSgplanPrograma();
            if (sgplanPrograma != null) {
                sgplanPrograma.getSgprogDetalleList().remove(sgprogDetalle);
                sgplanPrograma = em.merge(sgplanPrograma);
            }
            em.remove(sgprogDetalle);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgprogDetalle> findSgprogDetalleEntities() {
        return findSgprogDetalleEntities(true, -1, -1);
    }

    public List<SgprogDetalle> findSgprogDetalleEntities(int maxResults, int firstResult) {
        return findSgprogDetalleEntities(false, maxResults, firstResult);
    }

    private List<SgprogDetalle> findSgprogDetalleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgprogDetalle.class));
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

    public SgprogDetalle findSgprogDetalle(SgprogDetallePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgprogDetalle.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgprogDetalleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgprogDetalle> rt = cq.from(SgprogDetalle.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
