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
import sg.sistemas.entidades.SgMenus;
import sg.sistemas.entidades.SgUsuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgProgramas;
import sg.sistemas.entidades.SgProgramasPK;

/**
 *
 * @author Misael
 */
public class SgProgramasJpaController implements Serializable {

    public SgProgramasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgProgramas sgProgramas) throws PreexistingEntityException, Exception {
        if (sgProgramas.getSgProgramasPK() == null) {
            sgProgramas.setSgProgramasPK(new SgProgramasPK());
        }
        if (sgProgramas.getSgUsuarioList() == null) {
            sgProgramas.setSgUsuarioList(new ArrayList<SgUsuario>());
        }
        sgProgramas.getSgProgramasPK().setIdmenu(sgProgramas.getSgMenus().getIdmenu());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgMenus sgMenus = sgProgramas.getSgMenus();
            if (sgMenus != null) {
                sgMenus = em.getReference(sgMenus.getClass(), sgMenus.getIdmenu());
                sgProgramas.setSgMenus(sgMenus);
            }
            List<SgUsuario> attachedSgUsuarioList = new ArrayList<SgUsuario>();
            for (SgUsuario sgUsuarioListSgUsuarioToAttach : sgProgramas.getSgUsuarioList()) {
                sgUsuarioListSgUsuarioToAttach = em.getReference(sgUsuarioListSgUsuarioToAttach.getClass(), sgUsuarioListSgUsuarioToAttach.getCodUsuario());
                attachedSgUsuarioList.add(sgUsuarioListSgUsuarioToAttach);
            }
            sgProgramas.setSgUsuarioList(attachedSgUsuarioList);
            em.persist(sgProgramas);
            if (sgMenus != null) {
                sgMenus.getSgProgramasList().add(sgProgramas);
                sgMenus = em.merge(sgMenus);
            }
            for (SgUsuario sgUsuarioListSgUsuario : sgProgramas.getSgUsuarioList()) {
                sgUsuarioListSgUsuario.getSgProgramasList().add(sgProgramas);
                sgUsuarioListSgUsuario = em.merge(sgUsuarioListSgUsuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgProgramas(sgProgramas.getSgProgramasPK()) != null) {
                throw new PreexistingEntityException("SgProgramas " + sgProgramas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgProgramas sgProgramas) throws NonexistentEntityException, Exception {
        sgProgramas.getSgProgramasPK().setIdmenu(sgProgramas.getSgMenus().getIdmenu());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgProgramas persistentSgProgramas = em.find(SgProgramas.class, sgProgramas.getSgProgramasPK());
            SgMenus sgMenusOld = persistentSgProgramas.getSgMenus();
            SgMenus sgMenusNew = sgProgramas.getSgMenus();
            List<SgUsuario> sgUsuarioListOld = persistentSgProgramas.getSgUsuarioList();
            List<SgUsuario> sgUsuarioListNew = sgProgramas.getSgUsuarioList();
            if (sgMenusNew != null) {
                sgMenusNew = em.getReference(sgMenusNew.getClass(), sgMenusNew.getIdmenu());
                sgProgramas.setSgMenus(sgMenusNew);
            }
            List<SgUsuario> attachedSgUsuarioListNew = new ArrayList<SgUsuario>();
            for (SgUsuario sgUsuarioListNewSgUsuarioToAttach : sgUsuarioListNew) {
                sgUsuarioListNewSgUsuarioToAttach = em.getReference(sgUsuarioListNewSgUsuarioToAttach.getClass(), sgUsuarioListNewSgUsuarioToAttach.getCodUsuario());
                attachedSgUsuarioListNew.add(sgUsuarioListNewSgUsuarioToAttach);
            }
            sgUsuarioListNew = attachedSgUsuarioListNew;
            sgProgramas.setSgUsuarioList(sgUsuarioListNew);
            sgProgramas = em.merge(sgProgramas);
            if (sgMenusOld != null && !sgMenusOld.equals(sgMenusNew)) {
                sgMenusOld.getSgProgramasList().remove(sgProgramas);
                sgMenusOld = em.merge(sgMenusOld);
            }
            if (sgMenusNew != null && !sgMenusNew.equals(sgMenusOld)) {
                sgMenusNew.getSgProgramasList().add(sgProgramas);
                sgMenusNew = em.merge(sgMenusNew);
            }
            for (SgUsuario sgUsuarioListOldSgUsuario : sgUsuarioListOld) {
                if (!sgUsuarioListNew.contains(sgUsuarioListOldSgUsuario)) {
                    sgUsuarioListOldSgUsuario.getSgProgramasList().remove(sgProgramas);
                    sgUsuarioListOldSgUsuario = em.merge(sgUsuarioListOldSgUsuario);
                }
            }
            for (SgUsuario sgUsuarioListNewSgUsuario : sgUsuarioListNew) {
                if (!sgUsuarioListOld.contains(sgUsuarioListNewSgUsuario)) {
                    sgUsuarioListNewSgUsuario.getSgProgramasList().add(sgProgramas);
                    sgUsuarioListNewSgUsuario = em.merge(sgUsuarioListNewSgUsuario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgProgramasPK id = sgProgramas.getSgProgramasPK();
                if (findSgProgramas(id) == null) {
                    throw new NonexistentEntityException("The sgProgramas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgProgramasPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgProgramas sgProgramas;
            try {
                sgProgramas = em.getReference(SgProgramas.class, id);
                sgProgramas.getSgProgramasPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgProgramas with id " + id + " no longer exists.", enfe);
            }
            SgMenus sgMenus = sgProgramas.getSgMenus();
            if (sgMenus != null) {
                sgMenus.getSgProgramasList().remove(sgProgramas);
                sgMenus = em.merge(sgMenus);
            }
            List<SgUsuario> sgUsuarioList = sgProgramas.getSgUsuarioList();
            for (SgUsuario sgUsuarioListSgUsuario : sgUsuarioList) {
                sgUsuarioListSgUsuario.getSgProgramasList().remove(sgProgramas);
                sgUsuarioListSgUsuario = em.merge(sgUsuarioListSgUsuario);
            }
            em.remove(sgProgramas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgProgramas> findSgProgramasEntities() {
        return findSgProgramasEntities(true, -1, -1);
    }

    public List<SgProgramas> findSgProgramasEntities(int maxResults, int firstResult) {
        return findSgProgramasEntities(false, maxResults, firstResult);
    }

    private List<SgProgramas> findSgProgramasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgProgramas.class));
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

    public SgProgramas findSgProgramas(SgProgramasPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgProgramas.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgProgramasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgProgramas> rt = cq.from(SgProgramas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
