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
import sg.sistemas.entidades.SgrevisionDireccion;
import sg.sistemas.entidades.SgUsuario;
import sg.sistemas.entidades.SgplanProcesos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgdetalleRdireccion;
import sg.sistemas.entidades.SgdetalleRdireccionPK;

/**
 *
 * @author Misael
 */
public class SgdetalleRdireccionJpaController implements Serializable {

    public SgdetalleRdireccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgdetalleRdireccion sgdetalleRdireccion) throws PreexistingEntityException, Exception {
        if (sgdetalleRdireccion.getSgdetalleRdireccionPK() == null) {
            sgdetalleRdireccion.setSgdetalleRdireccionPK(new SgdetalleRdireccionPK());
        }
        if (sgdetalleRdireccion.getSgUsuarioList() == null) {
            sgdetalleRdireccion.setSgUsuarioList(new ArrayList<SgUsuario>());
        }
        sgdetalleRdireccion.getSgdetalleRdireccionPK().setIdtipoPdireccion(sgdetalleRdireccion.getSgrevisionDireccion().getSgrevisionDireccionPK().getIdtipoPdireccion());
        sgdetalleRdireccion.getSgdetalleRdireccionPK().setIdsociedad(sgdetalleRdireccion.getSgrevisionDireccion().getSgrevisionDireccionPK().getIdsociedad());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgrevisionDireccion sgrevisionDireccion = sgdetalleRdireccion.getSgrevisionDireccion();
            if (sgrevisionDireccion != null) {
                sgrevisionDireccion = em.getReference(sgrevisionDireccion.getClass(), sgrevisionDireccion.getSgrevisionDireccionPK());
                sgdetalleRdireccion.setSgrevisionDireccion(sgrevisionDireccion);
            }
            SgUsuario codUsuario = sgdetalleRdireccion.getCodUsuario();
            if (codUsuario != null) {
                codUsuario = em.getReference(codUsuario.getClass(), codUsuario.getCodUsuario());
                sgdetalleRdireccion.setCodUsuario(codUsuario);
            }
            SgplanProcesos sgplanProcesos = sgdetalleRdireccion.getSgplanProcesos();
            if (sgplanProcesos != null) {
                sgplanProcesos = em.getReference(sgplanProcesos.getClass(), sgplanProcesos.getSgplanProcesosPK());
                sgdetalleRdireccion.setSgplanProcesos(sgplanProcesos);
            }
            List<SgUsuario> attachedSgUsuarioList = new ArrayList<SgUsuario>();
            for (SgUsuario sgUsuarioListSgUsuarioToAttach : sgdetalleRdireccion.getSgUsuarioList()) {
                sgUsuarioListSgUsuarioToAttach = em.getReference(sgUsuarioListSgUsuarioToAttach.getClass(), sgUsuarioListSgUsuarioToAttach.getCodUsuario());
                attachedSgUsuarioList.add(sgUsuarioListSgUsuarioToAttach);
            }
            sgdetalleRdireccion.setSgUsuarioList(attachedSgUsuarioList);
            em.persist(sgdetalleRdireccion);
            if (sgrevisionDireccion != null) {
                sgrevisionDireccion.getSgdetalleRdireccionList().add(sgdetalleRdireccion);
                sgrevisionDireccion = em.merge(sgrevisionDireccion);
            }
            if (codUsuario != null) {
                codUsuario.getSgdetalleRdireccionList().add(sgdetalleRdireccion);
                codUsuario = em.merge(codUsuario);
            }
            if (sgplanProcesos != null) {
                SgdetalleRdireccion oldSgdetalleRdireccionOfSgplanProcesos = sgplanProcesos.getSgdetalleRdireccion();
                if (oldSgdetalleRdireccionOfSgplanProcesos != null) {
                    oldSgdetalleRdireccionOfSgplanProcesos.setSgplanProcesos(null);
                    oldSgdetalleRdireccionOfSgplanProcesos = em.merge(oldSgdetalleRdireccionOfSgplanProcesos);
                }
                sgplanProcesos.setSgdetalleRdireccion(sgdetalleRdireccion);
                sgplanProcesos = em.merge(sgplanProcesos);
            }
            for (SgUsuario sgUsuarioListSgUsuario : sgdetalleRdireccion.getSgUsuarioList()) {
                sgUsuarioListSgUsuario.getSgdetalleRdireccionList().add(sgdetalleRdireccion);
                sgUsuarioListSgUsuario = em.merge(sgUsuarioListSgUsuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgdetalleRdireccion(sgdetalleRdireccion.getSgdetalleRdireccionPK()) != null) {
                throw new PreexistingEntityException("SgdetalleRdireccion " + sgdetalleRdireccion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgdetalleRdireccion sgdetalleRdireccion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        sgdetalleRdireccion.getSgdetalleRdireccionPK().setIdtipoPdireccion(sgdetalleRdireccion.getSgrevisionDireccion().getSgrevisionDireccionPK().getIdtipoPdireccion());
        sgdetalleRdireccion.getSgdetalleRdireccionPK().setIdsociedad(sgdetalleRdireccion.getSgrevisionDireccion().getSgrevisionDireccionPK().getIdsociedad());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgdetalleRdireccion persistentSgdetalleRdireccion = em.find(SgdetalleRdireccion.class, sgdetalleRdireccion.getSgdetalleRdireccionPK());
            SgrevisionDireccion sgrevisionDireccionOld = persistentSgdetalleRdireccion.getSgrevisionDireccion();
            SgrevisionDireccion sgrevisionDireccionNew = sgdetalleRdireccion.getSgrevisionDireccion();
            SgUsuario codUsuarioOld = persistentSgdetalleRdireccion.getCodUsuario();
            SgUsuario codUsuarioNew = sgdetalleRdireccion.getCodUsuario();
            SgplanProcesos sgplanProcesosOld = persistentSgdetalleRdireccion.getSgplanProcesos();
            SgplanProcesos sgplanProcesosNew = sgdetalleRdireccion.getSgplanProcesos();
            List<SgUsuario> sgUsuarioListOld = persistentSgdetalleRdireccion.getSgUsuarioList();
            List<SgUsuario> sgUsuarioListNew = sgdetalleRdireccion.getSgUsuarioList();
            List<String> illegalOrphanMessages = null;
            if (sgplanProcesosOld != null && !sgplanProcesosOld.equals(sgplanProcesosNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain SgplanProcesos " + sgplanProcesosOld + " since its sgdetalleRdireccion field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sgrevisionDireccionNew != null) {
                sgrevisionDireccionNew = em.getReference(sgrevisionDireccionNew.getClass(), sgrevisionDireccionNew.getSgrevisionDireccionPK());
                sgdetalleRdireccion.setSgrevisionDireccion(sgrevisionDireccionNew);
            }
            if (codUsuarioNew != null) {
                codUsuarioNew = em.getReference(codUsuarioNew.getClass(), codUsuarioNew.getCodUsuario());
                sgdetalleRdireccion.setCodUsuario(codUsuarioNew);
            }
            if (sgplanProcesosNew != null) {
                sgplanProcesosNew = em.getReference(sgplanProcesosNew.getClass(), sgplanProcesosNew.getSgplanProcesosPK());
                sgdetalleRdireccion.setSgplanProcesos(sgplanProcesosNew);
            }
            List<SgUsuario> attachedSgUsuarioListNew = new ArrayList<SgUsuario>();
            for (SgUsuario sgUsuarioListNewSgUsuarioToAttach : sgUsuarioListNew) {
                sgUsuarioListNewSgUsuarioToAttach = em.getReference(sgUsuarioListNewSgUsuarioToAttach.getClass(), sgUsuarioListNewSgUsuarioToAttach.getCodUsuario());
                attachedSgUsuarioListNew.add(sgUsuarioListNewSgUsuarioToAttach);
            }
            sgUsuarioListNew = attachedSgUsuarioListNew;
            sgdetalleRdireccion.setSgUsuarioList(sgUsuarioListNew);
            sgdetalleRdireccion = em.merge(sgdetalleRdireccion);
            if (sgrevisionDireccionOld != null && !sgrevisionDireccionOld.equals(sgrevisionDireccionNew)) {
                sgrevisionDireccionOld.getSgdetalleRdireccionList().remove(sgdetalleRdireccion);
                sgrevisionDireccionOld = em.merge(sgrevisionDireccionOld);
            }
            if (sgrevisionDireccionNew != null && !sgrevisionDireccionNew.equals(sgrevisionDireccionOld)) {
                sgrevisionDireccionNew.getSgdetalleRdireccionList().add(sgdetalleRdireccion);
                sgrevisionDireccionNew = em.merge(sgrevisionDireccionNew);
            }
            if (codUsuarioOld != null && !codUsuarioOld.equals(codUsuarioNew)) {
                codUsuarioOld.getSgdetalleRdireccionList().remove(sgdetalleRdireccion);
                codUsuarioOld = em.merge(codUsuarioOld);
            }
            if (codUsuarioNew != null && !codUsuarioNew.equals(codUsuarioOld)) {
                codUsuarioNew.getSgdetalleRdireccionList().add(sgdetalleRdireccion);
                codUsuarioNew = em.merge(codUsuarioNew);
            }
            if (sgplanProcesosNew != null && !sgplanProcesosNew.equals(sgplanProcesosOld)) {
                SgdetalleRdireccion oldSgdetalleRdireccionOfSgplanProcesos = sgplanProcesosNew.getSgdetalleRdireccion();
                if (oldSgdetalleRdireccionOfSgplanProcesos != null) {
                    oldSgdetalleRdireccionOfSgplanProcesos.setSgplanProcesos(null);
                    oldSgdetalleRdireccionOfSgplanProcesos = em.merge(oldSgdetalleRdireccionOfSgplanProcesos);
                }
                sgplanProcesosNew.setSgdetalleRdireccion(sgdetalleRdireccion);
                sgplanProcesosNew = em.merge(sgplanProcesosNew);
            }
            for (SgUsuario sgUsuarioListOldSgUsuario : sgUsuarioListOld) {
                if (!sgUsuarioListNew.contains(sgUsuarioListOldSgUsuario)) {
                    sgUsuarioListOldSgUsuario.getSgdetalleRdireccionList().remove(sgdetalleRdireccion);
                    sgUsuarioListOldSgUsuario = em.merge(sgUsuarioListOldSgUsuario);
                }
            }
            for (SgUsuario sgUsuarioListNewSgUsuario : sgUsuarioListNew) {
                if (!sgUsuarioListOld.contains(sgUsuarioListNewSgUsuario)) {
                    sgUsuarioListNewSgUsuario.getSgdetalleRdireccionList().add(sgdetalleRdireccion);
                    sgUsuarioListNewSgUsuario = em.merge(sgUsuarioListNewSgUsuario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgdetalleRdireccionPK id = sgdetalleRdireccion.getSgdetalleRdireccionPK();
                if (findSgdetalleRdireccion(id) == null) {
                    throw new NonexistentEntityException("The sgdetalleRdireccion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgdetalleRdireccionPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgdetalleRdireccion sgdetalleRdireccion;
            try {
                sgdetalleRdireccion = em.getReference(SgdetalleRdireccion.class, id);
                sgdetalleRdireccion.getSgdetalleRdireccionPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgdetalleRdireccion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            SgplanProcesos sgplanProcesosOrphanCheck = sgdetalleRdireccion.getSgplanProcesos();
            if (sgplanProcesosOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SgdetalleRdireccion (" + sgdetalleRdireccion + ") cannot be destroyed since the SgplanProcesos " + sgplanProcesosOrphanCheck + " in its sgplanProcesos field has a non-nullable sgdetalleRdireccion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            SgrevisionDireccion sgrevisionDireccion = sgdetalleRdireccion.getSgrevisionDireccion();
            if (sgrevisionDireccion != null) {
                sgrevisionDireccion.getSgdetalleRdireccionList().remove(sgdetalleRdireccion);
                sgrevisionDireccion = em.merge(sgrevisionDireccion);
            }
            SgUsuario codUsuario = sgdetalleRdireccion.getCodUsuario();
            if (codUsuario != null) {
                codUsuario.getSgdetalleRdireccionList().remove(sgdetalleRdireccion);
                codUsuario = em.merge(codUsuario);
            }
            List<SgUsuario> sgUsuarioList = sgdetalleRdireccion.getSgUsuarioList();
            for (SgUsuario sgUsuarioListSgUsuario : sgUsuarioList) {
                sgUsuarioListSgUsuario.getSgdetalleRdireccionList().remove(sgdetalleRdireccion);
                sgUsuarioListSgUsuario = em.merge(sgUsuarioListSgUsuario);
            }
            em.remove(sgdetalleRdireccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgdetalleRdireccion> findSgdetalleRdireccionEntities() {
        return findSgdetalleRdireccionEntities(true, -1, -1);
    }

    public List<SgdetalleRdireccion> findSgdetalleRdireccionEntities(int maxResults, int firstResult) {
        return findSgdetalleRdireccionEntities(false, maxResults, firstResult);
    }

    private List<SgdetalleRdireccion> findSgdetalleRdireccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgdetalleRdireccion.class));
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

    public SgdetalleRdireccion findSgdetalleRdireccion(SgdetalleRdireccionPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgdetalleRdireccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgdetalleRdireccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgdetalleRdireccion> rt = cq.from(SgdetalleRdireccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
