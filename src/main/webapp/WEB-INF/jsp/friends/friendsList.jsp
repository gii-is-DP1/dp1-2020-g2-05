<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="friends">
	

	
    <h2>Messages</h2>
	<table id="messagesTable" class="table table-striped">
		<thead>
			<tr>
				<th>nombreamigo</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${resultados}" var="friend">
				
				
				<tr>
					
					<td><c:out value="${friend.username}" /></td>
					<td>
						<spring:url value="/messages/view/{messageId}" var="messageUrl">
							<spring:param name="messageId" value="${message.id}" />
						</spring:url> <a href="${fn:escapeXml(messageUrl)}">Abrir</a>
						<spring:url value="/messages/delete/{messageId}" var="messageUrl">
							<spring:param name="messageId" value="${message.id}" />
						</spring:url> <a href="${fn:escapeXml(messageUrl)}">Delete</a>
					</td>
				</tr>
				
				
			</c:forEach>
		</tbody>
	</table>
	<spring:url value="/messages/new/" var="messageUrl">
			<spring:param name="messageId" value="${message.id}" />
	</spring:url> <a href="${fn:escapeXml(messageUrl)}">Nuevo mensaje</a>
</petclinic:layout>