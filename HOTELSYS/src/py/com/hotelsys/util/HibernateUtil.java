package py.com.hotelsys.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	
	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			//crear la fabrica de conecciones
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

}
