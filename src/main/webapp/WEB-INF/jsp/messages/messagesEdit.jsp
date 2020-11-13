<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="messages">
	
	
    <h2>
        <c:if test="${message['new']}">New </c:if> Mensaje
    </h2>
    <form:form modelAttribute="message" class="form-horizontal" id="add-message-form" action="/messages/save">
        <div class="form-group has-feedback">
        	<input type="hidden" name="id" value="${message.id}"/>
            <petclinic:inputField label="Asunto" name="asunto"/>
            <petclinic:inputField label="Cuerpo" name="cuerpo"/>
            <petclinic:inputField label="Remitente" name="usersend"/>
            <petclinic:inputField label="Destinatario" name="userreceive"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${message['new']}">
                        <button class="btn btn-default" type="submit">Add message</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update message</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>
