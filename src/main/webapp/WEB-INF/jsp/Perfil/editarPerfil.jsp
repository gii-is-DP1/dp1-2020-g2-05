<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="teamDetails">


	
    <h2>Edit <c:out value="${user.username}" /></h2>
     <form:form modelAttribute="user" class="form-horizontal" id="add-pilot-form" actions="/users/editarPerfil"  > 
        <div class="form-group has-feedback">
        	<input type="hidden" name="username" value="${user.username}"/>
            <petclinic:inputField label="Email" name="email"/>
            <petclinic:inputField label="Password" name="password"/>
           	<petclinic:inputField label="Imagen de perfil" name="imgperfil"/>
            
            	 
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
               
                        <button class="btn btn-default" type="submit">Save</button>
              
               
            </div>
        </div>
     </form:form> 
  
     	
</petclinic:layout>