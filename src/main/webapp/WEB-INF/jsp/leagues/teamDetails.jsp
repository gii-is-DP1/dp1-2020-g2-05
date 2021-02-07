<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ page import="org.springframework.samples.petclinic.model.Status"%>

<petclinic:layout pageName="teamDetails" userTeam="${team.id}" userTeamName="${team.name}" userLeague="${team.league.id}" userLeagueName="${team.league.name}" userMoney="${team.money}">

	<h2>Team Information</h2>


	<table class="table table-striped">
		<tr>
			<th>Name</th>
			<th>Points</th>
			<th>Money</th>
			<th>User</th>
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
			<th>Points</th>
			<th>Nationality</th>
			<th>Actions</th>
		</tr>
		<c:forEach items="${misFichajes}" var="recruit">
			<tr>
				<td><c:out value="${recruit.pilot.name}" /></td>
				<td><c:out value="${recruit.pilot.lastName}" /></td>
				<td><c:out value="${recruit.pilot.category}" /></td>
				<td><c:out value="${recruit.pilot.points}" /></td>
				<td><c:out value="${recruit.pilot.nationality}" /></td>
				<c:choose>
					<c:when test="${recruit.id == recruitToSale.id}">
						<form:form modelAttribute="offer" class="form-horizontal"
							id="setPrice"
							actions="/leagues/{leagueId}/teams/{teamId}/details/{recruitId}">
							<td><div class="form-group has-feedback">
									<petclinic:inputField label="Price" name="price" />
									<button class="btn btn-default" type="submit">Set
										Price</button>
								</div></td>
						</form:form>
					</c:when>
					<c:otherwise>
						<td><spring:url
								value="/leagues/{leagueId}/teams/{teamId}/details/{recruitId}"
								var="putOnSaleUrl">
								<spring:param name="leagueId" value="${team.league.id}" />
								<spring:param name="teamId" value="${team.id}" />
								<spring:param name="recruitId" value="${recruit.id}" />
							</spring:url> <a href="${fn:escapeXml(putOnSaleUrl)}">Poner a la venta</a></td>
					</c:otherwise>
				</c:choose>
			</tr>
		</c:forEach>
		<c:forEach items="${fichajesEnVenta}" var="recruit">
			<tr>
				<td><c:out value="${recruit.pilot.name}" /></td>
				<td><c:out value="${recruit.pilot.lastName}" /></td>
				<td><c:out value="${recruit.pilot.category}" /></td>
				<td><c:out value="${recruit.pilot.points}" /></td>
				<td><c:out value="${recruit.pilot.nationality}" /></td>
				<td><spring:url value="/leagues/{leagueId}/market/{offerId}" var="offerUrl">
						<spring:param name="offerId" value="${recruit.offer
 							.stream().filter(o->o.getStatus() == Status.Outstanding).findFirst().get().id}" />
						<spring:param name="leagueId" value="${leagueId}" />
					</spring:url> <a href="${fn:escapeXml(offerUrl)}"> <span aria-hidden="true">Cancelar venta</span></a></td>
			</tr>
		</c:forEach>
	</table>



	<h2>Lineup Selection</h2>

	<spring:url value="/leagues/{leagueId}/teams/{teamId}/newLineup"
		var="newLineupUrl">
		<spring:param name="leagueId" value="${team.league.id}" />
		<spring:param name="teamId" value="${team.id}" />
	</spring:url>
	<a href="${fn:escapeXml(newLineupUrl)}">New lineup</a>

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
					<td><c:out value="${lineup.gp.date0}, ${lineup.gp.site}, ${lineup.gp.circuit}" /></td>
					<td><c:out value="${lineup.recruit1.pilot.name} ${lineup.recruit1.pilot.lastName}" /></td>
					<td><c:out value="${lineup.recruit2.pilot.name} ${lineup.recruit2.pilot.lastName}" /></td>
					<c:if test="${!lineup.gp.hasBeenRun}">
						<td><spring:url value="editLineup/{lineupId}" var="lineupUrl">
								<spring:param name="lineupId" value="${lineup.id}" />
							</spring:url> <a href="${fn:escapeXml(lineupUrl)}">Edit</a> <spring:url
								value="delete/{lineupId}" var="lineupUrl">
								<spring:param name="lineupId" value="${lineup.id}" />
							</spring:url> <a href="${fn:escapeXml(lineupUrl)}">Delete</a></td>
					</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>
