package py.com.hotelsys.componentes;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;

import py.com.hotelsys.util.FormatoFecha;

@SuppressWarnings("serial")
public class VisorFechaHora extends JLabel {
	private Timer timer;
	private TimerTask task;
	public VisorFechaHora() {
		setText(FormatoFecha.showFull(new Date()));
	}
	public void ejecutar(){
		timer = new Timer();
		task = new TimerTask() {
			
			@Override
			public void run() {
				setText(FormatoFecha.showFull(new Date()));
			}
		};
		timer.schedule(task,1000, 1000);
	}
}
