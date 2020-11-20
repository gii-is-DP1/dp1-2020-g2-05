<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="leagues">
	<h2>Your Teams</h2>
	
	

	<table id="TeamsTable" class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
				<th>Points</th>
				<th>Money</th>
				<th>User</th>
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
							<spring:param name="leagueId" value="${league.id}" />
						</spring:url> <a href="${fn:escapeXml(TeamUrl)}">Edit</a>
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