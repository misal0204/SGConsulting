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
import sg.sistemas.entidades.SgprobabilidadRiesgo;
import sg.sistemas.entidades.SgtipoRiesgo;
import sg.sistemas.entidades.SgUsuario;
import sg.sistemas.entidades.SgprocesoRiesgo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.Sgriesgo;
import sg.sistemas.entidades.SgriesgoPK;

/**
 *
 * @author Misael
 */
public class SgriesgoJpaController implements Serializable {

    public SgriesgoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sgriesgo sgriesgo) throws PreexistingEntityException, Exception {
        if (sgriesgo.getSgriesgoPK() == null) {
            sgriesgo.setSgriesgoPK(new SgriesgoPK());
        }
        if (sgriesgo.getSgprocesoRiesgoList() == null) {
            sgriesgo.setSgprocesoRiesgoList(new ArrayList<SgprocesoRiesgo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgprobabilidadRiesgo idprobabilidad = sgriesgo.getIdprobabilidad();
            if (idprobabilidad != null) {
                idprobabilidad = em.getReference(idprobabilidad.getClass(), idprobabilidad.getIdprobabilidad());
                sgriesgo.setIdprobabilidad(idprobabilidad);
            }
            SgtipoRiesgo idtipoRiesgo = sgriesgo.getIdtipoRiesgo();
            if (idtipoRiesgo != null) {
                idtipoRiesgo = em.getReference(idtipoRiesgo.getClass(), idtipoRiesgo.getIdtipoRiesgo());
                sgriesgo.setIdtipoRiesgo(idtipoRiesgo);
            }
            SgUsuario codUsuario = sgriesgo.getCodUsuario();
            if (codUsuario != null) {
                codUsuario = em.getReference(codUsuario.getClass(), codUsuario.getCodUsuario());
                sgriesgo.setCodUsuario(codUsuario);
            }
            List<SgprocesoRiesgo> attachedSgprocesoRiesgoList = new ArrayList<SgprocesoRiesgo>();
            for (SgprocesoRiesgo sgprocesoRiesgoListSgprocesoRiesgoToAttach : sgriesgo.getSgprocesoRiesgoList()) {
                sgprocesoRiesgoListSgprocesoRiesgoToAttach = em.getReference(sgprocesoRiesgoListSgprocesoRiesgoToAttach.getClass(), sgprocesoRiesgoListSgprocesoRiesgoToAttach.getSgprocesoRiesgoPK());
                attachedSgprocesoRiesgoList.add(sgprocesoRiesgoListSgprocesoRiesgoToAttach);
            }
            sgriesgo.setSgprocesoRiesgoList(attachedSgprocesoRiesgoList);
            em.persist(sgriesgo);
            if (idprobabilidad != null) {
                idprobabilidad.getSgriesgoList().add(sgriesgo);
                idprobabilidad = em.merge(idprobabilidad);
            }
            if (idtipoRiesgo != null) {
                idtipoRiesgo.getSgriesgoList().add(sgriesgo);
                idtipoRiesgo = em.merge(idtipoRiesgo);
            }
            if (codUsuario != null) {
                codUsuario.getSgriesgoList().add(sgriesgo);
                codUsuario = em.merge(codUsuario);
            }
            for (SgprocesoRiesgo sgprocesoRiesgoListSgprocesoRiesgo : sgriesgo.getSgprocesoRiesgoList()) {
                Sgriesgo oldSgriesgoOfSgprocesoRiesgoListSgprocesoRiesgo = sgprocesoRiesgoListSgprocesoRiesgo.getSgriesgo();
                sgprocesoRiesgoListSgprocesoRiesgo.setSgriesgo(sgriesgo);
                sgprocesoRiesgoListSgprocesoRiesgo = em.merge(sgprocesoRiesgoListSgprocesoRiesgo);
                if (oldSgriesgoOfSgprocesoRiesgoListSgprocesoRiesgo != null) {
                    oldSgriesgoOfSgprocesoRiesgoListSgprocesoRiesgo.getSgprocesoRiesgoList().remove(sgprocesoRiesgoListSgprocesoRiesgo);
                    oldSgriesgoOfSgprocesoRiesgoListSgprocesoRiesgo = em.merge(oldSgriesgoOfSgprocesoRiesgoListSgprocesoRiesgo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgriesgo(sgriesgo.getSgriesgoPK()) != null) {
                throw new PreexistingEntityException("Sgriesgo " + sgriesgo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sgriesgo sgriesgo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgriesgo persistentSgriesgo = em.find(Sgriesgo.class, sgriesgo.getSgriesgoPK());
            SgprobabilidadRiesgo idprobabilidadOld = persistentSgriesgo.getIdprobabilidad();
            SgprobabilidadRiesgo idprobabilidadNew = sgriesgo.getIdprobabilidad();
            SgtipoRiesgo idtipoRiesgoOld = persistentSgriesgo.getIdtipoRiesgo();
            SgtipoRiesgo idtipoRiesgoNew = sgriesgo.getIdtipoRiesgo();
            SgUsuario codUsuarioOld = persistentSgriesgo.getCodUsuario();
            SgUsuario codUsuarioNew = sgriesgo.getCodUsuario();
            List<SgprocesoRiesgo> sgprocesoRiesgoListOld = persistentSgriesgo.getSgprocesoRiesgoList();
            List<SgprocesoRiesgo> sgprocesoRiesgoListNew = sgriesgo.getSgprocesoRiesgoList();
            List<String> illegalOrphanMessages = null;
            for (SgprocesoRiesgo sgprocesoRiesgoListOldSgprocesoRiesgo : sgprocesoRiesgoListOld) {
                if (!sgprocesoRiesgoListNew.contains(sgprocesoRiesgoListOldSgprocesoRiesgo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SgprocesoRiesgo " + sgprocesoRiesgoListOldSgprocesoRiesgo + " since its sgriesgo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idprobabilidadNew != null) {
                idprobabilidadNew = em.getReference(idprobabilidadNew.getClass(), idprobabilidadNew.getIdprobabilidad());
                sgriesgo.setIdprobabilidad(idprobabilidadNew);
            }
            if (idtipoRiesgoNew != null) {
                idtipoRiesgoNew = em.getReference(idtipoRiesgoNew.getClass(), idtipoRiesgoNew.getIdtipoRiesgo());
                sgriesgo.setIdtipoRiesgo(idtipoRiesgoNew);
            }
            if (codUsuarioNew != null) {
                codUsuarioNew = em.getReference(codUsuarioNew.getClass(), codUsuarioNew.getCodUsuario());
                sgriesgo.setCodUsuario(codUsuarioNew);
            }
            List<SgprocesoRiesgo> attachedSgprocesoRiesgoListNew = new ArrayList<SgprocesoRiesgo>();
            for (SgprocesoRiesgo sgprocesoRiesgoListNewSgprocesoRiesgoToAttach : sgprocesoRiesgoListNew) {
                sgprocesoRiesgoListNewSgprocesoRiesgoToAttach = em.getReference(sgprocesoRiesgoListNewSgprocesoRiesgoToAttach.getClass(), sgprocesoRiesgoListNewSgprocesoRiesgoToAttach.getSgprocesoRiesgoPK());
                attachedSgprocesoRiesgoListNew.add(sgprocesoRiesgoListNewSgprocesoRiesgoToAttach);
            }
            sgprocesoRiesgoListNew = attachedSgprocesoRiesgoListNew;
            sgriesgo.setSgprocesoRiesgoList(sgprocesoRiesgoListNew);
            sgriesgo = em.merge(sgriesgo);
            if (idprobabilidadOld != null && !idprobabilidadOld.equals(idprobabilidadNew)) {
                idprobabilidadOld.getSgriesgoList().remove(sgriesgo);
                idprobabilidadOld = em.merge(idprobabilidadOld);
            }
            if (idprobabilidadNew != null && !idprobabilidadNew.equals(idprobabilidadOld)) {
                idprobabilidadNew.getSgriesgoList().add(sgriesgo);
                idprobabilidadNew = em.merge(idprobabilidadNew);
            }
            if (idtipoRiesgoOld != null && !idtipoRiesgoOld.equals(idtipoRiesgoNew)) {
                idtipoRiesgoOld.getSgriesgoList().remove(sgriesgo);
                idtipoRiesgoOld = em.merge(idtipoRiesgoOld);
            }
            if (idtipoRiesgoNew != null && !idtipoRiesgoNew.equals(idtipoRiesgoOld)) {
                idtipoRiesgoNew.getSgriesgoList().add(sgriesgo);
                idtipoRiesgoNew = em.merge(idtipoRiesgoNew);
            }
            if (codUsuarioOld != null && !codUsuarioOld.equals(codUsuarioNew)) {
                codUsuarioOld.getSgriesgoList().remove(sgriesgo);
                codUsuarioOld = em.merge(codUsuarioOld);
            }
            if (codUsuarioNew != null && !codUsuarioNew.equals(codUsuarioOld)) {
                codUsuarioNew.getSgriesgoList().add(sgriesgo);
                codUsuarioNew = em.merge(codUsuarioNew);
            }
            for (SgprocesoRiesgo sgprocesoRiesgoListNewSgprocesoRiesgo : sgprocesoRiesgoListNew) {
                if (!sgprocesoRiesgoListOld.contains(sgprocesoRiesgoListNewSgprocesoRiesgo)) {
                    Sgriesgo oldSgriesgoOfSgprocesoRiesgoListNewSgprocesoRiesgo = sgprocesoRiesgoListNewSgprocesoRiesgo.getSgriesgo();
                    sgprocesoRiesgoListNewSgprocesoRiesgo.setSgriesgo(sgriesgo);
                    sgprocesoRiesgoListNewSgprocesoRiesgo = em.merge(sgprocesoRiesgoListNewSgprocesoRiesgo);
                    if (oldSgriesgoOfSgprocesoRiesgoListNewSgprocesoRiesgo != null && !oldSgriesgoOfSgprocesoRiesgoListNewSgprocesoRiesgo.equals(sgriesgo)) {
                        oldSgriesgoOfSgprocesoRiesgoListNewSgprocesoRiesgo.getSgprocesoRiesgoList().remove(sgprocesoRiesgoListNewSgprocesoRiesgo);
                        oldSgriesgoOfSgprocesoRiesgoListNewSgprocesoRiesgo = em.merge(oldSgriesgoOfSgprocesoRiesgoListNewSgprocesoRiesgo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgriesgoPK id = sgriesgo.getSgriesgoPK();
                if (findSgriesgo(id) == null) {
                    throw new NonexistentEntityException("The sgriesgo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgriesgoPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgriesgo sgriesgo;
            try {
                sgriesgo = em.getReference(Sgriesgo.class, id);
                sgriesgo.getSgriesgoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgriesgo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SgprocesoRiesgo> sgprocesoRiesgoListOrphanCheck = sgriesgo.getSgprocesoRiesgoList();
            for (SgprocesoRiesgo sgprocesoRiesgoListOrphanCheckSgprocesoRiesgo : sgprocesoRiesgoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sgriesgo (" + sgriesgo + ") cannot be destroyed since the SgprocesoRiesgo " + sgprocesoRiesgoListOrphanCheckSgprocesoRiesgo + " in its sgprocesoRiesgoList field has a non-nullable sgriesgo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            SgprobabilidadRiesgo idprobabilidad = sgriesgo.getIdprobabilidad();
            if (idprobabilidad != null) {
                idprobabilidad.getSgriesgoList().remove(sgriesgo);
                idprobabilidad = em.merge(idprobabilidad);
            }
            SgtipoRiesgo idtipoRiesgo = sgriesgo.getIdtipoRiesgo();
            if (idtipoRiesgo != null) {
                idtipoRiesgo.getSgriesgoList().remove(sgriesgo);
                idtipoRiesgo = em.merge(idtipoRiesgo);
            }
            SgUsuario codUsuario = sgriesgo.getCodUsuario();
            if (codUsuario != null) {
                codUsuario.getSgriesgoList().remove(sgriesgo);
                codUsuario = em.merge(codUsuario);
            }
            em.remove(sgriesgo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sgriesgo> findSgriesgoEntities() {
        return findSgriesgoEntities(true, -1, -1);
    }

    public List<Sgriesgo> findSgriesgoEntities(int maxResults, int firstResult) {
        return findSgriesgoEntities(false, maxResults, firstResult);
    }

    private List<Sgriesgo> findSgriesgoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sgriesgo.class));
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

    public Sgriesgo findSgriesgo(SgriesgoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sgriesgo.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgriesgoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sgriesgo> rt = cq.from(Sgriesgo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
