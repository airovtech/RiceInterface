/* 
 * $Id$
 * created by    : yukm
 * creation-date : 2014. 4. 25.
 * =========================================================
 * Copyright (c) 2014 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.tle.scheduler.trigger;

import net.smartworks.tle.scheduler.job.GetDataStarterJob;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

public class RiceCronTrigger {

	private SchedulerFactory schedulerFactory;
	private Scheduler sched;
	
	public RiceCronTrigger() {

		try {
			/*schedulerFactory = new StdSchedulerFactory();
			sched = schedulerFactory.getScheduler();
			sched.start();
			
			JobDetail job1 = new JobDetailImpl("job1", "group1", GetDataJob.class);
			CronTrigger trigger = new CronTriggerImpl("grigger1", "group1", "0 * * * * ?");
			sched.scheduleJob(job1, trigger);*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
