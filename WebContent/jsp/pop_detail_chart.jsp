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
	Date toDate = SmartUtil.convertDateStringToDate(fromDateStr);
	
	IUiManager mgr = ManagerFactory.getInstance().getUiManager();
	
	Data data = mgr.getLineChartReportData(fromDate, toDate, selectorType);
	
	String dataJson = null;
	if(data!=null){
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		dataJson = ow.writeValueAsString(data);
		System.out.println(dataJson);
	}
%>

<!--  전체 레이아웃 -->
<div class="pop_corner_all">

	<!-- 팝업 타이틀 -->
	<div class="form_title">
		<div class="pop_title">코드별 불량비율</div>
		<div class="txt_btn">			
			<a href="" onclick="smartPop.close();return false;"><div class="btn_x"></div></a>
		</div>
		<div class="solid_line"></div>
	</div>
	<!-- 팝업 타이틀 //-->
	<!-- 컨텐츠 -->
	<div class="contents_space">
		<div id="detail_chart_target"></div>
	</div>
	<!-- 컨텐츠 //-->

</div>
<!-- 전체 레이아웃//-->
<%-- <script type="text/javascript">
$(function() {
	smartChart.loadWithData(swReportType.CHART, <%=dataJson%>, swChartType.PIE, false, "detail_chart_target");
});
</script>
 --%>