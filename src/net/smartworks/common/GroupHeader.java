package net.smartworks.common;


public class GroupHeader {

	private String startColumnName;
	private int startColumnIndex;
	private int numberOfColumns;
	private String titleText;
	
	public String getStartColumnName() {
		return startColumnName;
	}
	public void setStartColumnName(String startColumnName) {
		this.startColumnName = startColumnName;
	}
	public int getNumberOfColumns() {
		return numberOfColumns;
	}
	public void setNumberOfColumns(int numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}
	public String getTitleText() {
		return titleText;
	}
	public void setTitleText(String titleText) {
		this.titleText = titleText;
	}
	public int getStartColumnIndex() {
		return startColumnIndex;
	}
	public void setStartColumnIndex(int startColumnIndex) {
		this.startColumnIndex = startColumnIndex;
	}
	
}
