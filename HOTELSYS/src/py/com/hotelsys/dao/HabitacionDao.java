package py.com.hotelsys.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import py.com.hotelsys.modelo.Habitacion;

public class HabitacionDao extends GenericDao<Habitacion>{

	public HabitacionDao() {
		super(Habitacion.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Habitacion> cosultarPorFiltros(String [] filtro) {
		criteria = session.createCriteria(entity);
		criteria.add(
						
						Restrictions.like("descripcion", "%"+filtro[0]+"%").ignoreCase()
					);
		
		list = criteria.list();
		cerrar();
		return list;
	}
}
