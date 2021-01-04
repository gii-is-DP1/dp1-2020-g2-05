<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="result">

	<h2>Gran Premio asociado: <spring:eval expression="gp"/></h2>
	
	<h2>Results</h2>

	<table class="table table-striped">
		<tr>
			<th>Position</th>
			<th>Pole</th>
			<th>Lap</th>
			<th>Pilot</th>
		</tr>

		<c:forEach items="${resultados}" var="res">
			<tr>
				<td><c:out value="${res.position}" /></td>
				<td><c:out value="${res.pole}" /></td>
				<td><c:out value="${res.lap}" /></td>
				<td><c:out value="${res.pilot}" /></td>
			</tr>
		</c:forEach>
	</table>

	<h2>Records</h2>

	<table class="table table-striped">
		<tr>
			<th>Pole Lap</th>
			<th>Fastest Lap</th>
			<th>Circuit Record</th>
			<th>Best Lap</th>
		</tr>

		<tr>
			<td><c:out value="${record.vueltaDePole}" /></td>
			<td><c:out value="${record.vueltaRapida}" /></td>
			<td><c:out value="${record.recordDelCircuito}" /></td>
			<td><c:out value="${record.mejorVuelta}" /></td>
		</tr>
	</table>

</petclinic:layout>
