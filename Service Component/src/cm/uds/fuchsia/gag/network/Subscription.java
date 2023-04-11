package cm.uds.fuchsia.gag.network;

import java.io.Serializable;

import cm.uds.fuchsia.gag.model.configuration.Data;

public class Subscription implements Serializable{
	
	private Data data;
	private boolean remote=false;
	private String componentName;  // different from the current component when the subscription does'nt belong to it 
	
	
	
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	public boolean isRemote() {
		return remote;
	}
	public void setRemote(boolean remote) {
		this.remote = remote;
	}
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	
	
	
	
	
	
	
	

}
