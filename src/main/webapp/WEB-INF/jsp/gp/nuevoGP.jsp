<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="GranPremio">
 <c:forEach items="${errors}" var="mess">
<h2><c:out value="${mess.getDefaultMessage()}"/></h2>
</c:forEach>
     <form:form modelAttribute="GranPremio" class="form-horizontal" id="add-pilot-form" actions="/granPremios/new"  > 
        <div class="form-group has-feedback">
        
            <petclinic:inputField label="Site" name="site"/>
            <petclinic:inputField label="Circuit" name="circuit"/>
            <petclinic:inputField label="Date" name="date0"/>
            <petclinic:inputField label="raceCode" name="raceCode"/>
        </div>
        <div class="form-group">
   				 <button class="btn btn-default" type="submit">Add Gran Premio</button>
           
        </div>
     </form:form> 
</petclinic:layout>