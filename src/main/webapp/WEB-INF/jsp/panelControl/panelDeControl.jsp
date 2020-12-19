<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page import="org.springframework.samples.petclinic.model.Category" %>


<petclinic:layout pageName="leagues">
<c:if test="${message!=null}"><c:out value="${message}"/></c:if>	
<c:if test="${messageMoto2NotFound!=null}"><c:out value="${messageMoto2NotFound}"/><br></c:if>	
<c:if test="${messageMotogpNotFound!=null}"><c:out value="${messageMotogpNotFound}"/><br></c:if>	
<c:if test="${messageMoto3NotFound!=null}"><c:out value="${messageMoto3NotFound}"/><br></c:if>	
<h2>This is the control panel</h2>
        <div class="form-group has-feedback">
		        	
		<spring:url value="/granPremios/new" var="leagueUrl">
		</spring:url> <a href="${fn:escapeXml(leagueUrl)}">new GP (Calendar)</a>
               
        </div>
	<div>
	<c:forEach items="${listaGP}" var="res">
			 			 <c:if test="${res.hasBeenRun==false}">
		
			 <h5>
			 <c:out value="${res.circuit}"  />  
			 <c:out value=" ${res.date0}"  /> 
			<spring:url value="/BD/carrerasBD/{date}/{code}" var="leagueUrl">
			<spring:param name="date" value="${res.date0}" />
			<spring:param name="code" value="${res.raceCode}" />
			<spring:param name="code" value="${res.id}" />
			</spring:url> <a href="${fn:escapeXml(leagueUrl)}"> Poblar</a>
			
			 </h5>
			</c:if>	 
				
		</c:forEach>
	</div>
</petclinic:layout>