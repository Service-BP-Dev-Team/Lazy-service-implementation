package main;

import java.util.ArrayList;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="World")
public class Network {

	private String processName;
	private ArrayList<LazyComponent> components;
	
	@XmlAttribute(name="name")
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public ArrayList<LazyComponent> getComponents() {
		return components;
	}
	public void setComponents(ArrayList<LazyComponent> components) {
		this.components = components;
	}
	
	
}
