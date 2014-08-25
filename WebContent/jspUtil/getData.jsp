<%@page import="net.smartworks.rice.model.FtpHistory"%>
<%@page import="net.smartworks.util.IdUtil"%>
<%@page import="net.smartworks.rice.model.SensorReport"%>
<%@page import="net.smartworks.rice.model.TestReport"%>
<%@page import="net.smartworks.util.FileUtil"%>
<%@page import="org.springframework.util.StringUtils"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.FileNotFoundException"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.net.ftp.FTPFile"%>
<%@page import="java.util.ArrayList"%>
<%@page import="net.smartworks.factory.ManagerFactory"%>
<%@page import="java.util.List"%>
<%@page import="net.smartworks.rice.model.FtpHistoryCond"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.apache.commons.logging.LogFactory"%>
<%@page import="org.apache.commons.net.ftp.FTPClient"%>
<%@page import="org.apache.commons.logging.Log"%>
<%@page import="net.smartworks.rice.scheduler.job.FtpFileTransfer"%>
<%@page import="net.smartworks.util.PropertiesLoader"%>
<%@page import="java.util.Properties"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<%

	Log log = LogFactory.getLog(FtpFileTransfer.class);
	
	String server = "70.26.12.254";
	int port = 21;
	String user = "smart";
	String password = "password";
	
	FTPClient ftpClient = new FTPClient();
	ftpClient.connect(server, port);
	ftpClient.login(user, password);
	
	//toDayStr = 20140625
	String toDayStr = (String)request.getParameter("toDayStr");
	if (toDayStr == null) {
		throw new Exception("No Argument : toDayStr!!!!!!");
	}
	String folderName = "ADS_" + toDayStr;
	String targetDir = "/ads/" + folderName;
	
	log.info("	From Dir : " + targetDir);
	
	FtpHistoryCond historyCond = new FtpHistoryCond();
	historyCond.setFolderName(folderName);
	List<String> todayFtpHistory = ManagerFactory.getInstance().getRptManager().getFtpHistory(historyCond);
	if (todayFtpHistory == null)
		todayFtpHistory = new ArrayList<String>();
	//test
	//targetDir = "/ads/ADS_20140423";
	
	ftpClient.changeWorkingDirectory(targetDir);
	FTPFile[] files = ftpClient.listFiles();
	
	for (int i = 0; i < files.length; i++) {
		String fileName = files[i].getName();
		// 파일 이름에서 확장자 추출
		String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
		long size = files[i].getSize();
		
		// 파일 사이즈가 0보다 크고 리포트 파일이면서 이미 가져온 파일은 제외하고 가져온다
		if ((size > 0) && (extension.equalsIgnoreCase("txt") && fileName.indexOf("Report_") != -1) && !todayFtpHistory.contains(fileName)) {
			//File file = ftp.get(fileName, fileName);
			
			File file = null;
			
			OutputStream output = null;
			try {
				File local = new File(fileName);
				output = new FileOutputStream(local);
			} catch (FileNotFoundException fnfe) {
				fnfe.printStackTrace();
			}
			File tempfile = new File(fileName);
			try {
				if (ftpClient.retrieveFile(fileName, output)) {
					file = tempfile;
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			
			//System.out.println(fileName  + " 전송완료!");
			
			log.info("	@@ Start Parsing File! : " + fileName);
			
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddhhmmss");
			
			String[] rows = StringUtils.tokenizeToStringArray(FileUtil.readString(file), FileUtil.RN);
			TestReport report = new TestReport();
			
			List<SensorReport> sensorDataList = new ArrayList<SensorReport>();
			String firstSensorId = "";
			
			for (int j = 0; j < rows.length; j++) {
				String rowData = rows[j].substring(1, rows[j].indexOf("]"));
				log.info("	" + fileName + "'s rowData : " + rowData);
				if (j == 0) {
					//검사 데이터
					String[] columnDatas = StringUtils.tokenizeToStringArray(rowData, ",");
					report.setProject("ADS");
					report.setId("rpt_"+IdUtil.getInstance().generate());
					
					Date date = sdf2.parse(columnDatas[0]);						
					report.setDateTime(date);
					report.setLotNo(columnDatas[1]);
					report.setFairQualityCount(Integer.parseInt(columnDatas[2]));
					report.setFaultCount(Integer.parseInt(columnDatas[3]));
					report.setReportFileName(fileName);
					
				} else {
					//센서 데이터
					String[] columnDatas = StringUtils.tokenizeToStringArray(rowData, ",");
					
					SensorReport sr = new SensorReport();
					
					sr.setId("sen_"+IdUtil.getInstance().generate());
					sr.setLotNo(report.getLotNo());
					sr.setTestReportId(report.getId());

					sr.setSensorNo(columnDatas[0]);
					sr.setDateTime(report.getDateTime());
					sr.setIndoorTemperature(columnDatas[2]);
					sr.setGlassTemperature(columnDatas[3]);
					sr.setIndoorHumidity(columnDatas[4]);
					sr.setDecisionCode(columnDatas[6]);
					
					if (j == 1) {
						sr.setSerialNo(columnDatas[7]);
						report.setFirstSensorId(columnDatas[7]);
					} else {
						sr.setSerialNo(columnDatas[7]);
					}
					sr.setLotNoSerialNo(sr.getLotNo() + sr.getSerialNo());
					sensorDataList.add(sr);
				}
			}
			
			SensorReport[] sensorReports = new SensorReport[sensorDataList.size()];
			sensorDataList.toArray(sensorReports);				
			report.setSensorReports(sensorReports);
			
			//리포트를 저장한다
			ManagerFactory.getInstance().getRptManager().setTestReport(report);

			log.info("	Save TestReport Data! : Done!!!!");
			
			//ftp history 를 저장한다
			FtpHistory history = new FtpHistory();
			history.setId(IdUtil.getInstance().generate());
			history.setFolderName(folderName);
			history.setFileName(fileName);
			history.setFileSerial(fileName.substring(fileName.indexOf("_") + 1, fileName.indexOf(".")));
			history.setCreateDate(new Date());
			ManagerFactory.getInstance().getRptManager().setFtpHistory(history);
			
			log.info("	Save Ftp History Data! : Done!!!!");
		}
	}
	ftpClient.logout();
	ftpClient.disconnect();
	log.info("###### End Ftp File Transfer Module at " + new Date() + " ######");
%>
</head>
<body>
</body>
</html>