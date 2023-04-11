package cm.uds.fuchsia.gag.configuration.aspect

import cm.uds.fuchsia.gag.model.configuration.PendingLocalFunctionComputation
import cm.uds.fuchsia.gag.util.EncapsulatedValue
import groovy.lang.*
import cm.uds.fuchsia.gag.util.Console

class PendingLocalFunctionComputationAspect extends PendingLocalFunctionComputation {
	
	static String classPath ="E:\\PhD Recherche\\Implementation\\workspace-java\\GagApp\\bin";
	
	
	new(){
		
	}
	new(PendingLocalFunctionComputation pend){
		this.functionDeclaration=pend.functionDeclaration;
		this.actualParameters=pend.actualParameters;
		this.dataToCompute=pend.dataToCompute;
	}
	
	def boolean isExecutable() {
		var isexc =  true
		Console.debug(functionDeclaration.name+": am i executable ?");
		for(i:0 ..<this.actualParameters.size){
			var data =this.actualParameters.get(i);
			var ecData = data.value as EncapsulatedValue;
			if(ecData.isNull){
			   isexc=false;	
			}
		}
		return isexc;
	}
	
	def Object execute(){
		var binding = new Binding ;
		var res = null as Object;
		try {
			println("run code")
			

			val shell = new GroovyShell( /*currentClassLoader,*/ binding)
			var cl = shell.classLoader
			//cl.addClasspath(classPath);
			cl.addClasspath(this.functionDeclaration.location);
			var params="("
			for(i:0 ..<this.actualParameters.size){
				var d = this.actualParameters.get(i);
				var ecD = d.value as EncapsulatedValue;
				binding.setVariable('data'+i,ecD.value);
				if (i!=0) { params+=',' } 
				params+= 'data'+i;
				
				 
			}
			params+=')'
			// var htmlCleanedDescr = "MyCustomGAGGuard.staticIsRuleActivable()"
			var stringToExecute = this.functionDeclaration.method+params;
			//Console.debug(stringToExecute);
			res = shell.evaluate(stringToExecute)  // as Map<String, Object>
//			for (OutputPin port: _self.outputs) {
//				//_self.system.sharedMemory.put(portName, res.get(portName))
//			}
		} catch (Exception cnfe) {
			println("Failed to call Groovy script " + cnfe.message)
			cnfe.printStackTrace()
		}
		return res
	
	}
	
	
	
	// just for debug purpose
	def String prettyPrint(){
		var result="_ "+functionDeclaration.name+"(";
		for(i:0 ..<actualParameters.size){
			result+=actualParameters.get(i).fullDisplayName;
			if(i!=(actualParameters.size-1)){
				result+=",";
			}
		}
		result+=")";
		return result;
	}
	
	
	
}