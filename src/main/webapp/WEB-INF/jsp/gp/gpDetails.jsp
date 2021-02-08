<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<style>

.divfoto{
display:inline-block;
width: 40%; 
height: 100vh;
vertical-align: top;
margin-left:60px;
margin-right:30px;
margin-top: 60px;

}
.divdatos{
display:inline-block;
width: 40%; 
height: 100vh;
vertical-align: top;
margin-right:30px;
margin-top:60px;
}
</style>

<petclinic:layout pageName="gpDetails">

	<h2>Information about <c:out value="${gp.circuit }"/></h2>
	
	<div>
	
	<div class="divfoto">
	<img alt="Imagen no encontrada" src="<c:out value="${gp.imagen}"/>" 
 	 		width="265px" height="265px" 
 	 		style="border-radius: 10px;border:1px solid #a43939;align-items: center;" onerror="this.src='https://i.pinimg.com/originals/29/d5/ca/29d5ca916cca2eb10871eaa9af3ce84b.png'" />
 			   
	</div>
	
	<div class="divdatos">
		<i class="glyphicon glyphicon-menu-right"></i>  City: <c:if test="${lista.city==null}">
			NA
			</c:if>
			<c:if test="${lista.city!=null}">
			<c:out value="${lista.city}"/>		
			</c:if><br><br>
			
		<i class="glyphicon glyphicon-menu-right"></i>  Left corners: 	<c:if test="${lista.curvesLeft==null}">
			NA
			</c:if>
			<c:if test="${lista.curvesLeft!=null}">
			<c:out value="${lista.curvesLeft}"/>		
				</c:if><br><br>
			
		<i class="glyphicon glyphicon-menu-right"></i>	Right corners: 	<c:if test="${lista.curvesRight==null}">
			NA
			</c:if>
			<c:if test="${lista.curvesRight!=null}">
			<c:out value="${lista.curvesRight}"/>
			</c:if><br><br>
			
			<i class="glyphicon glyphicon-menu-right"></i>	Length: 	<c:if test="${lista.length==null}">
			NA
			</c:if>
			<c:if test="${lista.length!=null}">
			<c:out value="${lista.length}"/>
			</c:if><br><br>
			
			<i class="glyphicon glyphicon-menu-right"></i>  Debut: <c:if test="${lista.debut==null}">
			NA
			</c:if>
			<c:if test="${lista.debut!=null}">
			<c:out value="${lista.debut}"/>			
			</c:if><br><br>
			<i class="glyphicon glyphicon-menu-right"></i> Fecha: <c:out value="${gp.date0}"/>	
	</div>
	</div>
	
	

	
	


</petclinic:layout>
