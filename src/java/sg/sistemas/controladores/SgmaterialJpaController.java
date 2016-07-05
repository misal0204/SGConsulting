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
import sg.sistemas.entidades.SgprocesosRe;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.Sgmaterial;

/**
 *
 * @author Misael
 */
public class SgmaterialJpaController implements Serializable {

    public SgmaterialJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sgmaterial sgmaterial) throws PreexistingEntityException, Exception {
        if (sgmaterial.getSgprocesosReList() == null) {
            sgmaterial.setSgprocesosReList(new ArrayList<SgprocesosRe>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SgprocesosRe> attachedSgprocesosReList = new ArrayList<SgprocesosRe>();
            for (SgprocesosRe sgprocesosReListSgprocesosReToAttach : sgmaterial.getSgprocesosReList()) {
                sgprocesosReListSgprocesosReToAttach = em.getReference(sgprocesosReListSgprocesosReToAttach.getClass(), sgprocesosReListSgprocesosReToAttach.getSgprocesosRePK());
                attachedSgprocesosReList.add(sgprocesosReListSgprocesosReToAttach);
            }
            sgmaterial.setSgprocesosReList(attachedSgprocesosReList);
            em.persist(sgmaterial);
            for (SgprocesosRe sgprocesosReListSgprocesosRe : sgmaterial.getSgprocesosReList()) {
                Sgmaterial oldIdmaterialOfSgprocesosReListSgprocesosRe = sgprocesosReListSgprocesosRe.getIdmaterial();
                sgprocesosReListSgprocesosRe.setIdmaterial(sgmaterial);
                sgprocesosReListSgprocesosRe = em.merge(sgprocesosReListSgprocesosRe);
                if (oldIdmaterialOfSgprocesosReListSgprocesosRe != null) {
                    oldIdmaterialOfSgprocesosReListSgprocesosRe.getSgprocesosReList().remove(sgprocesosReListSgprocesosRe);
                    oldIdmaterialOfSgprocesosReListSgprocesosRe = em.merge(oldIdmaterialOfSgprocesosReListSgprocesosRe);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgmaterial(sgmaterial.getIdmaterial()) != null) {
                throw new PreexistingEntityException("Sgmaterial " + sgmaterial + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sgmaterial sgmaterial) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgmaterial persistentSgmaterial = em.find(Sgmaterial.class, sgmaterial.getIdmaterial());
            List<SgprocesosRe> sgprocesosReListOld = persistentSgmaterial.getSgprocesosReList();
            List<SgprocesosRe> sgprocesosReListNew = sgmaterial.getSgprocesosReList();
            List<SgprocesosRe> attachedSgprocesosReListNew = new ArrayList<SgprocesosRe>();
            for (SgprocesosRe sgprocesosReListNewSgprocesosReToAttach : sgprocesosReListNew) {
                sgprocesosReListNewSgprocesosReToAttach = em.getReference(sgprocesosReListNewSgprocesosReToAttach.getClass(), sgprocesosReListNewSgprocesosReToAttach.getSgprocesosRePK());
                attachedSgprocesosReListNew.add(sgprocesosReListNewSgprocesosReToAttach);
            }
            sgprocesosReListNew = attachedSgprocesosReListNew;
            sgmaterial.setSgprocesosReList(sgprocesosReListNew);
            sgmaterial = em.merge(sgmaterial);
            for (SgprocesosRe sgprocesosReListOldSgprocesosRe : sgprocesosReListOld) {
                if (!sgprocesosReListNew.contains(sgprocesosReListOldSgprocesosRe)) {
                    sgprocesosReListOldSgprocesosRe.setIdmaterial(null);
                    sgprocesosReListOldSgprocesosRe = em.merge(sgprocesosReListOldSgprocesosRe);
                }
            }
            for (SgprocesosRe sgprocesosReListNewSgprocesosRe : sgprocesosReListNew) {
                if (!sgprocesosReListOld.contains(sgprocesosReListNewSgprocesosRe)) {
                    Sgmaterial oldIdmaterialOfSgprocesosReListNewSgprocesosRe = sgprocesosReListNewSgprocesosRe.getIdmaterial();
                    sgprocesosReListNewSgprocesosRe.setIdmaterial(sgmaterial);
                    sgprocesosReListNewSgprocesosRe = em.merge(sgprocesosReListNewSgprocesosRe);
                    if (oldIdmaterialOfSgprocesosReListNewSgprocesosRe != null && !oldIdmaterialOfSgprocesosReListNewSgprocesosRe.equals(sgmaterial)) {
                        oldIdmaterialOfSgprocesosReListNewSgprocesosRe.getSgprocesosReList().remove(sgprocesosReListNewSgprocesosRe);
                        oldIdmaterialOfSgprocesosReListNewSgprocesosRe = em.merge(oldIdmaterialOfSgprocesosReListNewSgprocesosRe);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = sgmaterial.getIdmaterial();
                if (findSgmaterial(id) == null) {
                    throw new NonexistentEntityException("The sgmaterial with id " + id + " no longer exists.");
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
            Sgmaterial sgmaterial;
            try {
                sgmaterial = em.getReference(Sgmaterial.class, id);
                sgmaterial.getIdmaterial();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgmaterial with id " + id + " no longer exists.", enfe);
            }
            List<SgprocesosRe> sgprocesosReList = sgmaterial.getSgprocesosReList();
            for (SgprocesosRe sgprocesosReListSgprocesosRe : sgprocesosReList) {
                sgprocesosReListSgprocesosRe.setIdmaterial(null);
                sgprocesosReListSgprocesosRe = em.merge(sgprocesosReListSgprocesosRe);
            }
            em.remove(sgmaterial);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sgmaterial> findSgmaterialEntities() {
        return findSgmaterialEntities(true, -1, -1);
    }

    public List<Sgmaterial> findSgmaterialEntities(int maxResults, int firstResult) {
        return findSgmaterialEntities(false, maxResults, firstResult);
    }

    private List<Sgmaterial> findSgmaterialEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sgmaterial.class));
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

    public Sgmaterial findSgmaterial(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sgmaterial.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgmaterialCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sgmaterial> rt = cq.from(Sgmaterial.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
