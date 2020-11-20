<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="Recruits">


	<c:if test="${resultados.size() == 0}">
		<h2>¡No hay fichajes en este equipo o no existe el equipo!</h2>
	</c:if>

	<c:if test="${resultados.size() != 0}">
		<h2>Fichajes</h2>
		<table id="tablaDeFichajes" class="table table-striped">
			<thead>
				<tr>
					<th>NAME</th>
					<th>LASTNAME</th>
					<th>CATEGORY</th>
					<th>DORSAL</th>
					<th>NATIONALITY</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${resultados}" var="piloto">
					<tr>
						<td><c:out value="${piloto.name}" /></td>
						<td><c:out value="${piloto.lastName}" /></td>
						<td><c:out value="${piloto.category}" /></td>
						<td><c:out value="${piloto.dorsal}" /></td>
						<td><c:out value="${piloto.nationality}" /></td>
						<td><spring:url value="/pilotos/view/{pilotoId}"
								var="pilotoUrl">
								<spring:param name="pilotoId" value="${piloto.id}" />
							</spring:url> <a href="${fn:escapeXml(pilotoUrl)}">Abrir</a> <spring:url
								value="/pilotos/delete/{pilotoId}" var="pilotoUrl">
								<spring:param name="pilotoId" value="${piloto.id}" />
							</spring:url> <a href="${fn:escapeXml(pilotoUrl)}">Delete</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
	<spring:url value="/pilotos/new/" var="pilotoUrl">
		<spring:param name="pilotoId" value="${piloto.id}" />
	</spring:url>
	<a href="${fn:escapeXml(pilotoUrl)}">Nuevo mensaje</a>
</petclinic:layout>