<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Show All Cell phones</title>
<style>
table,th,td {
	border: 1px solid black;
}
</style>
</head>
<body>
<div align="center"><H3>All Cell Phones For All Employee</H3></div>
<c:set var="today" value="<%=new java.util.Date() %>" />
Today is <fmt:formatDate type="both" value="${today}" />
<%-- List all company's cell phones --%>
<table>
	<tbody>
		<tr>
			<th>ID</th>
			<th>Employee Name</th>
			<th>PurchaseDate</th>
			<th>Model</th>
		</tr>
		<c:forEach var="ph" items="${requestScope.phonelist}">
			<tr>
				<td><c:out value="${ph.employeeId}" /></td>
				<td><c:out value="${ph.employeeName}" /></td>
				<td><c:out value="${ph.purchaseDate}" /></td>
				<td><c:out value="${ph.phoneModel}" /></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<br />
<a href="/">Go Back</a>
</body>
</html>