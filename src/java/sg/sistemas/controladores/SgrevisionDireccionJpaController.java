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
import sg.sistemas.entidades.SgtipoplanDireccion;
import sg.sistemas.entidades.SgUsuario;
import sg.sistemas.entidades.SgdetalleRdireccion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgrevisionDireccion;
import sg.sistemas.entidades.SgrevisionDireccionPK;

/**
 *
 * @author Misael
 */
public class SgrevisionDireccionJpaController implements Serializable {

    public SgrevisionDireccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgrevisionDireccion sgrevisionDireccion) throws PreexistingEntityException, Exception {
        if (sgrevisionDireccion.getSgrevisionDireccionPK() == null) {
            sgrevisionDireccion.setSgrevisionDireccionPK(new SgrevisionDireccionPK());
        }
        if (sgrevisionDireccion.getSgdetalleRdireccionList() == null) {
            sgrevisionDireccion.setSgdetalleRdireccionList(new ArrayList<SgdetalleRdireccion>());
        }
        sgrevisionDireccion.getSgrevisionDireccionPK().setIdtipoPdireccion(sgrevisionDireccion.getSgtipoplanDireccion().getIdtipoPdireccion());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgtipoplanDireccion sgtipoplanDireccion = sgrevisionDireccion.getSgtipoplanDireccion();
            if (sgtipoplanDireccion != null) {
                sgtipoplanDireccion = em.getReference(sgtipoplanDireccion.getClass(), sgtipoplanDireccion.getIdtipoPdireccion());
                sgrevisionDireccion.setSgtipoplanDireccion(sgtipoplanDireccion);
            }
            SgUsuario codUsuario = sgrevisionDireccion.getCodUsuario();
            if (codUsuario != null) {
                codUsuario = em.getReference(codUsuario.getClass(), codUsuario.getCodUsuario());
                sgrevisionDireccion.setCodUsuario(codUsuario);
            }
            List<SgdetalleRdireccion> attachedSgdetalleRdireccionList = new ArrayList<SgdetalleRdireccion>();
            for (SgdetalleRdireccion sgdetalleRdireccionListSgdetalleRdireccionToAttach : sgrevisionDireccion.getSgdetalleRdireccionList()) {
                sgdetalleRdireccionListSgdetalleRdireccionToAttach = em.getReference(sgdetalleRdireccionListSgdetalleRdireccionToAttach.getClass(), sgdetalleRdireccionListSgdetalleRdireccionToAttach.getSgdetalleRdireccionPK());
                attachedSgdetalleRdireccionList.add(sgdetalleRdireccionListSgdetalleRdireccionToAttach);
            }
            sgrevisionDireccion.setSgdetalleRdireccionList(attachedSgdetalleRdireccionList);
            em.persist(sgrevisionDireccion);
            if (sgtipoplanDireccion != null) {
                sgtipoplanDireccion.getSgrevisionDireccionList().add(sgrevisionDireccion);
                sgtipoplanDireccion = em.merge(sgtipoplanDireccion);
            }
            if (codUsuario != null) {
                codUsuario.getSgrevisionDireccionList().add(sgrevisionDireccion);
                codUsuario = em.merge(codUsuario);
            }
            for (SgdetalleRdireccion sgdetalleRdireccionListSgdetalleRdireccion : sgrevisionDireccion.getSgdetalleRdireccionList()) {
                SgrevisionDireccion oldSgrevisionDireccionOfSgdetalleRdireccionListSgdetalleRdireccion = sgdetalleRdireccionListSgdetalleRdireccion.getSgrevisionDireccion();
                sgdetalleRdireccionListSgdetalleRdireccion.setSgrevisionDireccion(sgrevisionDireccion);
                sgdetalleRdireccionListSgdetalleRdireccion = em.merge(sgdetalleRdireccionListSgdetalleRdireccion);
                if (oldSgrevisionDireccionOfSgdetalleRdireccionListSgdetalleRdireccion != null) {
                    oldSgrevisionDireccionOfSgdetalleRdireccionListSgdetalleRdireccion.getSgdetalleRdireccionList().remove(sgdetalleRdireccionListSgdetalleRdireccion);
                    oldSgrevisionDireccionOfSgdetalleRdireccionListSgdetalleRdireccion = em.merge(oldSgrevisionDireccionOfSgdetalleRdireccionListSgdetalleRdireccion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgrevisionDireccion(sgrevisionDireccion.getSgrevisionDireccionPK()) != null) {
                throw new PreexistingEntityException("SgrevisionDireccion " + sgrevisionDireccion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgrevisionDireccion sgrevisionDireccion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        sgrevisionDireccion.getSgrevisionDireccionPK().setIdtipoPdireccion(sgrevisionDireccion.getSgtipoplanDireccion().getIdtipoPdireccion());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgrevisionDireccion persistentSgrevisionDireccion = em.find(SgrevisionDireccion.class, sgrevisionDireccion.getSgrevisionDireccionPK());
            SgtipoplanDireccion sgtipoplanDireccionOld = persistentSgrevisionDireccion.getSgtipoplanDireccion();
            SgtipoplanDireccion sgtipoplanDireccionNew = sgrevisionDireccion.getSgtipoplanDireccion();
            SgUsuario codUsuarioOld = persistentSgrevisionDireccion.getCodUsuario();
            SgUsuario codUsuarioNew = sgrevisionDireccion.getCodUsuario();
            List<SgdetalleRdireccion> sgdetalleRdireccionListOld = persistentSgrevisionDireccion.getSgdetalleRdireccionList();
            List<SgdetalleRdireccion> sgdetalleRdireccionListNew = sgrevisionDireccion.getSgdetalleRdireccionList();
            List<String> illegalOrphanMessages = null;
            for (SgdetalleRdireccion sgdetalleRdireccionListOldSgdetalleRdireccion : sgdetalleRdireccionListOld) {
                if (!sgdetalleRdireccionListNew.contains(sgdetalleRdireccionListOldSgdetalleRdireccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SgdetalleRdireccion " + sgdetalleRdireccionListOldSgdetalleRdireccion + " since its sgrevisionDireccion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sgtipoplanDireccionNew != null) {
                sgtipoplanDireccionNew = em.getReference(sgtipoplanDireccionNew.getClass(), sgtipoplanDireccionNew.getIdtipoPdireccion());
                sgrevisionDireccion.setSgtipoplanDireccion(sgtipoplanDireccionNew);
            }
            if (codUsuarioNew != null) {
                codUsuarioNew = em.getReference(codUsuarioNew.getClass(), codUsuarioNew.getCodUsuario());
                sgrevisionDireccion.setCodUsuario(codUsuarioNew);
            }
            List<SgdetalleRdireccion> attachedSgdetalleRdireccionListNew = new ArrayList<SgdetalleRdireccion>();
            for (SgdetalleRdireccion sgdetalleRdireccionListNewSgdetalleRdireccionToAttach : sgdetalleRdireccionListNew) {
                sgdetalleRdireccionListNewSgdetalleRdireccionToAttach = em.getReference(sgdetalleRdireccionListNewSgdetalleRdireccionToAttach.getClass(), sgdetalleRdireccionListNewSgdetalleRdireccionToAttach.getSgdetalleRdireccionPK());
                attachedSgdetalleRdireccionListNew.add(sgdetalleRdireccionListNewSgdetalleRdireccionToAttach);
            }
            sgdetalleRdireccionListNew = attachedSgdetalleRdireccionListNew;
            sgrevisionDireccion.setSgdetalleRdireccionList(sgdetalleRdireccionListNew);
            sgrevisionDireccion = em.merge(sgrevisionDireccion);
            if (sgtipoplanDireccionOld != null && !sgtipoplanDireccionOld.equals(sgtipoplanDireccionNew)) {
                sgtipoplanDireccionOld.getSgrevisionDireccionList().remove(sgrevisionDireccion);
                sgtipoplanDireccionOld = em.merge(sgtipoplanDireccionOld);
            }
            if (sgtipoplanDireccionNew != null && !sgtipoplanDireccionNew.equals(sgtipoplanDireccionOld)) {
                sgtipoplanDireccionNew.getSgrevisionDireccionList().add(sgrevisionDireccion);
                sgtipoplanDireccionNew = em.merge(sgtipoplanDireccionNew);
            }
            if (codUsuarioOld != null && !codUsuarioOld.equals(codUsuarioNew)) {
                codUsuarioOld.getSgrevisionDireccionList().remove(sgrevisionDireccion);
                codUsuarioOld = em.merge(codUsuarioOld);
            }
            if (codUsuarioNew != null && !codUsuarioNew.equals(codUsuarioOld)) {
                codUsuarioNew.getSgrevisionDireccionList().add(sgrevisionDireccion);
                codUsuarioNew = em.merge(codUsuarioNew);
            }
            for (SgdetalleRdireccion sgdetalleRdireccionListNewSgdetalleRdireccion : sgdetalleRdireccionListNew) {
                if (!sgdetalleRdireccionListOld.contains(sgdetalleRdireccionListNewSgdetalleRdireccion)) {
                    SgrevisionDireccion oldSgrevisionDireccionOfSgdetalleRdireccionListNewSgdetalleRdireccion = sgdetalleRdireccionListNewSgdetalleRdireccion.getSgrevisionDireccion();
                    sgdetalleRdireccionListNewSgdetalleRdireccion.setSgrevisionDireccion(sgrevisionDireccion);
                    sgdetalleRdireccionListNewSgdetalleRdireccion = em.merge(sgdetalleRdireccionListNewSgdetalleRdireccion);
                    if (oldSgrevisionDireccionOfSgdetalleRdireccionListNewSgdetalleRdireccion != null && !oldSgrevisionDireccionOfSgdetalleRdireccionListNewSgdetalleRdireccion.equals(sgrevisionDireccion)) {
                        oldSgrevisionDireccionOfSgdetalleRdireccionListNewSgdetalleRdireccion.getSgdetalleRdireccionList().remove(sgdetalleRdireccionListNewSgdetalleRdireccion);
                        oldSgrevisionDireccionOfSgdetalleRdireccionListNewSgdetalleRdireccion = em.merge(oldSgrevisionDireccionOfSgdetalleRdireccionListNewSgdetalleRdireccion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgrevisionDireccionPK id = sgrevisionDireccion.getSgrevisionDireccionPK();
                if (findSgrevisionDireccion(id) == null) {
                    throw new NonexistentEntityException("The sgrevisionDireccion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgrevisionDireccionPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgrevisionDireccion sgrevisionDireccion;
            try {
                sgrevisionDireccion = em.getReference(SgrevisionDireccion.class, id);
                sgrevisionDireccion.getSgrevisionDireccionPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgrevisionDireccion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SgdetalleRdireccion> sgdetalleRdireccionListOrphanCheck = sgrevisionDireccion.getSgdetalleRdireccionList();
            for (SgdetalleRdireccion sgdetalleRdireccionListOrphanCheckSgdetalleRdireccion : sgdetalleRdireccionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SgrevisionDireccion (" + sgrevisionDireccion + ") cannot be destroyed since the SgdetalleRdireccion " + sgdetalleRdireccionListOrphanCheckSgdetalleRdireccion + " in its sgdetalleRdireccionList field has a non-nullable sgrevisionDireccion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            SgtipoplanDireccion sgtipoplanDireccion = sgrevisionDireccion.getSgtipoplanDireccion();
            if (sgtipoplanDireccion != null) {
                sgtipoplanDireccion.getSgrevisionDireccionList().remove(sgrevisionDireccion);
                sgtipoplanDireccion = em.merge(sgtipoplanDireccion);
            }
            SgUsuario codUsuario = sgrevisionDireccion.getCodUsuario();
            if (codUsuario != null) {
                codUsuario.getSgrevisionDireccionList().remove(sgrevisionDireccion);
                codUsuario = em.merge(codUsuario);
            }
            em.remove(sgrevisionDireccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgrevisionDireccion> findSgrevisionDireccionEntities() {
        return findSgrevisionDireccionEntities(true, -1, -1);
    }

    public List<SgrevisionDireccion> findSgrevisionDireccionEntities(int maxResults, int firstResult) {
        return findSgrevisionDireccionEntities(false, maxResults, firstResult);
    }

    private List<SgrevisionDireccion> findSgrevisionDireccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgrevisionDireccion.class));
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

    public SgrevisionDireccion findSgrevisionDireccion(SgrevisionDireccionPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgrevisionDireccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgrevisionDireccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgrevisionDireccion> rt = cq.from(SgrevisionDireccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
