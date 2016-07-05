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
import sg.sistemas.entidades.SgProgramas;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgMenus;

/**
 *
 * @author Misael
 */
public class SgMenusJpaController implements Serializable {

    public SgMenusJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgMenus sgMenus) throws PreexistingEntityException, Exception {
        if (sgMenus.getSgProgramasList() == null) {
            sgMenus.setSgProgramasList(new ArrayList<SgProgramas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SgProgramas> attachedSgProgramasList = new ArrayList<SgProgramas>();
            for (SgProgramas sgProgramasListSgProgramasToAttach : sgMenus.getSgProgramasList()) {
                sgProgramasListSgProgramasToAttach = em.getReference(sgProgramasListSgProgramasToAttach.getClass(), sgProgramasListSgProgramasToAttach.getSgProgramasPK());
                attachedSgProgramasList.add(sgProgramasListSgProgramasToAttach);
            }
            sgMenus.setSgProgramasList(attachedSgProgramasList);
            em.persist(sgMenus);
            for (SgProgramas sgProgramasListSgProgramas : sgMenus.getSgProgramasList()) {
                SgMenus oldSgMenusOfSgProgramasListSgProgramas = sgProgramasListSgProgramas.getSgMenus();
                sgProgramasListSgProgramas.setSgMenus(sgMenus);
                sgProgramasListSgProgramas = em.merge(sgProgramasListSgProgramas);
                if (oldSgMenusOfSgProgramasListSgProgramas != null) {
                    oldSgMenusOfSgProgramasListSgProgramas.getSgProgramasList().remove(sgProgramasListSgProgramas);
                    oldSgMenusOfSgProgramasListSgProgramas = em.merge(oldSgMenusOfSgProgramasListSgProgramas);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgMenus(sgMenus.getIdmenu()) != null) {
                throw new PreexistingEntityException("SgMenus " + sgMenus + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgMenus sgMenus) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgMenus persistentSgMenus = em.find(SgMenus.class, sgMenus.getIdmenu());
            List<SgProgramas> sgProgramasListOld = persistentSgMenus.getSgProgramasList();
            List<SgProgramas> sgProgramasListNew = sgMenus.getSgProgramasList();
            List<String> illegalOrphanMessages = null;
            for (SgProgramas sgProgramasListOldSgProgramas : sgProgramasListOld) {
                if (!sgProgramasListNew.contains(sgProgramasListOldSgProgramas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SgProgramas " + sgProgramasListOldSgProgramas + " since its sgMenus field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<SgProgramas> attachedSgProgramasListNew = new ArrayList<SgProgramas>();
            for (SgProgramas sgProgramasListNewSgProgramasToAttach : sgProgramasListNew) {
                sgProgramasListNewSgProgramasToAttach = em.getReference(sgProgramasListNewSgProgramasToAttach.getClass(), sgProgramasListNewSgProgramasToAttach.getSgProgramasPK());
                attachedSgProgramasListNew.add(sgProgramasListNewSgProgramasToAttach);
            }
            sgProgramasListNew = attachedSgProgramasListNew;
            sgMenus.setSgProgramasList(sgProgramasListNew);
            sgMenus = em.merge(sgMenus);
            for (SgProgramas sgProgramasListNewSgProgramas : sgProgramasListNew) {
                if (!sgProgramasListOld.contains(sgProgramasListNewSgProgramas)) {
                    SgMenus oldSgMenusOfSgProgramasListNewSgProgramas = sgProgramasListNewSgProgramas.getSgMenus();
                    sgProgramasListNewSgProgramas.setSgMenus(sgMenus);
                    sgProgramasListNewSgProgramas = em.merge(sgProgramasListNewSgProgramas);
                    if (oldSgMenusOfSgProgramasListNewSgProgramas != null && !oldSgMenusOfSgProgramasListNewSgProgramas.equals(sgMenus)) {
                        oldSgMenusOfSgProgramasListNewSgProgramas.getSgProgramasList().remove(sgProgramasListNewSgProgramas);
                        oldSgMenusOfSgProgramasListNewSgProgramas = em.merge(oldSgMenusOfSgProgramasListNewSgProgramas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Double id = sgMenus.getIdmenu();
                if (findSgMenus(id) == null) {
                    throw new NonexistentEntityException("The sgMenus with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Double id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgMenus sgMenus;
            try {
                sgMenus = em.getReference(SgMenus.class, id);
                sgMenus.getIdmenu();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgMenus with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SgProgramas> sgProgramasListOrphanCheck = sgMenus.getSgProgramasList();
            for (SgProgramas sgProgramasListOrphanCheckSgProgramas : sgProgramasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SgMenus (" + sgMenus + ") cannot be destroyed since the SgProgramas " + sgProgramasListOrphanCheckSgProgramas + " in its sgProgramasList field has a non-nullable sgMenus field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(sgMenus);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgMenus> findSgMenusEntities() {
        return findSgMenusEntities(true, -1, -1);
    }

    public List<SgMenus> findSgMenusEntities(int maxResults, int firstResult) {
        return findSgMenusEntities(false, maxResults, firstResult);
    }

    private List<SgMenus> findSgMenusEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgMenus.class));
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

    public SgMenus findSgMenus(Double id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgMenus.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgMenusCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgMenus> rt = cq.from(SgMenus.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
