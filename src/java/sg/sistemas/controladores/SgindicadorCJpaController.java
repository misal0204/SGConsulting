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
import sg.sistemas.entidades.SgdetalleObjInd;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgindicadorC;
import sg.sistemas.entidades.SgindicadorCPK;

/**
 *
 * @author Misael
 */
public class SgindicadorCJpaController implements Serializable {

    public SgindicadorCJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgindicadorC sgindicadorC) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (sgindicadorC.getSgindicadorCPK() == null) {
            sgindicadorC.setSgindicadorCPK(new SgindicadorCPK());
        }
        sgindicadorC.getSgindicadorCPK().setIdobjIndicador(sgindicadorC.getSgdetalleObjInd().getSgdetalleObjIndPK().getIdobjIndicador());
        sgindicadorC.getSgindicadorCPK().setIdsociedad(sgindicadorC.getSgdetalleObjInd().getSgdetalleObjIndPK().getIdsociedad());
        sgindicadorC.getSgindicadorCPK().setIddetalleIo(sgindicadorC.getSgdetalleObjInd().getSgdetalleObjIndPK().getIddetalleIo());
        List<String> illegalOrphanMessages = null;
        SgdetalleObjInd sgdetalleObjIndOrphanCheck = sgindicadorC.getSgdetalleObjInd();
        if (sgdetalleObjIndOrphanCheck != null) {
            SgindicadorC oldSgindicadorCOfSgdetalleObjInd = sgdetalleObjIndOrphanCheck.getSgindicadorC();
            if (oldSgindicadorCOfSgdetalleObjInd != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The SgdetalleObjInd " + sgdetalleObjIndOrphanCheck + " already has an item of type SgindicadorC whose sgdetalleObjInd column cannot be null. Please make another selection for the sgdetalleObjInd field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgdetalleObjInd sgdetalleObjInd = sgindicadorC.getSgdetalleObjInd();
            if (sgdetalleObjInd != null) {
                sgdetalleObjInd = em.getReference(sgdetalleObjInd.getClass(), sgdetalleObjInd.getSgdetalleObjIndPK());
                sgindicadorC.setSgdetalleObjInd(sgdetalleObjInd);
            }
            em.persist(sgindicadorC);
            if (sgdetalleObjInd != null) {
                sgdetalleObjInd.setSgindicadorC(sgindicadorC);
                sgdetalleObjInd = em.merge(sgdetalleObjInd);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgindicadorC(sgindicadorC.getSgindicadorCPK()) != null) {
                throw new PreexistingEntityException("SgindicadorC " + sgindicadorC + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgindicadorC sgindicadorC) throws IllegalOrphanException, NonexistentEntityException, Exception {
        sgindicadorC.getSgindicadorCPK().setIdobjIndicador(sgindicadorC.getSgdetalleObjInd().getSgdetalleObjIndPK().getIdobjIndicador());
        sgindicadorC.getSgindicadorCPK().setIdsociedad(sgindicadorC.getSgdetalleObjInd().getSgdetalleObjIndPK().getIdsociedad());
        sgindicadorC.getSgindicadorCPK().setIddetalleIo(sgindicadorC.getSgdetalleObjInd().getSgdetalleObjIndPK().getIddetalleIo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgindicadorC persistentSgindicadorC = em.find(SgindicadorC.class, sgindicadorC.getSgindicadorCPK());
            SgdetalleObjInd sgdetalleObjIndOld = persistentSgindicadorC.getSgdetalleObjInd();
            SgdetalleObjInd sgdetalleObjIndNew = sgindicadorC.getSgdetalleObjInd();
            List<String> illegalOrphanMessages = null;
            if (sgdetalleObjIndNew != null && !sgdetalleObjIndNew.equals(sgdetalleObjIndOld)) {
                SgindicadorC oldSgindicadorCOfSgdetalleObjInd = sgdetalleObjIndNew.getSgindicadorC();
                if (oldSgindicadorCOfSgdetalleObjInd != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The SgdetalleObjInd " + sgdetalleObjIndNew + " already has an item of type SgindicadorC whose sgdetalleObjInd column cannot be null. Please make another selection for the sgdetalleObjInd field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sgdetalleObjIndNew != null) {
                sgdetalleObjIndNew = em.getReference(sgdetalleObjIndNew.getClass(), sgdetalleObjIndNew.getSgdetalleObjIndPK());
                sgindicadorC.setSgdetalleObjInd(sgdetalleObjIndNew);
            }
            sgindicadorC = em.merge(sgindicadorC);
            if (sgdetalleObjIndOld != null && !sgdetalleObjIndOld.equals(sgdetalleObjIndNew)) {
                sgdetalleObjIndOld.setSgindicadorC(null);
                sgdetalleObjIndOld = em.merge(sgdetalleObjIndOld);
            }
            if (sgdetalleObjIndNew != null && !sgdetalleObjIndNew.equals(sgdetalleObjIndOld)) {
                sgdetalleObjIndNew.setSgindicadorC(sgindicadorC);
                sgdetalleObjIndNew = em.merge(sgdetalleObjIndNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgindicadorCPK id = sgindicadorC.getSgindicadorCPK();
                if (findSgindicadorC(id) == null) {
                    throw new NonexistentEntityException("The sgindicadorC with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgindicadorCPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgindicadorC sgindicadorC;
            try {
                sgindicadorC = em.getReference(SgindicadorC.class, id);
                sgindicadorC.getSgindicadorCPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgindicadorC with id " + id + " no longer exists.", enfe);
            }
            SgdetalleObjInd sgdetalleObjInd = sgindicadorC.getSgdetalleObjInd();
            if (sgdetalleObjInd != null) {
                sgdetalleObjInd.setSgindicadorC(null);
                sgdetalleObjInd = em.merge(sgdetalleObjInd);
            }
            em.remove(sgindicadorC);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgindicadorC> findSgindicadorCEntities() {
        return findSgindicadorCEntities(true, -1, -1);
    }

    public List<SgindicadorC> findSgindicadorCEntities(int maxResults, int firstResult) {
        return findSgindicadorCEntities(false, maxResults, firstResult);
    }

    private List<SgindicadorC> findSgindicadorCEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgindicadorC.class));
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

    public SgindicadorC findSgindicadorC(SgindicadorCPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgindicadorC.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgindicadorCCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgindicadorC> rt = cq.from(SgindicadorC.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
