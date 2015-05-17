package py.com.hotelsys.componentes;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class JCustomPanel1 extends JPanel{
	public JCustomPanel1() {
		setBorder(new LineBorder(new Color(128, 0, 0), 1, true));
		setBackground(new Color(173, 216, 230));
	}

}
