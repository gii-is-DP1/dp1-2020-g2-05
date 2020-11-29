<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ page import="org.springframework.samples.petclinic.util.Status" %>

<petclinic:layout pageName="leagues">
	<h2>Welcome to the Pilots Market</h2>

	<table id="offersTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Pilot</th>
				<th style="width: 900px;">Price</th>
				<th>Recruit</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${offers}" var="offer">
			<c:if test="${offer.status == Status.Outstanding}">
				<tr>
					<td><c:out value="${offer.pilot.name}" /></td>
					<td><c:out value="${offer.price} " /></td>
					<td>
					<spring:url value="/leagues/{leagueId}/market/{offerId}" var="offerUrl">
							<spring:param name="offerId" value="${offer.id}" />
							<spring:param name="leagueId" value="${leagueId}" />
						</spring:url> <a href="${fn:escapeXml(offerUrl)}">
							<span class="glyphicon glyphicon-euro" aria-hidden="true"></span>
						</a>
					</td>
				</tr>
			</c:if>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>