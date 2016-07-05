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
import sg.sistemas.entidades.SgdetalleDocumento;
import sg.sistemas.entidades.SgdetalleDocumentoPK;

/**
 *
 * @author Misael
 */
public class SgdetalleDocumentoJpaController implements Serializable {

    public SgdetalleDocumentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgdetalleDocumento sgdetalleDocumento) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (sgdetalleDocumento.getSgdetalleDocumentoPK() == null) {
            sgdetalleDocumento.setSgdetalleDocumentoPK(new SgdetalleDocumentoPK());
        }
        sgdetalleDocumento.getSgdetalleDocumentoPK().setIdtipoDocumento(sgdetalleDocumento.getSgdocumentos().getSgdocumentosPK().getIdtipoDocumento());
        sgdetalleDocumento.getSgdetalleDocumentoPK().setIddocumento(sgdetalleDocumento.getSgdocumentos().getSgdocumentosPK().getIddocumento());
        sgdetalleDocumento.getSgdetalleDocumentoPK().setIdsociedad(sgdetalleDocumento.getSgdocumentos().getSgdocumentosPK().getIdsociedad());
        List<String> illegalOrphanMessages = null;
        Sgdocumentos sgdocumentosOrphanCheck = sgdetalleDocumento.getSgdocumentos();
        if (sgdocumentosOrphanCheck != null) {
            SgdetalleDocumento oldSgdetalleDocumentoOfSgdocumentos = sgdocumentosOrphanCheck.getSgdetalleDocumento();
            if (oldSgdetalleDocumentoOfSgdocumentos != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Sgdocumentos " + sgdocumentosOrphanCheck + " already has an item of type SgdetalleDocumento whose sgdocumentos column cannot be null. Please make another selection for the sgdocumentos field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgdocumentos sgdocumentos = sgdetalleDocumento.getSgdocumentos();
            if (sgdocumentos != null) {
                sgdocumentos = em.getReference(sgdocumentos.getClass(), sgdocumentos.getSgdocumentosPK());
                sgdetalleDocumento.setSgdocumentos(sgdocumentos);
            }
            em.persist(sgdetalleDocumento);
            if (sgdocumentos != null) {
                sgdocumentos.setSgdetalleDocumento(sgdetalleDocumento);
                sgdocumentos = em.merge(sgdocumentos);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgdetalleDocumento(sgdetalleDocumento.getSgdetalleDocumentoPK()) != null) {
                throw new PreexistingEntityException("SgdetalleDocumento " + sgdetalleDocumento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgdetalleDocumento sgdetalleDocumento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        sgdetalleDocumento.getSgdetalleDocumentoPK().setIdtipoDocumento(sgdetalleDocumento.getSgdocumentos().getSgdocumentosPK().getIdtipoDocumento());
        sgdetalleDocumento.getSgdetalleDocumentoPK().setIddocumento(sgdetalleDocumento.getSgdocumentos().getSgdocumentosPK().getIddocumento());
        sgdetalleDocumento.getSgdetalleDocumentoPK().setIdsociedad(sgdetalleDocumento.getSgdocumentos().getSgdocumentosPK().getIdsociedad());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgdetalleDocumento persistentSgdetalleDocumento = em.find(SgdetalleDocumento.class, sgdetalleDocumento.getSgdetalleDocumentoPK());
            Sgdocumentos sgdocumentosOld = persistentSgdetalleDocumento.getSgdocumentos();
            Sgdocumentos sgdocumentosNew = sgdetalleDocumento.getSgdocumentos();
            List<String> illegalOrphanMessages = null;
            if (sgdocumentosNew != null && !sgdocumentosNew.equals(sgdocumentosOld)) {
                SgdetalleDocumento oldSgdetalleDocumentoOfSgdocumentos = sgdocumentosNew.getSgdetalleDocumento();
                if (oldSgdetalleDocumentoOfSgdocumentos != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Sgdocumentos " + sgdocumentosNew + " already has an item of type SgdetalleDocumento whose sgdocumentos column cannot be null. Please make another selection for the sgdocumentos field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sgdocumentosNew != null) {
                sgdocumentosNew = em.getReference(sgdocumentosNew.getClass(), sgdocumentosNew.getSgdocumentosPK());
                sgdetalleDocumento.setSgdocumentos(sgdocumentosNew);
            }
            sgdetalleDocumento = em.merge(sgdetalleDocumento);
            if (sgdocumentosOld != null && !sgdocumentosOld.equals(sgdocumentosNew)) {
                sgdocumentosOld.setSgdetalleDocumento(null);
                sgdocumentosOld = em.merge(sgdocumentosOld);
            }
            if (sgdocumentosNew != null && !sgdocumentosNew.equals(sgdocumentosOld)) {
                sgdocumentosNew.setSgdetalleDocumento(sgdetalleDocumento);
                sgdocumentosNew = em.merge(sgdocumentosNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgdetalleDocumentoPK id = sgdetalleDocumento.getSgdetalleDocumentoPK();
                if (findSgdetalleDocumento(id) == null) {
                    throw new NonexistentEntityException("The sgdetalleDocumento with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgdetalleDocumentoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgdetalleDocumento sgdetalleDocumento;
            try {
                sgdetalleDocumento = em.getReference(SgdetalleDocumento.class, id);
                sgdetalleDocumento.getSgdetalleDocumentoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgdetalleDocumento with id " + id + " no longer exists.", enfe);
            }
            Sgdocumentos sgdocumentos = sgdetalleDocumento.getSgdocumentos();
            if (sgdocumentos != null) {
                sgdocumentos.setSgdetalleDocumento(null);
                sgdocumentos = em.merge(sgdocumentos);
            }
            em.remove(sgdetalleDocumento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgdetalleDocumento> findSgdetalleDocumentoEntities() {
        return findSgdetalleDocumentoEntities(true, -1, -1);
    }

    public List<SgdetalleDocumento> findSgdetalleDocumentoEntities(int maxResults, int firstResult) {
        return findSgdetalleDocumentoEntities(false, maxResults, firstResult);
    }

    private List<SgdetalleDocumento> findSgdetalleDocumentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgdetalleDocumento.class));
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

    public SgdetalleDocumento findSgdetalleDocumento(SgdetalleDocumentoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgdetalleDocumento.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgdetalleDocumentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgdetalleDocumento> rt = cq.from(SgdetalleDocumento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
