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
import sg.sistemas.entidades.SgpEncuestaDetalle;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgpSeccionesEncuesta;

/**
 *
 * @author Misael
 */
public class SgpSeccionesEncuestaJpaController implements Serializable {

    public SgpSeccionesEncuestaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgpSeccionesEncuesta sgpSeccionesEncuesta) throws PreexistingEntityException, Exception {
        if (sgpSeccionesEncuesta.getSgpEncuestaDetalleList() == null) {
            sgpSeccionesEncuesta.setSgpEncuestaDetalleList(new ArrayList<SgpEncuestaDetalle>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SgpEncuestaDetalle> attachedSgpEncuestaDetalleList = new ArrayList<SgpEncuestaDetalle>();
            for (SgpEncuestaDetalle sgpEncuestaDetalleListSgpEncuestaDetalleToAttach : sgpSeccionesEncuesta.getSgpEncuestaDetalleList()) {
                sgpEncuestaDetalleListSgpEncuestaDetalleToAttach = em.getReference(sgpEncuestaDetalleListSgpEncuestaDetalleToAttach.getClass(), sgpEncuestaDetalleListSgpEncuestaDetalleToAttach.getSgpEncuestaDetallePK());
                attachedSgpEncuestaDetalleList.add(sgpEncuestaDetalleListSgpEncuestaDetalleToAttach);
            }
            sgpSeccionesEncuesta.setSgpEncuestaDetalleList(attachedSgpEncuestaDetalleList);
            em.persist(sgpSeccionesEncuesta);
            for (SgpEncuestaDetalle sgpEncuestaDetalleListSgpEncuestaDetalle : sgpSeccionesEncuesta.getSgpEncuestaDetalleList()) {
                SgpSeccionesEncuesta oldIdseccionOfSgpEncuestaDetalleListSgpEncuestaDetalle = sgpEncuestaDetalleListSgpEncuestaDetalle.getIdseccion();
                sgpEncuestaDetalleListSgpEncuestaDetalle.setIdseccion(sgpSeccionesEncuesta);
                sgpEncuestaDetalleListSgpEncuestaDetalle = em.merge(sgpEncuestaDetalleListSgpEncuestaDetalle);
                if (oldIdseccionOfSgpEncuestaDetalleListSgpEncuestaDetalle != null) {
                    oldIdseccionOfSgpEncuestaDetalleListSgpEncuestaDetalle.getSgpEncuestaDetalleList().remove(sgpEncuestaDetalleListSgpEncuestaDetalle);
                    oldIdseccionOfSgpEncuestaDetalleListSgpEncuestaDetalle = em.merge(oldIdseccionOfSgpEncuestaDetalleListSgpEncuestaDetalle);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgpSeccionesEncuesta(sgpSeccionesEncuesta.getIdseccion()) != null) {
                throw new PreexistingEntityException("SgpSeccionesEncuesta " + sgpSeccionesEncuesta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgpSeccionesEncuesta sgpSeccionesEncuesta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgpSeccionesEncuesta persistentSgpSeccionesEncuesta = em.find(SgpSeccionesEncuesta.class, sgpSeccionesEncuesta.getIdseccion());
            List<SgpEncuestaDetalle> sgpEncuestaDetalleListOld = persistentSgpSeccionesEncuesta.getSgpEncuestaDetalleList();
            List<SgpEncuestaDetalle> sgpEncuestaDetalleListNew = sgpSeccionesEncuesta.getSgpEncuestaDetalleList();
            List<SgpEncuestaDetalle> attachedSgpEncuestaDetalleListNew = new ArrayList<SgpEncuestaDetalle>();
            for (SgpEncuestaDetalle sgpEncuestaDetalleListNewSgpEncuestaDetalleToAttach : sgpEncuestaDetalleListNew) {
                sgpEncuestaDetalleListNewSgpEncuestaDetalleToAttach = em.getReference(sgpEncuestaDetalleListNewSgpEncuestaDetalleToAttach.getClass(), sgpEncuestaDetalleListNewSgpEncuestaDetalleToAttach.getSgpEncuestaDetallePK());
                attachedSgpEncuestaDetalleListNew.add(sgpEncuestaDetalleListNewSgpEncuestaDetalleToAttach);
            }
            sgpEncuestaDetalleListNew = attachedSgpEncuestaDetalleListNew;
            sgpSeccionesEncuesta.setSgpEncuestaDetalleList(sgpEncuestaDetalleListNew);
            sgpSeccionesEncuesta = em.merge(sgpSeccionesEncuesta);
            for (SgpEncuestaDetalle sgpEncuestaDetalleListOldSgpEncuestaDetalle : sgpEncuestaDetalleListOld) {
                if (!sgpEncuestaDetalleListNew.contains(sgpEncuestaDetalleListOldSgpEncuestaDetalle)) {
                    sgpEncuestaDetalleListOldSgpEncuestaDetalle.setIdseccion(null);
                    sgpEncuestaDetalleListOldSgpEncuestaDetalle = em.merge(sgpEncuestaDetalleListOldSgpEncuestaDetalle);
                }
            }
            for (SgpEncuestaDetalle sgpEncuestaDetalleListNewSgpEncuestaDetalle : sgpEncuestaDetalleListNew) {
                if (!sgpEncuestaDetalleListOld.contains(sgpEncuestaDetalleListNewSgpEncuestaDetalle)) {
                    SgpSeccionesEncuesta oldIdseccionOfSgpEncuestaDetalleListNewSgpEncuestaDetalle = sgpEncuestaDetalleListNewSgpEncuestaDetalle.getIdseccion();
                    sgpEncuestaDetalleListNewSgpEncuestaDetalle.setIdseccion(sgpSeccionesEncuesta);
                    sgpEncuestaDetalleListNewSgpEncuestaDetalle = em.merge(sgpEncuestaDetalleListNewSgpEncuestaDetalle);
                    if (oldIdseccionOfSgpEncuestaDetalleListNewSgpEncuestaDetalle != null && !oldIdseccionOfSgpEncuestaDetalleListNewSgpEncuestaDetalle.equals(sgpSeccionesEncuesta)) {
                        oldIdseccionOfSgpEncuestaDetalleListNewSgpEncuestaDetalle.getSgpEncuestaDetalleList().remove(sgpEncuestaDetalleListNewSgpEncuestaDetalle);
                        oldIdseccionOfSgpEncuestaDetalleListNewSgpEncuestaDetalle = em.merge(oldIdseccionOfSgpEncuestaDetalleListNewSgpEncuestaDetalle);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = sgpSeccionesEncuesta.getIdseccion();
                if (findSgpSeccionesEncuesta(id) == null) {
                    throw new NonexistentEntityException("The sgpSeccionesEncuesta with id " + id + " no longer exists.");
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
            SgpSeccionesEncuesta sgpSeccionesEncuesta;
            try {
                sgpSeccionesEncuesta = em.getReference(SgpSeccionesEncuesta.class, id);
                sgpSeccionesEncuesta.getIdseccion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgpSeccionesEncuesta with id " + id + " no longer exists.", enfe);
            }
            List<SgpEncuestaDetalle> sgpEncuestaDetalleList = sgpSeccionesEncuesta.getSgpEncuestaDetalleList();
            for (SgpEncuestaDetalle sgpEncuestaDetalleListSgpEncuestaDetalle : sgpEncuestaDetalleList) {
                sgpEncuestaDetalleListSgpEncuestaDetalle.setIdseccion(null);
                sgpEncuestaDetalleListSgpEncuestaDetalle = em.merge(sgpEncuestaDetalleListSgpEncuestaDetalle);
            }
            em.remove(sgpSeccionesEncuesta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgpSeccionesEncuesta> findSgpSeccionesEncuestaEntities() {
        return findSgpSeccionesEncuestaEntities(true, -1, -1);
    }

    public List<SgpSeccionesEncuesta> findSgpSeccionesEncuestaEntities(int maxResults, int firstResult) {
        return findSgpSeccionesEncuestaEntities(false, maxResults, firstResult);
    }

    private List<SgpSeccionesEncuesta> findSgpSeccionesEncuestaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgpSeccionesEncuesta.class));
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

    public SgpSeccionesEncuesta findSgpSeccionesEncuesta(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgpSeccionesEncuesta.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgpSeccionesEncuestaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgpSeccionesEncuesta> rt = cq.from(SgpSeccionesEncuesta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
