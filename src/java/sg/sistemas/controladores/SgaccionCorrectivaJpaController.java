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
import sg.sistemas.entidades.SgaccionResponsables;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgaccionCorrectiva;
import sg.sistemas.entidades.SgaccionCorrectivaPK;

/**
 *
 * @author Misael
 */
public class SgaccionCorrectivaJpaController implements Serializable {

    public SgaccionCorrectivaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgaccionCorrectiva sgaccionCorrectiva) throws PreexistingEntityException, Exception {
        if (sgaccionCorrectiva.getSgaccionCorrectivaPK() == null) {
            sgaccionCorrectiva.setSgaccionCorrectivaPK(new SgaccionCorrectivaPK());
        }
        if (sgaccionCorrectiva.getSgaccionResponsablesList() == null) {
            sgaccionCorrectiva.setSgaccionResponsablesList(new ArrayList<SgaccionResponsables>());
        }
        sgaccionCorrectiva.getSgaccionCorrectivaPK().setIdcausa(sgaccionCorrectiva.getSgcausa().getSgcausaPK().getIdcausa());
        sgaccionCorrectiva.getSgaccionCorrectivaPK().setIdsociedad(sgaccionCorrectiva.getSgcausa().getSgcausaPK().getIdsociedad());
        sgaccionCorrectiva.getSgaccionCorrectivaPK().setNonc(sgaccionCorrectiva.getSgcausa().getSgcausaPK().getNonc());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgcausa sgcausa = sgaccionCorrectiva.getSgcausa();
            if (sgcausa != null) {
                sgcausa = em.getReference(sgcausa.getClass(), sgcausa.getSgcausaPK());
                sgaccionCorrectiva.setSgcausa(sgcausa);
            }
            List<SgaccionResponsables> attachedSgaccionResponsablesList = new ArrayList<SgaccionResponsables>();
            for (SgaccionResponsables sgaccionResponsablesListSgaccionResponsablesToAttach : sgaccionCorrectiva.getSgaccionResponsablesList()) {
                sgaccionResponsablesListSgaccionResponsablesToAttach = em.getReference(sgaccionResponsablesListSgaccionResponsablesToAttach.getClass(), sgaccionResponsablesListSgaccionResponsablesToAttach.getSgaccionResponsablesPK());
                attachedSgaccionResponsablesList.add(sgaccionResponsablesListSgaccionResponsablesToAttach);
            }
            sgaccionCorrectiva.setSgaccionResponsablesList(attachedSgaccionResponsablesList);
            em.persist(sgaccionCorrectiva);
            if (sgcausa != null) {
                sgcausa.getSgaccionCorrectivaList().add(sgaccionCorrectiva);
                sgcausa = em.merge(sgcausa);
            }
            for (SgaccionResponsables sgaccionResponsablesListSgaccionResponsables : sgaccionCorrectiva.getSgaccionResponsablesList()) {
                SgaccionCorrectiva oldSgaccionCorrectivaOfSgaccionResponsablesListSgaccionResponsables = sgaccionResponsablesListSgaccionResponsables.getSgaccionCorrectiva();
                sgaccionResponsablesListSgaccionResponsables.setSgaccionCorrectiva(sgaccionCorrectiva);
                sgaccionResponsablesListSgaccionResponsables = em.merge(sgaccionResponsablesListSgaccionResponsables);
                if (oldSgaccionCorrectivaOfSgaccionResponsablesListSgaccionResponsables != null) {
                    oldSgaccionCorrectivaOfSgaccionResponsablesListSgaccionResponsables.getSgaccionResponsablesList().remove(sgaccionResponsablesListSgaccionResponsables);
                    oldSgaccionCorrectivaOfSgaccionResponsablesListSgaccionResponsables = em.merge(oldSgaccionCorrectivaOfSgaccionResponsablesListSgaccionResponsables);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgaccionCorrectiva(sgaccionCorrectiva.getSgaccionCorrectivaPK()) != null) {
                throw new PreexistingEntityException("SgaccionCorrectiva " + sgaccionCorrectiva + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgaccionCorrectiva sgaccionCorrectiva) throws IllegalOrphanException, NonexistentEntityException, Exception {
        sgaccionCorrectiva.getSgaccionCorrectivaPK().setIdcausa(sgaccionCorrectiva.getSgcausa().getSgcausaPK().getIdcausa());
        sgaccionCorrectiva.getSgaccionCorrectivaPK().setIdsociedad(sgaccionCorrectiva.getSgcausa().getSgcausaPK().getIdsociedad());
        sgaccionCorrectiva.getSgaccionCorrectivaPK().setNonc(sgaccionCorrectiva.getSgcausa().getSgcausaPK().getNonc());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgaccionCorrectiva persistentSgaccionCorrectiva = em.find(SgaccionCorrectiva.class, sgaccionCorrectiva.getSgaccionCorrectivaPK());
            Sgcausa sgcausaOld = persistentSgaccionCorrectiva.getSgcausa();
            Sgcausa sgcausaNew = sgaccionCorrectiva.getSgcausa();
            List<SgaccionResponsables> sgaccionResponsablesListOld = persistentSgaccionCorrectiva.getSgaccionResponsablesList();
            List<SgaccionResponsables> sgaccionResponsablesListNew = sgaccionCorrectiva.getSgaccionResponsablesList();
            List<String> illegalOrphanMessages = null;
            for (SgaccionResponsables sgaccionResponsablesListOldSgaccionResponsables : sgaccionResponsablesListOld) {
                if (!sgaccionResponsablesListNew.contains(sgaccionResponsablesListOldSgaccionResponsables)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SgaccionResponsables " + sgaccionResponsablesListOldSgaccionResponsables + " since its sgaccionCorrectiva field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sgcausaNew != null) {
                sgcausaNew = em.getReference(sgcausaNew.getClass(), sgcausaNew.getSgcausaPK());
                sgaccionCorrectiva.setSgcausa(sgcausaNew);
            }
            List<SgaccionResponsables> attachedSgaccionResponsablesListNew = new ArrayList<SgaccionResponsables>();
            for (SgaccionResponsables sgaccionResponsablesListNewSgaccionResponsablesToAttach : sgaccionResponsablesListNew) {
                sgaccionResponsablesListNewSgaccionResponsablesToAttach = em.getReference(sgaccionResponsablesListNewSgaccionResponsablesToAttach.getClass(), sgaccionResponsablesListNewSgaccionResponsablesToAttach.getSgaccionResponsablesPK());
                attachedSgaccionResponsablesListNew.add(sgaccionResponsablesListNewSgaccionResponsablesToAttach);
            }
            sgaccionResponsablesListNew = attachedSgaccionResponsablesListNew;
            sgaccionCorrectiva.setSgaccionResponsablesList(sgaccionResponsablesListNew);
            sgaccionCorrectiva = em.merge(sgaccionCorrectiva);
            if (sgcausaOld != null && !sgcausaOld.equals(sgcausaNew)) {
                sgcausaOld.getSgaccionCorrectivaList().remove(sgaccionCorrectiva);
                sgcausaOld = em.merge(sgcausaOld);
            }
            if (sgcausaNew != null && !sgcausaNew.equals(sgcausaOld)) {
                sgcausaNew.getSgaccionCorrectivaList().add(sgaccionCorrectiva);
                sgcausaNew = em.merge(sgcausaNew);
            }
            for (SgaccionResponsables sgaccionResponsablesListNewSgaccionResponsables : sgaccionResponsablesListNew) {
                if (!sgaccionResponsablesListOld.contains(sgaccionResponsablesListNewSgaccionResponsables)) {
                    SgaccionCorrectiva oldSgaccionCorrectivaOfSgaccionResponsablesListNewSgaccionResponsables = sgaccionResponsablesListNewSgaccionResponsables.getSgaccionCorrectiva();
                    sgaccionResponsablesListNewSgaccionResponsables.setSgaccionCorrectiva(sgaccionCorrectiva);
                    sgaccionResponsablesListNewSgaccionResponsables = em.merge(sgaccionResponsablesListNewSgaccionResponsables);
                    if (oldSgaccionCorrectivaOfSgaccionResponsablesListNewSgaccionResponsables != null && !oldSgaccionCorrectivaOfSgaccionResponsablesListNewSgaccionResponsables.equals(sgaccionCorrectiva)) {
                        oldSgaccionCorrectivaOfSgaccionResponsablesListNewSgaccionResponsables.getSgaccionResponsablesList().remove(sgaccionResponsablesListNewSgaccionResponsables);
                        oldSgaccionCorrectivaOfSgaccionResponsablesListNewSgaccionResponsables = em.merge(oldSgaccionCorrectivaOfSgaccionResponsablesListNewSgaccionResponsables);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgaccionCorrectivaPK id = sgaccionCorrectiva.getSgaccionCorrectivaPK();
                if (findSgaccionCorrectiva(id) == null) {
                    throw new NonexistentEntityException("The sgaccionCorrectiva with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgaccionCorrectivaPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgaccionCorrectiva sgaccionCorrectiva;
            try {
                sgaccionCorrectiva = em.getReference(SgaccionCorrectiva.class, id);
                sgaccionCorrectiva.getSgaccionCorrectivaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgaccionCorrectiva with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SgaccionResponsables> sgaccionResponsablesListOrphanCheck = sgaccionCorrectiva.getSgaccionResponsablesList();
            for (SgaccionResponsables sgaccionResponsablesListOrphanCheckSgaccionResponsables : sgaccionResponsablesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SgaccionCorrectiva (" + sgaccionCorrectiva + ") cannot be destroyed since the SgaccionResponsables " + sgaccionResponsablesListOrphanCheckSgaccionResponsables + " in its sgaccionResponsablesList field has a non-nullable sgaccionCorrectiva field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Sgcausa sgcausa = sgaccionCorrectiva.getSgcausa();
            if (sgcausa != null) {
                sgcausa.getSgaccionCorrectivaList().remove(sgaccionCorrectiva);
                sgcausa = em.merge(sgcausa);
            }
            em.remove(sgaccionCorrectiva);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgaccionCorrectiva> findSgaccionCorrectivaEntities() {
        return findSgaccionCorrectivaEntities(true, -1, -1);
    }

    public List<SgaccionCorrectiva> findSgaccionCorrectivaEntities(int maxResults, int firstResult) {
        return findSgaccionCorrectivaEntities(false, maxResults, firstResult);
    }

    private List<SgaccionCorrectiva> findSgaccionCorrectivaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgaccionCorrectiva.class));
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

    public SgaccionCorrectiva findSgaccionCorrectiva(SgaccionCorrectivaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgaccionCorrectiva.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgaccionCorrectivaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgaccionCorrectiva> rt = cq.from(SgaccionCorrectiva.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
