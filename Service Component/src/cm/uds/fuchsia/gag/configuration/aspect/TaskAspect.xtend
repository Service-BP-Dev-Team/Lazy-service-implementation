package cm.uds.fuchsia.gag.configuration.aspect

import cm.uds.fuchsia.gag.model.configuration.Task
import cm.uds.fuchsia.gag.model.configuration.Data

class TaskAspect extends Task{
	
	new(){
		
	}
	
	new(Task t){
		this.appliedRule=t.appliedRule;
		this.inputs = t.inputs;
		this.outputs = t.outputs;
		this.subTasks = t.subTasks;
		this.service =t.service;
		this.open = t.open;
	}
	
	def String print() {
		var String conf = "";
		conf += this.service.name +"(" ;//+ "(isOpen=" + this.isIsOpen;
		if (!this.isOpen)
		{
			conf += " appliedRule=" + this.appliedRule;
			conf += ")[";
			var i = 1;
			for (Task t : this.subTasks) {
				conf += t.print();
				if (i < this.subTasks.size)
					conf += ", ";
			}
			conf += "]";
		
		}else {
			for (i:0 ..< this.inputs.size ) {
				val el= this.inputs.get(i);
				conf += el.print();
				if (i < this.inputs.size -1 )
					conf += ", ";
			}
			conf += ")[";
			for (i:0 ..< this.outputs.size ) {
				val el= this.outputs.get(i);
				conf += el.print();
				if (i < this.outputs.size - 1)
					conf += ", ";
			}
			conf += "]";
		}
		return conf;
	}
	
	
	// not really necessary except for debug purpose
	def String prettyPrint(){
		var result="(";
		for(i:0 ..<outputs.size){
			result+=outputs.get(i).fullDisplayName;
			if(i!=outputs.size-1){
				result+=",";
			}
		}
		result+=") = "+service.name+"(";
		for(i:0 ..<inputs.size){
			result+=inputs.get(i).fullDisplayName;
			if(i!=inputs.size-1){
				result+=",";
			}
		}
		result+=")";
		
		return result;
		
	}
	
	// extention methods
	def String print(Task task) {
		return new TaskAspect(task).print();
	}
	
	def String print(Data data) {
		return new DataAspect(data).print();
	}
	
}