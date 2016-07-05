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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.Sgdepartamento;
import sg.sistemas.entidades.Sgnc;

/**
 *
 * @author Misael
 */
public class SgdepartamentoJpaController implements Serializable {

    public SgdepartamentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sgdepartamento sgdepartamento) throws PreexistingEntityException, Exception {
        if (sgdepartamento.getSgUsuarioList() == null) {
            sgdepartamento.setSgUsuarioList(new ArrayList<SgUsuario>());
        }
        if (sgdepartamento.getSgncList() == null) {
            sgdepartamento.setSgncList(new ArrayList<Sgnc>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SgUsuario> attachedSgUsuarioList = new ArrayList<SgUsuario>();
            for (SgUsuario sgUsuarioListSgUsuarioToAttach : sgdepartamento.getSgUsuarioList()) {
                sgUsuarioListSgUsuarioToAttach = em.getReference(sgUsuarioListSgUsuarioToAttach.getClass(), sgUsuarioListSgUsuarioToAttach.getCodUsuario());
                attachedSgUsuarioList.add(sgUsuarioListSgUsuarioToAttach);
            }
            sgdepartamento.setSgUsuarioList(attachedSgUsuarioList);
            List<Sgnc> attachedSgncList = new ArrayList<Sgnc>();
            for (Sgnc sgncListSgncToAttach : sgdepartamento.getSgncList()) {
                sgncListSgncToAttach = em.getReference(sgncListSgncToAttach.getClass(), sgncListSgncToAttach.getSgncPK());
                attachedSgncList.add(sgncListSgncToAttach);
            }
            sgdepartamento.setSgncList(attachedSgncList);
            em.persist(sgdepartamento);
            for (SgUsuario sgUsuarioListSgUsuario : sgdepartamento.getSgUsuarioList()) {
                sgUsuarioListSgUsuario.getSgdepartamentoList().add(sgdepartamento);
                sgUsuarioListSgUsuario = em.merge(sgUsuarioListSgUsuario);
            }
            for (Sgnc sgncListSgnc : sgdepartamento.getSgncList()) {
                sgncListSgnc.getSgdepartamentoList().add(sgdepartamento);
                sgncListSgnc = em.merge(sgncListSgnc);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgdepartamento(sgdepartamento.getIddept()) != null) {
                throw new PreexistingEntityException("Sgdepartamento " + sgdepartamento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sgdepartamento sgdepartamento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgdepartamento persistentSgdepartamento = em.find(Sgdepartamento.class, sgdepartamento.getIddept());
            List<SgUsuario> sgUsuarioListOld = persistentSgdepartamento.getSgUsuarioList();
            List<SgUsuario> sgUsuarioListNew = sgdepartamento.getSgUsuarioList();
            List<Sgnc> sgncListOld = persistentSgdepartamento.getSgncList();
            List<Sgnc> sgncListNew = sgdepartamento.getSgncList();
            List<SgUsuario> attachedSgUsuarioListNew = new ArrayList<SgUsuario>();
            for (SgUsuario sgUsuarioListNewSgUsuarioToAttach : sgUsuarioListNew) {
                sgUsuarioListNewSgUsuarioToAttach = em.getReference(sgUsuarioListNewSgUsuarioToAttach.getClass(), sgUsuarioListNewSgUsuarioToAttach.getCodUsuario());
                attachedSgUsuarioListNew.add(sgUsuarioListNewSgUsuarioToAttach);
            }
            sgUsuarioListNew = attachedSgUsuarioListNew;
            sgdepartamento.setSgUsuarioList(sgUsuarioListNew);
            List<Sgnc> attachedSgncListNew = new ArrayList<Sgnc>();
            for (Sgnc sgncListNewSgncToAttach : sgncListNew) {
                sgncListNewSgncToAttach = em.getReference(sgncListNewSgncToAttach.getClass(), sgncListNewSgncToAttach.getSgncPK());
                attachedSgncListNew.add(sgncListNewSgncToAttach);
            }
            sgncListNew = attachedSgncListNew;
            sgdepartamento.setSgncList(sgncListNew);
            sgdepartamento = em.merge(sgdepartamento);
            for (SgUsuario sgUsuarioListOldSgUsuario : sgUsuarioListOld) {
                if (!sgUsuarioListNew.contains(sgUsuarioListOldSgUsuario)) {
                    sgUsuarioListOldSgUsuario.getSgdepartamentoList().remove(sgdepartamento);
                    sgUsuarioListOldSgUsuario = em.merge(sgUsuarioListOldSgUsuario);
                }
            }
            for (SgUsuario sgUsuarioListNewSgUsuario : sgUsuarioListNew) {
                if (!sgUsuarioListOld.contains(sgUsuarioListNewSgUsuario)) {
                    sgUsuarioListNewSgUsuario.getSgdepartamentoList().add(sgdepartamento);
                    sgUsuarioListNewSgUsuario = em.merge(sgUsuarioListNewSgUsuario);
                }
            }
            for (Sgnc sgncListOldSgnc : sgncListOld) {
                if (!sgncListNew.contains(sgncListOldSgnc)) {
                    sgncListOldSgnc.getSgdepartamentoList().remove(sgdepartamento);
                    sgncListOldSgnc = em.merge(sgncListOldSgnc);
                }
            }
            for (Sgnc sgncListNewSgnc : sgncListNew) {
                if (!sgncListOld.contains(sgncListNewSgnc)) {
                    sgncListNewSgnc.getSgdepartamentoList().add(sgdepartamento);
                    sgncListNewSgnc = em.merge(sgncListNewSgnc);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = sgdepartamento.getIddept();
                if (findSgdepartamento(id) == null) {
                    throw new NonexistentEntityException("The sgdepartamento with id " + id + " no longer exists.");
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
            Sgdepartamento sgdepartamento;
            try {
                sgdepartamento = em.getReference(Sgdepartamento.class, id);
                sgdepartamento.getIddept();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgdepartamento with id " + id + " no longer exists.", enfe);
            }
            List<SgUsuario> sgUsuarioList = sgdepartamento.getSgUsuarioList();
            for (SgUsuario sgUsuarioListSgUsuario : sgUsuarioList) {
                sgUsuarioListSgUsuario.getSgdepartamentoList().remove(sgdepartamento);
                sgUsuarioListSgUsuario = em.merge(sgUsuarioListSgUsuario);
            }
            List<Sgnc> sgncList = sgdepartamento.getSgncList();
            for (Sgnc sgncListSgnc : sgncList) {
                sgncListSgnc.getSgdepartamentoList().remove(sgdepartamento);
                sgncListSgnc = em.merge(sgncListSgnc);
            }
            em.remove(sgdepartamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sgdepartamento> findSgdepartamentoEntities() {
        return findSgdepartamentoEntities(true, -1, -1);
    }

    public List<Sgdepartamento> findSgdepartamentoEntities(int maxResults, int firstResult) {
        return findSgdepartamentoEntities(false, maxResults, firstResult);
    }

    private List<Sgdepartamento> findSgdepartamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sgdepartamento.class));
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

    public Sgdepartamento findSgdepartamento(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sgdepartamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgdepartamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sgdepartamento> rt = cq.from(Sgdepartamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
