package window;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.table.DefaultTableModel;

import log.Logger;
import serveur.connectionStruct.Client;
import serveur.connectionStruct.ClientSocketList;

public class 
ClientThread implements Runnable 
{
	private GUI gui;
	
	public
	ClientThread (GUI gui)
	{this.gui = gui;}
	
	@Override
	public void run() 
	{
		while (true)
		{
			try {
				TimeUnit.SECONDS.sleep(1);
				ClientSocketList clients = this.gui.getServer().getClients();
				DefaultTableModel newModel = new DefaultTableModel();
				newModel.addColumn("Name"); 
				newModel.addColumn("IPv4");
				newModel.addColumn("Port");
				for (Client c : clients)
				{
					newModel.addRow(this.row(c));
				}
				this.gui.setTableModel(newModel);
				this.gui.updateTable();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private Object[]
	row (Client c)
	{
		Object[] res = new Object[3];
		res[0] = c.getStudent().toString();
		res[1] = c.getSock().getRemoteAddress().toString().replaceAll("/", "").replaceAll(":.*", "");
		res[2] = c.getSock().getRemoteAddress().toString().replaceAll("/.*:", "");
		return res;
	}
}
