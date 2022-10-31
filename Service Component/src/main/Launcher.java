package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import cm.uds.fuchsia.gag.model.configuration.Configuration;
import cm.uds.fuchsia.gag.model.specification.FunctionExpression;
import cm.uds.fuchsia.gag.model.specification.GAG;
import cm.uds.fuchsia.gag.model.specification.IdExpression;
import cm.uds.fuchsia.gag.network.Component;
import cm.uds.fuchsia.gag.network.ComponentMiddleware;
import cm.uds.fuchsia.gag.network.Middleware;
import cm.uds.fuchsia.gag.network.Network;
import cm.uds.fuchsia.gag.network.Subscription;
import cm.uds.fuchsia.gag.specification.aspect.GAGAspect;
import cm.uds.fuchsia.gag.ui.component.ComponentIHM;
import cm.uds.fuchsia.gag.util.Console;

public class Launcher {

static String classPath ="E:\\PhD Recherche\\Implementation\\workspace-java\\GagApp\\bin";
	
	public static Component launchComponent(String componentName, String gagSpecificationPath, ComponentMiddleware middleware) {
        Component net=null;
		JAXBContext ctx;
		try { 
			ctx= JAXBContext.newInstance(GAG.class,Configuration.class,IdExpression.class, FunctionExpression.class);
			
			Marshaller msh = ctx.createMarshaller();
			Unmarshaller umsh = ctx.createUnmarshaller();
			/*
			msh.marshal(g, new File("C:\\Users\\TAGUEU\\Desktop\\file.xml"));
			//GAG mygag= (GAG) umsh.unmarshal(new File("C:\\Users\\TAGUEU\\Desktop\\file.xml"));
			//msh.marshal(mygag, new File("C:\\Users\\TAGUEU\\Desktop\\file1.xml"));
			*/
			GAG mygag= (GAG) umsh.unmarshal(new File(gagSpecificationPath));
			GAGAspect gasp=new GAGAspect(mygag);
			ComponentIHM window = new ComponentIHM();
			Console.debug("The component name is : "+componentName);
			
			net = new Component();
			net.setIhm(window);
			//net.setSubscriptionList(new ArrayList<Subscription>());
			net.setComponentName(componentName);
			GAGAspect mygagAspect = new GAGAspect(mygag);
			mygagAspect.setComponent(net);
			net.setAssociateGAG(mygagAspect);
			
			net.setMiddleware(middleware);
			window.disposeTheGraph(mygagAspect);
			window.setTitle(componentName);
			window.setVisible(true);
			
			//gasp.runWithExternalOuputInterface(window.getGraphLayout());
		} catch (JAXBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		return net;
		
	}
	
	public static void main (String args[]) {
		
		int port =888; //default port
		String specificationFile = null;
		String componentName =null;
		String networkFile = null;
		Network world = new Network();
		 System.out.println("Argument count: " + args.length);
		    for (int i = 0; i < args.length; i++) {
		        if((args[i].equals("--port") || args[i].equals("-p"))  && (i+1) < args.length) {
		        	port = Integer.parseInt(args[i+1]);
		        }
		        if((args[i].equals("--specification") || args[i].equals("-s"))  && (i+1) < args.length) {
		        	specificationFile = args[i+1];
		        }
		        if((args[i].equals("--network") || args[i].equals("-n") || args[i].equals("--world") || args[i].equals("-w"))  && (i+1) < args.length) {
		        	networkFile = args[i+1];
		        }

		        if((args[i].equals("--title") || args[i].equals("-t") || args[i].equals("--name") || args[i].equals("-nm"))  && (i+1) < args.length) {
		        	componentName = args[i+1];
		        }
		    }
		  Middleware middle = Middleware.createComponentMiddleware(componentName, port, networkFile, specificationFile);
		  Component component = launchComponent(componentName, specificationFile, middle);
		  //associate the component as current instance of the middleware
		  middle.setComponentInstance(component);
	}
}
