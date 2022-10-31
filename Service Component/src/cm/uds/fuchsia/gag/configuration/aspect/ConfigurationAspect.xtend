package cm.uds.fuchsia.gag.configuration.aspect

import cm.uds.fuchsia.gag.model.configuration.Configuration
import cm.uds.fuchsia.gag.model.configuration.Task

class ConfigurationAspect extends Configuration{
	new(){
		
	}
	
	new(Configuration conf){
		this.pendingLocalComputations=conf.pendingLocalComputations;
		this.root = conf.root;
		
	}
	
	def String print() {
		return this.root.print();
	}
	
	
	// extension method
	def String print(Task task) {
		return new TaskAspect(task).print();
	}
	
}