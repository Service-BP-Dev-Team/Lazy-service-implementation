package cb;

import java.util.Random;

public class Subscribe {

	
	public static boolean guardSubscribe(Object classe, Object debit, Object decision) {
		if(decision==null)return false;
		return ((boolean) decision && debit!=null);
	}
	
	public static boolean guardTerminate(Object classe, Object debit, Object decision) {
		if(decision==null)return false;
		return (! (boolean) decision);
	}
	
	public static Object terminate() {
		return "Null";
	}
	
	public static Object subscribe() {
		Random rand = new Random();
    	int rd=rand.nextInt(200);
		return "http://school.or/receipt"+rd;
	}
	
}
