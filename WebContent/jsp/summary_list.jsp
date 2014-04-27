<%@page import="java.util.Date"%>
<%@page import="net.smartworks.rice.model.SummaryReport"%>
<%@page import="net.smartworks.rice.model.SummaryReportCond"%>
<%@page import="net.smartworks.util.SmartUtil"%>
<%@page import="net.smartworks.common.Order"%>
<%@page import="net.smartworks.model.SortingField"%>
<%@page import="net.smartworks.rice.model.TestReport"%>
<%@page import="net.smartworks.rice.model.TestReportCond"%>
<%@page import="net.smartworks.rice.manager.IUiManager"%>
<%@page import="net.smartworks.factory.ManagerFactory"%>
<%@page import="net.smartworks.model.RequestParams"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%

	String cleanup = request.getParameter("cleanup");
	RequestParams requestParams = (RequestParams)request.getAttribute("requestParams");
	
	if(SmartUtil.isBlankObject(requestParams) || "true".equals(cleanup)){
		String savedWorkId = (String)session.getAttribute("workId");
		requestParams = (RequestParams)session.getAttribute("requestParams");
		if(!RequestParams.LIST_TYPE_SUMMARY.equals(savedWorkId) || SmartUtil.isBlankObject(requestParams)){
			requestParams = new RequestParams();
			requestParams.setPageSize(20);
			requestParams.setCurrentPage(1);
			requestParams.setSortingField(new SortingField(TestReport.FIELD_ID_DATETIME, false));
			requestParams.setSelectorType(SummaryReportCond.SELECTOR_DAILY);
			requestParams.setListType(RequestParams.LIST_TYPE_SUMMARY);
		}
	}
	
	Date fromDate = SmartUtil.convertDateStringToDate(requestParams.getSearchDateFrom());
	Date toDate = SmartUtil.convertDateStringToDate(requestParams.getSearchDateTo());
	String selectorType = SmartUtil.isBlankObject(requestParams.getSelectorType())? SummaryReportCond.SELECTOR_DAILY : requestParams.getSelectorType();
	session.setAttribute("requestParams", requestParams);
	session.setAttribute("workId", RequestParams.LIST_TYPE_SUMMARY);
	
	IUiManager mgr = ManagerFactory.getInstance().getUiManager();
	
	SummaryReport[] reports = mgr.getSummaryReports(fromDate, toDate, selectorType);
%>

<!-- 목록 테이블 -->
<table>

	<tr class="tit_bg">
 		<th class="r_line">검사일</th>
 		<th class="r_line">총 검사 수량</th>
 		<th class="r_line">총 양품 수량</th>
 		<th class="r_line">총 불량 수량</th>
 		<th class="r_line">불량률</th>
 		<th>보기</th>
	</tr>	
	<%
	if(!SmartUtil.isBlankObject(reports)){
		for (SummaryReport report : reports) {
		%>
			<tr class="instance_list js_select_test_report" summaryId="<%=report.getTestDate()%>">
				<td><%=report.getTestDate() %></td>
				<td class="tr"><%=report.getTotalTestCount()%></td>
				<td class="tr"><%=report.getTotalFairQualityCount() %></td>
				<td class="tr"><%=report.getTotalFaultCount() %></td>
				<td class="tr"><%=report.getFaultPercent() %></td>
				<td></td>
			</tr>
	<%
		}
	}
	%>
</table>
<%
if(SmartUtil.isBlankObject(reports)){
%>
	<div class="tc mt5mb5">기간별 항목이 존재하지 않습니다.</div>
<%
}
%>
