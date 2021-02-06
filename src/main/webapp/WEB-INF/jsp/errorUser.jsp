<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<html>
<head>
</head>
<body>
	<petclinic:layout pageName="error">
		<h1>${error_message}</h1>
		<a href="/" style="display: flex;justify-content: center;align-items: center;"><button class="btn btn-default" >Home</button></a>
	</petclinic:layout>
</body>
</html>