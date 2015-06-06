package py.com.hotelsys.dao;

import java.util.List;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import py.com.hotelsys.modelo.Caja;
import py.com.hotelsys.modelo.Cobranza;

public class CobranzaDao extends GenericDao<Cobranza>{

	public CobranzaDao() {
		super(Cobranza.class);
	}

	
	
	@Override
	public List<Cobranza> recuperarPorFiltros(String [] filtro) {
		return list;
	}



	public Double recuperaTotalPorCaja(Caja caja) {
		System.out.println(caja);
		criteria = session.createCriteria(entity).createAlias("caja", "c");
		criteria.setProjection(Projections.sum("monto"));
		criteria.add(Restrictions.eq("c.id", caja.getId()));
		Double total = (Double) criteria.uniqueResult();
		if(total == null)
			total = 0.0;
		cerrar();
		return total;
	}
	
}
