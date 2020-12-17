<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="leagues">
	<h2>Leagues</h2>
	
	<table id="leaguesTable" class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
			<c:if test="${admin==true}"><th>Code</th></c:if>	
				<th>Date</th>
				<th>Category</th>
				<th>Teams</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${ligas}" var="league">
					<tr>	<!--  onclick="window.location='/leagues/${league.id}/details'"  -->
					<td><c:out value="${league.name} " /></td>
					<c:if test="${admin==true}"><td><c:out value="${league.leagueCode} " /></td></c:if>
					<td><c:out value="${league.leagueDate}" /></td>
					<c:if test="${league.racesCompleted<10}">  
					<td>Moto 3 </td>					
					</c:if>
					<c:if test="${league.racesCompleted>=10 && league.racesCompleted<15}">  
					<td>Moto 2 </td>					
					</c:if>
					<c:if test="${league.racesCompleted>=15}">  
					<td>Moto GP </td>					
					</c:if>  
					<td>
					<spring:url value="/leagues/{leagueId}/teams" var="leagueUrl">
							<spring:param name="leagueId" value="${league.id}" />
						</spring:url> <a href="${fn:escapeXml(leagueUrl)}">Teams</a>
					</td>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>
					<spring:url value="/BD/carrerasBD" var="leagueUrl">
						</spring:url> <a href="${fn:escapeXml(leagueUrl)}"><img src="resources/images/meta.png" style="height:15px;weight:15px;border:none;"/></a>
<%-- 					<input id="button${league.id}"  type="submit" name="button" onclick="window.location.href='/leagues'" value="enter"/> --%>
<%-- 		<c:if test="${league.racesCompleted<10}">   --%>
<!-- 					<h2>Moto 3 </h2>					 -->
<%-- 					</c:if> --%>
<%-- 					<c:if test="${league.racesCompleted>=10 && league.racesCompleted<15}">   --%>
<!-- 					<h2>Moto 2 </h2>				 -->
<%-- 					</c:if> --%>
<%-- 					<c:if test="${league.racesCompleted>=15}">   --%>
<!-- 					<h2>Moto GP </h2>					 -->
<%-- 					</c:if>   --%>
	<spring:url value="/granPremios" var="leagueUrl">
	</spring:url> <a href="${fn:escapeXml(leagueUrl)}">Gran Premios</a>
				<h4>Races Completed(MOTO3) : <c:out value="${rcc3}" /></h4>
				<h4>Races Completed(MOTO2) : <c:out value="${rcc2}" /></h4>
				<h4>Races Completed(MOTOgp) : <c:out value="${rccgp}" /></h4>
</petclinic:layout>