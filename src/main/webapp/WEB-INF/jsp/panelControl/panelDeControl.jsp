<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page import="org.springframework.samples.petclinic.model.Category" %>
<!-- CSS only -->

<petclinic:layout pageName="CP">
<c:if test="${message!=null}"><c:out value="${message}"/></c:if>	
<c:if test="${messageMoto2NotFound!=null}"><c:out value="${messageMoto2NotFound}"/><br></c:if>	
<c:if test="${messageMotogpNotFound!=null}"><c:out value="${messageMotogpNotFound}"/><br></c:if>	
<c:if test="${messageMoto3NotFound!=null}"><c:out value="${messageMoto3NotFound}"/><br></c:if>	
<h2>CONTROL PANEL</h2>


<div style="">
<div style="display: inline-block;  width: 30%; height: 100vh;vertical-align: top">
<spring:url value="/controlPanelSP/actualizaVariables" var="actualizaVariables">
		</spring:url> <a href="${fn:escapeXml(actualizaVariables)}"> Update Variables</a><br>
		
		<spring:url value="/controlPanelSP/validaCarreras" var="validaCarreras">
		</spring:url> <a href="${fn:escapeXml(validaCarreras)}"> Validate the last race</a><br>
		        	
		<spring:url value="/granPremios/new" var="leagueUrl">
		</spring:url> <a href="${fn:escapeXml(leagueUrl)}">Add new GP (Calendar)</a><br>
		
		<spring:url value="/controlPanelSP/poblarCarreras" var="poblarUrl">
		</spring:url> <a href="${fn:escapeXml(poblarUrl)}">Populate database</a>
</div>

<div style="display: inline-block;  width: 65%; height: 100vh;vertical-align: top;">
BIENVENIDO AL PANEL DE CONTROL DE DREAMGP<br>
Herramientas habilitadas:<br>
-Actualizar variables del sistema.<br>
-Validar manualmente la ultima carrera completada.<br>
-Poblar manualmente los resultados de una carrera.<br>
-Añadir/Eliminar una carrera.<br>
<img alt="" src="/resources/images/controlpanel1.png">
</div>
</div>

</petclinic:layout>