<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="pilot">
	
	
    <h2>
        <c:if test="${pilot['new']}">New </c:if> Pilot
    </h2>
    <form:form modelAttribute="pilot" class="form-horizontal" id="add-pilot-form" action="/pilots/save">
        <div class="form-group has-feedback">
        	<input type="hidden" name="id" value="${pilot.id}"/>
            <petclinic:inputField label="Name" name="name"/>
            <petclinic:inputField label="Last Name" name="lastName"/>
            <petclinic:inputField label="Dorsal" name="dorsal"/>
            <petclinic:inputField label="Category" name="category"/>
            <petclinic:inputField label="Nationality" name="nationality"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${pilot['new']}">
                        <button class="btn btn-default" type="submit">Add Pilot</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Pilot</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>
