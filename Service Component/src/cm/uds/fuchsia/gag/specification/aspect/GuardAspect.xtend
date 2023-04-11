package cm.uds.fuchsia.gag.specification.aspect

import cm.uds.fuchsia.gag.model.specification.Guard
import cm.uds.fuchsia.gag.model.configuration.Task
import cm.uds.fuchsia.gag.model.specification.FunctionDeclaration
import cm.uds.fuchsia.gag.model.configuration.PendingLocalFunctionComputation
import cm.uds.fuchsia.gag.configuration.aspect.PendingLocalFunctionComputationAspect

class GuardAspect extends Guard{
	
	
	
	new(){
		
	}
	new(Guard g){
		this.location=g.location;
		this.method=g.method;
	}
	
	def boolean isApplicable( Task t){
		var result=false;
		var funcDec= new FunctionDeclaration;
		funcDec.location = this.location;
		funcDec.method =this.method;
		var funcCall = new PendingLocalFunctionComputation;
		funcCall.functionDeclaration = funcDec;
		for(i:0 ..<t.inputs.size){
			funcCall.actualParameters.add(t.inputs.get(i));
		}
		//if (funcCall.isExecutable){
			result =funcCall.execute as Boolean ;
		//}
		
		return result;
		
	}
	
	
	// extention methods

	def Boolean isExecutable(PendingLocalFunctionComputation computation) {
		return new PendingLocalFunctionComputationAspect(computation).executable;
	}
	
	def Object execute(PendingLocalFunctionComputation computation) {
		return new PendingLocalFunctionComputationAspect(computation).execute;
	}
	
	
	
}