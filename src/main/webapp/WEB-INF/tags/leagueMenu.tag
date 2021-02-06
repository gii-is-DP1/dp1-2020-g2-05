<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petcinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ attribute name="userTeam" required="false"%>
<%@ attribute name="userTeamName" required="false"%>
<%@ attribute name="userLeague" required="false" %>
<%@ attribute name="userLeagueName" required="false" %>
<%@ attribute name="userMoney" required="false" %>

<nav class="navbar navbar-default" style="background-color:#e7e7e7" role="navigation">
	<div class="container">
		<div class="navbar-collapse collapse" id="league-navbar">
			<ul class="nav" >
				<li class="navbar-inverse navbar-text">
					<span>League: ${userLeagueName}</span>
				</li>
				<li class="navbar-text">
					<span>Team: ${userTeamName}</span>
				</li>
				<li class="navbar-text">
					<span>Money: ${userMoney}</span>
				</li>
			
				<li class="navbar-right">
				<spring:url value="/leagues/{leagueId}/market" var="marketUrl">
								<spring:param name="leagueId" value="${userLeague}" />
							</spring:url> <a href="${fn:escapeXml(marketUrl)}">
								<span aria-hidden="true">Go to Market</span>
							</a>
				</li>
			</ul>
		</div>
	</div>
</nav>