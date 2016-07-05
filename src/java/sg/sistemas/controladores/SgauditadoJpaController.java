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
import sg.sistemas.entidades.SgproccAuditoria;
import sg.sistemas.entidades.SgUsuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.Sgauditado;
import sg.sistemas.entidades.SgauditadoPK;

/**
 *
 * @author Misael
 */
public class SgauditadoJpaController implements Serializable {

    public SgauditadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sgauditado sgauditado) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (sgauditado.getSgauditadoPK() == null) {
            sgauditado.setSgauditadoPK(new SgauditadoPK());
        }
        sgauditado.getSgauditadoPK().setIdprogAud(sgauditado.getSgproccAuditoria().getSgproccAuditoriaPK().getIdprogAud());
        sgauditado.getSgauditadoPK().setSubproceso(sgauditado.getSgproccAuditoria().getSgproccAuditoriaPK().getSubproceso());
        sgauditado.getSgauditadoPK().setIdsociedad(sgauditado.getSgproccAuditoria().getSgproccAuditoriaPK().getIdsociedad());
        sgauditado.getSgauditadoPK().setIdprocesos(sgauditado.getSgproccAuditoria().getSgproccAuditoriaPK().getIdprocesos());
        List<String> illegalOrphanMessages = null;
        SgproccAuditoria sgproccAuditoriaOrphanCheck = sgauditado.getSgproccAuditoria();
        if (sgproccAuditoriaOrphanCheck != null) {
            Sgauditado oldSgauditadoOfSgproccAuditoria = sgproccAuditoriaOrphanCheck.getSgauditado();
            if (oldSgauditadoOfSgproccAuditoria != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The SgproccAuditoria " + sgproccAuditoriaOrphanCheck + " already has an item of type Sgauditado whose sgproccAuditoria column cannot be null. Please make another selection for the sgproccAuditoria field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgproccAuditoria sgproccAuditoria = sgauditado.getSgproccAuditoria();
            if (sgproccAuditoria != null) {
                sgproccAuditoria = em.getReference(sgproccAuditoria.getClass(), sgproccAuditoria.getSgproccAuditoriaPK());
                sgauditado.setSgproccAuditoria(sgproccAuditoria);
            }
            SgUsuario codUsuario = sgauditado.getCodUsuario();
            if (codUsuario != null) {
                codUsuario = em.getReference(codUsuario.getClass(), codUsuario.getCodUsuario());
                sgauditado.setCodUsuario(codUsuario);
            }
            em.persist(sgauditado);
            if (sgproccAuditoria != null) {
                sgproccAuditoria.setSgauditado(sgauditado);
                sgproccAuditoria = em.merge(sgproccAuditoria);
            }
            if (codUsuario != null) {
                codUsuario.getSgauditadoList().add(sgauditado);
                codUsuario = em.merge(codUsuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgauditado(sgauditado.getSgauditadoPK()) != null) {
                throw new PreexistingEntityException("Sgauditado " + sgauditado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sgauditado sgauditado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        sgauditado.getSgauditadoPK().setIdprogAud(sgauditado.getSgproccAuditoria().getSgproccAuditoriaPK().getIdprogAud());
        sgauditado.getSgauditadoPK().setSubproceso(sgauditado.getSgproccAuditoria().getSgproccAuditoriaPK().getSubproceso());
        sgauditado.getSgauditadoPK().setIdsociedad(sgauditado.getSgproccAuditoria().getSgproccAuditoriaPK().getIdsociedad());
        sgauditado.getSgauditadoPK().setIdprocesos(sgauditado.getSgproccAuditoria().getSgproccAuditoriaPK().getIdprocesos());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgauditado persistentSgauditado = em.find(Sgauditado.class, sgauditado.getSgauditadoPK());
            SgproccAuditoria sgproccAuditoriaOld = persistentSgauditado.getSgproccAuditoria();
            SgproccAuditoria sgproccAuditoriaNew = sgauditado.getSgproccAuditoria();
            SgUsuario codUsuarioOld = persistentSgauditado.getCodUsuario();
            SgUsuario codUsuarioNew = sgauditado.getCodUsuario();
            List<String> illegalOrphanMessages = null;
            if (sgproccAuditoriaNew != null && !sgproccAuditoriaNew.equals(sgproccAuditoriaOld)) {
                Sgauditado oldSgauditadoOfSgproccAuditoria = sgproccAuditoriaNew.getSgauditado();
                if (oldSgauditadoOfSgproccAuditoria != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The SgproccAuditoria " + sgproccAuditoriaNew + " already has an item of type Sgauditado whose sgproccAuditoria column cannot be null. Please make another selection for the sgproccAuditoria field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sgproccAuditoriaNew != null) {
                sgproccAuditoriaNew = em.getReference(sgproccAuditoriaNew.getClass(), sgproccAuditoriaNew.getSgproccAuditoriaPK());
                sgauditado.setSgproccAuditoria(sgproccAuditoriaNew);
            }
            if (codUsuarioNew != null) {
                codUsuarioNew = em.getReference(codUsuarioNew.getClass(), codUsuarioNew.getCodUsuario());
                sgauditado.setCodUsuario(codUsuarioNew);
            }
            sgauditado = em.merge(sgauditado);
            if (sgproccAuditoriaOld != null && !sgproccAuditoriaOld.equals(sgproccAuditoriaNew)) {
                sgproccAuditoriaOld.setSgauditado(null);
                sgproccAuditoriaOld = em.merge(sgproccAuditoriaOld);
            }
            if (sgproccAuditoriaNew != null && !sgproccAuditoriaNew.equals(sgproccAuditoriaOld)) {
                sgproccAuditoriaNew.setSgauditado(sgauditado);
                sgproccAuditoriaNew = em.merge(sgproccAuditoriaNew);
            }
            if (codUsuarioOld != null && !codUsuarioOld.equals(codUsuarioNew)) {
                codUsuarioOld.getSgauditadoList().remove(sgauditado);
                codUsuarioOld = em.merge(codUsuarioOld);
            }
            if (codUsuarioNew != null && !codUsuarioNew.equals(codUsuarioOld)) {
                codUsuarioNew.getSgauditadoList().add(sgauditado);
                codUsuarioNew = em.merge(codUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgauditadoPK id = sgauditado.getSgauditadoPK();
                if (findSgauditado(id) == null) {
                    throw new NonexistentEntityException("The sgauditado with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgauditadoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgauditado sgauditado;
            try {
                sgauditado = em.getReference(Sgauditado.class, id);
                sgauditado.getSgauditadoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgauditado with id " + id + " no longer exists.", enfe);
            }
            SgproccAuditoria sgproccAuditoria = sgauditado.getSgproccAuditoria();
            if (sgproccAuditoria != null) {
                sgproccAuditoria.setSgauditado(null);
                sgproccAuditoria = em.merge(sgproccAuditoria);
            }
            SgUsuario codUsuario = sgauditado.getCodUsuario();
            if (codUsuario != null) {
                codUsuario.getSgauditadoList().remove(sgauditado);
                codUsuario = em.merge(codUsuario);
            }
            em.remove(sgauditado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sgauditado> findSgauditadoEntities() {
        return findSgauditadoEntities(true, -1, -1);
    }

    public List<Sgauditado> findSgauditadoEntities(int maxResults, int firstResult) {
        return findSgauditadoEntities(false, maxResults, firstResult);
    }

    private List<Sgauditado> findSgauditadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sgauditado.class));
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

    public Sgauditado findSgauditado(SgauditadoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sgauditado.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgauditadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sgauditado> rt = cq.from(Sgauditado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
