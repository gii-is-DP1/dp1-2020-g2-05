<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="granPremios">

	<h2>GPs calendar</h2>
	
	<table class="table table-striped">
		<tr>
			<th style="text-align:center;">Site</th>
			<th style="text-align:center;">Circuit</th>
			<th style="text-align:center;">Date</th>
			<th style="text-align:center;">Results</th>
		</tr>
		
		<c:forEach items="${listaGP}" var="res">
			<tr>
				<c:if test="${racesCompleted >= res.id}">

					<td	style="text-align: center; background-color: #b8bbb757; color: #00000040;"><c:out value="${res.site}" /></td>
					<td	style="text-align: center; background-color: #b8bbb757; color: #00000040;"><c:out value="${res.circuit}" /></td>
					<td	style="text-align: center; background-color: #b8bbb757; color: #00000040;"><c:out value="${res.date0}" /></td>
					<td	style="text-align: center; background-color: #b8bbb757; color: #00000040;">
					
						<spring:url value="granPremios/{gpId}/results/MOTOGP/{raceCode}" var="leagueUrl">
							<spring:param name="raceCode" value="${res.raceCode}" />
							<spring:param name="gpId" value="${res.id}" />
						</spring:url> <a href="${fn:escapeXml(leagueUrl)}"> MotoGP</a>
						
						<spring:url value="granPremios/{gpId}/results/MOTO2/{raceCode}" var="leagueUrl">
							<spring:param name="raceCode" value="${res.raceCode}" />
							<spring:param name="gpId" value="${res.id}" />
						</spring:url> <a href="${fn:escapeXml(leagueUrl)}"> Moto2</a>
						
						<spring:url value="granPremios/{gpId}/results/MOTO3/{raceCode}" var="leagueUrl">
							<spring:param name="raceCode" value="${res.raceCode}" />
							<spring:param name="gpId" value="${res.id}" />
						</spring:url> <a href="${fn:escapeXml(leagueUrl)}"> Moto3</a>
						
					</td>
				</c:if>
			</tr>
			
			<c:if test="${racesCompleted < res.id}">

				<td style="text-align: center;"><c:out value="${res.site}" /></td>
				<td style="text-align: center;"><c:out value="${res.circuit}" /></td>
				<td style="text-align: center;"><c:out value="${res.date0}" /></td>
				<td style="text-align: center;">
				
					<spring:url value="granPremios/{gpId}/results/MOTOGP/{raceCode}" var="leagueUrl">
						<spring:param name="raceCode" value="${res.raceCode}" />
						<spring:param name="gpId" value="${res.id}" />
					</spring:url> <a href="${fn:escapeXml(leagueUrl)}"> MotoGP</a>
						
					<spring:url value="granPremios/{gpId}/results/MOTO2/{raceCode}" var="leagueUrl">
						<spring:param name="raceCode" value="${res.raceCode}" />
						<spring:param name="gpId" value="${res.id}" />
					</spring:url> <a href="${fn:escapeXml(leagueUrl)}"> Moto2</a>
						
					<spring:url value="granPremios/{gpId}/results/MOTO3/{raceCode}" var="leagueUrl">
						<spring:param name="raceCode" value="${res.raceCode}" />
						<spring:param name="gpId" value="${res.id}" />
					</spring:url> <a href="${fn:escapeXml(leagueUrl)}"> Moto3</a>
					
			</c:if>
		</c:forEach>
	</table>
</petclinic:layout>
