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
import sg.sistemas.entidades.Sgnc;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.Sgcentro;
import sg.sistemas.entidades.Sgsociedad;

/**
 *
 * @author Misael
 */
public class SgcentroJpaController implements Serializable {

    public SgcentroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sgcentro sgcentro) throws PreexistingEntityException, Exception {
        if (sgcentro.getSgncList() == null) {
            sgcentro.setSgncList(new ArrayList<Sgnc>());
        }
        if (sgcentro.getSgsociedadList() == null) {
            sgcentro.setSgsociedadList(new ArrayList<Sgsociedad>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Sgnc> attachedSgncList = new ArrayList<Sgnc>();
            for (Sgnc sgncListSgncToAttach : sgcentro.getSgncList()) {
                sgncListSgncToAttach = em.getReference(sgncListSgncToAttach.getClass(), sgncListSgncToAttach.getSgncPK());
                attachedSgncList.add(sgncListSgncToAttach);
            }
            sgcentro.setSgncList(attachedSgncList);
            List<Sgsociedad> attachedSgsociedadList = new ArrayList<Sgsociedad>();
            for (Sgsociedad sgsociedadListSgsociedadToAttach : sgcentro.getSgsociedadList()) {
                sgsociedadListSgsociedadToAttach = em.getReference(sgsociedadListSgsociedadToAttach.getClass(), sgsociedadListSgsociedadToAttach.getIdsociedad());
                attachedSgsociedadList.add(sgsociedadListSgsociedadToAttach);
            }
            sgcentro.setSgsociedadList(attachedSgsociedadList);
            em.persist(sgcentro);
            for (Sgnc sgncListSgnc : sgcentro.getSgncList()) {
                sgncListSgnc.getSgcentroList().add(sgcentro);
                sgncListSgnc = em.merge(sgncListSgnc);
            }
            for (Sgsociedad sgsociedadListSgsociedad : sgcentro.getSgsociedadList()) {
                sgsociedadListSgsociedad.getSgcentroList().add(sgcentro);
                sgsociedadListSgsociedad = em.merge(sgsociedadListSgsociedad);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgcentro(sgcentro.getIdcentro()) != null) {
                throw new PreexistingEntityException("Sgcentro " + sgcentro + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sgcentro sgcentro) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgcentro persistentSgcentro = em.find(Sgcentro.class, sgcentro.getIdcentro());
            List<Sgnc> sgncListOld = persistentSgcentro.getSgncList();
            List<Sgnc> sgncListNew = sgcentro.getSgncList();
            List<Sgsociedad> sgsociedadListOld = persistentSgcentro.getSgsociedadList();
            List<Sgsociedad> sgsociedadListNew = sgcentro.getSgsociedadList();
            List<Sgnc> attachedSgncListNew = new ArrayList<Sgnc>();
            for (Sgnc sgncListNewSgncToAttach : sgncListNew) {
                sgncListNewSgncToAttach = em.getReference(sgncListNewSgncToAttach.getClass(), sgncListNewSgncToAttach.getSgncPK());
                attachedSgncListNew.add(sgncListNewSgncToAttach);
            }
            sgncListNew = attachedSgncListNew;
            sgcentro.setSgncList(sgncListNew);
            List<Sgsociedad> attachedSgsociedadListNew = new ArrayList<Sgsociedad>();
            for (Sgsociedad sgsociedadListNewSgsociedadToAttach : sgsociedadListNew) {
                sgsociedadListNewSgsociedadToAttach = em.getReference(sgsociedadListNewSgsociedadToAttach.getClass(), sgsociedadListNewSgsociedadToAttach.getIdsociedad());
                attachedSgsociedadListNew.add(sgsociedadListNewSgsociedadToAttach);
            }
            sgsociedadListNew = attachedSgsociedadListNew;
            sgcentro.setSgsociedadList(sgsociedadListNew);
            sgcentro = em.merge(sgcentro);
            for (Sgnc sgncListOldSgnc : sgncListOld) {
                if (!sgncListNew.contains(sgncListOldSgnc)) {
                    sgncListOldSgnc.getSgcentroList().remove(sgcentro);
                    sgncListOldSgnc = em.merge(sgncListOldSgnc);
                }
            }
            for (Sgnc sgncListNewSgnc : sgncListNew) {
                if (!sgncListOld.contains(sgncListNewSgnc)) {
                    sgncListNewSgnc.getSgcentroList().add(sgcentro);
                    sgncListNewSgnc = em.merge(sgncListNewSgnc);
                }
            }
            for (Sgsociedad sgsociedadListOldSgsociedad : sgsociedadListOld) {
                if (!sgsociedadListNew.contains(sgsociedadListOldSgsociedad)) {
                    sgsociedadListOldSgsociedad.getSgcentroList().remove(sgcentro);
                    sgsociedadListOldSgsociedad = em.merge(sgsociedadListOldSgsociedad);
                }
            }
            for (Sgsociedad sgsociedadListNewSgsociedad : sgsociedadListNew) {
                if (!sgsociedadListOld.contains(sgsociedadListNewSgsociedad)) {
                    sgsociedadListNewSgsociedad.getSgcentroList().add(sgcentro);
                    sgsociedadListNewSgsociedad = em.merge(sgsociedadListNewSgsociedad);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = sgcentro.getIdcentro();
                if (findSgcentro(id) == null) {
                    throw new NonexistentEntityException("The sgcentro with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgcentro sgcentro;
            try {
                sgcentro = em.getReference(Sgcentro.class, id);
                sgcentro.getIdcentro();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgcentro with id " + id + " no longer exists.", enfe);
            }
            List<Sgnc> sgncList = sgcentro.getSgncList();
            for (Sgnc sgncListSgnc : sgncList) {
                sgncListSgnc.getSgcentroList().remove(sgcentro);
                sgncListSgnc = em.merge(sgncListSgnc);
            }
            List<Sgsociedad> sgsociedadList = sgcentro.getSgsociedadList();
            for (Sgsociedad sgsociedadListSgsociedad : sgsociedadList) {
                sgsociedadListSgsociedad.getSgcentroList().remove(sgcentro);
                sgsociedadListSgsociedad = em.merge(sgsociedadListSgsociedad);
            }
            em.remove(sgcentro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sgcentro> findSgcentroEntities() {
        return findSgcentroEntities(true, -1, -1);
    }

    public List<Sgcentro> findSgcentroEntities(int maxResults, int firstResult) {
        return findSgcentroEntities(false, maxResults, firstResult);
    }

    private List<Sgcentro> findSgcentroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sgcentro.class));
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

    public Sgcentro findSgcentro(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sgcentro.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgcentroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sgcentro> rt = cq.from(Sgcentro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
