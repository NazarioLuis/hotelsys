package py.com.hotelsys.dao;

import java.util.List;

import org.hibernate.Session;

import py.com.hotelsys.util.HibernateUtil;


public class GenericGao <T>{
	private Session session;
	private Class<?> entity;

	GenericGao(Class<?> entity){
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		this.entity = entity;
	}
	//Metodo Generico para Insertar, para todas las entidades
	public void insertar(T entity){
		session.save(entity);
		session.getTransaction().commit();
	}
	//Metodo Generico para Actualizar, para todas las entidades
	public void actualizar(T entity){
		session.merge(entity);
		session.getTransaction().commit();
	}
	//Metodo Generico para Eliminar, para todas las entidades
	public void eliminar(T entity){
		session.delete(entity);
		session.getTransaction().commit();
	}
	//Metodo Generico para Recuperar por Id todas las entidades
	@SuppressWarnings("unchecked")
	public T encontrarPorId(int id){
		return (T) session.get(this.entity, id);
	}
	@SuppressWarnings("unchecked")
	public List<T> recuperaTodo(){
		return (List<T>) session.createCriteria(entity).list();
	}
}
