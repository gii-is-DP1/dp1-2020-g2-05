<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="pilotsPaged">
	<h2>Pilots</h2>

	<form:form method="get" modelAttribute="pageSize" class="form-horizontal" id="setPageSize" actions="/pilotsPaged">
		<div class="form-group has-feedback">
			<button class="btn btn-default" type="submit">Set page size</button>
			<input name="pageSize" /> <input type="hidden" name="pageNumber" value="${pageNumber}" />
		</div>
	</form:form>

	<div id="pagination">

		<c:url value="pilotsPaged" var="prev">
			<c:param name="pageNumber" value="${pageNumber}" />
			<c:param name="pageSize" value="${pageSize}" />
		</c:url>
		<c:if test="${pageNumber > 0}">
			<a href="<c:out value="${prev}" />" class="pn prev">Prev</a>

		</c:if>

		<c:forEach begin="1" end="${maxPageNumber}" step="1" varStatus="i">
			<c:choose>
				<c:when test="${pageNumber == i.index-1}">
					<span>${i.index}</span>
				</c:when>
				<c:otherwise>
					<c:url value="pilotsPaged" var="url">
						<c:param name="pageNumber" value="${i.index}" />
						<c:param name="pageSize" value="${pageSize}" />
					</c:url>
					<a href='<c:out value="${url}" />'>${i.index}</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>

		<c:url value="pilotsPaged" var="next">
			<c:param name="pageNumber" value="${pageNumber + 2}" />
			<c:param name="pageSize" value="${pageSize}" />

		</c:url>
		<c:if test="${pageNumber + 2 <= maxPageNumber}">
			<a href='<c:out value="${next}" />' class="pn next">Next</a>
		</c:if>

	</div>

	<table id="pilotsTable" class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
				<th>Lastname</th>
				<th>Dorsal</th>
				<th>Nationality</th>
				<th>Category</th>
				<th>Actions</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${resultadosPaginados}" var="pilot">
				<tr>
					<td><c:out value="${pilot.name} " /></td>
					<td><c:out value="${pilot.lastName}" /></td>
					<td><c:out value="${pilot.dorsal}" /></td>
					<td><c:out value="${pilot.nationality}" /></td>
					<td><c:out value="${pilot.category}" /></td>
					<td><spring:url value="/pilots/edit/{pilotId}" var="pilotUrl">
							<spring:param name="pilotId" value="${pilot.id}" />
						</spring:url> <a href="${fn:escapeXml(pilotUrl)}">Edit</a> <spring:url
							value="/pilots/delete/{pilotId}" var="pilotUrl">
							<spring:param name="pilotId" value="${pilot.id}" />
						</spring:url> <a href="${fn:escapeXml(pilotUrl)}">Delete</a> <spring:url
							value="/pilots/{pilotId}" var="pilotUrl">
							<spring:param name="pilotId" value="${pilot.id}" />
						</spring:url> <a href="${fn:escapeXml(pilotUrl)}">Details</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>