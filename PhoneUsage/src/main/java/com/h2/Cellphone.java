package com.h2;

import java.util.Date;
/*
 * @Author: Linus Kwong
 * @2019, All Rights Reserved
 * 
 * Description: Cell phone data schema/bean to collect cell phone ownership information
 */
public class Cellphone {
	private Integer employeeId;
	private String employeeName;
	private Date purchaseDate;
	private String phoneModel;
	
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public String getPhoneModel() {
		return phoneModel;
	}
	public void setPhoneModel(String phoneModel) {
		this.phoneModel = phoneModel;
	}
	
	@Override
	public String toString() {
		return "Cellphone(" +
			"employeeId=" + employeeId +
			"employeeName=" + employeeName +
			"purchaseDate=" + purchaseDate +
			"phoneModel=" + phoneModel +
			")";
	}
	
	public static Cellphone create(Integer employeeId, String employeeName, Date purchaseDate, String phoneModel) {
		Cellphone cellPhone = new Cellphone ();
		cellPhone.setEmployeeId(employeeId);
		cellPhone.setEmployeeName(employeeName);
		cellPhone.setPurchaseDate(purchaseDate);
		cellPhone.setPhoneModel(phoneModel);
		return cellPhone;
	}
}
