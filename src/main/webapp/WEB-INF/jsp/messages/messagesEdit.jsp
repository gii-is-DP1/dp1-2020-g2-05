<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page import="org.springframework.samples.petclinic.model.User"%>

<petclinic:layout pageName="messagese">
	
	
    <h2>
        <c:if test="${messagee['new']}">New </c:if> Mensaje
    </h2>
    <form:form modelAttribute="messagee" class="form-horizontal" id="add-message-form">
        <div class="form-group has-feedback">
        
        	<input type="hidden" name="visto" value="0"/>
        	<input type="hidden"  name="usernamesend" value="${usersend}"/>	
            <petclinic:inputField label="Asunto" name="asunto"/>
            <petclinic:inputField label="Cuerpo" name="cuerpo"/>
            <petclinic:inputField label="Destinatario" name="usernamereceive"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${messagee['new']}">
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
