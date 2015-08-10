<%@page import="org.codehaus.jackson.map.ObjectMapper"%>
<%@page import="org.codehaus.jackson.map.ObjectWriter"%>
<%@page import="net.smartworks.common.Data"%>
<%@page import="net.smartworks.rice.model.SummaryReportCond"%>
<%@page import="java.util.Date"%>
<%@page import="net.smartworks.rice.model.SensorReport"%>
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

	RequestParams requestParams = (RequestParams)session.getAttribute("requestParams");
	Date fromDate = SmartUtil.convertDateStringToDate(requestParams.getSearchDateFrom());
	Date toDate = SmartUtil.convertDateStringToDate(requestParams.getSearchDateTo());
	String selectorType = SmartUtil.isBlankObject(requestParams.getSelectorType())? SummaryReportCond.SELECTOR_DAILY : requestParams.getSelectorType();
	
	IUiManager mgr = ManagerFactory.getInstance().getUiManager();
	
	Data data = mgr.getLineChartReportData(fromDate, toDate, selectorType);
	
	String dataJson1 = null;
	if(data!=null){
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		dataJson1 = ow.writeValueAsString(data);
		System.out.println(dataJson1);
	}
	
	data = mgr.getBarChartReportData(fromDate, toDate, selectorType);
	
	String dataJson2 = null;
	if(data!=null){
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		dataJson2 = ow.writeValueAsString(data);
		System.out.println(dataJson2);
	}
	
%>
<div class="list_title_space" style="margin-top:20px">
	<div class="title">기간별 양품 수량</div>
</div>
<div class="list_contents border" style="padding: 10px">

	<table id="chart_target1"></table>
</div>
	
<div class="list_title_space" style="margin-top:20px">
	<div class="title">기간별 양품/불량 수량</div>
</div>
<div class="list_contents border" style="padding: 10px">

	<table id="chart_target2"></table>
</div>
	
<script type="text/javascript">
	var loadSummaryCharts = function(){
		smartChart.loadWithData(swReportType.CHART, <%=dataJson1%>, swChartType.LINE, false, "chart_target1");
		var chartTarget1 = $('#chart_target1');
		chartTarget1.find('text[text="양품 수량"]').css("font-size", "14px");
		chartTarget1.find('text[text="검사일"]').css("font-size", "14px");
		
		smartChart.loadWithData(swReportType.CHART, <%=dataJson2%>, swChartType.COLUMN, true, "chart_target2");
		var chartTarget2 = $('#chart_target2');
		chartTarget2.find('text[text="수량"]').css("font-size", "14px");
		chartTarget2.find('text[text="검사일"]').css("font-size", "14px");
	};
	
	loadSummaryCharts();
 	try{
		parent.doIframeAutoHeight();
	}catch(e){}
	
	$(function() {
		$(window).resize(function() {
			loadSummaryCharts();
		});
	});
</script>
