<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="lineups">

	<h2>Lineup Information</h2>


	<table class="table table-striped">
		<tr>
			<th>Category</th>
			<th>GP</th>
			<th>Recruit1</th>
			<th>Recruit2</th>
			<th>Team</th>
		</tr>
		<tr>
			<td>${lineup.category}</td>
			<td><spring:eval expression="lineup.gp" /></td>
			<td><spring:eval expression="lineup.recruit1" /></td>
			<td><spring:eval expression="lineup.recruit2" /></td>
			<td>${lineup.team}</td>
		</tr>
	</table>



</petclinic:layout>
