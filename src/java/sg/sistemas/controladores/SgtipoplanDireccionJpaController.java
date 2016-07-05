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
import sg.sistemas.entidades.SgrevisionDireccion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgtipoplanDireccion;

/**
 *
 * @author Misael
 */
public class SgtipoplanDireccionJpaController implements Serializable {

    public SgtipoplanDireccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgtipoplanDireccion sgtipoplanDireccion) throws PreexistingEntityException, Exception {
        if (sgtipoplanDireccion.getSgrevisionDireccionList() == null) {
            sgtipoplanDireccion.setSgrevisionDireccionList(new ArrayList<SgrevisionDireccion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SgrevisionDireccion> attachedSgrevisionDireccionList = new ArrayList<SgrevisionDireccion>();
            for (SgrevisionDireccion sgrevisionDireccionListSgrevisionDireccionToAttach : sgtipoplanDireccion.getSgrevisionDireccionList()) {
                sgrevisionDireccionListSgrevisionDireccionToAttach = em.getReference(sgrevisionDireccionListSgrevisionDireccionToAttach.getClass(), sgrevisionDireccionListSgrevisionDireccionToAttach.getSgrevisionDireccionPK());
                attachedSgrevisionDireccionList.add(sgrevisionDireccionListSgrevisionDireccionToAttach);
            }
            sgtipoplanDireccion.setSgrevisionDireccionList(attachedSgrevisionDireccionList);
            em.persist(sgtipoplanDireccion);
            for (SgrevisionDireccion sgrevisionDireccionListSgrevisionDireccion : sgtipoplanDireccion.getSgrevisionDireccionList()) {
                SgtipoplanDireccion oldSgtipoplanDireccionOfSgrevisionDireccionListSgrevisionDireccion = sgrevisionDireccionListSgrevisionDireccion.getSgtipoplanDireccion();
                sgrevisionDireccionListSgrevisionDireccion.setSgtipoplanDireccion(sgtipoplanDireccion);
                sgrevisionDireccionListSgrevisionDireccion = em.merge(sgrevisionDireccionListSgrevisionDireccion);
                if (oldSgtipoplanDireccionOfSgrevisionDireccionListSgrevisionDireccion != null) {
                    oldSgtipoplanDireccionOfSgrevisionDireccionListSgrevisionDireccion.getSgrevisionDireccionList().remove(sgrevisionDireccionListSgrevisionDireccion);
                    oldSgtipoplanDireccionOfSgrevisionDireccionListSgrevisionDireccion = em.merge(oldSgtipoplanDireccionOfSgrevisionDireccionListSgrevisionDireccion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgtipoplanDireccion(sgtipoplanDireccion.getIdtipoPdireccion()) != null) {
                throw new PreexistingEntityException("SgtipoplanDireccion " + sgtipoplanDireccion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgtipoplanDireccion sgtipoplanDireccion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgtipoplanDireccion persistentSgtipoplanDireccion = em.find(SgtipoplanDireccion.class, sgtipoplanDireccion.getIdtipoPdireccion());
            List<SgrevisionDireccion> sgrevisionDireccionListOld = persistentSgtipoplanDireccion.getSgrevisionDireccionList();
            List<SgrevisionDireccion> sgrevisionDireccionListNew = sgtipoplanDireccion.getSgrevisionDireccionList();
            List<String> illegalOrphanMessages = null;
            for (SgrevisionDireccion sgrevisionDireccionListOldSgrevisionDireccion : sgrevisionDireccionListOld) {
                if (!sgrevisionDireccionListNew.contains(sgrevisionDireccionListOldSgrevisionDireccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SgrevisionDireccion " + sgrevisionDireccionListOldSgrevisionDireccion + " since its sgtipoplanDireccion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<SgrevisionDireccion> attachedSgrevisionDireccionListNew = new ArrayList<SgrevisionDireccion>();
            for (SgrevisionDireccion sgrevisionDireccionListNewSgrevisionDireccionToAttach : sgrevisionDireccionListNew) {
                sgrevisionDireccionListNewSgrevisionDireccionToAttach = em.getReference(sgrevisionDireccionListNewSgrevisionDireccionToAttach.getClass(), sgrevisionDireccionListNewSgrevisionDireccionToAttach.getSgrevisionDireccionPK());
                attachedSgrevisionDireccionListNew.add(sgrevisionDireccionListNewSgrevisionDireccionToAttach);
            }
            sgrevisionDireccionListNew = attachedSgrevisionDireccionListNew;
            sgtipoplanDireccion.setSgrevisionDireccionList(sgrevisionDireccionListNew);
            sgtipoplanDireccion = em.merge(sgtipoplanDireccion);
            for (SgrevisionDireccion sgrevisionDireccionListNewSgrevisionDireccion : sgrevisionDireccionListNew) {
                if (!sgrevisionDireccionListOld.contains(sgrevisionDireccionListNewSgrevisionDireccion)) {
                    SgtipoplanDireccion oldSgtipoplanDireccionOfSgrevisionDireccionListNewSgrevisionDireccion = sgrevisionDireccionListNewSgrevisionDireccion.getSgtipoplanDireccion();
                    sgrevisionDireccionListNewSgrevisionDireccion.setSgtipoplanDireccion(sgtipoplanDireccion);
                    sgrevisionDireccionListNewSgrevisionDireccion = em.merge(sgrevisionDireccionListNewSgrevisionDireccion);
                    if (oldSgtipoplanDireccionOfSgrevisionDireccionListNewSgrevisionDireccion != null && !oldSgtipoplanDireccionOfSgrevisionDireccionListNewSgrevisionDireccion.equals(sgtipoplanDireccion)) {
                        oldSgtipoplanDireccionOfSgrevisionDireccionListNewSgrevisionDireccion.getSgrevisionDireccionList().remove(sgrevisionDireccionListNewSgrevisionDireccion);
                        oldSgtipoplanDireccionOfSgrevisionDireccionListNewSgrevisionDireccion = em.merge(oldSgtipoplanDireccionOfSgrevisionDireccionListNewSgrevisionDireccion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = sgtipoplanDireccion.getIdtipoPdireccion();
                if (findSgtipoplanDireccion(id) == null) {
                    throw new NonexistentEntityException("The sgtipoplanDireccion with id " + id + " no longer exists.");
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
            SgtipoplanDireccion sgtipoplanDireccion;
            try {
                sgtipoplanDireccion = em.getReference(SgtipoplanDireccion.class, id);
                sgtipoplanDireccion.getIdtipoPdireccion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgtipoplanDireccion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SgrevisionDireccion> sgrevisionDireccionListOrphanCheck = sgtipoplanDireccion.getSgrevisionDireccionList();
            for (SgrevisionDireccion sgrevisionDireccionListOrphanCheckSgrevisionDireccion : sgrevisionDireccionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SgtipoplanDireccion (" + sgtipoplanDireccion + ") cannot be destroyed since the SgrevisionDireccion " + sgrevisionDireccionListOrphanCheckSgrevisionDireccion + " in its sgrevisionDireccionList field has a non-nullable sgtipoplanDireccion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(sgtipoplanDireccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgtipoplanDireccion> findSgtipoplanDireccionEntities() {
        return findSgtipoplanDireccionEntities(true, -1, -1);
    }

    public List<SgtipoplanDireccion> findSgtipoplanDireccionEntities(int maxResults, int firstResult) {
        return findSgtipoplanDireccionEntities(false, maxResults, firstResult);
    }

    private List<SgtipoplanDireccion> findSgtipoplanDireccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgtipoplanDireccion.class));
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

    public SgtipoplanDireccion findSgtipoplanDireccion(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgtipoplanDireccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgtipoplanDireccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgtipoplanDireccion> rt = cq.from(SgtipoplanDireccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
