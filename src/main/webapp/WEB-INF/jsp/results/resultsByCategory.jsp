<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="result">

	<h2>Results</h2>
	
	<table class="table table-striped">
		<tr>
			<th>Position</th>
			<th>Pole</th>
			<th>Lap</th>
			<th>Pilot</th>
		</tr>
		
		<c:forEach items="${resultados}" var="res">
		<tr>
			 <td><c:out value="${res.position}"  /> </td>
			 <td><c:out value="${res.pole}"  /> </td>
			 <td><c:out value="${res.lap}"  /> </td>
			 <td><c:out value="${res.pilot}"  /></td>
			
		</tr>
			 
		</c:forEach>
	</table>
	
	

</petclinic:layout>
