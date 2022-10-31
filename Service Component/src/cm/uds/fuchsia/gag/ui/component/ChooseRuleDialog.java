package cm.uds.fuchsia.gag.ui.component;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cm.uds.fuchsia.gag.model.configuration.Task;
import cm.uds.fuchsia.gag.model.specification.DecompositionRule;
import cm.uds.fuchsia.gag.specification.aspect.GAGGraphAspect;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChooseRuleDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private GAGGraphAspect graph;
	private Task task;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ChooseRuleDialog dialog = new ChooseRuleDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ChooseRuleDialog() {
		setBounds(100, 100, 149, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(4, 2, 0, 0));
		
		setTitle("Select a production");
		
		
		// remove ok button
		/*
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
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
		*/
	}
	
	public void setGraph( GAGGraphAspect gag) {
		this.graph = gag;
	}
	public void setRulesForTask(Task t) {
		this.task=t;
		ArrayList<DecompositionRule> rules = task.getService().getRules();
		ArrayList<DecompositionRule> applicableRules=this.graph.getApplicablesRules(t);
		
		//holder
		int i = 3;
		int j = 2;
		JPanel[][] panelHolder = new JPanel[i][j];    
		contentPanel.setLayout(new GridLayout(i,j));

		for(int m = 0; m < i; m++) {
		   for(int n = 0; n < j; n++) {
		      panelHolder[m][n] = new JPanel();
		      panelHolder[m][n].setLayout(new BorderLayout());
		      contentPanel.add(panelHolder[m][n]);
		   }
		}
		final ChooseRuleDialog refOnMe=this;
		for(int k=0;k<rules.size();k++) {
			DecompositionRule r=rules.get(k);
			JLabel label = new JLabel(r.getName());
			JButton activer =new JButton("Activate");
			activer.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					graph.applyRule(task, r);
					graph.update(graph);
					graph.getWindowContainer().updateUI();
					refOnMe.dispose();
				}
			});
			if(!applicableRules.contains(r)) {
				activer.setEnabled(false);
			}
			panelHolder[k][0].add(label);
			panelHolder[k][1].add(activer);
		}
		contentPanel.updateUI();
	}

}
