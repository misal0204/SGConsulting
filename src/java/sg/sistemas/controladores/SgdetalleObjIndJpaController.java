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
import sg.sistemas.entidades.SgobjetivoIndicadores;
import sg.sistemas.entidades.SgindicadorC;
import sg.sistemas.entidades.SgseguimientoIndObj;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgdetalleObjInd;
import sg.sistemas.entidades.SgdetalleObjIndPK;

/**
 *
 * @author Misael
 */
public class SgdetalleObjIndJpaController implements Serializable {

    public SgdetalleObjIndJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgdetalleObjInd sgdetalleObjInd) throws PreexistingEntityException, Exception {
        if (sgdetalleObjInd.getSgdetalleObjIndPK() == null) {
            sgdetalleObjInd.setSgdetalleObjIndPK(new SgdetalleObjIndPK());
        }
        if (sgdetalleObjInd.getSgseguimientoIndObjList() == null) {
            sgdetalleObjInd.setSgseguimientoIndObjList(new ArrayList<SgseguimientoIndObj>());
        }
        sgdetalleObjInd.getSgdetalleObjIndPK().setIdobjIndicador(sgdetalleObjInd.getSgobjetivoIndicadores().getSgobjetivoIndicadoresPK().getIdobjIndicador());
        sgdetalleObjInd.getSgdetalleObjIndPK().setIdsociedad(sgdetalleObjInd.getSgobjetivoIndicadores().getSgobjetivoIndicadoresPK().getIdsociedad());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgobjetivoIndicadores sgobjetivoIndicadores = sgdetalleObjInd.getSgobjetivoIndicadores();
            if (sgobjetivoIndicadores != null) {
                sgobjetivoIndicadores = em.getReference(sgobjetivoIndicadores.getClass(), sgobjetivoIndicadores.getSgobjetivoIndicadoresPK());
                sgdetalleObjInd.setSgobjetivoIndicadores(sgobjetivoIndicadores);
            }
            SgindicadorC sgindicadorC = sgdetalleObjInd.getSgindicadorC();
            if (sgindicadorC != null) {
                sgindicadorC = em.getReference(sgindicadorC.getClass(), sgindicadorC.getSgindicadorCPK());
                sgdetalleObjInd.setSgindicadorC(sgindicadorC);
            }
            List<SgseguimientoIndObj> attachedSgseguimientoIndObjList = new ArrayList<SgseguimientoIndObj>();
            for (SgseguimientoIndObj sgseguimientoIndObjListSgseguimientoIndObjToAttach : sgdetalleObjInd.getSgseguimientoIndObjList()) {
                sgseguimientoIndObjListSgseguimientoIndObjToAttach = em.getReference(sgseguimientoIndObjListSgseguimientoIndObjToAttach.getClass(), sgseguimientoIndObjListSgseguimientoIndObjToAttach.getSgseguimientoIndObjPK());
                attachedSgseguimientoIndObjList.add(sgseguimientoIndObjListSgseguimientoIndObjToAttach);
            }
            sgdetalleObjInd.setSgseguimientoIndObjList(attachedSgseguimientoIndObjList);
            em.persist(sgdetalleObjInd);
            if (sgobjetivoIndicadores != null) {
                sgobjetivoIndicadores.getSgdetalleObjIndList().add(sgdetalleObjInd);
                sgobjetivoIndicadores = em.merge(sgobjetivoIndicadores);
            }
            if (sgindicadorC != null) {
                SgdetalleObjInd oldSgdetalleObjIndOfSgindicadorC = sgindicadorC.getSgdetalleObjInd();
                if (oldSgdetalleObjIndOfSgindicadorC != null) {
                    oldSgdetalleObjIndOfSgindicadorC.setSgindicadorC(null);
                    oldSgdetalleObjIndOfSgindicadorC = em.merge(oldSgdetalleObjIndOfSgindicadorC);
                }
                sgindicadorC.setSgdetalleObjInd(sgdetalleObjInd);
                sgindicadorC = em.merge(sgindicadorC);
            }
            for (SgseguimientoIndObj sgseguimientoIndObjListSgseguimientoIndObj : sgdetalleObjInd.getSgseguimientoIndObjList()) {
                SgdetalleObjInd oldSgdetalleObjIndOfSgseguimientoIndObjListSgseguimientoIndObj = sgseguimientoIndObjListSgseguimientoIndObj.getSgdetalleObjInd();
                sgseguimientoIndObjListSgseguimientoIndObj.setSgdetalleObjInd(sgdetalleObjInd);
                sgseguimientoIndObjListSgseguimientoIndObj = em.merge(sgseguimientoIndObjListSgseguimientoIndObj);
                if (oldSgdetalleObjIndOfSgseguimientoIndObjListSgseguimientoIndObj != null) {
                    oldSgdetalleObjIndOfSgseguimientoIndObjListSgseguimientoIndObj.getSgseguimientoIndObjList().remove(sgseguimientoIndObjListSgseguimientoIndObj);
                    oldSgdetalleObjIndOfSgseguimientoIndObjListSgseguimientoIndObj = em.merge(oldSgdetalleObjIndOfSgseguimientoIndObjListSgseguimientoIndObj);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgdetalleObjInd(sgdetalleObjInd.getSgdetalleObjIndPK()) != null) {
                throw new PreexistingEntityException("SgdetalleObjInd " + sgdetalleObjInd + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgdetalleObjInd sgdetalleObjInd) throws IllegalOrphanException, NonexistentEntityException, Exception {
        sgdetalleObjInd.getSgdetalleObjIndPK().setIdobjIndicador(sgdetalleObjInd.getSgobjetivoIndicadores().getSgobjetivoIndicadoresPK().getIdobjIndicador());
        sgdetalleObjInd.getSgdetalleObjIndPK().setIdsociedad(sgdetalleObjInd.getSgobjetivoIndicadores().getSgobjetivoIndicadoresPK().getIdsociedad());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgdetalleObjInd persistentSgdetalleObjInd = em.find(SgdetalleObjInd.class, sgdetalleObjInd.getSgdetalleObjIndPK());
            SgobjetivoIndicadores sgobjetivoIndicadoresOld = persistentSgdetalleObjInd.getSgobjetivoIndicadores();
            SgobjetivoIndicadores sgobjetivoIndicadoresNew = sgdetalleObjInd.getSgobjetivoIndicadores();
            SgindicadorC sgindicadorCOld = persistentSgdetalleObjInd.getSgindicadorC();
            SgindicadorC sgindicadorCNew = sgdetalleObjInd.getSgindicadorC();
            List<SgseguimientoIndObj> sgseguimientoIndObjListOld = persistentSgdetalleObjInd.getSgseguimientoIndObjList();
            List<SgseguimientoIndObj> sgseguimientoIndObjListNew = sgdetalleObjInd.getSgseguimientoIndObjList();
            List<String> illegalOrphanMessages = null;
            if (sgindicadorCOld != null && !sgindicadorCOld.equals(sgindicadorCNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain SgindicadorC " + sgindicadorCOld + " since its sgdetalleObjInd field is not nullable.");
            }
            for (SgseguimientoIndObj sgseguimientoIndObjListOldSgseguimientoIndObj : sgseguimientoIndObjListOld) {
                if (!sgseguimientoIndObjListNew.contains(sgseguimientoIndObjListOldSgseguimientoIndObj)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SgseguimientoIndObj " + sgseguimientoIndObjListOldSgseguimientoIndObj + " since its sgdetalleObjInd field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sgobjetivoIndicadoresNew != null) {
                sgobjetivoIndicadoresNew = em.getReference(sgobjetivoIndicadoresNew.getClass(), sgobjetivoIndicadoresNew.getSgobjetivoIndicadoresPK());
                sgdetalleObjInd.setSgobjetivoIndicadores(sgobjetivoIndicadoresNew);
            }
            if (sgindicadorCNew != null) {
                sgindicadorCNew = em.getReference(sgindicadorCNew.getClass(), sgindicadorCNew.getSgindicadorCPK());
                sgdetalleObjInd.setSgindicadorC(sgindicadorCNew);
            }
            List<SgseguimientoIndObj> attachedSgseguimientoIndObjListNew = new ArrayList<SgseguimientoIndObj>();
            for (SgseguimientoIndObj sgseguimientoIndObjListNewSgseguimientoIndObjToAttach : sgseguimientoIndObjListNew) {
                sgseguimientoIndObjListNewSgseguimientoIndObjToAttach = em.getReference(sgseguimientoIndObjListNewSgseguimientoIndObjToAttach.getClass(), sgseguimientoIndObjListNewSgseguimientoIndObjToAttach.getSgseguimientoIndObjPK());
                attachedSgseguimientoIndObjListNew.add(sgseguimientoIndObjListNewSgseguimientoIndObjToAttach);
            }
            sgseguimientoIndObjListNew = attachedSgseguimientoIndObjListNew;
            sgdetalleObjInd.setSgseguimientoIndObjList(sgseguimientoIndObjListNew);
            sgdetalleObjInd = em.merge(sgdetalleObjInd);
            if (sgobjetivoIndicadoresOld != null && !sgobjetivoIndicadoresOld.equals(sgobjetivoIndicadoresNew)) {
                sgobjetivoIndicadoresOld.getSgdetalleObjIndList().remove(sgdetalleObjInd);
                sgobjetivoIndicadoresOld = em.merge(sgobjetivoIndicadoresOld);
            }
            if (sgobjetivoIndicadoresNew != null && !sgobjetivoIndicadoresNew.equals(sgobjetivoIndicadoresOld)) {
                sgobjetivoIndicadoresNew.getSgdetalleObjIndList().add(sgdetalleObjInd);
                sgobjetivoIndicadoresNew = em.merge(sgobjetivoIndicadoresNew);
            }
            if (sgindicadorCNew != null && !sgindicadorCNew.equals(sgindicadorCOld)) {
                SgdetalleObjInd oldSgdetalleObjIndOfSgindicadorC = sgindicadorCNew.getSgdetalleObjInd();
                if (oldSgdetalleObjIndOfSgindicadorC != null) {
                    oldSgdetalleObjIndOfSgindicadorC.setSgindicadorC(null);
                    oldSgdetalleObjIndOfSgindicadorC = em.merge(oldSgdetalleObjIndOfSgindicadorC);
                }
                sgindicadorCNew.setSgdetalleObjInd(sgdetalleObjInd);
                sgindicadorCNew = em.merge(sgindicadorCNew);
            }
            for (SgseguimientoIndObj sgseguimientoIndObjListNewSgseguimientoIndObj : sgseguimientoIndObjListNew) {
                if (!sgseguimientoIndObjListOld.contains(sgseguimientoIndObjListNewSgseguimientoIndObj)) {
                    SgdetalleObjInd oldSgdetalleObjIndOfSgseguimientoIndObjListNewSgseguimientoIndObj = sgseguimientoIndObjListNewSgseguimientoIndObj.getSgdetalleObjInd();
                    sgseguimientoIndObjListNewSgseguimientoIndObj.setSgdetalleObjInd(sgdetalleObjInd);
                    sgseguimientoIndObjListNewSgseguimientoIndObj = em.merge(sgseguimientoIndObjListNewSgseguimientoIndObj);
                    if (oldSgdetalleObjIndOfSgseguimientoIndObjListNewSgseguimientoIndObj != null && !oldSgdetalleObjIndOfSgseguimientoIndObjListNewSgseguimientoIndObj.equals(sgdetalleObjInd)) {
                        oldSgdetalleObjIndOfSgseguimientoIndObjListNewSgseguimientoIndObj.getSgseguimientoIndObjList().remove(sgseguimientoIndObjListNewSgseguimientoIndObj);
                        oldSgdetalleObjIndOfSgseguimientoIndObjListNewSgseguimientoIndObj = em.merge(oldSgdetalleObjIndOfSgseguimientoIndObjListNewSgseguimientoIndObj);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgdetalleObjIndPK id = sgdetalleObjInd.getSgdetalleObjIndPK();
                if (findSgdetalleObjInd(id) == null) {
                    throw new NonexistentEntityException("The sgdetalleObjInd with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgdetalleObjIndPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgdetalleObjInd sgdetalleObjInd;
            try {
                sgdetalleObjInd = em.getReference(SgdetalleObjInd.class, id);
                sgdetalleObjInd.getSgdetalleObjIndPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgdetalleObjInd with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            SgindicadorC sgindicadorCOrphanCheck = sgdetalleObjInd.getSgindicadorC();
            if (sgindicadorCOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SgdetalleObjInd (" + sgdetalleObjInd + ") cannot be destroyed since the SgindicadorC " + sgindicadorCOrphanCheck + " in its sgindicadorC field has a non-nullable sgdetalleObjInd field.");
            }
            List<SgseguimientoIndObj> sgseguimientoIndObjListOrphanCheck = sgdetalleObjInd.getSgseguimientoIndObjList();
            for (SgseguimientoIndObj sgseguimientoIndObjListOrphanCheckSgseguimientoIndObj : sgseguimientoIndObjListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SgdetalleObjInd (" + sgdetalleObjInd + ") cannot be destroyed since the SgseguimientoIndObj " + sgseguimientoIndObjListOrphanCheckSgseguimientoIndObj + " in its sgseguimientoIndObjList field has a non-nullable sgdetalleObjInd field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            SgobjetivoIndicadores sgobjetivoIndicadores = sgdetalleObjInd.getSgobjetivoIndicadores();
            if (sgobjetivoIndicadores != null) {
                sgobjetivoIndicadores.getSgdetalleObjIndList().remove(sgdetalleObjInd);
                sgobjetivoIndicadores = em.merge(sgobjetivoIndicadores);
            }
            em.remove(sgdetalleObjInd);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgdetalleObjInd> findSgdetalleObjIndEntities() {
        return findSgdetalleObjIndEntities(true, -1, -1);
    }

    public List<SgdetalleObjInd> findSgdetalleObjIndEntities(int maxResults, int firstResult) {
        return findSgdetalleObjIndEntities(false, maxResults, firstResult);
    }

    private List<SgdetalleObjInd> findSgdetalleObjIndEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgdetalleObjInd.class));
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

    public SgdetalleObjInd findSgdetalleObjInd(SgdetalleObjIndPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgdetalleObjInd.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgdetalleObjIndCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgdetalleObjInd> rt = cq.from(SgdetalleObjInd.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
