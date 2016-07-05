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
import sg.sistemas.entidades.SgencuestaDetalle;
import sg.sistemas.entidades.SgpCabEncuesta;
import sg.sistemas.entidades.SgUsuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgcabEncuesta;
import sg.sistemas.entidades.SgcabEncuestaPK;

/**
 *
 * @author Misael
 */
public class SgcabEncuestaJpaController implements Serializable {

    public SgcabEncuestaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgcabEncuesta sgcabEncuesta) throws PreexistingEntityException, Exception {
        if (sgcabEncuesta.getSgcabEncuestaPK() == null) {
            sgcabEncuesta.setSgcabEncuestaPK(new SgcabEncuestaPK());
        }
        if (sgcabEncuesta.getSgUsuarioList() == null) {
            sgcabEncuesta.setSgUsuarioList(new ArrayList<SgUsuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgencuestaDetalle sgencuestaDetalle = sgcabEncuesta.getSgencuestaDetalle();
            if (sgencuestaDetalle != null) {
                sgencuestaDetalle = em.getReference(sgencuestaDetalle.getClass(), sgencuestaDetalle.getSgencuestaDetallePK());
                sgcabEncuesta.setSgencuestaDetalle(sgencuestaDetalle);
            }
            SgpCabEncuesta idencuesta = sgcabEncuesta.getIdencuesta();
            if (idencuesta != null) {
                idencuesta = em.getReference(idencuesta.getClass(), idencuesta.getIdencuesta());
                sgcabEncuesta.setIdencuesta(idencuesta);
            }
            SgUsuario codUsuario = sgcabEncuesta.getCodUsuario();
            if (codUsuario != null) {
                codUsuario = em.getReference(codUsuario.getClass(), codUsuario.getCodUsuario());
                sgcabEncuesta.setCodUsuario(codUsuario);
            }
            List<SgUsuario> attachedSgUsuarioList = new ArrayList<SgUsuario>();
            for (SgUsuario sgUsuarioListSgUsuarioToAttach : sgcabEncuesta.getSgUsuarioList()) {
                sgUsuarioListSgUsuarioToAttach = em.getReference(sgUsuarioListSgUsuarioToAttach.getClass(), sgUsuarioListSgUsuarioToAttach.getCodUsuario());
                attachedSgUsuarioList.add(sgUsuarioListSgUsuarioToAttach);
            }
            sgcabEncuesta.setSgUsuarioList(attachedSgUsuarioList);
            em.persist(sgcabEncuesta);
            if (sgencuestaDetalle != null) {
                SgcabEncuesta oldSgcabEncuestaOfSgencuestaDetalle = sgencuestaDetalle.getSgcabEncuesta();
                if (oldSgcabEncuestaOfSgencuestaDetalle != null) {
                    oldSgcabEncuestaOfSgencuestaDetalle.setSgencuestaDetalle(null);
                    oldSgcabEncuestaOfSgencuestaDetalle = em.merge(oldSgcabEncuestaOfSgencuestaDetalle);
                }
                sgencuestaDetalle.setSgcabEncuesta(sgcabEncuesta);
                sgencuestaDetalle = em.merge(sgencuestaDetalle);
            }
            if (idencuesta != null) {
                idencuesta.getSgcabEncuestaList().add(sgcabEncuesta);
                idencuesta = em.merge(idencuesta);
            }
            if (codUsuario != null) {
                codUsuario.getSgcabEncuestaList().add(sgcabEncuesta);
                codUsuario = em.merge(codUsuario);
            }
            for (SgUsuario sgUsuarioListSgUsuario : sgcabEncuesta.getSgUsuarioList()) {
                sgUsuarioListSgUsuario.getSgcabEncuestaList().add(sgcabEncuesta);
                sgUsuarioListSgUsuario = em.merge(sgUsuarioListSgUsuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgcabEncuesta(sgcabEncuesta.getSgcabEncuestaPK()) != null) {
                throw new PreexistingEntityException("SgcabEncuesta " + sgcabEncuesta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgcabEncuesta sgcabEncuesta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgcabEncuesta persistentSgcabEncuesta = em.find(SgcabEncuesta.class, sgcabEncuesta.getSgcabEncuestaPK());
            SgencuestaDetalle sgencuestaDetalleOld = persistentSgcabEncuesta.getSgencuestaDetalle();
            SgencuestaDetalle sgencuestaDetalleNew = sgcabEncuesta.getSgencuestaDetalle();
            SgpCabEncuesta idencuestaOld = persistentSgcabEncuesta.getIdencuesta();
            SgpCabEncuesta idencuestaNew = sgcabEncuesta.getIdencuesta();
            SgUsuario codUsuarioOld = persistentSgcabEncuesta.getCodUsuario();
            SgUsuario codUsuarioNew = sgcabEncuesta.getCodUsuario();
            List<SgUsuario> sgUsuarioListOld = persistentSgcabEncuesta.getSgUsuarioList();
            List<SgUsuario> sgUsuarioListNew = sgcabEncuesta.getSgUsuarioList();
            List<String> illegalOrphanMessages = null;
            if (sgencuestaDetalleOld != null && !sgencuestaDetalleOld.equals(sgencuestaDetalleNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain SgencuestaDetalle " + sgencuestaDetalleOld + " since its sgcabEncuesta field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sgencuestaDetalleNew != null) {
                sgencuestaDetalleNew = em.getReference(sgencuestaDetalleNew.getClass(), sgencuestaDetalleNew.getSgencuestaDetallePK());
                sgcabEncuesta.setSgencuestaDetalle(sgencuestaDetalleNew);
            }
            if (idencuestaNew != null) {
                idencuestaNew = em.getReference(idencuestaNew.getClass(), idencuestaNew.getIdencuesta());
                sgcabEncuesta.setIdencuesta(idencuestaNew);
            }
            if (codUsuarioNew != null) {
                codUsuarioNew = em.getReference(codUsuarioNew.getClass(), codUsuarioNew.getCodUsuario());
                sgcabEncuesta.setCodUsuario(codUsuarioNew);
            }
            List<SgUsuario> attachedSgUsuarioListNew = new ArrayList<SgUsuario>();
            for (SgUsuario sgUsuarioListNewSgUsuarioToAttach : sgUsuarioListNew) {
                sgUsuarioListNewSgUsuarioToAttach = em.getReference(sgUsuarioListNewSgUsuarioToAttach.getClass(), sgUsuarioListNewSgUsuarioToAttach.getCodUsuario());
                attachedSgUsuarioListNew.add(sgUsuarioListNewSgUsuarioToAttach);
            }
            sgUsuarioListNew = attachedSgUsuarioListNew;
            sgcabEncuesta.setSgUsuarioList(sgUsuarioListNew);
            sgcabEncuesta = em.merge(sgcabEncuesta);
            if (sgencuestaDetalleNew != null && !sgencuestaDetalleNew.equals(sgencuestaDetalleOld)) {
                SgcabEncuesta oldSgcabEncuestaOfSgencuestaDetalle = sgencuestaDetalleNew.getSgcabEncuesta();
                if (oldSgcabEncuestaOfSgencuestaDetalle != null) {
                    oldSgcabEncuestaOfSgencuestaDetalle.setSgencuestaDetalle(null);
                    oldSgcabEncuestaOfSgencuestaDetalle = em.merge(oldSgcabEncuestaOfSgencuestaDetalle);
                }
                sgencuestaDetalleNew.setSgcabEncuesta(sgcabEncuesta);
                sgencuestaDetalleNew = em.merge(sgencuestaDetalleNew);
            }
            if (idencuestaOld != null && !idencuestaOld.equals(idencuestaNew)) {
                idencuestaOld.getSgcabEncuestaList().remove(sgcabEncuesta);
                idencuestaOld = em.merge(idencuestaOld);
            }
            if (idencuestaNew != null && !idencuestaNew.equals(idencuestaOld)) {
                idencuestaNew.getSgcabEncuestaList().add(sgcabEncuesta);
                idencuestaNew = em.merge(idencuestaNew);
            }
            if (codUsuarioOld != null && !codUsuarioOld.equals(codUsuarioNew)) {
                codUsuarioOld.getSgcabEncuestaList().remove(sgcabEncuesta);
                codUsuarioOld = em.merge(codUsuarioOld);
            }
            if (codUsuarioNew != null && !codUsuarioNew.equals(codUsuarioOld)) {
                codUsuarioNew.getSgcabEncuestaList().add(sgcabEncuesta);
                codUsuarioNew = em.merge(codUsuarioNew);
            }
            for (SgUsuario sgUsuarioListOldSgUsuario : sgUsuarioListOld) {
                if (!sgUsuarioListNew.contains(sgUsuarioListOldSgUsuario)) {
                    sgUsuarioListOldSgUsuario.getSgcabEncuestaList().remove(sgcabEncuesta);
                    sgUsuarioListOldSgUsuario = em.merge(sgUsuarioListOldSgUsuario);
                }
            }
            for (SgUsuario sgUsuarioListNewSgUsuario : sgUsuarioListNew) {
                if (!sgUsuarioListOld.contains(sgUsuarioListNewSgUsuario)) {
                    sgUsuarioListNewSgUsuario.getSgcabEncuestaList().add(sgcabEncuesta);
                    sgUsuarioListNewSgUsuario = em.merge(sgUsuarioListNewSgUsuario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgcabEncuestaPK id = sgcabEncuesta.getSgcabEncuestaPK();
                if (findSgcabEncuesta(id) == null) {
                    throw new NonexistentEntityException("The sgcabEncuesta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgcabEncuestaPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgcabEncuesta sgcabEncuesta;
            try {
                sgcabEncuesta = em.getReference(SgcabEncuesta.class, id);
                sgcabEncuesta.getSgcabEncuestaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgcabEncuesta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            SgencuestaDetalle sgencuestaDetalleOrphanCheck = sgcabEncuesta.getSgencuestaDetalle();
            if (sgencuestaDetalleOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SgcabEncuesta (" + sgcabEncuesta + ") cannot be destroyed since the SgencuestaDetalle " + sgencuestaDetalleOrphanCheck + " in its sgencuestaDetalle field has a non-nullable sgcabEncuesta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            SgpCabEncuesta idencuesta = sgcabEncuesta.getIdencuesta();
            if (idencuesta != null) {
                idencuesta.getSgcabEncuestaList().remove(sgcabEncuesta);
                idencuesta = em.merge(idencuesta);
            }
            SgUsuario codUsuario = sgcabEncuesta.getCodUsuario();
            if (codUsuario != null) {
                codUsuario.getSgcabEncuestaList().remove(sgcabEncuesta);
                codUsuario = em.merge(codUsuario);
            }
            List<SgUsuario> sgUsuarioList = sgcabEncuesta.getSgUsuarioList();
            for (SgUsuario sgUsuarioListSgUsuario : sgUsuarioList) {
                sgUsuarioListSgUsuario.getSgcabEncuestaList().remove(sgcabEncuesta);
                sgUsuarioListSgUsuario = em.merge(sgUsuarioListSgUsuario);
            }
            em.remove(sgcabEncuesta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgcabEncuesta> findSgcabEncuestaEntities() {
        return findSgcabEncuestaEntities(true, -1, -1);
    }

    public List<SgcabEncuesta> findSgcabEncuestaEntities(int maxResults, int firstResult) {
        return findSgcabEncuestaEntities(false, maxResults, firstResult);
    }

    private List<SgcabEncuesta> findSgcabEncuestaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgcabEncuesta.class));
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

    public SgcabEncuesta findSgcabEncuesta(SgcabEncuestaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgcabEncuesta.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgcabEncuestaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgcabEncuesta> rt = cq.from(SgcabEncuesta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
