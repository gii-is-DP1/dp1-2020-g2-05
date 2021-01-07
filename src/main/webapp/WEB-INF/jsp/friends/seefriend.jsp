<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="friends">
	
<head>
<link rel="stylesheet" href="/resources/styles/seefriend.css">
 	
</head>
	
 
 	<div id="contenedor">
 	<div id="datosUser">
 	<h1>DATOS DEL USUARIO: </h1>
 	
 	<div style="display:inline-block;width: 48%;">
 	<p><strong>Nombre de usuario:</strong> <c:out value="${user.username}" /></p>
  	<p><strong>Email:</strong> <c:out value="${user.email}" /></p>
 	</div>
 	
 	<div style="display:inline-block;width: 48%;">
 	 	<img alt="Imagen no encontrada" src="<c:out value="${user.imgperfil}"/>" 
 	 	width="165px" height="165px" 
 	 	style="border:1px solid #414b50;align-items: center;" onerror="this.src='https://images.assetsdelivery.com/compings_v2/pxlprostudio/pxlprostudio1902/pxlprostudio190203671.jpg'" />
 	
 	</div>
 	 </div>
 	<div id="teams">
 	<h1>EQUIPOS DEL USUARIO:</h1>
 	   <c:forEach items="${user.team}" var="team">
 	   <div id="teamIndividual">
 	   <p><strong>Nombre del equipo:</strong> <c:out value="${team.name}" /></p>
   	   <p><strong>Puntos del equipo:</strong> <c:out value="${team.points}" /></p>
   	   <p><strong>Liga a la que pertenece:</strong> <c:out value="${team.league.name}" /></p>

 	   </div>
   	   
   	</c:forEach>
   	</div>
   	</div>
 

<%-- 		<form:form modelAttribute="user" class="form-horizontal" id="add-friend-form"> --%>
<!--         <div class="form-group has-feedback"> -->
<%--            <form:hidden path="email"/> --%>
<%--            <form:hidden path="password"/> --%>
<%--             <petclinic:inputField label="Username" name="username"/> --%>
<!--              <button class="btn btn-default" type="submit">Follow</button> -->
           
<!--         </div> -->
    
<%--     </form:form> --%>
<%-- 	<spring:url value="/messages/new/" var="messageUrl"> --%>
<%-- 			<spring:param name="messageId" value="${message.id}" /> --%>
<%-- 	</spring:url> <a href="${fn:escapeXml(messageUrl)}">Nuevo mensaje</a> --%>
</petclinic:layout>