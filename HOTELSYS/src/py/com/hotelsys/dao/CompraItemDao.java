package py.com.hotelsys.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import py.com.hotelsys.modelo.Compra;
import py.com.hotelsys.modelo.CompraItem;
import py.com.hotelsys.modelo.Producto;

public class CompraItemDao extends GenericGao<CompraItem>{

	public CompraItemDao() {
		super(CompraItem.class);
	}

	
	public int cosultarPromedio(Producto producto) {
		DetachedCriteria maxId = DetachedCriteria.forClass(Compra.class)
			    .setProjection( Projections.max("fecha") );
		criteria = session.createCriteria(entity);
		criteria.createAlias("compra", "c");
		criteria.createAlias("producto", "p");
		criteria.add(Property.forName("c.fecha").eq(maxId));
		criteria.add(Restrictions.eq("p.id", producto.getId()));
		CompraItem item=(CompraItem) criteria.uniqueResult();
		
		return (int) item.getCostoPromedio();
	}


	@Override
	public List<CompraItem> cosultarPorFiltros(String[] filtro) {
		return null;
	}
	
}
