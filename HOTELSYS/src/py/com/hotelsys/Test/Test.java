package py.com.hotelsys.Test;

import java.util.Date;

import py.com.hotelsys.dao.ClienteDao;
import py.com.hotelsys.dao.CompraDao;
import py.com.hotelsys.modelo.Cliente;
import py.com.hotelsys.modelo.Compra;
import py.com.hotelsys.modelo.CompraItem;
import py.com.hotelsys.modelo.Producto;
import py.com.hotelsys.modelo.Proveedor;
import py.com.hotelsys.modelo.Stock;





public class Test {

	public static void main(String[] args) {
//		Cliente c = new Cliente();
//		c.setId(1);
//		c.setNombre("Nombre");
//		ClienteDao cDao = new ClienteDao();
//		cDao.insertar(c);
		
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
		
		Proveedor proveedor= new Proveedor();
		proveedor.setId(1);
		proveedor.setNombre("pro");
		proveedor.setDireccion("sdg");
		proveedor.setDocumento("444");
		proveedor.setTelefono("666");
		
		Compra compra= new Compra();
		compra.setId(2);
		compra.setFactura("4444");
		compra.setTimbrado("44444");
		compra.setVencimientoTimbrado(new Date());
		compra.setFecha(new Date());
		compra.setTotal((double) 10000);
		compra.setProveedor(proveedor);
		
		Producto producto = new Producto();
		producto.setId(1);
		producto.setDescripcion("pro1");
		producto.setStock(new Stock());
		producto.getStock().setCantidad(0);
		producto.getStock().setPrecio(0);
		Producto producto2 = new Producto();
		producto2.setId(2);
		producto2.setDescripcion("pro2");
		producto2.setStock(new Stock());
		producto2.getStock().setCantidad(0);
		producto2.getStock().setPrecio(0);
		
		CompraItem compraItem = new CompraItem();
		compraItem.setCantidad(3);
		compraItem.setCosto(3000);
		compraItem.setCostoPromedio(3000);
		compraItem.setProducto(producto);
		
		
		CompraItem compraItem2 = new CompraItem();
		compraItem2.setCantidad(4);
		compraItem2.setCosto(4000);
		compraItem2.setCostoPromedio(4000);
		compraItem2.setProducto(producto2);
		
		
		
		
		compra.getCompraItems().add(compraItem);
		compra.getCompraItems().add(compraItem2);
		
		compraItem.setCompra(compra);
		compraItem2.setCompra(compra);
		
		CompraDao compraDao = new CompraDao();
		try {
			
			compraDao.insertar(compra);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
