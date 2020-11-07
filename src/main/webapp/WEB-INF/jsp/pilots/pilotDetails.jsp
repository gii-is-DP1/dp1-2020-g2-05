<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="pilots">

	<h2>Pilot Information</h2>


	<table class="table table-striped">
		<tr>
			<th>Name</th>
			<th>Lastname</th>
			<th>Dorsal</th>
			<th>Nationality</th>
			<th>Category</th>
			<th>Results</th>
			<th>Actions</th>
		</tr>
		<tr>
			<td><c:out value="${pilot.name} " /></td>
			<td><c:out value="${pilot.lastName}" /></td>
			<td><c:out value="${pilot.dorsal}" /></td>
			<td><c:out value="${pilot.nationality}" /></td>
			<td><c:out value="${pilot.category}" /></td>
			<td>${pilot.results}</td>
		</tr>
	</table>



</petclinic:layout>
