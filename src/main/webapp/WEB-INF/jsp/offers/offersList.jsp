<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="org.springframework.samples.petclinic.model.Status" %>

<petclinic:layout pageName="leagues" userTeam="${userTeam.id}" userTeamName="${userTeam.name}" userLeague="${userTeam.league.id}" userLeagueName="${userTeam.league.name}" userMoney="${userTeam.money}">
	<h2>Welcome to the Pilots Market</h2>

	<table id="offersTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 200px;">Pilot</th>
				<th style="width: 200px;">Nationality</th>
				<th style="width: 150px;">Points earned</th>
				<th>Price</th>
				<th style="width: 150px;">Recruit</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${offers}" var="offer">
			<c:if test="${offer.status == Status.Outstanding}">
				<tr>
					<td><c:out value="${offer.recruit.pilot.name} " /> 
					<c:out value="${offer.recruit.pilot.lastName}" /></td>
					<td><c:out value="${offer.recruit.pilot.nationality} " /></td>
					<td><c:out value="${offer.recruit.pilot.points} " /></td>
					<td><c:out value="${offer.price} " /></td>
					<c:choose>
					<c:when test="${offer.recruit.team == userTeam}">
						<td>
						<spring:url value="/leagues/{leagueId}/market/{offerId}" var="offerUrl">
								<spring:param name="offerId" value="${offer.id}" />
								<spring:param name="leagueId" value="${leagueId}" />
							</spring:url> <a href="${fn:escapeXml(offerUrl)}">
								<span aria-hidden="true">Cancelar venta</span>
							</a>
						</td>
					</c:when>
					<c:otherwise>
						<td>
						<spring:url value="/leagues/{leagueId}/market/{offerId}" var="offerUrl">
								<spring:param name="offerId" value="${offer.id}" />
								<spring:param name="leagueId" value="${leagueId}" />
						</spring:url>
						<form:form method="get" action="${offerUrl}">
								<input name="version" type="hidden" value="${offer.version}">
								<button type="submit">
								<span class="glyphicon glyphicon-euro" aria-hidden="true"></span>
								</button>
							</form:form>
						</td>
					</c:otherwise>
					</c:choose>
				</tr>
			</c:if>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>