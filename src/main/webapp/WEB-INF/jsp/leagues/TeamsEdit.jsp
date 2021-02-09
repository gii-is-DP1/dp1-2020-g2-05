<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="teamDetails" userTeam="${team.id}" userTeamName="${team.name}" userLeague="${team.league.id}" userLeagueName="${team.league.name}" userMoney="${team.money}">


     	<c:if test="${Editar!=true}">
	
    <h2>
        <c:if test="${team['new']}">New </c:if> Team
    </h2>
    
    <c:if test="${user ==true}">
     <form:form modelAttribute="team" class="form-horizontal" id="add-pilot-form" actions="/leagues/{leagueId}/teams/new"  > 
        <div class="form-group has-feedback">
        	<input type="hidden" name="id" value="${team.id}"/>
            <input type="hidden"  name="league" value="${leagueId}"/>	
            <petclinic:inputField label="Name" name="name"/>
    		<input type="hidden"  name="points" value="0"/>
            <input type="hidden"  name="money" value="2000"/>
            <input type="hidden"  name="league" value="${team.league}"/>	
             <input type="hidden"  name="user" value="${team.user.username}"/>	 
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
               
                        <button class="btn btn-default" type="submit">Add Team</button>
              
               
            </div>
        </div>
     </form:form> 
     </c:if>
     
     <c:if test="${admin ==true}">
     <form:form modelAttribute="team" class="form-horizontal" id="add-pilot-form" actions="/leagues/{leagueId}/teams/new"  > 
        <div class="form-group has-feedback">
        	<input type="hidden" name="id" value="${team.id}"/>
        	<input type="hidden"  name="league" value="${leagueId}"/>	
            <petclinic:inputField label="Name" name="name"/>
           <petclinic:inputField label="points" name="points"/>
            <petclinic:inputField label="money" name="money"/>
             <input type="hidden"  name="user" value="${team.user.username}"/>	 
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
          
     	         <button class="btn btn-default" type="submit">Add Team</button>
              
            </div>
        </div>
     </form:form>      	
     </c:if>
     </c:if>
     	<c:if test="${Editar==true}">
     	 <c:if test="${admin==true}">
     	<form:form modelAttribute="team" class="form-horizontal" id="add-pilot-form" actions="/leagues/{leagueId}/teams/{teamId}/edit"  > 
        <div class="form-group has-feedback">
        	<input type="hidden" name="id" value="${team.id}"/>
        	<input type="hidden"  name="league" value="${leagueId}"/>	
            <petclinic:inputField label="Name" name="name"/>
           <petclinic:inputField label="points" name="points"/>
            <petclinic:inputField label="money" name="money"/>
             <input type="hidden"  name="user" value="${team.user.username}"/>	 
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
          
     	        <button class="btn btn-default" type="submit">Update Teams</button>
              
            </div>
        </div>
     </form:form>      	
     	</c:if>
     <c:if test="${usuario==true}">
     	<form:form modelAttribute="team" class="form-horizontal" id="add-pilot-form" actions="/leagues/{leagueId}/teams/{teamId}/edit"  > 
        <div class="form-group has-feedback">
        	<input type="hidden" name="id" value="${team.id}"/>
            <input type="hidden"  name="league" value="${leagueId}"/>	
            <petclinic:inputField label="Name" name="name"/>
            <input type="hidden"  name="points" value="${team.points}"/>	
             <input type="hidden"  name="money" value="${team.money}"/>	
             <input type="hidden"  name="user" value="${team.user.username}"/>	 
              
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
          
     	        <button class="btn btn-default" type="submit">Update Teams</button>
              
            </div>
        </div>
     </form:form>      	

     </c:if>
     
     </c:if>
</petclinic:layout>