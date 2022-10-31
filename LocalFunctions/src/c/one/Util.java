package c.one;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import c.general.Request;

public class Util {

	public static boolean user(Object offer, Object askValidation){
		System.out.println("execution of the user function");
		String path=Util.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		//String path="";
		path=path.replaceAll("%20", " ");
		c.general.Util.createInFile(offer, path+"\\..\\myBin\\offerFile.req");
		String commpleteLibs="weblaf-demo-1.2.13-jar-with-dependencies.jar";
		String minimalLibs="weblaf-complete-1.29.jar";
		String libs=path+"..\\libs\\"+minimalLibs;
		deleteConfirmationIfItExist();
		Runtime rt = Runtime.getRuntime();
		try {
			System.out.println("running command line");
			Process pr = rt.exec("java -cp \""+path+"\";\""+libs+"\" c.one.ConfirmOfferIHM");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getConfirmation();
	}
	
	public static Object makeRequest(Object infoStore){
		
		System.out.println("makeRequest success");
		String path=Util.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		//String path="";
		path=path.replaceAll("%20", " ");
		String commpleteLibs="weblaf-demo-1.2.13-jar-with-dependencies.jar";
		String minimalLibs="weblaf-complete-1.29.jar";
		String libs=path+"..\\libs\\"+minimalLibs;
		Runtime rt = Runtime.getRuntime();
		try {
			System.out.println("running command line");
			Process pr = rt.exec("java -cp \""+path+"\";\""+libs+"\" c.one.RequestForm");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		deleteRequestIfItExist();
		return getRequest();
	}
	
	public static void deleteRequestIfItExist(){
		 String path=Util.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			path=path.replaceAll("%20", " ");
			path+="\\requestFile.req";
			File file = new File(path);
         
	        if(file.delete())
	        {
	            System.out.println("File deleted successfully");
	        }
	        else
	        {
	            System.out.println("Failed to delete the file");
	        }
	}
	
	public static Request getRequest(){
		 Request req =null; 
		 String path=Util.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			path=path.replaceAll("%20", " ");
			path+="\\requestFile.req";
			File file = new File(path);
        
	        if(file.exists())
	        {
	        	FileInputStream fileIn;
				try {
					fileIn = new FileInputStream(file);
					ObjectInputStream objectIn = new ObjectInputStream(fileIn);
					 
		            Object obj = objectIn.readObject();
		 
		            System.out.println("The Object has been read from the file");
		            objectIn.close();
		            req=(Request) obj;
				} catch ( IOException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            
	            return req;
	        }
	        else
	        {
	            try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            return getRequest();
	        }
	}
	
	public static Object getStoreInfo(){
		
		return "Store Info";
	}
	
	public static void deleteConfirmationIfItExist(){
		 String path=Util.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			path=path.replaceAll("%20", " ");
			path+="\\..\\myBin\\confirmFile.req";
			File file = new File(path);
        
	        if(file.delete())
	        {
	            System.out.println("File deleted successfully");
	        }
	        else
	        {
	            System.out.println("Failed to delete the file");
	        }
	}
	
	public static Boolean getConfirmation(){
		 Boolean req =null; 
		 String path=Util.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			path=path.replaceAll("%20", " ");
			path+="\\..\\myBin\\confirmFile.req";
			File file = new File(path);
       
	        if(file.exists())
	        {
	        	FileInputStream fileIn;
				try {
					fileIn = new FileInputStream(file);
					ObjectInputStream objectIn = new ObjectInputStream(fileIn);
					 
		            Object obj = objectIn.readObject();
		 
		            System.out.println("The Object has been read from the file");
		            objectIn.close();
		            req=(Boolean) obj;
				} catch ( IOException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            
	            return req;
	        }
	        else
	        {
	            try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            return getConfirmation();
	        }
	}
	
	public static Object defaultfunc(Object obj){
		
		return 1;
	}
	
	public static int computeFees(Object obj){
		
		return 170000 + 5000;
				
				
	}
	
	public static String getDeliverLink(Object obj){
		
		return "https://link-to-delivery-info";
	}
	
	
	public static Boolean guardP2(Object obj1, Object obj2){
		
		return (obj1!=null && obj2!=null);
		
	}

	
}
