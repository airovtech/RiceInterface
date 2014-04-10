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
	TestReport[] reps = mgr.getTestReports(new TestReportCond());
	for (TestReport rp : reps) {
		out.println(rp.getLotNo() + " , " + rp.getDateTime() + " , " + rp.getFairQualityCount() + " , " + rp.getFaultCount() + "</br>");
	}
	%>
</body>
</html>