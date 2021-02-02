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
					<c:if test="${leagueYaEquipoId == league.id}"> 
				
					<div class="columns">
						<ul class="price">
							<li class="header"><c:out value="${league.name} " /></li>
					<li class="grey" style="background-color:#73bf41;color:white;"><c:out value="${league.leagueCode} " /></li>
							<li><c:out value="${league.leagueDate}" /></li>
							<li class="grey"> <spring:url value="/leagues/{leagueId}/teams" var="leagueUrl">
							<spring:param name="leagueId" value="${league.id}" />
						</spring:url> <a class="button" href="${fn:escapeXml(leagueUrl)}">Teams</a> 
						
						</li>
					<li class="grey"> 
							<c:forEach items="${league.team}" var="teams">
							
							<c:if test="${teams.user.username==nombreUsuarioDeLaSesion}">
							
							<spring:url value="/leagues/{leagueId}/teams" var="teamUrl">
							<spring:param name="teams" value="${teams.id}" />
							</spring:url>  
							
							<a class="button" href="${fn:escapeXml(teamUrl)}"><c:out value="${teams.name} " />
							</a>
							</c:if>
							</c:forEach>
						
						</li>
							
						</ul>
					</div>
					
					</c:if>
					
		
					
					
					<c:if test="${leagueYaEquipoId != league.id}"> 
					<div class="columns">
						<ul class="price">
							<li class="header"><c:out value="${league.name} " /></li>
					<li class="grey"><c:out value="${league.leagueCode} " /></li>
							<li><c:out value="${league.leagueDate}" /></li>
							<li class="grey"> <spring:url value="/leagues/{leagueId}/teams" var="leagueUrl">
							<spring:param name="leagueId" value="${league.id}" />
						</spring:url> <a class="button" href="${fn:escapeXml(leagueUrl)}">Teams</a>
							<c:forEach items="${league.team}" var="teams">
							
							<c:if test="${teams.user.username==nombreUsuarioDeLaSesion}">
							
							<spring:url value="/leagues/{leagueId}/teams/{teamId}/details" var="teamUrl">
							<spring:param name="teamId" value="${teams.id}" />
							<spring:param name="leagueId" value="${league.id}" />
							</spring:url>  
							<a class="button" href="${fn:escapeXml(teamUrl)}"><c:out value="${teams.name} " />
							</a>
							</c:if>
							</c:forEach>
						 </li>
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