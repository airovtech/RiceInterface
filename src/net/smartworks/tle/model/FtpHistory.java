/* 
 * $Id$
 * created by    : yukm
 * creation-date : 2014. 4. 25.
 * =========================================================
 * Copyright (c) 2014 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.tle.model;

import java.util.Date;

public class FtpHistory {

	private String id;
	private String folderName;
	private String fileName;
	private String fileSerial;
	private Date createDate;
	private String lastId;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileSerial() {
		return fileSerial;
	}
	public void setFileSerial(String fileSerial) {
		this.fileSerial = fileSerial;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getLastId() {
		return lastId;
	}
	public void setLastId(String lastId) {
		this.lastId = lastId;
	}
}
