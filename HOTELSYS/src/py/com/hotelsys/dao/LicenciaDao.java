package py.com.hotelsys.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import py.com.hotelsys.modelo.Licencia;

public class LicenciaDao extends GenericDao<Licencia>{

	public LicenciaDao() {
		super(Licencia.class);
	}
	@Override
	public List<Licencia> recuperarPorFiltros(String[] filtro) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Licencia recuperarValides(String filtro) {
		criteria = session.createCriteria(entity);
		criteria.add(Restrictions.eq("serial", filtro));
		criteria.add(Restrictions.ge("valides", new Date()));
		Licencia l = (Licencia) criteria.uniqueResult();
		cerrar();
		return l;
	}

}
