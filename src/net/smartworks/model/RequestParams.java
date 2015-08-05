package net.smartworks.model;

import net.smartworks.model.SortingField;
import net.smartworks.tle.model.SummaryReportCond;

public class RequestParams {

	public static final int PAGING_ACTION_NEXT10 = 1;
	public static final int PAGING_ACTION_NEXTEND = 2;
	public static final int PAGING_ACTION_PREV10 = 3;
	public static final int PAGING_ACTION_PREVEND = 4;
	public static final int DEFAULT_PAGE_SIZE = 20;
	
	public static final String LIST_TYPE_TEST = "testList";
	public static final String LIST_TYPE_SUMMARY = "summaryList";
	
	public static final String SEARCH_TYPE_LOTNO = "searchLotNo";
	public static final String SEARCH_TYPE_DATETIME = "searchDateTime";
	
	private int pageSize=DEFAULT_PAGE_SIZE;
	private int currentPage=1;
	private int pagingAction;
	private SortingField sortingField;
	
	private String listType;
	private String selectorType=SummaryReportCond.SELECTOR_MONTHLY;
	private String searchType;
	private String searchLotNo;
	private String searchDateFrom;
	private String searchDateTo;
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPagingAction() {
		return pagingAction;
	}
	public void setPagingAction(int pagingAction) {
		this.pagingAction = pagingAction;
	}
	public SortingField getSortingField() {
		return sortingField;
	}
	public void setSortingField(SortingField sortingField) {
		this.sortingField = sortingField;
	}
	public String getListType() {
		return listType;
	}
	public void setListType(String listType) {
		this.listType = listType;
	}
	public String getSelectorType() {
		return selectorType;
	}
	public void setSelectorType(String selectorType) {
		this.selectorType = selectorType;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getSearchLotNo() {
		return searchLotNo;
	}
	public void setSearchLotNo(String searchLotNo) {
		this.searchLotNo = searchLotNo;
	}
	public String getSearchDateFrom() {
		return searchDateFrom;
	}
	public void setSearchDateFrom(String searchDateFrom) {
		this.searchDateFrom = searchDateFrom;
	}
	public String getSearchDateTo() {
		return searchDateTo;
	}
	public void setSearchDateTo(String searchDateTo) {
		this.searchDateTo = searchDateTo;
	}
	public RequestParams() {
		super();
	}
}
