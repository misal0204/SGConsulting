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
import sg.sistemas.entidades.SgUsuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgrolAuditor;

/**
 *
 * @author Misael
 */
public class SgrolAuditorJpaController implements Serializable {

    public SgrolAuditorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgrolAuditor sgrolAuditor) throws PreexistingEntityException, Exception {
        if (sgrolAuditor.getSgUsuarioList() == null) {
            sgrolAuditor.setSgUsuarioList(new ArrayList<SgUsuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SgUsuario> attachedSgUsuarioList = new ArrayList<SgUsuario>();
            for (SgUsuario sgUsuarioListSgUsuarioToAttach : sgrolAuditor.getSgUsuarioList()) {
                sgUsuarioListSgUsuarioToAttach = em.getReference(sgUsuarioListSgUsuarioToAttach.getClass(), sgUsuarioListSgUsuarioToAttach.getCodUsuario());
                attachedSgUsuarioList.add(sgUsuarioListSgUsuarioToAttach);
            }
            sgrolAuditor.setSgUsuarioList(attachedSgUsuarioList);
            em.persist(sgrolAuditor);
            for (SgUsuario sgUsuarioListSgUsuario : sgrolAuditor.getSgUsuarioList()) {
                SgrolAuditor oldIdrolAuditorOfSgUsuarioListSgUsuario = sgUsuarioListSgUsuario.getIdrolAuditor();
                sgUsuarioListSgUsuario.setIdrolAuditor(sgrolAuditor);
                sgUsuarioListSgUsuario = em.merge(sgUsuarioListSgUsuario);
                if (oldIdrolAuditorOfSgUsuarioListSgUsuario != null) {
                    oldIdrolAuditorOfSgUsuarioListSgUsuario.getSgUsuarioList().remove(sgUsuarioListSgUsuario);
                    oldIdrolAuditorOfSgUsuarioListSgUsuario = em.merge(oldIdrolAuditorOfSgUsuarioListSgUsuario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgrolAuditor(sgrolAuditor.getIdrolAuditor()) != null) {
                throw new PreexistingEntityException("SgrolAuditor " + sgrolAuditor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgrolAuditor sgrolAuditor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgrolAuditor persistentSgrolAuditor = em.find(SgrolAuditor.class, sgrolAuditor.getIdrolAuditor());
            List<SgUsuario> sgUsuarioListOld = persistentSgrolAuditor.getSgUsuarioList();
            List<SgUsuario> sgUsuarioListNew = sgrolAuditor.getSgUsuarioList();
            List<SgUsuario> attachedSgUsuarioListNew = new ArrayList<SgUsuario>();
            for (SgUsuario sgUsuarioListNewSgUsuarioToAttach : sgUsuarioListNew) {
                sgUsuarioListNewSgUsuarioToAttach = em.getReference(sgUsuarioListNewSgUsuarioToAttach.getClass(), sgUsuarioListNewSgUsuarioToAttach.getCodUsuario());
                attachedSgUsuarioListNew.add(sgUsuarioListNewSgUsuarioToAttach);
            }
            sgUsuarioListNew = attachedSgUsuarioListNew;
            sgrolAuditor.setSgUsuarioList(sgUsuarioListNew);
            sgrolAuditor = em.merge(sgrolAuditor);
            for (SgUsuario sgUsuarioListOldSgUsuario : sgUsuarioListOld) {
                if (!sgUsuarioListNew.contains(sgUsuarioListOldSgUsuario)) {
                    sgUsuarioListOldSgUsuario.setIdrolAuditor(null);
                    sgUsuarioListOldSgUsuario = em.merge(sgUsuarioListOldSgUsuario);
                }
            }
            for (SgUsuario sgUsuarioListNewSgUsuario : sgUsuarioListNew) {
                if (!sgUsuarioListOld.contains(sgUsuarioListNewSgUsuario)) {
                    SgrolAuditor oldIdrolAuditorOfSgUsuarioListNewSgUsuario = sgUsuarioListNewSgUsuario.getIdrolAuditor();
                    sgUsuarioListNewSgUsuario.setIdrolAuditor(sgrolAuditor);
                    sgUsuarioListNewSgUsuario = em.merge(sgUsuarioListNewSgUsuario);
                    if (oldIdrolAuditorOfSgUsuarioListNewSgUsuario != null && !oldIdrolAuditorOfSgUsuarioListNewSgUsuario.equals(sgrolAuditor)) {
                        oldIdrolAuditorOfSgUsuarioListNewSgUsuario.getSgUsuarioList().remove(sgUsuarioListNewSgUsuario);
                        oldIdrolAuditorOfSgUsuarioListNewSgUsuario = em.merge(oldIdrolAuditorOfSgUsuarioListNewSgUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = sgrolAuditor.getIdrolAuditor();
                if (findSgrolAuditor(id) == null) {
                    throw new NonexistentEntityException("The sgrolAuditor with id " + id + " no longer exists.");
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
            SgrolAuditor sgrolAuditor;
            try {
                sgrolAuditor = em.getReference(SgrolAuditor.class, id);
                sgrolAuditor.getIdrolAuditor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgrolAuditor with id " + id + " no longer exists.", enfe);
            }
            List<SgUsuario> sgUsuarioList = sgrolAuditor.getSgUsuarioList();
            for (SgUsuario sgUsuarioListSgUsuario : sgUsuarioList) {
                sgUsuarioListSgUsuario.setIdrolAuditor(null);
                sgUsuarioListSgUsuario = em.merge(sgUsuarioListSgUsuario);
            }
            em.remove(sgrolAuditor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgrolAuditor> findSgrolAuditorEntities() {
        return findSgrolAuditorEntities(true, -1, -1);
    }

    public List<SgrolAuditor> findSgrolAuditorEntities(int maxResults, int firstResult) {
        return findSgrolAuditorEntities(false, maxResults, firstResult);
    }

    private List<SgrolAuditor> findSgrolAuditorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgrolAuditor.class));
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

    public SgrolAuditor findSgrolAuditor(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgrolAuditor.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgrolAuditorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgrolAuditor> rt = cq.from(SgrolAuditor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
