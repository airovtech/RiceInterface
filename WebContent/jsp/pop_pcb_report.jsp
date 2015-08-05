<%@page import="net.smartworks.tle.model.PcbReport"%>
<%@page import="net.smartworks.tle.model.SummaryReportPopCond"%>
<%@page import="org.codehaus.jackson.map.ObjectMapper"%>
<%@page import="org.codehaus.jackson.map.ObjectWriter"%>
<%@page import="net.smartworks.common.Data"%>
<%@page import="net.smartworks.factory.ManagerFactory"%>
<%@page import="net.smartworks.tle.manager.IUiManager"%>
<%@page import="net.smartworks.util.SmartUtil"%>
<%@page import="java.util.Date"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%
	
	String sensor_bar = (String)request.getParameter("sensor_bar"); 
	IUiManager mgr = ManagerFactory.getInstance().getUiManager();
	PcbReport rpt = mgr.getPcbReport(sensor_bar);
	
	String title = "생산 이력 조회";
	String idx = "";
	String deviceId = "";
	String pcbBar = "";
	String sensorBar = "";
	String deviceDt = "";
	String deviceBy = "";
	String sensorDt = "";
	String sensorBy = "";
	String status = "";
	String actFg = "";
	String delFg = "";
	String locDt = "";
	
	if (rpt != null) {
		idx = SmartUtil.toNotNull(rpt.getIdx());
		deviceId = SmartUtil.toNotNull(rpt.getDevice_id());
		pcbBar = SmartUtil.toNotNull(rpt.getPcb_bar());
		sensorBar = SmartUtil.toNotNull(rpt.getSensor_bar());
		deviceDt = SmartUtil.toNotNull(SmartUtil.printDateTime(rpt.getDevice_dt()));
		deviceBy = SmartUtil.toNotNull(rpt.getDevice_by());
		sensorDt = SmartUtil.toNotNull(SmartUtil.printDateTime(rpt.getSensor_dt()));
		sensorBy = SmartUtil.toNotNull(rpt.getSensor_by());
		status = SmartUtil.toNotNull(rpt.getStatus());
		if (status.equalsIgnoreCase("PCB")) {
			status = "단품 검사 공정 완료";
		} else if (status.equalsIgnoreCase("SENSOR")) {
			status = "AGING 공정 완료";
		}
		actFg = SmartUtil.toNotNull(rpt.getAct_fg());
		if (actFg.equalsIgnoreCase("Y")) {
			actFg = "사용";
		} else if (actFg.equalsIgnoreCase("N")) {
			actFg = "사용안함";
		}
		delFg = SmartUtil.toNotNull(rpt.getDel_fg());
		if (delFg.equalsIgnoreCase("Y")) {
			delFg = "사용";
		} else if (delFg.equalsIgnoreCase("N")) {
			delFg = "사용안함";
		}
		locDt = SmartUtil.toNotNull(SmartUtil.printDateTime(rpt.getLoc_dt()));
	}
%>
<div class="pop_corner_all">
	<!-- 팝업 타이틀 -->
	<div class="form_title">
		<div class="pop_title"><%=title %></div>
		<div class="txt_btn">			
			<a href="" onclick="smartPop.close();return false;"><div class="btn_x"></div></a>
		</div>
		<div class="solid_line"></div>
	</div>
	<!-- 팝업 타이틀 //-->
	<!-- 컨텐츠 -->
	<div class="contents_space" style="width:760px;margin-top:10px">
		<div class="list_contents border" style="padding: 10px">
			<table>
				<tbody>
					<tr>
						<td class="tl"><span style="color: #23579e;font-weight: bold;">SENSOR BARCODE : </span><%=sensorBar %></td>
						<td class="tl"><span style="color: #23579e;font-weight: bold;">STATUS : </span><%=status %></td>
					</tr>
					<tr>
						<td class="tl"><span style="color: #23579e;font-weight: bold;">PCB BARCODE : </span><%=pcbBar %></td>
						<td class="tl"><span style="color: #23579e;font-weight: bold;">DEVICE ID : </span><%=deviceId %></td>
					</tr>
					<tr>
						<td class="tl"><span style="color: #23579e;font-weight: bold;">단품 검사 작업일시 : </span><%=deviceDt %></td>
						<td class="tl"><span style="color: #23579e;font-weight: bold;">단품 검사 작업자 : </span><%=deviceBy %></td>
					</tr>
					<tr>
						<td class="tl"><span style="color: #23579e;font-weight: bold;">AGING 검사 작업일시 : </span><%=sensorDt %></td>
						<td class="tl"><span style="color: #23579e;font-weight: bold;">AGING 검사 작업자 : </span><%=sensorBy %></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<!-- 컨텐츠 //-->

</div>
