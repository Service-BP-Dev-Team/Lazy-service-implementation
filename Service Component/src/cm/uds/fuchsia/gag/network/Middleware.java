package cm.uds.fuchsia.gag.network;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import cm.uds.fuchsia.gag.model.configuration.Task;
import cm.uds.fuchsia.gag.util.Console;

public class Middleware implements ComponentMiddleware{
	
	private Network world;
	private LazyComponent currentInstance;
	private ServerSocket serverSocketInstance;
	private  Component componentInstance;

	@Override
	public void inServiceCall(String expeditor, Task task) {
		// TODO Auto-generated method stub
		componentInstance.receiveTask(expeditor, task);
	}

	@Override
	public void inNotify(String expeditor, Subscription subscription) {
		// TODO Auto-generated method stub
	Console.debug("the component instance name is "+componentInstance.getComponentName());
	   componentInstance.receiveNotification(subscription);
	}

	@Override
	public void outServiceCall(String expeditor, Task task) {
		// TODO Auto-generated method stub
		int port =Middleware.getPort(expeditor, world);
		ServiceCallMessage sm = new ServiceCallMessage();
		sm.setSender(currentInstance.getName());
		sm.setTask(task);
		sendMessage(port, sm);
		
	}

	@Override
	public void outNotify(String expeditor, Subscription subscription) {
		// TODO Auto-generated method stub
		int port =Middleware.getPort(expeditor, world);
		NotificationMessage nm = new NotificationMessage();
		nm.setSender(currentInstance.getName());
		nm.setSubscription(subscription);
		sendMessage(port, nm);
	}
	
	public void sendMessage(int port, Message message) {
		Socket socket;
		try {
			socket = new Socket("localhost", port);
			System.out.println("Connected!");

	        // get the output stream from the socket.
	        OutputStream outputStream = socket.getOutputStream();
	        // create an object output stream from the output stream so we can send an object through it
	        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

	        // make a bunch of messages to send.

	        System.out.println("Sending messages to the ServerSocket");
	        objectOutputStream.writeObject(message);

	        System.out.println("Closing socket and terminating program.");
	        socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		
	}

	public Network getWorld() {
		return world;
	}

	public void setWorld(Network world) {
		this.world = world;
	}

	public LazyComponent getCurrentInstance() {
		return currentInstance;
	}

	public void setCurrentInstance(LazyComponent currentInstance) {
		this.currentInstance = currentInstance;
	}
	
	
	public ServerSocket getServerSocketInstance() {
		return serverSocketInstance;
	}

	public void setServerSocketInstance(ServerSocket serverSocketInstance) {
		this.serverSocketInstance = serverSocketInstance;
	}

	
	public static int getPort(String componentName,Network network) {
		int port = 888;
		for(int i=0;i<network.getComponents().size();i++) {
			if(network.getComponents().get(i).getName().equals(componentName)) {
				port=network.getComponents().get(i).getPort();
				break;
			}
		}
		return port;
	}
	public static Middleware createComponentMiddleware( String componentName, int port, String networkPath, String SpecificationPath  ) {
		Middleware middle = new Middleware();
		LazyComponent lcomponent = new LazyComponent();
		lcomponent.setName(componentName);
		lcomponent.setPort(port);
		lcomponent.setSpecificationPath(SpecificationPath);
		middle.setCurrentInstance(lcomponent);
		Network myNetwork= new Network();
		
		if(networkPath!=null) {
			System.out.println(networkPath);
	    	JAXBContext ctx;
			try {
				ctx = JAXBContext.newInstance(Network.class);

				Marshaller msh = ctx.createMarshaller();
				Unmarshaller umsh = ctx.createUnmarshaller();
				myNetwork= (Network) umsh.unmarshal(new File(networkPath));
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         }
	    middle.setWorld(myNetwork);		
	    // Create server Socket
       
	    
        //creating the socket
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
            	try {
            		System.out.println("the port is :"+port);
            		ServerSocket ss = new ServerSocket(port);
        			middle.setServerSocketInstance(ss);
        	        // connect it to client socket
        			while(true) {
        	        Socket socket = ss.accept();
        	        System.out.println("Connection from " + socket + "!");

        	        // get the input stream from the connected socket
        	        InputStream inputStream = socket.getInputStream();
        	        // create a DataInputStream so we can read data from it.
        	        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        	        // read the list of messages from the socket
        	        Message message = (Message) objectInputStream.readObject();
        	        if(message instanceof NotificationMessage) {
        	        	NotificationMessage nMessage = (NotificationMessage) message;
        	        	middle.inNotify(nMessage.getSender(),nMessage.getSubscription());
        	        }
        	        else if(message instanceof ServiceCallMessage) {
        	        	ServiceCallMessage sMessage = (ServiceCallMessage) message;
        	        	middle.inServiceCall(sMessage.getSender(), sMessage.getTask());
        	        }
        	        
        	        //close the socket;
        	        socket.close();
        	        }
        		} catch (IOException | ClassNotFoundException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
            }
        });  
        t1.start();
		
		
		return middle;
	}

	public Component getComponentInstance() {
		return componentInstance;
	}

	public void setComponentInstance(Component componentInstance) {
		this.componentInstance = componentInstance;
	}
	
	
	
	

}
