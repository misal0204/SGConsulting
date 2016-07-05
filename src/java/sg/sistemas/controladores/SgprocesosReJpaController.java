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
import sg.sistemas.entidades.Sgcliente;
import sg.sistemas.entidades.Sgmaterial;
import sg.sistemas.entidades.Sgproveedores;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgprocesosRe;
import sg.sistemas.entidades.SgprocesosRePK;

/**
 *
 * @author Misael
 */
public class SgprocesosReJpaController implements Serializable {

    public SgprocesosReJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgprocesosRe sgprocesosRe) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (sgprocesosRe.getSgprocesosRePK() == null) {
            sgprocesosRe.setSgprocesosRePK(new SgprocesosRePK());
        }
        sgprocesosRe.getSgprocesosRePK().setIdsociedad(sgprocesosRe.getSgproveedores().getSgproveedoresPK().getIdsociedad());
        sgprocesosRe.getSgprocesosRePK().setNonc(sgprocesosRe.getSgnc().getSgncPK().getNonc());
        List<String> illegalOrphanMessages = null;
        Sgnc sgncOrphanCheck = sgprocesosRe.getSgnc();
        if (sgncOrphanCheck != null) {
            SgprocesosRe oldSgprocesosReOfSgnc = sgncOrphanCheck.getSgprocesosRe();
            if (oldSgprocesosReOfSgnc != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Sgnc " + sgncOrphanCheck + " already has an item of type SgprocesosRe whose sgnc column cannot be null. Please make another selection for the sgnc field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgnc sgnc = sgprocesosRe.getSgnc();
            if (sgnc != null) {
                sgnc = em.getReference(sgnc.getClass(), sgnc.getSgncPK());
                sgprocesosRe.setSgnc(sgnc);
            }
            Sgcliente sgcliente = sgprocesosRe.getSgcliente();
            if (sgcliente != null) {
                sgcliente = em.getReference(sgcliente.getClass(), sgcliente.getSgclientePK());
                sgprocesosRe.setSgcliente(sgcliente);
            }
            Sgmaterial idmaterial = sgprocesosRe.getIdmaterial();
            if (idmaterial != null) {
                idmaterial = em.getReference(idmaterial.getClass(), idmaterial.getIdmaterial());
                sgprocesosRe.setIdmaterial(idmaterial);
            }
            Sgproveedores sgproveedores = sgprocesosRe.getSgproveedores();
            if (sgproveedores != null) {
                sgproveedores = em.getReference(sgproveedores.getClass(), sgproveedores.getSgproveedoresPK());
                sgprocesosRe.setSgproveedores(sgproveedores);
            }
            em.persist(sgprocesosRe);
            if (sgnc != null) {
                sgnc.setSgprocesosRe(sgprocesosRe);
                sgnc = em.merge(sgnc);
            }
            if (sgcliente != null) {
                sgcliente.getSgprocesosReList().add(sgprocesosRe);
                sgcliente = em.merge(sgcliente);
            }
            if (idmaterial != null) {
                idmaterial.getSgprocesosReList().add(sgprocesosRe);
                idmaterial = em.merge(idmaterial);
            }
            if (sgproveedores != null) {
                sgproveedores.getSgprocesosReList().add(sgprocesosRe);
                sgproveedores = em.merge(sgproveedores);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgprocesosRe(sgprocesosRe.getSgprocesosRePK()) != null) {
                throw new PreexistingEntityException("SgprocesosRe " + sgprocesosRe + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgprocesosRe sgprocesosRe) throws IllegalOrphanException, NonexistentEntityException, Exception {
        sgprocesosRe.getSgprocesosRePK().setIdsociedad(sgprocesosRe.getSgproveedores().getSgproveedoresPK().getIdsociedad());
        sgprocesosRe.getSgprocesosRePK().setNonc(sgprocesosRe.getSgnc().getSgncPK().getNonc());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgprocesosRe persistentSgprocesosRe = em.find(SgprocesosRe.class, sgprocesosRe.getSgprocesosRePK());
            Sgnc sgncOld = persistentSgprocesosRe.getSgnc();
            Sgnc sgncNew = sgprocesosRe.getSgnc();
            Sgcliente sgclienteOld = persistentSgprocesosRe.getSgcliente();
            Sgcliente sgclienteNew = sgprocesosRe.getSgcliente();
            Sgmaterial idmaterialOld = persistentSgprocesosRe.getIdmaterial();
            Sgmaterial idmaterialNew = sgprocesosRe.getIdmaterial();
            Sgproveedores sgproveedoresOld = persistentSgprocesosRe.getSgproveedores();
            Sgproveedores sgproveedoresNew = sgprocesosRe.getSgproveedores();
            List<String> illegalOrphanMessages = null;
            if (sgncNew != null && !sgncNew.equals(sgncOld)) {
                SgprocesosRe oldSgprocesosReOfSgnc = sgncNew.getSgprocesosRe();
                if (oldSgprocesosReOfSgnc != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Sgnc " + sgncNew + " already has an item of type SgprocesosRe whose sgnc column cannot be null. Please make another selection for the sgnc field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sgncNew != null) {
                sgncNew = em.getReference(sgncNew.getClass(), sgncNew.getSgncPK());
                sgprocesosRe.setSgnc(sgncNew);
            }
            if (sgclienteNew != null) {
                sgclienteNew = em.getReference(sgclienteNew.getClass(), sgclienteNew.getSgclientePK());
                sgprocesosRe.setSgcliente(sgclienteNew);
            }
            if (idmaterialNew != null) {
                idmaterialNew = em.getReference(idmaterialNew.getClass(), idmaterialNew.getIdmaterial());
                sgprocesosRe.setIdmaterial(idmaterialNew);
            }
            if (sgproveedoresNew != null) {
                sgproveedoresNew = em.getReference(sgproveedoresNew.getClass(), sgproveedoresNew.getSgproveedoresPK());
                sgprocesosRe.setSgproveedores(sgproveedoresNew);
            }
            sgprocesosRe = em.merge(sgprocesosRe);
            if (sgncOld != null && !sgncOld.equals(sgncNew)) {
                sgncOld.setSgprocesosRe(null);
                sgncOld = em.merge(sgncOld);
            }
            if (sgncNew != null && !sgncNew.equals(sgncOld)) {
                sgncNew.setSgprocesosRe(sgprocesosRe);
                sgncNew = em.merge(sgncNew);
            }
            if (sgclienteOld != null && !sgclienteOld.equals(sgclienteNew)) {
                sgclienteOld.getSgprocesosReList().remove(sgprocesosRe);
                sgclienteOld = em.merge(sgclienteOld);
            }
            if (sgclienteNew != null && !sgclienteNew.equals(sgclienteOld)) {
                sgclienteNew.getSgprocesosReList().add(sgprocesosRe);
                sgclienteNew = em.merge(sgclienteNew);
            }
            if (idmaterialOld != null && !idmaterialOld.equals(idmaterialNew)) {
                idmaterialOld.getSgprocesosReList().remove(sgprocesosRe);
                idmaterialOld = em.merge(idmaterialOld);
            }
            if (idmaterialNew != null && !idmaterialNew.equals(idmaterialOld)) {
                idmaterialNew.getSgprocesosReList().add(sgprocesosRe);
                idmaterialNew = em.merge(idmaterialNew);
            }
            if (sgproveedoresOld != null && !sgproveedoresOld.equals(sgproveedoresNew)) {
                sgproveedoresOld.getSgprocesosReList().remove(sgprocesosRe);
                sgproveedoresOld = em.merge(sgproveedoresOld);
            }
            if (sgproveedoresNew != null && !sgproveedoresNew.equals(sgproveedoresOld)) {
                sgproveedoresNew.getSgprocesosReList().add(sgprocesosRe);
                sgproveedoresNew = em.merge(sgproveedoresNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgprocesosRePK id = sgprocesosRe.getSgprocesosRePK();
                if (findSgprocesosRe(id) == null) {
                    throw new NonexistentEntityException("The sgprocesosRe with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgprocesosRePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgprocesosRe sgprocesosRe;
            try {
                sgprocesosRe = em.getReference(SgprocesosRe.class, id);
                sgprocesosRe.getSgprocesosRePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgprocesosRe with id " + id + " no longer exists.", enfe);
            }
            Sgnc sgnc = sgprocesosRe.getSgnc();
            if (sgnc != null) {
                sgnc.setSgprocesosRe(null);
                sgnc = em.merge(sgnc);
            }
            Sgcliente sgcliente = sgprocesosRe.getSgcliente();
            if (sgcliente != null) {
                sgcliente.getSgprocesosReList().remove(sgprocesosRe);
                sgcliente = em.merge(sgcliente);
            }
            Sgmaterial idmaterial = sgprocesosRe.getIdmaterial();
            if (idmaterial != null) {
                idmaterial.getSgprocesosReList().remove(sgprocesosRe);
                idmaterial = em.merge(idmaterial);
            }
            Sgproveedores sgproveedores = sgprocesosRe.getSgproveedores();
            if (sgproveedores != null) {
                sgproveedores.getSgprocesosReList().remove(sgprocesosRe);
                sgproveedores = em.merge(sgproveedores);
            }
            em.remove(sgprocesosRe);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgprocesosRe> findSgprocesosReEntities() {
        return findSgprocesosReEntities(true, -1, -1);
    }

    public List<SgprocesosRe> findSgprocesosReEntities(int maxResults, int firstResult) {
        return findSgprocesosReEntities(false, maxResults, firstResult);
    }

    private List<SgprocesosRe> findSgprocesosReEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgprocesosRe.class));
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

    public SgprocesosRe findSgprocesosRe(SgprocesosRePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgprocesosRe.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgprocesosReCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgprocesosRe> rt = cq.from(SgprocesosRe.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
