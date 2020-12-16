<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="BDCarrera">

<c:if test="${messageNullPointerException==true}">
    <h2>
    API has not found any result to return with values that have been entered
    </h2>
    </c:if>
	
	
    <h2>
       BD
    </h2>
     <form:form modelAttribute="BDCarrera" class="form-horizontal" id="add-pilot-form" actions="/"  > 
        <div class="form-group has-feedback">
       	
            <petclinic:inputField label="A�o" name="year"/>         
            <div class="control-group">
				<petclinic:selectField name="Category" label="Categoria" names="${listaCategoria}" size="3" />
			</div>
			 <div class="control-group">
				<petclinic:selectField name="session" label="Session" names="${listaSession}" size="10" />
			</div>
			<div class="control-group">
				<petclinic:selectField name="racecode" label="RaceCode" names="${listaRacecode}" size="10" />
			</div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                        <button class="btn btn-default" type="submit">Poblar</button>
            </div>
        </div>
     </form:form> 
</petclinic:layout>