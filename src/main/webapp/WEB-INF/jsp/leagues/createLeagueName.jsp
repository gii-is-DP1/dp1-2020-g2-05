<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="leagues">
<h2>Enter here the League name</h2>
<c:if test="${malNombre==true}"><h2>Name is not valid</h2></c:if>

     <form:form modelAttribute="league" class="form-horizontal" id="add-pilot-form" actions="/leagues/new"  > 
        <div class="form-group has-feedback">

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