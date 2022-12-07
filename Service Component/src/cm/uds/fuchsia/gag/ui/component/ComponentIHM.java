package cm.uds.fuchsia.gag.ui.component;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import cm.uds.fuchsia.gag.configuration.aspect.PendingLocalFunctionComputationAspect;
import cm.uds.fuchsia.gag.configuration.aspect.TaskAspect;
import cm.uds.fuchsia.gag.specification.aspect.GAGAspect;
import cm.uds.fuchsia.gag.specification.aspect.GAGGraphAspect;

import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.border.LineBorder;
import javax.swing.plaf.FontUIResource;

import cm.uds.fuchsia.gag.model.configuration.Configuration;
import cm.uds.fuchsia.gag.model.configuration.Data;
import cm.uds.fuchsia.gag.model.configuration.PendingLocalFunctionComputation;
import cm.uds.fuchsia.gag.model.configuration.Task;
import cm.uds.fuchsia.gag.model.specification.GAG;
import cm.uds.fuchsia.gag.network.Subscription;
import cm.uds.fuchsia.gag.util.Console;
import cm.uds.fuchsia.gag.util.EncapsulatedValue;
import cm.uds.fuchsia.gag.util.UIUtil;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

public class ComponentIHM {

	private JFrame frame;
	private JPanel panelConfigurationGraph;
	private GAGGraphAspect graphLayout;
	private JPanel panelConfValueContent;
	private JButton btnStart;
	private JPanel panelConfigurationEquations;
	private ArrayList<Subscription> subscriptionsList;
	private JPanel panelSubscriptionsContent;
	private JButton btnStop;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ComponentIHM window = new ComponentIHM();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ComponentIHM() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 528);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		try {
			// UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaWhiteVisionLookAndFeel");
			//UIManager.setLookAndFeel("com.alee.laf.WebLookAndFeel");
			UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
	        for (UIManager.LookAndFeelInfo look : looks) {
	            System.out.println(look.getClassName());
	        }
			//UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
	        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
			Logger.getLogger(ComponentIHM.class.getName()).log(Level.SEVERE, null, ex);
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		JMenuItem exitMenuItem = new JMenuItem("exit");
		fileMenu.add(exitMenuItem);

		JMenu toolMenu = new JMenu("Tool");
		menuBar.add(toolMenu);

		JMenu aboutMenu = new JMenu("About");
		menuBar.add(aboutMenu);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panelBtn = new JPanel();
		frame.getContentPane().add(panelBtn, BorderLayout.NORTH);

		JButton btnSave = new JButton("Save");
		panelBtn.add(btnSave);

		btnStart = new JButton("Start");
		panelBtn.add(btnStart);

		JButton btnPause = new JButton("Pause");
		panelBtn.add(btnPause);

		JButton btnResume = new JButton("Resume");
		panelBtn.add(btnResume);

		btnStop = new JButton("Stop");
		panelBtn.add(btnStop);

		JPanel panelMetaData = new JPanel();
		frame.getContentPane().add(panelMetaData, BorderLayout.SOUTH);
		panelMetaData.setPreferredSize(new Dimension(100, 159));
		panelMetaData.setLayout(new GridLayout(1, 0, 0, 0));

		JPanel panelConfValue = new JPanel();
		//panelMetaData.add(panelConfValue); /* old UI */
		panelConfValue.setLayout(new BorderLayout(0, 0));

		JPanel panelConValueTitle = new JPanel();
		panelConfValue.add(panelConValueTitle, BorderLayout.NORTH);

		JLabel lblConfigurationValues = new JLabel("Configuration Valuation");
		panelConValueTitle.add(lblConfigurationValues);

		panelConfValueContent = new JPanel();
		panelConfValue.add(panelConfValueContent, BorderLayout.CENTER);

		JPanel panelInputs = new JPanel();
		panelMetaData.add(panelInputs);
		panelInputs.setLayout(new BorderLayout(0, 0));

		JPanel panelInputValueTitle = new JPanel();
		panelInputs.add(panelInputValueTitle, BorderLayout.NORTH);

		JLabel lblInputVariables = new JLabel("Configuration Equations");
		panelInputValueTitle.add(lblInputVariables);

		panelConfigurationEquations = new JPanel();
		panelInputs.add(panelConfigurationEquations, BorderLayout.CENTER);

		JPanel panelOutputs = new JPanel();
		panelMetaData.add(panelOutputs);
		panelOutputs.setLayout(new BorderLayout(0, 0));

		JPanel panelOutputsTitle = new JPanel();
		panelOutputs.add(panelOutputsTitle, BorderLayout.NORTH);

		JLabel lblOutputTitle = new JLabel("Subscriptions");
		panelOutputsTitle.add(lblOutputTitle);
		
		panelSubscriptionsContent= new JPanel();
		panelOutputs.add(panelSubscriptionsContent, BorderLayout.CENTER);

		JPanel panelConfiguration = new JPanel();
		frame.getContentPane().add(panelConfiguration, BorderLayout.CENTER);
		panelConfiguration.setLayout(new BorderLayout(0, 0));

		JPanel panelConfigurationText = new JPanel();
		panelConfiguration.add(panelConfigurationText, BorderLayout.NORTH);
		panelConfigurationText.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panelConfigurationText.add(panel, BorderLayout.EAST);
		panel.setPreferredSize(new Dimension(300,panelConfigurationText.getPreferredSize().height));
		
		JPanel panel_1 = new JPanel();
		panelConfigurationText.add(panel_1, BorderLayout.CENTER);
		
		JLabel lblNewLabel = new JLabel("Graphic Visualization");
		panel_1.add(lblNewLabel);
		
		panelConfigurationGraph = new JPanel();
		panelConfiguration.add(panelConfigurationGraph, BorderLayout.CENTER);
		
		// new place for value
		JPanel panelForConfvalue = new JPanel();
		panelForConfvalue.setPreferredSize(new Dimension(300,400));
		panelConfiguration.add(panelForConfvalue,BorderLayout.EAST);
		panelForConfvalue.setLayout(new BorderLayout());

		panelMetaData.remove(panelForConfvalue);
		panelForConfvalue.add(panelConfValue,BorderLayout.CENTER);
		
	}

	public void disposeTheGraph(GAGAspect gag) {
		this.graphLayout = new GAGGraphAspect(gag);
		this.graphLayout.setComponent(gag.getComponent()); // to ensure that the graph is connected to the network
		this.graphLayout.setWindowContainer(this);
		this.graphLayout.dispose(panelConfigurationGraph);
	}
	
	public void disposeTheGraph() {
		
		this.graphLayout.dispose(panelConfigurationGraph);
	}
	
	public void stopExecution(){
		Configuration configuration = (Configuration) this.graphLayout.getConfiguration();
		if(configuration!=null){
			//stopping pending local computations
			configuration.setPendingLocalComputations(new ArrayList<PendingLocalFunctionComputation>());
			//removing subscriptions
			this.graphLayout.getComponent().setSubscriptionList(new ArrayList<Subscription>());
			
			// update the ui
			this.updateUI();
		}
	}

	public void updateUI() {

		this.updateConfigurationValuePanel();
		this.updateConfigurationEquationsPanel();
		this.updateSubscriptionsPanel();
	}

	public void updateConfigurationValuePanel() {

		ArrayList<Task> allTasks = this.graphLayout.getAllTasks(null);

		// count the line numbers
		int rows = 0;
		for (int i = 0; i < allTasks.size(); i++) {
			Task el = allTasks.get(i);
			rows += el.getInputs().size() + el.getOutputs().size();
		}
		JPanel[][] panes = UIUtil.layout(rows, 3, panelConfValueContent);
		int cpt = 0;
		for (int i = 0; i < allTasks.size(); i++) {
			Task el = allTasks.get(i);
			for (int k = 0; k < el.getInputs().size(); k++) {
				Data in = el.getInputs().get(k);
				// panes[cpt][0].add( new
				// JLabel(el.getService().getName()+"."+in.getParameter().getName()));
				panes[cpt][0].add(new JLabel(in.getFullDisplayName()));
				panes[cpt][1].add(new JLabel("="));
				EncapsulatedValue ecD = (EncapsulatedValue) in.getValue();
				panes[cpt][2].add(new JLabel((ecD.isNull()) ? "?" : ecD.getValue().toString()));
				cpt++;
			}
			for (int k = 0; k < el.getOutputs().size(); k++) {
				Data out = el.getOutputs().get(k);
				// panes[cpt][0].add( new
				// JLabel(el.getService().getName()+"."+out.getParameter().getName()));
				panes[cpt][0].add(new JLabel(out.getFullDisplayName()));
				panes[cpt][1].add(new JLabel("="));
				EncapsulatedValue ecD = (EncapsulatedValue) out.getValue();
				panes[cpt][2].add(new JLabel((ecD.isNull()) ? "?" : ecD.getValue().toString()));
				cpt++;
			}
		}

		// update the font
		changeFont(panelConfValueContent, new Font("Arial", 0, 15), 3);

		panelConfValueContent.updateUI();

	}

	public void setVisible(boolean visible) {
		this.frame.setVisible(visible);

		// add listener event;
		this.btnStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ChooseAxiomDialog dialog = new ChooseAxiomDialog();
				dialog.setGag(graphLayout);
				dialog.showUI();
			}

		});
		
		this.btnStop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				stopExecution();
			}
		});
	}

	public GAGGraphAspect getGraphLayout() {
		return graphLayout;
	}

	public void setGraphLayout(GAGGraphAspect graphLayout) {
		this.graphLayout = graphLayout;
	}

	public void setTitle(String title) {
		frame.setTitle(title);
	}
	
	public void updateSubscriptionsPanel(){
		ArrayList<Subscription> remoteSubscriptions = new ArrayList<Subscription>();
		//get remote subscriptions
		for(int i=0; i< this.graphLayout.getComponent().getSubscriptionList().size();i++){
			Subscription el = this.graphLayout.getComponent().getSubscriptionList().get(i);
			if(el.isRemote()){
			remoteSubscriptions.add(el);
			}
		}
		JPanel[][] panes = UIUtil.layout(remoteSubscriptions.size(), 3, panelSubscriptionsContent);
		
		for(int i=0;i<remoteSubscriptions.size();i++){
			Subscription el = remoteSubscriptions.get(i);
			panes[i][0].add(new JLabel(el.getData().getFullDisplayName()));
			panes[i][1].add(new JLabel("->"));
			panes[i][2].add(new JLabel(el.getComponentName()));
		}
		// update the font
		changeFont(panelSubscriptionsContent, new Font("Arial", 0, 15), 3);
		panelSubscriptionsContent.updateUI();
		
	}

	public void updateConfigurationEquationsPanel() {
		ArrayList<Task> allTasks = this.graphLayout.getAllTasks(null);
		Configuration conf = (Configuration) this.graphLayout.getConfiguration();
		// count the line numbers
		int rows = 0;
		for (int i = 0; i < allTasks.size(); i++) {
			Task el = allTasks.get(i);
			if (el.isOpen() && !el.getService().isRemote()) {
				rows++; // a line for the service equation eg. (x)=s(y)
				// add line for inputs ref
			}
			for (int j = 0; j < el.getInputs().size(); j++) {
				Data dat = el.getInputs().get(j);
				if (!EncapsulatedValue.isGlobalInput(dat, conf)) {
					rows++; // it means that the data is computed from another data;
					// we add a line to mention the computation;
				}

			}

			if (!el.isOpen()) {
				// add line to show outputs computations
				rows += el.getOutputs().size();
			}
		}

		JPanel[][] panes = UIUtil.layout(rows, 3, panelConfigurationEquations);
		// redo the pool to place the equations in the panel

		int count = 0;
		for (int i = 0; i < allTasks.size(); i++) {
			Task el = allTasks.get(i);
			if (el.isOpen() && !el.getService().isRemote()) {
				// add a line for the service equation eg. (x)=s(y)
				TaskAspect ta = new TaskAspect(el);
				String print = ta.prettyPrint();
				String[] leftright = print.split("=");
				panes[count][0].add(new JLabel(leftright[0]));
				panes[count][1].add(new JLabel("="));
				panes[count][2].add(new JLabel(leftright[1]));
				count++;
			}
			// add lines for inputs ref
			for (int j = 0; j < el.getInputs().size(); j++) {
				Data dat = el.getInputs().get(j);
				Object ref = EncapsulatedValue.getComputingReference(dat, conf);
				if (ref != null) {
					// it means that the data is computed from another data;
					// we add a line to mention the computation;
					String rightPart = "";
					if (ref instanceof PendingLocalFunctionComputation) {
						rightPart = (new PendingLocalFunctionComputationAspect((PendingLocalFunctionComputation) ref))
								.prettyPrint();
					} else {
						//rightPart = "_ id(" + ((Data) ref).getFullDisplayName() + ")";
						rightPart = "" + ((Data) ref).getFullDisplayName() + "";
					}
					panes[count][0].add(new JLabel(dat.getFullDisplayName()));
					panes[count][1].add(new JLabel("="));
					panes[count][2].add(new JLabel(rightPart));
					count++;
				}

			}
			if (!el.isOpen()) {
				// add line to show outputs computations
				for (int j = 0; j < el.getOutputs().size(); j++) {
					Data dat = el.getOutputs().get(j);
					Object ref = EncapsulatedValue.getComputingReference(dat, conf);
					if (ref != null) {
						// it means that the data is computed from another data;
						// we add a line to mention the computation;
						String rightPart = "";
						if (ref instanceof PendingLocalFunctionComputation) {
							rightPart = (new PendingLocalFunctionComputationAspect(
									(PendingLocalFunctionComputation) ref)).prettyPrint();
						} else if (ref instanceof Data) {
							//rightPart = "_ id(" + ((Data) ref).getFullDisplayName() + ")";
							rightPart = "" + ((Data) ref).getFullDisplayName() + "";
						}
						panes[count][0].add(new JLabel(dat.getFullDisplayName()));
						panes[count][1].add(new JLabel("="));
						panes[count][2].add(new JLabel(rightPart));
						count++;
					}
				}

			}
		}
		// update the font
				changeFont(panelConfigurationEquations, new Font("Arial", 0, 15), 3);
	}

	private void changeFont(Container p, Font f, int level) {
		Container mycomponent=p;
		//specific case of jscrollpane
		if(p instanceof JScrollPane) {
			Component view = ((JScrollPane) p).getViewport().getView();
			if(view instanceof Container) {
				mycomponent=(Container)view;
			}
		}
		if (level > 0) {
			Component[] alls;
			
				alls = mycomponent.getComponents();
			
			for (int i = 0; i < alls.length; i++) {
				Component el = alls[i];
				el.setFont(f);
				if (el instanceof Container) {
					changeFont((Container)el, f, level - 1);
				}
				//Console.debug(el.getClass());
			}
		}

	}

	public ArrayList<Subscription> getSubscriptionsList() {
		return subscriptionsList;
	}

	public void setSubscriptionsList(ArrayList<Subscription> subscriptionsList) {
		this.subscriptionsList = subscriptionsList;
	}
	
	

}
