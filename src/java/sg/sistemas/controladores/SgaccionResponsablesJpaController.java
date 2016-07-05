/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.controladores;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgaccionCorrectiva;
import sg.sistemas.entidades.SgaccionResponsables;
import sg.sistemas.entidades.SgaccionResponsablesPK;

/**
 *
 * @author Misael
 */
public class SgaccionResponsablesJpaController implements Serializable {

    public SgaccionResponsablesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgaccionResponsables sgaccionResponsables) throws PreexistingEntityException, Exception {
        if (sgaccionResponsables.getSgaccionResponsablesPK() == null) {
            sgaccionResponsables.setSgaccionResponsablesPK(new SgaccionResponsablesPK());
        }
        sgaccionResponsables.getSgaccionResponsablesPK().setNonc(sgaccionResponsables.getSgaccionCorrectiva().getSgaccionCorrectivaPK().getNonc());
        sgaccionResponsables.getSgaccionResponsablesPK().setIdaccion(sgaccionResponsables.getSgaccionCorrectiva().getSgaccionCorrectivaPK().getIdaccion());
        sgaccionResponsables.getSgaccionResponsablesPK().setIdsociedad(sgaccionResponsables.getSgaccionCorrectiva().getSgaccionCorrectivaPK().getIdsociedad());
        sgaccionResponsables.getSgaccionResponsablesPK().setIdcausa(sgaccionResponsables.getSgaccionCorrectiva().getSgaccionCorrectivaPK().getIdcausa());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgaccionCorrectiva sgaccionCorrectiva = sgaccionResponsables.getSgaccionCorrectiva();
            if (sgaccionCorrectiva != null) {
                sgaccionCorrectiva = em.getReference(sgaccionCorrectiva.getClass(), sgaccionCorrectiva.getSgaccionCorrectivaPK());
                sgaccionResponsables.setSgaccionCorrectiva(sgaccionCorrectiva);
            }
            em.persist(sgaccionResponsables);
            if (sgaccionCorrectiva != null) {
                sgaccionCorrectiva.getSgaccionResponsablesList().add(sgaccionResponsables);
                sgaccionCorrectiva = em.merge(sgaccionCorrectiva);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgaccionResponsables(sgaccionResponsables.getSgaccionResponsablesPK()) != null) {
                throw new PreexistingEntityException("SgaccionResponsables " + sgaccionResponsables + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgaccionResponsables sgaccionResponsables) throws NonexistentEntityException, Exception {
        sgaccionResponsables.getSgaccionResponsablesPK().setNonc(sgaccionResponsables.getSgaccionCorrectiva().getSgaccionCorrectivaPK().getNonc());
        sgaccionResponsables.getSgaccionResponsablesPK().setIdaccion(sgaccionResponsables.getSgaccionCorrectiva().getSgaccionCorrectivaPK().getIdaccion());
        sgaccionResponsables.getSgaccionResponsablesPK().setIdsociedad(sgaccionResponsables.getSgaccionCorrectiva().getSgaccionCorrectivaPK().getIdsociedad());
        sgaccionResponsables.getSgaccionResponsablesPK().setIdcausa(sgaccionResponsables.getSgaccionCorrectiva().getSgaccionCorrectivaPK().getIdcausa());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgaccionResponsables persistentSgaccionResponsables = em.find(SgaccionResponsables.class, sgaccionResponsables.getSgaccionResponsablesPK());
            SgaccionCorrectiva sgaccionCorrectivaOld = persistentSgaccionResponsables.getSgaccionCorrectiva();
            SgaccionCorrectiva sgaccionCorrectivaNew = sgaccionResponsables.getSgaccionCorrectiva();
            if (sgaccionCorrectivaNew != null) {
                sgaccionCorrectivaNew = em.getReference(sgaccionCorrectivaNew.getClass(), sgaccionCorrectivaNew.getSgaccionCorrectivaPK());
                sgaccionResponsables.setSgaccionCorrectiva(sgaccionCorrectivaNew);
            }
            sgaccionResponsables = em.merge(sgaccionResponsables);
            if (sgaccionCorrectivaOld != null && !sgaccionCorrectivaOld.equals(sgaccionCorrectivaNew)) {
                sgaccionCorrectivaOld.getSgaccionResponsablesList().remove(sgaccionResponsables);
                sgaccionCorrectivaOld = em.merge(sgaccionCorrectivaOld);
            }
            if (sgaccionCorrectivaNew != null && !sgaccionCorrectivaNew.equals(sgaccionCorrectivaOld)) {
                sgaccionCorrectivaNew.getSgaccionResponsablesList().add(sgaccionResponsables);
                sgaccionCorrectivaNew = em.merge(sgaccionCorrectivaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgaccionResponsablesPK id = sgaccionResponsables.getSgaccionResponsablesPK();
                if (findSgaccionResponsables(id) == null) {
                    throw new NonexistentEntityException("The sgaccionResponsables with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgaccionResponsablesPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgaccionResponsables sgaccionResponsables;
            try {
                sgaccionResponsables = em.getReference(SgaccionResponsables.class, id);
                sgaccionResponsables.getSgaccionResponsablesPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgaccionResponsables with id " + id + " no longer exists.", enfe);
            }
            SgaccionCorrectiva sgaccionCorrectiva = sgaccionResponsables.getSgaccionCorrectiva();
            if (sgaccionCorrectiva != null) {
                sgaccionCorrectiva.getSgaccionResponsablesList().remove(sgaccionResponsables);
                sgaccionCorrectiva = em.merge(sgaccionCorrectiva);
            }
            em.remove(sgaccionResponsables);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgaccionResponsables> findSgaccionResponsablesEntities() {
        return findSgaccionResponsablesEntities(true, -1, -1);
    }

    public List<SgaccionResponsables> findSgaccionResponsablesEntities(int maxResults, int firstResult) {
        return findSgaccionResponsablesEntities(false, maxResults, firstResult);
    }

    private List<SgaccionResponsables> findSgaccionResponsablesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgaccionResponsables.class));
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

    public SgaccionResponsables findSgaccionResponsables(SgaccionResponsablesPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgaccionResponsables.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgaccionResponsablesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgaccionResponsables> rt = cq.from(SgaccionResponsables.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
