/* 
 * $Id$
 * created by    : yukm
 * creation-date : 2014. 4. 25.
 * =========================================================
 * Copyright (c) 2014 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.rice.scheduler.job;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetDataStarterJob implements Job{

	Logger log = LoggerFactory.getLogger(GetDataStarterJob.class);
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log.info("**** Job Start Date : " + new Date() + "+++++");
		try {
			FtpFileTransfer.parsing();
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("**** Job End Date : " + new Date() + "+++++");
	}

}
