package cm.uds.fuchsia.gag.network;

import java.util.ArrayList;

import cm.uds.fuchsia.gag.model.configuration.Configuration;
import cm.uds.fuchsia.gag.model.configuration.Data;
import cm.uds.fuchsia.gag.model.configuration.Task;
import cm.uds.fuchsia.gag.model.specification.GAG;
import cm.uds.fuchsia.gag.model.specification.Service;
import cm.uds.fuchsia.gag.specification.aspect.GAGAspect;
import cm.uds.fuchsia.gag.ui.component.ComponentIHM;
import cm.uds.fuchsia.gag.util.Console;
import cm.uds.fuchsia.gag.util.EncapsulatedValue;

public class Component {

	private String componentName;
	private GAGAspect associateGAG;
	private ComponentIHM ihm;
	private ArrayList<Subscription> subscriptionList;
	private ComponentMiddleware middleware;
	
	public Component() {
		this.subscriptionList=new ArrayList<Subscription>();
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String cOMPONENT_NAME) {
		componentName = cOMPONENT_NAME;
	}

	public GAGAspect getAssociateGAG() {
		return associateGAG;
	}

	public void setAssociateGAG(GAGAspect aSSOCIATE_GAG) {
		associateGAG = aSSOCIATE_GAG;
	}

	public ComponentIHM getIhm() {
		return ihm;
	}

	public void setIhm(ComponentIHM iHM) {
		ihm = iHM;
	}

	public ArrayList<Subscription> getSubscriptionList() {
		return subscriptionList;
	}

	public void setSubscriptionList(ArrayList<Subscription> sUBSCRIPTION_LIST) {
		subscriptionList = sUBSCRIPTION_LIST;
	}

	public ComponentMiddleware getMiddleware() {
		return middleware;
	}

	public void setMiddleware(ComponentMiddleware middleware) {
		this.middleware = middleware;
	}

	// behavior when receiving a task, this method will be call by the
	// middleware (inServiceCall method)
	public void receiveTask(String senderName, Task t) {
		// GAGAspect gag = new GAGAspect(g);
		Task myTask = new Task();
		myTask.setInputs(cloneDatas(t.getInputs()));
		myTask.setOutputs(cloneDatas(t.getOutputs()));
		// find associate service
		Service serv = t.getService();
		Service myService = null;
		ArrayList<Service> services = this.associateGAG.getServices();
		for (int i = 0; i < services.size(); i++) {
			if (serv.getName().equals(services.get(i).getName())) {
				myService = services.get(i);
				break;
			}
		}
		// we set the configuration
		myTask.setService(myService);
		Configuration conf = new Configuration();
		conf.setRoot(myTask);
		associateGAG.setConfiguration(conf);

		// create output subscriptions
		for (int i = 0; i < myTask.getOutputs().size(); i++) {
			Subscription sub = new Subscription();
			sub.setRemote(true);
			sub.setData(myTask.getOutputs().get(i));
			sub.setComponentName(senderName);
			this.subscriptionList.add(sub);
		}

		// Store subscriptions to handle later notifcations of current undefined inputs 
		
		for (int i = 0; i < myTask.getInputs().size(); i++) {
			Data el = myTask.getInputs().get(i);
			EncapsulatedValue ecd = (EncapsulatedValue) el.getValue();
			ecd.addReference(null); //point reference to null, because the value is a global input (given remotely)
			if (ecd.isNull()) {
				
				Subscription sub = new Subscription();
				sub.setRemote(false);
				sub.setData(myTask.getInputs().get(i));
				sub.setComponentName(this.componentName);
				subscriptionList.add(sub);
				
			}

		}

		// put the right subscription list to ihm

		this.ihm.setSubscriptionsList(this.subscriptionList);

		// draw the graph
		this.ihm.disposeTheGraph(this.associateGAG);

		// update the UI
		this.ihm.updateUI();

	}

	// behavior when sending Task
	public void sendTask(Task t) {

		Task myTask = t;

		// Create subscriptions to later notify the component providing the
		// remote services (notifications of inputs)
		for (int i = 0; i < myTask.getInputs().size(); i++) {
			Data el = myTask.getInputs().get(i);
			EncapsulatedValue ecd = (EncapsulatedValue) el.getValue();
			if (ecd.isNull()) {
				Subscription sub = new Subscription();
				sub.setRemote(true);
				sub.setData(myTask.getInputs().get(i));
				sub.setComponentName(myTask.getService().getLocation());
				subscriptionList.add(sub);
			}else{
				
			}

		}

		// Store subscriptions to recall the remote call and to handle
		// notifications receipt
		for (int i = 0; i < myTask.getOutputs().size(); i++) {
			Subscription sub = new Subscription();
			sub.setRemote(false);
			sub.setData(myTask.getOutputs().get(i));
			sub.setComponentName(this.componentName);
			subscriptionList.add(sub);
		}
		// update the ui
		ihm.updateUI();
		// network aspect, we use the middleware
		middleware.outServiceCall(t.getService().getLocation(), t);

	}

	public void sendNotification(Subscription sub) {

		// remove the subscription in the list
		this.subscriptionList.remove(sub);

		// network aspect, we use the middleware
		middleware.outNotify(sub.getComponentName(), sub);
	}

	// behavior when receiving a notification, this method will be call by the
	// middleware (inNotification method)
	public void receiveNotification(Subscription subscription) {

		Console.debug("Start receiving notification");
		Data data = subscription.getData();

		Console.debug(subscription.getComponentName()+" <- "+subscription.getData().getName());
		// look for the subscriptions
		Subscription mySub = null;
		Console.debug("subscription list size "+ subscriptionList.size());
		for (int i = 0; i < subscriptionList.size(); i++) {
			Subscription sub = subscriptionList.get(i);
			Console.debug("mysub: "+sub.getComponentName()+" <- "+sub.getData().getName());
			if (sub.getData().getName().equals(data.getName())
					&& sub.getComponentName().equals(subscription.getComponentName())) {
				mySub = sub;
				Console.debug("notification ok");
				break;
			}
		}
		subscriptionList.remove(mySub);

		// update the gag

		// get the value
		Data dat = mySub.getData();
		EncapsulatedValue ecValData = (EncapsulatedValue)data.getValue();
		EncapsulatedValue ecValDat = (EncapsulatedValue)dat.getValue();
		ecValDat.setValue(ecValData.getValue());
		
		
		
		// draw the graph
		ihm.disposeTheGraph();

		// update the UI
		ihm.updateUI();
		
		//re-execute function as some functions are now executable
		Console.debug("I re-execute functions");
		Thread t=new Thread(new Runnable(){
			public void run(){
				Console.debug("Thread started");
				Configuration conf= (Configuration)ihm.getGraphLayout().getConfiguration() ;
				Console.debug("the size"+conf.getPendingLocalComputations().size());
						associateGAG.computeFunction( conf.getPendingLocalComputations());
				//notify is necessary
				ihm.getGraphLayout().notifyComponents();
						
				// update the UI
				ihm.updateUI();
				
			}
		});
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

	}
	
	private static ArrayList<Data> cloneDatas(ArrayList<Data> datas){
		ArrayList<Data> nDatas= new ArrayList<Data>();
		for(int i=0; i<datas.size();i++){
			Data d= datas.get(i).clone(); // false to increment the name generator
			nDatas.add(d);
		}
		return nDatas;
	}

}
