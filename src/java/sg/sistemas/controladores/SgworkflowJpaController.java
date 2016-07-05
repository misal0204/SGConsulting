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
import sg.sistemas.entidades.SgprocesoDetalle;
import sg.sistemas.entidades.Sgworkflow;
import sg.sistemas.entidades.SgworkflowPK;

/**
 *
 * @author Misael
 */
public class SgworkflowJpaController implements Serializable {

    public SgworkflowJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sgworkflow sgworkflow) throws PreexistingEntityException, Exception {
        if (sgworkflow.getSgworkflowPK() == null) {
            sgworkflow.setSgworkflowPK(new SgworkflowPK());
        }
        sgworkflow.getSgworkflowPK().setSubproceso(sgworkflow.getSgprocesoDetalle().getSgprocesoDetallePK().getSubproceso());
        sgworkflow.getSgworkflowPK().setIdprocesos(sgworkflow.getSgprocesoDetalle().getSgprocesoDetallePK().getIdprocesos());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgprocesoDetalle sgprocesoDetalle = sgworkflow.getSgprocesoDetalle();
            if (sgprocesoDetalle != null) {
                sgprocesoDetalle = em.getReference(sgprocesoDetalle.getClass(), sgprocesoDetalle.getSgprocesoDetallePK());
                sgworkflow.setSgprocesoDetalle(sgprocesoDetalle);
            }
            em.persist(sgworkflow);
            if (sgprocesoDetalle != null) {
                sgprocesoDetalle.getSgworkflowList().add(sgworkflow);
                sgprocesoDetalle = em.merge(sgprocesoDetalle);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgworkflow(sgworkflow.getSgworkflowPK()) != null) {
                throw new PreexistingEntityException("Sgworkflow " + sgworkflow + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sgworkflow sgworkflow) throws NonexistentEntityException, Exception {
        sgworkflow.getSgworkflowPK().setSubproceso(sgworkflow.getSgprocesoDetalle().getSgprocesoDetallePK().getSubproceso());
        sgworkflow.getSgworkflowPK().setIdprocesos(sgworkflow.getSgprocesoDetalle().getSgprocesoDetallePK().getIdprocesos());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgworkflow persistentSgworkflow = em.find(Sgworkflow.class, sgworkflow.getSgworkflowPK());
            SgprocesoDetalle sgprocesoDetalleOld = persistentSgworkflow.getSgprocesoDetalle();
            SgprocesoDetalle sgprocesoDetalleNew = sgworkflow.getSgprocesoDetalle();
            if (sgprocesoDetalleNew != null) {
                sgprocesoDetalleNew = em.getReference(sgprocesoDetalleNew.getClass(), sgprocesoDetalleNew.getSgprocesoDetallePK());
                sgworkflow.setSgprocesoDetalle(sgprocesoDetalleNew);
            }
            sgworkflow = em.merge(sgworkflow);
            if (sgprocesoDetalleOld != null && !sgprocesoDetalleOld.equals(sgprocesoDetalleNew)) {
                sgprocesoDetalleOld.getSgworkflowList().remove(sgworkflow);
                sgprocesoDetalleOld = em.merge(sgprocesoDetalleOld);
            }
            if (sgprocesoDetalleNew != null && !sgprocesoDetalleNew.equals(sgprocesoDetalleOld)) {
                sgprocesoDetalleNew.getSgworkflowList().add(sgworkflow);
                sgprocesoDetalleNew = em.merge(sgprocesoDetalleNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgworkflowPK id = sgworkflow.getSgworkflowPK();
                if (findSgworkflow(id) == null) {
                    throw new NonexistentEntityException("The sgworkflow with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgworkflowPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgworkflow sgworkflow;
            try {
                sgworkflow = em.getReference(Sgworkflow.class, id);
                sgworkflow.getSgworkflowPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgworkflow with id " + id + " no longer exists.", enfe);
            }
            SgprocesoDetalle sgprocesoDetalle = sgworkflow.getSgprocesoDetalle();
            if (sgprocesoDetalle != null) {
                sgprocesoDetalle.getSgworkflowList().remove(sgworkflow);
                sgprocesoDetalle = em.merge(sgprocesoDetalle);
            }
            em.remove(sgworkflow);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sgworkflow> findSgworkflowEntities() {
        return findSgworkflowEntities(true, -1, -1);
    }

    public List<Sgworkflow> findSgworkflowEntities(int maxResults, int firstResult) {
        return findSgworkflowEntities(false, maxResults, firstResult);
    }

    private List<Sgworkflow> findSgworkflowEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sgworkflow.class));
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

    public Sgworkflow findSgworkflow(SgworkflowPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sgworkflow.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgworkflowCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sgworkflow> rt = cq.from(Sgworkflow.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
