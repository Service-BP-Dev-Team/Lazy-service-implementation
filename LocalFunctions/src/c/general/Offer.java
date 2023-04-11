package c.general;

import java.io.Serializable;

public class Offer implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int price;
	private int deliveryTime;
	private String quality;
	private int quantity;
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(int deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public String getQuality() {
		return quality;
	}
	public void setQuality(String quality) {
		this.quality = quality;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public static Offer getBasicOffer(){
		Offer offer= new Offer();
		offer.setDeliveryTime(5);
		offer.setPrice(40000);
		offer.setQuality("A");
		offer.setQuantity(5000);
		return offer;
	}
	
	public String toString(){
		return "The offer";
	}
	
	
}
