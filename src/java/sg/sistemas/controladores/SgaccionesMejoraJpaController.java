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
import sg.sistemas.entidades.SgUsuario;
import sg.sistemas.entidades.SgaccionesMejora;
import sg.sistemas.entidades.SgaccionesMejoraPK;

/**
 *
 * @author Misael
 */
public class SgaccionesMejoraJpaController implements Serializable {

    public SgaccionesMejoraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgaccionesMejora sgaccionesMejora) throws PreexistingEntityException, Exception {
        if (sgaccionesMejora.getSgaccionesMejoraPK() == null) {
            sgaccionesMejora.setSgaccionesMejoraPK(new SgaccionesMejoraPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgUsuario codUsuario = sgaccionesMejora.getCodUsuario();
            if (codUsuario != null) {
                codUsuario = em.getReference(codUsuario.getClass(), codUsuario.getCodUsuario());
                sgaccionesMejora.setCodUsuario(codUsuario);
            }
            em.persist(sgaccionesMejora);
            if (codUsuario != null) {
                codUsuario.getSgaccionesMejoraList().add(sgaccionesMejora);
                codUsuario = em.merge(codUsuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgaccionesMejora(sgaccionesMejora.getSgaccionesMejoraPK()) != null) {
                throw new PreexistingEntityException("SgaccionesMejora " + sgaccionesMejora + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgaccionesMejora sgaccionesMejora) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgaccionesMejora persistentSgaccionesMejora = em.find(SgaccionesMejora.class, sgaccionesMejora.getSgaccionesMejoraPK());
            SgUsuario codUsuarioOld = persistentSgaccionesMejora.getCodUsuario();
            SgUsuario codUsuarioNew = sgaccionesMejora.getCodUsuario();
            if (codUsuarioNew != null) {
                codUsuarioNew = em.getReference(codUsuarioNew.getClass(), codUsuarioNew.getCodUsuario());
                sgaccionesMejora.setCodUsuario(codUsuarioNew);
            }
            sgaccionesMejora = em.merge(sgaccionesMejora);
            if (codUsuarioOld != null && !codUsuarioOld.equals(codUsuarioNew)) {
                codUsuarioOld.getSgaccionesMejoraList().remove(sgaccionesMejora);
                codUsuarioOld = em.merge(codUsuarioOld);
            }
            if (codUsuarioNew != null && !codUsuarioNew.equals(codUsuarioOld)) {
                codUsuarioNew.getSgaccionesMejoraList().add(sgaccionesMejora);
                codUsuarioNew = em.merge(codUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgaccionesMejoraPK id = sgaccionesMejora.getSgaccionesMejoraPK();
                if (findSgaccionesMejora(id) == null) {
                    throw new NonexistentEntityException("The sgaccionesMejora with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgaccionesMejoraPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgaccionesMejora sgaccionesMejora;
            try {
                sgaccionesMejora = em.getReference(SgaccionesMejora.class, id);
                sgaccionesMejora.getSgaccionesMejoraPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgaccionesMejora with id " + id + " no longer exists.", enfe);
            }
            SgUsuario codUsuario = sgaccionesMejora.getCodUsuario();
            if (codUsuario != null) {
                codUsuario.getSgaccionesMejoraList().remove(sgaccionesMejora);
                codUsuario = em.merge(codUsuario);
            }
            em.remove(sgaccionesMejora);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgaccionesMejora> findSgaccionesMejoraEntities() {
        return findSgaccionesMejoraEntities(true, -1, -1);
    }

    public List<SgaccionesMejora> findSgaccionesMejoraEntities(int maxResults, int firstResult) {
        return findSgaccionesMejoraEntities(false, maxResults, firstResult);
    }

    private List<SgaccionesMejora> findSgaccionesMejoraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgaccionesMejora.class));
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

    public SgaccionesMejora findSgaccionesMejora(SgaccionesMejoraPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgaccionesMejora.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgaccionesMejoraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgaccionesMejora> rt = cq.from(SgaccionesMejora.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
