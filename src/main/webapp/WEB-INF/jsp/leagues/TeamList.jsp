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
	

	
	
		
		<c:if test="${admin == true}">
		<table id="teamsTable" class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
				<th>Points</th>
<!-- 				<c:if test="condition"></c:if> -->
				<th>money</th>
				<th>user</th>
				<th>Actions</th>
	
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${teams}" var="team">
		<tr>
					<td><c:out value="${team.name} " /></td>
					<td><c:out value="${team.points}" /></td>
					<td><c:out value="${team.money}" /></td>
					<td><c:out value="${team.user.username}" /></td>
					<td>
						<spring:url value="/leagues/{leagueId}/teams/{teamId}/edit" var="TeamUrl">
								<spring:param name="teamId" value="${team.id}" />
							<spring:param name="leagueId" value="${team.league.id}" />
						</spring:url> <a href="${fn:escapeXml(TeamUrl)}">Edit</a>
						<spring:url value="/leagues/{leagueId}/teams/{teamId}/delete" var="TeamUrl">
							<spring:param name="teamId" value="${team.id}" />
							<spring:param name="leagueId" value="${league.id}" />
						</spring:url> <a href="${fn:escapeXml(TeamUrl)}">Delete</a>
						
<!-- 			Visualizar fichajes de un equipo -->

						<spring:url value="/leagues/{leagueId}/teams/{teamId}/details" var="TeamUrl">
							<spring:param name="teamId" value="${team.id}" />
							<spring:param name="leagueId" value="${league.id}" />
						</spring:url> <a href="${fn:escapeXml(TeamUrl)}">Details</a>
						
<!-- 			Visualizar fichajes de un equipo -->
						
					
					</td>
				</tr>
				
		</c:forEach>
		</tbody>
		</table>
				<spring:url value="/leagues/{leagueId}/teams/new" var="TeamUrl">
							<spring:param name="leagueId" value="${league.id}" />
						</spring:url> <a href="${fn:escapeXml(TeamUrl)}">New</a>
	<spring:url value="market" var="marketUrl">
	</spring:url> <a href="${fn:escapeXml(marketUrl)}">Go to Market</a>
	
		</c:if>
		<c:if test="${usuario == true }">
		<table id="teamsTable" class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
				<th>Points</th>
<!-- 				<c:if test="condition"></c:if> -->
				<th>money</th>
				<th>user</th>
				<th>Actions</th>
	
			</tr>
		</thead>
		<tbody>
		
			<c:forEach items="${teams}" var="team">
			<c:if test="${team.name != 'Sistema' }">
				<c:if test="${team.user.username == user.username}">
				<tr>
					<td><c:out value="${team.name} " /></td>
					<td><c:out value="${team.points}" /></td>
					<td><c:out value="${team.money}" /></td>
					<td><c:out value="${team.user.username}" /></td>
					<td>
						<spring:url value="/leagues/{leagueId}/teams/{teamId}/edit" var="TeamUrl">
								<spring:param name="teamId" value="${team.id}" />
							<spring:param name="leagueId" value="${team.league.id}" />
						</spring:url> <a href="${fn:escapeXml(TeamUrl)}">Edit</a>
						<spring:url value="/leagues/{leagueId}/teams/{teamId}/delete" var="TeamUrl">
							<spring:param name="teamId" value="${team.id}" />
							<spring:param name="leagueId" value="${league.id}" />
						</spring:url> <a href="${fn:escapeXml(TeamUrl)}">Delete</a>
						
<!-- 			Visualizar fichajes de un equipo -->

						<spring:url value="/leagues/{leagueId}/teams/{teamId}/details" var="TeamUrl">
							<spring:param name="teamId" value="${team.id}" />
							<spring:param name="leagueId" value="${league.id}" />
						</spring:url> <a href="${fn:escapeXml(TeamUrl)}">Details</a>
						
<!-- 			Visualizar fichajes de un equipo -->
						
					
					</td>
				</tr>
				</c:if>
				
				<c:if test="${team.user.username != user.username}">
				<tr>
					<td><c:out value="${team.name} " /></td>
					<td><c:out value="${team.points}" /></td>
					<td><c:out value="${team.money}" /></td>
					<td><c:out value="${team.user.username}" /></td>
					<td><c:out value="" /></td>
				</tr>
				</c:if>
				</c:if>
			</c:forEach>
			
			
		</tbody>
		</table>
	</c:if>


</petclinic:layout>