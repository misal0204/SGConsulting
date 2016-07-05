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
import sg.sistemas.entidades.Sgnc;
import sg.sistemas.entidades.SgUsuario;
import sg.sistemas.entidades.Sgverifica;
import sg.sistemas.entidades.SgverificaPK;

/**
 *
 * @author Misael
 */
public class SgverificaJpaController implements Serializable {

    public SgverificaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sgverifica sgverifica) throws PreexistingEntityException, Exception {
        if (sgverifica.getSgverificaPK() == null) {
            sgverifica.setSgverificaPK(new SgverificaPK());
        }
        sgverifica.getSgverificaPK().setIdsociedad(sgverifica.getSgnc().getSgncPK().getIdsociedad());
        sgverifica.getSgverificaPK().setNonc(sgverifica.getSgnc().getSgncPK().getNonc());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgnc sgnc = sgverifica.getSgnc();
            if (sgnc != null) {
                sgnc = em.getReference(sgnc.getClass(), sgnc.getSgncPK());
                sgverifica.setSgnc(sgnc);
            }
            SgUsuario codUsuario = sgverifica.getCodUsuario();
            if (codUsuario != null) {
                codUsuario = em.getReference(codUsuario.getClass(), codUsuario.getCodUsuario());
                sgverifica.setCodUsuario(codUsuario);
            }
            em.persist(sgverifica);
            if (sgnc != null) {
                sgnc.getSgverificaList().add(sgverifica);
                sgnc = em.merge(sgnc);
            }
            if (codUsuario != null) {
                codUsuario.getSgverificaList().add(sgverifica);
                codUsuario = em.merge(codUsuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgverifica(sgverifica.getSgverificaPK()) != null) {
                throw new PreexistingEntityException("Sgverifica " + sgverifica + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sgverifica sgverifica) throws NonexistentEntityException, Exception {
        sgverifica.getSgverificaPK().setIdsociedad(sgverifica.getSgnc().getSgncPK().getIdsociedad());
        sgverifica.getSgverificaPK().setNonc(sgverifica.getSgnc().getSgncPK().getNonc());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgverifica persistentSgverifica = em.find(Sgverifica.class, sgverifica.getSgverificaPK());
            Sgnc sgncOld = persistentSgverifica.getSgnc();
            Sgnc sgncNew = sgverifica.getSgnc();
            SgUsuario codUsuarioOld = persistentSgverifica.getCodUsuario();
            SgUsuario codUsuarioNew = sgverifica.getCodUsuario();
            if (sgncNew != null) {
                sgncNew = em.getReference(sgncNew.getClass(), sgncNew.getSgncPK());
                sgverifica.setSgnc(sgncNew);
            }
            if (codUsuarioNew != null) {
                codUsuarioNew = em.getReference(codUsuarioNew.getClass(), codUsuarioNew.getCodUsuario());
                sgverifica.setCodUsuario(codUsuarioNew);
            }
            sgverifica = em.merge(sgverifica);
            if (sgncOld != null && !sgncOld.equals(sgncNew)) {
                sgncOld.getSgverificaList().remove(sgverifica);
                sgncOld = em.merge(sgncOld);
            }
            if (sgncNew != null && !sgncNew.equals(sgncOld)) {
                sgncNew.getSgverificaList().add(sgverifica);
                sgncNew = em.merge(sgncNew);
            }
            if (codUsuarioOld != null && !codUsuarioOld.equals(codUsuarioNew)) {
                codUsuarioOld.getSgverificaList().remove(sgverifica);
                codUsuarioOld = em.merge(codUsuarioOld);
            }
            if (codUsuarioNew != null && !codUsuarioNew.equals(codUsuarioOld)) {
                codUsuarioNew.getSgverificaList().add(sgverifica);
                codUsuarioNew = em.merge(codUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgverificaPK id = sgverifica.getSgverificaPK();
                if (findSgverifica(id) == null) {
                    throw new NonexistentEntityException("The sgverifica with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgverificaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgverifica sgverifica;
            try {
                sgverifica = em.getReference(Sgverifica.class, id);
                sgverifica.getSgverificaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgverifica with id " + id + " no longer exists.", enfe);
            }
            Sgnc sgnc = sgverifica.getSgnc();
            if (sgnc != null) {
                sgnc.getSgverificaList().remove(sgverifica);
                sgnc = em.merge(sgnc);
            }
            SgUsuario codUsuario = sgverifica.getCodUsuario();
            if (codUsuario != null) {
                codUsuario.getSgverificaList().remove(sgverifica);
                codUsuario = em.merge(codUsuario);
            }
            em.remove(sgverifica);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sgverifica> findSgverificaEntities() {
        return findSgverificaEntities(true, -1, -1);
    }

    public List<Sgverifica> findSgverificaEntities(int maxResults, int firstResult) {
        return findSgverificaEntities(false, maxResults, firstResult);
    }

    private List<Sgverifica> findSgverificaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sgverifica.class));
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

    public Sgverifica findSgverifica(SgverificaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sgverifica.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgverificaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sgverifica> rt = cq.from(Sgverifica.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
