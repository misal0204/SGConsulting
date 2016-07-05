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
import sg.sistemas.entidades.SgdetalleRdireccion;
import sg.sistemas.entidades.SgprocesosSistema;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgplanProcesos;
import sg.sistemas.entidades.SgplanProcesosPK;

/**
 *
 * @author Misael
 */
public class SgplanProcesosJpaController implements Serializable {

    public SgplanProcesosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgplanProcesos sgplanProcesos) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (sgplanProcesos.getSgplanProcesosPK() == null) {
            sgplanProcesos.setSgplanProcesosPK(new SgplanProcesosPK());
        }
        sgplanProcesos.getSgplanProcesosPK().setIddetalleDireccion(sgplanProcesos.getSgdetalleRdireccion().getSgdetalleRdireccionPK().getIddetalleDireccion());
        sgplanProcesos.getSgplanProcesosPK().setIdtipoPdireccion(sgplanProcesos.getSgdetalleRdireccion().getSgdetalleRdireccionPK().getIdtipoPdireccion());
        sgplanProcesos.getSgplanProcesosPK().setIdsociedad(sgplanProcesos.getSgdetalleRdireccion().getSgdetalleRdireccionPK().getIdsociedad());
        List<String> illegalOrphanMessages = null;
        SgdetalleRdireccion sgdetalleRdireccionOrphanCheck = sgplanProcesos.getSgdetalleRdireccion();
        if (sgdetalleRdireccionOrphanCheck != null) {
            SgplanProcesos oldSgplanProcesosOfSgdetalleRdireccion = sgdetalleRdireccionOrphanCheck.getSgplanProcesos();
            if (oldSgplanProcesosOfSgdetalleRdireccion != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The SgdetalleRdireccion " + sgdetalleRdireccionOrphanCheck + " already has an item of type SgplanProcesos whose sgdetalleRdireccion column cannot be null. Please make another selection for the sgdetalleRdireccion field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgdetalleRdireccion sgdetalleRdireccion = sgplanProcesos.getSgdetalleRdireccion();
            if (sgdetalleRdireccion != null) {
                sgdetalleRdireccion = em.getReference(sgdetalleRdireccion.getClass(), sgdetalleRdireccion.getSgdetalleRdireccionPK());
                sgplanProcesos.setSgdetalleRdireccion(sgdetalleRdireccion);
            }
            SgprocesosSistema idcodProceso = sgplanProcesos.getIdcodProceso();
            if (idcodProceso != null) {
                idcodProceso = em.getReference(idcodProceso.getClass(), idcodProceso.getIdcodProceso());
                sgplanProcesos.setIdcodProceso(idcodProceso);
            }
            em.persist(sgplanProcesos);
            if (sgdetalleRdireccion != null) {
                sgdetalleRdireccion.setSgplanProcesos(sgplanProcesos);
                sgdetalleRdireccion = em.merge(sgdetalleRdireccion);
            }
            if (idcodProceso != null) {
                idcodProceso.getSgplanProcesosList().add(sgplanProcesos);
                idcodProceso = em.merge(idcodProceso);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgplanProcesos(sgplanProcesos.getSgplanProcesosPK()) != null) {
                throw new PreexistingEntityException("SgplanProcesos " + sgplanProcesos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgplanProcesos sgplanProcesos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        sgplanProcesos.getSgplanProcesosPK().setIddetalleDireccion(sgplanProcesos.getSgdetalleRdireccion().getSgdetalleRdireccionPK().getIddetalleDireccion());
        sgplanProcesos.getSgplanProcesosPK().setIdtipoPdireccion(sgplanProcesos.getSgdetalleRdireccion().getSgdetalleRdireccionPK().getIdtipoPdireccion());
        sgplanProcesos.getSgplanProcesosPK().setIdsociedad(sgplanProcesos.getSgdetalleRdireccion().getSgdetalleRdireccionPK().getIdsociedad());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgplanProcesos persistentSgplanProcesos = em.find(SgplanProcesos.class, sgplanProcesos.getSgplanProcesosPK());
            SgdetalleRdireccion sgdetalleRdireccionOld = persistentSgplanProcesos.getSgdetalleRdireccion();
            SgdetalleRdireccion sgdetalleRdireccionNew = sgplanProcesos.getSgdetalleRdireccion();
            SgprocesosSistema idcodProcesoOld = persistentSgplanProcesos.getIdcodProceso();
            SgprocesosSistema idcodProcesoNew = sgplanProcesos.getIdcodProceso();
            List<String> illegalOrphanMessages = null;
            if (sgdetalleRdireccionNew != null && !sgdetalleRdireccionNew.equals(sgdetalleRdireccionOld)) {
                SgplanProcesos oldSgplanProcesosOfSgdetalleRdireccion = sgdetalleRdireccionNew.getSgplanProcesos();
                if (oldSgplanProcesosOfSgdetalleRdireccion != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The SgdetalleRdireccion " + sgdetalleRdireccionNew + " already has an item of type SgplanProcesos whose sgdetalleRdireccion column cannot be null. Please make another selection for the sgdetalleRdireccion field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sgdetalleRdireccionNew != null) {
                sgdetalleRdireccionNew = em.getReference(sgdetalleRdireccionNew.getClass(), sgdetalleRdireccionNew.getSgdetalleRdireccionPK());
                sgplanProcesos.setSgdetalleRdireccion(sgdetalleRdireccionNew);
            }
            if (idcodProcesoNew != null) {
                idcodProcesoNew = em.getReference(idcodProcesoNew.getClass(), idcodProcesoNew.getIdcodProceso());
                sgplanProcesos.setIdcodProceso(idcodProcesoNew);
            }
            sgplanProcesos = em.merge(sgplanProcesos);
            if (sgdetalleRdireccionOld != null && !sgdetalleRdireccionOld.equals(sgdetalleRdireccionNew)) {
                sgdetalleRdireccionOld.setSgplanProcesos(null);
                sgdetalleRdireccionOld = em.merge(sgdetalleRdireccionOld);
            }
            if (sgdetalleRdireccionNew != null && !sgdetalleRdireccionNew.equals(sgdetalleRdireccionOld)) {
                sgdetalleRdireccionNew.setSgplanProcesos(sgplanProcesos);
                sgdetalleRdireccionNew = em.merge(sgdetalleRdireccionNew);
            }
            if (idcodProcesoOld != null && !idcodProcesoOld.equals(idcodProcesoNew)) {
                idcodProcesoOld.getSgplanProcesosList().remove(sgplanProcesos);
                idcodProcesoOld = em.merge(idcodProcesoOld);
            }
            if (idcodProcesoNew != null && !idcodProcesoNew.equals(idcodProcesoOld)) {
                idcodProcesoNew.getSgplanProcesosList().add(sgplanProcesos);
                idcodProcesoNew = em.merge(idcodProcesoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgplanProcesosPK id = sgplanProcesos.getSgplanProcesosPK();
                if (findSgplanProcesos(id) == null) {
                    throw new NonexistentEntityException("The sgplanProcesos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgplanProcesosPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgplanProcesos sgplanProcesos;
            try {
                sgplanProcesos = em.getReference(SgplanProcesos.class, id);
                sgplanProcesos.getSgplanProcesosPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgplanProcesos with id " + id + " no longer exists.", enfe);
            }
            SgdetalleRdireccion sgdetalleRdireccion = sgplanProcesos.getSgdetalleRdireccion();
            if (sgdetalleRdireccion != null) {
                sgdetalleRdireccion.setSgplanProcesos(null);
                sgdetalleRdireccion = em.merge(sgdetalleRdireccion);
            }
            SgprocesosSistema idcodProceso = sgplanProcesos.getIdcodProceso();
            if (idcodProceso != null) {
                idcodProceso.getSgplanProcesosList().remove(sgplanProcesos);
                idcodProceso = em.merge(idcodProceso);
            }
            em.remove(sgplanProcesos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgplanProcesos> findSgplanProcesosEntities() {
        return findSgplanProcesosEntities(true, -1, -1);
    }

    public List<SgplanProcesos> findSgplanProcesosEntities(int maxResults, int firstResult) {
        return findSgplanProcesosEntities(false, maxResults, firstResult);
    }

    private List<SgplanProcesos> findSgplanProcesosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgplanProcesos.class));
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

    public SgplanProcesos findSgplanProcesos(SgplanProcesosPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgplanProcesos.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgplanProcesosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgplanProcesos> rt = cq.from(SgplanProcesos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
