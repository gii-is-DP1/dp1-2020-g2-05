<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="pilots">
    <h2>Pilots</h2>

    <table id="pilotsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>Lastname</th>
            <th>Dorsal</th>
            <th>Nationality</th>
            <th>Category</th>
            <th>Actions </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${resultados}" var="pilot">
            <tr>
                <td>
                    <c:out value="${pilot.name} "/>
                </td>
                <td>
                    <c:out value="${pilot.lastName}"/>
                </td>
                <td>
                    <c:out value="${pilot.dorsal}"/>
                </td>
                <td>
                    <c:out value="${pilot.nationality}"/>
                </td>
                <td>
                    <c:out value="${pilot.category}"/>
                </td>
                <td>
                
                 <spring:url value="/pilots/edit/{pilotId}" var="pilotUrl">
                        <spring:param name="pilotId" value="${pilot.id}"/>
                    </spring:url>
                	<a href="${fn:escapeXml(pilotUrl)}">Edit</a>
                	 
                	 <spring:url value="/pilots/delete/{pilotId}" var="pilotUrl">
                        <spring:param name="pilotId" value="${pilot.id}"/>
                    </spring:url>
                	<a href="${fn:escapeXml(pilotUrl)}">Delete</a>
                	
                	
                	
                	
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
