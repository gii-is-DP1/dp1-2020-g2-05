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

	<div style="display:inline-block;margin-left:3.5%;"><h2>Leagues</h2></div>
	
<!-- 				<div style="display:inline-block;"> -->
<%-- 				<h4>Current Category : <c:out value="${categoriaActual}" /></h4> --%>
<!-- 				</div> -->
				<div style="display:inline-block;margin-left:25%;">
				<h4>Races to <c:out value="${siguienteCategoria}"/> : <c:out value="${carrerasParaSiguienteCategoria-carrerasCompletadas}" /></h4>
  				</div>
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
	
    

</petclinic:layout>