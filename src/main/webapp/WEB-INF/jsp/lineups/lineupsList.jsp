<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="lineups">
	<h2>Lineups</h2>

	<table id="lineupsTable" class="table table-striped">
		<thead>
			<tr>
				<th>Category</th>
				<th>GP</th>
				<th>League</th>
				<th>Recruit1</th>
				<th>Recruit2</th>
				<th>Team</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${lineups}" var="lineup">
				<tr>
					<td>${lineup.category}</td>
					<td>${lineup.gp}</td>
					<td>${lineup.league}</td>
					<td>${lineup.recruit1}</td>
					<td>${lineup.recruit2}</td>
					<td>${lineup.team}</td>
<!-- 					<td> -->
<%-- 					<spring:url value="/lineups/{lineupId}/teams" var="lineupUrl"> --%>
<%-- 						<spring:param name="lineupId" value="${lineup.id}" /> --%>
<%-- 					</spring:url> <a href="${fn:escapeXml(lineupUrl)}">Teams</a> --%>
<!-- 					</td> -->
				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>