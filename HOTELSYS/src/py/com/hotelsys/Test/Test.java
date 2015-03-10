package py.com.hotelsys.Test;

import py.com.hotelsys.dao.ClienteDao;
import py.com.hotelsys.modelo.Cliente;





public class Test {

	public static void main(String[] args) {
		Cliente c = new Cliente();
		c.setId(1);
		c.setNombre("Nombre");
		ClienteDao cDao = new ClienteDao();
		cDao.insertar(c);
		
//		Habitacion h = new Habitacion();
//		h.setId(2);
//		h.setDescripcion("Descripcion");
//		HabitacionDao hDao = new HabitacionDao();
//		hDao.insertar(h);
		
//		Cliente c = new Cliente();
//		c.setId(2);
//		ClienteDao cDao = new ClienteDao();
//		cDao.eliminar(c);
		
//		ClienteDao cDao = new ClienteDao();
//		Cliente c = cDao.encontrarPorId(1);
//		System.out.println("Nombre="+c.getNombre());
		
//		ClienteDao cDao = new ClienteDao();
//		List<Cliente> lista = cDao.recuperaTodo();
//		
//		for (Cliente c : lista) {
//			System.out.println("Nombre="+c.getNombre());
//		}
	}

}
