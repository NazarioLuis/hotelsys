package py.com.hotelsys.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import py.com.hotelsys.modelo.Producto;

public class ProductoDao extends GenericDao<Producto>{

	public ProductoDao() {
		super(Producto.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Producto> cosultarPorFiltros(String [] filtro) {
		criteria = session.createCriteria(entity);
		criteria.add(
						Restrictions.like("descripcion", "%"+filtro[0]+"%").ignoreCase()
					);
		
		list = criteria.list();
		cerrar();
		return list;
	}
}
