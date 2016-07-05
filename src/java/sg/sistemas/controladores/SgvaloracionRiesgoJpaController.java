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
import sg.sistemas.entidades.SgprocesoRiesgo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgvaloracionRiesgo;

/**
 *
 * @author Misael
 */
public class SgvaloracionRiesgoJpaController implements Serializable {

    public SgvaloracionRiesgoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgvaloracionRiesgo sgvaloracionRiesgo) throws PreexistingEntityException, Exception {
        if (sgvaloracionRiesgo.getSgprocesoRiesgoList() == null) {
            sgvaloracionRiesgo.setSgprocesoRiesgoList(new ArrayList<SgprocesoRiesgo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SgprocesoRiesgo> attachedSgprocesoRiesgoList = new ArrayList<SgprocesoRiesgo>();
            for (SgprocesoRiesgo sgprocesoRiesgoListSgprocesoRiesgoToAttach : sgvaloracionRiesgo.getSgprocesoRiesgoList()) {
                sgprocesoRiesgoListSgprocesoRiesgoToAttach = em.getReference(sgprocesoRiesgoListSgprocesoRiesgoToAttach.getClass(), sgprocesoRiesgoListSgprocesoRiesgoToAttach.getSgprocesoRiesgoPK());
                attachedSgprocesoRiesgoList.add(sgprocesoRiesgoListSgprocesoRiesgoToAttach);
            }
            sgvaloracionRiesgo.setSgprocesoRiesgoList(attachedSgprocesoRiesgoList);
            em.persist(sgvaloracionRiesgo);
            for (SgprocesoRiesgo sgprocesoRiesgoListSgprocesoRiesgo : sgvaloracionRiesgo.getSgprocesoRiesgoList()) {
                SgvaloracionRiesgo oldIdvaloracionOfSgprocesoRiesgoListSgprocesoRiesgo = sgprocesoRiesgoListSgprocesoRiesgo.getIdvaloracion();
                sgprocesoRiesgoListSgprocesoRiesgo.setIdvaloracion(sgvaloracionRiesgo);
                sgprocesoRiesgoListSgprocesoRiesgo = em.merge(sgprocesoRiesgoListSgprocesoRiesgo);
                if (oldIdvaloracionOfSgprocesoRiesgoListSgprocesoRiesgo != null) {
                    oldIdvaloracionOfSgprocesoRiesgoListSgprocesoRiesgo.getSgprocesoRiesgoList().remove(sgprocesoRiesgoListSgprocesoRiesgo);
                    oldIdvaloracionOfSgprocesoRiesgoListSgprocesoRiesgo = em.merge(oldIdvaloracionOfSgprocesoRiesgoListSgprocesoRiesgo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgvaloracionRiesgo(sgvaloracionRiesgo.getIdvaloracion()) != null) {
                throw new PreexistingEntityException("SgvaloracionRiesgo " + sgvaloracionRiesgo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgvaloracionRiesgo sgvaloracionRiesgo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgvaloracionRiesgo persistentSgvaloracionRiesgo = em.find(SgvaloracionRiesgo.class, sgvaloracionRiesgo.getIdvaloracion());
            List<SgprocesoRiesgo> sgprocesoRiesgoListOld = persistentSgvaloracionRiesgo.getSgprocesoRiesgoList();
            List<SgprocesoRiesgo> sgprocesoRiesgoListNew = sgvaloracionRiesgo.getSgprocesoRiesgoList();
            List<SgprocesoRiesgo> attachedSgprocesoRiesgoListNew = new ArrayList<SgprocesoRiesgo>();
            for (SgprocesoRiesgo sgprocesoRiesgoListNewSgprocesoRiesgoToAttach : sgprocesoRiesgoListNew) {
                sgprocesoRiesgoListNewSgprocesoRiesgoToAttach = em.getReference(sgprocesoRiesgoListNewSgprocesoRiesgoToAttach.getClass(), sgprocesoRiesgoListNewSgprocesoRiesgoToAttach.getSgprocesoRiesgoPK());
                attachedSgprocesoRiesgoListNew.add(sgprocesoRiesgoListNewSgprocesoRiesgoToAttach);
            }
            sgprocesoRiesgoListNew = attachedSgprocesoRiesgoListNew;
            sgvaloracionRiesgo.setSgprocesoRiesgoList(sgprocesoRiesgoListNew);
            sgvaloracionRiesgo = em.merge(sgvaloracionRiesgo);
            for (SgprocesoRiesgo sgprocesoRiesgoListOldSgprocesoRiesgo : sgprocesoRiesgoListOld) {
                if (!sgprocesoRiesgoListNew.contains(sgprocesoRiesgoListOldSgprocesoRiesgo)) {
                    sgprocesoRiesgoListOldSgprocesoRiesgo.setIdvaloracion(null);
                    sgprocesoRiesgoListOldSgprocesoRiesgo = em.merge(sgprocesoRiesgoListOldSgprocesoRiesgo);
                }
            }
            for (SgprocesoRiesgo sgprocesoRiesgoListNewSgprocesoRiesgo : sgprocesoRiesgoListNew) {
                if (!sgprocesoRiesgoListOld.contains(sgprocesoRiesgoListNewSgprocesoRiesgo)) {
                    SgvaloracionRiesgo oldIdvaloracionOfSgprocesoRiesgoListNewSgprocesoRiesgo = sgprocesoRiesgoListNewSgprocesoRiesgo.getIdvaloracion();
                    sgprocesoRiesgoListNewSgprocesoRiesgo.setIdvaloracion(sgvaloracionRiesgo);
                    sgprocesoRiesgoListNewSgprocesoRiesgo = em.merge(sgprocesoRiesgoListNewSgprocesoRiesgo);
                    if (oldIdvaloracionOfSgprocesoRiesgoListNewSgprocesoRiesgo != null && !oldIdvaloracionOfSgprocesoRiesgoListNewSgprocesoRiesgo.equals(sgvaloracionRiesgo)) {
                        oldIdvaloracionOfSgprocesoRiesgoListNewSgprocesoRiesgo.getSgprocesoRiesgoList().remove(sgprocesoRiesgoListNewSgprocesoRiesgo);
                        oldIdvaloracionOfSgprocesoRiesgoListNewSgprocesoRiesgo = em.merge(oldIdvaloracionOfSgprocesoRiesgoListNewSgprocesoRiesgo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = sgvaloracionRiesgo.getIdvaloracion();
                if (findSgvaloracionRiesgo(id) == null) {
                    throw new NonexistentEntityException("The sgvaloracionRiesgo with id " + id + " no longer exists.");
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
            SgvaloracionRiesgo sgvaloracionRiesgo;
            try {
                sgvaloracionRiesgo = em.getReference(SgvaloracionRiesgo.class, id);
                sgvaloracionRiesgo.getIdvaloracion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgvaloracionRiesgo with id " + id + " no longer exists.", enfe);
            }
            List<SgprocesoRiesgo> sgprocesoRiesgoList = sgvaloracionRiesgo.getSgprocesoRiesgoList();
            for (SgprocesoRiesgo sgprocesoRiesgoListSgprocesoRiesgo : sgprocesoRiesgoList) {
                sgprocesoRiesgoListSgprocesoRiesgo.setIdvaloracion(null);
                sgprocesoRiesgoListSgprocesoRiesgo = em.merge(sgprocesoRiesgoListSgprocesoRiesgo);
            }
            em.remove(sgvaloracionRiesgo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgvaloracionRiesgo> findSgvaloracionRiesgoEntities() {
        return findSgvaloracionRiesgoEntities(true, -1, -1);
    }

    public List<SgvaloracionRiesgo> findSgvaloracionRiesgoEntities(int maxResults, int firstResult) {
        return findSgvaloracionRiesgoEntities(false, maxResults, firstResult);
    }

    private List<SgvaloracionRiesgo> findSgvaloracionRiesgoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgvaloracionRiesgo.class));
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

    public SgvaloracionRiesgo findSgvaloracionRiesgo(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgvaloracionRiesgo.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgvaloracionRiesgoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgvaloracionRiesgo> rt = cq.from(SgvaloracionRiesgo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
