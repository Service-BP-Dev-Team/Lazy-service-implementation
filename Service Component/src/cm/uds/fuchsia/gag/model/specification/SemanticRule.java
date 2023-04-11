package cm.uds.fuchsia.gag.model.specification;

import java.io.Serializable;
import java.util.ArrayList;

import jakarta.xml.bind.annotation.XmlElement;

public class SemanticRule  implements Serializable{

	private ArrayList<FunctionDeclaration> functionDeclarations;
	private ArrayList<Equation> equations;
	
	public SemanticRule() {
		functionDeclarations = new ArrayList<FunctionDeclaration>();
		equations = new ArrayList<Equation>();
	}
	@XmlElement(name="function-declaration")
	public ArrayList<FunctionDeclaration> getFunctionDeclarations() {
		return functionDeclarations;
	}
	public void setFunctionDeclarations(ArrayList<FunctionDeclaration> functionDeclarations) {
		this.functionDeclarations = functionDeclarations;
	}
	@XmlElement(name="action")
	public ArrayList<Equation> getEquations() {
		return equations;
	}
	public void setEquations(ArrayList<Equation> equations) {
		this.equations = equations;
	}
	
	
}
