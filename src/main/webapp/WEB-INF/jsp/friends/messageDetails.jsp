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
		</tr>
		<tr>
			<td><c:out value="${messagee.id} " /></td>
			<td><c:out value="${messagee.asunto}" /></td>
			<td><c:out value="${messagee.cuerpo}" /></td>
			<td><c:out value="${messagee.usernamesend}" /></td>
			<td><c:out value="${messagee.usernamereceive}" /></td>
			
		</tr>
	</table>



</petclinic:layout>
