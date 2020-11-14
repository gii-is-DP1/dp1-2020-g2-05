<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="messages">

	<h2>message Information</h2>


	<table class="table table-striped">
		<tr>
			<th>ID</th>
			<th>Asunto</th>
			<th>Cuerpo</th>
			<th>Remitente</th>
			<th>Destinatario</th>
			<th>Actions</th>
		</tr>
		<tr>
			<td><c:out value="${message.id} " /></td>
			<td><c:out value="${message.asunto}" /></td>
			<td><c:out value="${message.cuerpo}" /></td>
			<td><c:out value="${message.usersend}" /></td>
			<td><c:out value="${message.userreceive}" /></td>
			
		</tr>
	</table>



</petclinic:layout>
