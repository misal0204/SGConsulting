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
import sg.sistemas.entidades.SgtipoPlan;
import sg.sistemas.entidades.SgplanPrograma;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgplanForma;
import sg.sistemas.entidades.SgplanFormaPK;

/**
 *
 * @author Misael
 */
public class SgplanFormaJpaController implements Serializable {

    public SgplanFormaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgplanForma sgplanForma) throws PreexistingEntityException, Exception {
        if (sgplanForma.getSgplanFormaPK() == null) {
            sgplanForma.setSgplanFormaPK(new SgplanFormaPK());
        }
        if (sgplanForma.getSgplanProgramaList() == null) {
            sgplanForma.setSgplanProgramaList(new ArrayList<SgplanPrograma>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgtipoPlan idtipoPlan = sgplanForma.getIdtipoPlan();
            if (idtipoPlan != null) {
                idtipoPlan = em.getReference(idtipoPlan.getClass(), idtipoPlan.getIdtipoPlan());
                sgplanForma.setIdtipoPlan(idtipoPlan);
            }
            List<SgplanPrograma> attachedSgplanProgramaList = new ArrayList<SgplanPrograma>();
            for (SgplanPrograma sgplanProgramaListSgplanProgramaToAttach : sgplanForma.getSgplanProgramaList()) {
                sgplanProgramaListSgplanProgramaToAttach = em.getReference(sgplanProgramaListSgplanProgramaToAttach.getClass(), sgplanProgramaListSgplanProgramaToAttach.getSgplanProgramaPK());
                attachedSgplanProgramaList.add(sgplanProgramaListSgplanProgramaToAttach);
            }
            sgplanForma.setSgplanProgramaList(attachedSgplanProgramaList);
            em.persist(sgplanForma);
            if (idtipoPlan != null) {
                idtipoPlan.getSgplanFormaList().add(sgplanForma);
                idtipoPlan = em.merge(idtipoPlan);
            }
            for (SgplanPrograma sgplanProgramaListSgplanPrograma : sgplanForma.getSgplanProgramaList()) {
                SgplanForma oldSgplanFormaOfSgplanProgramaListSgplanPrograma = sgplanProgramaListSgplanPrograma.getSgplanForma();
                sgplanProgramaListSgplanPrograma.setSgplanForma(sgplanForma);
                sgplanProgramaListSgplanPrograma = em.merge(sgplanProgramaListSgplanPrograma);
                if (oldSgplanFormaOfSgplanProgramaListSgplanPrograma != null) {
                    oldSgplanFormaOfSgplanProgramaListSgplanPrograma.getSgplanProgramaList().remove(sgplanProgramaListSgplanPrograma);
                    oldSgplanFormaOfSgplanProgramaListSgplanPrograma = em.merge(oldSgplanFormaOfSgplanProgramaListSgplanPrograma);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgplanForma(sgplanForma.getSgplanFormaPK()) != null) {
                throw new PreexistingEntityException("SgplanForma " + sgplanForma + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgplanForma sgplanForma) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgplanForma persistentSgplanForma = em.find(SgplanForma.class, sgplanForma.getSgplanFormaPK());
            SgtipoPlan idtipoPlanOld = persistentSgplanForma.getIdtipoPlan();
            SgtipoPlan idtipoPlanNew = sgplanForma.getIdtipoPlan();
            List<SgplanPrograma> sgplanProgramaListOld = persistentSgplanForma.getSgplanProgramaList();
            List<SgplanPrograma> sgplanProgramaListNew = sgplanForma.getSgplanProgramaList();
            List<String> illegalOrphanMessages = null;
            for (SgplanPrograma sgplanProgramaListOldSgplanPrograma : sgplanProgramaListOld) {
                if (!sgplanProgramaListNew.contains(sgplanProgramaListOldSgplanPrograma)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SgplanPrograma " + sgplanProgramaListOldSgplanPrograma + " since its sgplanForma field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idtipoPlanNew != null) {
                idtipoPlanNew = em.getReference(idtipoPlanNew.getClass(), idtipoPlanNew.getIdtipoPlan());
                sgplanForma.setIdtipoPlan(idtipoPlanNew);
            }
            List<SgplanPrograma> attachedSgplanProgramaListNew = new ArrayList<SgplanPrograma>();
            for (SgplanPrograma sgplanProgramaListNewSgplanProgramaToAttach : sgplanProgramaListNew) {
                sgplanProgramaListNewSgplanProgramaToAttach = em.getReference(sgplanProgramaListNewSgplanProgramaToAttach.getClass(), sgplanProgramaListNewSgplanProgramaToAttach.getSgplanProgramaPK());
                attachedSgplanProgramaListNew.add(sgplanProgramaListNewSgplanProgramaToAttach);
            }
            sgplanProgramaListNew = attachedSgplanProgramaListNew;
            sgplanForma.setSgplanProgramaList(sgplanProgramaListNew);
            sgplanForma = em.merge(sgplanForma);
            if (idtipoPlanOld != null && !idtipoPlanOld.equals(idtipoPlanNew)) {
                idtipoPlanOld.getSgplanFormaList().remove(sgplanForma);
                idtipoPlanOld = em.merge(idtipoPlanOld);
            }
            if (idtipoPlanNew != null && !idtipoPlanNew.equals(idtipoPlanOld)) {
                idtipoPlanNew.getSgplanFormaList().add(sgplanForma);
                idtipoPlanNew = em.merge(idtipoPlanNew);
            }
            for (SgplanPrograma sgplanProgramaListNewSgplanPrograma : sgplanProgramaListNew) {
                if (!sgplanProgramaListOld.contains(sgplanProgramaListNewSgplanPrograma)) {
                    SgplanForma oldSgplanFormaOfSgplanProgramaListNewSgplanPrograma = sgplanProgramaListNewSgplanPrograma.getSgplanForma();
                    sgplanProgramaListNewSgplanPrograma.setSgplanForma(sgplanForma);
                    sgplanProgramaListNewSgplanPrograma = em.merge(sgplanProgramaListNewSgplanPrograma);
                    if (oldSgplanFormaOfSgplanProgramaListNewSgplanPrograma != null && !oldSgplanFormaOfSgplanProgramaListNewSgplanPrograma.equals(sgplanForma)) {
                        oldSgplanFormaOfSgplanProgramaListNewSgplanPrograma.getSgplanProgramaList().remove(sgplanProgramaListNewSgplanPrograma);
                        oldSgplanFormaOfSgplanProgramaListNewSgplanPrograma = em.merge(oldSgplanFormaOfSgplanProgramaListNewSgplanPrograma);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgplanFormaPK id = sgplanForma.getSgplanFormaPK();
                if (findSgplanForma(id) == null) {
                    throw new NonexistentEntityException("The sgplanForma with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgplanFormaPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgplanForma sgplanForma;
            try {
                sgplanForma = em.getReference(SgplanForma.class, id);
                sgplanForma.getSgplanFormaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgplanForma with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SgplanPrograma> sgplanProgramaListOrphanCheck = sgplanForma.getSgplanProgramaList();
            for (SgplanPrograma sgplanProgramaListOrphanCheckSgplanPrograma : sgplanProgramaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SgplanForma (" + sgplanForma + ") cannot be destroyed since the SgplanPrograma " + sgplanProgramaListOrphanCheckSgplanPrograma + " in its sgplanProgramaList field has a non-nullable sgplanForma field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            SgtipoPlan idtipoPlan = sgplanForma.getIdtipoPlan();
            if (idtipoPlan != null) {
                idtipoPlan.getSgplanFormaList().remove(sgplanForma);
                idtipoPlan = em.merge(idtipoPlan);
            }
            em.remove(sgplanForma);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgplanForma> findSgplanFormaEntities() {
        return findSgplanFormaEntities(true, -1, -1);
    }

    public List<SgplanForma> findSgplanFormaEntities(int maxResults, int firstResult) {
        return findSgplanFormaEntities(false, maxResults, firstResult);
    }

    private List<SgplanForma> findSgplanFormaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgplanForma.class));
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

    public SgplanForma findSgplanForma(SgplanFormaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgplanForma.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgplanFormaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgplanForma> rt = cq.from(SgplanForma.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
