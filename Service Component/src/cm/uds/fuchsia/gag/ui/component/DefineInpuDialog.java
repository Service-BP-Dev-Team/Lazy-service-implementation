package cm.uds.fuchsia.gag.ui.component;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import cm.uds.fuchsia.gag.model.configuration.Data;
import cm.uds.fuchsia.gag.model.configuration.Task;
import cm.uds.fuchsia.gag.model.specification.Parameter;
import cm.uds.fuchsia.gag.model.specification.Service;
import cm.uds.fuchsia.gag.util.EncapsulatedValue;
import cm.uds.fuchsia.gag.util.UIUtil;

import javax.swing.JPanel;
import cm.uds.fuchsia.gag.specification.aspect.GAGGraphAspect;
public class DefineInpuDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Task task;
	private JLabel message;
	private JPanel panelFields;
	private JButton okButton;
	private JButton cancelButton;
	private GAGGraphAspect gagAspect;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DefineInpuDialog dialog = new DefineInpuDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DefineInpuDialog() {
		setBounds(100, 100, 450, 300);
		setTitle("Dialog inputs");
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			contentPanel.setLayout(new BorderLayout());
			message= new JLabel("Provide remaining inputs" );
			JPanel panelMessage = new JPanel();
			panelMessage.setLayout(new FlowLayout());
			panelMessage.add(message);
			contentPanel.add(panelMessage,BorderLayout.NORTH);
			panelFields=new JPanel();
			contentPanel.add(panelFields,BorderLayout.CENTER);
		}
		getContentPane().add(contentPanel, BorderLayout.CENTER);
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
	
	

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
		Service s=task.getService();
		 JPanel[][] myPanes = UIUtil.layout(s.getInputParameters().size(), 2, this.panelFields);
		 JTextField[] textFields = new JTextField[s.getInputParameters().size()];
		for(int i=0;i<s.getInputParameters().size();i++) {
			Parameter par = s.getInputParameters().get(i);
			myPanes[i][0].add(new JLabel(par.getName()));
			myPanes[i][1].setLayout(new BorderLayout());
			textFields[i]=new JTextField(30);
			Data data = task.getInputs().get(i);
			if(data!=null) {
			EncapsulatedValue ecData = (EncapsulatedValue) data.getValue(); 
			  if(ecData!=null && ecData.getValue()!=null) {
				  textFields[i].setText(ecData.getValue().toString());
				  textFields[i].setEditable(false);
			  }}
			
			myPanes[i][1].add(textFields[i]);
		}
		setTitle(s.getName()+" inputs");
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ArrayList myInputsArray = new ArrayList();
				for(int i=0;i<s.getInputParameters().size();i++) {
					String txt=textFields[i].getText().trim();
					if(txt.equals("\'Nil")) {
						txt=null;
					}
					else if(!txt.equals("")) {
					Data data = task.getInputs().get(i);
					EncapsulatedValue ecData = (EncapsulatedValue) data.getValue(); 
					ecData.setValue(textFields[i].getText());
					data.setValue(ecData);}
				}
				setVisible(false);
				dispose();
				gagAspect.notifyComponents();
				gagAspect.getWindowContainer().updateUI();
				
			}
		});
	}

	public void setGagAspect(GAGGraphAspect aspect) {
	 this.gagAspect=aspect;
	}

	public GAGGraphAspect getGagAspect() {
		return gagAspect;
	}
	
	

}
