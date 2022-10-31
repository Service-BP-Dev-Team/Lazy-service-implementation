package cm.uds.fuchsia.gag.network;

public class NotificationMessage extends Message {
	private Subscription subscription;

	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}
	
	

}
