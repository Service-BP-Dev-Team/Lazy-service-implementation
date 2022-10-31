package cm.uds.fuchsia.gag.model.specification;

import cm.uds.fuchsia.gag.model.configuration.Data;
import cm.uds.fuchsia.gag.model.configuration.Task;
import cm.uds.fuchsia.gag.network.Component;
import cm.uds.fuchsia.gag.network.Subscription;

public class SpecificActions {

	
	public static void doWhenAxiom(Task t, Component component){
		if(t.getService().getExampleSignature().equals("book-order-example")){
			Data out = t.getOutputs().get(0);
			Subscription sub= new Subscription();
			sub.setComponentName("A");
			sub.setData(out);
			sub.setRemote(true);
			component.getSubscriptionList().add(sub);
			
			
		}
		
		
	}
}
