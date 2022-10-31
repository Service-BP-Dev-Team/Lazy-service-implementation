package cm.uds.fuchsia.gag.ui.component;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.border.LineBorder;

import cm.uds.fuchsia.gag.model.configuration.Configuration;
import cm.uds.fuchsia.gag.model.configuration.Task;
import cm.uds.fuchsia.gag.model.specification.Parameter;
import cm.uds.fuchsia.gag.model.specification.Service;
import cm.uds.fuchsia.gag.model.specification.SpecificActions;
import cm.uds.fuchsia.gag.specification.aspect.GAGGraphAspect;
import cm.uds.fuchsia.gag.util.UIUtil;

import java.awt.Color;

public class ChooseAxiomDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel panelAxiomInputsValue;
	private JComboBox axiomBox;
	private GAGGraphAspect gag;
	private JPanel panelAxiomChoice;
	private ArrayList<Service> axioms;
	private JButton okButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ChooseAxiomDialog dialog = new ChooseAxiomDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ChooseAxiomDialog() {
		setTitle("Select the axiom");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			panelAxiomChoice = new JPanel();
			contentPanel.add(panelAxiomChoice, BorderLayout.NORTH);
			{
				JLabel lblNewLabel = new JLabel("Select an axiom");
				panelAxiomChoice.add(lblNewLabel);
			}
			{
				axiomBox = new JComboBox();
				panelAxiomChoice.add(axiomBox);
			}
		}
		{
			panelAxiomInputsValue = new JPanel();
			//panelAxiomInputsValue.setBorder(new LineBorder(new Color(0, 0, 0)));
			contentPanel.add(panelAxiomInputsValue, BorderLayout.CENTER);
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
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public GAGGraphAspect getGag() {
		return gag;
	}

	public void setGag(GAGGraphAspect gag) {
		this.gag = gag;
	}
	
	public void showUI(){
		ArrayList<Service> axioms = gag.getAxioms();
		this.axioms=axioms;
		panelAxiomChoice.remove(axiomBox);
		JLabel[] list =new JLabel[axioms.size()];
		axiomBox= new JComboBox<>();
		for (int i=0;i<axioms.size();i++) {
			//list[i]=new JLabel(axioms.get(i).getName());
			axiomBox.addItem(axioms.get(i).getName());
		}
		axiomBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				fillDialogWithTextFields();
			}
		});
		fillDialogWithTextFields();
		//axiomBox= new JComboBox<JLabel>(list);
		panelAxiomChoice.add(axiomBox);
		
		
		setVisible(true);
	}
	
	public void fillDialogWithTextFields() {
		int selectedItemNumber = axiomBox.getSelectedIndex();
		Service axiom = axioms.get(selectedItemNumber);
		 JPanel[][] myPanes = UIUtil.layout(axiom.getInputParameters().size(), 2, this.panelAxiomInputsValue);
		 JTextField[] textFields = new JTextField[axiom.getInputParameters().size()];
		for(int i=0;i<axiom.getInputParameters().size();i++) {
			Parameter par = axiom.getInputParameters().get(i);
			myPanes[i][0].add(new JLabel(par.getName()));
			myPanes[i][1].setLayout(new BorderLayout());
			textFields[i]=new JTextField(30);
			myPanes[i][1].add(textFields[i]);
		}
		final ChooseAxiomDialog myRef=this;
		for( ActionListener al : this.okButton.getActionListeners() ) {
			this.okButton.removeActionListener( al );
	    }
		this.okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selectedItemNumber = axiomBox.getSelectedIndex();
				Service axiom = axioms.get(selectedItemNumber);
				ArrayList myInputsArray = new ArrayList();
				for(int i=0;i<axiom.getInputParameters().size();i++) {
					myInputsArray.add(textFields[i].getText());
				}
				Task t=gag.createRootTask(axiom,myInputsArray);
				Configuration conf= new Configuration();
				conf.setRoot(t);
				SpecificActions.doWhenAxiom(t, gag.getComponent());
				gag.setConfiguration(conf);
				gag.update(gag);
				myRef.dispose();
			}
		});
	}

}
