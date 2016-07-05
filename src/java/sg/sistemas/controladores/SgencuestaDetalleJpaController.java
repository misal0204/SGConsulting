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
import sg.sistemas.entidades.SgcabEncuesta;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgencuestaDetalle;
import sg.sistemas.entidades.SgencuestaDetallePK;

/**
 *
 * @author Misael
 */
public class SgencuestaDetalleJpaController implements Serializable {

    public SgencuestaDetalleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgencuestaDetalle sgencuestaDetalle) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (sgencuestaDetalle.getSgencuestaDetallePK() == null) {
            sgencuestaDetalle.setSgencuestaDetallePK(new SgencuestaDetallePK());
        }
        sgencuestaDetalle.getSgencuestaDetallePK().setNoenc(sgencuestaDetalle.getSgcabEncuesta().getSgcabEncuestaPK().getNoenc());
        sgencuestaDetalle.getSgencuestaDetallePK().setIdsociedad(sgencuestaDetalle.getSgcabEncuesta().getSgcabEncuestaPK().getIdsociedad());
        List<String> illegalOrphanMessages = null;
        SgcabEncuesta sgcabEncuestaOrphanCheck = sgencuestaDetalle.getSgcabEncuesta();
        if (sgcabEncuestaOrphanCheck != null) {
            SgencuestaDetalle oldSgencuestaDetalleOfSgcabEncuesta = sgcabEncuestaOrphanCheck.getSgencuestaDetalle();
            if (oldSgencuestaDetalleOfSgcabEncuesta != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The SgcabEncuesta " + sgcabEncuestaOrphanCheck + " already has an item of type SgencuestaDetalle whose sgcabEncuesta column cannot be null. Please make another selection for the sgcabEncuesta field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgcabEncuesta sgcabEncuesta = sgencuestaDetalle.getSgcabEncuesta();
            if (sgcabEncuesta != null) {
                sgcabEncuesta = em.getReference(sgcabEncuesta.getClass(), sgcabEncuesta.getSgcabEncuestaPK());
                sgencuestaDetalle.setSgcabEncuesta(sgcabEncuesta);
            }
            em.persist(sgencuestaDetalle);
            if (sgcabEncuesta != null) {
                sgcabEncuesta.setSgencuestaDetalle(sgencuestaDetalle);
                sgcabEncuesta = em.merge(sgcabEncuesta);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgencuestaDetalle(sgencuestaDetalle.getSgencuestaDetallePK()) != null) {
                throw new PreexistingEntityException("SgencuestaDetalle " + sgencuestaDetalle + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgencuestaDetalle sgencuestaDetalle) throws IllegalOrphanException, NonexistentEntityException, Exception {
        sgencuestaDetalle.getSgencuestaDetallePK().setNoenc(sgencuestaDetalle.getSgcabEncuesta().getSgcabEncuestaPK().getNoenc());
        sgencuestaDetalle.getSgencuestaDetallePK().setIdsociedad(sgencuestaDetalle.getSgcabEncuesta().getSgcabEncuestaPK().getIdsociedad());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgencuestaDetalle persistentSgencuestaDetalle = em.find(SgencuestaDetalle.class, sgencuestaDetalle.getSgencuestaDetallePK());
            SgcabEncuesta sgcabEncuestaOld = persistentSgencuestaDetalle.getSgcabEncuesta();
            SgcabEncuesta sgcabEncuestaNew = sgencuestaDetalle.getSgcabEncuesta();
            List<String> illegalOrphanMessages = null;
            if (sgcabEncuestaNew != null && !sgcabEncuestaNew.equals(sgcabEncuestaOld)) {
                SgencuestaDetalle oldSgencuestaDetalleOfSgcabEncuesta = sgcabEncuestaNew.getSgencuestaDetalle();
                if (oldSgencuestaDetalleOfSgcabEncuesta != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The SgcabEncuesta " + sgcabEncuestaNew + " already has an item of type SgencuestaDetalle whose sgcabEncuesta column cannot be null. Please make another selection for the sgcabEncuesta field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sgcabEncuestaNew != null) {
                sgcabEncuestaNew = em.getReference(sgcabEncuestaNew.getClass(), sgcabEncuestaNew.getSgcabEncuestaPK());
                sgencuestaDetalle.setSgcabEncuesta(sgcabEncuestaNew);
            }
            sgencuestaDetalle = em.merge(sgencuestaDetalle);
            if (sgcabEncuestaOld != null && !sgcabEncuestaOld.equals(sgcabEncuestaNew)) {
                sgcabEncuestaOld.setSgencuestaDetalle(null);
                sgcabEncuestaOld = em.merge(sgcabEncuestaOld);
            }
            if (sgcabEncuestaNew != null && !sgcabEncuestaNew.equals(sgcabEncuestaOld)) {
                sgcabEncuestaNew.setSgencuestaDetalle(sgencuestaDetalle);
                sgcabEncuestaNew = em.merge(sgcabEncuestaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgencuestaDetallePK id = sgencuestaDetalle.getSgencuestaDetallePK();
                if (findSgencuestaDetalle(id) == null) {
                    throw new NonexistentEntityException("The sgencuestaDetalle with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgencuestaDetallePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgencuestaDetalle sgencuestaDetalle;
            try {
                sgencuestaDetalle = em.getReference(SgencuestaDetalle.class, id);
                sgencuestaDetalle.getSgencuestaDetallePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgencuestaDetalle with id " + id + " no longer exists.", enfe);
            }
            SgcabEncuesta sgcabEncuesta = sgencuestaDetalle.getSgcabEncuesta();
            if (sgcabEncuesta != null) {
                sgcabEncuesta.setSgencuestaDetalle(null);
                sgcabEncuesta = em.merge(sgcabEncuesta);
            }
            em.remove(sgencuestaDetalle);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgencuestaDetalle> findSgencuestaDetalleEntities() {
        return findSgencuestaDetalleEntities(true, -1, -1);
    }

    public List<SgencuestaDetalle> findSgencuestaDetalleEntities(int maxResults, int firstResult) {
        return findSgencuestaDetalleEntities(false, maxResults, firstResult);
    }

    private List<SgencuestaDetalle> findSgencuestaDetalleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgencuestaDetalle.class));
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

    public SgencuestaDetalle findSgencuestaDetalle(SgencuestaDetallePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgencuestaDetalle.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgencuestaDetalleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgencuestaDetalle> rt = cq.from(SgencuestaDetalle.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
