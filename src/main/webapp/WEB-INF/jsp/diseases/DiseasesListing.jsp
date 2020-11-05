<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="vademécum">
    <h2>Diseases</h2>

    <table id="diseasesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Name</th>
            <th style="width: 200px;">Description</th>
            <th style="width: 20px;"></th>
            <th style="width: 20px;"></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${diseases}" var="disease">
            <tr>
                <td>
                    <c:out value="${disease.name}"/>
                </td>
                <td>
                    <c:out value="${disease.description}"/>
                </td>
                <td>
                    <a href="/diseases/${disease.id}/edit" class="glyphicon glyphicon-pencil"/></a>
                </td>
                <td>
                    <a href="/diseases/${disease.id}/delete" class="glyphicon glyphicon-trash"/></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <p>
    	<a href="/diseases/new" class="btn  btn-success"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>Add disease</a>
    </p>
</petclinic:layout>
