<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cell Phone Usage Report</title>
</head>
<body onload="window.print()">
<c:set var="today" value="<%=new java.util.Date() %>" />
<div align="center" style="font-size:20px;font-weight:bold;">Cell Phone Usage Report<br />
For the year ended <fmt:formatDate type="date" dateStyle = "long" value="${beginendYMDyearendmap.yearend}" /></div>
<p style="text-decoration: underline;font-size:16px;font-weight:bold;">General Section</p>
<table  style="padding:0px; border-spacing: 0px;font-size:10px; ">
<tr><th align="right">Report Run Date:  </th><td align="left"><fmt:formatDate type="both" value="${today}" /></td></tr>
<tr><th align="right">Number of Phones:  </th><td align="left"> <c:out value="${headerdata.no}" /></td></tr>
<tr><th align="right">Total Minutes:  </th><td align="left"> <c:out value="${headerdata.totmin}" /></td></tr>
<tr><th align="right">Total Data:  </th><td align="left"> <c:out value="${headerdata.totdata}" /></td></tr>
<tr><th align="right">Average Minutes:  </th><td align="left"> <c:out value="${headerdata.avemin}" /></td></tr>
<tr><th align="right">Average Data:  </th><td align="left"> <c:out value="${headerdata.avedata}" /></td></tr>
</table>
<p style="text-decoration: underline;font-size:16px;font-weight:bold;">Details Section</p>
<p style="font-size:2px;">(* account for the period between <fmt:formatDate type="date" dateStyle = "long" value="${beginendYMDyearendmap.begindate}" /> and ended <fmt:formatDate type="date" dateStyle = "long" value="${beginendYMDyearendmap.enddate}" />)</p>
<table  style="padding:0px; border-spacing: 0px;font-size:5px;">
	<tbody>
		<c:forEach var="ph" items="${requestScope.phonelist}">
			<tr><th align="right">Employee ID:  </th><td align="left" colspan="13"><c:out value="${ph.employeeId}" /></td></tr>
			<tr><th align="right">Employee Name:  </th><td align="left" colspan="13"><c:out value="${ph.employeeName}" /></td></tr>
			<tr><th align="right">Model:  </th><td align="left" colspan="13"><c:out value="${ph.phoneModel}" /></td></tr>
			<tr><th align="right">Purchase Date:  </th><td align="left" colspan="13"><fmt:formatDate type="date" value="${ph.purchaseDate}" /></td></tr>
			<tr><th colspan="2">&nbsp;</th>
			<c:forEach var="eachmonth" items="${usagemonths}">
				<th align="center"><c:out value="${eachmonth}" /></th>
			</c:forEach>
			</tr>
			<tr><th colspan="2" align="right">Minutes Usage<sup>*</sup>:  </th>
			<c:forEach var="totmin12monthList" items="${usagedatalistmap.get(ph.employeeId)}">			
				<td align="center"><c:out value="${totmin12monthList.totmin}" /></td>
			</c:forEach>
			</tr>
			<tr><th colspan="2" align="right">Data Usage<sup>*</sup>:  </th>
			<c:forEach var="totdata12monthList" items="${usagedatalistmap.get(ph.employeeId)}">			
				<td align="center"><c:out value="${totdata12monthList.totdata}" /></td>
			</c:forEach>
			</tr>
			<tr><td colspan="14"><hr size=3></td></tr>
		</c:forEach>
	</tbody>
</table>

</body>
</html>