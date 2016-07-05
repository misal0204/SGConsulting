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
import sg.sistemas.entidades.SgdetalleDocumento;
import sg.sistemas.entidades.SgtiposDocumentos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.Sgdocumentos;
import sg.sistemas.entidades.SgdocumentosPK;

/**
 *
 * @author Misael
 */
public class SgdocumentosJpaController implements Serializable {

    public SgdocumentosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sgdocumentos sgdocumentos) throws PreexistingEntityException, Exception {
        if (sgdocumentos.getSgdocumentosPK() == null) {
            sgdocumentos.setSgdocumentosPK(new SgdocumentosPK());
        }
        sgdocumentos.getSgdocumentosPK().setIdtipoDocumento(sgdocumentos.getSgtiposDocumentos().getIdtipoDocumento());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgdetalleDocumento sgdetalleDocumento = sgdocumentos.getSgdetalleDocumento();
            if (sgdetalleDocumento != null) {
                sgdetalleDocumento = em.getReference(sgdetalleDocumento.getClass(), sgdetalleDocumento.getSgdetalleDocumentoPK());
                sgdocumentos.setSgdetalleDocumento(sgdetalleDocumento);
            }
            SgtiposDocumentos sgtiposDocumentos = sgdocumentos.getSgtiposDocumentos();
            if (sgtiposDocumentos != null) {
                sgtiposDocumentos = em.getReference(sgtiposDocumentos.getClass(), sgtiposDocumentos.getIdtipoDocumento());
                sgdocumentos.setSgtiposDocumentos(sgtiposDocumentos);
            }
            em.persist(sgdocumentos);
            if (sgdetalleDocumento != null) {
                Sgdocumentos oldSgdocumentosOfSgdetalleDocumento = sgdetalleDocumento.getSgdocumentos();
                if (oldSgdocumentosOfSgdetalleDocumento != null) {
                    oldSgdocumentosOfSgdetalleDocumento.setSgdetalleDocumento(null);
                    oldSgdocumentosOfSgdetalleDocumento = em.merge(oldSgdocumentosOfSgdetalleDocumento);
                }
                sgdetalleDocumento.setSgdocumentos(sgdocumentos);
                sgdetalleDocumento = em.merge(sgdetalleDocumento);
            }
            if (sgtiposDocumentos != null) {
                sgtiposDocumentos.getSgdocumentosList().add(sgdocumentos);
                sgtiposDocumentos = em.merge(sgtiposDocumentos);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgdocumentos(sgdocumentos.getSgdocumentosPK()) != null) {
                throw new PreexistingEntityException("Sgdocumentos " + sgdocumentos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sgdocumentos sgdocumentos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        sgdocumentos.getSgdocumentosPK().setIdtipoDocumento(sgdocumentos.getSgtiposDocumentos().getIdtipoDocumento());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgdocumentos persistentSgdocumentos = em.find(Sgdocumentos.class, sgdocumentos.getSgdocumentosPK());
            SgdetalleDocumento sgdetalleDocumentoOld = persistentSgdocumentos.getSgdetalleDocumento();
            SgdetalleDocumento sgdetalleDocumentoNew = sgdocumentos.getSgdetalleDocumento();
            SgtiposDocumentos sgtiposDocumentosOld = persistentSgdocumentos.getSgtiposDocumentos();
            SgtiposDocumentos sgtiposDocumentosNew = sgdocumentos.getSgtiposDocumentos();
            List<String> illegalOrphanMessages = null;
            if (sgdetalleDocumentoOld != null && !sgdetalleDocumentoOld.equals(sgdetalleDocumentoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain SgdetalleDocumento " + sgdetalleDocumentoOld + " since its sgdocumentos field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sgdetalleDocumentoNew != null) {
                sgdetalleDocumentoNew = em.getReference(sgdetalleDocumentoNew.getClass(), sgdetalleDocumentoNew.getSgdetalleDocumentoPK());
                sgdocumentos.setSgdetalleDocumento(sgdetalleDocumentoNew);
            }
            if (sgtiposDocumentosNew != null) {
                sgtiposDocumentosNew = em.getReference(sgtiposDocumentosNew.getClass(), sgtiposDocumentosNew.getIdtipoDocumento());
                sgdocumentos.setSgtiposDocumentos(sgtiposDocumentosNew);
            }
            sgdocumentos = em.merge(sgdocumentos);
            if (sgdetalleDocumentoNew != null && !sgdetalleDocumentoNew.equals(sgdetalleDocumentoOld)) {
                Sgdocumentos oldSgdocumentosOfSgdetalleDocumento = sgdetalleDocumentoNew.getSgdocumentos();
                if (oldSgdocumentosOfSgdetalleDocumento != null) {
                    oldSgdocumentosOfSgdetalleDocumento.setSgdetalleDocumento(null);
                    oldSgdocumentosOfSgdetalleDocumento = em.merge(oldSgdocumentosOfSgdetalleDocumento);
                }
                sgdetalleDocumentoNew.setSgdocumentos(sgdocumentos);
                sgdetalleDocumentoNew = em.merge(sgdetalleDocumentoNew);
            }
            if (sgtiposDocumentosOld != null && !sgtiposDocumentosOld.equals(sgtiposDocumentosNew)) {
                sgtiposDocumentosOld.getSgdocumentosList().remove(sgdocumentos);
                sgtiposDocumentosOld = em.merge(sgtiposDocumentosOld);
            }
            if (sgtiposDocumentosNew != null && !sgtiposDocumentosNew.equals(sgtiposDocumentosOld)) {
                sgtiposDocumentosNew.getSgdocumentosList().add(sgdocumentos);
                sgtiposDocumentosNew = em.merge(sgtiposDocumentosNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgdocumentosPK id = sgdocumentos.getSgdocumentosPK();
                if (findSgdocumentos(id) == null) {
                    throw new NonexistentEntityException("The sgdocumentos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgdocumentosPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgdocumentos sgdocumentos;
            try {
                sgdocumentos = em.getReference(Sgdocumentos.class, id);
                sgdocumentos.getSgdocumentosPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgdocumentos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            SgdetalleDocumento sgdetalleDocumentoOrphanCheck = sgdocumentos.getSgdetalleDocumento();
            if (sgdetalleDocumentoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sgdocumentos (" + sgdocumentos + ") cannot be destroyed since the SgdetalleDocumento " + sgdetalleDocumentoOrphanCheck + " in its sgdetalleDocumento field has a non-nullable sgdocumentos field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            SgtiposDocumentos sgtiposDocumentos = sgdocumentos.getSgtiposDocumentos();
            if (sgtiposDocumentos != null) {
                sgtiposDocumentos.getSgdocumentosList().remove(sgdocumentos);
                sgtiposDocumentos = em.merge(sgtiposDocumentos);
            }
            em.remove(sgdocumentos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sgdocumentos> findSgdocumentosEntities() {
        return findSgdocumentosEntities(true, -1, -1);
    }

    public List<Sgdocumentos> findSgdocumentosEntities(int maxResults, int firstResult) {
        return findSgdocumentosEntities(false, maxResults, firstResult);
    }

    private List<Sgdocumentos> findSgdocumentosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sgdocumentos.class));
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

    public Sgdocumentos findSgdocumentos(SgdocumentosPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sgdocumentos.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgdocumentosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sgdocumentos> rt = cq.from(Sgdocumentos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
