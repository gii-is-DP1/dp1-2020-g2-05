<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="messages">
	

	<c:if test="${resultados.size() == 0}">
	<h2>¡No hay mensajes para tí! :<(</h2>
	</c:if>
	
	<c:if test="${resultados.size() != 0}">
    <h2>Messages</h2>
	<table id="messagesTable" class="table table-striped">
		<thead>
			<tr>
				<th>Asunto</th>
				<th>Remitente</th>
				<th>Destinatario</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${resultados}" var="message">
				<c:if test="${message.visto == 0}">
				
				<tr>
					
					<td><c:out value="${message.asunto}" /></td>
					<td><c:out value="${message.usernamesend}" /></td>
					<td><c:out value="${message.usernamereceive}" /></td>
					<td>
						<spring:url value="/messages/view/{messageId}" var="messageUrl">
							<spring:param name="messageId" value="${message.id}" />
						</spring:url> <a href="${fn:escapeXml(messageUrl)}">Abrir</a>
						<spring:url value="/messages/delete/{messageId}" var="messageUrl">
							<spring:param name="messageId" value="${message.id}" />
						</spring:url> <a href="${fn:escapeXml(messageUrl)}">Delete</a>
					</td>
				</tr>
				</c:if>
				<c:if test="${message.visto == 1}">
				
				<tr id="msgleido">
					
					<td><c:out value="${message.asunto}" /></td>
					<td><c:out value="${message.usernamesend}" /></td>
					<td><c:out value="${message.usernamereceive}" /></td>
					<td>
						<spring:url value="/messages/view/{messageId}" var="messageUrl">
							<spring:param name="messageId" value="${message.id}" />
						</spring:url> <a href="${fn:escapeXml(messageUrl)}">Abrir</a>
						<spring:url value="/messages/delete/{messageId}" var="messageUrl">
							<spring:param name="messageId" value="${message.id}" />
						</spring:url> <a href="${fn:escapeXml(messageUrl)}">Delete</a>
					</td>
				</tr>
				</c:if>
				
			</c:forEach>
		</tbody>
	</table>
	</c:if>
	<spring:url value="/messages/new/" var="messageUrl">
			<spring:param name="messageId" value="${message.id}" />
	</spring:url> <a href="${fn:escapeXml(messageUrl)}">Nuevo mensaje</a>
</petclinic:layout>