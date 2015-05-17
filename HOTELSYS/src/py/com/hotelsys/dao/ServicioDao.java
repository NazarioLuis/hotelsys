package py.com.hotelsys.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import py.com.hotelsys.modelo.Servicio;

public class ServicioDao extends GenericDao<Servicio>{

	public ServicioDao() {
		super(Servicio.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Servicio> recuperarPorFiltros(String [] filtro) {
		criteria = session.createCriteria(entity);
		criteria.add(
						Restrictions.like("descripcion", "%"+filtro[0]+"%").ignoreCase()
					);
		
		list = criteria.list();
		cerrar();
		return list;
	}
	
}
