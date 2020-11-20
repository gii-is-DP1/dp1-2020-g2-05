<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="leagues">
	<h2>Your Leagues</h2>
	<h2>        <c:if test="${yaTienesEquipo}">You already have a team in this League :( !</c:if> 
	</h2>
	<table id="leaguesTable" class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
				<th>Code</th>
				<th>Date</th>
				<th>Category</th>
				<th>Races Completed</th>
				<th>Teams</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${misLigas}" var="league">
					
				<tr>
					
					<td><c:out value="${league.name} " /></td>
					<td><c:out value="${league.leagueCode} " /></td>
					<td><c:out value="${league.leagueDate}" /></td>
					<c:if test="${league.motogpActive==true}">  
					<td>Moto GP </td>					
					</c:if>
					<c:if test="${league.moto2Active==true}">  
					<td>Moto 2 </td>					
					</c:if>
					<c:if test="${league.moto3Active==true}">  
					<td>Moto 3 </td>					
					</c:if>  
					<td><c:out value="${league.racesCompleted}" /></td>
					<td>
					<spring:url value="/leagues/{leagueId}/teams" var="leagueUrl">
							<spring:param name="leagueId" value="${league.id}" />
						</spring:url> <a href="${fn:escapeXml(leagueUrl)}">Teams</a>
					</td>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<spring:url value="/leagues/new" var="messageUrl">
	</spring:url> <a href="${fn:escapeXml(messageUrl)}">New league</a>
	<spring:url value="/leagues/join" var="messageUrl">
	</br>
	</spring:url> <a href="${fn:escapeXml(messageUrl)}">Join league</a>
</petclinic:layout>