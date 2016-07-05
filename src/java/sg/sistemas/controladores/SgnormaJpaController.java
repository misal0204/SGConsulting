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
import sg.sistemas.entidades.SgpuntoNorma;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.Sgnorma;

/**
 *
 * @author Misael
 */
public class SgnormaJpaController implements Serializable {

    public SgnormaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sgnorma sgnorma) throws PreexistingEntityException, Exception {
        if (sgnorma.getSgpuntoNormaList() == null) {
            sgnorma.setSgpuntoNormaList(new ArrayList<SgpuntoNorma>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SgpuntoNorma> attachedSgpuntoNormaList = new ArrayList<SgpuntoNorma>();
            for (SgpuntoNorma sgpuntoNormaListSgpuntoNormaToAttach : sgnorma.getSgpuntoNormaList()) {
                sgpuntoNormaListSgpuntoNormaToAttach = em.getReference(sgpuntoNormaListSgpuntoNormaToAttach.getClass(), sgpuntoNormaListSgpuntoNormaToAttach.getSgpuntoNormaPK());
                attachedSgpuntoNormaList.add(sgpuntoNormaListSgpuntoNormaToAttach);
            }
            sgnorma.setSgpuntoNormaList(attachedSgpuntoNormaList);
            em.persist(sgnorma);
            for (SgpuntoNorma sgpuntoNormaListSgpuntoNorma : sgnorma.getSgpuntoNormaList()) {
                Sgnorma oldSgnormaOfSgpuntoNormaListSgpuntoNorma = sgpuntoNormaListSgpuntoNorma.getSgnorma();
                sgpuntoNormaListSgpuntoNorma.setSgnorma(sgnorma);
                sgpuntoNormaListSgpuntoNorma = em.merge(sgpuntoNormaListSgpuntoNorma);
                if (oldSgnormaOfSgpuntoNormaListSgpuntoNorma != null) {
                    oldSgnormaOfSgpuntoNormaListSgpuntoNorma.getSgpuntoNormaList().remove(sgpuntoNormaListSgpuntoNorma);
                    oldSgnormaOfSgpuntoNormaListSgpuntoNorma = em.merge(oldSgnormaOfSgpuntoNormaListSgpuntoNorma);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgnorma(sgnorma.getIdnorma()) != null) {
                throw new PreexistingEntityException("Sgnorma " + sgnorma + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sgnorma sgnorma) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgnorma persistentSgnorma = em.find(Sgnorma.class, sgnorma.getIdnorma());
            List<SgpuntoNorma> sgpuntoNormaListOld = persistentSgnorma.getSgpuntoNormaList();
            List<SgpuntoNorma> sgpuntoNormaListNew = sgnorma.getSgpuntoNormaList();
            List<String> illegalOrphanMessages = null;
            for (SgpuntoNorma sgpuntoNormaListOldSgpuntoNorma : sgpuntoNormaListOld) {
                if (!sgpuntoNormaListNew.contains(sgpuntoNormaListOldSgpuntoNorma)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SgpuntoNorma " + sgpuntoNormaListOldSgpuntoNorma + " since its sgnorma field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<SgpuntoNorma> attachedSgpuntoNormaListNew = new ArrayList<SgpuntoNorma>();
            for (SgpuntoNorma sgpuntoNormaListNewSgpuntoNormaToAttach : sgpuntoNormaListNew) {
                sgpuntoNormaListNewSgpuntoNormaToAttach = em.getReference(sgpuntoNormaListNewSgpuntoNormaToAttach.getClass(), sgpuntoNormaListNewSgpuntoNormaToAttach.getSgpuntoNormaPK());
                attachedSgpuntoNormaListNew.add(sgpuntoNormaListNewSgpuntoNormaToAttach);
            }
            sgpuntoNormaListNew = attachedSgpuntoNormaListNew;
            sgnorma.setSgpuntoNormaList(sgpuntoNormaListNew);
            sgnorma = em.merge(sgnorma);
            for (SgpuntoNorma sgpuntoNormaListNewSgpuntoNorma : sgpuntoNormaListNew) {
                if (!sgpuntoNormaListOld.contains(sgpuntoNormaListNewSgpuntoNorma)) {
                    Sgnorma oldSgnormaOfSgpuntoNormaListNewSgpuntoNorma = sgpuntoNormaListNewSgpuntoNorma.getSgnorma();
                    sgpuntoNormaListNewSgpuntoNorma.setSgnorma(sgnorma);
                    sgpuntoNormaListNewSgpuntoNorma = em.merge(sgpuntoNormaListNewSgpuntoNorma);
                    if (oldSgnormaOfSgpuntoNormaListNewSgpuntoNorma != null && !oldSgnormaOfSgpuntoNormaListNewSgpuntoNorma.equals(sgnorma)) {
                        oldSgnormaOfSgpuntoNormaListNewSgpuntoNorma.getSgpuntoNormaList().remove(sgpuntoNormaListNewSgpuntoNorma);
                        oldSgnormaOfSgpuntoNormaListNewSgpuntoNorma = em.merge(oldSgnormaOfSgpuntoNormaListNewSgpuntoNorma);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = sgnorma.getIdnorma();
                if (findSgnorma(id) == null) {
                    throw new NonexistentEntityException("The sgnorma with id " + id + " no longer exists.");
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
            Sgnorma sgnorma;
            try {
                sgnorma = em.getReference(Sgnorma.class, id);
                sgnorma.getIdnorma();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgnorma with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SgpuntoNorma> sgpuntoNormaListOrphanCheck = sgnorma.getSgpuntoNormaList();
            for (SgpuntoNorma sgpuntoNormaListOrphanCheckSgpuntoNorma : sgpuntoNormaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sgnorma (" + sgnorma + ") cannot be destroyed since the SgpuntoNorma " + sgpuntoNormaListOrphanCheckSgpuntoNorma + " in its sgpuntoNormaList field has a non-nullable sgnorma field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(sgnorma);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sgnorma> findSgnormaEntities() {
        return findSgnormaEntities(true, -1, -1);
    }

    public List<Sgnorma> findSgnormaEntities(int maxResults, int firstResult) {
        return findSgnormaEntities(false, maxResults, firstResult);
    }

    private List<Sgnorma> findSgnormaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sgnorma.class));
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

    public Sgnorma findSgnorma(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sgnorma.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgnormaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sgnorma> rt = cq.from(Sgnorma.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
