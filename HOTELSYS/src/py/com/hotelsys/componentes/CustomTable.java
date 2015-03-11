package py.com.hotelsys.componentes;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class CustomTable extends JTable {
	private DefaultTableModel modelo;
	
	
	public CustomTable(String[] modelo,int[] anchos) {
		configurarModelo(modelo);
		if (anchos != null) {
			ajusrtarAnchoColumnas(anchos);
		}
		
	}
	
	private void configurarModelo(String[] modelo) {
		this.modelo = new DefaultTableModel(null,modelo){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		setModel(this.modelo);
		
	}
	
	private void ajusrtarAnchoColumnas(int[] anchos) {
		for (int i = 0; i < anchos.length; i++) {
			getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
		}
		
	}
	
	

	public DefaultTableModel getModelo() {
		return this.modelo;
	}
}
