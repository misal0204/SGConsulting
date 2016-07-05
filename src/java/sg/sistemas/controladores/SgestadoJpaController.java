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
import sg.sistemas.entidades.Sgestado;

/**
 *
 * @author Misael
 */
public class SgestadoJpaController implements Serializable {

    public SgestadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sgestado sgestado) throws PreexistingEntityException, Exception {
        if (sgestado.getSgncList() == null) {
            sgestado.setSgncList(new ArrayList<Sgnc>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Sgnc> attachedSgncList = new ArrayList<Sgnc>();
            for (Sgnc sgncListSgncToAttach : sgestado.getSgncList()) {
                sgncListSgncToAttach = em.getReference(sgncListSgncToAttach.getClass(), sgncListSgncToAttach.getSgncPK());
                attachedSgncList.add(sgncListSgncToAttach);
            }
            sgestado.setSgncList(attachedSgncList);
            em.persist(sgestado);
            for (Sgnc sgncListSgnc : sgestado.getSgncList()) {
                Sgestado oldIdestadoOfSgncListSgnc = sgncListSgnc.getIdestado();
                sgncListSgnc.setIdestado(sgestado);
                sgncListSgnc = em.merge(sgncListSgnc);
                if (oldIdestadoOfSgncListSgnc != null) {
                    oldIdestadoOfSgncListSgnc.getSgncList().remove(sgncListSgnc);
                    oldIdestadoOfSgncListSgnc = em.merge(oldIdestadoOfSgncListSgnc);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgestado(sgestado.getIdestado()) != null) {
                throw new PreexistingEntityException("Sgestado " + sgestado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sgestado sgestado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgestado persistentSgestado = em.find(Sgestado.class, sgestado.getIdestado());
            List<Sgnc> sgncListOld = persistentSgestado.getSgncList();
            List<Sgnc> sgncListNew = sgestado.getSgncList();
            List<Sgnc> attachedSgncListNew = new ArrayList<Sgnc>();
            for (Sgnc sgncListNewSgncToAttach : sgncListNew) {
                sgncListNewSgncToAttach = em.getReference(sgncListNewSgncToAttach.getClass(), sgncListNewSgncToAttach.getSgncPK());
                attachedSgncListNew.add(sgncListNewSgncToAttach);
            }
            sgncListNew = attachedSgncListNew;
            sgestado.setSgncList(sgncListNew);
            sgestado = em.merge(sgestado);
            for (Sgnc sgncListOldSgnc : sgncListOld) {
                if (!sgncListNew.contains(sgncListOldSgnc)) {
                    sgncListOldSgnc.setIdestado(null);
                    sgncListOldSgnc = em.merge(sgncListOldSgnc);
                }
            }
            for (Sgnc sgncListNewSgnc : sgncListNew) {
                if (!sgncListOld.contains(sgncListNewSgnc)) {
                    Sgestado oldIdestadoOfSgncListNewSgnc = sgncListNewSgnc.getIdestado();
                    sgncListNewSgnc.setIdestado(sgestado);
                    sgncListNewSgnc = em.merge(sgncListNewSgnc);
                    if (oldIdestadoOfSgncListNewSgnc != null && !oldIdestadoOfSgncListNewSgnc.equals(sgestado)) {
                        oldIdestadoOfSgncListNewSgnc.getSgncList().remove(sgncListNewSgnc);
                        oldIdestadoOfSgncListNewSgnc = em.merge(oldIdestadoOfSgncListNewSgnc);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = sgestado.getIdestado();
                if (findSgestado(id) == null) {
                    throw new NonexistentEntityException("The sgestado with id " + id + " no longer exists.");
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
            Sgestado sgestado;
            try {
                sgestado = em.getReference(Sgestado.class, id);
                sgestado.getIdestado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgestado with id " + id + " no longer exists.", enfe);
            }
            List<Sgnc> sgncList = sgestado.getSgncList();
            for (Sgnc sgncListSgnc : sgncList) {
                sgncListSgnc.setIdestado(null);
                sgncListSgnc = em.merge(sgncListSgnc);
            }
            em.remove(sgestado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sgestado> findSgestadoEntities() {
        return findSgestadoEntities(true, -1, -1);
    }

    public List<Sgestado> findSgestadoEntities(int maxResults, int firstResult) {
        return findSgestadoEntities(false, maxResults, firstResult);
    }

    private List<Sgestado> findSgestadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sgestado.class));
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

    public Sgestado findSgestado(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sgestado.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgestadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sgestado> rt = cq.from(Sgestado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
