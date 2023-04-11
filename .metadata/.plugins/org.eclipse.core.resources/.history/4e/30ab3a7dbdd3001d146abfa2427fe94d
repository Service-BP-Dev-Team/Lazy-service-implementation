package cb;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import c.one.Util;

import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.Semaphore;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class MakeDecisionDialog extends JFrame {

	private final JPanel contentPanel = new JPanel();
	private JPanel panelText;
	private JComboBox comboBoxDecision;
	private JComboBox comboBoxGrant;
	private JLabel lblSystem;
	private JButton okButton;
	private JButton cancelButton;
	private Treatment treatment;

	public static String[] availableDecisionChoices= new String[] {"Accept","Reject"};
	public static String[] availableGrantChoices= new String[] {"", "Grant A", "Grant B", "Grant C", "Grant D"};
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			String decision=null;
			String grant=null;
		    for (int i = 0; i < args.length; i++) {
		        if((args[i].equals("--decision") || args[i].equals("-d")) && (i+1) < args.length) {
		        	decision = args[i+1];
		        }
		        if((args[i].equals("--grant") || args[i].equals("-g")) && (i+1) < args.length) {
		        	grant = args[i+1];
		        }
		    }
		    
		    
			MakeDecisionDialog dialog = new MakeDecisionDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.treatment=new Treatment();
			dialog.treatment.setDecision(decision.equals("yes")?true:false);
			dialog.treatment.setGrant(grant);
			dialog.updateTreatment(dialog.treatment);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public MakeDecisionDialog() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel bodyPanel = new JPanel();
			contentPanel.add(bodyPanel);
			bodyPanel.setLayout(new GridLayout(0, 2, 0, 0));
			{
				JLabel lblDecision = new JLabel("Decision");
				bodyPanel.add(lblDecision);
			}
			{
				comboBoxDecision = new JComboBox();
				comboBoxDecision.setModel(new DefaultComboBoxModel(availableDecisionChoices));
				bodyPanel.add(comboBoxDecision);
			}
			{
				JLabel lblGrant = new JLabel("Grant");
				bodyPanel.add(lblGrant);
			}
			{
				comboBoxGrant = new JComboBox();
				comboBoxGrant.setModel(new DefaultComboBoxModel(availableGrantChoices));
				bodyPanel.add(comboBoxGrant);
			}
		}
		{
			panelText = new JPanel();
			contentPanel.add(panelText, BorderLayout.NORTH);
			{
				lblSystem = new JLabel("New label");
				panelText.add(lblSystem);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		
	}
	
	public void updateTreatment(Treatment systTreatment) {
		setVisible(true);
		
		Treatment result=new Treatment(false,"\'NilResult");
		if(systTreatment.getDecision()) {
			lblSystem.setText("The system propose to accept the application and suggest the "+systTreatment.getGrant()+".");
		}else {
			lblSystem.setText("The system propose to reject the application.");
		}
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int index=comboBoxDecision.getSelectedIndex();
				if(index==-1) {index=1;}// by default we reject
				String txtDecision = availableDecisionChoices[index];
				System.out.println("the txt decision is: "+txtDecision);
				if(txtDecision.equals("Accept")) {
					result.setDecision(true);
					int grantIndex= comboBoxGrant.getSelectedIndex();
					if(grantIndex==-1) {grantIndex=0;}//by the default we place an empty grant
					result.setGrant(availableGrantChoices[grantIndex]);
				}else {
					result.setDecision(false);
					result.setGrant("");
				}
				makeDecision(result);
				setVisible(false);
				dispose();
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				makeDecision(result);
				setVisible(false);
				dispose();
			}
		});
		
		this.addWindowListener(new WindowAdapter() { 
		    @Override public void windowClosed(WindowEvent e) { 
		    	makeDecision(result);
				dispose();
			    }
			  });
	}

    public void makeDecision(Treatment treatment) {
    	try {
   		 String path=Util.class.getProtectionDomain().getCodeSource().getLocation().getPath();
   			path=path.replaceAll("%20", " ");
   			path+="\\..\\myBin\\treatmentFile.req";
              FileOutputStream fileOut = new FileOutputStream(path);
              ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
              objectOut.writeObject(treatment);
              objectOut.close();
              System.out.println("The Object  was succesfully written to a file");

          } catch (Exception ex) {
              ex.printStackTrace();
          }
    }

}
