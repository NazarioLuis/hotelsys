package py.com.hotelsys.dao;

import java.util.List;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import py.com.hotelsys.modelo.Deuda;

public class DeudaDao extends GenericDao<Deuda>{

	public DeudaDao() {
		super(Deuda.class);
	}

	@Override
	public List<Deuda> recuperarPorFiltros(String [] filtro) {
		
		return list;
	}

	public Double recuperarMontoDeuda(int id) {
		criteria = session.createCriteria(Deuda.class);
		criteria.createAlias("cliente", "c");
		criteria.add(Restrictions.eq("estado",true));
		criteria.add(Restrictions.eq("c.id",id));
		criteria.setProjection(Projections.sum("monto"));
		Double monto = (Double) criteria.uniqueResult();
		cerrar();
		return monto;
	}

	@SuppressWarnings("unchecked")
	public List<Deuda> recuperarDeudasPorCliente(int id) {
		criteria = session.createCriteria(Deuda.class);
		criteria.createAlias("cliente", "c");
		criteria.add(Restrictions.eq("estado",true));
		criteria.add(Restrictions.eq("c.id",id));
		list = (List<Deuda>) criteria.list();
		cerrar();
		return list;
	}
	
}
