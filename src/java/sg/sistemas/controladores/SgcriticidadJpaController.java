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
import sg.sistemas.entidades.Sgnc;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.Sgcriticidad;

/**
 *
 * @author Misael
 */
public class SgcriticidadJpaController implements Serializable {

    public SgcriticidadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sgcriticidad sgcriticidad) throws PreexistingEntityException, Exception {
        if (sgcriticidad.getSgncList() == null) {
            sgcriticidad.setSgncList(new ArrayList<Sgnc>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Sgnc> attachedSgncList = new ArrayList<Sgnc>();
            for (Sgnc sgncListSgncToAttach : sgcriticidad.getSgncList()) {
                sgncListSgncToAttach = em.getReference(sgncListSgncToAttach.getClass(), sgncListSgncToAttach.getSgncPK());
                attachedSgncList.add(sgncListSgncToAttach);
            }
            sgcriticidad.setSgncList(attachedSgncList);
            em.persist(sgcriticidad);
            for (Sgnc sgncListSgnc : sgcriticidad.getSgncList()) {
                Sgcriticidad oldIdcriticidadOfSgncListSgnc = sgncListSgnc.getIdcriticidad();
                sgncListSgnc.setIdcriticidad(sgcriticidad);
                sgncListSgnc = em.merge(sgncListSgnc);
                if (oldIdcriticidadOfSgncListSgnc != null) {
                    oldIdcriticidadOfSgncListSgnc.getSgncList().remove(sgncListSgnc);
                    oldIdcriticidadOfSgncListSgnc = em.merge(oldIdcriticidadOfSgncListSgnc);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgcriticidad(sgcriticidad.getIdcriticidad()) != null) {
                throw new PreexistingEntityException("Sgcriticidad " + sgcriticidad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sgcriticidad sgcriticidad) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgcriticidad persistentSgcriticidad = em.find(Sgcriticidad.class, sgcriticidad.getIdcriticidad());
            List<Sgnc> sgncListOld = persistentSgcriticidad.getSgncList();
            List<Sgnc> sgncListNew = sgcriticidad.getSgncList();
            List<Sgnc> attachedSgncListNew = new ArrayList<Sgnc>();
            for (Sgnc sgncListNewSgncToAttach : sgncListNew) {
                sgncListNewSgncToAttach = em.getReference(sgncListNewSgncToAttach.getClass(), sgncListNewSgncToAttach.getSgncPK());
                attachedSgncListNew.add(sgncListNewSgncToAttach);
            }
            sgncListNew = attachedSgncListNew;
            sgcriticidad.setSgncList(sgncListNew);
            sgcriticidad = em.merge(sgcriticidad);
            for (Sgnc sgncListOldSgnc : sgncListOld) {
                if (!sgncListNew.contains(sgncListOldSgnc)) {
                    sgncListOldSgnc.setIdcriticidad(null);
                    sgncListOldSgnc = em.merge(sgncListOldSgnc);
                }
            }
            for (Sgnc sgncListNewSgnc : sgncListNew) {
                if (!sgncListOld.contains(sgncListNewSgnc)) {
                    Sgcriticidad oldIdcriticidadOfSgncListNewSgnc = sgncListNewSgnc.getIdcriticidad();
                    sgncListNewSgnc.setIdcriticidad(sgcriticidad);
                    sgncListNewSgnc = em.merge(sgncListNewSgnc);
                    if (oldIdcriticidadOfSgncListNewSgnc != null && !oldIdcriticidadOfSgncListNewSgnc.equals(sgcriticidad)) {
                        oldIdcriticidadOfSgncListNewSgnc.getSgncList().remove(sgncListNewSgnc);
                        oldIdcriticidadOfSgncListNewSgnc = em.merge(oldIdcriticidadOfSgncListNewSgnc);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = sgcriticidad.getIdcriticidad();
                if (findSgcriticidad(id) == null) {
                    throw new NonexistentEntityException("The sgcriticidad with id " + id + " no longer exists.");
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
            Sgcriticidad sgcriticidad;
            try {
                sgcriticidad = em.getReference(Sgcriticidad.class, id);
                sgcriticidad.getIdcriticidad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgcriticidad with id " + id + " no longer exists.", enfe);
            }
            List<Sgnc> sgncList = sgcriticidad.getSgncList();
            for (Sgnc sgncListSgnc : sgncList) {
                sgncListSgnc.setIdcriticidad(null);
                sgncListSgnc = em.merge(sgncListSgnc);
            }
            em.remove(sgcriticidad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sgcriticidad> findSgcriticidadEntities() {
        return findSgcriticidadEntities(true, -1, -1);
    }

    public List<Sgcriticidad> findSgcriticidadEntities(int maxResults, int firstResult) {
        return findSgcriticidadEntities(false, maxResults, firstResult);
    }

    private List<Sgcriticidad> findSgcriticidadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sgcriticidad.class));
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

    public Sgcriticidad findSgcriticidad(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sgcriticidad.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgcriticidadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sgcriticidad> rt = cq.from(Sgcriticidad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
