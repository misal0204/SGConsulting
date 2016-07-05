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
import sg.sistemas.entidades.Sgriesgo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgprobabilidadRiesgo;

/**
 *
 * @author Misael
 */
public class SgprobabilidadRiesgoJpaController implements Serializable {

    public SgprobabilidadRiesgoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgprobabilidadRiesgo sgprobabilidadRiesgo) throws PreexistingEntityException, Exception {
        if (sgprobabilidadRiesgo.getSgriesgoList() == null) {
            sgprobabilidadRiesgo.setSgriesgoList(new ArrayList<Sgriesgo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Sgriesgo> attachedSgriesgoList = new ArrayList<Sgriesgo>();
            for (Sgriesgo sgriesgoListSgriesgoToAttach : sgprobabilidadRiesgo.getSgriesgoList()) {
                sgriesgoListSgriesgoToAttach = em.getReference(sgriesgoListSgriesgoToAttach.getClass(), sgriesgoListSgriesgoToAttach.getSgriesgoPK());
                attachedSgriesgoList.add(sgriesgoListSgriesgoToAttach);
            }
            sgprobabilidadRiesgo.setSgriesgoList(attachedSgriesgoList);
            em.persist(sgprobabilidadRiesgo);
            for (Sgriesgo sgriesgoListSgriesgo : sgprobabilidadRiesgo.getSgriesgoList()) {
                SgprobabilidadRiesgo oldIdprobabilidadOfSgriesgoListSgriesgo = sgriesgoListSgriesgo.getIdprobabilidad();
                sgriesgoListSgriesgo.setIdprobabilidad(sgprobabilidadRiesgo);
                sgriesgoListSgriesgo = em.merge(sgriesgoListSgriesgo);
                if (oldIdprobabilidadOfSgriesgoListSgriesgo != null) {
                    oldIdprobabilidadOfSgriesgoListSgriesgo.getSgriesgoList().remove(sgriesgoListSgriesgo);
                    oldIdprobabilidadOfSgriesgoListSgriesgo = em.merge(oldIdprobabilidadOfSgriesgoListSgriesgo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgprobabilidadRiesgo(sgprobabilidadRiesgo.getIdprobabilidad()) != null) {
                throw new PreexistingEntityException("SgprobabilidadRiesgo " + sgprobabilidadRiesgo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgprobabilidadRiesgo sgprobabilidadRiesgo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgprobabilidadRiesgo persistentSgprobabilidadRiesgo = em.find(SgprobabilidadRiesgo.class, sgprobabilidadRiesgo.getIdprobabilidad());
            List<Sgriesgo> sgriesgoListOld = persistentSgprobabilidadRiesgo.getSgriesgoList();
            List<Sgriesgo> sgriesgoListNew = sgprobabilidadRiesgo.getSgriesgoList();
            List<Sgriesgo> attachedSgriesgoListNew = new ArrayList<Sgriesgo>();
            for (Sgriesgo sgriesgoListNewSgriesgoToAttach : sgriesgoListNew) {
                sgriesgoListNewSgriesgoToAttach = em.getReference(sgriesgoListNewSgriesgoToAttach.getClass(), sgriesgoListNewSgriesgoToAttach.getSgriesgoPK());
                attachedSgriesgoListNew.add(sgriesgoListNewSgriesgoToAttach);
            }
            sgriesgoListNew = attachedSgriesgoListNew;
            sgprobabilidadRiesgo.setSgriesgoList(sgriesgoListNew);
            sgprobabilidadRiesgo = em.merge(sgprobabilidadRiesgo);
            for (Sgriesgo sgriesgoListOldSgriesgo : sgriesgoListOld) {
                if (!sgriesgoListNew.contains(sgriesgoListOldSgriesgo)) {
                    sgriesgoListOldSgriesgo.setIdprobabilidad(null);
                    sgriesgoListOldSgriesgo = em.merge(sgriesgoListOldSgriesgo);
                }
            }
            for (Sgriesgo sgriesgoListNewSgriesgo : sgriesgoListNew) {
                if (!sgriesgoListOld.contains(sgriesgoListNewSgriesgo)) {
                    SgprobabilidadRiesgo oldIdprobabilidadOfSgriesgoListNewSgriesgo = sgriesgoListNewSgriesgo.getIdprobabilidad();
                    sgriesgoListNewSgriesgo.setIdprobabilidad(sgprobabilidadRiesgo);
                    sgriesgoListNewSgriesgo = em.merge(sgriesgoListNewSgriesgo);
                    if (oldIdprobabilidadOfSgriesgoListNewSgriesgo != null && !oldIdprobabilidadOfSgriesgoListNewSgriesgo.equals(sgprobabilidadRiesgo)) {
                        oldIdprobabilidadOfSgriesgoListNewSgriesgo.getSgriesgoList().remove(sgriesgoListNewSgriesgo);
                        oldIdprobabilidadOfSgriesgoListNewSgriesgo = em.merge(oldIdprobabilidadOfSgriesgoListNewSgriesgo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = sgprobabilidadRiesgo.getIdprobabilidad();
                if (findSgprobabilidadRiesgo(id) == null) {
                    throw new NonexistentEntityException("The sgprobabilidadRiesgo with id " + id + " no longer exists.");
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
            SgprobabilidadRiesgo sgprobabilidadRiesgo;
            try {
                sgprobabilidadRiesgo = em.getReference(SgprobabilidadRiesgo.class, id);
                sgprobabilidadRiesgo.getIdprobabilidad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgprobabilidadRiesgo with id " + id + " no longer exists.", enfe);
            }
            List<Sgriesgo> sgriesgoList = sgprobabilidadRiesgo.getSgriesgoList();
            for (Sgriesgo sgriesgoListSgriesgo : sgriesgoList) {
                sgriesgoListSgriesgo.setIdprobabilidad(null);
                sgriesgoListSgriesgo = em.merge(sgriesgoListSgriesgo);
            }
            em.remove(sgprobabilidadRiesgo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgprobabilidadRiesgo> findSgprobabilidadRiesgoEntities() {
        return findSgprobabilidadRiesgoEntities(true, -1, -1);
    }

    public List<SgprobabilidadRiesgo> findSgprobabilidadRiesgoEntities(int maxResults, int firstResult) {
        return findSgprobabilidadRiesgoEntities(false, maxResults, firstResult);
    }

    private List<SgprobabilidadRiesgo> findSgprobabilidadRiesgoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgprobabilidadRiesgo.class));
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

    public SgprobabilidadRiesgo findSgprobabilidadRiesgo(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgprobabilidadRiesgo.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgprobabilidadRiesgoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgprobabilidadRiesgo> rt = cq.from(SgprobabilidadRiesgo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
