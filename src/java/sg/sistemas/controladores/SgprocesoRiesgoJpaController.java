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
import sg.sistemas.entidades.SgprocesoRiesgo;
import sg.sistemas.entidades.SgprocesoRiesgoPK;
import sg.sistemas.entidades.Sgriesgo;
import sg.sistemas.entidades.SgvaloracionRiesgo;

/**
 *
 * @author Misael
 */
public class SgprocesoRiesgoJpaController implements Serializable {

    public SgprocesoRiesgoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgprocesoRiesgo sgprocesoRiesgo) throws PreexistingEntityException, Exception {
        if (sgprocesoRiesgo.getSgprocesoRiesgoPK() == null) {
            sgprocesoRiesgo.setSgprocesoRiesgoPK(new SgprocesoRiesgoPK());
        }
        sgprocesoRiesgo.getSgprocesoRiesgoPK().setIdriesgo(sgprocesoRiesgo.getSgriesgo().getSgriesgoPK().getIdriesgo());
        sgprocesoRiesgo.getSgprocesoRiesgoPK().setIdsociedad(sgprocesoRiesgo.getSgriesgo().getSgriesgoPK().getIdsociedad());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgriesgo sgriesgo = sgprocesoRiesgo.getSgriesgo();
            if (sgriesgo != null) {
                sgriesgo = em.getReference(sgriesgo.getClass(), sgriesgo.getSgriesgoPK());
                sgprocesoRiesgo.setSgriesgo(sgriesgo);
            }
            SgvaloracionRiesgo idvaloracion = sgprocesoRiesgo.getIdvaloracion();
            if (idvaloracion != null) {
                idvaloracion = em.getReference(idvaloracion.getClass(), idvaloracion.getIdvaloracion());
                sgprocesoRiesgo.setIdvaloracion(idvaloracion);
            }
            em.persist(sgprocesoRiesgo);
            if (sgriesgo != null) {
                sgriesgo.getSgprocesoRiesgoList().add(sgprocesoRiesgo);
                sgriesgo = em.merge(sgriesgo);
            }
            if (idvaloracion != null) {
                idvaloracion.getSgprocesoRiesgoList().add(sgprocesoRiesgo);
                idvaloracion = em.merge(idvaloracion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgprocesoRiesgo(sgprocesoRiesgo.getSgprocesoRiesgoPK()) != null) {
                throw new PreexistingEntityException("SgprocesoRiesgo " + sgprocesoRiesgo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgprocesoRiesgo sgprocesoRiesgo) throws NonexistentEntityException, Exception {
        sgprocesoRiesgo.getSgprocesoRiesgoPK().setIdriesgo(sgprocesoRiesgo.getSgriesgo().getSgriesgoPK().getIdriesgo());
        sgprocesoRiesgo.getSgprocesoRiesgoPK().setIdsociedad(sgprocesoRiesgo.getSgriesgo().getSgriesgoPK().getIdsociedad());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgprocesoRiesgo persistentSgprocesoRiesgo = em.find(SgprocesoRiesgo.class, sgprocesoRiesgo.getSgprocesoRiesgoPK());
            Sgriesgo sgriesgoOld = persistentSgprocesoRiesgo.getSgriesgo();
            Sgriesgo sgriesgoNew = sgprocesoRiesgo.getSgriesgo();
            SgvaloracionRiesgo idvaloracionOld = persistentSgprocesoRiesgo.getIdvaloracion();
            SgvaloracionRiesgo idvaloracionNew = sgprocesoRiesgo.getIdvaloracion();
            if (sgriesgoNew != null) {
                sgriesgoNew = em.getReference(sgriesgoNew.getClass(), sgriesgoNew.getSgriesgoPK());
                sgprocesoRiesgo.setSgriesgo(sgriesgoNew);
            }
            if (idvaloracionNew != null) {
                idvaloracionNew = em.getReference(idvaloracionNew.getClass(), idvaloracionNew.getIdvaloracion());
                sgprocesoRiesgo.setIdvaloracion(idvaloracionNew);
            }
            sgprocesoRiesgo = em.merge(sgprocesoRiesgo);
            if (sgriesgoOld != null && !sgriesgoOld.equals(sgriesgoNew)) {
                sgriesgoOld.getSgprocesoRiesgoList().remove(sgprocesoRiesgo);
                sgriesgoOld = em.merge(sgriesgoOld);
            }
            if (sgriesgoNew != null && !sgriesgoNew.equals(sgriesgoOld)) {
                sgriesgoNew.getSgprocesoRiesgoList().add(sgprocesoRiesgo);
                sgriesgoNew = em.merge(sgriesgoNew);
            }
            if (idvaloracionOld != null && !idvaloracionOld.equals(idvaloracionNew)) {
                idvaloracionOld.getSgprocesoRiesgoList().remove(sgprocesoRiesgo);
                idvaloracionOld = em.merge(idvaloracionOld);
            }
            if (idvaloracionNew != null && !idvaloracionNew.equals(idvaloracionOld)) {
                idvaloracionNew.getSgprocesoRiesgoList().add(sgprocesoRiesgo);
                idvaloracionNew = em.merge(idvaloracionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgprocesoRiesgoPK id = sgprocesoRiesgo.getSgprocesoRiesgoPK();
                if (findSgprocesoRiesgo(id) == null) {
                    throw new NonexistentEntityException("The sgprocesoRiesgo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgprocesoRiesgoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgprocesoRiesgo sgprocesoRiesgo;
            try {
                sgprocesoRiesgo = em.getReference(SgprocesoRiesgo.class, id);
                sgprocesoRiesgo.getSgprocesoRiesgoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgprocesoRiesgo with id " + id + " no longer exists.", enfe);
            }
            Sgriesgo sgriesgo = sgprocesoRiesgo.getSgriesgo();
            if (sgriesgo != null) {
                sgriesgo.getSgprocesoRiesgoList().remove(sgprocesoRiesgo);
                sgriesgo = em.merge(sgriesgo);
            }
            SgvaloracionRiesgo idvaloracion = sgprocesoRiesgo.getIdvaloracion();
            if (idvaloracion != null) {
                idvaloracion.getSgprocesoRiesgoList().remove(sgprocesoRiesgo);
                idvaloracion = em.merge(idvaloracion);
            }
            em.remove(sgprocesoRiesgo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgprocesoRiesgo> findSgprocesoRiesgoEntities() {
        return findSgprocesoRiesgoEntities(true, -1, -1);
    }

    public List<SgprocesoRiesgo> findSgprocesoRiesgoEntities(int maxResults, int firstResult) {
        return findSgprocesoRiesgoEntities(false, maxResults, firstResult);
    }

    private List<SgprocesoRiesgo> findSgprocesoRiesgoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgprocesoRiesgo.class));
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

    public SgprocesoRiesgo findSgprocesoRiesgo(SgprocesoRiesgoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgprocesoRiesgo.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgprocesoRiesgoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgprocesoRiesgo> rt = cq.from(SgprocesoRiesgo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
