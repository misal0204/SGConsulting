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
import sg.sistemas.entidades.SgUsuariorol;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgRol;

/**
 *
 * @author Misael
 */
public class SgRolJpaController implements Serializable {

    public SgRolJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgRol sgRol) throws PreexistingEntityException, Exception {
        if (sgRol.getSgUsuariorolList() == null) {
            sgRol.setSgUsuariorolList(new ArrayList<SgUsuariorol>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SgUsuariorol> attachedSgUsuariorolList = new ArrayList<SgUsuariorol>();
            for (SgUsuariorol sgUsuariorolListSgUsuariorolToAttach : sgRol.getSgUsuariorolList()) {
                sgUsuariorolListSgUsuariorolToAttach = em.getReference(sgUsuariorolListSgUsuariorolToAttach.getClass(), sgUsuariorolListSgUsuariorolToAttach.getSgUsuariorolPK());
                attachedSgUsuariorolList.add(sgUsuariorolListSgUsuariorolToAttach);
            }
            sgRol.setSgUsuariorolList(attachedSgUsuariorolList);
            em.persist(sgRol);
            for (SgUsuariorol sgUsuariorolListSgUsuariorol : sgRol.getSgUsuariorolList()) {
                SgRol oldSgRolOfSgUsuariorolListSgUsuariorol = sgUsuariorolListSgUsuariorol.getSgRol();
                sgUsuariorolListSgUsuariorol.setSgRol(sgRol);
                sgUsuariorolListSgUsuariorol = em.merge(sgUsuariorolListSgUsuariorol);
                if (oldSgRolOfSgUsuariorolListSgUsuariorol != null) {
                    oldSgRolOfSgUsuariorolListSgUsuariorol.getSgUsuariorolList().remove(sgUsuariorolListSgUsuariorol);
                    oldSgRolOfSgUsuariorolListSgUsuariorol = em.merge(oldSgRolOfSgUsuariorolListSgUsuariorol);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgRol(sgRol.getIdrol()) != null) {
                throw new PreexistingEntityException("SgRol " + sgRol + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgRol sgRol) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgRol persistentSgRol = em.find(SgRol.class, sgRol.getIdrol());
            List<SgUsuariorol> sgUsuariorolListOld = persistentSgRol.getSgUsuariorolList();
            List<SgUsuariorol> sgUsuariorolListNew = sgRol.getSgUsuariorolList();
            List<String> illegalOrphanMessages = null;
            for (SgUsuariorol sgUsuariorolListOldSgUsuariorol : sgUsuariorolListOld) {
                if (!sgUsuariorolListNew.contains(sgUsuariorolListOldSgUsuariorol)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SgUsuariorol " + sgUsuariorolListOldSgUsuariorol + " since its sgRol field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<SgUsuariorol> attachedSgUsuariorolListNew = new ArrayList<SgUsuariorol>();
            for (SgUsuariorol sgUsuariorolListNewSgUsuariorolToAttach : sgUsuariorolListNew) {
                sgUsuariorolListNewSgUsuariorolToAttach = em.getReference(sgUsuariorolListNewSgUsuariorolToAttach.getClass(), sgUsuariorolListNewSgUsuariorolToAttach.getSgUsuariorolPK());
                attachedSgUsuariorolListNew.add(sgUsuariorolListNewSgUsuariorolToAttach);
            }
            sgUsuariorolListNew = attachedSgUsuariorolListNew;
            sgRol.setSgUsuariorolList(sgUsuariorolListNew);
            sgRol = em.merge(sgRol);
            for (SgUsuariorol sgUsuariorolListNewSgUsuariorol : sgUsuariorolListNew) {
                if (!sgUsuariorolListOld.contains(sgUsuariorolListNewSgUsuariorol)) {
                    SgRol oldSgRolOfSgUsuariorolListNewSgUsuariorol = sgUsuariorolListNewSgUsuariorol.getSgRol();
                    sgUsuariorolListNewSgUsuariorol.setSgRol(sgRol);
                    sgUsuariorolListNewSgUsuariorol = em.merge(sgUsuariorolListNewSgUsuariorol);
                    if (oldSgRolOfSgUsuariorolListNewSgUsuariorol != null && !oldSgRolOfSgUsuariorolListNewSgUsuariorol.equals(sgRol)) {
                        oldSgRolOfSgUsuariorolListNewSgUsuariorol.getSgUsuariorolList().remove(sgUsuariorolListNewSgUsuariorol);
                        oldSgRolOfSgUsuariorolListNewSgUsuariorol = em.merge(oldSgRolOfSgUsuariorolListNewSgUsuariorol);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = sgRol.getIdrol();
                if (findSgRol(id) == null) {
                    throw new NonexistentEntityException("The sgRol with id " + id + " no longer exists.");
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
            SgRol sgRol;
            try {
                sgRol = em.getReference(SgRol.class, id);
                sgRol.getIdrol();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgRol with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SgUsuariorol> sgUsuariorolListOrphanCheck = sgRol.getSgUsuariorolList();
            for (SgUsuariorol sgUsuariorolListOrphanCheckSgUsuariorol : sgUsuariorolListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SgRol (" + sgRol + ") cannot be destroyed since the SgUsuariorol " + sgUsuariorolListOrphanCheckSgUsuariorol + " in its sgUsuariorolList field has a non-nullable sgRol field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(sgRol);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgRol> findSgRolEntities() {
        return findSgRolEntities(true, -1, -1);
    }

    public List<SgRol> findSgRolEntities(int maxResults, int firstResult) {
        return findSgRolEntities(false, maxResults, firstResult);
    }

    private List<SgRol> findSgRolEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgRol.class));
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

    public SgRol findSgRol(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgRol.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgRolCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgRol> rt = cq.from(SgRol.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
