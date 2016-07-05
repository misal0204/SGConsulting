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
import sg.sistemas.entidades.SgdetalleObjInd;
import sg.sistemas.entidades.SgtipoAlerta;
import sg.sistemas.entidades.SgUsuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.SgseguimientoIndObj;
import sg.sistemas.entidades.SgseguimientoIndObjPK;

/**
 *
 * @author Misael
 */
public class SgseguimientoIndObjJpaController implements Serializable {

    public SgseguimientoIndObjJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SgseguimientoIndObj sgseguimientoIndObj) throws PreexistingEntityException, Exception {
        if (sgseguimientoIndObj.getSgseguimientoIndObjPK() == null) {
            sgseguimientoIndObj.setSgseguimientoIndObjPK(new SgseguimientoIndObjPK());
        }
        if (sgseguimientoIndObj.getSgUsuarioList() == null) {
            sgseguimientoIndObj.setSgUsuarioList(new ArrayList<SgUsuario>());
        }
        sgseguimientoIndObj.getSgseguimientoIndObjPK().setIdtipoAlerta(sgseguimientoIndObj.getSgtipoAlerta().getIdtipoAlerta());
        sgseguimientoIndObj.getSgseguimientoIndObjPK().setIdsociedad(sgseguimientoIndObj.getSgdetalleObjInd().getSgdetalleObjIndPK().getIdsociedad());
        sgseguimientoIndObj.getSgseguimientoIndObjPK().setIdobjIndicador(sgseguimientoIndObj.getSgdetalleObjInd().getSgdetalleObjIndPK().getIdobjIndicador());
        sgseguimientoIndObj.getSgseguimientoIndObjPK().setIddetalleIo(sgseguimientoIndObj.getSgdetalleObjInd().getSgdetalleObjIndPK().getIddetalleIo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgdetalleObjInd sgdetalleObjInd = sgseguimientoIndObj.getSgdetalleObjInd();
            if (sgdetalleObjInd != null) {
                sgdetalleObjInd = em.getReference(sgdetalleObjInd.getClass(), sgdetalleObjInd.getSgdetalleObjIndPK());
                sgseguimientoIndObj.setSgdetalleObjInd(sgdetalleObjInd);
            }
            SgtipoAlerta sgtipoAlerta = sgseguimientoIndObj.getSgtipoAlerta();
            if (sgtipoAlerta != null) {
                sgtipoAlerta = em.getReference(sgtipoAlerta.getClass(), sgtipoAlerta.getIdtipoAlerta());
                sgseguimientoIndObj.setSgtipoAlerta(sgtipoAlerta);
            }
            List<SgUsuario> attachedSgUsuarioList = new ArrayList<SgUsuario>();
            for (SgUsuario sgUsuarioListSgUsuarioToAttach : sgseguimientoIndObj.getSgUsuarioList()) {
                sgUsuarioListSgUsuarioToAttach = em.getReference(sgUsuarioListSgUsuarioToAttach.getClass(), sgUsuarioListSgUsuarioToAttach.getCodUsuario());
                attachedSgUsuarioList.add(sgUsuarioListSgUsuarioToAttach);
            }
            sgseguimientoIndObj.setSgUsuarioList(attachedSgUsuarioList);
            em.persist(sgseguimientoIndObj);
            if (sgdetalleObjInd != null) {
                sgdetalleObjInd.getSgseguimientoIndObjList().add(sgseguimientoIndObj);
                sgdetalleObjInd = em.merge(sgdetalleObjInd);
            }
            if (sgtipoAlerta != null) {
                sgtipoAlerta.getSgseguimientoIndObjList().add(sgseguimientoIndObj);
                sgtipoAlerta = em.merge(sgtipoAlerta);
            }
            for (SgUsuario sgUsuarioListSgUsuario : sgseguimientoIndObj.getSgUsuarioList()) {
                sgUsuarioListSgUsuario.getSgseguimientoIndObjList().add(sgseguimientoIndObj);
                sgUsuarioListSgUsuario = em.merge(sgUsuarioListSgUsuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgseguimientoIndObj(sgseguimientoIndObj.getSgseguimientoIndObjPK()) != null) {
                throw new PreexistingEntityException("SgseguimientoIndObj " + sgseguimientoIndObj + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SgseguimientoIndObj sgseguimientoIndObj) throws NonexistentEntityException, Exception {
        sgseguimientoIndObj.getSgseguimientoIndObjPK().setIdtipoAlerta(sgseguimientoIndObj.getSgtipoAlerta().getIdtipoAlerta());
        sgseguimientoIndObj.getSgseguimientoIndObjPK().setIdsociedad(sgseguimientoIndObj.getSgdetalleObjInd().getSgdetalleObjIndPK().getIdsociedad());
        sgseguimientoIndObj.getSgseguimientoIndObjPK().setIdobjIndicador(sgseguimientoIndObj.getSgdetalleObjInd().getSgdetalleObjIndPK().getIdobjIndicador());
        sgseguimientoIndObj.getSgseguimientoIndObjPK().setIddetalleIo(sgseguimientoIndObj.getSgdetalleObjInd().getSgdetalleObjIndPK().getIddetalleIo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgseguimientoIndObj persistentSgseguimientoIndObj = em.find(SgseguimientoIndObj.class, sgseguimientoIndObj.getSgseguimientoIndObjPK());
            SgdetalleObjInd sgdetalleObjIndOld = persistentSgseguimientoIndObj.getSgdetalleObjInd();
            SgdetalleObjInd sgdetalleObjIndNew = sgseguimientoIndObj.getSgdetalleObjInd();
            SgtipoAlerta sgtipoAlertaOld = persistentSgseguimientoIndObj.getSgtipoAlerta();
            SgtipoAlerta sgtipoAlertaNew = sgseguimientoIndObj.getSgtipoAlerta();
            List<SgUsuario> sgUsuarioListOld = persistentSgseguimientoIndObj.getSgUsuarioList();
            List<SgUsuario> sgUsuarioListNew = sgseguimientoIndObj.getSgUsuarioList();
            if (sgdetalleObjIndNew != null) {
                sgdetalleObjIndNew = em.getReference(sgdetalleObjIndNew.getClass(), sgdetalleObjIndNew.getSgdetalleObjIndPK());
                sgseguimientoIndObj.setSgdetalleObjInd(sgdetalleObjIndNew);
            }
            if (sgtipoAlertaNew != null) {
                sgtipoAlertaNew = em.getReference(sgtipoAlertaNew.getClass(), sgtipoAlertaNew.getIdtipoAlerta());
                sgseguimientoIndObj.setSgtipoAlerta(sgtipoAlertaNew);
            }
            List<SgUsuario> attachedSgUsuarioListNew = new ArrayList<SgUsuario>();
            for (SgUsuario sgUsuarioListNewSgUsuarioToAttach : sgUsuarioListNew) {
                sgUsuarioListNewSgUsuarioToAttach = em.getReference(sgUsuarioListNewSgUsuarioToAttach.getClass(), sgUsuarioListNewSgUsuarioToAttach.getCodUsuario());
                attachedSgUsuarioListNew.add(sgUsuarioListNewSgUsuarioToAttach);
            }
            sgUsuarioListNew = attachedSgUsuarioListNew;
            sgseguimientoIndObj.setSgUsuarioList(sgUsuarioListNew);
            sgseguimientoIndObj = em.merge(sgseguimientoIndObj);
            if (sgdetalleObjIndOld != null && !sgdetalleObjIndOld.equals(sgdetalleObjIndNew)) {
                sgdetalleObjIndOld.getSgseguimientoIndObjList().remove(sgseguimientoIndObj);
                sgdetalleObjIndOld = em.merge(sgdetalleObjIndOld);
            }
            if (sgdetalleObjIndNew != null && !sgdetalleObjIndNew.equals(sgdetalleObjIndOld)) {
                sgdetalleObjIndNew.getSgseguimientoIndObjList().add(sgseguimientoIndObj);
                sgdetalleObjIndNew = em.merge(sgdetalleObjIndNew);
            }
            if (sgtipoAlertaOld != null && !sgtipoAlertaOld.equals(sgtipoAlertaNew)) {
                sgtipoAlertaOld.getSgseguimientoIndObjList().remove(sgseguimientoIndObj);
                sgtipoAlertaOld = em.merge(sgtipoAlertaOld);
            }
            if (sgtipoAlertaNew != null && !sgtipoAlertaNew.equals(sgtipoAlertaOld)) {
                sgtipoAlertaNew.getSgseguimientoIndObjList().add(sgseguimientoIndObj);
                sgtipoAlertaNew = em.merge(sgtipoAlertaNew);
            }
            for (SgUsuario sgUsuarioListOldSgUsuario : sgUsuarioListOld) {
                if (!sgUsuarioListNew.contains(sgUsuarioListOldSgUsuario)) {
                    sgUsuarioListOldSgUsuario.getSgseguimientoIndObjList().remove(sgseguimientoIndObj);
                    sgUsuarioListOldSgUsuario = em.merge(sgUsuarioListOldSgUsuario);
                }
            }
            for (SgUsuario sgUsuarioListNewSgUsuario : sgUsuarioListNew) {
                if (!sgUsuarioListOld.contains(sgUsuarioListNewSgUsuario)) {
                    sgUsuarioListNewSgUsuario.getSgseguimientoIndObjList().add(sgseguimientoIndObj);
                    sgUsuarioListNewSgUsuario = em.merge(sgUsuarioListNewSgUsuario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SgseguimientoIndObjPK id = sgseguimientoIndObj.getSgseguimientoIndObjPK();
                if (findSgseguimientoIndObj(id) == null) {
                    throw new NonexistentEntityException("The sgseguimientoIndObj with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SgseguimientoIndObjPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SgseguimientoIndObj sgseguimientoIndObj;
            try {
                sgseguimientoIndObj = em.getReference(SgseguimientoIndObj.class, id);
                sgseguimientoIndObj.getSgseguimientoIndObjPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgseguimientoIndObj with id " + id + " no longer exists.", enfe);
            }
            SgdetalleObjInd sgdetalleObjInd = sgseguimientoIndObj.getSgdetalleObjInd();
            if (sgdetalleObjInd != null) {
                sgdetalleObjInd.getSgseguimientoIndObjList().remove(sgseguimientoIndObj);
                sgdetalleObjInd = em.merge(sgdetalleObjInd);
            }
            SgtipoAlerta sgtipoAlerta = sgseguimientoIndObj.getSgtipoAlerta();
            if (sgtipoAlerta != null) {
                sgtipoAlerta.getSgseguimientoIndObjList().remove(sgseguimientoIndObj);
                sgtipoAlerta = em.merge(sgtipoAlerta);
            }
            List<SgUsuario> sgUsuarioList = sgseguimientoIndObj.getSgUsuarioList();
            for (SgUsuario sgUsuarioListSgUsuario : sgUsuarioList) {
                sgUsuarioListSgUsuario.getSgseguimientoIndObjList().remove(sgseguimientoIndObj);
                sgUsuarioListSgUsuario = em.merge(sgUsuarioListSgUsuario);
            }
            em.remove(sgseguimientoIndObj);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SgseguimientoIndObj> findSgseguimientoIndObjEntities() {
        return findSgseguimientoIndObjEntities(true, -1, -1);
    }

    public List<SgseguimientoIndObj> findSgseguimientoIndObjEntities(int maxResults, int firstResult) {
        return findSgseguimientoIndObjEntities(false, maxResults, firstResult);
    }

    private List<SgseguimientoIndObj> findSgseguimientoIndObjEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SgseguimientoIndObj.class));
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

    public SgseguimientoIndObj findSgseguimientoIndObj(SgseguimientoIndObjPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SgseguimientoIndObj.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgseguimientoIndObjCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SgseguimientoIndObj> rt = cq.from(SgseguimientoIndObj.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
