package cb;

import java.awt.EventQueue;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Apply {

	public static String[] array={"Grant A","Grant B", "Grant C","Grand D"};
	public static Object system(Object infos, Object resume, Object letter) {
		String res= resume.toString();
		String inf= infos.toString();
		Semaphore sem=new Semaphore(0);
		Treatment treatment =new Treatment();
		
		if(res.length()>=4)
		{
			Random rand = new Random();
	    	int rd=rand.nextInt(500);
		    if(rd % 2 ==0) {
		    	Random randGrant = new Random();
		    	int rdg=rand.nextInt(4);
		    	treatment.setDecision(true);
		    	treatment.setGrant(array[rdg]);
		    }
		}

    	treatment.setDecision(false);
    	treatment.setGrant("");
    	Treatment newTreatment = Util.getUserTreatment(treatment);
    	System.out.println("the new decision is: "+newTreatment.getDecision()+" and grant is : "+newTreatment.getGrant());
		return newTreatment;
		
	}
	
	
	
	public static boolean guardDecide(Object infos, Object letter, Object resume) {
		return (resume!=null);
	}
	
	public static boolean decide(Object syst) {
	  Treatment treatment = (Treatment) Util.fileAdapt(syst);
	  return treatment.getDecision();
	  
	}
	
	public static String grant(Object syst) {
		  Treatment treatment =  (Treatment) Util.fileAdapt(syst);;
		  return treatment.getGrant();
		  
		}
}
