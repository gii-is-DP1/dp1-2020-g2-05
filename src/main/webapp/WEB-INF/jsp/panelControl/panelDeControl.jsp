<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page import="org.springframework.samples.petclinic.model.Category" %>
<!-- CSS only -->

<style>

.button:hover{
color:white;
background-color: #912c2c;

}
.buttontop:hover{
color:white;
background-color: #912c2c;

}

.buttonbot:hover{
color:white;
background-color: #912c2c;

}
.button{
height:25px;
width:100%;
background-color: #a43939;
color:white;
-webkit-appearance: button;
    -moz-appearance: button;
    appearance: button;
    text-align:center;

}
.buttonbot{
height:25px;
text-align:center;
width:100%;
background-color: #a43939;
color:white;
border-bottom-left-radius:10px;
border-bottom-right-radius:10px;
-webkit-appearance: button;
    -moz-appearance: button;
    appearance: button;
}
.buttontop{
height:25px;
text-align:center;
width:100%;
background-color: #a43939;
color:white;
border-top-left-radius:10px;
border-top-right-radius:10px;
-webkit-appearance: button;
-moz-appearance: button;
appearance: button;
}
</style>
<petclinic:layout pageName="CP">
<c:if test="${message!=null}"><c:out value="${message}"/></c:if>	
<c:if test="${messageMoto2NotFound!=null}"><c:out value="${messageMoto2NotFound}"/><br></c:if>	
<c:if test="${messageMotogpNotFound!=null}"><c:out value="${messageMotogpNotFound}"/><br></c:if>	
<c:if test="${messageMoto3NotFound!=null}"><c:out value="${messageMoto3NotFound}"/><br></c:if>	
<h2>CONTROL PANEL</h2>


<div>
<div style="display: inline-block;  width: 30%; height: 100vh;vertical-align: top;margin-right:30px;">
<spring:url value="/controlPanelSP/actualizaVariables" var="actualizaVariables">
		</spring:url> <a class="buttontop" href="${fn:escapeXml(actualizaVariables)}" > Update Variables</a><br>
		
		<spring:url value="/controlPanelSP/validaCarreras" var="validaCarreras">
		</spring:url> <a class="button" href="${fn:escapeXml(validaCarreras)}" > Validate the last race</a><br>
		        	
		<spring:url value="/granPremios/new" var="leagueUrl">
		</spring:url> <a class="button" href="${fn:escapeXml(leagueUrl)}" >Add new GP (Calendar)</a><br>
		
		<spring:url value="/controlPanelSP/poblarCarreras" var="poblarUrl">
		</spring:url> <a class="buttonbot" href="${fn:escapeXml(poblarUrl)}" >Populate database</a>

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