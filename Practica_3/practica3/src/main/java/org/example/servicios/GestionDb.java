package org.example.servicios;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.example.encapsulaciones.Articulo;
import org.example.encapsulaciones.Comentario;
import org.example.encapsulaciones.Etiqueta;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

public class GestionDb<T> {
    private static EntityManagerFactory emf;
    private Class<T> claseEntidad;


    public GestionDb(Class<T> claseEntidad) {
        if(emf == null) {
            emf = Persistence.createEntityManagerFactory("MiUnidadPersistencia");
        }
        this.claseEntidad = claseEntidad;

    }
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    private Object getValorCampo(T entidad){
        if(entidad == null){
            return null;
        }
        //aplicando la clase de reflexión.
        for(Field f : entidad.getClass().getDeclaredFields()) {  //tomando todos los campos privados.
            if (f.isAnnotationPresent(Id.class)) { //preguntando por la anotación ID.
                try {
                    f.setAccessible(true);
                    Object valorCampo = f.get(entidad);

                    System.out.println("Nombre del campo: "+f.getName());
                    System.out.println("Tipo del campo: "+f.getType().getName());
                    System.out.println("Valor del campo: "+valorCampo );

                    return valorCampo;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    /**
     *
     * @param entidad
     */
    public T crear(T entidad) throws IllegalArgumentException, EntityExistsException, PersistenceException{
        EntityManager em = getEntityManager();

        try {

            em.getTransaction().begin();
            em.persist(entidad);
            em.getTransaction().commit();

        }finally {
            em.close();
        }
        return entidad;
    }

    /**
     *
     * @param entidad
     */
    public T editar(T entidad) throws PersistenceException{
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            em.merge(entidad);
            em.getTransaction().commit();
        }finally {
            em.close();
        }
        return entidad;
    }

    /**
     *
     * @param entidadId
     */
    public boolean eliminar(Object entidadId) throws PersistenceException{
        boolean ok = false;
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            T entidad = em.find(claseEntidad, entidadId);
            em.remove(entidad);
            em.getTransaction().commit();
            ok = true;
        }finally {
            em.close();
        }
        return ok;
    }

    /**
     *
     * @param id
     * @return
     */
    public T find(Object id) throws PersistenceException {
        EntityManager em = getEntityManager();
        try{
            return em.find(claseEntidad, id);
        } finally {
            em.close();
        }
    }

    public T findByUsername(String username) throws PersistenceException {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(claseEntidad);
            Root<T> root = cq.from(claseEntidad);
            cq.select(root).where(cb.equal(root.get("username"), username));
            TypedQuery<T> query = em.createQuery(cq);
            List<T> resultList = query.getResultList();
            if (resultList.isEmpty()) {
                return null;
            } else {
                return resultList.get(0);
            }
        } finally {
            em.close();
        }
    }
    public List<Comentario> obtenerComentariosDeArticulo(Articulo articulo) throws PersistenceException {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Comentario> cq = cb.createQuery(Comentario.class);
            Root<Comentario> root = cq.from(Comentario.class);
            cq.select(root).where(cb.equal(root.get("articulo"), articulo));
            TypedQuery<Comentario> query = em.createQuery(cq);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /*public List<T> find2(Object id) throws PersistenceException {
        EntityManager em = getEntityManager();
        try{
            CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(claseEntidad);
            criteriaQuery.select(criteriaQuery.from(claseEntidad));
            List<T> lista = null;
            for (T data:em.createQuery(criteriaQuery).getResultList()) {
                if(data.equals(id)){
                    lista.add(data);
                }
            }
            return lista;
        } finally {
            em.close();
        }
    }*/
    public List<T> find2(Object id) throws PersistenceException {
        EntityManager em = getEntityManager();
        try{
            List<T> lista = Collections.singletonList(em.find(claseEntidad, id));
            return lista;
        } finally {
            em.close();
        }
    }
    /**
     *
     * @return
     */
    public List<T> findAll() throws PersistenceException {
        EntityManager em = getEntityManager();
        try{
            CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(claseEntidad);
            criteriaQuery.select(criteriaQuery.from(claseEntidad));
            return em.createQuery(criteriaQuery).getResultList();
        } finally {
            em.close();
        }
    }
    public List<T> buscarByUserId(Object id)throws PersistenceException {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(claseEntidad);
            Root<T> root = criteriaQuery.from(claseEntidad);
            criteriaQuery.select(root);
            criteriaQuery.where(criteriaBuilder.equal(root.get("autor").get("id"), id));
            return em.createQuery(criteriaQuery).getResultList();
        } finally {
            em.close();
        }

    }
    public List<T> buscarFotoByUserId(Object id)throws PersistenceException {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(claseEntidad);
            Root<T> root = criteriaQuery.from(claseEntidad);
            criteriaQuery.select(root);
            criteriaQuery.where(criteriaBuilder.equal(root.get("usuario").get("id"), id));
            return em.createQuery(criteriaQuery).getResultList();
        } finally {
            em.close();
        }

    }
    public T verificarCredenciales(String username, String password) throws PersistenceException {
        EntityManager em = getEntityManager();

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(claseEntidad);
            Root<T> root = criteriaQuery.from(claseEntidad);
            criteriaQuery.select(root);
            criteriaQuery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get("username"), username),
                            criteriaBuilder.equal(root.get("password"), password)
                    )
            );

            TypedQuery<T> typedQuery = em.createQuery(criteriaQuery);
            typedQuery.setMaxResults(1);

            List<T> resultado = typedQuery.getResultList();

            if (!resultado.isEmpty()) {
                return resultado.get(0);
            } else {
                return null;
            }
        } finally {
            em.close();
        }
    }
    public T obtenerOCrearEtiqueta(String nombreEtiqueta) throws PersistenceException {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Etiqueta> criteriaQuery = criteriaBuilder.createQuery(Etiqueta.class);
            Root<Etiqueta> root = criteriaQuery.from(Etiqueta.class);
            criteriaQuery.select(root);
            criteriaQuery.where(criteriaBuilder.equal(root.get("etiqueta"), nombreEtiqueta));

            TypedQuery<Etiqueta> typedQuery = em.createQuery(criteriaQuery);
            typedQuery.setMaxResults(1);

            List<Etiqueta> resultado = typedQuery.getResultList();

            if (!resultado.isEmpty()) {
                return (T) resultado.get(0);
            } else {
                Etiqueta etiqueta = new Etiqueta(nombreEtiqueta);
                em.getTransaction().begin();
                em.persist(etiqueta);
                em.getTransaction().commit();
                return (T) etiqueta;
            }
        } finally {
            em.close();
        }
    }
    public String showEtiqueta(int articuloId) throws PersistenceException {
        EntityManager em = getEntityManager();
        try {
            Articulo articulo = em.find(Articulo.class, articuloId);
            if (articulo != null) {
                List<Etiqueta> etiquetas = articulo.getListaEtiquetas();

                StringBuilder etiquetasString = new StringBuilder();
                for (Etiqueta etiqueta : etiquetas) {
                    if (etiquetasString.length() > 0) {
                        etiquetasString.append(", ");
                    }
                    etiquetasString.append(etiqueta.getEtiqueta());
                }

                return etiquetasString.toString();
            } else {
                return "";
            }
        } finally {
            em.close();
        }
    }
    public List<Etiqueta> showEtiquetaList(int articuloId) throws PersistenceException {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Etiqueta> criteriaQuery = criteriaBuilder.createQuery(Etiqueta.class);
            Root<Articulo> root = criteriaQuery.from(Articulo.class);
            Join<Articulo, Etiqueta> etiquetaJoin = root.join("listaEtiquetas");

            criteriaQuery.select(etiquetaJoin);
            criteriaQuery.where(criteriaBuilder.equal(root.get("id"), articuloId));

            TypedQuery<Etiqueta> typedQuery = em.createQuery(criteriaQuery);
            return typedQuery.getResultList();
        } finally {
            em.close();
        }
    }


    public List<Articulo> getArticulosPorPagina(int numeroPagina) throws PersistenceException {
        int resultadosPorPagina = 5;
        int primerResultado = (numeroPagina - 1) * resultadosPorPagina;

        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Articulo> criteriaQuery = criteriaBuilder.createQuery(Articulo.class);
            Root<Articulo> root = criteriaQuery.from(Articulo.class);
            criteriaQuery.select(root);
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("fecha")));

            TypedQuery<Articulo> typedQuery = em.createQuery(criteriaQuery);
            typedQuery.setFirstResult(primerResultado);
            typedQuery.setMaxResults(resultadosPorPagina);

            return typedQuery.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Articulo> getArticulosPorEtiqueta(String nombreEtiqueta) throws PersistenceException {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Articulo> criteriaQuery = criteriaBuilder.createQuery(Articulo.class);
            Root<Articulo> root = criteriaQuery.from(Articulo.class);
            criteriaQuery.select(root);
            Join<Articulo, Etiqueta> etiquetasJoin = root.join("listaEtiquetas");
            criteriaQuery.where(criteriaBuilder.equal(etiquetasJoin.get("etiqueta"), nombreEtiqueta));

            TypedQuery<Articulo> typedQuery = em.createQuery(criteriaQuery);
            return typedQuery.getResultList();
        } finally {
            em.close();
        }
    }


}





