<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="pageName" required="true" %>
<%@ attribute name="customScript" required="false" fragment="true"%>
<%@ attribute name="userTeam" required="false"%>
<%@ attribute name="userTeamName" required="false"%>
<%@ attribute name="userLeague" required="false" %>
<%@ attribute name="userLeagueName" required="false" %>
<%@ attribute name="userMoney" required="false" %>

<!doctype html>
<html>
<petclinic:htmlHeader/>

<body>
<petclinic:bodyHeader menuName="${pageName}"/>

<c:if test="${not empty userTeam}">
	<petclinic:leagueMenu userTeam="${userTeam}" userTeamName="${userTeamName}" userLeague="${userLeague}" userLeagueName="${userLeagueName}" userMoney="${userMoney}"/>
</c:if>

<div class="container-fluid">
    <div class="container xd-container">
	<c:if test="${not empty message}" >
	<div class="alert alert-${not empty messageType ? messageType : 'info'}" role="alert">
  		<c:out value="${message}"></c:out>
   		<button type="button" class="close" data-dismiss="alert" aria-label="Close">
    		<span aria-hidden="true">&times;</span>
  		</button> 
	</div>
	</c:if>

        <jsp:doBody/>

<%--         <petclinic:pivotal/> --%>
    </div>
</div>
<petclinic:footer/>
<jsp:invoke fragment="customScript" />

</body>

</html>
