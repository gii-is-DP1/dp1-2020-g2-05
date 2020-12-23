<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="friends">
	

	
    <h2>followings</h2>
	<table id="messagesTable" class="table table-striped">
		<thead>
			<tr>
				<th>Usuario</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${resultados}" var="friend">
				
				
				<tr>
					
					<td><c:out value="${friend.username}" /></td>
					<td>
						<spring:url value="/messages/new/{username}" var="messageUrl">
							<spring:param name="username" value="${friend.username}" />
						</spring:url> <a href="${fn:escapeXml(messageUrl)}">Enviar mensaje</a>
						<spring:url value="/friends/remove/{username}" var="messageUrl">
							<spring:param name="username" value="${friend.username}" />
						</spring:url> <a href="${fn:escapeXml(messageUrl)}">Dejar de seguir</a>
					</td>
				</tr>
				
				
			</c:forEach>
		</tbody>
	</table>
		<form:form modelAttribute="user" class="form-horizontal" id="add-friend-form">
        <div class="form-group has-feedback">
           <form:hidden path="email"/>
           <form:hidden path="password"/>
            <petclinic:inputField label="Username" name="username"/>
             <button class="btn btn-default" type="submit">Follow</button>
           
        </div>
    
    </form:form>
<%-- 	<spring:url value="/messages/new/" var="messageUrl"> --%>
<%-- 			<spring:param name="messageId" value="${message.id}" /> --%>
<%-- 	</spring:url> <a href="${fn:escapeXml(messageUrl)}">Nuevo mensaje</a> --%>
</petclinic:layout>