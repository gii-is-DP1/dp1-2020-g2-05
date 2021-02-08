<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ page import="org.springframework.samples.petclinic.model.Category" %>
<head>
<link rel="stylesheet" href="/resources/styles/pilot.css">
 	
</head>
<petclinic:layout pageName="leagues">

<div class="textCategory"><c:out value="${listasPilotos[1].category}"/></div>

<div id="podium-box" class="row" style="height: 300px">
	
  <div class="col-md-4 step-container m-0 p-0">
    <div>
     <span class="podiumText">  <c:out value="${listasPilotos[1].name} " /> </span> 
    <span class="podiumText">   <c:out value="${listasPilotos[1].lastName}" /> </span> 
          
     
    </div>
    <div id="second-step" class="bg-silver step centerBoth podium-number">
       <c:out value="${listasPilotos[1].points}" /> 
    </div>
  </div>
  
  
  <div class="col-md-4 step-container m-0 p-0">
    <div>
     <span class="podiumText"> <c:out value="${listasPilotos[0].name} " /></span>
    <span class="podiumText">  <c:out value="${listasPilotos[0].lastName}" /> </span>
         
     
    </div>
    <div id="first-step" class="bg-gold step centerBoth podium-number">
               <c:out value="${listasPilotos[0].points} "/>
    </div>
  </div>
  <div class="col-md-4 step-container m-0 p-0">
    <div>
		<span class="podiumText"><c:out value="${listasPilotos[2].name} " /> </span>
     <span class="podiumText"><c:out value="${listasPilotos[2].lastName}" /></span>
        
          </div>
    <div id="third-step" class="bg-ocre step centerBoth podium-number">
       <c:out value="${listasPilotos[2].points}" />
    </div>
  </div>
</div>

<div class="divisor"></div>

<div class="textCategory"><c:out value="${listasPilotos[4].category}"/></div>
<div id="podium-box" class="row" style="height: 300px">
  <div class="col-md-4 step-container m-0 p-0">
    <div>
 <span class="podiumText">  <c:out value="${listasPilotos[4].name} " /> </span> 
     <span class="podiumText">  <c:out value="${listasPilotos[4].lastName}" /></span> 
    
          </div>
    <div id="second-step" class="bg-silver step centerBoth podium-number">
       <c:out value="${listasPilotos[4].points}" />
    </div>
  </div>
  <div class="col-md-4 step-container m-0 p-0">
    <div>
 <span class="podiumText">  <c:out value="${listasPilotos[3].name} " /> </span> 
    <span class="podiumText">   <c:out value="${listasPilotos[3].lastName}" /></span> 
          </div>
    <div id="first-step" class="bg-gold step centerBoth podium-number">
             <c:out value="${listasPilotos[3].points}" />

    </div>
  </div>
  <div class="col-md-4 step-container m-0 p-0">
    <div>
 <span class="podiumText">  <c:out value="${listasPilotos[5].name} " /> </span> 
    <span class="podiumText">   <c:out value="${listasPilotos[5].lastName}" /></span> 
       
          </div>
    <div id="third-step" class="bg-ocre step centerBoth podium-number">
     <c:out value="${listasPilotos[5].points}" />
    </div>
  </div>
</div>


<div class="divisor"></div>

<div class="textCategory"><c:out value="${listasPilotos[7].category}"/></div>
<div id="podium-box" class="row" style="height: 300px">
  <div class="col-md-4 step-container m-0 p-0">
    <div>
 <span class="podiumText">    <c:out value="${listasPilotos[7].name} " /> </span>
     <span class="podiumText">    <c:out value="${listasPilotos[7].lastName}" /></span>
         
          </div>
    <div id="second-step" class="bg-silver step centerBoth podium-number">
      <c:out value="${listasPilotos[7].points}" /> 
    </div>
  </div>
  <div class="col-md-4 step-container m-0 p-0">
    <div>
  <span class="podiumText">   <c:out value="${listasPilotos[6].name} " /> </span>
     <span class="podiumText">    <c:out value="${listasPilotos[6].lastName}" />   </span>
        
       </div>
    <div id="first-step" class="bg-gold step centerBoth podium-number">
       <c:out value="${listasPilotos[6].points}" /> 
    </div>
  </div>
  <div class="col-md-4 step-container m-0 p-0">
    <div>
  <span class="podiumText">   <c:out value="${listasPilotos[8].name} " /> </span>
      <span class="podiumText">  <c:out value="${listasPilotos[8].lastName}" />   </span>  
     </div>
    <div id="third-step" class="bg-ocre step centerBoth podium-number">
            <c:out value="${listasPilotos[8].points}" /> 

    </div>
  </div>
</div>


</petclinic:layout>