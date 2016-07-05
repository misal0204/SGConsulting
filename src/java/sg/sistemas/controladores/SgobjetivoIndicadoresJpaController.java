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
import sg.sistemas.entidades.SgindicadorProceso;
import sg.sistemas.entidades.SgdetalleObjInd;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgobjetivoIndicadores;
import sg.sistemas.entidades.SgobjetivoIndicadoresPK;

/**
 *
 * @author Misael
 */
public class SgobjetivoIndicadoresJpaController implements Serializable {

    public SgobjetivoIndicadoresJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgobjetivoIndicadores sgobjetivoIndicadores) throws PreexistingEntityException, Exception {
        if (sgobjetivoIndicadores.getSgobjetivoIndicadoresPK() == null) {
            sgobjetivoIndicadores.setSgobjetivoIndicadoresPK(new SgobjetivoIndicadoresPK());
        }
        if (sgobjetivoIndicadores.getSgdetalleObjIndList() == null) {
            sgobjetivoIndicadores.setSgdetalleObjIndList(new ArrayList<SgdetalleObjInd>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgindicadorProceso sgindicadorProceso = sgobjetivoIndicadores.getSgindicadorProceso();
            if (sgindicadorProceso != null) {
                sgindicadorProceso = em.getReference(sgindicadorProceso.getClass(), sgindicadorProceso.getSgindicadorProcesoPK());
                sgobjetivoIndicadores.setSgindicadorProceso(sgindicadorProceso);
            }
            List<SgdetalleObjInd> attachedSgdetalleObjIndList = new ArrayList<SgdetalleObjInd>();
            for (SgdetalleObjInd sgdetalleObjIndListSgdetalleObjIndToAttach : sgobjetivoIndicadores.getSgdetalleObjIndList()) {
                sgdetalleObjIndListSgdetalleObjIndToAttach = em.getReference(sgdetalleObjIndListSgdetalleObjIndToAttach.getClass(), sgdetalleObjIndListSgdetalleObjIndToAttach.getSgdetalleObjIndPK());
                attachedSgdetalleObjIndList.add(sgdetalleObjIndListSgdetalleObjIndToAttach);
            }
            sgobjetivoIndicadores.setSgdetalleObjIndList(attachedSgdetalleObjIndList);
            em.persist(sgobjetivoIndicadores);
            if (sgindicadorProceso != null) {
                SgobjetivoIndicadores oldSgobjetivoIndicadoresOfSgindicadorProceso = sgindicadorProceso.getSgobjetivoIndicadores();
                if (oldSgobjetivoIndicadoresOfSgindicadorProceso != null) {
                    oldSgobjetivoIndicadoresOfSgindicadorProceso.setSgindicadorProceso(null);
                    oldSgobjetivoIndicadoresOfSgindicadorProceso = em.merge(oldSgobjetivoIndicadoresOfSgindicadorProceso);
                }
                sgindicadorProceso.setSgobjetivoIndicadores(sgobjetivoIndicadores);
                sgindicadorProceso = em.merge(sgindicadorProceso);
            }
            for (SgdetalleObjInd sgdetalleObjIndListSgdetalleObjInd : sgobjetivoIndicadores.getSgdetalleObjIndList()) {
                SgobjetivoIndicadores oldSgobjetivoIndicadoresOfSgdetalleObjIndListSgdetalleObjInd = sgdetalleObjIndListSgdetalleObjInd.getSgobjetivoIndicadores();
                sgdetalleObjIndListSgdetalleObjInd.setSgobjetivoIndicadores(sgobjetivoIndicadores);
                sgdetalleObjIndListSgdetalleObjInd = em.merge(sgdetalleObjIndListSgdetalleObjInd);
                if (oldSgobjetivoIndicadoresOfSgdetalleObjIndListSgdetalleObjInd != null) {
                    oldSgobjetivoIndicadoresOfSgdetalleObjIndListSgdetalleObjInd.getSgdetalleObjIndList().remove(sgdetalleObjIndListSgdetalleObjInd);
                    oldSgobjetivoIndicadoresOfSgdetalleObjIndListSgdetalleObjInd = em.merge(oldSgobjetivoIndicadoresOfSgdetalleObjIndListSgdetalleObjInd);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgobjetivoIndicadores(sgobjetivoIndicadores.getSgobjetivoIndicadoresPK()) != null) {
                throw new PreexistingEntityException("SgobjetivoIndicadores " + sgobjetivoIndicadores + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgobjetivoIndicadores sgobjetivoIndicadores) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgobjetivoIndicadores persistentSgobjetivoIndicadores = em.find(SgobjetivoIndicadores.class, sgobjetivoIndicadores.getSgobjetivoIndicadoresPK());
            SgindicadorProceso sgindicadorProcesoOld = persistentSgobjetivoIndicadores.getSgindicadorProceso();
            SgindicadorProceso sgindicadorProcesoNew = sgobjetivoIndicadores.getSgindicadorProceso();
            List<SgdetalleObjInd> sgdetalleObjIndListOld = persistentSgobjetivoIndicadores.getSgdetalleObjIndList();
            List<SgdetalleObjInd> sgdetalleObjIndListNew = sgobjetivoIndicadores.getSgdetalleObjIndList();
            List<String> illegalOrphanMessages = null;
            if (sgindicadorProcesoOld != null && !sgindicadorProcesoOld.equals(sgindicadorProcesoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain SgindicadorProceso " + sgindicadorProcesoOld + " since its sgobjetivoIndicadores field is not nullable.");
            }
            for (SgdetalleObjInd sgdetalleObjIndListOldSgdetalleObjInd : sgdetalleObjIndListOld) {
                if (!sgdetalleObjIndListNew.contains(sgdetalleObjIndListOldSgdetalleObjInd)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SgdetalleObjInd " + sgdetalleObjIndListOldSgdetalleObjInd + " since its sgobjetivoIndicadores field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sgindicadorProcesoNew != null) {
                sgindicadorProcesoNew = em.getReference(sgindicadorProcesoNew.getClass(), sgindicadorProcesoNew.getSgindicadorProcesoPK());
                sgobjetivoIndicadores.setSgindicadorProceso(sgindicadorProcesoNew);
            }
            List<SgdetalleObjInd> attachedSgdetalleObjIndListNew = new ArrayList<SgdetalleObjInd>();
            for (SgdetalleObjInd sgdetalleObjIndListNewSgdetalleObjIndToAttach : sgdetalleObjIndListNew) {
                sgdetalleObjIndListNewSgdetalleObjIndToAttach = em.getReference(sgdetalleObjIndListNewSgdetalleObjIndToAttach.getClass(), sgdetalleObjIndListNewSgdetalleObjIndToAttach.getSgdetalleObjIndPK());
                attachedSgdetalleObjIndListNew.add(sgdetalleObjIndListNewSgdetalleObjIndToAttach);
            }
            sgdetalleObjIndListNew = attachedSgdetalleObjIndListNew;
            sgobjetivoIndicadores.setSgdetalleObjIndList(sgdetalleObjIndListNew);
            sgobjetivoIndicadores = em.merge(sgobjetivoIndicadores);
            if (sgindicadorProcesoNew != null && !sgindicadorProcesoNew.equals(sgindicadorProcesoOld)) {
                SgobjetivoIndicadores oldSgobjetivoIndicadoresOfSgindicadorProceso = sgindicadorProcesoNew.getSgobjetivoIndicadores();
                if (oldSgobjetivoIndicadoresOfSgindicadorProceso != null) {
                    oldSgobjetivoIndicadoresOfSgindicadorProceso.setSgindicadorProceso(null);
                    oldSgobjetivoIndicadoresOfSgindicadorProceso = em.merge(oldSgobjetivoIndicadoresOfSgindicadorProceso);
                }
                sgindicadorProcesoNew.setSgobjetivoIndicadores(sgobjetivoIndicadores);
                sgindicadorProcesoNew = em.merge(sgindicadorProcesoNew);
            }
            for (SgdetalleObjInd sgdetalleObjIndListNewSgdetalleObjInd : sgdetalleObjIndListNew) {
                if (!sgdetalleObjIndListOld.contains(sgdetalleObjIndListNewSgdetalleObjInd)) {
                    SgobjetivoIndicadores oldSgobjetivoIndicadoresOfSgdetalleObjIndListNewSgdetalleObjInd = sgdetalleObjIndListNewSgdetalleObjInd.getSgobjetivoIndicadores();
                    sgdetalleObjIndListNewSgdetalleObjInd.setSgobjetivoIndicadores(sgobjetivoIndicadores);
                    sgdetalleObjIndListNewSgdetalleObjInd = em.merge(sgdetalleObjIndListNewSgdetalleObjInd);
                    if (oldSgobjetivoIndicadoresOfSgdetalleObjIndListNewSgdetalleObjInd != null && !oldSgobjetivoIndicadoresOfSgdetalleObjIndListNewSgdetalleObjInd.equals(sgobjetivoIndicadores)) {
                        oldSgobjetivoIndicadoresOfSgdetalleObjIndListNewSgdetalleObjInd.getSgdetalleObjIndList().remove(sgdetalleObjIndListNewSgdetalleObjInd);
                        oldSgobjetivoIndicadoresOfSgdetalleObjIndListNewSgdetalleObjInd = em.merge(oldSgobjetivoIndicadoresOfSgdetalleObjIndListNewSgdetalleObjInd);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgobjetivoIndicadoresPK id = sgobjetivoIndicadores.getSgobjetivoIndicadoresPK();
                if (findSgobjetivoIndicadores(id) == null) {
                    throw new NonexistentEntityException("The sgobjetivoIndicadores with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgobjetivoIndicadoresPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgobjetivoIndicadores sgobjetivoIndicadores;
            try {
                sgobjetivoIndicadores = em.getReference(SgobjetivoIndicadores.class, id);
                sgobjetivoIndicadores.getSgobjetivoIndicadoresPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgobjetivoIndicadores with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            SgindicadorProceso sgindicadorProcesoOrphanCheck = sgobjetivoIndicadores.getSgindicadorProceso();
            if (sgindicadorProcesoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SgobjetivoIndicadores (" + sgobjetivoIndicadores + ") cannot be destroyed since the SgindicadorProceso " + sgindicadorProcesoOrphanCheck + " in its sgindicadorProceso field has a non-nullable sgobjetivoIndicadores field.");
            }
            List<SgdetalleObjInd> sgdetalleObjIndListOrphanCheck = sgobjetivoIndicadores.getSgdetalleObjIndList();
            for (SgdetalleObjInd sgdetalleObjIndListOrphanCheckSgdetalleObjInd : sgdetalleObjIndListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SgobjetivoIndicadores (" + sgobjetivoIndicadores + ") cannot be destroyed since the SgdetalleObjInd " + sgdetalleObjIndListOrphanCheckSgdetalleObjInd + " in its sgdetalleObjIndList field has a non-nullable sgobjetivoIndicadores field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(sgobjetivoIndicadores);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgobjetivoIndicadores> findSgobjetivoIndicadoresEntities() {
        return findSgobjetivoIndicadoresEntities(true, -1, -1);
    }

    public List<SgobjetivoIndicadores> findSgobjetivoIndicadoresEntities(int maxResults, int firstResult) {
        return findSgobjetivoIndicadoresEntities(false, maxResults, firstResult);
    }

    private List<SgobjetivoIndicadores> findSgobjetivoIndicadoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgobjetivoIndicadores.class));
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

    public SgobjetivoIndicadores findSgobjetivoIndicadores(SgobjetivoIndicadoresPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgobjetivoIndicadores.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgobjetivoIndicadoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgobjetivoIndicadores> rt = cq.from(SgobjetivoIndicadores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
