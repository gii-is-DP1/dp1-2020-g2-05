<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="leagues">
	<h2>Leagues</h2>

	<table id="leaguesTable" class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
				<th>Date</th>
				<th>Category</th>
				<th>Races Completed</th>
	
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${ligas}" var="league">
				<tr>
					<td><c:out value="${league.name} " /></td>
					<td><c:out value="${league.leagueDate}" /></td>
					<c:if test="${league.motogpActive==true}">  
					<td>Moto GP </td>					
					</c:if>
					<c:if test="${league.moto2Active==true}">  
					<td>Moto 2 </td>					
					</c:if>
					<c:if test="${league.moto3Active==true}">  
					<td>Moto 3 </td>					
					</c:if>  
					<td><c:out value="${league.racesCompleted}" /></td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>