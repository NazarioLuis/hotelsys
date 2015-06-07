package py.com.hotelsys.dao;

import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import py.com.hotelsys.modelo.Caja;
import py.com.hotelsys.modelo.Usuario;

public class CajaDao extends GenericDao<Caja>{

	public CajaDao() {
		super(Caja.class);
	}

	@Override
	public List<Caja> recuperarPorFiltros(String [] filtro) {
		return list;
	}

	public Caja cajaPorUsuario(Usuario usu) {
		criteria = session.createCriteria(entity)
				.createAlias("usuario", "u");
		criteria.add(Restrictions.isNull("cierre"));
		criteria.add(Restrictions.eq("u.alias", usu.getAlias()));
		Caja c = (Caja) criteria.uniqueResult();
		cerrar();
		return c;
	}

	public List<Caja> recuperarPorLimites(int d) {
		criteria = session.createCriteria(entity).addOrder(Order.desc("apertura"));
		criteria.add(Restrictions.isNotNull("cierre"));
		criteria.setFirstResult(d);
		criteria.setMaxResults(d+9);
		@SuppressWarnings("unchecked")
		List<Caja> lista = criteria.list();
		cerrar();
		return lista;
	}

	public int contar() {
		criteria = session.createCriteria(entity);
		criteria.setProjection(Projections.count("id"));
		criteria.add(Restrictions.isNotNull("cierre"));
		int c = Integer.parseInt(""+criteria.uniqueResult());
		cerrar();
		return c;
	}
	
}
