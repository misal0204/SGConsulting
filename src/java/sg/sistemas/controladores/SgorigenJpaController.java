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
import sg.sistemas.entidades.Sgmotivo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.Sgorigen;

/**
 *
 * @author Misael
 */
public class SgorigenJpaController implements Serializable {

    public SgorigenJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sgorigen sgorigen) throws PreexistingEntityException, Exception {
        if (sgorigen.getSgmotivoList() == null) {
            sgorigen.setSgmotivoList(new ArrayList<Sgmotivo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Sgmotivo> attachedSgmotivoList = new ArrayList<Sgmotivo>();
            for (Sgmotivo sgmotivoListSgmotivoToAttach : sgorigen.getSgmotivoList()) {
                sgmotivoListSgmotivoToAttach = em.getReference(sgmotivoListSgmotivoToAttach.getClass(), sgmotivoListSgmotivoToAttach.getIdmotivo());
                attachedSgmotivoList.add(sgmotivoListSgmotivoToAttach);
            }
            sgorigen.setSgmotivoList(attachedSgmotivoList);
            em.persist(sgorigen);
            for (Sgmotivo sgmotivoListSgmotivo : sgorigen.getSgmotivoList()) {
                sgmotivoListSgmotivo.getSgorigenList().add(sgorigen);
                sgmotivoListSgmotivo = em.merge(sgmotivoListSgmotivo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgorigen(sgorigen.getIdorigen()) != null) {
                throw new PreexistingEntityException("Sgorigen " + sgorigen + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sgorigen sgorigen) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgorigen persistentSgorigen = em.find(Sgorigen.class, sgorigen.getIdorigen());
            List<Sgmotivo> sgmotivoListOld = persistentSgorigen.getSgmotivoList();
            List<Sgmotivo> sgmotivoListNew = sgorigen.getSgmotivoList();
            List<Sgmotivo> attachedSgmotivoListNew = new ArrayList<Sgmotivo>();
            for (Sgmotivo sgmotivoListNewSgmotivoToAttach : sgmotivoListNew) {
                sgmotivoListNewSgmotivoToAttach = em.getReference(sgmotivoListNewSgmotivoToAttach.getClass(), sgmotivoListNewSgmotivoToAttach.getIdmotivo());
                attachedSgmotivoListNew.add(sgmotivoListNewSgmotivoToAttach);
            }
            sgmotivoListNew = attachedSgmotivoListNew;
            sgorigen.setSgmotivoList(sgmotivoListNew);
            sgorigen = em.merge(sgorigen);
            for (Sgmotivo sgmotivoListOldSgmotivo : sgmotivoListOld) {
                if (!sgmotivoListNew.contains(sgmotivoListOldSgmotivo)) {
                    sgmotivoListOldSgmotivo.getSgorigenList().remove(sgorigen);
                    sgmotivoListOldSgmotivo = em.merge(sgmotivoListOldSgmotivo);
                }
            }
            for (Sgmotivo sgmotivoListNewSgmotivo : sgmotivoListNew) {
                if (!sgmotivoListOld.contains(sgmotivoListNewSgmotivo)) {
                    sgmotivoListNewSgmotivo.getSgorigenList().add(sgorigen);
                    sgmotivoListNewSgmotivo = em.merge(sgmotivoListNewSgmotivo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = sgorigen.getIdorigen();
                if (findSgorigen(id) == null) {
                    throw new NonexistentEntityException("The sgorigen with id " + id + " no longer exists.");
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
            Sgorigen sgorigen;
            try {
                sgorigen = em.getReference(Sgorigen.class, id);
                sgorigen.getIdorigen();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgorigen with id " + id + " no longer exists.", enfe);
            }
            List<Sgmotivo> sgmotivoList = sgorigen.getSgmotivoList();
            for (Sgmotivo sgmotivoListSgmotivo : sgmotivoList) {
                sgmotivoListSgmotivo.getSgorigenList().remove(sgorigen);
                sgmotivoListSgmotivo = em.merge(sgmotivoListSgmotivo);
            }
            em.remove(sgorigen);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sgorigen> findSgorigenEntities() {
        return findSgorigenEntities(true, -1, -1);
    }

    public List<Sgorigen> findSgorigenEntities(int maxResults, int firstResult) {
        return findSgorigenEntities(false, maxResults, firstResult);
    }

    private List<Sgorigen> findSgorigenEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sgorigen.class));
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

    public Sgorigen findSgorigen(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sgorigen.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgorigenCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sgorigen> rt = cq.from(Sgorigen.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
