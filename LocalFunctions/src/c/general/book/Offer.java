package c.general.book;

import java.io.Serializable;

public class Offer implements  Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String[] bookNames={"book 1","book 2", "book 3"};
	public int[] bookQuantities=new int[3];
	public int[] bookPrices=new int[3];
	public String[] getBookNames() {
		return bookNames;
	}
	public void setBookNames(String[] bookNames) {
		this.bookNames = bookNames;
	}
	public int[] getBookQuantities() {
		return bookQuantities;
	}
	public void setBookQuantities(int[] bookQuantities) {
		this.bookQuantities = bookQuantities;
	}
	public int[] getBookPrices() {
		return bookPrices;
	}
	public void setBookPrices(int[] bookPrices) {
		this.bookPrices = bookPrices;
	}

	public String toString(){
		return "The offer ...";
	}

}
