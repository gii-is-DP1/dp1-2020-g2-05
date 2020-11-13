<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="offers">
	<h2>Offers</h2>

	<table id="offersTable" class="table table-striped">
		<thead>
			<tr>
				<th>Price</th>
				<th>Status</th>
				<th>Pilot</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${offers}" var="offer">
				<tr>
					<td><c:out value="${offer.price} " /></td>
					<td><c:out value="${offer.status}" /></td>
					<td><c:out value="${offer.pilot.name}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>