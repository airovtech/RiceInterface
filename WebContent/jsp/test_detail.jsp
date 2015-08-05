<%@page import="net.smartworks.tle.model.SensorReport"%>
<%@page import="net.smartworks.util.SmartUtil"%>
<%@page import="net.smartworks.common.Order"%>
<%@page import="net.smartworks.model.SortingField"%>
<%@page import="net.smartworks.tle.model.TestReport"%>
<%@page import="net.smartworks.tle.model.TestReportCond"%>
<%@page import="net.smartworks.tle.manager.IUiManager"%>
<%@page import="net.smartworks.factory.ManagerFactory"%>
<%@page import="net.smartworks.model.RequestParams"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%

	String reportId = request.getParameter("reportId");	
	IUiManager mgr = ManagerFactory.getInstance().getUiManager();
	TestReport report = mgr.getTestReportWithSensorReports(reportId);
%>

<div class="list_title_space" style="margin-top:20px">
	<div class="title">상세정보</div>
</div>
<div class="list_contents border" style="padding: 10px">
	<table>
		<tbody>
			<tr>
				<td width="30%" class="tl"><span style="color: #23579e;font-weight: bold;">로트 넘버 : </span><%=report.getLotNo() %></td>
				<td width="30%" class="tl"><span style="color: #23579e;font-weight: bold;">날짜/시간 : </span><%=SmartUtil.printDateTime(report.getDateTime())%></td>
				<td width="40%"></td>
			</tr>
			<tr>
				<td width="30%" class="tl"><span style="color: #23579e;font-weight: bold;">검사 수량 : </span><%=report.getTotalTestCount() %></td>
				<td width="30%" class="tl"><span style="color: #23579e;font-weight: bold;">양품 수량 : </span><%=report.getFairQualityCount() %></td>
				<td width="30%" class="tl"><span style="color: #23579e;font-weight: bold;">불량 수량 : </span><%=report.getFaultCount() %></td>
			</tr>
		</tbody>
	</table>
	<!-- 목록 테이블 -->
	<table>
		<%	
		if (!SmartUtil.isBlankObject(report.getSensorReports())) {
		%>
		
			<tr class="tit_bg">
				<th class="r_line" style="border-top:none">측 정 시 간</th>
		 		<th class="r_line" style="border-top:none">판 정</th>
		 		<th class="r_line" style="border-top:none">실 내 습 도</th>
		 		<th class="r_line" style="border-top:none">QRcode</th>
		 		<th class="r_line" style="border-top:none">DATAMATRIX</th>
			</tr>	
		<%
			for (SensorReport sensorReport : report.getSensorReports()) {
				
				String decisionCode = sensorReport.getDecisionCode();
				boolean isFairQualityCode = decisionCode.equalsIgnoreCase(SensorReport.fairQualityCode);
				String serialNoStr = isFairQualityCode ? sensorReport.getDecisionCode() : "-";
				if (isFairQualityCode) {
			%>
				<tr class="instance_sensor_list js_pop_pcb_report" href="" sensor_bar="<%=sensorReport.getLotNo() + sensorReport.getDataMatrix()%> ">
			<%
				} else {
			%>
				<tr>
				
			<%
				}
			%>	
					<td class="tc"><%=SmartUtil.printDateTime(sensorReport.getDateTime()) %></td>
					<td class="tc"><%=SmartUtil.toNotNull(sensorReport.getDecisionCode()) %></td>
					<td class="tc"><%=SmartUtil.toNotNull(sensorReport.getHumidity()) %></td>
					<td class="tc"><%=SmartUtil.toNotNull(sensorReport.getQrCode()) %></td>
					<td class="tc"><%=SmartUtil.toNotNull(sensorReport.getDataMatrix()) %></td>
					
					
				</tr>
		<%
			}
		}else {
		%>
			<tr class="tit_bg">
				<th class="r_line" style="border-top:none">측 정 시 간</th>
		 		<th class="r_line" style="border-top:none">판 정 내 용</th>
		 		<th class="r_line" style="border-top:none">실 내 습 도</th>
		 		<th class="r_line" style="border-top:none">QRcode</th>
		 		<th class="r_line" style="border-top:none">DATAMATRIX</th>
		 		
		 		
			</tr>	
		<%
		}
		%>
	</table>
	<%
	if(SmartUtil.isBlankObject(report.getSensorReports())){ 
	%>
		<div class="tc mt5mb5">불량 데이터가 존재하지 않습니다.</div>
	<%
	}
	%>
</div>

<script type="text/javascript">
$(function() {
	try{
	setTimeout(function(){parent.doIframeAutoHeight();}, 1500);
	}catch(e){}
});
</script>
