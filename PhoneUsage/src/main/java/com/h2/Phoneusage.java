package com.h2;

import java.util.Date;
/*
 * @Author: Linus Kwong
 * @2019, All Rights Reserved
 * 
 * Description: Phoneusage data schema/bean to collect phone usage information
 */
public class Phoneusage {
	private Integer employeeId;
	private Date usageDate;
	private Integer minutesUsed;
	private Double dataUsed;
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public Date getUsageDate() {
		return usageDate;
	}
	public void setUsageDate(Date usageDate) {
		this.usageDate = usageDate;
	}
	public Integer getMinutesUsed() {
		return minutesUsed;
	}
	public void setMinutesUsed(Integer minutesUsed) {
		this.minutesUsed = minutesUsed;
	}
	public Double getDataUsed() {
		return dataUsed;
	}
	public void setDataUsed(Double dataUsed) {
		this.dataUsed = dataUsed;
	}
	
	@Override
	public String toString() {
		return "Phoneusage(" +
				"employeeId=" + employeeId +
				"usageDate=" + usageDate +
				"minutesUsed=" + minutesUsed +
				"dataUsed=" + dataUsed +
				")";
	}
	
	public static Phoneusage create(Integer employeeId, Date usageDate, Integer minutesUsed, Double dataUsed) {
		Phoneusage phoneUsage = new Phoneusage ();
		phoneUsage.setEmployeeId(employeeId);
		phoneUsage.setUsageDate(usageDate);
		phoneUsage.setMinutesUsed(minutesUsed);
		phoneUsage.setDataUsed(dataUsed);
		return phoneUsage;
	}
}
