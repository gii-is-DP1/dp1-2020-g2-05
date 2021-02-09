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
margin-right:30px;
margin-top:60px;
}

.grid{
display: grid;
  grid-template-columns: auto auto auto;
  padding: 10px;

}

.grid-item {

  padding: 20px;
  
  text-align: center;
}

.grid-item2 {
  padding: 20px;

  text-align: center;
  
  margin-top:20px;
}

.grid-item3 {
  padding: 20px;

  text-align: center;
  
  margin-top:40px;
}
</style>

<petclinic:layout pageName="gpDetails">

	<h2>Grid <c:out value="${gp.circuit }"/></h2>
	
	<div>
	
	<div class="divfoto">
	<img alt="Imagen no encontrada" src="<c:out value="${gp.imagen}"/>" 
 	 		width="265px" height="265px" 
 	 		style="border-radius: 10px;border:1px solid #a43939;align-items: center;" onerror="this.src='https://i.pinimg.com/originals/29/d5/ca/29d5ca916cca2eb10871eaa9af3ce84b.png'" />
 			   
	</div>
	
	<div class="divdatos">
	
<div class="grid">
<c:forEach step="3" items="${grid}" var="g" begin="${0}"  varStatus="status">
<c:set var="index1" value="${status.index +1}" />
<c:set var="index2" value="${status.index +2}" />




		<div class="grid-item">
		 
			<h2 style="text-align:center;"><c:out value="${g.result.position}"/>	<br>	</h2>		
			<p style="text-align:center;"><b><c:out value="${g.name}"/></b>	<br>	 </p>
			<p style="text-align:center;"> <c:out  value="${g.result.fastestLapTime}"/></p>	<br>	
		
		</div>
		<div class="grid-item2">
		 
			<h2 style="text-align:center;"><c:out value="${grid[index1].result.position}"/>	<br>	</h2>		
			<p style="text-align:center;"><b><c:out value="${grid[index1].name}"/></b>	<br>	 </p>
			<p style="text-align:center;"> <c:out  value="${grid[index1].result.fastestLapTime}"/></p>	<br>	
		
		</div>
		<div class="grid-item3">		 
			<h2 style="text-align:center;"><c:out value="${grid[index2].result.position}"/>	<br>	</h2>		
			<p style="text-align:center;"><b><c:out value="${grid[index2].name}"/></b>	<br>	 </p>
			<p style="text-align:center;"> <c:out  value="${grid[index2].result.fastestLapTime}"/></p>	<br>	
		
		</div>
		
		</c:forEach>
</div>
		
	</div>
	</div>
	
	

	
	


</petclinic:layout>
