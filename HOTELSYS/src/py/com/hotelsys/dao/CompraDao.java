package py.com.hotelsys.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import py.com.hotelsys.modelo.Compra;
import py.com.hotelsys.util.FormatoFecha;

public class CompraDao extends GenericDao<Compra>{

	public CompraDao() {
		super(Compra.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Compra> recuperarPorFiltros(String [] filtro) {
		criteria = session.createCriteria(entity);
		criteria.createAlias("proveedor", "p");
		criteria.add(Restrictions.or(Restrictions.like("factura", "%"+filtro[0]+"%"),
				Restrictions.like("timbrado", "%"+filtro[0]+"%"),
				Restrictions.like("p.nombre", "%"+filtro[0]+"%")));
		criteria.add(Restrictions.between("fecha", FormatoFecha.stringToDate(filtro[1]),FormatoFecha.stringToDate(filtro[2])));
		
		list = criteria.list();
		session.close();
		return list;
		
	}
	
	
	@Override
	public Compra recuperarPorId(int id) {
		criteria = session.createCriteria(entity);
		criteria.add(Restrictions.eq("id", id));
		
		Compra c = (Compra) criteria.uniqueResult();
		session.close();
		return c;
	}
	
}
