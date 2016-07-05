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
import sg.sistemas.entidades.SgUsuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgcorrecionNc;
import sg.sistemas.entidades.SgcorrecionNcPK;

/**
 *
 * @author Misael
 */
public class SgcorrecionNcJpaController implements Serializable {

    public SgcorrecionNcJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgcorrecionNc sgcorrecionNc) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (sgcorrecionNc.getSgcorrecionNcPK() == null) {
            sgcorrecionNc.setSgcorrecionNcPK(new SgcorrecionNcPK());
        }
        sgcorrecionNc.getSgcorrecionNcPK().setNonc(sgcorrecionNc.getSgnc().getSgncPK().getNonc());
        sgcorrecionNc.getSgcorrecionNcPK().setIdsociedad(sgcorrecionNc.getSgnc().getSgncPK().getIdsociedad());
        List<String> illegalOrphanMessages = null;
        Sgnc sgncOrphanCheck = sgcorrecionNc.getSgnc();
        if (sgncOrphanCheck != null) {
            SgcorrecionNc oldSgcorrecionNcOfSgnc = sgncOrphanCheck.getSgcorrecionNc();
            if (oldSgcorrecionNcOfSgnc != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Sgnc " + sgncOrphanCheck + " already has an item of type SgcorrecionNc whose sgnc column cannot be null. Please make another selection for the sgnc field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgnc sgnc = sgcorrecionNc.getSgnc();
            if (sgnc != null) {
                sgnc = em.getReference(sgnc.getClass(), sgnc.getSgncPK());
                sgcorrecionNc.setSgnc(sgnc);
            }
            SgUsuario codUsuario = sgcorrecionNc.getCodUsuario();
            if (codUsuario != null) {
                codUsuario = em.getReference(codUsuario.getClass(), codUsuario.getCodUsuario());
                sgcorrecionNc.setCodUsuario(codUsuario);
            }
            em.persist(sgcorrecionNc);
            if (sgnc != null) {
                sgnc.setSgcorrecionNc(sgcorrecionNc);
                sgnc = em.merge(sgnc);
            }
            if (codUsuario != null) {
                codUsuario.getSgcorrecionNcList().add(sgcorrecionNc);
                codUsuario = em.merge(codUsuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgcorrecionNc(sgcorrecionNc.getSgcorrecionNcPK()) != null) {
                throw new PreexistingEntityException("SgcorrecionNc " + sgcorrecionNc + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgcorrecionNc sgcorrecionNc) throws IllegalOrphanException, NonexistentEntityException, Exception {
        sgcorrecionNc.getSgcorrecionNcPK().setNonc(sgcorrecionNc.getSgnc().getSgncPK().getNonc());
        sgcorrecionNc.getSgcorrecionNcPK().setIdsociedad(sgcorrecionNc.getSgnc().getSgncPK().getIdsociedad());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgcorrecionNc persistentSgcorrecionNc = em.find(SgcorrecionNc.class, sgcorrecionNc.getSgcorrecionNcPK());
            Sgnc sgncOld = persistentSgcorrecionNc.getSgnc();
            Sgnc sgncNew = sgcorrecionNc.getSgnc();
            SgUsuario codUsuarioOld = persistentSgcorrecionNc.getCodUsuario();
            SgUsuario codUsuarioNew = sgcorrecionNc.getCodUsuario();
            List<String> illegalOrphanMessages = null;
            if (sgncNew != null && !sgncNew.equals(sgncOld)) {
                SgcorrecionNc oldSgcorrecionNcOfSgnc = sgncNew.getSgcorrecionNc();
                if (oldSgcorrecionNcOfSgnc != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Sgnc " + sgncNew + " already has an item of type SgcorrecionNc whose sgnc column cannot be null. Please make another selection for the sgnc field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sgncNew != null) {
                sgncNew = em.getReference(sgncNew.getClass(), sgncNew.getSgncPK());
                sgcorrecionNc.setSgnc(sgncNew);
            }
            if (codUsuarioNew != null) {
                codUsuarioNew = em.getReference(codUsuarioNew.getClass(), codUsuarioNew.getCodUsuario());
                sgcorrecionNc.setCodUsuario(codUsuarioNew);
            }
            sgcorrecionNc = em.merge(sgcorrecionNc);
            if (sgncOld != null && !sgncOld.equals(sgncNew)) {
                sgncOld.setSgcorrecionNc(null);
                sgncOld = em.merge(sgncOld);
            }
            if (sgncNew != null && !sgncNew.equals(sgncOld)) {
                sgncNew.setSgcorrecionNc(sgcorrecionNc);
                sgncNew = em.merge(sgncNew);
            }
            if (codUsuarioOld != null && !codUsuarioOld.equals(codUsuarioNew)) {
                codUsuarioOld.getSgcorrecionNcList().remove(sgcorrecionNc);
                codUsuarioOld = em.merge(codUsuarioOld);
            }
            if (codUsuarioNew != null && !codUsuarioNew.equals(codUsuarioOld)) {
                codUsuarioNew.getSgcorrecionNcList().add(sgcorrecionNc);
                codUsuarioNew = em.merge(codUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgcorrecionNcPK id = sgcorrecionNc.getSgcorrecionNcPK();
                if (findSgcorrecionNc(id) == null) {
                    throw new NonexistentEntityException("The sgcorrecionNc with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgcorrecionNcPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgcorrecionNc sgcorrecionNc;
            try {
                sgcorrecionNc = em.getReference(SgcorrecionNc.class, id);
                sgcorrecionNc.getSgcorrecionNcPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgcorrecionNc with id " + id + " no longer exists.", enfe);
            }
            Sgnc sgnc = sgcorrecionNc.getSgnc();
            if (sgnc != null) {
                sgnc.setSgcorrecionNc(null);
                sgnc = em.merge(sgnc);
            }
            SgUsuario codUsuario = sgcorrecionNc.getCodUsuario();
            if (codUsuario != null) {
                codUsuario.getSgcorrecionNcList().remove(sgcorrecionNc);
                codUsuario = em.merge(codUsuario);
            }
            em.remove(sgcorrecionNc);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgcorrecionNc> findSgcorrecionNcEntities() {
        return findSgcorrecionNcEntities(true, -1, -1);
    }

    public List<SgcorrecionNc> findSgcorrecionNcEntities(int maxResults, int firstResult) {
        return findSgcorrecionNcEntities(false, maxResults, firstResult);
    }

    private List<SgcorrecionNc> findSgcorrecionNcEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgcorrecionNc.class));
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

    public SgcorrecionNc findSgcorrecionNc(SgcorrecionNcPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgcorrecionNc.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgcorrecionNcCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgcorrecionNc> rt = cq.from(SgcorrecionNc.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
