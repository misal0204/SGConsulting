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
import sg.sistemas.entidades.SgobjetivoIndicadores;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgindicadorProceso;
import sg.sistemas.entidades.SgindicadorProcesoPK;

/**
 *
 * @author Misael
 */
public class SgindicadorProcesoJpaController implements Serializable {

    public SgindicadorProcesoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgindicadorProceso sgindicadorProceso) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (sgindicadorProceso.getSgindicadorProcesoPK() == null) {
            sgindicadorProceso.setSgindicadorProcesoPK(new SgindicadorProcesoPK());
        }
        sgindicadorProceso.getSgindicadorProcesoPK().setIdobjIndicador(sgindicadorProceso.getSgobjetivoIndicadores().getSgobjetivoIndicadoresPK().getIdobjIndicador());
        sgindicadorProceso.getSgindicadorProcesoPK().setIdsociedad(sgindicadorProceso.getSgobjetivoIndicadores().getSgobjetivoIndicadoresPK().getIdsociedad());
        List<String> illegalOrphanMessages = null;
        SgobjetivoIndicadores sgobjetivoIndicadoresOrphanCheck = sgindicadorProceso.getSgobjetivoIndicadores();
        if (sgobjetivoIndicadoresOrphanCheck != null) {
            SgindicadorProceso oldSgindicadorProcesoOfSgobjetivoIndicadores = sgobjetivoIndicadoresOrphanCheck.getSgindicadorProceso();
            if (oldSgindicadorProcesoOfSgobjetivoIndicadores != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The SgobjetivoIndicadores " + sgobjetivoIndicadoresOrphanCheck + " already has an item of type SgindicadorProceso whose sgobjetivoIndicadores column cannot be null. Please make another selection for the sgobjetivoIndicadores field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgobjetivoIndicadores sgobjetivoIndicadores = sgindicadorProceso.getSgobjetivoIndicadores();
            if (sgobjetivoIndicadores != null) {
                sgobjetivoIndicadores = em.getReference(sgobjetivoIndicadores.getClass(), sgobjetivoIndicadores.getSgobjetivoIndicadoresPK());
                sgindicadorProceso.setSgobjetivoIndicadores(sgobjetivoIndicadores);
            }
            em.persist(sgindicadorProceso);
            if (sgobjetivoIndicadores != null) {
                sgobjetivoIndicadores.setSgindicadorProceso(sgindicadorProceso);
                sgobjetivoIndicadores = em.merge(sgobjetivoIndicadores);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgindicadorProceso(sgindicadorProceso.getSgindicadorProcesoPK()) != null) {
                throw new PreexistingEntityException("SgindicadorProceso " + sgindicadorProceso + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgindicadorProceso sgindicadorProceso) throws IllegalOrphanException, NonexistentEntityException, Exception {
        sgindicadorProceso.getSgindicadorProcesoPK().setIdobjIndicador(sgindicadorProceso.getSgobjetivoIndicadores().getSgobjetivoIndicadoresPK().getIdobjIndicador());
        sgindicadorProceso.getSgindicadorProcesoPK().setIdsociedad(sgindicadorProceso.getSgobjetivoIndicadores().getSgobjetivoIndicadoresPK().getIdsociedad());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgindicadorProceso persistentSgindicadorProceso = em.find(SgindicadorProceso.class, sgindicadorProceso.getSgindicadorProcesoPK());
            SgobjetivoIndicadores sgobjetivoIndicadoresOld = persistentSgindicadorProceso.getSgobjetivoIndicadores();
            SgobjetivoIndicadores sgobjetivoIndicadoresNew = sgindicadorProceso.getSgobjetivoIndicadores();
            List<String> illegalOrphanMessages = null;
            if (sgobjetivoIndicadoresNew != null && !sgobjetivoIndicadoresNew.equals(sgobjetivoIndicadoresOld)) {
                SgindicadorProceso oldSgindicadorProcesoOfSgobjetivoIndicadores = sgobjetivoIndicadoresNew.getSgindicadorProceso();
                if (oldSgindicadorProcesoOfSgobjetivoIndicadores != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The SgobjetivoIndicadores " + sgobjetivoIndicadoresNew + " already has an item of type SgindicadorProceso whose sgobjetivoIndicadores column cannot be null. Please make another selection for the sgobjetivoIndicadores field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sgobjetivoIndicadoresNew != null) {
                sgobjetivoIndicadoresNew = em.getReference(sgobjetivoIndicadoresNew.getClass(), sgobjetivoIndicadoresNew.getSgobjetivoIndicadoresPK());
                sgindicadorProceso.setSgobjetivoIndicadores(sgobjetivoIndicadoresNew);
            }
            sgindicadorProceso = em.merge(sgindicadorProceso);
            if (sgobjetivoIndicadoresOld != null && !sgobjetivoIndicadoresOld.equals(sgobjetivoIndicadoresNew)) {
                sgobjetivoIndicadoresOld.setSgindicadorProceso(null);
                sgobjetivoIndicadoresOld = em.merge(sgobjetivoIndicadoresOld);
            }
            if (sgobjetivoIndicadoresNew != null && !sgobjetivoIndicadoresNew.equals(sgobjetivoIndicadoresOld)) {
                sgobjetivoIndicadoresNew.setSgindicadorProceso(sgindicadorProceso);
                sgobjetivoIndicadoresNew = em.merge(sgobjetivoIndicadoresNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgindicadorProcesoPK id = sgindicadorProceso.getSgindicadorProcesoPK();
                if (findSgindicadorProceso(id) == null) {
                    throw new NonexistentEntityException("The sgindicadorProceso with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgindicadorProcesoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgindicadorProceso sgindicadorProceso;
            try {
                sgindicadorProceso = em.getReference(SgindicadorProceso.class, id);
                sgindicadorProceso.getSgindicadorProcesoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgindicadorProceso with id " + id + " no longer exists.", enfe);
            }
            SgobjetivoIndicadores sgobjetivoIndicadores = sgindicadorProceso.getSgobjetivoIndicadores();
            if (sgobjetivoIndicadores != null) {
                sgobjetivoIndicadores.setSgindicadorProceso(null);
                sgobjetivoIndicadores = em.merge(sgobjetivoIndicadores);
            }
            em.remove(sgindicadorProceso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgindicadorProceso> findSgindicadorProcesoEntities() {
        return findSgindicadorProcesoEntities(true, -1, -1);
    }

    public List<SgindicadorProceso> findSgindicadorProcesoEntities(int maxResults, int firstResult) {
        return findSgindicadorProcesoEntities(false, maxResults, firstResult);
    }

    private List<SgindicadorProceso> findSgindicadorProcesoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgindicadorProceso.class));
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

    public SgindicadorProceso findSgindicadorProceso(SgindicadorProcesoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgindicadorProceso.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgindicadorProcesoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgindicadorProceso> rt = cq.from(SgindicadorProceso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
