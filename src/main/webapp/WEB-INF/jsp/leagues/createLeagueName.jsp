<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page import="org.springframework.samples.petclinic.model.Category" %>


<petclinic:layout pageName="leagues">
<h2>Enter here the League name</h2>
<c:forEach items="${message}" var="mess">
<h2><c:out value="${mess.getDefaultMessage()}"/></h2>
</c:forEach>
     <form:form modelAttribute="league" class="form-horizontal" id="add-pilot-form" actions="/leagues/new"  > 
        <div class="form-group has-feedback">
		        	<input type="hidden" name="id" value="${league.id}"/>
		        	<input type="hidden" name="leagueCode" value="${league.leagueCode}"/>
		        	<input type="hidden" name="leagueDate" value="${league.leagueDate}"/>
<%-- 		        	<input type="hidden" name="racesCompleted" value="${league.racesCompleted}"/> --%>
<%-- 		        	<input type="hidden" name="activeCategory" value="${league.activeCategory}"/> --%>
		        	
		
            <petclinic:inputField label="League Name" name="Name"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
               
                        <button class="btn btn-default" type="submit">Continue</button>
            </div>
        </div>
     </form:form> 
<%-- 		<form  actions="/leagues/join/{leagueCode}"> --%>
<!-- 		<input id="leagueCode" name="leagueCode" type="text"/> -->
<!-- 		                        <button class="btn btn-default" type="submit">Join League</button> -->
		
<%-- 		</form> --%>
</petclinic:layout>