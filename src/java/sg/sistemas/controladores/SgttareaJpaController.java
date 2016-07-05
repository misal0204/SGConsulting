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
import sg.sistemas.entidades.Sgtareas;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.Sgttarea;

/**
 *
 * @author Misael
 */
public class SgttareaJpaController implements Serializable {

    public SgttareaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sgttarea sgttarea) throws PreexistingEntityException, Exception {
        if (sgttarea.getSgtareasList() == null) {
            sgttarea.setSgtareasList(new ArrayList<Sgtareas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Sgtareas> attachedSgtareasList = new ArrayList<Sgtareas>();
            for (Sgtareas sgtareasListSgtareasToAttach : sgttarea.getSgtareasList()) {
                sgtareasListSgtareasToAttach = em.getReference(sgtareasListSgtareasToAttach.getClass(), sgtareasListSgtareasToAttach.getSgtareasPK());
                attachedSgtareasList.add(sgtareasListSgtareasToAttach);
            }
            sgttarea.setSgtareasList(attachedSgtareasList);
            em.persist(sgttarea);
            for (Sgtareas sgtareasListSgtareas : sgttarea.getSgtareasList()) {
                Sgttarea oldSgttareaOfSgtareasListSgtareas = sgtareasListSgtareas.getSgttarea();
                sgtareasListSgtareas.setSgttarea(sgttarea);
                sgtareasListSgtareas = em.merge(sgtareasListSgtareas);
                if (oldSgttareaOfSgtareasListSgtareas != null) {
                    oldSgttareaOfSgtareasListSgtareas.getSgtareasList().remove(sgtareasListSgtareas);
                    oldSgttareaOfSgtareasListSgtareas = em.merge(oldSgttareaOfSgtareasListSgtareas);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgttarea(sgttarea.getIdtarea()) != null) {
                throw new PreexistingEntityException("Sgttarea " + sgttarea + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sgttarea sgttarea) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgttarea persistentSgttarea = em.find(Sgttarea.class, sgttarea.getIdtarea());
            List<Sgtareas> sgtareasListOld = persistentSgttarea.getSgtareasList();
            List<Sgtareas> sgtareasListNew = sgttarea.getSgtareasList();
            List<String> illegalOrphanMessages = null;
            for (Sgtareas sgtareasListOldSgtareas : sgtareasListOld) {
                if (!sgtareasListNew.contains(sgtareasListOldSgtareas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sgtareas " + sgtareasListOldSgtareas + " since its sgttarea field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Sgtareas> attachedSgtareasListNew = new ArrayList<Sgtareas>();
            for (Sgtareas sgtareasListNewSgtareasToAttach : sgtareasListNew) {
                sgtareasListNewSgtareasToAttach = em.getReference(sgtareasListNewSgtareasToAttach.getClass(), sgtareasListNewSgtareasToAttach.getSgtareasPK());
                attachedSgtareasListNew.add(sgtareasListNewSgtareasToAttach);
            }
            sgtareasListNew = attachedSgtareasListNew;
            sgttarea.setSgtareasList(sgtareasListNew);
            sgttarea = em.merge(sgttarea);
            for (Sgtareas sgtareasListNewSgtareas : sgtareasListNew) {
                if (!sgtareasListOld.contains(sgtareasListNewSgtareas)) {
                    Sgttarea oldSgttareaOfSgtareasListNewSgtareas = sgtareasListNewSgtareas.getSgttarea();
                    sgtareasListNewSgtareas.setSgttarea(sgttarea);
                    sgtareasListNewSgtareas = em.merge(sgtareasListNewSgtareas);
                    if (oldSgttareaOfSgtareasListNewSgtareas != null && !oldSgttareaOfSgtareasListNewSgtareas.equals(sgttarea)) {
                        oldSgttareaOfSgtareasListNewSgtareas.getSgtareasList().remove(sgtareasListNewSgtareas);
                        oldSgttareaOfSgtareasListNewSgtareas = em.merge(oldSgttareaOfSgtareasListNewSgtareas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = sgttarea.getIdtarea();
                if (findSgttarea(id) == null) {
                    throw new NonexistentEntityException("The sgttarea with id " + id + " no longer exists.");
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
            Sgttarea sgttarea;
            try {
                sgttarea = em.getReference(Sgttarea.class, id);
                sgttarea.getIdtarea();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgttarea with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Sgtareas> sgtareasListOrphanCheck = sgttarea.getSgtareasList();
            for (Sgtareas sgtareasListOrphanCheckSgtareas : sgtareasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sgttarea (" + sgttarea + ") cannot be destroyed since the Sgtareas " + sgtareasListOrphanCheckSgtareas + " in its sgtareasList field has a non-nullable sgttarea field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(sgttarea);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sgttarea> findSgttareaEntities() {
        return findSgttareaEntities(true, -1, -1);
    }

    public List<Sgttarea> findSgttareaEntities(int maxResults, int firstResult) {
        return findSgttareaEntities(false, maxResults, firstResult);
    }

    private List<Sgttarea> findSgttareaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sgttarea.class));
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

    public Sgttarea findSgttarea(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sgttarea.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgttareaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sgttarea> rt = cq.from(Sgttarea.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
