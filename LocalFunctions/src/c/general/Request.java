package c.general;

import java.io.Serializable;

public class Request implements Serializable{
    //default serialVersion id
    private static final long serialVersionUID = 1L;
	private int minPrice;
	private int maxPrice;
	private int maxDeliveryTime;
	private String quality;
	private int quantity;
	public int getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}
	public int getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}
	public int getMaxDeliveryTime() {
		return maxDeliveryTime;
	}
	public void setMaxDeliveryTime(int maxDeliveryTime) {
		this.maxDeliveryTime = maxDeliveryTime;
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
	
	public String toString(){
		return "my request"; //just for display purpose
	}
	
}
