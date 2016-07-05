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
import sg.sistemas.entidades.SgplanProcesos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgprocesosSistema;

/**
 *
 * @author Misael
 */
public class SgprocesosSistemaJpaController implements Serializable {

    public SgprocesosSistemaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgprocesosSistema sgprocesosSistema) throws PreexistingEntityException, Exception {
        if (sgprocesosSistema.getSgplanProcesosList() == null) {
            sgprocesosSistema.setSgplanProcesosList(new ArrayList<SgplanProcesos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SgplanProcesos> attachedSgplanProcesosList = new ArrayList<SgplanProcesos>();
            for (SgplanProcesos sgplanProcesosListSgplanProcesosToAttach : sgprocesosSistema.getSgplanProcesosList()) {
                sgplanProcesosListSgplanProcesosToAttach = em.getReference(sgplanProcesosListSgplanProcesosToAttach.getClass(), sgplanProcesosListSgplanProcesosToAttach.getSgplanProcesosPK());
                attachedSgplanProcesosList.add(sgplanProcesosListSgplanProcesosToAttach);
            }
            sgprocesosSistema.setSgplanProcesosList(attachedSgplanProcesosList);
            em.persist(sgprocesosSistema);
            for (SgplanProcesos sgplanProcesosListSgplanProcesos : sgprocesosSistema.getSgplanProcesosList()) {
                SgprocesosSistema oldIdcodProcesoOfSgplanProcesosListSgplanProcesos = sgplanProcesosListSgplanProcesos.getIdcodProceso();
                sgplanProcesosListSgplanProcesos.setIdcodProceso(sgprocesosSistema);
                sgplanProcesosListSgplanProcesos = em.merge(sgplanProcesosListSgplanProcesos);
                if (oldIdcodProcesoOfSgplanProcesosListSgplanProcesos != null) {
                    oldIdcodProcesoOfSgplanProcesosListSgplanProcesos.getSgplanProcesosList().remove(sgplanProcesosListSgplanProcesos);
                    oldIdcodProcesoOfSgplanProcesosListSgplanProcesos = em.merge(oldIdcodProcesoOfSgplanProcesosListSgplanProcesos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgprocesosSistema(sgprocesosSistema.getIdcodProceso()) != null) {
                throw new PreexistingEntityException("SgprocesosSistema " + sgprocesosSistema + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgprocesosSistema sgprocesosSistema) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgprocesosSistema persistentSgprocesosSistema = em.find(SgprocesosSistema.class, sgprocesosSistema.getIdcodProceso());
            List<SgplanProcesos> sgplanProcesosListOld = persistentSgprocesosSistema.getSgplanProcesosList();
            List<SgplanProcesos> sgplanProcesosListNew = sgprocesosSistema.getSgplanProcesosList();
            List<SgplanProcesos> attachedSgplanProcesosListNew = new ArrayList<SgplanProcesos>();
            for (SgplanProcesos sgplanProcesosListNewSgplanProcesosToAttach : sgplanProcesosListNew) {
                sgplanProcesosListNewSgplanProcesosToAttach = em.getReference(sgplanProcesosListNewSgplanProcesosToAttach.getClass(), sgplanProcesosListNewSgplanProcesosToAttach.getSgplanProcesosPK());
                attachedSgplanProcesosListNew.add(sgplanProcesosListNewSgplanProcesosToAttach);
            }
            sgplanProcesosListNew = attachedSgplanProcesosListNew;
            sgprocesosSistema.setSgplanProcesosList(sgplanProcesosListNew);
            sgprocesosSistema = em.merge(sgprocesosSistema);
            for (SgplanProcesos sgplanProcesosListOldSgplanProcesos : sgplanProcesosListOld) {
                if (!sgplanProcesosListNew.contains(sgplanProcesosListOldSgplanProcesos)) {
                    sgplanProcesosListOldSgplanProcesos.setIdcodProceso(null);
                    sgplanProcesosListOldSgplanProcesos = em.merge(sgplanProcesosListOldSgplanProcesos);
                }
            }
            for (SgplanProcesos sgplanProcesosListNewSgplanProcesos : sgplanProcesosListNew) {
                if (!sgplanProcesosListOld.contains(sgplanProcesosListNewSgplanProcesos)) {
                    SgprocesosSistema oldIdcodProcesoOfSgplanProcesosListNewSgplanProcesos = sgplanProcesosListNewSgplanProcesos.getIdcodProceso();
                    sgplanProcesosListNewSgplanProcesos.setIdcodProceso(sgprocesosSistema);
                    sgplanProcesosListNewSgplanProcesos = em.merge(sgplanProcesosListNewSgplanProcesos);
                    if (oldIdcodProcesoOfSgplanProcesosListNewSgplanProcesos != null && !oldIdcodProcesoOfSgplanProcesosListNewSgplanProcesos.equals(sgprocesosSistema)) {
                        oldIdcodProcesoOfSgplanProcesosListNewSgplanProcesos.getSgplanProcesosList().remove(sgplanProcesosListNewSgplanProcesos);
                        oldIdcodProcesoOfSgplanProcesosListNewSgplanProcesos = em.merge(oldIdcodProcesoOfSgplanProcesosListNewSgplanProcesos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = sgprocesosSistema.getIdcodProceso();
                if (findSgprocesosSistema(id) == null) {
                    throw new NonexistentEntityException("The sgprocesosSistema with id " + id + " no longer exists.");
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
            SgprocesosSistema sgprocesosSistema;
            try {
                sgprocesosSistema = em.getReference(SgprocesosSistema.class, id);
                sgprocesosSistema.getIdcodProceso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgprocesosSistema with id " + id + " no longer exists.", enfe);
            }
            List<SgplanProcesos> sgplanProcesosList = sgprocesosSistema.getSgplanProcesosList();
            for (SgplanProcesos sgplanProcesosListSgplanProcesos : sgplanProcesosList) {
                sgplanProcesosListSgplanProcesos.setIdcodProceso(null);
                sgplanProcesosListSgplanProcesos = em.merge(sgplanProcesosListSgplanProcesos);
            }
            em.remove(sgprocesosSistema);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgprocesosSistema> findSgprocesosSistemaEntities() {
        return findSgprocesosSistemaEntities(true, -1, -1);
    }

    public List<SgprocesosSistema> findSgprocesosSistemaEntities(int maxResults, int firstResult) {
        return findSgprocesosSistemaEntities(false, maxResults, firstResult);
    }

    private List<SgprocesosSistema> findSgprocesosSistemaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgprocesosSistema.class));
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

    public SgprocesosSistema findSgprocesosSistema(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgprocesosSistema.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgprocesosSistemaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgprocesosSistema> rt = cq.from(SgprocesosSistema.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
