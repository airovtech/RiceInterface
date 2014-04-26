package net.smartworks.model.filter;

public class Condition {
		
	private String leftOperand;
	private String operator;
	private Object rightOperand;
	
	public String getLeftOperand() {
		return leftOperand;
	}
	public void setLeftOperand(String leftOperand) {
		this.leftOperand = leftOperand;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Object getRightOperand() {
		return rightOperand;
	}
	public void setRightOperand(Object rightOperand) {
		this.rightOperand = rightOperand;
	}
	public Condition(){
	}	
	public Condition(String leftOperand, String operator, Object rightOperand){
		this.leftOperand = leftOperand;
		this.operator = operator;
		this.rightOperand = rightOperand;
	}
	
}
