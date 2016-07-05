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
import sg.sistemas.entidades.SgUsuario;
import sg.sistemas.entidades.Sgtcausa;
import sg.sistemas.entidades.SgaccionCorrectiva;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.Sgcausa;
import sg.sistemas.entidades.SgcausaPK;

/**
 *
 * @author Misael
 */
public class SgcausaJpaController implements Serializable {

    public SgcausaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sgcausa sgcausa) throws PreexistingEntityException, Exception {
        if (sgcausa.getSgcausaPK() == null) {
            sgcausa.setSgcausaPK(new SgcausaPK());
        }
        if (sgcausa.getSgaccionCorrectivaList() == null) {
            sgcausa.setSgaccionCorrectivaList(new ArrayList<SgaccionCorrectiva>());
        }
        sgcausa.getSgcausaPK().setNonc(sgcausa.getSgnc().getSgncPK().getNonc());
        sgcausa.getSgcausaPK().setIdsociedad(sgcausa.getSgnc().getSgncPK().getIdsociedad());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgnc sgnc = sgcausa.getSgnc();
            if (sgnc != null) {
                sgnc = em.getReference(sgnc.getClass(), sgnc.getSgncPK());
                sgcausa.setSgnc(sgnc);
            }
            SgUsuario codUsuario = sgcausa.getCodUsuario();
            if (codUsuario != null) {
                codUsuario = em.getReference(codUsuario.getClass(), codUsuario.getCodUsuario());
                sgcausa.setCodUsuario(codUsuario);
            }
            Sgtcausa idtcausa = sgcausa.getIdtcausa();
            if (idtcausa != null) {
                idtcausa = em.getReference(idtcausa.getClass(), idtcausa.getIdtcausa());
                sgcausa.setIdtcausa(idtcausa);
            }
            List<SgaccionCorrectiva> attachedSgaccionCorrectivaList = new ArrayList<SgaccionCorrectiva>();
            for (SgaccionCorrectiva sgaccionCorrectivaListSgaccionCorrectivaToAttach : sgcausa.getSgaccionCorrectivaList()) {
                sgaccionCorrectivaListSgaccionCorrectivaToAttach = em.getReference(sgaccionCorrectivaListSgaccionCorrectivaToAttach.getClass(), sgaccionCorrectivaListSgaccionCorrectivaToAttach.getSgaccionCorrectivaPK());
                attachedSgaccionCorrectivaList.add(sgaccionCorrectivaListSgaccionCorrectivaToAttach);
            }
            sgcausa.setSgaccionCorrectivaList(attachedSgaccionCorrectivaList);
            em.persist(sgcausa);
            if (sgnc != null) {
                sgnc.getSgcausaList().add(sgcausa);
                sgnc = em.merge(sgnc);
            }
            if (codUsuario != null) {
                codUsuario.getSgcausaList().add(sgcausa);
                codUsuario = em.merge(codUsuario);
            }
            if (idtcausa != null) {
                idtcausa.getSgcausaList().add(sgcausa);
                idtcausa = em.merge(idtcausa);
            }
            for (SgaccionCorrectiva sgaccionCorrectivaListSgaccionCorrectiva : sgcausa.getSgaccionCorrectivaList()) {
                Sgcausa oldSgcausaOfSgaccionCorrectivaListSgaccionCorrectiva = sgaccionCorrectivaListSgaccionCorrectiva.getSgcausa();
                sgaccionCorrectivaListSgaccionCorrectiva.setSgcausa(sgcausa);
                sgaccionCorrectivaListSgaccionCorrectiva = em.merge(sgaccionCorrectivaListSgaccionCorrectiva);
                if (oldSgcausaOfSgaccionCorrectivaListSgaccionCorrectiva != null) {
                    oldSgcausaOfSgaccionCorrectivaListSgaccionCorrectiva.getSgaccionCorrectivaList().remove(sgaccionCorrectivaListSgaccionCorrectiva);
                    oldSgcausaOfSgaccionCorrectivaListSgaccionCorrectiva = em.merge(oldSgcausaOfSgaccionCorrectivaListSgaccionCorrectiva);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgcausa(sgcausa.getSgcausaPK()) != null) {
                throw new PreexistingEntityException("Sgcausa " + sgcausa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sgcausa sgcausa) throws IllegalOrphanException, NonexistentEntityException, Exception {
        sgcausa.getSgcausaPK().setNonc(sgcausa.getSgnc().getSgncPK().getNonc());
        sgcausa.getSgcausaPK().setIdsociedad(sgcausa.getSgnc().getSgncPK().getIdsociedad());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgcausa persistentSgcausa = em.find(Sgcausa.class, sgcausa.getSgcausaPK());
            Sgnc sgncOld = persistentSgcausa.getSgnc();
            Sgnc sgncNew = sgcausa.getSgnc();
            SgUsuario codUsuarioOld = persistentSgcausa.getCodUsuario();
            SgUsuario codUsuarioNew = sgcausa.getCodUsuario();
            Sgtcausa idtcausaOld = persistentSgcausa.getIdtcausa();
            Sgtcausa idtcausaNew = sgcausa.getIdtcausa();
            List<SgaccionCorrectiva> sgaccionCorrectivaListOld = persistentSgcausa.getSgaccionCorrectivaList();
            List<SgaccionCorrectiva> sgaccionCorrectivaListNew = sgcausa.getSgaccionCorrectivaList();
            List<String> illegalOrphanMessages = null;
            for (SgaccionCorrectiva sgaccionCorrectivaListOldSgaccionCorrectiva : sgaccionCorrectivaListOld) {
                if (!sgaccionCorrectivaListNew.contains(sgaccionCorrectivaListOldSgaccionCorrectiva)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SgaccionCorrectiva " + sgaccionCorrectivaListOldSgaccionCorrectiva + " since its sgcausa field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sgncNew != null) {
                sgncNew = em.getReference(sgncNew.getClass(), sgncNew.getSgncPK());
                sgcausa.setSgnc(sgncNew);
            }
            if (codUsuarioNew != null) {
                codUsuarioNew = em.getReference(codUsuarioNew.getClass(), codUsuarioNew.getCodUsuario());
                sgcausa.setCodUsuario(codUsuarioNew);
            }
            if (idtcausaNew != null) {
                idtcausaNew = em.getReference(idtcausaNew.getClass(), idtcausaNew.getIdtcausa());
                sgcausa.setIdtcausa(idtcausaNew);
            }
            List<SgaccionCorrectiva> attachedSgaccionCorrectivaListNew = new ArrayList<SgaccionCorrectiva>();
            for (SgaccionCorrectiva sgaccionCorrectivaListNewSgaccionCorrectivaToAttach : sgaccionCorrectivaListNew) {
                sgaccionCorrectivaListNewSgaccionCorrectivaToAttach = em.getReference(sgaccionCorrectivaListNewSgaccionCorrectivaToAttach.getClass(), sgaccionCorrectivaListNewSgaccionCorrectivaToAttach.getSgaccionCorrectivaPK());
                attachedSgaccionCorrectivaListNew.add(sgaccionCorrectivaListNewSgaccionCorrectivaToAttach);
            }
            sgaccionCorrectivaListNew = attachedSgaccionCorrectivaListNew;
            sgcausa.setSgaccionCorrectivaList(sgaccionCorrectivaListNew);
            sgcausa = em.merge(sgcausa);
            if (sgncOld != null && !sgncOld.equals(sgncNew)) {
                sgncOld.getSgcausaList().remove(sgcausa);
                sgncOld = em.merge(sgncOld);
            }
            if (sgncNew != null && !sgncNew.equals(sgncOld)) {
                sgncNew.getSgcausaList().add(sgcausa);
                sgncNew = em.merge(sgncNew);
            }
            if (codUsuarioOld != null && !codUsuarioOld.equals(codUsuarioNew)) {
                codUsuarioOld.getSgcausaList().remove(sgcausa);
                codUsuarioOld = em.merge(codUsuarioOld);
            }
            if (codUsuarioNew != null && !codUsuarioNew.equals(codUsuarioOld)) {
                codUsuarioNew.getSgcausaList().add(sgcausa);
                codUsuarioNew = em.merge(codUsuarioNew);
            }
            if (idtcausaOld != null && !idtcausaOld.equals(idtcausaNew)) {
                idtcausaOld.getSgcausaList().remove(sgcausa);
                idtcausaOld = em.merge(idtcausaOld);
            }
            if (idtcausaNew != null && !idtcausaNew.equals(idtcausaOld)) {
                idtcausaNew.getSgcausaList().add(sgcausa);
                idtcausaNew = em.merge(idtcausaNew);
            }
            for (SgaccionCorrectiva sgaccionCorrectivaListNewSgaccionCorrectiva : sgaccionCorrectivaListNew) {
                if (!sgaccionCorrectivaListOld.contains(sgaccionCorrectivaListNewSgaccionCorrectiva)) {
                    Sgcausa oldSgcausaOfSgaccionCorrectivaListNewSgaccionCorrectiva = sgaccionCorrectivaListNewSgaccionCorrectiva.getSgcausa();
                    sgaccionCorrectivaListNewSgaccionCorrectiva.setSgcausa(sgcausa);
                    sgaccionCorrectivaListNewSgaccionCorrectiva = em.merge(sgaccionCorrectivaListNewSgaccionCorrectiva);
                    if (oldSgcausaOfSgaccionCorrectivaListNewSgaccionCorrectiva != null && !oldSgcausaOfSgaccionCorrectivaListNewSgaccionCorrectiva.equals(sgcausa)) {
                        oldSgcausaOfSgaccionCorrectivaListNewSgaccionCorrectiva.getSgaccionCorrectivaList().remove(sgaccionCorrectivaListNewSgaccionCorrectiva);
                        oldSgcausaOfSgaccionCorrectivaListNewSgaccionCorrectiva = em.merge(oldSgcausaOfSgaccionCorrectivaListNewSgaccionCorrectiva);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgcausaPK id = sgcausa.getSgcausaPK();
                if (findSgcausa(id) == null) {
                    throw new NonexistentEntityException("The sgcausa with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgcausaPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgcausa sgcausa;
            try {
                sgcausa = em.getReference(Sgcausa.class, id);
                sgcausa.getSgcausaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgcausa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SgaccionCorrectiva> sgaccionCorrectivaListOrphanCheck = sgcausa.getSgaccionCorrectivaList();
            for (SgaccionCorrectiva sgaccionCorrectivaListOrphanCheckSgaccionCorrectiva : sgaccionCorrectivaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sgcausa (" + sgcausa + ") cannot be destroyed since the SgaccionCorrectiva " + sgaccionCorrectivaListOrphanCheckSgaccionCorrectiva + " in its sgaccionCorrectivaList field has a non-nullable sgcausa field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Sgnc sgnc = sgcausa.getSgnc();
            if (sgnc != null) {
                sgnc.getSgcausaList().remove(sgcausa);
                sgnc = em.merge(sgnc);
            }
            SgUsuario codUsuario = sgcausa.getCodUsuario();
            if (codUsuario != null) {
                codUsuario.getSgcausaList().remove(sgcausa);
                codUsuario = em.merge(codUsuario);
            }
            Sgtcausa idtcausa = sgcausa.getIdtcausa();
            if (idtcausa != null) {
                idtcausa.getSgcausaList().remove(sgcausa);
                idtcausa = em.merge(idtcausa);
            }
            em.remove(sgcausa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sgcausa> findSgcausaEntities() {
        return findSgcausaEntities(true, -1, -1);
    }

    public List<Sgcausa> findSgcausaEntities(int maxResults, int firstResult) {
        return findSgcausaEntities(false, maxResults, firstResult);
    }

    private List<Sgcausa> findSgcausaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sgcausa.class));
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

    public Sgcausa findSgcausa(SgcausaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sgcausa.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgcausaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sgcausa> rt = cq.from(Sgcausa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
