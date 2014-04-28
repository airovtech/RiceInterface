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
	
	String dataJson = null;
	if(data!=null){
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		dataJson = ow.writeValueAsString(data);
		System.out.println(dataJson);
	}
	
%>
<div class="list_title_space" style="margin-top:20px">
	<div class="title">기간별 양품 갯수</div>
</div>
<div class="list_contents border" style="padding: 10px">

	<table id="chart_target"></table>
</div>
	
<script type="text/javascript">
$(function() {
	smartChart.loadWithData(swReportType.CHART, <%=dataJson%>, swChartType.LINE, false, "chart_target");
	var chartTarget = $('#chart_target');
	chartTarget.find('text[text="양품 수량"]').css("font-size", "14px");
	chartTarget.find('text[text="검사일"]').css("font-size", "14px");
	try{
		setTimeout(function(){parent.doIframeAutoHeight();}, 1000);
	}catch(e){}
});
</script>
