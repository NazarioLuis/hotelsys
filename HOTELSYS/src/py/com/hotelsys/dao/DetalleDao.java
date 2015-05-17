package py.com.hotelsys.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import py.com.hotelsys.modelo.Detalle;
import py.com.hotelsys.modelo.Estadia;

public class DetalleDao extends GenericDao<Detalle>{

	public DetalleDao() {
		super(Detalle.class);
	}

	
	@SuppressWarnings("unchecked")
	public List<Detalle> cosultarPorEstadia (Estadia e) {
		criteria = session.createCriteria(entity);
		criteria.createAlias("estadia", "e");
		criteria.add(
			Restrictions.eq("e.id", e.getId())
		);
		
		list = criteria.list();
		cerrar();
		return list;
	}


	@Override
	public List<Detalle> recuperarPorFiltros(String[] filtro) {
		return null;
	}
}
