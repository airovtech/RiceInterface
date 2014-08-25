/* 
 * $Id$
 * created by    : yukm
 * creation-date : 2014. 7. 17.
 * =========================================================
 * Copyright (c) 2014 ManinSoft, Inc. All rights reserved.
 */

package net.smartworks.rice.model;

import java.util.Date;

public class PcbReport {

	private String idx;
	private String device_id;
	private String pcb_bar;
	private String sensor_bar;
	private Date device_dt;
	private String device_by;
	private Date sensor_dt;
	private String sensor_by;
	private String status;
	private String act_fg;
	private String del_fg;
	private Date loc_dt;
	
	public String getIdx() {
		return idx;
	}
	public void setIdx(String idx) {
		this.idx = idx;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getPcb_bar() {
		return pcb_bar;
	}
	public void setPcb_bar(String pcb_bar) {
		this.pcb_bar = pcb_bar;
	}
	public String getSensor_bar() {
		return sensor_bar;
	}
	public void setSensor_bar(String sensor_bar) {
		this.sensor_bar = sensor_bar;
	}
	public Date getDevice_dt() {
		return device_dt;
	}
	public void setDevice_dt(Date device_dt) {
		this.device_dt = device_dt;
	}
	public String getDevice_by() {
		return device_by;
	}
	public void setDevice_by(String device_by) {
		this.device_by = device_by;
	}
	public Date getSensor_dt() {
		return sensor_dt;
	}
	public void setSensor_dt(Date sensor_dt) {
		this.sensor_dt = sensor_dt;
	}
	public String getSensor_by() {
		return sensor_by;
	}
	public void setSensor_by(String sensor_by) {
		this.sensor_by = sensor_by;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAct_fg() {
		return act_fg;
	}
	public void setAct_fg(String act_fg) {
		this.act_fg = act_fg;
	}
	public String getDel_fg() {
		return del_fg;
	}
	public void setDel_fg(String del_fg) {
		this.del_fg = del_fg;
	}
	public Date getLoc_dt() {
		return loc_dt;
	}
	public void setLoc_dt(Date loc_dt) {
		this.loc_dt = loc_dt;
	}
}
