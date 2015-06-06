package py.com.hotelsys.componentes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class ButtonTool extends JButton{
	public ButtonTool(String str) {
		super(str);
		setHorizontalTextPosition(SwingConstants.CENTER);
		setVerticalTextPosition(SwingConstants.BOTTOM);
		setMinimumSize(new Dimension(95, 85));
		setMaximumSize(new Dimension(95, 85));
		setFont(new Font("Tahoma", Font.BOLD, 11));
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		setFocusPainted(false);
		setBackground(new Color(233, 150, 122));
	}
}
