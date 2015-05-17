package py.com.hotelsys.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import py.com.hotelsys.modelo.Estadia;
import py.com.hotelsys.modelo.Habitacion;

public class HabitacionDao extends GenericDao<Habitacion>{

	public HabitacionDao() {
		super(Habitacion.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Habitacion> recuperarPorFiltros(String [] filtro) {
		criteria = session.createCriteria(entity);
		criteria.add(
						
						Restrictions.like("descripcion", "%"+filtro[0]+"%").ignoreCase()
					);
		
		list = criteria.list();
		cerrar();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Habitacion> recuperarLibres(String [] filtro) {
		DetachedCriteria subCriteria = DetachedCriteria.forClass(Estadia.class);
		subCriteria.createAlias("habitacion", "h");
		subCriteria.add(Restrictions.isNull("fechaSal"));
		subCriteria.setProjection(Projections.property("h.id"));
		
		criteria = session.createCriteria(entity);
		criteria.add(Restrictions.like("descripcion", "%"+filtro[0]+"%").ignoreCase());
		criteria.add(Restrictions.not(Property.forName("id").in(subCriteria)));
		
		list = criteria.list();
		cerrar();
		return list;
	}
}
