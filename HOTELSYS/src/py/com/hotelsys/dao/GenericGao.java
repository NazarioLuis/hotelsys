package py.com.hotelsys.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

import py.com.hotelsys.util.HibernateUtil;


public abstract class GenericGao <T>{
	Session session;
	Class<?> entity;
	Object id;
	Criteria criteria;
	List<T> list;
	public String[] filtros;

	GenericGao(Class<?> entity){
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		this.entity = entity;
	}
	//Metodo Generico para Insertar, para todas las entidades
	public void insertar(T entity) throws Exception{
		session.save(entity);
		commit();
	}
	//Metodo Generico para Actualizar, para todas las entidades
	public void actualizar(T entity) throws Exception{
		session.merge(entity);
		commit();
	}
	//Metodo Generico para Eliminar, para todas las entidades
	public void eliminar(int id) throws Exception{
		@SuppressWarnings("unchecked")
		T entity = (T) session.get(this.entity, id);
		session.delete(entity);
		session.getTransaction().commit();
	}
	//Metodo Generico para Recuperar por Id todas las entidades
	@SuppressWarnings("unchecked")
	public T recuperarPorId(int id){
		 T entity = (T) session.get(this.entity, id);
		 commit();
		 return entity;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> recuperaTodo(){
		list = session.createCriteria(entity).list();
		cerrar();
		return list;
	}
	
	public int recuperMaxId(){
		id = session.createCriteria(entity)
			    .setProjection(Projections.max("id")).uniqueResult();
		cerrar();
		if (id == null){
			return 0;
			
		}
		else
			return (int) id;
	}
	
	
	
	public abstract List<T> cosultarPorFiltros(String [] filtro);
	
	public void cerrar() {
		session.close();
	}
	private void commit() {
		session.getTransaction().commit();
	}
	public void rollback() {
		session.getTransaction().rollback();
	}
}
