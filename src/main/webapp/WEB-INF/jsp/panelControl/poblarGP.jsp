<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page import="org.springframework.samples.dreamgp.model.Category" %>


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
<h2>CONTROL PANEL -- POPULATE BBDD</h2>

<div>
<div style="display: inline-block;  width: 30%; height: 100vh;vertical-align: top; margin-right:30px; margin-top:20px;">
<spring:url value="/controlPanelSP/actualizaVariables" var="actualizaVariables">
		</spring:url> <a  class="buttontop" href="${fn:escapeXml(actualizaVariables)}" >    Update Variables</a><br>
		
		<spring:url value="/controlPanelSP/validaCarreras" var="validaCarreras"></spring:url> 
		
		<a class="button" href="${fn:escapeXml(validaCarreras)}" >    Validate the last race</a><br>
		        	
		<spring:url value="/granPremios/new" var="leagueUrl"></spring:url> 
		<a class="button" href="${fn:escapeXml(leagueUrl)}" >     Add new GP (Calendar)</a><br>
		
		<spring:url value="/controlPanelSP/poblarCarreras" var="poblarUrl"></spring:url> 
		<a class="buttonbot" href="${fn:escapeXml(poblarUrl)}" >    Populate database</a>
</div>

<div style="display: inline-block;  width: 65%; height: 100vh;vertical-align: top;">
<c:forEach items="${listaGP}" var="res" varStatus="status">

			
		 	<div style="display:inline-block;width: 32%;;height:240px">
		 	<c:out value="${res.circuit}"  /> <br>
		 	<c:out value=" ${res.date0}"  /> 
 	 		<img alt="Imagen no encontrada" src="<c:out value="${res.imagen}"/>" 
 	 		width="165px" height="165px" 
 	 		style="border-radius: 10px;border:1px solid #a43939;align-items: center;" onerror="this.src='https://i.pinimg.com/originals/29/d5/ca/29d5ca916cca2eb10871eaa9af3ce84b.png'" />
 			   
			 
			<spring:url value="/BD/carrerasBD/{date}/{code}/{id}" var="leagueUrl">
			<spring:param name="date" value="${res.date0}" />
			<spring:param name="code" value="${res.raceCode}" />
			<spring:param name="id" value="${res.id}" />
			</spring:url> 	
			<div>
			<div style="display:inline-block">
				 <c:if test="${(res.hasBeenRun==false && listaGP[status.index-1].hasBeenRun==true && listaGP[status.index-1].hasBeenValidated==true) || (status.index == 0 && res.hasBeenRun==false) }">
			<a href="${fn:escapeXml(leagueUrl)}"> Poblar</a>
			</c:if> 
				</div>	 			
			
			<div style="display:inline-block">
				<spring:url value="/granPremios/{id}/delete" var="deleteGranPremioUrl">
			<spring:param name="id" value="${res.id}" />
			</spring:url> 			 			 
			<a href="${fn:escapeXml(deleteGranPremioUrl)}"> Delete</a>
			</div>
			</div>
				

 			</div>
	
			
				
		</c:forEach>
</div>
</div>

</petclinic:layout>