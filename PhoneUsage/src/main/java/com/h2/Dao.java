package com.h2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
/*
 * @Author: Linus Kwong
 * @2019, All Rights Reserved
 * 
 * Description: data repository to provide relevant data for the cell phone usage report
 */
@Repository
public class Dao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	// Load cell phones ownership data into H2 from csv file
	public void addPhone(Cellphone cPhone) {
		String sql = "insert into cellphone (employeeId, employeeName, purchaseDate, phoneModel) values (?,?,?,?)";
		jdbcTemplate.update(sql, cPhone.getEmployeeId(), cPhone.getEmployeeName(), cPhone.getPurchaseDate(),cPhone.getPhoneModel());
	}

	// Load phone usage data into H2 from csv file
	public void addPhoneUsage(Phoneusage phUsage) {
		String sql = "insert into phoneusage (employeeId, usageDate, minutesUsed, dataUsed) values (?,?,?,?)";
		jdbcTemplate.update(sql, phUsage.getEmployeeId(), phUsage.getUsageDate(), phUsage.getMinutesUsed(),phUsage.getDataUsed());
	}
	
	// get preliminary data for
	// begin (year, month, day) and end (year, month, day)
	// actual (begin date, end date, year end) for phone minutes and data usage
	// e.g, from 9/21/2017 to 9/20/2018 based on given phone usage
	public Map<String,Object> getBeginEndYMDYearEnd(){
		String sql = "select min(usagedate) begindate, DATEADD(year, 1, DATEADD(day, -1 , min(usagedate))) enddate, DATEADD(year, 1, min(usagedate)) enddateplusone, DATEADD(dd, -DAY(DATEADD(m,1,min(usagedate))), DATEADD(m,13,min(usagedate))) yearend, year(min(usagedate)) beginyear, year(DATEADD(year, 1, DATEADD(day, -1 , min(usagedate)))) endyear, month(min(usagedate)) beginmonth, day(min(usagedate)) beginday, month(DATEADD(year, 1, DATEADD(day, -1 , min(usagedate)))) endmonth, day(DATEADD(year, 1, DATEADD(day, -1 , min(usagedate)))) endday from phoneusage";
		return jdbcTemplate.queryForMap(sql);
	}
	
	// Obtain Header Section data
	// Get number of phones, total minutes, total data, aversage minutes, and
	// average data
	public Map<String, Object> getHeaderSection() {
		Map<String,Object> beginEndDates = getBeginEndYMDYearEnd();
		String enddateplusone = beginEndDates.get("endyear").toString()+"-"+beginEndDates.get("endmonth").toString()+"-"+beginEndDates.get("beginday").toString();
		String sql = "select count (distinct c1.employeeId) no, sum(u1.minutesused) totmin, cast((sum(u1.dataused)) as decimal(10,2)) totdata,  (sum(u1.minutesused))/12 avemin, cast(((sum(u1.dataused))/12) as decimal(10,2)) avedata from cellphone c1, phoneusage u1 where c1.employeeid = u1.employeeid and (u1.usagedate >= '"+beginEndDates.get("begindate")+"') and (u1.usagedate < '"+enddateplusone+"')";
		return jdbcTemplate.queryForMap(sql);
	}

	// Obtain Detail Section A data
	// get the list of cell phone owners
	public List<Cellphone> getAllCellPhones() {
		String sql = "select * from cellphone order by 1";
		return jdbcTemplate.query(sql, (resultSet, i) -> {
			return toCellphone(resultSet);
		});
	}

	private Cellphone toCellphone(ResultSet resultSet) throws SQLException {
		Cellphone cellPhone = new Cellphone();
		cellPhone.setEmployeeId(resultSet.getInt("employeeId"));
		cellPhone.setEmployeeName(resultSet.getString("employeeName"));
		cellPhone.setPurchaseDate(resultSet.getDate("purchaseDate"));
		cellPhone.setPhoneModel(resultSet.getString("phoneModel"));
		return cellPhone;
	}

	// Obtain Detail Section B (b) data
	// get total minutes used an total data used per employee or phone user
	public List<Map<String, Object>> getPersonalMinutesDataUsed(Integer empid, Map<String, Object> map) throws ParseException{
		Integer beginyear = (Integer) map.get("beginyear");
		Integer endyear = (Integer) map.get("endyear");  // no change
		Integer beginmonth = (Integer) map.get("beginmonth");
		Integer endmonth = (Integer) map.get("endmonth"); // no change ??
		Integer beginday = (Integer) map.get("beginday"); // no change
		Integer endday = (Integer) map.get("endday"); 	// no change
		Integer nextmonth = 0;
		StringBuffer str_startdate = new StringBuffer();
		StringBuffer str_enddate = new StringBuffer();
		String pattern = "yyyy-MM-dd";
		List<Map<String, Object>> mapList = new ArrayList<>();
		int i=0;
		while (i<12) {
			if (beginyear <= endyear) {
				if ((beginyear == endyear)&&(beginmonth==endmonth)) {break;}
				if (i > 0) { 
					str_startdate.delete(0, str_startdate.length()); 
					str_enddate.delete(0, str_enddate.length()); 
				}
				str_startdate.append(beginyear);
				str_startdate.append('-');
				str_startdate.append(beginmonth);
				str_startdate.append('-');
				str_startdate.append(beginday);	
				if (beginmonth == 12) {
					nextmonth = 1;
					beginyear = beginyear + 1;
					beginmonth = 1;
				} else {
					nextmonth = beginmonth + 1;
					beginmonth++;
				}
				str_enddate.append(beginyear);
				str_enddate.append('-');	
				str_enddate.append(nextmonth);
				str_enddate.append('-');
				str_enddate.append(endday);	

				String sql = "select sum(minutesused) totmin, cast((sum(dataused)) as decimal(10,2)) totdata from phoneusage where employeeid="+empid+" and (usagedate between parsedatetime('"+str_startdate.toString()+"', '"+pattern+"') and parsedatetime('"+str_enddate.toString()+"', '"+pattern+"'))";
				Map<String, Object> onemonthusagetotalperuser = jdbcTemplate.queryForMap(sql);
				mapList.add(onemonthusagetotalperuser);
			}
			i++;
		}
		return mapList; 
	}
}
