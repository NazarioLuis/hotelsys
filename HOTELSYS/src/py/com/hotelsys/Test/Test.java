package py.com.hotelsys.Test;

import org.hibernate.Session;

import py.com.hotelsys.util.HibernateUtil;


public class Test {

	public static void main(String[] args) {
		
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
	}

}
