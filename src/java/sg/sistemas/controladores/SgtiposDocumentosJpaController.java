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
import sg.sistemas.entidades.Sgdocumentos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgtiposDocumentos;

/**
 *
 * @author Misael
 */
public class SgtiposDocumentosJpaController implements Serializable {

    public SgtiposDocumentosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgtiposDocumentos sgtiposDocumentos) throws PreexistingEntityException, Exception {
        if (sgtiposDocumentos.getSgdocumentosList() == null) {
            sgtiposDocumentos.setSgdocumentosList(new ArrayList<Sgdocumentos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Sgdocumentos> attachedSgdocumentosList = new ArrayList<Sgdocumentos>();
            for (Sgdocumentos sgdocumentosListSgdocumentosToAttach : sgtiposDocumentos.getSgdocumentosList()) {
                sgdocumentosListSgdocumentosToAttach = em.getReference(sgdocumentosListSgdocumentosToAttach.getClass(), sgdocumentosListSgdocumentosToAttach.getSgdocumentosPK());
                attachedSgdocumentosList.add(sgdocumentosListSgdocumentosToAttach);
            }
            sgtiposDocumentos.setSgdocumentosList(attachedSgdocumentosList);
            em.persist(sgtiposDocumentos);
            for (Sgdocumentos sgdocumentosListSgdocumentos : sgtiposDocumentos.getSgdocumentosList()) {
                SgtiposDocumentos oldSgtiposDocumentosOfSgdocumentosListSgdocumentos = sgdocumentosListSgdocumentos.getSgtiposDocumentos();
                sgdocumentosListSgdocumentos.setSgtiposDocumentos(sgtiposDocumentos);
                sgdocumentosListSgdocumentos = em.merge(sgdocumentosListSgdocumentos);
                if (oldSgtiposDocumentosOfSgdocumentosListSgdocumentos != null) {
                    oldSgtiposDocumentosOfSgdocumentosListSgdocumentos.getSgdocumentosList().remove(sgdocumentosListSgdocumentos);
                    oldSgtiposDocumentosOfSgdocumentosListSgdocumentos = em.merge(oldSgtiposDocumentosOfSgdocumentosListSgdocumentos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgtiposDocumentos(sgtiposDocumentos.getIdtipoDocumento()) != null) {
                throw new PreexistingEntityException("SgtiposDocumentos " + sgtiposDocumentos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgtiposDocumentos sgtiposDocumentos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgtiposDocumentos persistentSgtiposDocumentos = em.find(SgtiposDocumentos.class, sgtiposDocumentos.getIdtipoDocumento());
            List<Sgdocumentos> sgdocumentosListOld = persistentSgtiposDocumentos.getSgdocumentosList();
            List<Sgdocumentos> sgdocumentosListNew = sgtiposDocumentos.getSgdocumentosList();
            List<String> illegalOrphanMessages = null;
            for (Sgdocumentos sgdocumentosListOldSgdocumentos : sgdocumentosListOld) {
                if (!sgdocumentosListNew.contains(sgdocumentosListOldSgdocumentos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sgdocumentos " + sgdocumentosListOldSgdocumentos + " since its sgtiposDocumentos field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Sgdocumentos> attachedSgdocumentosListNew = new ArrayList<Sgdocumentos>();
            for (Sgdocumentos sgdocumentosListNewSgdocumentosToAttach : sgdocumentosListNew) {
                sgdocumentosListNewSgdocumentosToAttach = em.getReference(sgdocumentosListNewSgdocumentosToAttach.getClass(), sgdocumentosListNewSgdocumentosToAttach.getSgdocumentosPK());
                attachedSgdocumentosListNew.add(sgdocumentosListNewSgdocumentosToAttach);
            }
            sgdocumentosListNew = attachedSgdocumentosListNew;
            sgtiposDocumentos.setSgdocumentosList(sgdocumentosListNew);
            sgtiposDocumentos = em.merge(sgtiposDocumentos);
            for (Sgdocumentos sgdocumentosListNewSgdocumentos : sgdocumentosListNew) {
                if (!sgdocumentosListOld.contains(sgdocumentosListNewSgdocumentos)) {
                    SgtiposDocumentos oldSgtiposDocumentosOfSgdocumentosListNewSgdocumentos = sgdocumentosListNewSgdocumentos.getSgtiposDocumentos();
                    sgdocumentosListNewSgdocumentos.setSgtiposDocumentos(sgtiposDocumentos);
                    sgdocumentosListNewSgdocumentos = em.merge(sgdocumentosListNewSgdocumentos);
                    if (oldSgtiposDocumentosOfSgdocumentosListNewSgdocumentos != null && !oldSgtiposDocumentosOfSgdocumentosListNewSgdocumentos.equals(sgtiposDocumentos)) {
                        oldSgtiposDocumentosOfSgdocumentosListNewSgdocumentos.getSgdocumentosList().remove(sgdocumentosListNewSgdocumentos);
                        oldSgtiposDocumentosOfSgdocumentosListNewSgdocumentos = em.merge(oldSgtiposDocumentosOfSgdocumentosListNewSgdocumentos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = sgtiposDocumentos.getIdtipoDocumento();
                if (findSgtiposDocumentos(id) == null) {
                    throw new NonexistentEntityException("The sgtiposDocumentos with id " + id + " no longer exists.");
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
            SgtiposDocumentos sgtiposDocumentos;
            try {
                sgtiposDocumentos = em.getReference(SgtiposDocumentos.class, id);
                sgtiposDocumentos.getIdtipoDocumento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgtiposDocumentos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Sgdocumentos> sgdocumentosListOrphanCheck = sgtiposDocumentos.getSgdocumentosList();
            for (Sgdocumentos sgdocumentosListOrphanCheckSgdocumentos : sgdocumentosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SgtiposDocumentos (" + sgtiposDocumentos + ") cannot be destroyed since the Sgdocumentos " + sgdocumentosListOrphanCheckSgdocumentos + " in its sgdocumentosList field has a non-nullable sgtiposDocumentos field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(sgtiposDocumentos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgtiposDocumentos> findSgtiposDocumentosEntities() {
        return findSgtiposDocumentosEntities(true, -1, -1);
    }

    public List<SgtiposDocumentos> findSgtiposDocumentosEntities(int maxResults, int firstResult) {
        return findSgtiposDocumentosEntities(false, maxResults, firstResult);
    }

    private List<SgtiposDocumentos> findSgtiposDocumentosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgtiposDocumentos.class));
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

    public SgtiposDocumentos findSgtiposDocumentos(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgtiposDocumentos.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgtiposDocumentosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgtiposDocumentos> rt = cq.from(SgtiposDocumentos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
