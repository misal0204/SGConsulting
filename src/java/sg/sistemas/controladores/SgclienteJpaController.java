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
import sg.sistemas.entidades.Sgcliente;
import sg.sistemas.entidades.SgclientePK;

/**
 *
 * @author Misael
 */
public class SgclienteJpaController implements Serializable {

    public SgclienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sgcliente sgcliente) throws PreexistingEntityException, Exception {
        if (sgcliente.getSgclientePK() == null) {
            sgcliente.setSgclientePK(new SgclientePK());
        }
        if (sgcliente.getSgprocesosReList() == null) {
            sgcliente.setSgprocesosReList(new ArrayList<SgprocesosRe>());
        }
        sgcliente.getSgclientePK().setIdsociedad(sgcliente.getSgsociedad().getIdsociedad());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgsociedad sgsociedad = sgcliente.getSgsociedad();
            if (sgsociedad != null) {
                sgsociedad = em.getReference(sgsociedad.getClass(), sgsociedad.getIdsociedad());
                sgcliente.setSgsociedad(sgsociedad);
            }
            List<SgprocesosRe> attachedSgprocesosReList = new ArrayList<SgprocesosRe>();
            for (SgprocesosRe sgprocesosReListSgprocesosReToAttach : sgcliente.getSgprocesosReList()) {
                sgprocesosReListSgprocesosReToAttach = em.getReference(sgprocesosReListSgprocesosReToAttach.getClass(), sgprocesosReListSgprocesosReToAttach.getSgprocesosRePK());
                attachedSgprocesosReList.add(sgprocesosReListSgprocesosReToAttach);
            }
            sgcliente.setSgprocesosReList(attachedSgprocesosReList);
            em.persist(sgcliente);
            if (sgsociedad != null) {
                sgsociedad.getSgclienteList().add(sgcliente);
                sgsociedad = em.merge(sgsociedad);
            }
            for (SgprocesosRe sgprocesosReListSgprocesosRe : sgcliente.getSgprocesosReList()) {
                Sgcliente oldSgclienteOfSgprocesosReListSgprocesosRe = sgprocesosReListSgprocesosRe.getSgcliente();
                sgprocesosReListSgprocesosRe.setSgcliente(sgcliente);
                sgprocesosReListSgprocesosRe = em.merge(sgprocesosReListSgprocesosRe);
                if (oldSgclienteOfSgprocesosReListSgprocesosRe != null) {
                    oldSgclienteOfSgprocesosReListSgprocesosRe.getSgprocesosReList().remove(sgprocesosReListSgprocesosRe);
                    oldSgclienteOfSgprocesosReListSgprocesosRe = em.merge(oldSgclienteOfSgprocesosReListSgprocesosRe);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgcliente(sgcliente.getSgclientePK()) != null) {
                throw new PreexistingEntityException("Sgcliente " + sgcliente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sgcliente sgcliente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        sgcliente.getSgclientePK().setIdsociedad(sgcliente.getSgsociedad().getIdsociedad());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgcliente persistentSgcliente = em.find(Sgcliente.class, sgcliente.getSgclientePK());
            Sgsociedad sgsociedadOld = persistentSgcliente.getSgsociedad();
            Sgsociedad sgsociedadNew = sgcliente.getSgsociedad();
            List<SgprocesosRe> sgprocesosReListOld = persistentSgcliente.getSgprocesosReList();
            List<SgprocesosRe> sgprocesosReListNew = sgcliente.getSgprocesosReList();
            List<String> illegalOrphanMessages = null;
            for (SgprocesosRe sgprocesosReListOldSgprocesosRe : sgprocesosReListOld) {
                if (!sgprocesosReListNew.contains(sgprocesosReListOldSgprocesosRe)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SgprocesosRe " + sgprocesosReListOldSgprocesosRe + " since its sgcliente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sgsociedadNew != null) {
                sgsociedadNew = em.getReference(sgsociedadNew.getClass(), sgsociedadNew.getIdsociedad());
                sgcliente.setSgsociedad(sgsociedadNew);
            }
            List<SgprocesosRe> attachedSgprocesosReListNew = new ArrayList<SgprocesosRe>();
            for (SgprocesosRe sgprocesosReListNewSgprocesosReToAttach : sgprocesosReListNew) {
                sgprocesosReListNewSgprocesosReToAttach = em.getReference(sgprocesosReListNewSgprocesosReToAttach.getClass(), sgprocesosReListNewSgprocesosReToAttach.getSgprocesosRePK());
                attachedSgprocesosReListNew.add(sgprocesosReListNewSgprocesosReToAttach);
            }
            sgprocesosReListNew = attachedSgprocesosReListNew;
            sgcliente.setSgprocesosReList(sgprocesosReListNew);
            sgcliente = em.merge(sgcliente);
            if (sgsociedadOld != null && !sgsociedadOld.equals(sgsociedadNew)) {
                sgsociedadOld.getSgclienteList().remove(sgcliente);
                sgsociedadOld = em.merge(sgsociedadOld);
            }
            if (sgsociedadNew != null && !sgsociedadNew.equals(sgsociedadOld)) {
                sgsociedadNew.getSgclienteList().add(sgcliente);
                sgsociedadNew = em.merge(sgsociedadNew);
            }
            for (SgprocesosRe sgprocesosReListNewSgprocesosRe : sgprocesosReListNew) {
                if (!sgprocesosReListOld.contains(sgprocesosReListNewSgprocesosRe)) {
                    Sgcliente oldSgclienteOfSgprocesosReListNewSgprocesosRe = sgprocesosReListNewSgprocesosRe.getSgcliente();
                    sgprocesosReListNewSgprocesosRe.setSgcliente(sgcliente);
                    sgprocesosReListNewSgprocesosRe = em.merge(sgprocesosReListNewSgprocesosRe);
                    if (oldSgclienteOfSgprocesosReListNewSgprocesosRe != null && !oldSgclienteOfSgprocesosReListNewSgprocesosRe.equals(sgcliente)) {
                        oldSgclienteOfSgprocesosReListNewSgprocesosRe.getSgprocesosReList().remove(sgprocesosReListNewSgprocesosRe);
                        oldSgclienteOfSgprocesosReListNewSgprocesosRe = em.merge(oldSgclienteOfSgprocesosReListNewSgprocesosRe);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgclientePK id = sgcliente.getSgclientePK();
                if (findSgcliente(id) == null) {
                    throw new NonexistentEntityException("The sgcliente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgclientePK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgcliente sgcliente;
            try {
                sgcliente = em.getReference(Sgcliente.class, id);
                sgcliente.getSgclientePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgcliente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SgprocesosRe> sgprocesosReListOrphanCheck = sgcliente.getSgprocesosReList();
            for (SgprocesosRe sgprocesosReListOrphanCheckSgprocesosRe : sgprocesosReListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sgcliente (" + sgcliente + ") cannot be destroyed since the SgprocesosRe " + sgprocesosReListOrphanCheckSgprocesosRe + " in its sgprocesosReList field has a non-nullable sgcliente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Sgsociedad sgsociedad = sgcliente.getSgsociedad();
            if (sgsociedad != null) {
                sgsociedad.getSgclienteList().remove(sgcliente);
                sgsociedad = em.merge(sgsociedad);
            }
            em.remove(sgcliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sgcliente> findSgclienteEntities() {
        return findSgclienteEntities(true, -1, -1);
    }

    public List<Sgcliente> findSgclienteEntities(int maxResults, int firstResult) {
        return findSgclienteEntities(false, maxResults, firstResult);
    }

    private List<Sgcliente> findSgclienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sgcliente.class));
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

    public Sgcliente findSgcliente(SgclientePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sgcliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgclienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sgcliente> rt = cq.from(Sgcliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
