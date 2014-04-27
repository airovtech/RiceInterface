/* 
 * $Id$
 * created by    : yukm
 * creation-date : 2014. 4. 25.
 * =========================================================
 * Copyright (c) 2014 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.rice.scheduler.job;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.smartworks.factory.ManagerFactory;
import net.smartworks.rice.model.FtpHistory;
import net.smartworks.rice.model.FtpHistoryCond;
import net.smartworks.rice.model.SensorReport;
import net.smartworks.rice.model.TestReport;
import net.smartworks.util.FileUtil;
import net.smartworks.util.IdUtil;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.util.StringUtils;

public class FtpFileTransfer {

	static String server = "125.130.69.151";
	static int port = 21;
	static String id = "smart";
	static String password = "password";
	FTPClient ftpClient;

	public FtpFileTransfer(String server, int port, String id, String password) {
		this.server = server;
		this.port = port;
		ftpClient = new FTPClient();
	}

	public static void parsing() throws Exception {
		
		FtpFileTransfer ftp = new FtpFileTransfer(server, port, id, password);
		ftp.connect();
		ftp.login(ftp.id, ftp.password);
		// 로그파일이 있는 디렉토리로 이동한다
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String toDayStr = sdf.format(new Date());
		String folderName = "ADS_" + toDayStr;
		String targetDir = "/ads/" + folderName;
		
		
		FtpHistoryCond historyCond = new FtpHistoryCond();
		historyCond.setFolderName(folderName);
		List<String> todayFtpHistory = ManagerFactory.getInstance().getRptManager().getFtpHistory(historyCond);
		if (todayFtpHistory == null)
			todayFtpHistory = new ArrayList<String>();
		//test
		targetDir = "/ads/ADS_20140423";
		
		System.out.println(targetDir);
		
		ftp.cd(targetDir);
		FTPFile[] files = ftp.list();
		for (int i = 0; i < files.length; i++) {
			String fileName = files[i].getName();
			// 파일 이름에서 확장자 추출
			String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
			long size = files[i].getSize();
			
			// 파일 사이즈가 0보다 크고 리포트 파일이면서 이미 가져온 파일은 제외하고 가져온다
			if ((size > 0) && (extension.equalsIgnoreCase("txt") && fileName.indexOf("Report_") != -1) && !todayFtpHistory.contains(fileName)) {
				File file = ftp.get(fileName, fileName);
				//System.out.println(fileName  + " 전송완료!");
				
				//TODO 파싱
				//System.out.println(FileUtil.readString(file));
				
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddhhmmss");
				
				String[] rows = StringUtils.tokenizeToStringArray(FileUtil.readString(file), FileUtil.RN);
				TestReport report = new TestReport();
				
				List<SensorReport> sensorDataList = new ArrayList<SensorReport>();
				String firstSensorId = "";
				
				for (int j = 0; j < rows.length; j++) {
					String rowData = rows[j].substring(1, rows[j].indexOf("]"));
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
						sensorDataList.add(sr);
					}
				}
				
				SensorReport[] sensorReports = new SensorReport[sensorDataList.size()];
				sensorDataList.toArray(sensorReports);				
				report.setSensorReports(sensorReports);
				
				//리포트를 저장한다
				ManagerFactory.getInstance().getRptManager().setTestReport(report);
				
				//ftp history 를 저장한다
				FtpHistory history = new FtpHistory();
				history.setId(IdUtil.getInstance().generate());
				history.setFolderName(folderName);
				history.setFileName(fileName);
				history.setFileSerial(fileName.substring(fileName.indexOf("_") + 1, fileName.indexOf(".")));
				history.setCreateDate(new Date());
				ManagerFactory.getInstance().getRptManager().setFtpHistory(history);
			}
		}
		ftp.logout();
		ftp.disconnect();
		//System.exit(1);
	}

	// 계정과 패스워드로 로그인
	public boolean login(String user, String password) {
		try {
			this.connect();
			return ftpClient.login(user, password);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return false;
	}

	// 서버로부터 로그아웃
	private boolean logout() {
		try {
			return ftpClient.logout();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return false;
	}

	// 서버로 연결
	public void connect() {
		try {
			ftpClient.connect(server, port);
			int reply;
			// 연결 시도후, 성공했는지 응답 코드 확인
			reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				System.err.println("서버로부터 연결을 거부당했습니다");
				System.exit(1);
			}
		} catch (IOException ioe) {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException f) {
					//
				}
			}
			System.err.println("서버에 연결할 수 없습니다");
			System.exit(1);
		}
	}

	// FTP의 ls 명령, 모든 파일 리스트를 가져온다
	public FTPFile[] list() {
		FTPFile[] files = null;
		try {
			files = this.ftpClient.listFiles();
			return files;
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return null;
	}

	// 파일을 전송 받는다
	public File get(String source, String target) {
		OutputStream output = null;
		try {
			File local = new File(source);
			output = new FileOutputStream(local);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
		File file = new File(source);
		try {
			if (ftpClient.retrieveFile(source, output)) {
				return file;
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return null;
	}

	// 서버 디렉토리 이동
	public void cd(String path) {
		try {
			ftpClient.changeWorkingDirectory(path);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	// 서버로부터 연결을 닫는다
	private void disconnect() {
		try {
			ftpClient.disconnect();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
