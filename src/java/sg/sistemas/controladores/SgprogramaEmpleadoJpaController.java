/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.sistemas.controladores;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgplanPrograma;
import sg.sistemas.entidades.SgUsuario;
import sg.sistemas.entidades.SgprogramaEmpleado;
import sg.sistemas.entidades.SgprogramaEmpleadoPK;

/**
 *
 * @author Misael
 */
public class SgprogramaEmpleadoJpaController implements Serializable {

    public SgprogramaEmpleadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgprogramaEmpleado sgprogramaEmpleado) throws PreexistingEntityException, Exception {
        if (sgprogramaEmpleado.getSgprogramaEmpleadoPK() == null) {
            sgprogramaEmpleado.setSgprogramaEmpleadoPK(new SgprogramaEmpleadoPK());
        }
        sgprogramaEmpleado.getSgprogramaEmpleadoPK().setIdplanProg(sgprogramaEmpleado.getSgplanPrograma().getSgplanProgramaPK().getIdplanProg());
        sgprogramaEmpleado.getSgprogramaEmpleadoPK().setCodUsuario(sgprogramaEmpleado.getSgUsuario().getCodUsuario());
        sgprogramaEmpleado.getSgprogramaEmpleadoPK().setIdsociedad(sgprogramaEmpleado.getSgplanPrograma().getSgplanProgramaPK().getIdsociedad());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgplanPrograma sgplanPrograma = sgprogramaEmpleado.getSgplanPrograma();
            if (sgplanPrograma != null) {
                sgplanPrograma = em.getReference(sgplanPrograma.getClass(), sgplanPrograma.getSgplanProgramaPK());
                sgprogramaEmpleado.setSgplanPrograma(sgplanPrograma);
            }
            SgUsuario sgUsuario = sgprogramaEmpleado.getSgUsuario();
            if (sgUsuario != null) {
                sgUsuario = em.getReference(sgUsuario.getClass(), sgUsuario.getCodUsuario());
                sgprogramaEmpleado.setSgUsuario(sgUsuario);
            }
            em.persist(sgprogramaEmpleado);
            if (sgplanPrograma != null) {
                sgplanPrograma.getSgprogramaEmpleadoList().add(sgprogramaEmpleado);
                sgplanPrograma = em.merge(sgplanPrograma);
            }
            if (sgUsuario != null) {
                sgUsuario.getSgprogramaEmpleadoList().add(sgprogramaEmpleado);
                sgUsuario = em.merge(sgUsuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgprogramaEmpleado(sgprogramaEmpleado.getSgprogramaEmpleadoPK()) != null) {
                throw new PreexistingEntityException("SgprogramaEmpleado " + sgprogramaEmpleado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgprogramaEmpleado sgprogramaEmpleado) throws NonexistentEntityException, Exception {
        sgprogramaEmpleado.getSgprogramaEmpleadoPK().setIdplanProg(sgprogramaEmpleado.getSgplanPrograma().getSgplanProgramaPK().getIdplanProg());
        sgprogramaEmpleado.getSgprogramaEmpleadoPK().setCodUsuario(sgprogramaEmpleado.getSgUsuario().getCodUsuario());
        sgprogramaEmpleado.getSgprogramaEmpleadoPK().setIdsociedad(sgprogramaEmpleado.getSgplanPrograma().getSgplanProgramaPK().getIdsociedad());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgprogramaEmpleado persistentSgprogramaEmpleado = em.find(SgprogramaEmpleado.class, sgprogramaEmpleado.getSgprogramaEmpleadoPK());
            SgplanPrograma sgplanProgramaOld = persistentSgprogramaEmpleado.getSgplanPrograma();
            SgplanPrograma sgplanProgramaNew = sgprogramaEmpleado.getSgplanPrograma();
            SgUsuario sgUsuarioOld = persistentSgprogramaEmpleado.getSgUsuario();
            SgUsuario sgUsuarioNew = sgprogramaEmpleado.getSgUsuario();
            if (sgplanProgramaNew != null) {
                sgplanProgramaNew = em.getReference(sgplanProgramaNew.getClass(), sgplanProgramaNew.getSgplanProgramaPK());
                sgprogramaEmpleado.setSgplanPrograma(sgplanProgramaNew);
            }
            if (sgUsuarioNew != null) {
                sgUsuarioNew = em.getReference(sgUsuarioNew.getClass(), sgUsuarioNew.getCodUsuario());
                sgprogramaEmpleado.setSgUsuario(sgUsuarioNew);
            }
            sgprogramaEmpleado = em.merge(sgprogramaEmpleado);
            if (sgplanProgramaOld != null && !sgplanProgramaOld.equals(sgplanProgramaNew)) {
                sgplanProgramaOld.getSgprogramaEmpleadoList().remove(sgprogramaEmpleado);
                sgplanProgramaOld = em.merge(sgplanProgramaOld);
            }
            if (sgplanProgramaNew != null && !sgplanProgramaNew.equals(sgplanProgramaOld)) {
                sgplanProgramaNew.getSgprogramaEmpleadoList().add(sgprogramaEmpleado);
                sgplanProgramaNew = em.merge(sgplanProgramaNew);
            }
            if (sgUsuarioOld != null && !sgUsuarioOld.equals(sgUsuarioNew)) {
                sgUsuarioOld.getSgprogramaEmpleadoList().remove(sgprogramaEmpleado);
                sgUsuarioOld = em.merge(sgUsuarioOld);
            }
            if (sgUsuarioNew != null && !sgUsuarioNew.equals(sgUsuarioOld)) {
                sgUsuarioNew.getSgprogramaEmpleadoList().add(sgprogramaEmpleado);
                sgUsuarioNew = em.merge(sgUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgprogramaEmpleadoPK id = sgprogramaEmpleado.getSgprogramaEmpleadoPK();
                if (findSgprogramaEmpleado(id) == null) {
                    throw new NonexistentEntityException("The sgprogramaEmpleado with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgprogramaEmpleadoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgprogramaEmpleado sgprogramaEmpleado;
            try {
                sgprogramaEmpleado = em.getReference(SgprogramaEmpleado.class, id);
                sgprogramaEmpleado.getSgprogramaEmpleadoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgprogramaEmpleado with id " + id + " no longer exists.", enfe);
            }
            SgplanPrograma sgplanPrograma = sgprogramaEmpleado.getSgplanPrograma();
            if (sgplanPrograma != null) {
                sgplanPrograma.getSgprogramaEmpleadoList().remove(sgprogramaEmpleado);
                sgplanPrograma = em.merge(sgplanPrograma);
            }
            SgUsuario sgUsuario = sgprogramaEmpleado.getSgUsuario();
            if (sgUsuario != null) {
                sgUsuario.getSgprogramaEmpleadoList().remove(sgprogramaEmpleado);
                sgUsuario = em.merge(sgUsuario);
            }
            em.remove(sgprogramaEmpleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgprogramaEmpleado> findSgprogramaEmpleadoEntities() {
        return findSgprogramaEmpleadoEntities(true, -1, -1);
    }

    public List<SgprogramaEmpleado> findSgprogramaEmpleadoEntities(int maxResults, int firstResult) {
        return findSgprogramaEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<SgprogramaEmpleado> findSgprogramaEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgprogramaEmpleado.class));
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

    public SgprogramaEmpleado findSgprogramaEmpleado(SgprogramaEmpleadoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgprogramaEmpleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgprogramaEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgprogramaEmpleado> rt = cq.from(SgprogramaEmpleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
