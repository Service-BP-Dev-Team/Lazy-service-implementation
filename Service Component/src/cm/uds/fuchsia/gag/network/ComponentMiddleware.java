package cm.uds.fuchsia.gag.network;

import cm.uds.fuchsia.gag.model.configuration.Task;

public interface ComponentMiddleware {

	public void inServiceCall(String expeditor, Task task); 
	public void inNotify(String expeditor, Subscription subscription);

	public abstract void outServiceCall(String expeditor, Task task);
	public abstract void outNotify(String expeditor, Subscription subscription);
}
