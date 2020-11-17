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
				<th>Actions</th>
	
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${teams}" var="team">
				<tr>
					<td><c:out value="${team.name} " /></td>
					<td><c:out value="${team.points}" /></td>
					<td><c:out value="${team.money}" /></td>
					<td>
<%-- 						<spring:url value="/pilots/edit/{pilotId}" var="pilotUrl"> --%>
<%-- 							<spring:param name="pilotId" value="${pilot.id}" /> --%>
<%-- 						</spring:url> <a href="${fn:escapeXml(pilotUrl)}">Edit</a> --%>
						<spring:url value="/leagues/{leagueId}/teams/{teamId}/delete" var="TeamUrl">
							<spring:param name="teamId" value="${team.id}" />
							<spring:param name="leagueId" value="${league.id}" />
						</spring:url> <a href="${fn:escapeXml(TeamUrl)}">Delete</a>
						<spring:url value="/leagues/{leagueId}/teams/new" var="TeamUrl">
							<spring:param name="leagueId" value="${league.id}" />
						</spring:url> <a href="${fn:escapeXml(TeamUrl)}">NEW</a>
					
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>