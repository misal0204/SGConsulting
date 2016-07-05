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
import sg.sistemas.entidades.Sgcentro;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import sg.sistemas.controladores.exceptions.IllegalOrphanException;
import sg.sistemas.controladores.exceptions.NonexistentEntityException;
import sg.sistemas.controladores.exceptions.PreexistingEntityException;
import sg.sistemas.entidades.Sgcliente;
import sg.sistemas.entidades.Sgparametros;
import sg.sistemas.entidades.Sgtareas;
import sg.sistemas.entidades.Sgproveedores;
import sg.sistemas.entidades.Sgsociedad;

/**
 *
 * @author Misael
 */
public class SgsociedadJpaController implements Serializable {

    public SgsociedadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sgsociedad sgsociedad) throws PreexistingEntityException, Exception {
        if (sgsociedad.getSgcentroList() == null) {
            sgsociedad.setSgcentroList(new ArrayList<Sgcentro>());
        }
        if (sgsociedad.getSgclienteList() == null) {
            sgsociedad.setSgclienteList(new ArrayList<Sgcliente>());
        }
        if (sgsociedad.getSgparametrosList() == null) {
            sgsociedad.setSgparametrosList(new ArrayList<Sgparametros>());
        }
        if (sgsociedad.getSgtareasList() == null) {
            sgsociedad.setSgtareasList(new ArrayList<Sgtareas>());
        }
        if (sgsociedad.getSgproveedoresList() == null) {
            sgsociedad.setSgproveedoresList(new ArrayList<Sgproveedores>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Sgcentro> attachedSgcentroList = new ArrayList<Sgcentro>();
            for (Sgcentro sgcentroListSgcentroToAttach : sgsociedad.getSgcentroList()) {
                sgcentroListSgcentroToAttach = em.getReference(sgcentroListSgcentroToAttach.getClass(), sgcentroListSgcentroToAttach.getIdcentro());
                attachedSgcentroList.add(sgcentroListSgcentroToAttach);
            }
            sgsociedad.setSgcentroList(attachedSgcentroList);
            List<Sgcliente> attachedSgclienteList = new ArrayList<Sgcliente>();
            for (Sgcliente sgclienteListSgclienteToAttach : sgsociedad.getSgclienteList()) {
                sgclienteListSgclienteToAttach = em.getReference(sgclienteListSgclienteToAttach.getClass(), sgclienteListSgclienteToAttach.getSgclientePK());
                attachedSgclienteList.add(sgclienteListSgclienteToAttach);
            }
            sgsociedad.setSgclienteList(attachedSgclienteList);
            List<Sgparametros> attachedSgparametrosList = new ArrayList<Sgparametros>();
            for (Sgparametros sgparametrosListSgparametrosToAttach : sgsociedad.getSgparametrosList()) {
                sgparametrosListSgparametrosToAttach = em.getReference(sgparametrosListSgparametrosToAttach.getClass(), sgparametrosListSgparametrosToAttach.getSgparametrosPK());
                attachedSgparametrosList.add(sgparametrosListSgparametrosToAttach);
            }
            sgsociedad.setSgparametrosList(attachedSgparametrosList);
            List<Sgtareas> attachedSgtareasList = new ArrayList<Sgtareas>();
            for (Sgtareas sgtareasListSgtareasToAttach : sgsociedad.getSgtareasList()) {
                sgtareasListSgtareasToAttach = em.getReference(sgtareasListSgtareasToAttach.getClass(), sgtareasListSgtareasToAttach.getSgtareasPK());
                attachedSgtareasList.add(sgtareasListSgtareasToAttach);
            }
            sgsociedad.setSgtareasList(attachedSgtareasList);
            List<Sgproveedores> attachedSgproveedoresList = new ArrayList<Sgproveedores>();
            for (Sgproveedores sgproveedoresListSgproveedoresToAttach : sgsociedad.getSgproveedoresList()) {
                sgproveedoresListSgproveedoresToAttach = em.getReference(sgproveedoresListSgproveedoresToAttach.getClass(), sgproveedoresListSgproveedoresToAttach.getSgproveedoresPK());
                attachedSgproveedoresList.add(sgproveedoresListSgproveedoresToAttach);
            }
            sgsociedad.setSgproveedoresList(attachedSgproveedoresList);
            em.persist(sgsociedad);
            for (Sgcentro sgcentroListSgcentro : sgsociedad.getSgcentroList()) {
                sgcentroListSgcentro.getSgsociedadList().add(sgsociedad);
                sgcentroListSgcentro = em.merge(sgcentroListSgcentro);
            }
            for (Sgcliente sgclienteListSgcliente : sgsociedad.getSgclienteList()) {
                Sgsociedad oldSgsociedadOfSgclienteListSgcliente = sgclienteListSgcliente.getSgsociedad();
                sgclienteListSgcliente.setSgsociedad(sgsociedad);
                sgclienteListSgcliente = em.merge(sgclienteListSgcliente);
                if (oldSgsociedadOfSgclienteListSgcliente != null) {
                    oldSgsociedadOfSgclienteListSgcliente.getSgclienteList().remove(sgclienteListSgcliente);
                    oldSgsociedadOfSgclienteListSgcliente = em.merge(oldSgsociedadOfSgclienteListSgcliente);
                }
            }
            for (Sgparametros sgparametrosListSgparametros : sgsociedad.getSgparametrosList()) {
                Sgsociedad oldSgsociedadOfSgparametrosListSgparametros = sgparametrosListSgparametros.getSgsociedad();
                sgparametrosListSgparametros.setSgsociedad(sgsociedad);
                sgparametrosListSgparametros = em.merge(sgparametrosListSgparametros);
                if (oldSgsociedadOfSgparametrosListSgparametros != null) {
                    oldSgsociedadOfSgparametrosListSgparametros.getSgparametrosList().remove(sgparametrosListSgparametros);
                    oldSgsociedadOfSgparametrosListSgparametros = em.merge(oldSgsociedadOfSgparametrosListSgparametros);
                }
            }
            for (Sgtareas sgtareasListSgtareas : sgsociedad.getSgtareasList()) {
                Sgsociedad oldSgsociedadOfSgtareasListSgtareas = sgtareasListSgtareas.getSgsociedad();
                sgtareasListSgtareas.setSgsociedad(sgsociedad);
                sgtareasListSgtareas = em.merge(sgtareasListSgtareas);
                if (oldSgsociedadOfSgtareasListSgtareas != null) {
                    oldSgsociedadOfSgtareasListSgtareas.getSgtareasList().remove(sgtareasListSgtareas);
                    oldSgsociedadOfSgtareasListSgtareas = em.merge(oldSgsociedadOfSgtareasListSgtareas);
                }
            }
            for (Sgproveedores sgproveedoresListSgproveedores : sgsociedad.getSgproveedoresList()) {
                Sgsociedad oldSgsociedadOfSgproveedoresListSgproveedores = sgproveedoresListSgproveedores.getSgsociedad();
                sgproveedoresListSgproveedores.setSgsociedad(sgsociedad);
                sgproveedoresListSgproveedores = em.merge(sgproveedoresListSgproveedores);
                if (oldSgsociedadOfSgproveedoresListSgproveedores != null) {
                    oldSgsociedadOfSgproveedoresListSgproveedores.getSgproveedoresList().remove(sgproveedoresListSgproveedores);
                    oldSgsociedadOfSgproveedoresListSgproveedores = em.merge(oldSgsociedadOfSgproveedoresListSgproveedores);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSgsociedad(sgsociedad.getIdsociedad()) != null) {
                throw new PreexistingEntityException("Sgsociedad " + sgsociedad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sgsociedad sgsociedad) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sgsociedad persistentSgsociedad = em.find(Sgsociedad.class, sgsociedad.getIdsociedad());
            List<Sgcentro> sgcentroListOld = persistentSgsociedad.getSgcentroList();
            List<Sgcentro> sgcentroListNew = sgsociedad.getSgcentroList();
            List<Sgcliente> sgclienteListOld = persistentSgsociedad.getSgclienteList();
            List<Sgcliente> sgclienteListNew = sgsociedad.getSgclienteList();
            List<Sgparametros> sgparametrosListOld = persistentSgsociedad.getSgparametrosList();
            List<Sgparametros> sgparametrosListNew = sgsociedad.getSgparametrosList();
            List<Sgtareas> sgtareasListOld = persistentSgsociedad.getSgtareasList();
            List<Sgtareas> sgtareasListNew = sgsociedad.getSgtareasList();
            List<Sgproveedores> sgproveedoresListOld = persistentSgsociedad.getSgproveedoresList();
            List<Sgproveedores> sgproveedoresListNew = sgsociedad.getSgproveedoresList();
            List<String> illegalOrphanMessages = null;
            for (Sgcliente sgclienteListOldSgcliente : sgclienteListOld) {
                if (!sgclienteListNew.contains(sgclienteListOldSgcliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sgcliente " + sgclienteListOldSgcliente + " since its sgsociedad field is not nullable.");
                }
            }
            for (Sgparametros sgparametrosListOldSgparametros : sgparametrosListOld) {
                if (!sgparametrosListNew.contains(sgparametrosListOldSgparametros)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sgparametros " + sgparametrosListOldSgparametros + " since its sgsociedad field is not nullable.");
                }
            }
            for (Sgtareas sgtareasListOldSgtareas : sgtareasListOld) {
                if (!sgtareasListNew.contains(sgtareasListOldSgtareas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sgtareas " + sgtareasListOldSgtareas + " since its sgsociedad field is not nullable.");
                }
            }
            for (Sgproveedores sgproveedoresListOldSgproveedores : sgproveedoresListOld) {
                if (!sgproveedoresListNew.contains(sgproveedoresListOldSgproveedores)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sgproveedores " + sgproveedoresListOldSgproveedores + " since its sgsociedad field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Sgcentro> attachedSgcentroListNew = new ArrayList<Sgcentro>();
            for (Sgcentro sgcentroListNewSgcentroToAttach : sgcentroListNew) {
                sgcentroListNewSgcentroToAttach = em.getReference(sgcentroListNewSgcentroToAttach.getClass(), sgcentroListNewSgcentroToAttach.getIdcentro());
                attachedSgcentroListNew.add(sgcentroListNewSgcentroToAttach);
            }
            sgcentroListNew = attachedSgcentroListNew;
            sgsociedad.setSgcentroList(sgcentroListNew);
            List<Sgcliente> attachedSgclienteListNew = new ArrayList<Sgcliente>();
            for (Sgcliente sgclienteListNewSgclienteToAttach : sgclienteListNew) {
                sgclienteListNewSgclienteToAttach = em.getReference(sgclienteListNewSgclienteToAttach.getClass(), sgclienteListNewSgclienteToAttach.getSgclientePK());
                attachedSgclienteListNew.add(sgclienteListNewSgclienteToAttach);
            }
            sgclienteListNew = attachedSgclienteListNew;
            sgsociedad.setSgclienteList(sgclienteListNew);
            List<Sgparametros> attachedSgparametrosListNew = new ArrayList<Sgparametros>();
            for (Sgparametros sgparametrosListNewSgparametrosToAttach : sgparametrosListNew) {
                sgparametrosListNewSgparametrosToAttach = em.getReference(sgparametrosListNewSgparametrosToAttach.getClass(), sgparametrosListNewSgparametrosToAttach.getSgparametrosPK());
                attachedSgparametrosListNew.add(sgparametrosListNewSgparametrosToAttach);
            }
            sgparametrosListNew = attachedSgparametrosListNew;
            sgsociedad.setSgparametrosList(sgparametrosListNew);
            List<Sgtareas> attachedSgtareasListNew = new ArrayList<Sgtareas>();
            for (Sgtareas sgtareasListNewSgtareasToAttach : sgtareasListNew) {
                sgtareasListNewSgtareasToAttach = em.getReference(sgtareasListNewSgtareasToAttach.getClass(), sgtareasListNewSgtareasToAttach.getSgtareasPK());
                attachedSgtareasListNew.add(sgtareasListNewSgtareasToAttach);
            }
            sgtareasListNew = attachedSgtareasListNew;
            sgsociedad.setSgtareasList(sgtareasListNew);
            List<Sgproveedores> attachedSgproveedoresListNew = new ArrayList<Sgproveedores>();
            for (Sgproveedores sgproveedoresListNewSgproveedoresToAttach : sgproveedoresListNew) {
                sgproveedoresListNewSgproveedoresToAttach = em.getReference(sgproveedoresListNewSgproveedoresToAttach.getClass(), sgproveedoresListNewSgproveedoresToAttach.getSgproveedoresPK());
                attachedSgproveedoresListNew.add(sgproveedoresListNewSgproveedoresToAttach);
            }
            sgproveedoresListNew = attachedSgproveedoresListNew;
            sgsociedad.setSgproveedoresList(sgproveedoresListNew);
            sgsociedad = em.merge(sgsociedad);
            for (Sgcentro sgcentroListOldSgcentro : sgcentroListOld) {
                if (!sgcentroListNew.contains(sgcentroListOldSgcentro)) {
                    sgcentroListOldSgcentro.getSgsociedadList().remove(sgsociedad);
                    sgcentroListOldSgcentro = em.merge(sgcentroListOldSgcentro);
                }
            }
            for (Sgcentro sgcentroListNewSgcentro : sgcentroListNew) {
                if (!sgcentroListOld.contains(sgcentroListNewSgcentro)) {
                    sgcentroListNewSgcentro.getSgsociedadList().add(sgsociedad);
                    sgcentroListNewSgcentro = em.merge(sgcentroListNewSgcentro);
                }
            }
            for (Sgcliente sgclienteListNewSgcliente : sgclienteListNew) {
                if (!sgclienteListOld.contains(sgclienteListNewSgcliente)) {
                    Sgsociedad oldSgsociedadOfSgclienteListNewSgcliente = sgclienteListNewSgcliente.getSgsociedad();
                    sgclienteListNewSgcliente.setSgsociedad(sgsociedad);
                    sgclienteListNewSgcliente = em.merge(sgclienteListNewSgcliente);
                    if (oldSgsociedadOfSgclienteListNewSgcliente != null && !oldSgsociedadOfSgclienteListNewSgcliente.equals(sgsociedad)) {
                        oldSgsociedadOfSgclienteListNewSgcliente.getSgclienteList().remove(sgclienteListNewSgcliente);
                        oldSgsociedadOfSgclienteListNewSgcliente = em.merge(oldSgsociedadOfSgclienteListNewSgcliente);
                    }
                }
            }
            for (Sgparametros sgparametrosListNewSgparametros : sgparametrosListNew) {
                if (!sgparametrosListOld.contains(sgparametrosListNewSgparametros)) {
                    Sgsociedad oldSgsociedadOfSgparametrosListNewSgparametros = sgparametrosListNewSgparametros.getSgsociedad();
                    sgparametrosListNewSgparametros.setSgsociedad(sgsociedad);
                    sgparametrosListNewSgparametros = em.merge(sgparametrosListNewSgparametros);
                    if (oldSgsociedadOfSgparametrosListNewSgparametros != null && !oldSgsociedadOfSgparametrosListNewSgparametros.equals(sgsociedad)) {
                        oldSgsociedadOfSgparametrosListNewSgparametros.getSgparametrosList().remove(sgparametrosListNewSgparametros);
                        oldSgsociedadOfSgparametrosListNewSgparametros = em.merge(oldSgsociedadOfSgparametrosListNewSgparametros);
                    }
                }
            }
            for (Sgtareas sgtareasListNewSgtareas : sgtareasListNew) {
                if (!sgtareasListOld.contains(sgtareasListNewSgtareas)) {
                    Sgsociedad oldSgsociedadOfSgtareasListNewSgtareas = sgtareasListNewSgtareas.getSgsociedad();
                    sgtareasListNewSgtareas.setSgsociedad(sgsociedad);
                    sgtareasListNewSgtareas = em.merge(sgtareasListNewSgtareas);
                    if (oldSgsociedadOfSgtareasListNewSgtareas != null && !oldSgsociedadOfSgtareasListNewSgtareas.equals(sgsociedad)) {
                        oldSgsociedadOfSgtareasListNewSgtareas.getSgtareasList().remove(sgtareasListNewSgtareas);
                        oldSgsociedadOfSgtareasListNewSgtareas = em.merge(oldSgsociedadOfSgtareasListNewSgtareas);
                    }
                }
            }
            for (Sgproveedores sgproveedoresListNewSgproveedores : sgproveedoresListNew) {
                if (!sgproveedoresListOld.contains(sgproveedoresListNewSgproveedores)) {
                    Sgsociedad oldSgsociedadOfSgproveedoresListNewSgproveedores = sgproveedoresListNewSgproveedores.getSgsociedad();
                    sgproveedoresListNewSgproveedores.setSgsociedad(sgsociedad);
                    sgproveedoresListNewSgproveedores = em.merge(sgproveedoresListNewSgproveedores);
                    if (oldSgsociedadOfSgproveedoresListNewSgproveedores != null && !oldSgsociedadOfSgproveedoresListNewSgproveedores.equals(sgsociedad)) {
                        oldSgsociedadOfSgproveedoresListNewSgproveedores.getSgproveedoresList().remove(sgproveedoresListNewSgproveedores);
                        oldSgsociedadOfSgproveedoresListNewSgproveedores = em.merge(oldSgsociedadOfSgproveedoresListNewSgproveedores);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = sgsociedad.getIdsociedad();
                if (findSgsociedad(id) == null) {
                    throw new NonexistentEntityException("The sgsociedad with id " + id + " no longer exists.");
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
            Sgsociedad sgsociedad;
            try {
                sgsociedad = em.getReference(Sgsociedad.class, id);
                sgsociedad.getIdsociedad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sgsociedad with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Sgcliente> sgclienteListOrphanCheck = sgsociedad.getSgclienteList();
            for (Sgcliente sgclienteListOrphanCheckSgcliente : sgclienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sgsociedad (" + sgsociedad + ") cannot be destroyed since the Sgcliente " + sgclienteListOrphanCheckSgcliente + " in its sgclienteList field has a non-nullable sgsociedad field.");
            }
            List<Sgparametros> sgparametrosListOrphanCheck = sgsociedad.getSgparametrosList();
            for (Sgparametros sgparametrosListOrphanCheckSgparametros : sgparametrosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sgsociedad (" + sgsociedad + ") cannot be destroyed since the Sgparametros " + sgparametrosListOrphanCheckSgparametros + " in its sgparametrosList field has a non-nullable sgsociedad field.");
            }
            List<Sgtareas> sgtareasListOrphanCheck = sgsociedad.getSgtareasList();
            for (Sgtareas sgtareasListOrphanCheckSgtareas : sgtareasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sgsociedad (" + sgsociedad + ") cannot be destroyed since the Sgtareas " + sgtareasListOrphanCheckSgtareas + " in its sgtareasList field has a non-nullable sgsociedad field.");
            }
            List<Sgproveedores> sgproveedoresListOrphanCheck = sgsociedad.getSgproveedoresList();
            for (Sgproveedores sgproveedoresListOrphanCheckSgproveedores : sgproveedoresListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sgsociedad (" + sgsociedad + ") cannot be destroyed since the Sgproveedores " + sgproveedoresListOrphanCheckSgproveedores + " in its sgproveedoresList field has a non-nullable sgsociedad field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Sgcentro> sgcentroList = sgsociedad.getSgcentroList();
            for (Sgcentro sgcentroListSgcentro : sgcentroList) {
                sgcentroListSgcentro.getSgsociedadList().remove(sgsociedad);
                sgcentroListSgcentro = em.merge(sgcentroListSgcentro);
            }
            em.remove(sgsociedad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sgsociedad> findSgsociedadEntities() {
        return findSgsociedadEntities(true, -1, -1);
    }

    public List<Sgsociedad> findSgsociedadEntities(int maxResults, int firstResult) {
        return findSgsociedadEntities(false, maxResults, firstResult);
    }

    private List<Sgsociedad> findSgsociedadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sgsociedad.class));
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

    public Sgsociedad findSgsociedad(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sgsociedad.class, id);
        } finally {
            em.close();
        }
    }

    public int getSgsociedadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sgsociedad> rt = cq.from(Sgsociedad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
