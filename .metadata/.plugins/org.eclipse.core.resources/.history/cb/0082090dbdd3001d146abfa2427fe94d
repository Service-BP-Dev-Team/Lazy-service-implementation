package c.general;

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
	
}
