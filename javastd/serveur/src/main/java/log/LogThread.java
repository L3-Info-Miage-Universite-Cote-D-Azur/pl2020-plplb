package log;

import java.util.concurrent.TimeUnit;

import window.GUI;

public class 
LogThread implements Runnable
{
	private GUI gui;
	
	public
	LogThread (GUI gui)
	{
		this.gui = gui;
	}
	
	@Override
	public void
	run ()
	{
		while (true)
		{
			try {
				TimeUnit.SECONDS.sleep(1);
				this.gui.getLogger().setText(Logger.logs);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
