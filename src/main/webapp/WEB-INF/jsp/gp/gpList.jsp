<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="granPremios">

	<h2>GPs calendar</h2>
	
	<table class="table table-striped">
		<tr>
			<th>Site</th>
			<th>Circuit</th>
			<th>Date</th>
			<th>Results</th>
		</tr>
		
		<c:forEach items="${listaGP}" var="res">
		<tr>
			 <td><c:out value="${res.site}"  /> </td>
			 <td><c:out value="${res.circuit}"  /> </td>
			 <td><c:out value="${res.date0}"  /> </td>
			 <td>
			 <spring:url value="/granPremios" var="leagueUrl">
			</spring:url> <a href="${fn:escapeXml(leagueUrl)}"> MotoGP</a>
			<spring:url value="/granPremios" var="leagueUrl">
			</spring:url> <a href="${fn:escapeXml(leagueUrl)}"> Moto2</a>
			<spring:url value="/granPremios" var="leagueUrl">
			</spring:url> <a href="${fn:escapeXml(leagueUrl)}"> Moto3</a>
			</td>
			
		</tr>
			 
		</c:forEach>
	</table>
	
	

</petclinic:layout>
