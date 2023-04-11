package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String networkFilePath=null;
	    for (int i = 0; i < args.length; i++) {
	        if((args[i].equals("--network") || args[i].equals("-n") || args[i].equals("--world") || args[i].equals("-w"))  && (i+1) < args.length) {
	        	networkFilePath = args[i+1];
	        }
	    }
	    
	    //launch the component
	    Network world = new Network();
	    if(networkFilePath!=null) {
	    	try {
				JAXBContext ctx = JAXBContext.newInstance(Network.class);

				Marshaller msh = ctx.createMarshaller();
				Unmarshaller umsh = ctx.createUnmarshaller();
				world= (Network) umsh.unmarshal(new File(networkFilePath));
				for (int i=0; i<world.getComponents().size();i++) {
					
					LazyComponent component = world.getComponents().get(i);
					System.out.println(component.getName());
					System.out.println(component.getSpecificationPath());
					Runtime rt = Runtime.getRuntime();
					//String rootPath ="\"D:\\Implementation-to-deploy-book-order-simplified\\Workspace-java\\Service Component\\bin\"";
					//String toRun="java -cp "+rootPath+" main.Launcher" +" -t \""+component.getName()+"\" -p \""+component.getPort()+"\" -s \""+component.getSpecificationPath()+"\" -n \""+networkFilePath+"\"";
					String rootPath ="\"..\\serviceapp.jar\"";
					String toRun="java -jar "+rootPath+"" +" -t \""+component.getName()+"\" -p \""+component.getPort()+"\" -s \""+component.getSpecificationPath()+"\" -n \""+networkFilePath+"\"";
					
					System.out.println(toRun);
					Process pr = rt.exec(toRun);
					//InputStream result = pr.getInputStream();
					
					//watch the output of the process
					new Thread(new Runnable() {
					    public void run() {
					        BufferedReader input = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
					        String line = null;

					        try {
					            while ((line = input.readLine()) != null)
					                System.out.println(line);
					        } catch (IOException e) {
					            e.printStackTrace();
					        }
					    }
					}).start();

					pr.waitFor();
				}
				
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	}

}
