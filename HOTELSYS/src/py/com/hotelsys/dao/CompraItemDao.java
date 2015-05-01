package py.com.hotelsys.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import py.com.hotelsys.modelo.Cliente;
import py.com.hotelsys.modelo.Compra;
import py.com.hotelsys.modelo.CompraItem;
import py.com.hotelsys.modelo.Producto;

public class CompraItemDao extends GenericGao<CompraItem>{

	public CompraItemDao() {
		super(CompraItem.class);
	}

	
	public int cosultarPromedio(Producto producto) {
		DetachedCriteria max = DetachedCriteria.forClass(entity)
				.createAlias("producto", "p")
				.createAlias("compra", "c")
				.add(Restrictions.eq("p.id", producto.getId()))
				.setProjection(Projections.max("c.id"));
				
		criteria = session.createCriteria(entity)
		.createAlias("producto", "p")
		.createAlias("compra", "c")
		.add(Restrictions.eq("p.id", producto.getId()))
		.add(Property.forName("c.id").eq(max));
		CompraItem item=(CompraItem) criteria.uniqueResult();

		try {
			return (int) item.getCostoPromedio();
		} catch (Exception e) {
			return 0;
		}
	}


	@Override
	public List<CompraItem> cosultarPorFiltros(String[] filtro) {
		return null;
	}
	
}
