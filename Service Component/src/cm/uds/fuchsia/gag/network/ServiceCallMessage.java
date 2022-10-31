package cm.uds.fuchsia.gag.network;

import cm.uds.fuchsia.gag.model.configuration.Task;

public class ServiceCallMessage extends Message{

	private Task task;

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
	
	
}
