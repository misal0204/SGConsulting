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
import sg.sistemas.entidades.SgtipoPregunta;

/**
 *
 * @author Misael
 */
public class SgtipoPreguntaJpaController implements Serializable {

    public SgtipoPreguntaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgtipoPregunta sgtipoPregunta) throws PreexistingEntityException, Exception {
        if (sgtipoPregunta.getSgpEncuestaDetalleList() == null) {
            sgtipoPregunta.setSgpEncuestaDetalleList(new ArrayList<SgpEncuestaDetalle>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SgpEncuestaDetalle> attachedSgpEncuestaDetalleList = new ArrayList<SgpEncuestaDetalle>();
            for (SgpEncuestaDetalle sgpEncuestaDetalleListSgpEncuestaDetalleToAttach : sgtipoPregunta.getSgpEncuestaDetalleList()) {
                sgpEncuestaDetalleListSgpEncuestaDetalleToAttach = em.getReference(sgpEncuestaDetalleListSgpEncuestaDetalleToAttach.getClass(), sgpEncuestaDetalleListSgpEncuestaDetalleToAttach.getSgpEncuestaDetallePK());
                attachedSgpEncuestaDetalleList.add(sgpEncuestaDetalleListSgpEncuestaDetalleToAttach);
            }
            sgtipoPregunta.setSgpEncuestaDetalleList(attachedSgpEncuestaDetalleList);
            em.persist(sgtipoPregunta);
            for (SgpEncuestaDetalle sgpEncuestaDetalleListSgpEncuestaDetalle : sgtipoPregunta.getSgpEncuestaDetalleList()) {
                SgtipoPregunta oldIdtipoPreguntaOfSgpEncuestaDetalleListSgpEncuestaDetalle = sgpEncuestaDetalleListSgpEncuestaDetalle.getIdtipoPregunta();
                sgpEncuestaDetalleListSgpEncuestaDetalle.setIdtipoPregunta(sgtipoPregunta);
                sgpEncuestaDetalleListSgpEncuestaDetalle = em.merge(sgpEncuestaDetalleListSgpEncuestaDetalle);
                if (oldIdtipoPreguntaOfSgpEncuestaDetalleListSgpEncuestaDetalle != null) {
                    oldIdtipoPreguntaOfSgpEncuestaDetalleListSgpEncuestaDetalle.getSgpEncuestaDetalleList().remove(sgpEncuestaDetalleListSgpEncuestaDetalle);
                    oldIdtipoPreguntaOfSgpEncuestaDetalleListSgpEncuestaDetalle = em.merge(oldIdtipoPreguntaOfSgpEncuestaDetalleListSgpEncuestaDetalle);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgtipoPregunta(sgtipoPregunta.getIdtipoPregunta()) != null) {
                throw new PreexistingEntityException("SgtipoPregunta " + sgtipoPregunta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgtipoPregunta sgtipoPregunta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgtipoPregunta persistentSgtipoPregunta = em.find(SgtipoPregunta.class, sgtipoPregunta.getIdtipoPregunta());
            List<SgpEncuestaDetalle> sgpEncuestaDetalleListOld = persistentSgtipoPregunta.getSgpEncuestaDetalleList();
            List<SgpEncuestaDetalle> sgpEncuestaDetalleListNew = sgtipoPregunta.getSgpEncuestaDetalleList();
            List<SgpEncuestaDetalle> attachedSgpEncuestaDetalleListNew = new ArrayList<SgpEncuestaDetalle>();
            for (SgpEncuestaDetalle sgpEncuestaDetalleListNewSgpEncuestaDetalleToAttach : sgpEncuestaDetalleListNew) {
                sgpEncuestaDetalleListNewSgpEncuestaDetalleToAttach = em.getReference(sgpEncuestaDetalleListNewSgpEncuestaDetalleToAttach.getClass(), sgpEncuestaDetalleListNewSgpEncuestaDetalleToAttach.getSgpEncuestaDetallePK());
                attachedSgpEncuestaDetalleListNew.add(sgpEncuestaDetalleListNewSgpEncuestaDetalleToAttach);
            }
            sgpEncuestaDetalleListNew = attachedSgpEncuestaDetalleListNew;
            sgtipoPregunta.setSgpEncuestaDetalleList(sgpEncuestaDetalleListNew);
            sgtipoPregunta = em.merge(sgtipoPregunta);
            for (SgpEncuestaDetalle sgpEncuestaDetalleListOldSgpEncuestaDetalle : sgpEncuestaDetalleListOld) {
                if (!sgpEncuestaDetalleListNew.contains(sgpEncuestaDetalleListOldSgpEncuestaDetalle)) {
                    sgpEncuestaDetalleListOldSgpEncuestaDetalle.setIdtipoPregunta(null);
                    sgpEncuestaDetalleListOldSgpEncuestaDetalle = em.merge(sgpEncuestaDetalleListOldSgpEncuestaDetalle);
                }
            }
            for (SgpEncuestaDetalle sgpEncuestaDetalleListNewSgpEncuestaDetalle : sgpEncuestaDetalleListNew) {
                if (!sgpEncuestaDetalleListOld.contains(sgpEncuestaDetalleListNewSgpEncuestaDetalle)) {
                    SgtipoPregunta oldIdtipoPreguntaOfSgpEncuestaDetalleListNewSgpEncuestaDetalle = sgpEncuestaDetalleListNewSgpEncuestaDetalle.getIdtipoPregunta();
                    sgpEncuestaDetalleListNewSgpEncuestaDetalle.setIdtipoPregunta(sgtipoPregunta);
                    sgpEncuestaDetalleListNewSgpEncuestaDetalle = em.merge(sgpEncuestaDetalleListNewSgpEncuestaDetalle);
                    if (oldIdtipoPreguntaOfSgpEncuestaDetalleListNewSgpEncuestaDetalle != null && !oldIdtipoPreguntaOfSgpEncuestaDetalleListNewSgpEncuestaDetalle.equals(sgtipoPregunta)) {
                        oldIdtipoPreguntaOfSgpEncuestaDetalleListNewSgpEncuestaDetalle.getSgpEncuestaDetalleList().remove(sgpEncuestaDetalleListNewSgpEncuestaDetalle);
                        oldIdtipoPreguntaOfSgpEncuestaDetalleListNewSgpEncuestaDetalle = em.merge(oldIdtipoPreguntaOfSgpEncuestaDetalleListNewSgpEncuestaDetalle);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Double id = sgtipoPregunta.getIdtipoPregunta();
                if (findSgtipoPregunta(id) == null) {
                    throw new NonexistentEntityException("The sgtipoPregunta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Double id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgtipoPregunta sgtipoPregunta;
            try {
                sgtipoPregunta = em.getReference(SgtipoPregunta.class, id);
                sgtipoPregunta.getIdtipoPregunta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgtipoPregunta with id " + id + " no longer exists.", enfe);
            }
            List<SgpEncuestaDetalle> sgpEncuestaDetalleList = sgtipoPregunta.getSgpEncuestaDetalleList();
            for (SgpEncuestaDetalle sgpEncuestaDetalleListSgpEncuestaDetalle : sgpEncuestaDetalleList) {
                sgpEncuestaDetalleListSgpEncuestaDetalle.setIdtipoPregunta(null);
                sgpEncuestaDetalleListSgpEncuestaDetalle = em.merge(sgpEncuestaDetalleListSgpEncuestaDetalle);
            }
            em.remove(sgtipoPregunta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgtipoPregunta> findSgtipoPreguntaEntities() {
        return findSgtipoPreguntaEntities(true, -1, -1);
    }

    public List<SgtipoPregunta> findSgtipoPreguntaEntities(int maxResults, int firstResult) {
        return findSgtipoPreguntaEntities(false, maxResults, firstResult);
    }

    private List<SgtipoPregunta> findSgtipoPreguntaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgtipoPregunta.class));
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

    public SgtipoPregunta findSgtipoPregunta(Double id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgtipoPregunta.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgtipoPreguntaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgtipoPregunta> rt = cq.from(SgtipoPregunta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
