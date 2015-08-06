/* 
 * $Id$
 * created by    : yukm
 * creation-date : 2014. 4. 25.
 * =========================================================
 * Copyright (c) 2014 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.tle.scheduler.job;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import net.smartworks.factory.ManagerFactory;
import net.smartworks.tle.model.SensorReport;
import net.smartworks.tle.model.TestReport;
import net.smartworks.util.IdUtil;
import net.smartworks.util.PropertiesLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FtpFileTransfer {
	
	static String server;
	static int port;
	static String id;
	static String password;
	FTPClient ftpClient;
	static Log log;

	public FtpFileTransfer(String server, int port, String id, String password) {
		
		this.server = server;
		this.port = port;
		this.id = id;
		this.password = password;
		ftpClient = new FTPClient();
		log = LogFactory.getLog(FtpFileTransfer.class);
	}

	public static void parsing() throws Exception {
		
		Properties prop = PropertiesLoader.loadProp("ftp.config.properties");
		String serverAddress = prop.getProperty("ftp.address");
		int serverPort = Integer.parseInt(prop.getProperty("ftp.port"));
		String userId = prop.getProperty("ftp.id");
		String userPassword = prop.getProperty("ftp.password");
		FtpFileTransfer ftp = new FtpFileTransfer(serverAddress, serverPort, userId, userPassword);
		log.info("###### Start TLE Ftp File Transfer Module at " + new Date() + " ######");
		ftp.connect();
		ftp.login(ftp.id, ftp.password);
		String folderName = "M4522";
		String targetDir = "C:" + folderName;
		log.info("	TLE_From Dir : " + targetDir);
		FTPFile[] files = ftp.list();

		for (int i = 0; i < files.length; i++) {
			String fileName = files[i].getName();
//			파일 이름에서 확장자 추출
			String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
			long size = files[i].getSize();
//			파일 사이즈가 0보다 크고 mdb 확장자 이면서 DBASE이름의 파일을 가져옴
			if ((size > 0) && (extension.equalsIgnoreCase("mdb") && fileName.indexOf("DBASE") != -1)) {
				long start_time = System.currentTimeMillis();
				File file = ftp.get(fileName, fileName);
				long end_time = System.currentTimeMillis();
				log.info("	TLE 전송 시간: " + (end_time - start_time)/1000.0 + " 초");
				log.info("	" + fileName  + " 전송 완료!");
				log.info("	@@ Start Parsing File! : " + fileName);
				log.info("	@@ Save DBASE.mdb Data! : Done!!!!");
			}
		}

		ftp.logout();
		ftp.disconnect();
		log.info("###### End TLE_Ftp File Transfer Module at " + new Date() + " ######");
		
		int lastId = ManagerFactory.getInstance().getRptManager().getLastId();
		MdbDao mdbDao = new MdbDaoImpl();
		List<String> listLotNo = mdbDao.SearchSensorReportById(lastId);
		
		if(!listLotNo.isEmpty()) {
			for(String lotNo : listLotNo) { 
				TestReport tr = new TestReport();
				tr = mdbDao.readMdbByLotNo(lotNo);
				tr.setLotNo(lotNo);
				tr.setId("rpt_"+IdUtil.getInstance().generate());
				tr.setDateTime(tr.getSensorReports()[0].getDateTime());
				tr.setFairQualityCount(mdbDao.fairQualityCount(lotNo));
				tr.setFaultCount(mdbDao.faultCount(lotNo));
				for(SensorReport sr : tr.getSensorReports()) {
					sr.setTestReportId(tr.getId());
				}
				ManagerFactory.getInstance().getRptManager().setTestReport(tr);
			}
			log.info("	TLE_데이터가 추가되었습니다.");
		}
		else {
			log.info("	TLE_새로운 데이터가 없습니다.");
		}
		
		/*String fileName = "DBASE.mdb";
		File f = new File(fileName);
		if(f.delete()) {
			log.info(fileName + "가 삭제되었습니다.");
		}
		else {
			log.info("	삭제할 파일이 존재하지 않습니다.");
		}*/
	}

	public boolean localFileDelete(String filePath, String fileName) {
		return true;
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
			}
		} catch (IOException ioe) {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException f) {
					f.printStackTrace();
				}
			}
			System.err.println("서버에 연결할 수 없습니다");
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
