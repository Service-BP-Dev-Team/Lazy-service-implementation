package c.general.book;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;



public class Util {
	
	
	
	public static Offer getOffer(){
		 Offer req =null; 
		 String path=Util.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		//String path="";	
		 path=path.replaceAll("%20", " ");
			path+="\\..\\myBin\\offerFile.req";
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
		            req=(Offer) obj;
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
	            return getOffer();
	        }
	}
	
	public static void deleteOfferIfItExist(){
		 String path=Util.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			//String path="";
		    path=path.replaceAll("%20", " ");
			path+="\\..\\myBin\\offerFile.req";
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
	
public static Object makeOffer(Object order){
		
		System.out.println("makeOffer success");
		String path=Util.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		System.out.println("the path is "+path);
		path=path.replaceAll("%20", " ");
		String commpleteLibs="weblaf-demo-1.2.13-jar-with-dependencies.jar";
		String minimalLibs="weblaf-complete-1.29.jar";
		String libs=path+"..\\libs\\"+minimalLibs;
		Runtime rt = Runtime.getRuntime();
		try {
			System.out.println("running command line");
			Process pr = rt.exec("java -cp \""+path+"\";\""+libs+"\" c.general.book.BookMakeOfferFrame");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		deleteOfferIfItExist();
		return getOffer();
	}

}
