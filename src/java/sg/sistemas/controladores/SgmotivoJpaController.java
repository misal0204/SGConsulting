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
import sg.sistemas.entidades.Sgorigen;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.Sgmotivo;

/**
 *
 * @author Misael
 */
public class SgmotivoJpaController implements Serializable {

    public SgmotivoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sgmotivo sgmotivo) throws PreexistingEntityException, Exception {
        if (sgmotivo.getSgorigenList() == null) {
            sgmotivo.setSgorigenList(new ArrayList<Sgorigen>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Sgorigen> attachedSgorigenList = new ArrayList<Sgorigen>();
            for (Sgorigen sgorigenListSgorigenToAttach : sgmotivo.getSgorigenList()) {
                sgorigenListSgorigenToAttach = em.getReference(sgorigenListSgorigenToAttach.getClass(), sgorigenListSgorigenToAttach.getIdorigen());
                attachedSgorigenList.add(sgorigenListSgorigenToAttach);
            }
            sgmotivo.setSgorigenList(attachedSgorigenList);
            em.persist(sgmotivo);
            for (Sgorigen sgorigenListSgorigen : sgmotivo.getSgorigenList()) {
                sgorigenListSgorigen.getSgmotivoList().add(sgmotivo);
                sgorigenListSgorigen = em.merge(sgorigenListSgorigen);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgmotivo(sgmotivo.getIdmotivo()) != null) {
                throw new PreexistingEntityException("Sgmotivo " + sgmotivo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sgmotivo sgmotivo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgmotivo persistentSgmotivo = em.find(Sgmotivo.class, sgmotivo.getIdmotivo());
            List<Sgorigen> sgorigenListOld = persistentSgmotivo.getSgorigenList();
            List<Sgorigen> sgorigenListNew = sgmotivo.getSgorigenList();
            List<Sgorigen> attachedSgorigenListNew = new ArrayList<Sgorigen>();
            for (Sgorigen sgorigenListNewSgorigenToAttach : sgorigenListNew) {
                sgorigenListNewSgorigenToAttach = em.getReference(sgorigenListNewSgorigenToAttach.getClass(), sgorigenListNewSgorigenToAttach.getIdorigen());
                attachedSgorigenListNew.add(sgorigenListNewSgorigenToAttach);
            }
            sgorigenListNew = attachedSgorigenListNew;
            sgmotivo.setSgorigenList(sgorigenListNew);
            sgmotivo = em.merge(sgmotivo);
            for (Sgorigen sgorigenListOldSgorigen : sgorigenListOld) {
                if (!sgorigenListNew.contains(sgorigenListOldSgorigen)) {
                    sgorigenListOldSgorigen.getSgmotivoList().remove(sgmotivo);
                    sgorigenListOldSgorigen = em.merge(sgorigenListOldSgorigen);
                }
            }
            for (Sgorigen sgorigenListNewSgorigen : sgorigenListNew) {
                if (!sgorigenListOld.contains(sgorigenListNewSgorigen)) {
                    sgorigenListNewSgorigen.getSgmotivoList().add(sgmotivo);
                    sgorigenListNewSgorigen = em.merge(sgorigenListNewSgorigen);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = sgmotivo.getIdmotivo();
                if (findSgmotivo(id) == null) {
                    throw new NonexistentEntityException("The sgmotivo with id " + id + " no longer exists.");
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
            Sgmotivo sgmotivo;
            try {
                sgmotivo = em.getReference(Sgmotivo.class, id);
                sgmotivo.getIdmotivo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgmotivo with id " + id + " no longer exists.", enfe);
            }
            List<Sgorigen> sgorigenList = sgmotivo.getSgorigenList();
            for (Sgorigen sgorigenListSgorigen : sgorigenList) {
                sgorigenListSgorigen.getSgmotivoList().remove(sgmotivo);
                sgorigenListSgorigen = em.merge(sgorigenListSgorigen);
            }
            em.remove(sgmotivo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sgmotivo> findSgmotivoEntities() {
        return findSgmotivoEntities(true, -1, -1);
    }

    public List<Sgmotivo> findSgmotivoEntities(int maxResults, int firstResult) {
        return findSgmotivoEntities(false, maxResults, firstResult);
    }

    private List<Sgmotivo> findSgmotivoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sgmotivo.class));
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

    public Sgmotivo findSgmotivo(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sgmotivo.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgmotivoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sgmotivo> rt = cq.from(Sgmotivo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
