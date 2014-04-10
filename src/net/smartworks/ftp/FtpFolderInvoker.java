/* 
 * $Id$
 * created by    : yukm
 * creation-date : 2014. 3. 17.
 * =========================================================
 * Copyright (c) 2014 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.ftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import net.smartworks.util.PropertiesLoader;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FtpFolderInvoker {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		Properties prop = PropertiesLoader.loadProp("ftp.config.properties");
		String serverAddress = prop.getProperty("ftp.address");
		int serverPort = Integer.parseInt(prop.getProperty("ftp.port"));
		String userId = prop.getProperty("ftp.id");
		String userPassword = prop.getProperty("ftp.password");

		String localDir = prop.getProperty("ftp.localDir");
		String remoteDir = prop.getProperty("ftp.remoteDir");
		String logDir = prop.getProperty("ftp.logDir");

		FTPClient ftp = new FTPClient();
		FTPClientConfig config = new FTPClientConfig();
		ftp.configure(config);
		boolean error = false;
		try {
			int reply;
			ftp.connect(serverAddress, serverPort);

			System.out.println("Connected to "
					+ prop.getProperty("ftp.address") + ".");

			reply = ftp.getReplyCode();
			System.out.print(ftp.getReplyString());

			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				System.err.println("FTP server refused connection.");
				System.exit(1);
			}

			if (!ftp.login(userId, userPassword)) {
				ftp.logout();
				throw new Exception("FTP 서버에 로그인하지 못했습니다.");
			}

			ftp.changeWorkingDirectory(remoteDir);
			
			FTPFile[] files = ftp.listFiles();
			System.out.println("Number of files in dir: " + files.length);

			// Open log file
			String logFile = new SimpleDateFormat("yyyyMMddhhmm").format(new Date());
					
			FileWriter fw = new FileWriter(logDir + "/" + logFile +".txt", true);

			for (int i = 0; i < files.length; i++) {

				if (files[i].isDirectory()) {

				} else {
					System.out.println(files[i].getName());
					File file = new File(localDir + File.separator + files[i].getName());
					FileOutputStream fos = new FileOutputStream(file);
					ftp.retrieveFile(files[i].getName(), fos);
					fos.close();
					// Format the date
					String theTime = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(new Date());
					fw.write("FTP'd " + files[i].getName() + " at " + theTime + "\n");

				}
			}
			fw.close();
			System.out.println("done!");
			ftp.logout();
		} catch (IOException e) {
			error = true;
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					// do nothing
				}
			}
			//System.exit(error ? 1 : 0);
		}
	}
}
