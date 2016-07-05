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
import sg.sistemas.entidades.SgplanForma;
import sg.sistemas.entidades.SgprogDetalle;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgplanPrograma;
import sg.sistemas.entidades.SgplanProgramaPK;
import sg.sistemas.entidades.SgprogramaEmpleado;

/**
 *
 * @author Misael
 */
public class SgplanProgramaJpaController implements Serializable {

    public SgplanProgramaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgplanPrograma sgplanPrograma) throws PreexistingEntityException, Exception {
        if (sgplanPrograma.getSgplanProgramaPK() == null) {
            sgplanPrograma.setSgplanProgramaPK(new SgplanProgramaPK());
        }
        if (sgplanPrograma.getSgprogDetalleList() == null) {
            sgplanPrograma.setSgprogDetalleList(new ArrayList<SgprogDetalle>());
        }
        if (sgplanPrograma.getSgprogramaEmpleadoList() == null) {
            sgplanPrograma.setSgprogramaEmpleadoList(new ArrayList<SgprogramaEmpleado>());
        }
        sgplanPrograma.getSgplanProgramaPK().setIdsociedad(sgplanPrograma.getSgplanForma().getSgplanFormaPK().getIdsociedad());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgplanForma sgplanForma = sgplanPrograma.getSgplanForma();
            if (sgplanForma != null) {
                sgplanForma = em.getReference(sgplanForma.getClass(), sgplanForma.getSgplanFormaPK());
                sgplanPrograma.setSgplanForma(sgplanForma);
            }
            List<SgprogDetalle> attachedSgprogDetalleList = new ArrayList<SgprogDetalle>();
            for (SgprogDetalle sgprogDetalleListSgprogDetalleToAttach : sgplanPrograma.getSgprogDetalleList()) {
                sgprogDetalleListSgprogDetalleToAttach = em.getReference(sgprogDetalleListSgprogDetalleToAttach.getClass(), sgprogDetalleListSgprogDetalleToAttach.getSgprogDetallePK());
                attachedSgprogDetalleList.add(sgprogDetalleListSgprogDetalleToAttach);
            }
            sgplanPrograma.setSgprogDetalleList(attachedSgprogDetalleList);
            List<SgprogramaEmpleado> attachedSgprogramaEmpleadoList = new ArrayList<SgprogramaEmpleado>();
            for (SgprogramaEmpleado sgprogramaEmpleadoListSgprogramaEmpleadoToAttach : sgplanPrograma.getSgprogramaEmpleadoList()) {
                sgprogramaEmpleadoListSgprogramaEmpleadoToAttach = em.getReference(sgprogramaEmpleadoListSgprogramaEmpleadoToAttach.getClass(), sgprogramaEmpleadoListSgprogramaEmpleadoToAttach.getSgprogramaEmpleadoPK());
                attachedSgprogramaEmpleadoList.add(sgprogramaEmpleadoListSgprogramaEmpleadoToAttach);
            }
            sgplanPrograma.setSgprogramaEmpleadoList(attachedSgprogramaEmpleadoList);
            em.persist(sgplanPrograma);
            if (sgplanForma != null) {
                sgplanForma.getSgplanProgramaList().add(sgplanPrograma);
                sgplanForma = em.merge(sgplanForma);
            }
            for (SgprogDetalle sgprogDetalleListSgprogDetalle : sgplanPrograma.getSgprogDetalleList()) {
                SgplanPrograma oldSgplanProgramaOfSgprogDetalleListSgprogDetalle = sgprogDetalleListSgprogDetalle.getSgplanPrograma();
                sgprogDetalleListSgprogDetalle.setSgplanPrograma(sgplanPrograma);
                sgprogDetalleListSgprogDetalle = em.merge(sgprogDetalleListSgprogDetalle);
                if (oldSgplanProgramaOfSgprogDetalleListSgprogDetalle != null) {
                    oldSgplanProgramaOfSgprogDetalleListSgprogDetalle.getSgprogDetalleList().remove(sgprogDetalleListSgprogDetalle);
                    oldSgplanProgramaOfSgprogDetalleListSgprogDetalle = em.merge(oldSgplanProgramaOfSgprogDetalleListSgprogDetalle);
                }
            }
            for (SgprogramaEmpleado sgprogramaEmpleadoListSgprogramaEmpleado : sgplanPrograma.getSgprogramaEmpleadoList()) {
                SgplanPrograma oldSgplanProgramaOfSgprogramaEmpleadoListSgprogramaEmpleado = sgprogramaEmpleadoListSgprogramaEmpleado.getSgplanPrograma();
                sgprogramaEmpleadoListSgprogramaEmpleado.setSgplanPrograma(sgplanPrograma);
                sgprogramaEmpleadoListSgprogramaEmpleado = em.merge(sgprogramaEmpleadoListSgprogramaEmpleado);
                if (oldSgplanProgramaOfSgprogramaEmpleadoListSgprogramaEmpleado != null) {
                    oldSgplanProgramaOfSgprogramaEmpleadoListSgprogramaEmpleado.getSgprogramaEmpleadoList().remove(sgprogramaEmpleadoListSgprogramaEmpleado);
                    oldSgplanProgramaOfSgprogramaEmpleadoListSgprogramaEmpleado = em.merge(oldSgplanProgramaOfSgprogramaEmpleadoListSgprogramaEmpleado);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgplanPrograma(sgplanPrograma.getSgplanProgramaPK()) != null) {
                throw new PreexistingEntityException("SgplanPrograma " + sgplanPrograma + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgplanPrograma sgplanPrograma) throws IllegalOrphanException, NonexistentEntityException, Exception {
        sgplanPrograma.getSgplanProgramaPK().setIdsociedad(sgplanPrograma.getSgplanForma().getSgplanFormaPK().getIdsociedad());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgplanPrograma persistentSgplanPrograma = em.find(SgplanPrograma.class, sgplanPrograma.getSgplanProgramaPK());
            SgplanForma sgplanFormaOld = persistentSgplanPrograma.getSgplanForma();
            SgplanForma sgplanFormaNew = sgplanPrograma.getSgplanForma();
            List<SgprogDetalle> sgprogDetalleListOld = persistentSgplanPrograma.getSgprogDetalleList();
            List<SgprogDetalle> sgprogDetalleListNew = sgplanPrograma.getSgprogDetalleList();
            List<SgprogramaEmpleado> sgprogramaEmpleadoListOld = persistentSgplanPrograma.getSgprogramaEmpleadoList();
            List<SgprogramaEmpleado> sgprogramaEmpleadoListNew = sgplanPrograma.getSgprogramaEmpleadoList();
            List<String> illegalOrphanMessages = null;
            for (SgprogDetalle sgprogDetalleListOldSgprogDetalle : sgprogDetalleListOld) {
                if (!sgprogDetalleListNew.contains(sgprogDetalleListOldSgprogDetalle)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SgprogDetalle " + sgprogDetalleListOldSgprogDetalle + " since its sgplanPrograma field is not nullable.");
                }
            }
            for (SgprogramaEmpleado sgprogramaEmpleadoListOldSgprogramaEmpleado : sgprogramaEmpleadoListOld) {
                if (!sgprogramaEmpleadoListNew.contains(sgprogramaEmpleadoListOldSgprogramaEmpleado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SgprogramaEmpleado " + sgprogramaEmpleadoListOldSgprogramaEmpleado + " since its sgplanPrograma field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sgplanFormaNew != null) {
                sgplanFormaNew = em.getReference(sgplanFormaNew.getClass(), sgplanFormaNew.getSgplanFormaPK());
                sgplanPrograma.setSgplanForma(sgplanFormaNew);
            }
            List<SgprogDetalle> attachedSgprogDetalleListNew = new ArrayList<SgprogDetalle>();
            for (SgprogDetalle sgprogDetalleListNewSgprogDetalleToAttach : sgprogDetalleListNew) {
                sgprogDetalleListNewSgprogDetalleToAttach = em.getReference(sgprogDetalleListNewSgprogDetalleToAttach.getClass(), sgprogDetalleListNewSgprogDetalleToAttach.getSgprogDetallePK());
                attachedSgprogDetalleListNew.add(sgprogDetalleListNewSgprogDetalleToAttach);
            }
            sgprogDetalleListNew = attachedSgprogDetalleListNew;
            sgplanPrograma.setSgprogDetalleList(sgprogDetalleListNew);
            List<SgprogramaEmpleado> attachedSgprogramaEmpleadoListNew = new ArrayList<SgprogramaEmpleado>();
            for (SgprogramaEmpleado sgprogramaEmpleadoListNewSgprogramaEmpleadoToAttach : sgprogramaEmpleadoListNew) {
                sgprogramaEmpleadoListNewSgprogramaEmpleadoToAttach = em.getReference(sgprogramaEmpleadoListNewSgprogramaEmpleadoToAttach.getClass(), sgprogramaEmpleadoListNewSgprogramaEmpleadoToAttach.getSgprogramaEmpleadoPK());
                attachedSgprogramaEmpleadoListNew.add(sgprogramaEmpleadoListNewSgprogramaEmpleadoToAttach);
            }
            sgprogramaEmpleadoListNew = attachedSgprogramaEmpleadoListNew;
            sgplanPrograma.setSgprogramaEmpleadoList(sgprogramaEmpleadoListNew);
            sgplanPrograma = em.merge(sgplanPrograma);
            if (sgplanFormaOld != null && !sgplanFormaOld.equals(sgplanFormaNew)) {
                sgplanFormaOld.getSgplanProgramaList().remove(sgplanPrograma);
                sgplanFormaOld = em.merge(sgplanFormaOld);
            }
            if (sgplanFormaNew != null && !sgplanFormaNew.equals(sgplanFormaOld)) {
                sgplanFormaNew.getSgplanProgramaList().add(sgplanPrograma);
                sgplanFormaNew = em.merge(sgplanFormaNew);
            }
            for (SgprogDetalle sgprogDetalleListNewSgprogDetalle : sgprogDetalleListNew) {
                if (!sgprogDetalleListOld.contains(sgprogDetalleListNewSgprogDetalle)) {
                    SgplanPrograma oldSgplanProgramaOfSgprogDetalleListNewSgprogDetalle = sgprogDetalleListNewSgprogDetalle.getSgplanPrograma();
                    sgprogDetalleListNewSgprogDetalle.setSgplanPrograma(sgplanPrograma);
                    sgprogDetalleListNewSgprogDetalle = em.merge(sgprogDetalleListNewSgprogDetalle);
                    if (oldSgplanProgramaOfSgprogDetalleListNewSgprogDetalle != null && !oldSgplanProgramaOfSgprogDetalleListNewSgprogDetalle.equals(sgplanPrograma)) {
                        oldSgplanProgramaOfSgprogDetalleListNewSgprogDetalle.getSgprogDetalleList().remove(sgprogDetalleListNewSgprogDetalle);
                        oldSgplanProgramaOfSgprogDetalleListNewSgprogDetalle = em.merge(oldSgplanProgramaOfSgprogDetalleListNewSgprogDetalle);
                    }
                }
            }
            for (SgprogramaEmpleado sgprogramaEmpleadoListNewSgprogramaEmpleado : sgprogramaEmpleadoListNew) {
                if (!sgprogramaEmpleadoListOld.contains(sgprogramaEmpleadoListNewSgprogramaEmpleado)) {
                    SgplanPrograma oldSgplanProgramaOfSgprogramaEmpleadoListNewSgprogramaEmpleado = sgprogramaEmpleadoListNewSgprogramaEmpleado.getSgplanPrograma();
                    sgprogramaEmpleadoListNewSgprogramaEmpleado.setSgplanPrograma(sgplanPrograma);
                    sgprogramaEmpleadoListNewSgprogramaEmpleado = em.merge(sgprogramaEmpleadoListNewSgprogramaEmpleado);
                    if (oldSgplanProgramaOfSgprogramaEmpleadoListNewSgprogramaEmpleado != null && !oldSgplanProgramaOfSgprogramaEmpleadoListNewSgprogramaEmpleado.equals(sgplanPrograma)) {
                        oldSgplanProgramaOfSgprogramaEmpleadoListNewSgprogramaEmpleado.getSgprogramaEmpleadoList().remove(sgprogramaEmpleadoListNewSgprogramaEmpleado);
                        oldSgplanProgramaOfSgprogramaEmpleadoListNewSgprogramaEmpleado = em.merge(oldSgplanProgramaOfSgprogramaEmpleadoListNewSgprogramaEmpleado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgplanProgramaPK id = sgplanPrograma.getSgplanProgramaPK();
                if (findSgplanPrograma(id) == null) {
                    throw new NonexistentEntityException("The sgplanPrograma with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgplanProgramaPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgplanPrograma sgplanPrograma;
            try {
                sgplanPrograma = em.getReference(SgplanPrograma.class, id);
                sgplanPrograma.getSgplanProgramaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgplanPrograma with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SgprogDetalle> sgprogDetalleListOrphanCheck = sgplanPrograma.getSgprogDetalleList();
            for (SgprogDetalle sgprogDetalleListOrphanCheckSgprogDetalle : sgprogDetalleListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SgplanPrograma (" + sgplanPrograma + ") cannot be destroyed since the SgprogDetalle " + sgprogDetalleListOrphanCheckSgprogDetalle + " in its sgprogDetalleList field has a non-nullable sgplanPrograma field.");
            }
            List<SgprogramaEmpleado> sgprogramaEmpleadoListOrphanCheck = sgplanPrograma.getSgprogramaEmpleadoList();
            for (SgprogramaEmpleado sgprogramaEmpleadoListOrphanCheckSgprogramaEmpleado : sgprogramaEmpleadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SgplanPrograma (" + sgplanPrograma + ") cannot be destroyed since the SgprogramaEmpleado " + sgprogramaEmpleadoListOrphanCheckSgprogramaEmpleado + " in its sgprogramaEmpleadoList field has a non-nullable sgplanPrograma field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            SgplanForma sgplanForma = sgplanPrograma.getSgplanForma();
            if (sgplanForma != null) {
                sgplanForma.getSgplanProgramaList().remove(sgplanPrograma);
                sgplanForma = em.merge(sgplanForma);
            }
            em.remove(sgplanPrograma);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgplanPrograma> findSgplanProgramaEntities() {
        return findSgplanProgramaEntities(true, -1, -1);
    }

    public List<SgplanPrograma> findSgplanProgramaEntities(int maxResults, int firstResult) {
        return findSgplanProgramaEntities(false, maxResults, firstResult);
    }

    private List<SgplanPrograma> findSgplanProgramaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgplanPrograma.class));
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

    public SgplanPrograma findSgplanPrograma(SgplanProgramaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgplanPrograma.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgplanProgramaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgplanPrograma> rt = cq.from(SgplanPrograma.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
