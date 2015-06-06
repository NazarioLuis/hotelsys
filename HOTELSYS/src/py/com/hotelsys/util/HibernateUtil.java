package py.com.hotelsys.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	
	private static final SessionFactory sessionFactory = buildIfNeeded();

	
	//crear la fabrica de conecciones si aun no existe
	public static SessionFactory buildIfNeeded(){
	      if (sessionFactory != null) {
	           return sessionFactory;
	      }
	      return buildSessionFactory();
	}
	
	//crear la fabrica de conecciones
	private static SessionFactory buildSessionFactory() {
		try {
			
			Configuration conf = new Configuration();
			conf.configure();
			return conf.buildSessionFactory(
					new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build());
		} catch (Throwable e) {
			System.err.println("Inicio de Fabrica de Conexion Fallo: "+e);
			throw new ExceptionInInitializerError(e);
		}
		
	}
	
	
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}

	public static void cerrar() {
		sessionFactory.close();
	}

}
