package cm.uds.fuchsia.gag.ui.component;

import javax.swing.JPopupMenu;

import cm.uds.fuchsia.gag.specification.aspect.GAGGraphAspect;

public class ChooseRuleMenu extends JPopupMenu{
	private GAGGraphAspect graph;

	public ChooseRuleMenu(GAGGraphAspect gag)
	{
		setLabel("Select a production");
	  this.graph= gag;	
	}
}
