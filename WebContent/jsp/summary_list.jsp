<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.codehaus.jackson.map.ObjectMapper"%>
<%@page import="org.codehaus.jackson.map.ObjectWriter"%>
<%@page import="net.smartworks.common.Data"%>
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
			requestParams.setSelectorType(SummaryReportCond.SELECTOR_MONTHLY);
			requestParams.setListType(RequestParams.LIST_TYPE_SUMMARY);
		}
	}
	
	Date fromDate = SmartUtil.convertDateStringToDate(requestParams.getSearchDateFrom());
	Date toDate = SmartUtil.convertDateStringToDate(requestParams.getSearchDateTo());
	String selectorType = SmartUtil.isBlankObject(requestParams.getSelectorType())? SummaryReportCond.SELECTOR_MONTHLY : requestParams.getSelectorType();
	if(fromDate==null ){
		Date today = new Date();
		long oneMinute = 60*1000;
		long maximum = 0;
		if(selectorType.equals(SummaryReportCond.SELECTOR_DAILY))
			maximum = oneMinute*60*24*60;
		else if(selectorType.equals(SummaryReportCond.SELECTOR_WEEKLY))
			maximum = oneMinute*365*24*60;
		else if(selectorType.equals(SummaryReportCond.SELECTOR_MONTHLY))
			maximum = oneMinute*4*365*24*60;
		if(maximum>0){
			fromDate = new Date(today.getTime()-maximum);
			requestParams.setSearchDateFrom((new SimpleDateFormat("yyyy.MM.dd")).format(fromDate.getTime()));
		}
	
	}
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
 		<th>코드별 불량비율</th>
	</tr>	
	<%
	if(!SmartUtil.isBlankObject(reports)){
		for (SummaryReport report : reports) {
		%>
			<tr>
				<td class="tc"><%=report.getTestDate() %></td>
				<td class="tr"><%=NumberFormat.getNumberInstance(Locale.KOREA).format(report.getTotalTestCount())%></td>
				<td class="tr"><%=NumberFormat.getNumberInstance(Locale.KOREA).format(report.getTotalFairQualityCount()) %></td>
				<td class="tr"><%=NumberFormat.getNumberInstance(Locale.KOREA).format(report.getTotalFaultCount()) %></td>
				<td class="tr"><%=String.format("%3.2f", report.getFaultPercent())%>%</td>
				<td class="tc"><a href="" class="linkline js_pop_detail_chart" fromDate="<%=requestParams.getSearchDateFrom() %>" toDate="<%=requestParams.getSearchDateTo() %>" selectorType="<%=selectorType %>" testDate="<%=report.getTestDate()%>">차트보기</a></td>
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
