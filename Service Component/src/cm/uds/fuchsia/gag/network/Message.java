package cm.uds.fuchsia.gag.network;

import java.io.Serializable;

public class Message implements Serializable {
	private String sender;

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
	

}
