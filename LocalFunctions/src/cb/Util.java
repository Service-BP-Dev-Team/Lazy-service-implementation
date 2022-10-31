package cb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;






public class Util {
	public static int cpt=1;

	public static Object fileAdapt(Object obj){
		Object obj1=null;
		createInFile(obj, cpt);
		obj1=readFromFile(cpt);
		cpt++;
		return obj1;
	}
	
	public static Object readFromFile(int counter){
	Object obj=null;
	String path=Util.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	//String path="";
	path=path.replaceAll("%20", " ");
	path+="\\..\\myBin\\"+counter;
	return readFromFile(path);
    }
	
    
	public static void createInFile(Object obj,int counter){
	
	String path=Util.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	//String path="";
	path=path.replaceAll("%20", " ");
	path+="\\..\\myBin\\"+counter;
	createInFile(obj, path);
      
	}
	
	public static Object readFromFile(String path){
		Object obj=null;
		File file = new File(path);
		FileInputStream fileIn;
		try {
				fileIn = new FileInputStream(file);
				ObjectInputStream objectIn = new ObjectInputStream(fileIn);
				 
	            obj = objectIn.readObject();
	 
	            System.out.println("The Object has been read from the file");
	            objectIn.close();
			} catch ( IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        return obj;
	    }
	
	public static void createInFile(Object obj,String path){
		
		File file = new File(path);
		  FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(file);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
		    objectOut.writeObject(obj);
		    objectOut.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
		}
	
	public static void clearTreamentCommunication(){
		 String path=Util.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			//String path="";
		    path=path.replaceAll("%20", " ");
			path+="\\..\\myBin\\treamentFile.req";
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
	
public static Treatment getUserTreatment(Treatment treatment){
		
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
			Process pr = rt.exec("java -cp \""+path+"\";\""+libs+"\" cb.MakeDecisionDialog -d "+(treatment.getDecision()?"Yes":"No")+" -g "+treatment.getGrant());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clearTreamentCommunication();;
		return fetchUserTreatment();
	}



public static Treatment fetchUserTreatment(){
	 Treatment req =null; 
	 String path=Util.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	//String path="";	
	 path=path.replaceAll("%20", " ");
		path+="\\..\\myBin\\treatmentFile.req";
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
	            req=(Treatment) obj;
			} catch ( IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           
           return req;
       }
       else
       {
           try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           return fetchUserTreatment();
       }
}
}
