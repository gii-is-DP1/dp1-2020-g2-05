<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="myTeams">
	 
	 <div style="display:inline-block;width: 50%; margin-left: 30px">
	
	<h1>ˇBienvenido a tu perfil <c:out value="${user.username}" /> !</h1>
	 </div>
	<div id="contenedor">
 	<div id="datosUser">
 	
 	<div style="display:inline-block;width: 30%; margin-left: 300px">
 	<p><strong>Nombre de usuario:</strong> <c:out value="${user.username}" /></p>
  	<p><strong>Email:</strong> <c:out value="${user.email}" /></p>
 	</div>
 	
 	<div style="display:inline-block;width: 30%;">
 	 	<img alt="Imagen no encontrada" src="<c:out value="${user.imgperfil}"/>" 
 	 	width="165px" height="165px" 
 	 	style="border:1px solid #414b50;align-items: center;" onerror="this.src='https://images.assetsdelivery.com/compings_v2/pxlprostudio/pxlprostudio1902/pxlprostudio190203671.jpg'" />
 	 	
 	 	<spring:url value="/users/editarPerfil" var="ChangueUrl">
		<spring:param name="teamId" value="${team.id}" />
		</spring:url>
 	 	<a href="${fn:escapeXml(ChangueUrl)}" type="bottom">Editar perfil</a>
 	</div>
 	 </div>
 	 </div>
 	 </div>
 	 
	 <div style="display:inline-block;width: 80%; margin-left:110px; ">

 	<h2>Estos son todos tus equipos:</h2>
	<table id="TeamsTable" class="table table-striped">
		<thead>
			<tr>
				<th>Liga</th>
				<th>Name</th>
				<th>Points</th>
				<th>Money</th>
				<th>User</th>
				<th>Actions</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${teams}" var="team">
				<tr>
					<td><c:out value="${team.league.name} " /></td>
					<td><c:out value="${team.name} " /></td>
					<td><c:out value="${team.points}" /></td>
					<td><spring:url
							value="/myTeams/{teamID}/transactions" var="TeamUrl">
							<spring:param name="teamID" value="${team.id}" />
						</spring:url> <a href="${fn:escapeXml(TeamUrl)}"><c:out
								value="${team.money}" /></a></td>
					<td><c:out value="${team.user.username}" /></td>
					<td><spring:url
							value="/leagues/{leagueId}/teams/{teamId}/edit" var="TeamUrl">
							<spring:param name="teamId" value="${team.id}" />
							<spring:param name="leagueId" value="${team.league.id}" />
						</spring:url> <a href="${fn:escapeXml(TeamUrl)}">Edit</a> <spring:url
							value="/leagues/{leagueId}/teams/{teamId}/delete" var="TeamUrl">
							<spring:param name="teamId" value="${team.id}" />
							<spring:param name="leagueId" value="${team.league.id}" />
						</spring:url> <a href="${fn:escapeXml(TeamUrl)}">Delete</a>
						
						 <spring:url
							value="/leagues/{leagueId}/teams/{teamId}/details" var="TeamDetailsUrl">
							<spring:param name="teamId" value="${team.id}" />
							<spring:param name="leagueId" value="${team.league.id}" />
						</spring:url> <a href="${fn:escapeXml(TeamDetailsUrl)}">Details</a>
						
						</td>
				</tr>
			</c:forEach>

		</tbody>
	</table>
	</div>
</petclinic:layout>