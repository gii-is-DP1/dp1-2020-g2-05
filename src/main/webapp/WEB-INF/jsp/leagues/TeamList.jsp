<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
    
<petclinic:layout pageName="Teams">
	<h2>Teams</h2>

	<table id="teamsTable" class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
				<th>Points</th>
<!-- 				<c:if test="condition"></c:if> -->
				<th>money</th>
	
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${teams}" var="team">
				<tr>
					<td><c:out value="${team.name} " /></td>
					<td><c:out value="${team.points}" /></td>
					<td><c:out value="${team.money}" /></td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>