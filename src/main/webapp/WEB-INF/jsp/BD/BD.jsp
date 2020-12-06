<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="Team">


	
	
    <h2>
        <c:if test="${FormRellenarBD['new']}">New </c:if> BD
    </h2>
     <form:form modelAttribute="FormRellenarBD" class="form-horizontal" id="add-pilot-form" actions="/"  > 
        <div class="form-group has-feedback">
       	
            <petclinic:inputField label="anyoInicial" name="anyoInicial"/>
            <petclinic:inputField label="anyoFinal" name="anyoFinal"/>
            <petclinic:inputField label="Category" name="Category"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${FormRellenarBD['new']}">
                        <button class="btn btn-default" type="submit">Poblar</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Teams</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
     </form:form> 
</petclinic:layout>