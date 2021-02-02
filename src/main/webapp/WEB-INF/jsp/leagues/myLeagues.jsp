<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ page import="org.springframework.samples.petclinic.model.Category" %>
<head>
<link rel="stylesheet" href="/resources/styles/league.css">
 	
</head>
<petclinic:layout pageName="leagues">
	<c:if test="${noTengoLigas==true}"><h2>Your dont participate in any leagues, try to join or create one!</h2></c:if>
	<c:if test="${noTengoLigas==false}"><h2>Your Leagues</h2></c:if>
	<c:if test="${yaTienesEquipo}"><h2>You already have a team in this League :( ! </h2></c:if> 
	<c:if test="${yaTieneMaxLigas}"><h2>This league already have 5 teams !/h2></c:if>
	<c:if test="${yaTieneMaxLigas}"><h2>You have no Leagues!</h2></c:if>
	<c:if test="${yaTieneMaxTeams}"><h2>This league already have 5 teams! :(</h2></c:if>
	<c:if test="${noTengoLigas==false}">
			<div id="divLeagueTable">
		
			<c:forEach items="${misLigas}" var="league">
					
				<tr>
					<c:if test="${leagueYaEquipoId == league.id}"> 
					
<%-- 					<td style="text-align:center;background-color:#73bf41;color:white;"><c:out value="${league.name} " /></td> --%>
<%-- 					<td style="text-align:center;background-color:#73bf41;color:white;"	><c:out value="${league.leagueCode} " /></td> --%>
<%-- 					<td style="text-align:center;background-color:#73bf41;color:white;"><c:out value="${league.leagueDate}" /></td> --%>
					<div class="columns">
						<ul class="price">
							<li class="header"><c:out value="${league.name} " /></li>
					<li class="grey" style="background-color:#73bf41;color:white;"><c:out value="${league.leagueCode} " /></li>
							<li><c:out value="${league.leagueDate}" /></li>
							<li class="grey"> <spring:url value="/leagues/{leagueId}/teams" var="leagueUrl">
							<spring:param name="leagueId" value="${league.id}" />
						</spring:url> <a class="button" href="${fn:escapeXml(leagueUrl)}">Teams</a> </li>
							
						</ul>
					</div>
<%-- 					<c:if test="${league.activeCategory==Category.MOTOGP}">   --%>
<!-- 					<td style="text-align:center;background-color:#73bf41;color:white;">Moto GP </td>					 -->
<%-- 					</c:if> --%>
					
<%-- 					<c:if test="${league.activeCategory==Category.MOTO2}">   --%>
<!-- 					<td style="text-align:center;background-color:#73bf41;color:white;">Moto 2 </td>					 -->
<%-- 					</c:if> --%>
					
<%-- 					<c:if test="${league.activeCategory==Category.MOTO3}">   --%>
<!-- 					<td style="text-align:center;background-color:#73bf41;color:white;">Moto 3 </td>					 -->
<%-- 					</c:if>  --%>
					
					
<!-- 					<td style="text-align:center;background-color:#73bf41;color:white;"> -->
<%-- 					<spring:url value="/leagues/{leagueId}/teams" var="leagueUrl"> --%>
<%-- 							<spring:param name="leagueId" value="${league.id}" /> --%>
<%-- 						</spring:url> <a href="${fn:escapeXml(leagueUrl)}" style="text-align:center;background-color:#73bf41;color:white;">Teams</a> --%>
<!-- 					</td> -->
					
					</c:if>
					
		
					
					
					<c:if test="${leagueYaEquipoId != league.id}"> 
					
<%-- 					<td style="text-align:center;"><c:out value="${league.name} " /></td> --%>
<%-- 					<td style="text-align:center;"	><c:out value="${league.leagueCode} " /></td> --%>
<%-- 					<td style="text-align:center;"><c:out value="${league.leagueDate}" /></td> --%>
					
<%-- 					<c:if test="${league.activeCategory==Category.MOTOGP}">   --%>
<!-- 					<td style="text-align:center;">Moto GP </td>					 -->
<%-- 					</c:if> --%>
					
<%-- 					<c:if test="${league.activeCategory==Category.MOTO2}">   --%>
<!-- 					<td style="text-align:center;">Moto 2 </td>					 -->
<%-- 					</c:if> --%>
					
<%-- 					<c:if test="${league.activeCategory==Category.MOTO3}">   --%>
<!-- 					<td style="text-align:center;">Moto 3 </td>					 -->
<%-- 					</c:if>  --%>
					
					
<!-- 					<td style="text-align:center;"> -->
<%-- 					<spring:url value="/leagues/{leagueId}/teams" var="leagueUrl"> --%>
<%-- 							<spring:param name="leagueId" value="${league.id}" /> --%>
<%-- 						</spring:url> <a href="${fn:escapeXml(leagueUrl)}" style="text-align:center;">Teams</a> --%>
<!-- 					</td> -->
					<div class="columns">
						<ul class="price">
							<li class="header"><c:out value="${league.name} " /></li>
					<li class="grey"><c:out value="${league.leagueCode} " /></li>
							<li><c:out value="${league.leagueDate}" /></li>
							<li class="grey"> <spring:url value="/leagues/{leagueId}/teams" var="leagueUrl">
							<spring:param name="leagueId" value="${league.id}" />
						</spring:url> <a class="button" href="${fn:escapeXml(leagueUrl)}">Teams</a> </li>
							
						</ul>
					</div>
					</c:if>
					
					
					
				</tr>
			</c:forEach>
			</div>
	</c:if>
	</br>
	<spring:url value="/leagues/new" var="messageUrl">
	</spring:url> <a href="${fn:escapeXml(messageUrl)}">New league</a>
	<spring:url value="/leagues/join" var="messageUrl">
	</br>
	</spring:url> <a href="${fn:escapeXml(messageUrl)}">Join league</a>
</petclinic:layout>