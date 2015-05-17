package py.com.hotelsys.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import py.com.hotelsys.modelo.SalidaStock;
import py.com.hotelsys.util.FormatoFecha;

public class SalidaStockDao extends GenericDao<SalidaStock>{

	public SalidaStockDao() {
		super(SalidaStock.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SalidaStock> recuperarPorFiltros(String [] filtro) {
		criteria = session.createCriteria(entity);
		criteria.add(Restrictions.like("descripcion", "%"+filtro[0]+"%"));
		criteria.add(Restrictions.between("fecha", FormatoFecha.stringToDate(filtro[1]),FormatoFecha.stringToDate(filtro[2])));
		criteria.add(Restrictions.eq("estado", true));
		list = criteria.list();
		session.close();
		return list;
		
	}
	
}
