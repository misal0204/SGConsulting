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
import sg.sistemas.entidades.SgcapturaEncuesta;
import sg.sistemas.entidades.SgcapturaEncuestaPK;

/**
 *
 * @author Misael
 */
public class SgcapturaEncuestaJpaController implements Serializable {

    public SgcapturaEncuestaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgcapturaEncuesta sgcapturaEncuesta) throws PreexistingEntityException, Exception {
        if (sgcapturaEncuesta.getSgcapturaEncuestaPK() == null) {
            sgcapturaEncuesta.setSgcapturaEncuestaPK(new SgcapturaEncuestaPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgUsuario codUsuario = sgcapturaEncuesta.getCodUsuario();
            if (codUsuario != null) {
                codUsuario = em.getReference(codUsuario.getClass(), codUsuario.getCodUsuario());
                sgcapturaEncuesta.setCodUsuario(codUsuario);
            }
            em.persist(sgcapturaEncuesta);
            if (codUsuario != null) {
                codUsuario.getSgcapturaEncuestaList().add(sgcapturaEncuesta);
                codUsuario = em.merge(codUsuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgcapturaEncuesta(sgcapturaEncuesta.getSgcapturaEncuestaPK()) != null) {
                throw new PreexistingEntityException("SgcapturaEncuesta " + sgcapturaEncuesta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgcapturaEncuesta sgcapturaEncuesta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgcapturaEncuesta persistentSgcapturaEncuesta = em.find(SgcapturaEncuesta.class, sgcapturaEncuesta.getSgcapturaEncuestaPK());
            SgUsuario codUsuarioOld = persistentSgcapturaEncuesta.getCodUsuario();
            SgUsuario codUsuarioNew = sgcapturaEncuesta.getCodUsuario();
            if (codUsuarioNew != null) {
                codUsuarioNew = em.getReference(codUsuarioNew.getClass(), codUsuarioNew.getCodUsuario());
                sgcapturaEncuesta.setCodUsuario(codUsuarioNew);
            }
            sgcapturaEncuesta = em.merge(sgcapturaEncuesta);
            if (codUsuarioOld != null && !codUsuarioOld.equals(codUsuarioNew)) {
                codUsuarioOld.getSgcapturaEncuestaList().remove(sgcapturaEncuesta);
                codUsuarioOld = em.merge(codUsuarioOld);
            }
            if (codUsuarioNew != null && !codUsuarioNew.equals(codUsuarioOld)) {
                codUsuarioNew.getSgcapturaEncuestaList().add(sgcapturaEncuesta);
                codUsuarioNew = em.merge(codUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgcapturaEncuestaPK id = sgcapturaEncuesta.getSgcapturaEncuestaPK();
                if (findSgcapturaEncuesta(id) == null) {
                    throw new NonexistentEntityException("The sgcapturaEncuesta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgcapturaEncuestaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgcapturaEncuesta sgcapturaEncuesta;
            try {
                sgcapturaEncuesta = em.getReference(SgcapturaEncuesta.class, id);
                sgcapturaEncuesta.getSgcapturaEncuestaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgcapturaEncuesta with id " + id + " no longer exists.", enfe);
            }
            SgUsuario codUsuario = sgcapturaEncuesta.getCodUsuario();
            if (codUsuario != null) {
                codUsuario.getSgcapturaEncuestaList().remove(sgcapturaEncuesta);
                codUsuario = em.merge(codUsuario);
            }
            em.remove(sgcapturaEncuesta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgcapturaEncuesta> findSgcapturaEncuestaEntities() {
        return findSgcapturaEncuestaEntities(true, -1, -1);
    }

    public List<SgcapturaEncuesta> findSgcapturaEncuestaEntities(int maxResults, int firstResult) {
        return findSgcapturaEncuestaEntities(false, maxResults, firstResult);
    }

    private List<SgcapturaEncuesta> findSgcapturaEncuestaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgcapturaEncuesta.class));
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

    public SgcapturaEncuesta findSgcapturaEncuesta(SgcapturaEncuestaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgcapturaEncuesta.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgcapturaEncuestaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgcapturaEncuesta> rt = cq.from(SgcapturaEncuesta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
