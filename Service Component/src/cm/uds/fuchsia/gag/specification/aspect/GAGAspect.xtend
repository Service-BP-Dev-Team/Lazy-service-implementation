package cm.uds.fuchsia.gag.specification.aspect

import cm.uds.fuchsia.gag.model.specification.GAG
import cm.uds.fuchsia.gag.model.configuration.Configuration
import cm.uds.fuchsia.gag.model.configuration.Task
import cm.uds.fuchsia.gag.model.configuration.Data
import cm.uds.fuchsia.gag.model.specification.Service
import java.util.ArrayList
import cm.uds.fuchsia.gag.util.Console
import cm.uds.fuchsia.gag.util.EncapsulatedValue
import cm.uds.fuchsia.gag.model.specification.DecompositionRule
import cm.uds.fuchsia.gag.model.specification.IdExpression
import cm.uds.fuchsia.gag.model.specification.FunctionExpression
import cm.uds.fuchsia.gag.model.configuration.PendingLocalFunctionComputation
import java.util.List
import cm.uds.fuchsia.gag.model.specification.Guard
import cm.uds.fuchsia.gag.configuration.aspect.PendingLocalFunctionComputationAspect
import cm.uds.fuchsia.gag.configuration.aspect.ConfigurationAspect
import cm.uds.fuchsia.gag.network.Component
import cm.uds.fuchsia.gag.network.Subscription

class GAGAspect extends GAG{
	private Component component;
	
	def Component getComponent(){
		return component;
	}
	def void setComponent(Component comp){
		component=comp;
	}
	new(){
		
	}
	new (GAG g){
		this.name=g.name;
		this.configuration = g.configuration;
		this.services=g.services;
	}
	
	def void initExecution(){
		if(this.configuration===null){this.configuration = new Configuration()}
	}
	
	def void run() {
		
		initExecution();
		val conf = this.configuration as Configuration;
		
		
		
		

		// get the axioms
		chooseTheAxiom();

		// run the conf
		var openTask = getOpenTask(conf.root);
		while (openTask.size != 0) {
			var task = chooseTask(openTask);
			var rule = chooseRule(task);
			if(rule !=null){
			applyRule(task, rule);}
			Console.debug("La configuration resultante est " + conf.print());
			openTask = getOpenTask( conf.root);
		}
		Console.debug("Ex�cution termin�e !");
	}
	
	def void runWithExternalOuputInterface(OutputInterface  OI) {
		
		initExecution();
		val conf = this.configuration as Configuration;
		
		
		
		

		// get the axioms
		chooseTheAxiom();
		OI.update(this);
		// run the conf
		var openTask = getOpenTask(conf.root);
		while (openTask.size != 0) {
			var task = chooseTask(openTask);
			var rule = chooseRule(task);
			if(rule !=null){
			applyRule(task, rule);}
			Console.debug("La configuration resultante est " + conf.print());
			OI.update(this);
			openTask = getOpenTask( conf.root);
		}
		Console.debug("Ex�cution termin�e !");
	}

	
	def ArrayList<Service> getAxioms() {
		val services = this.services;
		val axioms = new ArrayList<Service>();
			for (i : 0 ..< services.size) {
				val element = services.get(i)
				if (element.isAxiom) {
					axioms.add(services.get(i));
				}
			}
		return axioms;
			
		}
	
	def void chooseTheAxiom() {
		val services = this.services;
		val axioms = this.axioms;
		Console.debug("Veuillez choisir le service axiome de d�marrage parmi les services suivants : ");
		var txtAf = "";
		for (i : 0 ..< axioms.size) {
			val element = axioms.get(i) as Service;
			Console.debug((i + 1) + "- " + element.name)
		// txtAf += " " + (i + 1) + "- " + element.name;
		}
		// Console.debug(txtAf);
		val choice = Console.readConsoleLine(txtAf);
		val id = Integer.parseInt(choice);
		val serviceChoice = axioms.get(id - 1) as Service;
		val conf = this.configuration as Configuration
		conf.root= createRootTask(serviceChoice)
	}
	
	def Task createRootTask(Service serviceChoice, ArrayList<Object> inputParams){
		var root = new Task();
		root.service = serviceChoice;
		for (i : 0 ..< root.service.inputParameters.size) {
			var data = new Data();
			data.parameter = root.service.inputParameters.get(i);
			
			val ecD = new EncapsulatedValue(inputParams.get(i));
			ecD.containerRef=data;
			data.value = ecD;
			root.inputs.add(data);
		}
		for (i : 0 ..< root.service.outputParameters.size) {
			var data = new Data();
			data.parameter = root.service.outputParameters.get(i);
			val ecD= new EncapsulatedValue;
			ecD.containerRef=data;
			data.value = ecD;
			root.outputs.add(data);
		}
		return root;
	}
	
	
	def Task createRootTask(Service serviceChoice){
		var root = new Task();
		root.service = serviceChoice;
		Console.debug("Veuillez fournir les valeurs des entr�es de l'axiome ");
		for (i : 0 ..< root.service.inputParameters.size) {
			var data = new Data();
			data.parameter = root.service.inputParameters.get(i);
			Console.debug("Veuillez entrer la valeur du param�tre " + data.parameter.name);
			val ecD= new EncapsulatedValue(Console.readConsoleLine(""));
			data.value =ecD ;
			ecD.containerRef = data;
			root.inputs.add(data);
		}
		for (i : 0 ..< root.service.outputParameters.size) {
			var data = new Data();
			data.parameter = root.service.outputParameters.get(i);
			val ecD= new EncapsulatedValue;
			data.value =ecD ;
			ecD.containerRef = data;
			root.outputs.add(data);
		}
		return root;
	}

	def Task chooseTask(ArrayList<Task> openTasks) {
		Console.debug("Veuillez choisir la t�che � traiter parmi les t�ches suivantes : ");
		var txtAf = "";
		for (i : 0 ..< openTasks.size) {
			val element = openTasks.get(i);
			Console.debug((i + 1) + "- " + element.service.name)
		// txtAf += " " + (i + 1) + "- " + element.service.name;
		}
		val choice = Console.readConsoleLine(txtAf);
		val id = Integer.parseInt(choice);
		return openTasks.get(id - 1);
	}

	
	def DecompositionRule chooseRule(Task t) {
		
		var applicableRules =new ArrayList<DecompositionRule> ;
		for (i : 0 ..< t.service.rules.size) {
			val element = t.service.rules.get(i);
			var guard =element.guard;
			if(guard ==null || guard.isApplicable(t)){
				applicableRules.add(element);
			}
			
		
		}
		if(applicableRules.size!=0){
			Console.debug("Veuillez choisir la r�gle de d�composition � appliquer parmi les r�gles suivantes : ");
			var txtAf = "";
			for(i:0 ..< applicableRules.size){
				Console.debug((i + 1) + "- " + applicableRules.get(i).name)
			}
			val choice = Console.readConsoleLine(txtAf);
			val id = Integer.parseInt(choice);
			val rule = applicableRules.get(id - 1);
			return rule;
		}else {
			Console.debug("Aucune r�gle de d�composition n'est actuellement applicable pour cette t�che ");
			Console.readConsoleLine("");
			return null;
		}
		

	}
	def ArrayList<DecompositionRule> getApplicablesRules(Task t){
		var applicableRules =new ArrayList<DecompositionRule> ;
		for (i : 0 ..< t.service.rules.size) {
			val element = t.service.rules.get(i);
			var guard =element.guard;
			if(guard ==null || guard.isApplicable(t)){
				applicableRules.add(element);
			}
			
		
		}
		return applicableRules;
	}
	

	def ArrayList<Task> getOpenTask(Task root) {
		val openTasks = new ArrayList<Task>();
		if (root.isOpen) {
			openTasks.add(root);
		} else {
			for (i : 0 ..< root.subTasks.size) {
				val element = root.subTasks.get(i)
				val subOpenTasks = getOpenTask( element);
				openTasks.addAll(subOpenTasks);
			}
		}
		return openTasks;
	}
	
	def ArrayList<Task> getAllTasks(Task root) {
		var myroot=root;
		if(root===null){
			myroot=(this.configuration as Configuration).root;
		}
		val allTasks = new ArrayList<Task>();
		
			allTasks.add(myroot);
		
			for (i : 0 ..< myroot.subTasks.size) {
				val element = myroot.subTasks.get(i)
				val subAllTasks = getAllTasks( element);
				allTasks.addAll(subAllTasks);
			}
		
		return allTasks;
	}


	def void applyRule(Task t, DecompositionRule r) {
		t.appliedRule = r.name;
		t.open = false;
		// t.subTasks = new EList<Task>();
		for (i : 0 ..< r.subServices.size) {
			val element = r.subServices.get(i)
			var st = new Task();
			initTask( st, element);

			t.subTasks.add(st);
		}

		// code for the semantic rule here
		var conf = this.configuration as Configuration;
		var context = new ArrayList<Task>();
		context.add(t);
		context.addAll(t.subTasks);
		for (i : 0 ..< r.semantic.equations.size) {
			var eq = r.semantic.equations.get(i);
			var String[] ref1 = #[eq.leftpart.serviceName, eq.leftpart.parameterName];

			var data1 = findReference( ref1, context)

			if (eq.rightpart instanceof IdExpression) {
				var data2 = null as Data;
				val rightPartIdExpression = eq.rightpart as IdExpression;
				val String[] ref2 = #[rightPartIdExpression.serviceName, rightPartIdExpression.parameterName];
				data2 = findReference( ref2, context);
				var ecData1 = data1.value as EncapsulatedValue;
				ecData1.addReference(data2.value as EncapsulatedValue);
			} else {
				var func = eq.rightpart as FunctionExpression;
				var ecData1 = data1.value as EncapsulatedValue;
				var runningFunction = new PendingLocalFunctionComputation;
				runningFunction.dataToCompute = data1;
				runningFunction.functionDeclaration = func.function;
				for (k : 0 ..< func.idExpressions.size) {
					var elId = func.idExpressions.get(k);
					val String[] ref = #[elId.serviceName, elId.parameterName];
					var data = findReference( ref, context);
					runningFunction.actualParameters.add(data);
				}
				
				conf.pendingLocalComputations.add(runningFunction);
			}

		}
		
		//code to compute function
		this.computeFunction(conf.pendingLocalComputations);
		
		//send remote task to remote component, only applied when executing with the network
		if(component!=null){
			for(i:0 ..<t.subTasks.size){
				var el=t.subTasks.get(i);
				if(el.service.remote){
					// send the task to remote component
					component.sendTask(el);
				}
			}
			
		}
		
		//notify components
		this.notifyComponents();
	}

	def cm.uds.fuchsia.gag.model.configuration.Data findReference(String[] ref, ArrayList<Task> tasks) {
		var objectRef = null as cm.uds.fuchsia.gag.model.configuration.Data;
		var serviceName = ref.get(0).toString.trim;
		var serviceParameter = ref.get(1).toString.trim;
		 Console.debug(serviceName+"."+serviceParameter);
		for (i : 0 ..< tasks.size) {
			var element = tasks.get(i);
			if (element.service.name.equals(serviceName)) {
				// we look in inputs and outputs to find the parameter
				for (j : 0 ..< element.inputs.size) {
					if (element.inputs.get(j).parameter.name.equals(serviceParameter)) {
						objectRef = element.inputs.get(j);
					 Console.debug('i found');
					}
				}
				for (j : 0 ..< element.outputs.size) {
					if (element.outputs.get(j).parameter.name.equals(serviceParameter)) {
						objectRef = element.outputs.get(j);
					 Console.debug('i found');
					}
				}
			}
		}
		return objectRef;
	}

	// method to execute functions
	def void computeFunction(List<PendingLocalFunctionComputation> runningFunctions) {

		var executableFunctions = new ArrayList<PendingLocalFunctionComputation>
		for (i : 0 ..< runningFunctions.size) {
			var func = runningFunctions.get(i);
			if (func.executable && !func.terminated) {
				executableFunctions.add(func);
			}
		}
		while (executableFunctions.size != 0) {
			for (i : 0 ..< executableFunctions.size) {
				//execute the function
				var elFunc = executableFunctions.get(i);
				var result = elFunc.execute;
				//terminate the function
				elFunc.terminated = true;
				var ecObj= elFunc.dataToCompute.value as EncapsulatedValue;
				ecObj.value = result;
								
			}
			
			//runningFunctions.removeAll(executableFunctions); // remove the executed function
			// we do not remove the terminated function any more, since the field terminated inform whether the function is terminated or not 
			executableFunctions = new ArrayList<PendingLocalFunctionComputation> //re-initialize the candidate function list
			for (i : 0 ..< runningFunctions.size) {
				var func = runningFunctions.get(i);
				if (func.executable && !func.terminated) {
					executableFunctions.add(func);
				}
			}
		}

	}
	
	
	
	
	
	

	def void initTask(Task t, Service s) {
		t.service = s;
		t.open = true;
		for (i : 0 ..< s.inputParameters.size) {
			var data = new Data;
			data.parameter = s.inputParameters.get(i);
			val ecD= new EncapsulatedValue;
			data.value =ecD ;
			ecD.containerRef = data;
			t.inputs.add(data);
		}
		for (i : 0 ..< s.outputParameters.size) {
			var data = new Data;
			data.parameter = s.outputParameters.get(i);
			val ecD= new EncapsulatedValue;
			data.value =ecD ;
			ecD.containerRef = data;
			t.outputs.add(data);
		}
	}
	
	// method to notify component across the network
	def void notifyComponents(){
		if(component!=null){
			var array = new ArrayList<Subscription>;
			array.addAll(component.subscriptionList);
			for(i:0 ..<array.size){
				var sub=array.get(i);
				var data = sub.data;
				var ecData = data.value as EncapsulatedValue;
				if(!ecData.^null){
					component.sendNotification(sub);
				}
			}
		}
	}
	
	// extention methods
	
	def boolean isApplicable(Guard guard, Task task) {
		//throw new UnsupportedOperationException("TODO: auto-generated method stub")
		var guardAspect=new GuardAspect(guard);
		return guardAspect.isApplicable(task);
	}
	
	def Boolean isExecutable(PendingLocalFunctionComputation computation) {
		return new PendingLocalFunctionComputationAspect(computation).executable;
	}
	
	def Object execute(PendingLocalFunctionComputation computation) {
		return new PendingLocalFunctionComputationAspect(computation).execute;
	}
	
	
	
	def String print(Configuration configuration) {
		return (new ConfigurationAspect(configuration)).print;
	}
}