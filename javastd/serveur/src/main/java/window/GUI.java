package window;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;

import file.Config;
import log.Logger;
import log.LogThread;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import serveur.Serveur;
import java.awt.Color;
import java.awt.Desktop;

/**
 * GUI est la classe qui permet l'affichage de l'interface graphique
 * de l'application.
 * Dans la GUI, nous avons acces à:
 * 	-Aux logs
 * 	-Aux clients connectes
 * 	-A l'etat du serveur
 * 	-Aux dossiers de sauvegarde
 */
public class GUI {

	private JFrame frmServeurPlplb;
	private JTextArea logger;
	private Serveur serv;
	private JScrollPane scrollLog;
	private DefaultTableModel model;
	private JTable table;
	private JScrollPane scrollTable;
	/** Configuration */
	private Config config;

	/**
	 * Create the application.
	 */
	public GUI(Config config) {
		this.config = config;
		initialize();
	}
	
	public JFrame getFrame ()
	{return this.frmServeurPlplb;}
	
	public JTextArea getLogger ()
	{return this.logger;}
	
	public JTable getTable ()
	{return this.table;}
	
	public DefaultTableModel getTableModel ()
	{return this.model;}
	
	public Serveur getServer ()
	{return this.serv;}
	
	public void
	setTableModel (DefaultTableModel mod)
	{this.model = mod;}
	
	public void
	updateTable ()
	{
		table = new JTable(model);
		table.setFont(new Font("SansSerif", Font.PLAIN, 14));
		table.setBackground(new Color(0xA1, 0xCD, 0xA1));
		table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
		table.getTableHeader().setBackground(new Color(0x63, 0xAC, 0x63));
		scrollTable.setViewportView(table);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		serv = new Serveur(this.config);
		frmServeurPlplb = new JFrame();
		frmServeurPlplb.setTitle("Serveur - PLPLB");
		frmServeurPlplb.setBounds(100, 100, 800, 600);
		frmServeurPlplb.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmServeurPlplb.getContentPane().setLayout(null);
		
		JTextPane txtpnOff = new JTextPane();
		txtpnOff.setForeground(new Color(0xE1, 0x4A, 0x4A));
		txtpnOff.setBackground(SystemColor.menu);
		txtpnOff.setFont(new Font("SansSerif", Font.BOLD, 25));
		txtpnOff.setText("OFF");
		txtpnOff.setBounds(674, 37, 62, 39);
		frmServeurPlplb.getContentPane().add(txtpnOff);
		
		JButton btnStartStop = new JButton("START");
		btnStartStop.setBorder(null);
		btnStartStop.setForeground(new Color(0, 0, 0));
		btnStartStop.setFont(new Font("SansSerif", Font.BOLD, 25));
		btnStartStop.setBackground(new Color(0x63, 0xAC, 0x63));
		btnStartStop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				if (e.getButton() == MouseEvent.BUTTON1)
				{
					if (btnStartStop.getText().equals("START"))
					{
						btnStartStop.setText("STOP");
						txtpnOff.setText("ON");
						txtpnOff.setForeground(new Color(0x63, 0xAC, 0x63));
						serv.startServer();
					}
					else
					{
						btnStartStop.setText("START");
						txtpnOff.setText("OFF");
						txtpnOff.setForeground(new Color(0xE1, 0x4A, 0x4A));
						serv.stopServeur();
						serv = new Serveur(config);
					}
				}
			}
		});
		btnStartStop.setBounds(636, 116, 116, 81);
		frmServeurPlplb.getContentPane().add(btnStartStop);
		
		scrollLog = new JScrollPane();
		scrollLog.setBounds(10, 234, 726, 316);
		frmServeurPlplb.getContentPane().add(scrollLog);
		
		logger = new JTextArea();
		logger.setFont(new Font("SansSerif", Font.BOLD, 18));
		logger.setText(Logger.logs);
		scrollLog.setViewportView(logger);
		logger.setColumns(10);
		
		JButton btnLog = new JButton("LAST LOGS");
		btnLog.setBorder(null);
		btnLog.setBackground(new Color(0x63, 0xAC, 0x63));
		btnLog.setFont(new Font("SansSerif", Font.BOLD, 25));
		btnLog.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				if (e.getButton() == MouseEvent.BUTTON1)
				{
					File[] list = new File(config.getparentPath(),config.getConfig("log_directory")).listFiles();
					if (list.length == 0)
						return;
					File x = list[0];
					// Prendre le dernier logfile
					for (File f : list)
						if (f.lastModified() > x.lastModified())
							x = f;
					try {
						Desktop.getDesktop().open(x);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnLog.setBounds(444, 21, 182, 81);
		frmServeurPlplb.getContentPane().add(btnLog);
		
		JButton btnLocalDB = new JButton("LOCAL DB");
		btnLocalDB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				if (e.getButton() == MouseEvent.BUTTON1)
				{
					try {
						Desktop.getDesktop().open(new File(config.getparentPath()));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnLocalDB.setBorder(null);
		btnLocalDB.setBackground(new Color(0x63, 0xAC, 0x63));
		btnLocalDB.setFont(new Font("SansSerif", Font.BOLD, 25));
		btnLocalDB.setBounds(444, 116, 182, 81);
		frmServeurPlplb.getContentPane().add(btnLocalDB);
		
		model = new DefaultTableModel(); 
		model.addColumn("Name"); 
		model.addColumn("IPv4");
		model.addColumn("Port");
		
		scrollTable = new JScrollPane();
		scrollTable.setFont(new Font("SansSerif", Font.PLAIN, 15));
		scrollTable.setBounds(10, 21, 403, 193);
		frmServeurPlplb.getContentPane().add(scrollTable);
		
		table = new JTable(model);
		table.setFont(new Font("SansSerif", Font.PLAIN, 14));
		table.setBackground(new Color(0xA1, 0xCD, 0xA1));
		table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
		table.getTableHeader().setBackground(new Color(0x63, 0xAC, 0x63));
		scrollTable.setViewportView(table);
		
		Thread logThread = new Thread(new LogThread(this));
		logThread.setDaemon(true);
		logThread.start();
		
		Thread clientThread = new Thread(new ClientThread(this));
		clientThread.setDaemon(true);
		clientThread.start();
	}
}
