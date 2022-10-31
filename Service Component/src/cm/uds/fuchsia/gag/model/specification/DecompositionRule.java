package cm.uds.fuchsia.gag.model.specification;

import java.io.Serializable;
import java.util.ArrayList;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlIDREF;

public class DecompositionRule implements Serializable {

	private String name;
	private Guard guard;
	private ArrayList<Service> subServices;
	private SemanticRule semantic;
	
	
	public DecompositionRule() {
		subServices= new ArrayList<Service>();
	}
	@XmlAttribute
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Guard getGuard() {
		return guard;
	}
	public void setGuard(Guard guard) {
		this.guard = guard;
	}
	@XmlAttribute @XmlIDREF
	public ArrayList<Service> getSubServices() {
		return subServices;
	}
	public void setSubServices(ArrayList<Service> subServices) {
		this.subServices = subServices;
	}
	public SemanticRule getSemantic() {
		return semantic;
	}
	public void setSemantic(SemanticRule semantic) {
		this.semantic = semantic;
	}
	
	
	
}
