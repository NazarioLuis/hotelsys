package py.com.hotelsys.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import py.com.hotelsys.modelo.CompraItem;
import py.com.hotelsys.modelo.Cotizacion;

public class CotizacionDao extends GenericDao<Cotizacion>{

	public CotizacionDao() {
		super(Cotizacion.class);
	}

	@Override
	public void eliminar(int id) throws Exception {
		Cotizacion cot = (Cotizacion) session.createCriteria(entity)
				.add(Restrictions.eq("id", id)).uniqueResult();
		session.delete(cot);
		session.getTransaction().commit();
		
	}
	
	@Override
	public Cotizacion recuperarPorId(int id) {
		
		Cotizacion cot = (Cotizacion) session.createCriteria(entity)
				.add(Restrictions.eq("id", id)).uniqueResult();
		cerrar();
		return cot;
	}
	@Override
	public List<Cotizacion> recuperaTodo() {
		list = session.createCriteria(entity).addOrder(Order.asc("fecha")).list();
		cerrar();
		return list;
	}
	
	@Override
	public List<Cotizacion> cosultarPorFiltros(String [] filtro) {
		return list;
	}

	public Cotizacion recuperarPorMoneda(int i) {
		DetachedCriteria max = DetachedCriteria.forClass(entity)
				.add(Restrictions.eq("moneda", i))
				.add(Restrictions.le("fecha", new Date()))
				.setProjection(Projections.max("fecha"));
				
		criteria = session.createCriteria(entity)
		.add(Property.forName("fecha").eq(max))
		.add(Restrictions.eq("moneda", i));
		Cotizacion item=(Cotizacion) criteria.uniqueResult();
		
		return item;
	}
	
}
