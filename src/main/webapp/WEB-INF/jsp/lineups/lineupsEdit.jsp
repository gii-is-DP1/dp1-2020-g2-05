<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="lineup">
	
	
    <h2>
        <c:if test="${lineup['new']}">New </c:if> Lineup
    </h2>
    <form:form modelAttribute="lineup" class="form-horizontal" id="add-lineup-form"> <%-- action="save"> --%>
        <div class="form-group has-feedback">
        	<input type="hidden" name="id" value="${lineup.id}"/>
            <input type="hidden" name="category" value="${leagueCategory}"/>
            <input type="hidden" name="league" value="${leagueId}"/>
            <input type="hidden" name="team" value="${teamId}"/>
            <petclinic:inputField label="GP" name="gp.id"/>
            <petclinic:inputField label="Recruit1" name="recruit1.id"/>
            <petclinic:inputField label="Recruit2" name="recruit2.id"/>
            
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${lineup['new']}">
                        <button class="btn btn-default" type="submit">Add Lineup</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Lineup</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>
