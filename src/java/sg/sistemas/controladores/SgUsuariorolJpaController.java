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
import sg.sistemas.entidades.SgRol;
import sg.sistemas.entidades.SgUsuariorol;
import sg.sistemas.entidades.SgUsuariorolPK;

/**
 *
 * @author Misael
 */
public class SgUsuariorolJpaController implements Serializable {

    public SgUsuariorolJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgUsuariorol sgUsuariorol) throws PreexistingEntityException, Exception {
        if (sgUsuariorol.getSgUsuariorolPK() == null) {
            sgUsuariorol.setSgUsuariorolPK(new SgUsuariorolPK());
        }
        sgUsuariorol.getSgUsuariorolPK().setCodUsuario(sgUsuariorol.getSgUsuario().getCodUsuario());
        sgUsuariorol.getSgUsuariorolPK().setIdrol(sgUsuariorol.getSgRol().getIdrol());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgUsuario sgUsuario = sgUsuariorol.getSgUsuario();
            if (sgUsuario != null) {
                sgUsuario = em.getReference(sgUsuario.getClass(), sgUsuario.getCodUsuario());
                sgUsuariorol.setSgUsuario(sgUsuario);
            }
            SgRol sgRol = sgUsuariorol.getSgRol();
            if (sgRol != null) {
                sgRol = em.getReference(sgRol.getClass(), sgRol.getIdrol());
                sgUsuariorol.setSgRol(sgRol);
            }
            em.persist(sgUsuariorol);
            if (sgUsuario != null) {
                sgUsuario.getSgUsuariorolList().add(sgUsuariorol);
                sgUsuario = em.merge(sgUsuario);
            }
            if (sgRol != null) {
                sgRol.getSgUsuariorolList().add(sgUsuariorol);
                sgRol = em.merge(sgRol);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgUsuariorol(sgUsuariorol.getSgUsuariorolPK()) != null) {
                throw new PreexistingEntityException("SgUsuariorol " + sgUsuariorol + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgUsuariorol sgUsuariorol) throws NonexistentEntityException, Exception {
        sgUsuariorol.getSgUsuariorolPK().setCodUsuario(sgUsuariorol.getSgUsuario().getCodUsuario());
        sgUsuariorol.getSgUsuariorolPK().setIdrol(sgUsuariorol.getSgRol().getIdrol());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgUsuariorol persistentSgUsuariorol = em.find(SgUsuariorol.class, sgUsuariorol.getSgUsuariorolPK());
            SgUsuario sgUsuarioOld = persistentSgUsuariorol.getSgUsuario();
            SgUsuario sgUsuarioNew = sgUsuariorol.getSgUsuario();
            SgRol sgRolOld = persistentSgUsuariorol.getSgRol();
            SgRol sgRolNew = sgUsuariorol.getSgRol();
            if (sgUsuarioNew != null) {
                sgUsuarioNew = em.getReference(sgUsuarioNew.getClass(), sgUsuarioNew.getCodUsuario());
                sgUsuariorol.setSgUsuario(sgUsuarioNew);
            }
            if (sgRolNew != null) {
                sgRolNew = em.getReference(sgRolNew.getClass(), sgRolNew.getIdrol());
                sgUsuariorol.setSgRol(sgRolNew);
            }
            sgUsuariorol = em.merge(sgUsuariorol);
            if (sgUsuarioOld != null && !sgUsuarioOld.equals(sgUsuarioNew)) {
                sgUsuarioOld.getSgUsuariorolList().remove(sgUsuariorol);
                sgUsuarioOld = em.merge(sgUsuarioOld);
            }
            if (sgUsuarioNew != null && !sgUsuarioNew.equals(sgUsuarioOld)) {
                sgUsuarioNew.getSgUsuariorolList().add(sgUsuariorol);
                sgUsuarioNew = em.merge(sgUsuarioNew);
            }
            if (sgRolOld != null && !sgRolOld.equals(sgRolNew)) {
                sgRolOld.getSgUsuariorolList().remove(sgUsuariorol);
                sgRolOld = em.merge(sgRolOld);
            }
            if (sgRolNew != null && !sgRolNew.equals(sgRolOld)) {
                sgRolNew.getSgUsuariorolList().add(sgUsuariorol);
                sgRolNew = em.merge(sgRolNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgUsuariorolPK id = sgUsuariorol.getSgUsuariorolPK();
                if (findSgUsuariorol(id) == null) {
                    throw new NonexistentEntityException("The sgUsuariorol with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgUsuariorolPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgUsuariorol sgUsuariorol;
            try {
                sgUsuariorol = em.getReference(SgUsuariorol.class, id);
                sgUsuariorol.getSgUsuariorolPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgUsuariorol with id " + id + " no longer exists.", enfe);
            }
            SgUsuario sgUsuario = sgUsuariorol.getSgUsuario();
            if (sgUsuario != null) {
                sgUsuario.getSgUsuariorolList().remove(sgUsuariorol);
                sgUsuario = em.merge(sgUsuario);
            }
            SgRol sgRol = sgUsuariorol.getSgRol();
            if (sgRol != null) {
                sgRol.getSgUsuariorolList().remove(sgUsuariorol);
                sgRol = em.merge(sgRol);
            }
            em.remove(sgUsuariorol);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgUsuariorol> findSgUsuariorolEntities() {
        return findSgUsuariorolEntities(true, -1, -1);
    }

    public List<SgUsuariorol> findSgUsuariorolEntities(int maxResults, int firstResult) {
        return findSgUsuariorolEntities(false, maxResults, firstResult);
    }

    private List<SgUsuariorol> findSgUsuariorolEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgUsuariorol.class));
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

    public SgUsuariorol findSgUsuariorol(SgUsuariorolPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgUsuariorol.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgUsuariorolCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgUsuariorol> rt = cq.from(SgUsuariorol.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
