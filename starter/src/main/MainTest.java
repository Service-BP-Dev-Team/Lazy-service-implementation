package main;

import java.io.File;
import java.util.ArrayList;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;


public class MainTest {
	
	public static void main (String[] args) {
		
		Network myNetwork= new Network();
		myNetwork.setProcessName("myworld");
		LazyComponent component1= new LazyComponent();
		component1.setName("component 1");
		component1.setSpecificationPath("path1");
		
		LazyComponent component2= new LazyComponent();
		component2.setName("component 2");
		component2.setSpecificationPath("path2");
		ArrayList<LazyComponent> myComponentList = new ArrayList<LazyComponent>();
		myComponentList.add(component1);
		myComponentList.add(component2);
		myNetwork.setComponents(myComponentList);
		
		//save it there in xml
		JAXBContext ctx;
		try {
			ctx = JAXBContext.newInstance(Network.class,LazyComponent.class);

			Marshaller msh = ctx.createMarshaller();
			Unmarshaller umsh = ctx.createUnmarshaller();
			
			msh.marshal(myNetwork, new File("network.xml"));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
