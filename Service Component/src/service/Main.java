package service;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import cm.uds.fuchsia.gag.network.*;

public class Main {

	
	public static void main (String args []) {
		int port =888; //default port
		String specificationFile = "";
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
		        if((args[i].equals("--network") || args[i].equals("-n"))  && (i+1) < args.length) {
		        	networkFile = args[i+1];
		        }
		    }
		    System.out.println("port: " + port+ " specification: " + specificationFile );
		    
		    if(networkFile!=null) {
		    	JAXBContext ctx;
				try {
					ctx = JAXBContext.newInstance(Network.class);

					Marshaller msh = ctx.createMarshaller();
					Unmarshaller umsh = ctx.createUnmarshaller();
					world= (Network) umsh.unmarshal(new File(specificationFile));
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	         }
				
		    // Create server Socket
	        ServerSocket ss;
			try {
				ss = new ServerSocket(port);
		        // connect it to client socket
		        Socket s = ss.accept();
		        System.out.println("Connection established");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
}
