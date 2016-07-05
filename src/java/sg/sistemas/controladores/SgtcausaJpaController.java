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
import sg.sistemas.entidades.Sgcausa;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.Sgtcausa;

/**
 *
 * @author Misael
 */
public class SgtcausaJpaController implements Serializable {

    public SgtcausaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sgtcausa sgtcausa) throws PreexistingEntityException, Exception {
        if (sgtcausa.getSgcausaList() == null) {
            sgtcausa.setSgcausaList(new ArrayList<Sgcausa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Sgcausa> attachedSgcausaList = new ArrayList<Sgcausa>();
            for (Sgcausa sgcausaListSgcausaToAttach : sgtcausa.getSgcausaList()) {
                sgcausaListSgcausaToAttach = em.getReference(sgcausaListSgcausaToAttach.getClass(), sgcausaListSgcausaToAttach.getSgcausaPK());
                attachedSgcausaList.add(sgcausaListSgcausaToAttach);
            }
            sgtcausa.setSgcausaList(attachedSgcausaList);
            em.persist(sgtcausa);
            for (Sgcausa sgcausaListSgcausa : sgtcausa.getSgcausaList()) {
                Sgtcausa oldIdtcausaOfSgcausaListSgcausa = sgcausaListSgcausa.getIdtcausa();
                sgcausaListSgcausa.setIdtcausa(sgtcausa);
                sgcausaListSgcausa = em.merge(sgcausaListSgcausa);
                if (oldIdtcausaOfSgcausaListSgcausa != null) {
                    oldIdtcausaOfSgcausaListSgcausa.getSgcausaList().remove(sgcausaListSgcausa);
                    oldIdtcausaOfSgcausaListSgcausa = em.merge(oldIdtcausaOfSgcausaListSgcausa);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgtcausa(sgtcausa.getIdtcausa()) != null) {
                throw new PreexistingEntityException("Sgtcausa " + sgtcausa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sgtcausa sgtcausa) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgtcausa persistentSgtcausa = em.find(Sgtcausa.class, sgtcausa.getIdtcausa());
            List<Sgcausa> sgcausaListOld = persistentSgtcausa.getSgcausaList();
            List<Sgcausa> sgcausaListNew = sgtcausa.getSgcausaList();
            List<String> illegalOrphanMessages = null;
            for (Sgcausa sgcausaListOldSgcausa : sgcausaListOld) {
                if (!sgcausaListNew.contains(sgcausaListOldSgcausa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sgcausa " + sgcausaListOldSgcausa + " since its idtcausa field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Sgcausa> attachedSgcausaListNew = new ArrayList<Sgcausa>();
            for (Sgcausa sgcausaListNewSgcausaToAttach : sgcausaListNew) {
                sgcausaListNewSgcausaToAttach = em.getReference(sgcausaListNewSgcausaToAttach.getClass(), sgcausaListNewSgcausaToAttach.getSgcausaPK());
                attachedSgcausaListNew.add(sgcausaListNewSgcausaToAttach);
            }
            sgcausaListNew = attachedSgcausaListNew;
            sgtcausa.setSgcausaList(sgcausaListNew);
            sgtcausa = em.merge(sgtcausa);
            for (Sgcausa sgcausaListNewSgcausa : sgcausaListNew) {
                if (!sgcausaListOld.contains(sgcausaListNewSgcausa)) {
                    Sgtcausa oldIdtcausaOfSgcausaListNewSgcausa = sgcausaListNewSgcausa.getIdtcausa();
                    sgcausaListNewSgcausa.setIdtcausa(sgtcausa);
                    sgcausaListNewSgcausa = em.merge(sgcausaListNewSgcausa);
                    if (oldIdtcausaOfSgcausaListNewSgcausa != null && !oldIdtcausaOfSgcausaListNewSgcausa.equals(sgtcausa)) {
                        oldIdtcausaOfSgcausaListNewSgcausa.getSgcausaList().remove(sgcausaListNewSgcausa);
                        oldIdtcausaOfSgcausaListNewSgcausa = em.merge(oldIdtcausaOfSgcausaListNewSgcausa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = sgtcausa.getIdtcausa();
                if (findSgtcausa(id) == null) {
                    throw new NonexistentEntityException("The sgtcausa with id " + id + " no longer exists.");
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
            Sgtcausa sgtcausa;
            try {
                sgtcausa = em.getReference(Sgtcausa.class, id);
                sgtcausa.getIdtcausa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgtcausa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Sgcausa> sgcausaListOrphanCheck = sgtcausa.getSgcausaList();
            for (Sgcausa sgcausaListOrphanCheckSgcausa : sgcausaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sgtcausa (" + sgtcausa + ") cannot be destroyed since the Sgcausa " + sgcausaListOrphanCheckSgcausa + " in its sgcausaList field has a non-nullable idtcausa field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(sgtcausa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sgtcausa> findSgtcausaEntities() {
        return findSgtcausaEntities(true, -1, -1);
    }

    public List<Sgtcausa> findSgtcausaEntities(int maxResults, int firstResult) {
        return findSgtcausaEntities(false, maxResults, firstResult);
    }

    private List<Sgtcausa> findSgtcausaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sgtcausa.class));
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

    public Sgtcausa findSgtcausa(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sgtcausa.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgtcausaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sgtcausa> rt = cq.from(Sgtcausa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
