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
import sg.sistemas.entidades.SgUsuario;
import sg.sistemas.entidades.Sgsigue;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgseguimientoDetalle;
import sg.sistemas.entidades.SgseguimientoDetallePK;

/**
 *
 * @author Misael
 */
public class SgseguimientoDetalleJpaController implements Serializable {

    public SgseguimientoDetalleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgseguimientoDetalle sgseguimientoDetalle) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (sgseguimientoDetalle.getSgseguimientoDetallePK() == null) {
            sgseguimientoDetalle.setSgseguimientoDetallePK(new SgseguimientoDetallePK());
        }
        sgseguimientoDetalle.getSgseguimientoDetallePK().setIdsociedad(sgseguimientoDetalle.getSgsigue().getSgsiguePK().getIdsociedad());
        sgseguimientoDetalle.getSgseguimientoDetallePK().setNonc(sgseguimientoDetalle.getSgsigue().getSgsiguePK().getNonc());
        sgseguimientoDetalle.getSgseguimientoDetallePK().setFecha(sgseguimientoDetalle.getSgsigue().getSgsiguePK().getFecha());
        List<String> illegalOrphanMessages = null;
        Sgsigue sgsigueOrphanCheck = sgseguimientoDetalle.getSgsigue();
        if (sgsigueOrphanCheck != null) {
            SgseguimientoDetalle oldSgseguimientoDetalleOfSgsigue = sgsigueOrphanCheck.getSgseguimientoDetalle();
            if (oldSgseguimientoDetalleOfSgsigue != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Sgsigue " + sgsigueOrphanCheck + " already has an item of type SgseguimientoDetalle whose sgsigue column cannot be null. Please make another selection for the sgsigue field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgUsuario codUsuario = sgseguimientoDetalle.getCodUsuario();
            if (codUsuario != null) {
                codUsuario = em.getReference(codUsuario.getClass(), codUsuario.getCodUsuario());
                sgseguimientoDetalle.setCodUsuario(codUsuario);
            }
            Sgsigue sgsigue = sgseguimientoDetalle.getSgsigue();
            if (sgsigue != null) {
                sgsigue = em.getReference(sgsigue.getClass(), sgsigue.getSgsiguePK());
                sgseguimientoDetalle.setSgsigue(sgsigue);
            }
            em.persist(sgseguimientoDetalle);
            if (codUsuario != null) {
                codUsuario.getSgseguimientoDetalleList().add(sgseguimientoDetalle);
                codUsuario = em.merge(codUsuario);
            }
            if (sgsigue != null) {
                sgsigue.setSgseguimientoDetalle(sgseguimientoDetalle);
                sgsigue = em.merge(sgsigue);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgseguimientoDetalle(sgseguimientoDetalle.getSgseguimientoDetallePK()) != null) {
                throw new PreexistingEntityException("SgseguimientoDetalle " + sgseguimientoDetalle + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgseguimientoDetalle sgseguimientoDetalle) throws IllegalOrphanException, NonexistentEntityException, Exception {
        sgseguimientoDetalle.getSgseguimientoDetallePK().setIdsociedad(sgseguimientoDetalle.getSgsigue().getSgsiguePK().getIdsociedad());
        sgseguimientoDetalle.getSgseguimientoDetallePK().setNonc(sgseguimientoDetalle.getSgsigue().getSgsiguePK().getNonc());
        sgseguimientoDetalle.getSgseguimientoDetallePK().setFecha(sgseguimientoDetalle.getSgsigue().getSgsiguePK().getFecha());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgseguimientoDetalle persistentSgseguimientoDetalle = em.find(SgseguimientoDetalle.class, sgseguimientoDetalle.getSgseguimientoDetallePK());
            SgUsuario codUsuarioOld = persistentSgseguimientoDetalle.getCodUsuario();
            SgUsuario codUsuarioNew = sgseguimientoDetalle.getCodUsuario();
            Sgsigue sgsigueOld = persistentSgseguimientoDetalle.getSgsigue();
            Sgsigue sgsigueNew = sgseguimientoDetalle.getSgsigue();
            List<String> illegalOrphanMessages = null;
            if (sgsigueNew != null && !sgsigueNew.equals(sgsigueOld)) {
                SgseguimientoDetalle oldSgseguimientoDetalleOfSgsigue = sgsigueNew.getSgseguimientoDetalle();
                if (oldSgseguimientoDetalleOfSgsigue != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Sgsigue " + sgsigueNew + " already has an item of type SgseguimientoDetalle whose sgsigue column cannot be null. Please make another selection for the sgsigue field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codUsuarioNew != null) {
                codUsuarioNew = em.getReference(codUsuarioNew.getClass(), codUsuarioNew.getCodUsuario());
                sgseguimientoDetalle.setCodUsuario(codUsuarioNew);
            }
            if (sgsigueNew != null) {
                sgsigueNew = em.getReference(sgsigueNew.getClass(), sgsigueNew.getSgsiguePK());
                sgseguimientoDetalle.setSgsigue(sgsigueNew);
            }
            sgseguimientoDetalle = em.merge(sgseguimientoDetalle);
            if (codUsuarioOld != null && !codUsuarioOld.equals(codUsuarioNew)) {
                codUsuarioOld.getSgseguimientoDetalleList().remove(sgseguimientoDetalle);
                codUsuarioOld = em.merge(codUsuarioOld);
            }
            if (codUsuarioNew != null && !codUsuarioNew.equals(codUsuarioOld)) {
                codUsuarioNew.getSgseguimientoDetalleList().add(sgseguimientoDetalle);
                codUsuarioNew = em.merge(codUsuarioNew);
            }
            if (sgsigueOld != null && !sgsigueOld.equals(sgsigueNew)) {
                sgsigueOld.setSgseguimientoDetalle(null);
                sgsigueOld = em.merge(sgsigueOld);
            }
            if (sgsigueNew != null && !sgsigueNew.equals(sgsigueOld)) {
                sgsigueNew.setSgseguimientoDetalle(sgseguimientoDetalle);
                sgsigueNew = em.merge(sgsigueNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgseguimientoDetallePK id = sgseguimientoDetalle.getSgseguimientoDetallePK();
                if (findSgseguimientoDetalle(id) == null) {
                    throw new NonexistentEntityException("The sgseguimientoDetalle with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgseguimientoDetallePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgseguimientoDetalle sgseguimientoDetalle;
            try {
                sgseguimientoDetalle = em.getReference(SgseguimientoDetalle.class, id);
                sgseguimientoDetalle.getSgseguimientoDetallePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgseguimientoDetalle with id " + id + " no longer exists.", enfe);
            }
            SgUsuario codUsuario = sgseguimientoDetalle.getCodUsuario();
            if (codUsuario != null) {
                codUsuario.getSgseguimientoDetalleList().remove(sgseguimientoDetalle);
                codUsuario = em.merge(codUsuario);
            }
            Sgsigue sgsigue = sgseguimientoDetalle.getSgsigue();
            if (sgsigue != null) {
                sgsigue.setSgseguimientoDetalle(null);
                sgsigue = em.merge(sgsigue);
            }
            em.remove(sgseguimientoDetalle);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgseguimientoDetalle> findSgseguimientoDetalleEntities() {
        return findSgseguimientoDetalleEntities(true, -1, -1);
    }

    public List<SgseguimientoDetalle> findSgseguimientoDetalleEntities(int maxResults, int firstResult) {
        return findSgseguimientoDetalleEntities(false, maxResults, firstResult);
    }

    private List<SgseguimientoDetalle> findSgseguimientoDetalleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgseguimientoDetalle.class));
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

    public SgseguimientoDetalle findSgseguimientoDetalle(SgseguimientoDetallePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgseguimientoDetalle.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgseguimientoDetalleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgseguimientoDetalle> rt = cq.from(SgseguimientoDetalle.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
