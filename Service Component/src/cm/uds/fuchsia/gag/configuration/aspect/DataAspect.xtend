package cm.uds.fuchsia.gag.configuration.aspect
import cm.uds.fuchsia.gag.model.configuration.Data;
import cm.uds.fuchsia.gag.util.EncapsulatedValue

class DataAspect extends Data{
	
	new(){
		
	}
	new(Data d){
		this.parameter=d.parameter;
		this.value = d.value;
	}
	
	def String print() {
		var stToPrint = "?"
		var encVal = this.value as EncapsulatedValue;
		//Console.debug(encVal.toString);
		if(!encVal.^null){stToPrint=encVal.value.toString}
		return this.parameter.name+"="+stToPrint;
	}
}