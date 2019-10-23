package com.h2;

import java.text.ParseException;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
 * @Author: Linus Kwong
 * @2019, All Rights Reserved
 * 
 * Description: Report Controller to direct relevant output resources
 */
@Controller
public class ReportController {
	@Autowired
	private Dao dao;
	
	@RequestMapping(value="/getCellPhones", method = RequestMethod.GET)
	public String getPhones(Model m) {
		List<Cellphone> phoneList = (List<Cellphone>) dao.getAllCellPhones();
		m.addAttribute("phonelist", phoneList); 
		return "showallphones";
	}
	
	@RequestMapping(value="/printPhoneReport", method = RequestMethod.GET)
	public String printPhoneReport(Model m) throws ParseException {
		// get Year End data
		Map<String, Object> beginendYMDyearendmap = dao.getBeginEndYMDYearEnd();
		m.addAttribute("beginendYMDyearendmap", beginendYMDyearendmap);
		
		// get header section data
		Map<String, Object> headermap = dao.getHeaderSection();
		m.addAttribute("headerdata", headermap);
	
		// get detail section A data 
		List<Cellphone> phoneList = (List<Cellphone>) dao.getAllCellPhones();
		m.addAttribute("phonelist", phoneList);
		
		// get 12 usage months definition from the usage data
		int startmonth = (int) beginendYMDyearendmap.get("beginmonth");
		int startday = (int) beginendYMDyearendmap.get("beginday");
		if (startday > 1) { startmonth++; }
		String[] str = new String[12];
		int i=0;
		while (i < 12) {
			switch(startmonth) {
				case 1: str[i] = "Jan"; break;
				case 2: str[i] = "Feb"; break;
				case 3: str[i] = "Mar"; break;
				case 4: str[i] = "Apr"; break;
				case 5: str[i] = "May"; break;
				case 6: str[i] = "Jun"; break;
				case 7: str[i] = "Jul"; break;
				case 8: str[i] = "Aug"; break;
				case 9: str[i] = "Sep"; break;
				case 10: str[i] = "Oct"; break;
				case 11: str[i] = "Nov"; break;
				case 12: str[i] = "Dec"; break;
			}
			if (startmonth == 12) {startmonth = 1;}
			else {startmonth++;}
			i++;
		}
		m.addAttribute("usagemonths", str);
		
		// get detail section B (b) data
		Map<Integer, List<Map<String, Object>>> usagedatalistmap = new TreeMap<>();
		ListIterator<Cellphone> ltr = phoneList.listIterator();
		while (ltr.hasNext()) {
			Integer empID =  (ltr.next()).getEmployeeId();
			usagedatalistmap.put(empID, dao.getPersonalMinutesDataUsed(empID,beginendYMDyearendmap));
		}
		m.addAttribute("usagedatalistmap", usagedatalistmap);
		
		return "showphoneusage";
	}

}
