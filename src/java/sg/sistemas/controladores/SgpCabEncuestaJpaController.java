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
import sg.sistemas.entidades.SgcabEncuesta;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgpCabEncuesta;
import sg.sistemas.entidades.SgpEncuestaDetalle;

/**
 *
 * @author Misael
 */
public class SgpCabEncuestaJpaController implements Serializable {

    public SgpCabEncuestaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgpCabEncuesta sgpCabEncuesta) throws PreexistingEntityException, Exception {
        if (sgpCabEncuesta.getSgcabEncuestaList() == null) {
            sgpCabEncuesta.setSgcabEncuestaList(new ArrayList<SgcabEncuesta>());
        }
        if (sgpCabEncuesta.getSgpEncuestaDetalleList() == null) {
            sgpCabEncuesta.setSgpEncuestaDetalleList(new ArrayList<SgpEncuestaDetalle>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgUsuario codUsuario = sgpCabEncuesta.getCodUsuario();
            if (codUsuario != null) {
                codUsuario = em.getReference(codUsuario.getClass(), codUsuario.getCodUsuario());
                sgpCabEncuesta.setCodUsuario(codUsuario);
            }
            List<SgcabEncuesta> attachedSgcabEncuestaList = new ArrayList<SgcabEncuesta>();
            for (SgcabEncuesta sgcabEncuestaListSgcabEncuestaToAttach : sgpCabEncuesta.getSgcabEncuestaList()) {
                sgcabEncuestaListSgcabEncuestaToAttach = em.getReference(sgcabEncuestaListSgcabEncuestaToAttach.getClass(), sgcabEncuestaListSgcabEncuestaToAttach.getSgcabEncuestaPK());
                attachedSgcabEncuestaList.add(sgcabEncuestaListSgcabEncuestaToAttach);
            }
            sgpCabEncuesta.setSgcabEncuestaList(attachedSgcabEncuestaList);
            List<SgpEncuestaDetalle> attachedSgpEncuestaDetalleList = new ArrayList<SgpEncuestaDetalle>();
            for (SgpEncuestaDetalle sgpEncuestaDetalleListSgpEncuestaDetalleToAttach : sgpCabEncuesta.getSgpEncuestaDetalleList()) {
                sgpEncuestaDetalleListSgpEncuestaDetalleToAttach = em.getReference(sgpEncuestaDetalleListSgpEncuestaDetalleToAttach.getClass(), sgpEncuestaDetalleListSgpEncuestaDetalleToAttach.getSgpEncuestaDetallePK());
                attachedSgpEncuestaDetalleList.add(sgpEncuestaDetalleListSgpEncuestaDetalleToAttach);
            }
            sgpCabEncuesta.setSgpEncuestaDetalleList(attachedSgpEncuestaDetalleList);
            em.persist(sgpCabEncuesta);
            if (codUsuario != null) {
                codUsuario.getSgpCabEncuestaList().add(sgpCabEncuesta);
                codUsuario = em.merge(codUsuario);
            }
            for (SgcabEncuesta sgcabEncuestaListSgcabEncuesta : sgpCabEncuesta.getSgcabEncuestaList()) {
                SgpCabEncuesta oldIdencuestaOfSgcabEncuestaListSgcabEncuesta = sgcabEncuestaListSgcabEncuesta.getIdencuesta();
                sgcabEncuestaListSgcabEncuesta.setIdencuesta(sgpCabEncuesta);
                sgcabEncuestaListSgcabEncuesta = em.merge(sgcabEncuestaListSgcabEncuesta);
                if (oldIdencuestaOfSgcabEncuestaListSgcabEncuesta != null) {
                    oldIdencuestaOfSgcabEncuestaListSgcabEncuesta.getSgcabEncuestaList().remove(sgcabEncuestaListSgcabEncuesta);
                    oldIdencuestaOfSgcabEncuestaListSgcabEncuesta = em.merge(oldIdencuestaOfSgcabEncuestaListSgcabEncuesta);
                }
            }
            for (SgpEncuestaDetalle sgpEncuestaDetalleListSgpEncuestaDetalle : sgpCabEncuesta.getSgpEncuestaDetalleList()) {
                SgpCabEncuesta oldSgpCabEncuestaOfSgpEncuestaDetalleListSgpEncuestaDetalle = sgpEncuestaDetalleListSgpEncuestaDetalle.getSgpCabEncuesta();
                sgpEncuestaDetalleListSgpEncuestaDetalle.setSgpCabEncuesta(sgpCabEncuesta);
                sgpEncuestaDetalleListSgpEncuestaDetalle = em.merge(sgpEncuestaDetalleListSgpEncuestaDetalle);
                if (oldSgpCabEncuestaOfSgpEncuestaDetalleListSgpEncuestaDetalle != null) {
                    oldSgpCabEncuestaOfSgpEncuestaDetalleListSgpEncuestaDetalle.getSgpEncuestaDetalleList().remove(sgpEncuestaDetalleListSgpEncuestaDetalle);
                    oldSgpCabEncuestaOfSgpEncuestaDetalleListSgpEncuestaDetalle = em.merge(oldSgpCabEncuestaOfSgpEncuestaDetalleListSgpEncuestaDetalle);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgpCabEncuesta(sgpCabEncuesta.getIdencuesta()) != null) {
                throw new PreexistingEntityException("SgpCabEncuesta " + sgpCabEncuesta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgpCabEncuesta sgpCabEncuesta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgpCabEncuesta persistentSgpCabEncuesta = em.find(SgpCabEncuesta.class, sgpCabEncuesta.getIdencuesta());
            SgUsuario codUsuarioOld = persistentSgpCabEncuesta.getCodUsuario();
            SgUsuario codUsuarioNew = sgpCabEncuesta.getCodUsuario();
            List<SgcabEncuesta> sgcabEncuestaListOld = persistentSgpCabEncuesta.getSgcabEncuestaList();
            List<SgcabEncuesta> sgcabEncuestaListNew = sgpCabEncuesta.getSgcabEncuestaList();
            List<SgpEncuestaDetalle> sgpEncuestaDetalleListOld = persistentSgpCabEncuesta.getSgpEncuestaDetalleList();
            List<SgpEncuestaDetalle> sgpEncuestaDetalleListNew = sgpCabEncuesta.getSgpEncuestaDetalleList();
            List<String> illegalOrphanMessages = null;
            for (SgpEncuestaDetalle sgpEncuestaDetalleListOldSgpEncuestaDetalle : sgpEncuestaDetalleListOld) {
                if (!sgpEncuestaDetalleListNew.contains(sgpEncuestaDetalleListOldSgpEncuestaDetalle)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SgpEncuestaDetalle " + sgpEncuestaDetalleListOldSgpEncuestaDetalle + " since its sgpCabEncuesta field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codUsuarioNew != null) {
                codUsuarioNew = em.getReference(codUsuarioNew.getClass(), codUsuarioNew.getCodUsuario());
                sgpCabEncuesta.setCodUsuario(codUsuarioNew);
            }
            List<SgcabEncuesta> attachedSgcabEncuestaListNew = new ArrayList<SgcabEncuesta>();
            for (SgcabEncuesta sgcabEncuestaListNewSgcabEncuestaToAttach : sgcabEncuestaListNew) {
                sgcabEncuestaListNewSgcabEncuestaToAttach = em.getReference(sgcabEncuestaListNewSgcabEncuestaToAttach.getClass(), sgcabEncuestaListNewSgcabEncuestaToAttach.getSgcabEncuestaPK());
                attachedSgcabEncuestaListNew.add(sgcabEncuestaListNewSgcabEncuestaToAttach);
            }
            sgcabEncuestaListNew = attachedSgcabEncuestaListNew;
            sgpCabEncuesta.setSgcabEncuestaList(sgcabEncuestaListNew);
            List<SgpEncuestaDetalle> attachedSgpEncuestaDetalleListNew = new ArrayList<SgpEncuestaDetalle>();
            for (SgpEncuestaDetalle sgpEncuestaDetalleListNewSgpEncuestaDetalleToAttach : sgpEncuestaDetalleListNew) {
                sgpEncuestaDetalleListNewSgpEncuestaDetalleToAttach = em.getReference(sgpEncuestaDetalleListNewSgpEncuestaDetalleToAttach.getClass(), sgpEncuestaDetalleListNewSgpEncuestaDetalleToAttach.getSgpEncuestaDetallePK());
                attachedSgpEncuestaDetalleListNew.add(sgpEncuestaDetalleListNewSgpEncuestaDetalleToAttach);
            }
            sgpEncuestaDetalleListNew = attachedSgpEncuestaDetalleListNew;
            sgpCabEncuesta.setSgpEncuestaDetalleList(sgpEncuestaDetalleListNew);
            sgpCabEncuesta = em.merge(sgpCabEncuesta);
            if (codUsuarioOld != null && !codUsuarioOld.equals(codUsuarioNew)) {
                codUsuarioOld.getSgpCabEncuestaList().remove(sgpCabEncuesta);
                codUsuarioOld = em.merge(codUsuarioOld);
            }
            if (codUsuarioNew != null && !codUsuarioNew.equals(codUsuarioOld)) {
                codUsuarioNew.getSgpCabEncuestaList().add(sgpCabEncuesta);
                codUsuarioNew = em.merge(codUsuarioNew);
            }
            for (SgcabEncuesta sgcabEncuestaListOldSgcabEncuesta : sgcabEncuestaListOld) {
                if (!sgcabEncuestaListNew.contains(sgcabEncuestaListOldSgcabEncuesta)) {
                    sgcabEncuestaListOldSgcabEncuesta.setIdencuesta(null);
                    sgcabEncuestaListOldSgcabEncuesta = em.merge(sgcabEncuestaListOldSgcabEncuesta);
                }
            }
            for (SgcabEncuesta sgcabEncuestaListNewSgcabEncuesta : sgcabEncuestaListNew) {
                if (!sgcabEncuestaListOld.contains(sgcabEncuestaListNewSgcabEncuesta)) {
                    SgpCabEncuesta oldIdencuestaOfSgcabEncuestaListNewSgcabEncuesta = sgcabEncuestaListNewSgcabEncuesta.getIdencuesta();
                    sgcabEncuestaListNewSgcabEncuesta.setIdencuesta(sgpCabEncuesta);
                    sgcabEncuestaListNewSgcabEncuesta = em.merge(sgcabEncuestaListNewSgcabEncuesta);
                    if (oldIdencuestaOfSgcabEncuestaListNewSgcabEncuesta != null && !oldIdencuestaOfSgcabEncuestaListNewSgcabEncuesta.equals(sgpCabEncuesta)) {
                        oldIdencuestaOfSgcabEncuestaListNewSgcabEncuesta.getSgcabEncuestaList().remove(sgcabEncuestaListNewSgcabEncuesta);
                        oldIdencuestaOfSgcabEncuestaListNewSgcabEncuesta = em.merge(oldIdencuestaOfSgcabEncuestaListNewSgcabEncuesta);
                    }
                }
            }
            for (SgpEncuestaDetalle sgpEncuestaDetalleListNewSgpEncuestaDetalle : sgpEncuestaDetalleListNew) {
                if (!sgpEncuestaDetalleListOld.contains(sgpEncuestaDetalleListNewSgpEncuestaDetalle)) {
                    SgpCabEncuesta oldSgpCabEncuestaOfSgpEncuestaDetalleListNewSgpEncuestaDetalle = sgpEncuestaDetalleListNewSgpEncuestaDetalle.getSgpCabEncuesta();
                    sgpEncuestaDetalleListNewSgpEncuestaDetalle.setSgpCabEncuesta(sgpCabEncuesta);
                    sgpEncuestaDetalleListNewSgpEncuestaDetalle = em.merge(sgpEncuestaDetalleListNewSgpEncuestaDetalle);
                    if (oldSgpCabEncuestaOfSgpEncuestaDetalleListNewSgpEncuestaDetalle != null && !oldSgpCabEncuestaOfSgpEncuestaDetalleListNewSgpEncuestaDetalle.equals(sgpCabEncuesta)) {
                        oldSgpCabEncuestaOfSgpEncuestaDetalleListNewSgpEncuestaDetalle.getSgpEncuestaDetalleList().remove(sgpEncuestaDetalleListNewSgpEncuestaDetalle);
                        oldSgpCabEncuestaOfSgpEncuestaDetalleListNewSgpEncuestaDetalle = em.merge(oldSgpCabEncuestaOfSgpEncuestaDetalleListNewSgpEncuestaDetalle);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = sgpCabEncuesta.getIdencuesta();
                if (findSgpCabEncuesta(id) == null) {
                    throw new NonexistentEntityException("The sgpCabEncuesta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgpCabEncuesta sgpCabEncuesta;
            try {
                sgpCabEncuesta = em.getReference(SgpCabEncuesta.class, id);
                sgpCabEncuesta.getIdencuesta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgpCabEncuesta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SgpEncuestaDetalle> sgpEncuestaDetalleListOrphanCheck = sgpCabEncuesta.getSgpEncuestaDetalleList();
            for (SgpEncuestaDetalle sgpEncuestaDetalleListOrphanCheckSgpEncuestaDetalle : sgpEncuestaDetalleListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SgpCabEncuesta (" + sgpCabEncuesta + ") cannot be destroyed since the SgpEncuestaDetalle " + sgpEncuestaDetalleListOrphanCheckSgpEncuestaDetalle + " in its sgpEncuestaDetalleList field has a non-nullable sgpCabEncuesta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            SgUsuario codUsuario = sgpCabEncuesta.getCodUsuario();
            if (codUsuario != null) {
                codUsuario.getSgpCabEncuestaList().remove(sgpCabEncuesta);
                codUsuario = em.merge(codUsuario);
            }
            List<SgcabEncuesta> sgcabEncuestaList = sgpCabEncuesta.getSgcabEncuestaList();
            for (SgcabEncuesta sgcabEncuestaListSgcabEncuesta : sgcabEncuestaList) {
                sgcabEncuestaListSgcabEncuesta.setIdencuesta(null);
                sgcabEncuestaListSgcabEncuesta = em.merge(sgcabEncuestaListSgcabEncuesta);
            }
            em.remove(sgpCabEncuesta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgpCabEncuesta> findSgpCabEncuestaEntities() {
        return findSgpCabEncuestaEntities(true, -1, -1);
    }

    public List<SgpCabEncuesta> findSgpCabEncuestaEntities(int maxResults, int firstResult) {
        return findSgpCabEncuestaEntities(false, maxResults, firstResult);
    }

    private List<SgpCabEncuesta> findSgpCabEncuestaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgpCabEncuesta.class));
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

    public SgpCabEncuesta findSgpCabEncuesta(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgpCabEncuesta.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgpCabEncuestaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgpCabEncuesta> rt = cq.from(SgpCabEncuesta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
