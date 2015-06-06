package py.com.hotelsys.dao;

import java.util.List;

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
	
}
