<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="Team">
	
	 <h2>
        <c:if test="${messageNewLiga}">Before creating or joining a league, you have to create a team which belongs to this league! </c:if> 
    </h2>
	
    <h2>
        <c:if test="${teams['new']}">New </c:if> Team
    </h2>
     <form:form modelAttribute="teams" class="form-horizontal" id="add-pilot-form" actions="/leagues/{leagueId}/teams/new/save"  > 
        <div class="form-group has-feedback">
        	<input type="hidden" name="id" value="${teams.id}"/>
        <input type="hidden"  name="league" value="${leagueId}"/>	
            <petclinic:inputField label="Name" name="name"/>
            <petclinic:inputField label="points" name="points"/>
            <petclinic:inputField label="money" name="money"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${teams['new']}">
                        <button class="btn btn-default" type="submit">Add Teams</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Teams</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
     </form:form> 
</petclinic:layout>