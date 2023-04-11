package cb;

import java.io.Serializable;

public class Treatment implements Serializable{

	protected boolean decision;
	protected String grant;
	
	public Treatment() {
		
	}
    public Treatment(boolean decision,String grant) {
		this.decision=decision;
		this.grant=grant;
	}
	public boolean getDecision() {
		return decision;
	}
	public void setDecision(boolean decision) {
		this.decision = decision;
	}
	public String getGrant() {
		return grant;
	}
	public void setGrant(String grant) {
		this.grant = grant;
	}
	
	
}
