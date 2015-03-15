package py.com.hotelsys.componentes;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class FondoPrincipal extends JPanel {
	private URL url = getClass().getResource("/img/fondo.jpg");
	private Image image = new ImageIcon(url).getImage();
	
	
	public FondoPrincipal() {
	}

	
	@Override
	public void paint(Graphics g ) {
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		setOpaque(false);
		super.paint(g);
	}

}
