<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<head>
<link rel="stylesheet" href="/resources/styles/league.css">
 	
</head>
<petclinic:layout pageName="leagues">

	<h2>Leagues</h2>
	
	<div id="divLeagueTable">
<!-- 	<table> -->
<!-- 		<thead> -->
<!-- 			<tr> -->
<!-- 				<th>Name</th> -->
<%-- 			<c:if test="${admin==true}"><th>Code</th></c:if>	 --%>
<!-- 				<th>Date</th> -->
<!-- 				<th>Teams</th> -->
<!-- 			</tr> -->
<!-- 		</thead> -->
<!-- 		<tbody>	 -->
			<c:forEach items="${ligas}" var="league">

					<div class="columns">
						<ul class="price">
							<li class="header"><c:out value="${league.name} " /></li>
							<c:if test="${admin==true}"><li class="grey"><c:out value="${league.leagueCode} " /></li></c:if>
							<li><c:out value="${league.leagueDate}" /></li>
							<li class="grey"> <spring:url value="/leagues/{leagueId}/teams" var="leagueUrl">
							<spring:param name="leagueId" value="${league.id}" />
						</spring:url> <a class="button" href="${fn:escapeXml(leagueUrl)}">Teams</a> </li>
							
						</ul>
					</div>

<!-- 					<tr>	 onclick="window.location='/leagues/${league.id}/details'"  -->
<%-- 					<td><c:out value="${league.name} " /></td> --%>
<%-- 					<c:if test="${admin==true}"><td><c:out value="${league.leagueCode} " /></td></c:if> --%>
<%-- 					<td><c:out value="${league.leagueDate}" /></td> --%>
<!-- 					<td> -->
<%-- 					<spring:url value="/leagues/{leagueId}/teams" var="leagueUrl"> --%>
<%-- 							<spring:param name="leagueId" value="${league.id}" /> --%>
<%-- 						</spring:url> <a href="${fn:escapeXml(leagueUrl)}">Teams</a> --%>
<!-- 					</td> -->
					
<!-- 				</tr> -->
			</c:forEach>
<!-- 		</tbody> -->
<!-- 	</table> -->
	</div>
	
				<div>
				<h4>Current Category : <c:out value="${categoriaActual}" /></h4>
				<h4>Races Completed : <c:out value="${carrerasCompletadas}" /></h4>
				
  </div>
    

</petclinic:layout>