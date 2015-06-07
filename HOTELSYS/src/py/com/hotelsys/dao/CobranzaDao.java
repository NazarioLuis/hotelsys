package py.com.hotelsys.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import py.com.hotelsys.modelo.Caja;
import py.com.hotelsys.modelo.Cobranza;
import py.com.hotelsys.modelo.Deuda;

public class CobranzaDao extends GenericDao<Cobranza>{

	public CobranzaDao() {
		super(Cobranza.class);
	}

	
	
	@Override
	public List<Cobranza> recuperarPorFiltros(String [] filtro) {
		return list;
	}



	public Double recuperaTotalPorCaja(Caja caja) {
		criteria = session.createCriteria(entity).createAlias("caja", "c");
		criteria.setProjection(Projections.sum("monto"));
		criteria.add(Restrictions.eq("c.id", caja.getId()));
		Double total = (Double) criteria.uniqueResult();
		if(total == null)
			total = 0.0;
		cerrar();
		return total;
	}



	public Double recuperaTotalPorCajaTipoDeuda(Caja caja,int tipo) {
		DetachedCriteria subCriteria = DetachedCriteria.forClass(entity).createAlias("caja", "c");
		subCriteria.setProjection(Projections.property("id"));
		subCriteria.add(Restrictions.eq("c.id", caja.getId()));
		
		criteria = session.createCriteria(Deuda.class).createAlias("cobranza", "c");
		criteria.setProjection(Projections.sum("monto"));
		criteria.add(Property.forName("c.id").in(subCriteria));
		criteria.add(Restrictions.eq("tipo", tipo));
		Double total = (Double) criteria.uniqueResult();
		if(total == null)
			total = 0.0;
		cerrar();
		return total;
	}
	
}
