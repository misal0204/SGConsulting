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
import sg.sistemas.entidades.Sgsociedad;
import sg.sistemas.entidades.SgprocesosRe;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.Sgproveedores;
import sg.sistemas.entidades.SgproveedoresPK;

/**
 *
 * @author Misael
 */
public class SgproveedoresJpaController implements Serializable {

    public SgproveedoresJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sgproveedores sgproveedores) throws PreexistingEntityException, Exception {
        if (sgproveedores.getSgproveedoresPK() == null) {
            sgproveedores.setSgproveedoresPK(new SgproveedoresPK());
        }
        if (sgproveedores.getSgprocesosReList() == null) {
            sgproveedores.setSgprocesosReList(new ArrayList<SgprocesosRe>());
        }
        sgproveedores.getSgproveedoresPK().setIdsociedad(sgproveedores.getSgsociedad().getIdsociedad());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgsociedad sgsociedad = sgproveedores.getSgsociedad();
            if (sgsociedad != null) {
                sgsociedad = em.getReference(sgsociedad.getClass(), sgsociedad.getIdsociedad());
                sgproveedores.setSgsociedad(sgsociedad);
            }
            List<SgprocesosRe> attachedSgprocesosReList = new ArrayList<SgprocesosRe>();
            for (SgprocesosRe sgprocesosReListSgprocesosReToAttach : sgproveedores.getSgprocesosReList()) {
                sgprocesosReListSgprocesosReToAttach = em.getReference(sgprocesosReListSgprocesosReToAttach.getClass(), sgprocesosReListSgprocesosReToAttach.getSgprocesosRePK());
                attachedSgprocesosReList.add(sgprocesosReListSgprocesosReToAttach);
            }
            sgproveedores.setSgprocesosReList(attachedSgprocesosReList);
            em.persist(sgproveedores);
            if (sgsociedad != null) {
                sgsociedad.getSgproveedoresList().add(sgproveedores);
                sgsociedad = em.merge(sgsociedad);
            }
            for (SgprocesosRe sgprocesosReListSgprocesosRe : sgproveedores.getSgprocesosReList()) {
                Sgproveedores oldSgproveedoresOfSgprocesosReListSgprocesosRe = sgprocesosReListSgprocesosRe.getSgproveedores();
                sgprocesosReListSgprocesosRe.setSgproveedores(sgproveedores);
                sgprocesosReListSgprocesosRe = em.merge(sgprocesosReListSgprocesosRe);
                if (oldSgproveedoresOfSgprocesosReListSgprocesosRe != null) {
                    oldSgproveedoresOfSgprocesosReListSgprocesosRe.getSgprocesosReList().remove(sgprocesosReListSgprocesosRe);
                    oldSgproveedoresOfSgprocesosReListSgprocesosRe = em.merge(oldSgproveedoresOfSgprocesosReListSgprocesosRe);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgproveedores(sgproveedores.getSgproveedoresPK()) != null) {
                throw new PreexistingEntityException("Sgproveedores " + sgproveedores + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sgproveedores sgproveedores) throws IllegalOrphanException, NonexistentEntityException, Exception {
        sgproveedores.getSgproveedoresPK().setIdsociedad(sgproveedores.getSgsociedad().getIdsociedad());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgproveedores persistentSgproveedores = em.find(Sgproveedores.class, sgproveedores.getSgproveedoresPK());
            Sgsociedad sgsociedadOld = persistentSgproveedores.getSgsociedad();
            Sgsociedad sgsociedadNew = sgproveedores.getSgsociedad();
            List<SgprocesosRe> sgprocesosReListOld = persistentSgproveedores.getSgprocesosReList();
            List<SgprocesosRe> sgprocesosReListNew = sgproveedores.getSgprocesosReList();
            List<String> illegalOrphanMessages = null;
            for (SgprocesosRe sgprocesosReListOldSgprocesosRe : sgprocesosReListOld) {
                if (!sgprocesosReListNew.contains(sgprocesosReListOldSgprocesosRe)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SgprocesosRe " + sgprocesosReListOldSgprocesosRe + " since its sgproveedores field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sgsociedadNew != null) {
                sgsociedadNew = em.getReference(sgsociedadNew.getClass(), sgsociedadNew.getIdsociedad());
                sgproveedores.setSgsociedad(sgsociedadNew);
            }
            List<SgprocesosRe> attachedSgprocesosReListNew = new ArrayList<SgprocesosRe>();
            for (SgprocesosRe sgprocesosReListNewSgprocesosReToAttach : sgprocesosReListNew) {
                sgprocesosReListNewSgprocesosReToAttach = em.getReference(sgprocesosReListNewSgprocesosReToAttach.getClass(), sgprocesosReListNewSgprocesosReToAttach.getSgprocesosRePK());
                attachedSgprocesosReListNew.add(sgprocesosReListNewSgprocesosReToAttach);
            }
            sgprocesosReListNew = attachedSgprocesosReListNew;
            sgproveedores.setSgprocesosReList(sgprocesosReListNew);
            sgproveedores = em.merge(sgproveedores);
            if (sgsociedadOld != null && !sgsociedadOld.equals(sgsociedadNew)) {
                sgsociedadOld.getSgproveedoresList().remove(sgproveedores);
                sgsociedadOld = em.merge(sgsociedadOld);
            }
            if (sgsociedadNew != null && !sgsociedadNew.equals(sgsociedadOld)) {
                sgsociedadNew.getSgproveedoresList().add(sgproveedores);
                sgsociedadNew = em.merge(sgsociedadNew);
            }
            for (SgprocesosRe sgprocesosReListNewSgprocesosRe : sgprocesosReListNew) {
                if (!sgprocesosReListOld.contains(sgprocesosReListNewSgprocesosRe)) {
                    Sgproveedores oldSgproveedoresOfSgprocesosReListNewSgprocesosRe = sgprocesosReListNewSgprocesosRe.getSgproveedores();
                    sgprocesosReListNewSgprocesosRe.setSgproveedores(sgproveedores);
                    sgprocesosReListNewSgprocesosRe = em.merge(sgprocesosReListNewSgprocesosRe);
                    if (oldSgproveedoresOfSgprocesosReListNewSgprocesosRe != null && !oldSgproveedoresOfSgprocesosReListNewSgprocesosRe.equals(sgproveedores)) {
                        oldSgproveedoresOfSgprocesosReListNewSgprocesosRe.getSgprocesosReList().remove(sgprocesosReListNewSgprocesosRe);
                        oldSgproveedoresOfSgprocesosReListNewSgprocesosRe = em.merge(oldSgproveedoresOfSgprocesosReListNewSgprocesosRe);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgproveedoresPK id = sgproveedores.getSgproveedoresPK();
                if (findSgproveedores(id) == null) {
                    throw new NonexistentEntityException("The sgproveedores with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgproveedoresPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgproveedores sgproveedores;
            try {
                sgproveedores = em.getReference(Sgproveedores.class, id);
                sgproveedores.getSgproveedoresPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgproveedores with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SgprocesosRe> sgprocesosReListOrphanCheck = sgproveedores.getSgprocesosReList();
            for (SgprocesosRe sgprocesosReListOrphanCheckSgprocesosRe : sgprocesosReListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sgproveedores (" + sgproveedores + ") cannot be destroyed since the SgprocesosRe " + sgprocesosReListOrphanCheckSgprocesosRe + " in its sgprocesosReList field has a non-nullable sgproveedores field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Sgsociedad sgsociedad = sgproveedores.getSgsociedad();
            if (sgsociedad != null) {
                sgsociedad.getSgproveedoresList().remove(sgproveedores);
                sgsociedad = em.merge(sgsociedad);
            }
            em.remove(sgproveedores);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sgproveedores> findSgproveedoresEntities() {
        return findSgproveedoresEntities(true, -1, -1);
    }

    public List<Sgproveedores> findSgproveedoresEntities(int maxResults, int firstResult) {
        return findSgproveedoresEntities(false, maxResults, firstResult);
    }

    private List<Sgproveedores> findSgproveedoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sgproveedores.class));
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

    public Sgproveedores findSgproveedores(SgproveedoresPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sgproveedores.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgproveedoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sgproveedores> rt = cq.from(Sgproveedores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
