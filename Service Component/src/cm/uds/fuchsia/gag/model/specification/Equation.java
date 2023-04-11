package cm.uds.fuchsia.gag.model.specification;

import java.io.Serializable;

import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlType;


public class Equation implements Serializable{
	
	private IdExpression leftpart;
	private Expression rightpart;
	

	public IdExpression getLeftpart() {
		return leftpart;
	}
	public void setLeftpart(IdExpression leftpart) {
		this.leftpart = leftpart;
	}
	
	public Expression getRightpart() {
		return rightpart;
	}
	public void setRightpart(Expression rightpart) {
		this.rightpart = rightpart;
	}
	
}
