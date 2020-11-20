d<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="lineups">

	<h2>Lineup Information</h2>


	<table class="table table-striped">
		<tr>
			<th>Name</th>
			<th>Points</th>
			<th>Money</th>
			<th>User</th>
			<th>Actions</th>
		</tr>
		<tr>
			<td><c:out value="${team.name} " /></td>
			<td><c:out value="${team.points}" /></td>
			<td><c:out value="${team.money}" /></td>
			<td><c:out value="${team.user.username}" /></td>
		</tr>
	</table>

	<table class="table table-striped">
		<tr>
			<th>Name</th>
			<th>Last Name</th>
			<th>Category</th>
			<th>Dorsal</th>
			<th>Nacionality</th>
		</tr>
		<c:forEach items="${misFichajes}" var="piloto">
		<tr>
			<td><c:out value="${piloto.name}" /></td>
			<td><c:out value="${piloto.lastName}" /></td>
			<td><c:out value="${piloto.category}" /></td>
			<td><c:out value="${piloto.dorsal}" /></td>
			<td><c:out value="${piloto.nationality}" /></td>
			<td><spring:url value="/pilotos/delete/{pilotoId}"
					var="pilotoUrl">
					<spring:param name="pilotoId" value="${piloto.id}" />
				</spring:url> <a href="${fn:escapeXml(pilotoUrl)}">Poner a la venta</a></td>
		</tr>
		</c:forEach>
	</table>



</petclinic:layout>
