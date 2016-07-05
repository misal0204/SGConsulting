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
import sg.sistemas.entidades.SgprocesoDetalle;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.Sgprocesos;

/**
 *
 * @author Misael
 */
public class SgprocesosJpaController implements Serializable {

    public SgprocesosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sgprocesos sgprocesos) throws PreexistingEntityException, Exception {
        if (sgprocesos.getSgprocesoDetalleList() == null) {
            sgprocesos.setSgprocesoDetalleList(new ArrayList<SgprocesoDetalle>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SgprocesoDetalle> attachedSgprocesoDetalleList = new ArrayList<SgprocesoDetalle>();
            for (SgprocesoDetalle sgprocesoDetalleListSgprocesoDetalleToAttach : sgprocesos.getSgprocesoDetalleList()) {
                sgprocesoDetalleListSgprocesoDetalleToAttach = em.getReference(sgprocesoDetalleListSgprocesoDetalleToAttach.getClass(), sgprocesoDetalleListSgprocesoDetalleToAttach.getSgprocesoDetallePK());
                attachedSgprocesoDetalleList.add(sgprocesoDetalleListSgprocesoDetalleToAttach);
            }
            sgprocesos.setSgprocesoDetalleList(attachedSgprocesoDetalleList);
            em.persist(sgprocesos);
            for (SgprocesoDetalle sgprocesoDetalleListSgprocesoDetalle : sgprocesos.getSgprocesoDetalleList()) {
                Sgprocesos oldSgprocesosOfSgprocesoDetalleListSgprocesoDetalle = sgprocesoDetalleListSgprocesoDetalle.getSgprocesos();
                sgprocesoDetalleListSgprocesoDetalle.setSgprocesos(sgprocesos);
                sgprocesoDetalleListSgprocesoDetalle = em.merge(sgprocesoDetalleListSgprocesoDetalle);
                if (oldSgprocesosOfSgprocesoDetalleListSgprocesoDetalle != null) {
                    oldSgprocesosOfSgprocesoDetalleListSgprocesoDetalle.getSgprocesoDetalleList().remove(sgprocesoDetalleListSgprocesoDetalle);
                    oldSgprocesosOfSgprocesoDetalleListSgprocesoDetalle = em.merge(oldSgprocesosOfSgprocesoDetalleListSgprocesoDetalle);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgprocesos(sgprocesos.getIdprocesos()) != null) {
                throw new PreexistingEntityException("Sgprocesos " + sgprocesos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sgprocesos sgprocesos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgprocesos persistentSgprocesos = em.find(Sgprocesos.class, sgprocesos.getIdprocesos());
            List<SgprocesoDetalle> sgprocesoDetalleListOld = persistentSgprocesos.getSgprocesoDetalleList();
            List<SgprocesoDetalle> sgprocesoDetalleListNew = sgprocesos.getSgprocesoDetalleList();
            List<String> illegalOrphanMessages = null;
            for (SgprocesoDetalle sgprocesoDetalleListOldSgprocesoDetalle : sgprocesoDetalleListOld) {
                if (!sgprocesoDetalleListNew.contains(sgprocesoDetalleListOldSgprocesoDetalle)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SgprocesoDetalle " + sgprocesoDetalleListOldSgprocesoDetalle + " since its sgprocesos field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<SgprocesoDetalle> attachedSgprocesoDetalleListNew = new ArrayList<SgprocesoDetalle>();
            for (SgprocesoDetalle sgprocesoDetalleListNewSgprocesoDetalleToAttach : sgprocesoDetalleListNew) {
                sgprocesoDetalleListNewSgprocesoDetalleToAttach = em.getReference(sgprocesoDetalleListNewSgprocesoDetalleToAttach.getClass(), sgprocesoDetalleListNewSgprocesoDetalleToAttach.getSgprocesoDetallePK());
                attachedSgprocesoDetalleListNew.add(sgprocesoDetalleListNewSgprocesoDetalleToAttach);
            }
            sgprocesoDetalleListNew = attachedSgprocesoDetalleListNew;
            sgprocesos.setSgprocesoDetalleList(sgprocesoDetalleListNew);
            sgprocesos = em.merge(sgprocesos);
            for (SgprocesoDetalle sgprocesoDetalleListNewSgprocesoDetalle : sgprocesoDetalleListNew) {
                if (!sgprocesoDetalleListOld.contains(sgprocesoDetalleListNewSgprocesoDetalle)) {
                    Sgprocesos oldSgprocesosOfSgprocesoDetalleListNewSgprocesoDetalle = sgprocesoDetalleListNewSgprocesoDetalle.getSgprocesos();
                    sgprocesoDetalleListNewSgprocesoDetalle.setSgprocesos(sgprocesos);
                    sgprocesoDetalleListNewSgprocesoDetalle = em.merge(sgprocesoDetalleListNewSgprocesoDetalle);
                    if (oldSgprocesosOfSgprocesoDetalleListNewSgprocesoDetalle != null && !oldSgprocesosOfSgprocesoDetalleListNewSgprocesoDetalle.equals(sgprocesos)) {
                        oldSgprocesosOfSgprocesoDetalleListNewSgprocesoDetalle.getSgprocesoDetalleList().remove(sgprocesoDetalleListNewSgprocesoDetalle);
                        oldSgprocesosOfSgprocesoDetalleListNewSgprocesoDetalle = em.merge(oldSgprocesosOfSgprocesoDetalleListNewSgprocesoDetalle);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = sgprocesos.getIdprocesos();
                if (findSgprocesos(id) == null) {
                    throw new NonexistentEntityException("The sgprocesos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgprocesos sgprocesos;
            try {
                sgprocesos = em.getReference(Sgprocesos.class, id);
                sgprocesos.getIdprocesos();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgprocesos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SgprocesoDetalle> sgprocesoDetalleListOrphanCheck = sgprocesos.getSgprocesoDetalleList();
            for (SgprocesoDetalle sgprocesoDetalleListOrphanCheckSgprocesoDetalle : sgprocesoDetalleListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sgprocesos (" + sgprocesos + ") cannot be destroyed since the SgprocesoDetalle " + sgprocesoDetalleListOrphanCheckSgprocesoDetalle + " in its sgprocesoDetalleList field has a non-nullable sgprocesos field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(sgprocesos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sgprocesos> findSgprocesosEntities() {
        return findSgprocesosEntities(true, -1, -1);
    }

    public List<Sgprocesos> findSgprocesosEntities(int maxResults, int firstResult) {
        return findSgprocesosEntities(false, maxResults, firstResult);
    }

    private List<Sgprocesos> findSgprocesosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sgprocesos.class));
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

    public Sgprocesos findSgprocesos(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sgprocesos.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgprocesosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sgprocesos> rt = cq.from(Sgprocesos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
