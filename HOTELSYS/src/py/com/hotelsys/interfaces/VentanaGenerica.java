package py.com.hotelsys.interfaces;

public interface VentanaGenerica {
	public void habilitarCampos(boolean b);
	public void cargarAtributos();
	public void inicializar();
	public void advertencia(String texto,int t);
	public void cargarFormulario();
	public void limpiarCampos();
	public void buscar();
}
