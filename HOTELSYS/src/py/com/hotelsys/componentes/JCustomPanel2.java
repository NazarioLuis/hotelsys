package py.com.hotelsys.componentes;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class JCustomPanel2 extends JPanel{
	public JCustomPanel2() {
		setBorder(new LineBorder(new Color(128, 128, 0), 1, true));
		setBackground(new Color(211, 211, 211));
	}

}
