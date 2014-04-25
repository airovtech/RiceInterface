<%@page import="net.smartworks.riceinterface.model.SummaryReportPop"%>
<%@page import="java.util.Date"%>
<%@page import="net.smartworks.riceinterface.model.SummaryReport"%>
<%@page import="net.smartworks.riceinterface.model.TestReport"%>
<%@page import="net.smartworks.riceinterface.model.TestReportCond"%>
<%@page import="net.smartworks.factory.ManagerFactory"%>
<%@page import="net.smartworks.riceinterface.manager.IUiManager"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
<%
	IUiManager mgr = ManagerFactory.getInstance().getUiManager();
	SummaryReportPop rep = mgr.getSummaryReportPop(new Date(114,01,01), new Date(), "byYear", "");
	out.println(rep.getTestDate() + ", " + rp.getTotalFaultCount() + "/" + rp.getTotalTestCount() + " : faultPercent - " + rp.getFaultPercent() + " % " + "</br>");
	%>
</body>
</html>