<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="BD">


<div style="margin-top:20%;margin-bottom:20%">
<div style="display:inline-block;margin-left:30%;"><button class="buttonBD" onclick="window.location.href='/BD/carrerasBD'">Settle DB race by race</button></div>

<div style="display:inline-block;margin-left:50px;"><button class="buttonBD" onclick="window.location.href='/BD/pilotsBD'">Settle DB from year "x" to year "y"</button></div>

</div>


</petclinic:layout>