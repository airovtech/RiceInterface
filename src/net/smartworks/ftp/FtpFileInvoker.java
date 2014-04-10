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
import java.io.IOException;
import java.util.Properties;

import net.smartworks.util.PropertiesLoader;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

public class FtpFileInvoker {

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
		
		String fileName = prop.getProperty("ftp.fileName");
		
		FTPClient ftp = new FTPClient();
		FTPClientConfig config = new FTPClientConfig();
		ftp.configure(config);
		boolean error = false;
		try {
			int reply;
			ftp.connect(serverAddress, serverPort);
			
			System.out.println("Connected to " + prop.getProperty("ftp.address") + ".");

			reply = ftp.getReplyCode();
			System.out.print(ftp.getReplyString());

			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				System.err.println("FTP server refused connection.");
				System.exit(1);
			}
			
			if(!ftp.login(userId, userPassword)){
				ftp.logout();
				throw new Exception("FTP 서버에 로그인하지 못했습니다.");
			}
			
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.enterLocalPassiveMode();
			
			File f = new File(localDir, fileName);
			
			FileOutputStream fos = new FileOutputStream(f);
			
			boolean isSuccess = ftp.retrieveFile(fileName, fos);
			
			System.out.println("done!" + isSuccess);
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
