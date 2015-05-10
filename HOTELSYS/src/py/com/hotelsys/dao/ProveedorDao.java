package py.com.hotelsys.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import py.com.hotelsys.modelo.Proveedor;

public class ProveedorDao extends GenericDao<Proveedor>{

	public ProveedorDao() {
		super(Proveedor.class);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Proveedor> cosultarPorFiltros(String [] filtro) {
		criteria = session.createCriteria(entity);
		criteria.add(
					Restrictions.or(
						Restrictions.like("nombre", "%"+filtro[0]+"%").ignoreCase(),
						Restrictions.like("documento", "%"+filtro[0]+"%").ignoreCase()
					)
				);
		
		list = criteria.list();
		cerrar();
		return list;
	}
	
	
	
	
}
