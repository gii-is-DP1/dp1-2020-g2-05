<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<head>
<link rel="stylesheet" href="/resources/styles/league.css">
 	
</head>
<petclinic:layout pageName="leagues">

	<h2>Leagues</h2>
	<c:if test="${temporalMessage!=null}"><h3><c:out value="${temporalMessage}"></c:out></h3></c:if>
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
							<sec:authorize access="hasAuthority('admin')">
					<li class="grey"><c:out value="${league.leagueCode} " /></li></sec:authorize>
							<li><c:out value="${league.leagueDate}" /></li>
							<li class="grey"> <spring:url value="/leagues/{leagueId}/teams" var="leagueUrl">
							<spring:param name="leagueId" value="${league.id}" />
						</spring:url> <a class="button" href="${fn:escapeXml(leagueUrl)}">Teams</a> </li>
							
						</ul>
					</div>
			</c:forEach>

	</div>
	
				<div>
				<h4>Current Category : <c:out value="${categoriaActual}" /></h4>
				<h4>Races Completed : <c:out value="${carrerasCompletadas}" /></h4>
				
  </div>
    

</petclinic:layout>