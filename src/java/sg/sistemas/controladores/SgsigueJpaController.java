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
import sg.sistemas.entidades.SgseguimientoDetalle;
import sg.sistemas.entidades.Sgnc;
import sg.sistemas.entidades.SgUsuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.Sgsigue;
import sg.sistemas.entidades.SgsiguePK;

/**
 *
 * @author Misael
 */
public class SgsigueJpaController implements Serializable {

    public SgsigueJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sgsigue sgsigue) throws PreexistingEntityException, Exception {
        if (sgsigue.getSgsiguePK() == null) {
            sgsigue.setSgsiguePK(new SgsiguePK());
        }
        sgsigue.getSgsiguePK().setNonc(sgsigue.getSgnc().getSgncPK().getNonc());
        sgsigue.getSgsiguePK().setIdsociedad(sgsigue.getSgnc().getSgncPK().getIdsociedad());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgseguimientoDetalle sgseguimientoDetalle = sgsigue.getSgseguimientoDetalle();
            if (sgseguimientoDetalle != null) {
                sgseguimientoDetalle = em.getReference(sgseguimientoDetalle.getClass(), sgseguimientoDetalle.getSgseguimientoDetallePK());
                sgsigue.setSgseguimientoDetalle(sgseguimientoDetalle);
            }
            Sgnc sgnc = sgsigue.getSgnc();
            if (sgnc != null) {
                sgnc = em.getReference(sgnc.getClass(), sgnc.getSgncPK());
                sgsigue.setSgnc(sgnc);
            }
            SgUsuario codUsuario = sgsigue.getCodUsuario();
            if (codUsuario != null) {
                codUsuario = em.getReference(codUsuario.getClass(), codUsuario.getCodUsuario());
                sgsigue.setCodUsuario(codUsuario);
            }
            em.persist(sgsigue);
            if (sgseguimientoDetalle != null) {
                Sgsigue oldSgsigueOfSgseguimientoDetalle = sgseguimientoDetalle.getSgsigue();
                if (oldSgsigueOfSgseguimientoDetalle != null) {
                    oldSgsigueOfSgseguimientoDetalle.setSgseguimientoDetalle(null);
                    oldSgsigueOfSgseguimientoDetalle = em.merge(oldSgsigueOfSgseguimientoDetalle);
                }
                sgseguimientoDetalle.setSgsigue(sgsigue);
                sgseguimientoDetalle = em.merge(sgseguimientoDetalle);
            }
            if (sgnc != null) {
                sgnc.getSgsigueList().add(sgsigue);
                sgnc = em.merge(sgnc);
            }
            if (codUsuario != null) {
                codUsuario.getSgsigueList().add(sgsigue);
                codUsuario = em.merge(codUsuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgsigue(sgsigue.getSgsiguePK()) != null) {
                throw new PreexistingEntityException("Sgsigue " + sgsigue + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sgsigue sgsigue) throws IllegalOrphanException, NonexistentEntityException, Exception {
        sgsigue.getSgsiguePK().setNonc(sgsigue.getSgnc().getSgncPK().getNonc());
        sgsigue.getSgsiguePK().setIdsociedad(sgsigue.getSgnc().getSgncPK().getIdsociedad());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgsigue persistentSgsigue = em.find(Sgsigue.class, sgsigue.getSgsiguePK());
            SgseguimientoDetalle sgseguimientoDetalleOld = persistentSgsigue.getSgseguimientoDetalle();
            SgseguimientoDetalle sgseguimientoDetalleNew = sgsigue.getSgseguimientoDetalle();
            Sgnc sgncOld = persistentSgsigue.getSgnc();
            Sgnc sgncNew = sgsigue.getSgnc();
            SgUsuario codUsuarioOld = persistentSgsigue.getCodUsuario();
            SgUsuario codUsuarioNew = sgsigue.getCodUsuario();
            List<String> illegalOrphanMessages = null;
            if (sgseguimientoDetalleOld != null && !sgseguimientoDetalleOld.equals(sgseguimientoDetalleNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain SgseguimientoDetalle " + sgseguimientoDetalleOld + " since its sgsigue field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sgseguimientoDetalleNew != null) {
                sgseguimientoDetalleNew = em.getReference(sgseguimientoDetalleNew.getClass(), sgseguimientoDetalleNew.getSgseguimientoDetallePK());
                sgsigue.setSgseguimientoDetalle(sgseguimientoDetalleNew);
            }
            if (sgncNew != null) {
                sgncNew = em.getReference(sgncNew.getClass(), sgncNew.getSgncPK());
                sgsigue.setSgnc(sgncNew);
            }
            if (codUsuarioNew != null) {
                codUsuarioNew = em.getReference(codUsuarioNew.getClass(), codUsuarioNew.getCodUsuario());
                sgsigue.setCodUsuario(codUsuarioNew);
            }
            sgsigue = em.merge(sgsigue);
            if (sgseguimientoDetalleNew != null && !sgseguimientoDetalleNew.equals(sgseguimientoDetalleOld)) {
                Sgsigue oldSgsigueOfSgseguimientoDetalle = sgseguimientoDetalleNew.getSgsigue();
                if (oldSgsigueOfSgseguimientoDetalle != null) {
                    oldSgsigueOfSgseguimientoDetalle.setSgseguimientoDetalle(null);
                    oldSgsigueOfSgseguimientoDetalle = em.merge(oldSgsigueOfSgseguimientoDetalle);
                }
                sgseguimientoDetalleNew.setSgsigue(sgsigue);
                sgseguimientoDetalleNew = em.merge(sgseguimientoDetalleNew);
            }
            if (sgncOld != null && !sgncOld.equals(sgncNew)) {
                sgncOld.getSgsigueList().remove(sgsigue);
                sgncOld = em.merge(sgncOld);
            }
            if (sgncNew != null && !sgncNew.equals(sgncOld)) {
                sgncNew.getSgsigueList().add(sgsigue);
                sgncNew = em.merge(sgncNew);
            }
            if (codUsuarioOld != null && !codUsuarioOld.equals(codUsuarioNew)) {
                codUsuarioOld.getSgsigueList().remove(sgsigue);
                codUsuarioOld = em.merge(codUsuarioOld);
            }
            if (codUsuarioNew != null && !codUsuarioNew.equals(codUsuarioOld)) {
                codUsuarioNew.getSgsigueList().add(sgsigue);
                codUsuarioNew = em.merge(codUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgsiguePK id = sgsigue.getSgsiguePK();
                if (findSgsigue(id) == null) {
                    throw new NonexistentEntityException("The sgsigue with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgsiguePK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgsigue sgsigue;
            try {
                sgsigue = em.getReference(Sgsigue.class, id);
                sgsigue.getSgsiguePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgsigue with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            SgseguimientoDetalle sgseguimientoDetalleOrphanCheck = sgsigue.getSgseguimientoDetalle();
            if (sgseguimientoDetalleOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sgsigue (" + sgsigue + ") cannot be destroyed since the SgseguimientoDetalle " + sgseguimientoDetalleOrphanCheck + " in its sgseguimientoDetalle field has a non-nullable sgsigue field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Sgnc sgnc = sgsigue.getSgnc();
            if (sgnc != null) {
                sgnc.getSgsigueList().remove(sgsigue);
                sgnc = em.merge(sgnc);
            }
            SgUsuario codUsuario = sgsigue.getCodUsuario();
            if (codUsuario != null) {
                codUsuario.getSgsigueList().remove(sgsigue);
                codUsuario = em.merge(codUsuario);
            }
            em.remove(sgsigue);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sgsigue> findSgsigueEntities() {
        return findSgsigueEntities(true, -1, -1);
    }

    public List<Sgsigue> findSgsigueEntities(int maxResults, int firstResult) {
        return findSgsigueEntities(false, maxResults, firstResult);
    }

    private List<Sgsigue> findSgsigueEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sgsigue.class));
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

    public Sgsigue findSgsigue(SgsiguePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sgsigue.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgsigueCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sgsigue> rt = cq.from(Sgsigue.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
