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
import sg.sistemas.entidades.SgseguimientoIndObj;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgtipoAlerta;

/**
 *
 * @author Misael
 */
public class SgtipoAlertaJpaController implements Serializable {

    public SgtipoAlertaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgtipoAlerta sgtipoAlerta) throws PreexistingEntityException, Exception {
        if (sgtipoAlerta.getSgseguimientoIndObjList() == null) {
            sgtipoAlerta.setSgseguimientoIndObjList(new ArrayList<SgseguimientoIndObj>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SgseguimientoIndObj> attachedSgseguimientoIndObjList = new ArrayList<SgseguimientoIndObj>();
            for (SgseguimientoIndObj sgseguimientoIndObjListSgseguimientoIndObjToAttach : sgtipoAlerta.getSgseguimientoIndObjList()) {
                sgseguimientoIndObjListSgseguimientoIndObjToAttach = em.getReference(sgseguimientoIndObjListSgseguimientoIndObjToAttach.getClass(), sgseguimientoIndObjListSgseguimientoIndObjToAttach.getSgseguimientoIndObjPK());
                attachedSgseguimientoIndObjList.add(sgseguimientoIndObjListSgseguimientoIndObjToAttach);
            }
            sgtipoAlerta.setSgseguimientoIndObjList(attachedSgseguimientoIndObjList);
            em.persist(sgtipoAlerta);
            for (SgseguimientoIndObj sgseguimientoIndObjListSgseguimientoIndObj : sgtipoAlerta.getSgseguimientoIndObjList()) {
                SgtipoAlerta oldSgtipoAlertaOfSgseguimientoIndObjListSgseguimientoIndObj = sgseguimientoIndObjListSgseguimientoIndObj.getSgtipoAlerta();
                sgseguimientoIndObjListSgseguimientoIndObj.setSgtipoAlerta(sgtipoAlerta);
                sgseguimientoIndObjListSgseguimientoIndObj = em.merge(sgseguimientoIndObjListSgseguimientoIndObj);
                if (oldSgtipoAlertaOfSgseguimientoIndObjListSgseguimientoIndObj != null) {
                    oldSgtipoAlertaOfSgseguimientoIndObjListSgseguimientoIndObj.getSgseguimientoIndObjList().remove(sgseguimientoIndObjListSgseguimientoIndObj);
                    oldSgtipoAlertaOfSgseguimientoIndObjListSgseguimientoIndObj = em.merge(oldSgtipoAlertaOfSgseguimientoIndObjListSgseguimientoIndObj);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgtipoAlerta(sgtipoAlerta.getIdtipoAlerta()) != null) {
                throw new PreexistingEntityException("SgtipoAlerta " + sgtipoAlerta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgtipoAlerta sgtipoAlerta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgtipoAlerta persistentSgtipoAlerta = em.find(SgtipoAlerta.class, sgtipoAlerta.getIdtipoAlerta());
            List<SgseguimientoIndObj> sgseguimientoIndObjListOld = persistentSgtipoAlerta.getSgseguimientoIndObjList();
            List<SgseguimientoIndObj> sgseguimientoIndObjListNew = sgtipoAlerta.getSgseguimientoIndObjList();
            List<String> illegalOrphanMessages = null;
            for (SgseguimientoIndObj sgseguimientoIndObjListOldSgseguimientoIndObj : sgseguimientoIndObjListOld) {
                if (!sgseguimientoIndObjListNew.contains(sgseguimientoIndObjListOldSgseguimientoIndObj)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SgseguimientoIndObj " + sgseguimientoIndObjListOldSgseguimientoIndObj + " since its sgtipoAlerta field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<SgseguimientoIndObj> attachedSgseguimientoIndObjListNew = new ArrayList<SgseguimientoIndObj>();
            for (SgseguimientoIndObj sgseguimientoIndObjListNewSgseguimientoIndObjToAttach : sgseguimientoIndObjListNew) {
                sgseguimientoIndObjListNewSgseguimientoIndObjToAttach = em.getReference(sgseguimientoIndObjListNewSgseguimientoIndObjToAttach.getClass(), sgseguimientoIndObjListNewSgseguimientoIndObjToAttach.getSgseguimientoIndObjPK());
                attachedSgseguimientoIndObjListNew.add(sgseguimientoIndObjListNewSgseguimientoIndObjToAttach);
            }
            sgseguimientoIndObjListNew = attachedSgseguimientoIndObjListNew;
            sgtipoAlerta.setSgseguimientoIndObjList(sgseguimientoIndObjListNew);
            sgtipoAlerta = em.merge(sgtipoAlerta);
            for (SgseguimientoIndObj sgseguimientoIndObjListNewSgseguimientoIndObj : sgseguimientoIndObjListNew) {
                if (!sgseguimientoIndObjListOld.contains(sgseguimientoIndObjListNewSgseguimientoIndObj)) {
                    SgtipoAlerta oldSgtipoAlertaOfSgseguimientoIndObjListNewSgseguimientoIndObj = sgseguimientoIndObjListNewSgseguimientoIndObj.getSgtipoAlerta();
                    sgseguimientoIndObjListNewSgseguimientoIndObj.setSgtipoAlerta(sgtipoAlerta);
                    sgseguimientoIndObjListNewSgseguimientoIndObj = em.merge(sgseguimientoIndObjListNewSgseguimientoIndObj);
                    if (oldSgtipoAlertaOfSgseguimientoIndObjListNewSgseguimientoIndObj != null && !oldSgtipoAlertaOfSgseguimientoIndObjListNewSgseguimientoIndObj.equals(sgtipoAlerta)) {
                        oldSgtipoAlertaOfSgseguimientoIndObjListNewSgseguimientoIndObj.getSgseguimientoIndObjList().remove(sgseguimientoIndObjListNewSgseguimientoIndObj);
                        oldSgtipoAlertaOfSgseguimientoIndObjListNewSgseguimientoIndObj = em.merge(oldSgtipoAlertaOfSgseguimientoIndObjListNewSgseguimientoIndObj);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = sgtipoAlerta.getIdtipoAlerta();
                if (findSgtipoAlerta(id) == null) {
                    throw new NonexistentEntityException("The sgtipoAlerta with id " + id + " no longer exists.");
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
            SgtipoAlerta sgtipoAlerta;
            try {
                sgtipoAlerta = em.getReference(SgtipoAlerta.class, id);
                sgtipoAlerta.getIdtipoAlerta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgtipoAlerta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SgseguimientoIndObj> sgseguimientoIndObjListOrphanCheck = sgtipoAlerta.getSgseguimientoIndObjList();
            for (SgseguimientoIndObj sgseguimientoIndObjListOrphanCheckSgseguimientoIndObj : sgseguimientoIndObjListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SgtipoAlerta (" + sgtipoAlerta + ") cannot be destroyed since the SgseguimientoIndObj " + sgseguimientoIndObjListOrphanCheckSgseguimientoIndObj + " in its sgseguimientoIndObjList field has a non-nullable sgtipoAlerta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(sgtipoAlerta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgtipoAlerta> findSgtipoAlertaEntities() {
        return findSgtipoAlertaEntities(true, -1, -1);
    }

    public List<SgtipoAlerta> findSgtipoAlertaEntities(int maxResults, int firstResult) {
        return findSgtipoAlertaEntities(false, maxResults, firstResult);
    }

    private List<SgtipoAlerta> findSgtipoAlertaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgtipoAlerta.class));
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

    public SgtipoAlerta findSgtipoAlerta(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgtipoAlerta.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgtipoAlertaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgtipoAlerta> rt = cq.from(SgtipoAlerta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
