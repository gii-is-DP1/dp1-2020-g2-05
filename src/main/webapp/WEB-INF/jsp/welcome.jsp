<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<petclinic:layout pageName="home">
  <img src="/resources/images/logo.png" style="width:50px:;height:50px;margin-left:100%"/>
    
    <div class="row">
	<h2>Welcome <c:out value="${user.username}"></c:out> !</h2>
    <h2>Project ${title}</h2>
    <p><h2>Group ${group}</h2></p>
    <p><ul>
    <c:forEach items="${persons}" var = "person">
    	<li><c:out value="${person.lastName}"></c:out>, <c:out value="${person.firstName}"></c:out></li>
    </c:forEach>
    </ul></p>
    </div>
    <div class="row">
        <div class="col-md-12">
            <spring:url value="/resources/images/welcomePhoto.png" htmlEscape="true" var="welcomePhoto"/>
            <img class="img-responsive" src="${welcomePhoto}"/>
            
        </div> 
    </div>
              
</petclinic:layout>
