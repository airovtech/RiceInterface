<%@page import="net.smartworks.rice.model.SummaryReportPopCond"%>
<%@page import="org.codehaus.jackson.map.ObjectMapper"%>
<%@page import="org.codehaus.jackson.map.ObjectWriter"%>
<%@page import="net.smartworks.common.Data"%>
<%@page import="net.smartworks.factory.ManagerFactory"%>
<%@page import="net.smartworks.rice.manager.IUiManager"%>
<%@page import="net.smartworks.util.SmartUtil"%>
<%@page import="java.util.Date"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%
	
	String fromDateStr = request.getParameter("fromDate");
	String toDateStr = request.getParameter("toDate");
	String selectorType = request.getParameter("selectorType");
	String testDateStr = request.getParameter("testDate");

	Date fromDate = SmartUtil.convertDateStringToDate(fromDateStr);
	Date toDate = SmartUtil.convertDateStringToDate(toDateStr);
	
	IUiManager mgr = ManagerFactory.getInstance().getUiManager();
	
	Data data1 = mgr.getSummaryReportPop(fromDate, toDate, selectorType, testDateStr, SummaryReportPopCond.CHARTTYPEFAULT);
	
	String dataJson1 = null;
	if(data1!=null){
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		dataJson1 = ow.writeValueAsString(data1);
		System.out.println(dataJson1);
	}
	
	Data data2 = mgr.getSummaryReportPop(fromDate, toDate, selectorType, testDateStr, SummaryReportPopCond.CHARTTYPEALL);
	
	String dataJson2 = null;
	if(data2!=null){
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		dataJson2 = ow.writeValueAsString(data2);
		System.out.println(dataJson2);
	}
%>

<!--  전체 레이아웃 -->
<div class="pop_corner_all">

	<!-- 팝업 타이틀 -->
	<div class="form_title">
		<div class="pop_title"><%=testDateStr %></div>
		<div class="txt_btn">			
			<a href="" onclick="smartPop.close();return false;"><div class="btn_x"></div></a>
		</div>
		<div class="solid_line"></div>
	</div>
	<!-- 팝업 타이틀 //-->
	<!-- 컨텐츠 -->
	<div class="contents_space" style="width:800px;margin-top:-150px">
		<span style="display:inline-block;width:390px" id="detail_chart_target2">
		</span>
		<span style="display:inline-block;width:390px" id="detail_chart_target1">
		</span>
	</div>
<!-- 	<div style="height:100px"></div>
 -->	<!-- 컨텐츠 //-->

</div>
<!-- 전체 레이아웃//-->
<script type="text/javascript">
$(function() {
	smartChart.loadWithData(swReportType.CHART, <%=dataJson1%>, swChartType.PIE, false, "detail_chart_target1");
	$('#detail_chart_target1>div:first>span:first').hide();
	var detailChartTarget1 = $('#detail_chart_target1');
	var xNames = detailChartTarget1.find('text>tspan');
	if(!isEmpty(xNames)){
		for(var i=0; i<xNames.length; i++){
			xName = $(xNames[i]);
			xName.text(xName.text().split("(")[0]);
		}
	}
<%-- 	<%if(data1.getValues()==null || data1.getValues().size()==0){%>
		$('#detail_chart_target1 > div:first').append('<span class="tc">선택한 기간에 데이터가 존재하지 않습니다.</span');
	<%}%>
 --%> 	smartChart.loadWithData(swReportType.CHART, <%=dataJson2%>, swChartType.PIE, false, "detail_chart_target2");
	$('#detail_chart_target2>div:first>span:first').hide();
});
</script>
