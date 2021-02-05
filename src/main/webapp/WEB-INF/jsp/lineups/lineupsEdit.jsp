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
    
    <c:choose>
    	<c:when test="${lineup['new']}">
    		<h2>New Lineup</h2>
    		
    		<form:form modelAttribute="lineup" class="form-horizontal" id="add-lineup-form" action="/newLineup">
		        <div class="form-group has-feedback">
		        	<input type="hidden" name="id" value="${lineup.id}"/>
		            <input type="hidden" name="category" value="${lineup.category}"/>
		            <input type="hidden" name="league" value="${leagueId}"/>
		            <input type="hidden" name="team" value="${teamId}"/>
		            <input type="hidden" name="gp.id" value="${lineup.gp.id}"/>
		            <h4>Date: ${lineup.gp.date0} </h4><h4> Place: ${lineup.gp.site} </h4><h4>Circuit: ${lineup.gp.circuit}</h4>
		            
					<div class="control-group">
						<petclinic:selectField name="recruit1" label="Recruit1" names="${recruitsSelection}" size="5" />
						<petclinic:selectField name="recruit2" label="Recruit2" names="${recruitsSelection}" size="5" />
					</div>
				</div>
				
		        <div class="form-group">
		            <div class="col-sm-offset-2 col-sm-10">
		            	<button class="btn btn-default" type="submit">Update Lineup</button>
		            </div>
		        </div>
		    </form:form>
    	</c:when> 
    	   
    	<c:otherwise>
    	    <h2>Lineup</h2>
    	    
    	    <form:form modelAttribute="lineup" class="form-horizontal" id="add-lineup-form" action="/editLineup/${lineup.id}">
		        <div class="form-group has-feedback">
		        	<input type="hidden" name="id" value="${lineup.id}"/>
		            <input type="hidden" name="category" value="${lineup.category}"/>
		            <input type="hidden" name="league" value="${leagueId}"/>
		            <input type="hidden" name="team" value="${teamId}"/>
		            <input type="hidden" name="gp.id" value="${lineup.gp.id}"/>
		            <h4>Date: ${lineup.gp.date0} </h4><h4> Place: ${lineup.gp.site} </h4><h4>Circuit: ${lineup.gp.circuit}</h4>
		            
					<div class="control-group">
						<petclinic:selectField name="recruit1" label="Recruit1" names="${recruitsSelection}" size="5" />
						<petclinic:selectField name="recruit2" label="Recruit2" names="${recruitsSelection}" size="5" />
					</div>
				</div>
				
		        <div class="form-group">
		            <div class="col-sm-offset-2 col-sm-10">
		            	<button class="btn btn-default" type="submit">Update Lineup</button>
		            </div>
		        </div>
		    </form:form>
    	</c:otherwise>
	</c:choose>
	
	
<%--     <form:form modelAttribute="lineup" class="form-horizontal" id="add-lineup-form" action="${lineup['new']} ? "/newLineup" : "/editLineup/${lineupId}"> --%>
<!--         <div class="form-group has-feedback"> -->
<%--         	<input type="hidden" name="id" value="${lineup.id}"/> --%>
<%--             <input type="hidden" name="category" value="${lineup.category}"/> --%>
<%--             <input type="hidden" name="league" value="${leagueId}"/> --%>
<%--             <input type="hidden" name="team" value="${teamId}"/> --%>
<%--             <input type="hidden" name="gp.id" value="${lineup.gp.id}"/> --%>
<%--             <h4>Date: ${lineup.gp.date0} </h4><h4> Place: ${lineup.gp.site} </h4><h4>Circuit: ${lineup.gp.circuit}</h4> --%>
            
<!-- 			<div class="control-group"> -->
<%-- 				<petclinic:selectField name="recruit1" label="Recruit1" names="${recruitsSelection}" size="5" /> --%>
<%-- 				<petclinic:selectField name="recruit2" label="Recruit2" names="${recruitsSelection}" size="5" /> --%>
<!-- 			</div> -->
<!-- 		</div> -->
<!--         <div class="form-group"> -->
<!--             <div class="col-sm-offset-2 col-sm-10"> -->
<%--                 <c:choose> --%>
<%--                     <c:when test="${lineup['new']}"> --%>
<!--                         <button class="btn btn-default" type="submit">Add Lineup</button> -->
<%--                     </c:when> --%>
<%--                     <c:otherwise> --%>
<!--                         <button class="btn btn-default" type="submit">Update Lineup</button> -->
<%--                     </c:otherwise> --%>
<%--                 </c:choose> --%>
<!--             </div> -->
<!--         </div> -->
<%--     </form:form> --%>
</petclinic:layout>
