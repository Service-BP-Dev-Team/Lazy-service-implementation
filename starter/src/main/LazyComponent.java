package main;
import jakarta.xml.bind.annotation.XmlAttribute;

public class LazyComponent {

	private String name;
	private String specificationPath;
	private int port;
	
	@XmlAttribute(name="name")
	public String getName() {
		return name;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlAttribute(name="path")
	public String getSpecificationPath() {
		return specificationPath;
	}
	public void setSpecificationPath(String specificationPath) {
		this.specificationPath = specificationPath;
	}

	@XmlAttribute(name="port")
	public int getPort() {
		return port;
	}


	public void setPort(int port) {
		this.port = port;
	}
	
	
	
	
}
