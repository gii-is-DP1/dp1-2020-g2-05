<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page import="org.springframework.samples.petclinic.model.Category" %>


<petclinic:layout pageName="CP">
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
	<c:forEach items="${listaGP}" var="res" varStatus="status">
		
			 <h5>
			 <c:out value="${res.circuit}"  />  
			 <c:out value=" ${res.date0}"  /> 
			<spring:url value="/BD/carrerasBD/{date}/{code}/{id}" var="leagueUrl">
			<spring:param name="date" value="${res.date0}" />
			<spring:param name="code" value="${res.raceCode}" />
			<spring:param name="id" value="${res.id}" />
			</spring:url> 	
			
					 			
			 <c:if test="${(res.hasBeenRun==false && listaGP[status.index-1].hasBeenRun==true) || (status.index == 0 && res.hasBeenRun==false) }">
			<a href="${fn:escapeXml(leagueUrl)}"> Poblar</a>
			</c:if>
			
			<spring:url value="/granPremios/{id}/delete" var="deleteGranPremioUrl">
			<spring:param name="id" value="${res.id}" />
			</spring:url> 			 			 
			<a href="${fn:escapeXml(deleteGranPremioUrl)}"> Delete</a>
			
<%-- 		    <c:if test="${res.hasBeenRun==true}"> --%>
<%-- 			<spring:url value="/granPremios/{id}/validateResults/{code}" var="deleteGranPremioUrl"> --%>
<%-- 			<spring:param name="id" value="${res.id}" /> --%>
<%-- 			<spring:param name="code" value="${res.raceCode}" /> --%>
<%-- 			</spring:url> 			 			  --%>
<%-- 			<a href="${fn:escapeXml(deleteGranPremioUrl)}"> Validate Results</a> --%>
<%-- 			</c:if> --%>
			 </h5>
			
				
		</c:forEach>
	</div>
</petclinic:layout>