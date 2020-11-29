<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="teamDetails">

	<h2>Team Information</h2>


	<table class="table table-striped">
		<tr>
			<th>Name</th>
			<th>Points</th>
			<th>Money</th>
			<th>User</th>
			<!-- 			<th>Actions</th> -->
		</tr>
		<tr>
			<td><c:out value="${team.name} " /></td>
			<td><c:out value="${team.points}" /></td>
			<td><c:out value="${team.money}" /></td>
			<td><c:out value="${team.user.username}" /></td>
		</tr>
	</table>

	<h2>Riders Information</h2>

	<table class="table table-striped">
		<tr>
			<th>Name</th>
			<th>Last Name</th>
			<th>Category</th>
			<th>Dorsal</th>
			<th>Nationality</th>
			<th>Actions</th>
		</tr>
		<c:forEach items="${misFichajes}" var="piloto">
			<tr>
				<td><c:out value="${piloto.name}" /></td>
				<td><c:out value="${piloto.lastName}" /></td>
				<td><c:out value="${piloto.category}" /></td>
				<td><c:out value="${piloto.dorsal}" /></td>
				<td><c:out value="${piloto.nationality}" /></td>
				<td><spring:url value="/pilots/delete/{pilotoId}" var="pilotoUrl">
						<spring:param name="pilotoId" value="${piloto.id}" />
					</spring:url> <a href="${fn:escapeXml(pilotoUrl)}">Poner a la venta</a></td>
			</tr>
		</c:forEach>
	</table>

	<h2>Lineup Selection</h2>

	<spring:url value="/leagues/{leagueId}/teams/{teamId}/newLineup" var="newLineupUrl">
		<spring:param name="leagueId" value="${team.league.id}" />
		<spring:param name="teamId" value="${team.id}" />
	</spring:url> <a href="${fn:escapeXml(newLineupUrl)}">New lineup</a>
	
	<table id="lineupsTable" class="table table-striped">
		<thead>
			<tr>
				<th>Category</th>
				<th>GP</th>
				<th>Recruit1</th>
				<th>Recruit2</th>
				<th>Actions</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${misAlineaciones}" var="lineup">
				<tr>
					<td>${lineup.category}</td>
					<td><c:out
							value="${lineup.gp.site}, ${lineup.gp.circuit}, ${lineup.gp.date0} " /></td>
					<td><c:out
							value="${lineup.recruit1.pilot.name} ${lineup.recruit1.pilot.lastName}" /></td>
					<td><c:out
							value="${lineup.recruit2.pilot.name} ${lineup.recruit2.pilot.lastName}" /></td>
					<td><spring:url value="editLineup/{lineupId}"
							var="lineupUrl">
							<spring:param name="lineupId" value="${lineup.id}" />
						</spring:url> <a href="${fn:escapeXml(lineupUrl)}">Edit</a> <spring:url
							value="delete/{lineupId}" var="lineupUrl">
							<spring:param name="lineupId" value="${lineup.id}" />
						</spring:url> <a href="${fn:escapeXml(lineupUrl)}">Delete</a></td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>
