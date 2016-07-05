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
import sg.sistemas.entidades.SgpSeccionesEncuesta;
import sg.sistemas.entidades.SgpCabEncuesta;
import sg.sistemas.entidades.SgpEncuestaDetalle;
import sg.sistemas.entidades.SgpEncuestaDetallePK;
import sg.sistemas.entidades.SgtipoPregunta;

/**
 *
 * @author Misael
 */
public class SgpEncuestaDetalleJpaController implements Serializable {

    public SgpEncuestaDetalleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgpEncuestaDetalle sgpEncuestaDetalle) throws PreexistingEntityException, Exception {
        if (sgpEncuestaDetalle.getSgpEncuestaDetallePK() == null) {
            sgpEncuestaDetalle.setSgpEncuestaDetallePK(new SgpEncuestaDetallePK());
        }
        sgpEncuestaDetalle.getSgpEncuestaDetallePK().setIdencuesta(sgpEncuestaDetalle.getSgpCabEncuesta().getIdencuesta());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgpSeccionesEncuesta idseccion = sgpEncuestaDetalle.getIdseccion();
            if (idseccion != null) {
                idseccion = em.getReference(idseccion.getClass(), idseccion.getIdseccion());
                sgpEncuestaDetalle.setIdseccion(idseccion);
            }
            SgpCabEncuesta sgpCabEncuesta = sgpEncuestaDetalle.getSgpCabEncuesta();
            if (sgpCabEncuesta != null) {
                sgpCabEncuesta = em.getReference(sgpCabEncuesta.getClass(), sgpCabEncuesta.getIdencuesta());
                sgpEncuestaDetalle.setSgpCabEncuesta(sgpCabEncuesta);
            }
            SgtipoPregunta idtipoPregunta = sgpEncuestaDetalle.getIdtipoPregunta();
            if (idtipoPregunta != null) {
                idtipoPregunta = em.getReference(idtipoPregunta.getClass(), idtipoPregunta.getIdtipoPregunta());
                sgpEncuestaDetalle.setIdtipoPregunta(idtipoPregunta);
            }
            em.persist(sgpEncuestaDetalle);
            if (idseccion != null) {
                idseccion.getSgpEncuestaDetalleList().add(sgpEncuestaDetalle);
                idseccion = em.merge(idseccion);
            }
            if (sgpCabEncuesta != null) {
                sgpCabEncuesta.getSgpEncuestaDetalleList().add(sgpEncuestaDetalle);
                sgpCabEncuesta = em.merge(sgpCabEncuesta);
            }
            if (idtipoPregunta != null) {
                idtipoPregunta.getSgpEncuestaDetalleList().add(sgpEncuestaDetalle);
                idtipoPregunta = em.merge(idtipoPregunta);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgpEncuestaDetalle(sgpEncuestaDetalle.getSgpEncuestaDetallePK()) != null) {
                throw new PreexistingEntityException("SgpEncuestaDetalle " + sgpEncuestaDetalle + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgpEncuestaDetalle sgpEncuestaDetalle) throws NonexistentEntityException, Exception {
        sgpEncuestaDetalle.getSgpEncuestaDetallePK().setIdencuesta(sgpEncuestaDetalle.getSgpCabEncuesta().getIdencuesta());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgpEncuestaDetalle persistentSgpEncuestaDetalle = em.find(SgpEncuestaDetalle.class, sgpEncuestaDetalle.getSgpEncuestaDetallePK());
            SgpSeccionesEncuesta idseccionOld = persistentSgpEncuestaDetalle.getIdseccion();
            SgpSeccionesEncuesta idseccionNew = sgpEncuestaDetalle.getIdseccion();
            SgpCabEncuesta sgpCabEncuestaOld = persistentSgpEncuestaDetalle.getSgpCabEncuesta();
            SgpCabEncuesta sgpCabEncuestaNew = sgpEncuestaDetalle.getSgpCabEncuesta();
            SgtipoPregunta idtipoPreguntaOld = persistentSgpEncuestaDetalle.getIdtipoPregunta();
            SgtipoPregunta idtipoPreguntaNew = sgpEncuestaDetalle.getIdtipoPregunta();
            if (idseccionNew != null) {
                idseccionNew = em.getReference(idseccionNew.getClass(), idseccionNew.getIdseccion());
                sgpEncuestaDetalle.setIdseccion(idseccionNew);
            }
            if (sgpCabEncuestaNew != null) {
                sgpCabEncuestaNew = em.getReference(sgpCabEncuestaNew.getClass(), sgpCabEncuestaNew.getIdencuesta());
                sgpEncuestaDetalle.setSgpCabEncuesta(sgpCabEncuestaNew);
            }
            if (idtipoPreguntaNew != null) {
                idtipoPreguntaNew = em.getReference(idtipoPreguntaNew.getClass(), idtipoPreguntaNew.getIdtipoPregunta());
                sgpEncuestaDetalle.setIdtipoPregunta(idtipoPreguntaNew);
            }
            sgpEncuestaDetalle = em.merge(sgpEncuestaDetalle);
            if (idseccionOld != null && !idseccionOld.equals(idseccionNew)) {
                idseccionOld.getSgpEncuestaDetalleList().remove(sgpEncuestaDetalle);
                idseccionOld = em.merge(idseccionOld);
            }
            if (idseccionNew != null && !idseccionNew.equals(idseccionOld)) {
                idseccionNew.getSgpEncuestaDetalleList().add(sgpEncuestaDetalle);
                idseccionNew = em.merge(idseccionNew);
            }
            if (sgpCabEncuestaOld != null && !sgpCabEncuestaOld.equals(sgpCabEncuestaNew)) {
                sgpCabEncuestaOld.getSgpEncuestaDetalleList().remove(sgpEncuestaDetalle);
                sgpCabEncuestaOld = em.merge(sgpCabEncuestaOld);
            }
            if (sgpCabEncuestaNew != null && !sgpCabEncuestaNew.equals(sgpCabEncuestaOld)) {
                sgpCabEncuestaNew.getSgpEncuestaDetalleList().add(sgpEncuestaDetalle);
                sgpCabEncuestaNew = em.merge(sgpCabEncuestaNew);
            }
            if (idtipoPreguntaOld != null && !idtipoPreguntaOld.equals(idtipoPreguntaNew)) {
                idtipoPreguntaOld.getSgpEncuestaDetalleList().remove(sgpEncuestaDetalle);
                idtipoPreguntaOld = em.merge(idtipoPreguntaOld);
            }
            if (idtipoPreguntaNew != null && !idtipoPreguntaNew.equals(idtipoPreguntaOld)) {
                idtipoPreguntaNew.getSgpEncuestaDetalleList().add(sgpEncuestaDetalle);
                idtipoPreguntaNew = em.merge(idtipoPreguntaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgpEncuestaDetallePK id = sgpEncuestaDetalle.getSgpEncuestaDetallePK();
                if (findSgpEncuestaDetalle(id) == null) {
                    throw new NonexistentEntityException("The sgpEncuestaDetalle with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgpEncuestaDetallePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgpEncuestaDetalle sgpEncuestaDetalle;
            try {
                sgpEncuestaDetalle = em.getReference(SgpEncuestaDetalle.class, id);
                sgpEncuestaDetalle.getSgpEncuestaDetallePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgpEncuestaDetalle with id " + id + " no longer exists.", enfe);
            }
            SgpSeccionesEncuesta idseccion = sgpEncuestaDetalle.getIdseccion();
            if (idseccion != null) {
                idseccion.getSgpEncuestaDetalleList().remove(sgpEncuestaDetalle);
                idseccion = em.merge(idseccion);
            }
            SgpCabEncuesta sgpCabEncuesta = sgpEncuestaDetalle.getSgpCabEncuesta();
            if (sgpCabEncuesta != null) {
                sgpCabEncuesta.getSgpEncuestaDetalleList().remove(sgpEncuestaDetalle);
                sgpCabEncuesta = em.merge(sgpCabEncuesta);
            }
            SgtipoPregunta idtipoPregunta = sgpEncuestaDetalle.getIdtipoPregunta();
            if (idtipoPregunta != null) {
                idtipoPregunta.getSgpEncuestaDetalleList().remove(sgpEncuestaDetalle);
                idtipoPregunta = em.merge(idtipoPregunta);
            }
            em.remove(sgpEncuestaDetalle);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgpEncuestaDetalle> findSgpEncuestaDetalleEntities() {
        return findSgpEncuestaDetalleEntities(true, -1, -1);
    }

    public List<SgpEncuestaDetalle> findSgpEncuestaDetalleEntities(int maxResults, int firstResult) {
        return findSgpEncuestaDetalleEntities(false, maxResults, firstResult);
    }

    private List<SgpEncuestaDetalle> findSgpEncuestaDetalleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgpEncuestaDetalle.class));
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

    public SgpEncuestaDetalle findSgpEncuestaDetalle(SgpEncuestaDetallePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgpEncuestaDetalle.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgpEncuestaDetalleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgpEncuestaDetalle> rt = cq.from(SgpEncuestaDetalle.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
