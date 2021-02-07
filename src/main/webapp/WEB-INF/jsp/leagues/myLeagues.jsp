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
	
	
			<div id="divLeagueTable">
		
			<c:forEach items="${misLigas}" var="league">
				
					
		
					
					
					<div class="columns">
						<ul class="price">
							<li class="header"><c:out value="${league.name} " /></li>
					<li class="grey"><c:out value="${league.leagueCode} " /></li>
							<li><c:out value="${league.leagueDate}" /></li>
							<li class="grey"> <spring:url value="/leagues/{leagueId}/teams" var="leagueUrl">
							<spring:param name="leagueId" value="${league.id}" />
						</spring:url> <a class="button" href="${fn:escapeXml(leagueUrl)}">Teams</a>
							 </li>
						
							<c:forEach items="${league.team}" var="teams">
							
							<c:if test="${teams.user.username==nombreUsuarioDeLaSesion}">
							
							<spring:url value="/leagues/{leagueId}/teams/{teamId}/details" var="teamUrl">
							<spring:param name="teamId" value="${teams.id}" />
							<spring:param name="leagueId" value="${league.id}" />
							</spring:url>  
							<li><a class="button" href="${fn:escapeXml(teamUrl)}"><c:out value="${teams.name} " /></a>
							</li>
							
							
							</c:if>
							</c:forEach>
						</ul>
					</div>
					
					
			
			</c:forEach>
			</div>
			
	<div style="margin-left:45%;" class="buttonNewJoinLeague">
	<spring:url value="/leagues/new" var="messageUrl">
	</spring:url> <a class="textButton" href="${fn:escapeXml(messageUrl)}">New league</a>
	</div>

	<div class="buttonNewJoinLeague">
	
	<spring:url value="/leagues/join" var="messageUrl">
	</spring:url> <a class="textButton" href="${fn:escapeXml(messageUrl)}">Join league</a>
	</div>
		
</petclinic:layout>