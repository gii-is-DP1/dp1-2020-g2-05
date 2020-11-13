<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="messages">
	<h2>Messages</h2>

	<table id="messagesTable" class="table table-striped">
		<thead>
			<tr>
				<th>ID</th>
				<th>Asunto</th>
				<th>Cuerpo</th>
				<th>Remitente</th>
				<th>Destinatario</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${resultados}" var="message">
				<tr>
					<td><c:out value="${message.id} " /></td>
					<td><c:out value="${message.asunto}" /></td>
					<td><c:out value="${message.cuerpo}" /></td>
					<td><c:out value="${message.usernamesend}" /></td>
					<td><c:out value="${message.usernamereceive}" /></td>
					<td>
						<spring:url value="/messages/edit/{messageId}" var="messageUrl">
							<spring:param name="messageId" value="${message.id}" />
						</spring:url> <a href="${fn:escapeXml(messageUrl)}">Edit</a>
						<spring:url value="/messages/delete/{messageId}" var="messageUrl">
							<spring:param name="messageId" value="${message.id}" />
						</spring:url> <a href="${fn:escapeXml(messageUrl)}">Delete</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>