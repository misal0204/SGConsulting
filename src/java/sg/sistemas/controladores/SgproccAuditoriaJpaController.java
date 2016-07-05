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
import sg.sistemas.entidades.SgprocesoDetalle;
import sg.sistemas.entidades.SgprogAuditoria;
import sg.sistemas.entidades.Sgauditado;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgproccAuditoria;
import sg.sistemas.entidades.SgproccAuditoriaPK;

/**
 *
 * @author Misael
 */
public class SgproccAuditoriaJpaController implements Serializable {

    public SgproccAuditoriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgproccAuditoria sgproccAuditoria) throws PreexistingEntityException, Exception {
        if (sgproccAuditoria.getSgproccAuditoriaPK() == null) {
            sgproccAuditoria.setSgproccAuditoriaPK(new SgproccAuditoriaPK());
        }
        sgproccAuditoria.getSgproccAuditoriaPK().setIdsociedad(sgproccAuditoria.getSgprogAuditoria().getSgprogAuditoriaPK().getIdsociedad());
        sgproccAuditoria.getSgproccAuditoriaPK().setIdprogAud(sgproccAuditoria.getSgprogAuditoria().getSgprogAuditoriaPK().getIdprogAud());
        sgproccAuditoria.getSgproccAuditoriaPK().setSubproceso(sgproccAuditoria.getSgprocesoDetalle().getSgprocesoDetallePK().getSubproceso());
        sgproccAuditoria.getSgproccAuditoriaPK().setIdprocesos(sgproccAuditoria.getSgprocesoDetalle().getSgprocesoDetallePK().getIdprocesos());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgprocesoDetalle sgprocesoDetalle = sgproccAuditoria.getSgprocesoDetalle();
            if (sgprocesoDetalle != null) {
                sgprocesoDetalle = em.getReference(sgprocesoDetalle.getClass(), sgprocesoDetalle.getSgprocesoDetallePK());
                sgproccAuditoria.setSgprocesoDetalle(sgprocesoDetalle);
            }
            SgprogAuditoria sgprogAuditoria = sgproccAuditoria.getSgprogAuditoria();
            if (sgprogAuditoria != null) {
                sgprogAuditoria = em.getReference(sgprogAuditoria.getClass(), sgprogAuditoria.getSgprogAuditoriaPK());
                sgproccAuditoria.setSgprogAuditoria(sgprogAuditoria);
            }
            Sgauditado sgauditado = sgproccAuditoria.getSgauditado();
            if (sgauditado != null) {
                sgauditado = em.getReference(sgauditado.getClass(), sgauditado.getSgauditadoPK());
                sgproccAuditoria.setSgauditado(sgauditado);
            }
            em.persist(sgproccAuditoria);
            if (sgprocesoDetalle != null) {
                sgprocesoDetalle.getSgproccAuditoriaList().add(sgproccAuditoria);
                sgprocesoDetalle = em.merge(sgprocesoDetalle);
            }
            if (sgprogAuditoria != null) {
                sgprogAuditoria.getSgproccAuditoriaList().add(sgproccAuditoria);
                sgprogAuditoria = em.merge(sgprogAuditoria);
            }
            if (sgauditado != null) {
                SgproccAuditoria oldSgproccAuditoriaOfSgauditado = sgauditado.getSgproccAuditoria();
                if (oldSgproccAuditoriaOfSgauditado != null) {
                    oldSgproccAuditoriaOfSgauditado.setSgauditado(null);
                    oldSgproccAuditoriaOfSgauditado = em.merge(oldSgproccAuditoriaOfSgauditado);
                }
                sgauditado.setSgproccAuditoria(sgproccAuditoria);
                sgauditado = em.merge(sgauditado);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgproccAuditoria(sgproccAuditoria.getSgproccAuditoriaPK()) != null) {
                throw new PreexistingEntityException("SgproccAuditoria " + sgproccAuditoria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgproccAuditoria sgproccAuditoria) throws IllegalOrphanException, NonexistentEntityException, Exception {
        sgproccAuditoria.getSgproccAuditoriaPK().setIdsociedad(sgproccAuditoria.getSgprogAuditoria().getSgprogAuditoriaPK().getIdsociedad());
        sgproccAuditoria.getSgproccAuditoriaPK().setIdprogAud(sgproccAuditoria.getSgprogAuditoria().getSgprogAuditoriaPK().getIdprogAud());
        sgproccAuditoria.getSgproccAuditoriaPK().setSubproceso(sgproccAuditoria.getSgprocesoDetalle().getSgprocesoDetallePK().getSubproceso());
        sgproccAuditoria.getSgproccAuditoriaPK().setIdprocesos(sgproccAuditoria.getSgprocesoDetalle().getSgprocesoDetallePK().getIdprocesos());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgproccAuditoria persistentSgproccAuditoria = em.find(SgproccAuditoria.class, sgproccAuditoria.getSgproccAuditoriaPK());
            SgprocesoDetalle sgprocesoDetalleOld = persistentSgproccAuditoria.getSgprocesoDetalle();
            SgprocesoDetalle sgprocesoDetalleNew = sgproccAuditoria.getSgprocesoDetalle();
            SgprogAuditoria sgprogAuditoriaOld = persistentSgproccAuditoria.getSgprogAuditoria();
            SgprogAuditoria sgprogAuditoriaNew = sgproccAuditoria.getSgprogAuditoria();
            Sgauditado sgauditadoOld = persistentSgproccAuditoria.getSgauditado();
            Sgauditado sgauditadoNew = sgproccAuditoria.getSgauditado();
            List<String> illegalOrphanMessages = null;
            if (sgauditadoOld != null && !sgauditadoOld.equals(sgauditadoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Sgauditado " + sgauditadoOld + " since its sgproccAuditoria field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sgprocesoDetalleNew != null) {
                sgprocesoDetalleNew = em.getReference(sgprocesoDetalleNew.getClass(), sgprocesoDetalleNew.getSgprocesoDetallePK());
                sgproccAuditoria.setSgprocesoDetalle(sgprocesoDetalleNew);
            }
            if (sgprogAuditoriaNew != null) {
                sgprogAuditoriaNew = em.getReference(sgprogAuditoriaNew.getClass(), sgprogAuditoriaNew.getSgprogAuditoriaPK());
                sgproccAuditoria.setSgprogAuditoria(sgprogAuditoriaNew);
            }
            if (sgauditadoNew != null) {
                sgauditadoNew = em.getReference(sgauditadoNew.getClass(), sgauditadoNew.getSgauditadoPK());
                sgproccAuditoria.setSgauditado(sgauditadoNew);
            }
            sgproccAuditoria = em.merge(sgproccAuditoria);
            if (sgprocesoDetalleOld != null && !sgprocesoDetalleOld.equals(sgprocesoDetalleNew)) {
                sgprocesoDetalleOld.getSgproccAuditoriaList().remove(sgproccAuditoria);
                sgprocesoDetalleOld = em.merge(sgprocesoDetalleOld);
            }
            if (sgprocesoDetalleNew != null && !sgprocesoDetalleNew.equals(sgprocesoDetalleOld)) {
                sgprocesoDetalleNew.getSgproccAuditoriaList().add(sgproccAuditoria);
                sgprocesoDetalleNew = em.merge(sgprocesoDetalleNew);
            }
            if (sgprogAuditoriaOld != null && !sgprogAuditoriaOld.equals(sgprogAuditoriaNew)) {
                sgprogAuditoriaOld.getSgproccAuditoriaList().remove(sgproccAuditoria);
                sgprogAuditoriaOld = em.merge(sgprogAuditoriaOld);
            }
            if (sgprogAuditoriaNew != null && !sgprogAuditoriaNew.equals(sgprogAuditoriaOld)) {
                sgprogAuditoriaNew.getSgproccAuditoriaList().add(sgproccAuditoria);
                sgprogAuditoriaNew = em.merge(sgprogAuditoriaNew);
            }
            if (sgauditadoNew != null && !sgauditadoNew.equals(sgauditadoOld)) {
                SgproccAuditoria oldSgproccAuditoriaOfSgauditado = sgauditadoNew.getSgproccAuditoria();
                if (oldSgproccAuditoriaOfSgauditado != null) {
                    oldSgproccAuditoriaOfSgauditado.setSgauditado(null);
                    oldSgproccAuditoriaOfSgauditado = em.merge(oldSgproccAuditoriaOfSgauditado);
                }
                sgauditadoNew.setSgproccAuditoria(sgproccAuditoria);
                sgauditadoNew = em.merge(sgauditadoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgproccAuditoriaPK id = sgproccAuditoria.getSgproccAuditoriaPK();
                if (findSgproccAuditoria(id) == null) {
                    throw new NonexistentEntityException("The sgproccAuditoria with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgproccAuditoriaPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgproccAuditoria sgproccAuditoria;
            try {
                sgproccAuditoria = em.getReference(SgproccAuditoria.class, id);
                sgproccAuditoria.getSgproccAuditoriaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgproccAuditoria with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Sgauditado sgauditadoOrphanCheck = sgproccAuditoria.getSgauditado();
            if (sgauditadoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SgproccAuditoria (" + sgproccAuditoria + ") cannot be destroyed since the Sgauditado " + sgauditadoOrphanCheck + " in its sgauditado field has a non-nullable sgproccAuditoria field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            SgprocesoDetalle sgprocesoDetalle = sgproccAuditoria.getSgprocesoDetalle();
            if (sgprocesoDetalle != null) {
                sgprocesoDetalle.getSgproccAuditoriaList().remove(sgproccAuditoria);
                sgprocesoDetalle = em.merge(sgprocesoDetalle);
            }
            SgprogAuditoria sgprogAuditoria = sgproccAuditoria.getSgprogAuditoria();
            if (sgprogAuditoria != null) {
                sgprogAuditoria.getSgproccAuditoriaList().remove(sgproccAuditoria);
                sgprogAuditoria = em.merge(sgprogAuditoria);
            }
            em.remove(sgproccAuditoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgproccAuditoria> findSgproccAuditoriaEntities() {
        return findSgproccAuditoriaEntities(true, -1, -1);
    }

    public List<SgproccAuditoria> findSgproccAuditoriaEntities(int maxResults, int firstResult) {
        return findSgproccAuditoriaEntities(false, maxResults, firstResult);
    }

    private List<SgproccAuditoria> findSgproccAuditoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgproccAuditoria.class));
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

    public SgproccAuditoria findSgproccAuditoria(SgproccAuditoriaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgproccAuditoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgproccAuditoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgproccAuditoria> rt = cq.from(SgproccAuditoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
