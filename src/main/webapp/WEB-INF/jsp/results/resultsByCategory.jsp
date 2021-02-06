<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="result">

	<h1>Gran Premio asociado: <spring:eval expression="gp"/></h1>
	
	<h2>Results</h2>

	<table class="table table-striped">
	<thead>
		<tr>
			<th>Position</th>
			<th>Pilot</th>
		</tr>
	</thead>
		<c:forEach items="${resultados}" var="res">
			<tr>
				<td><c:out value="${res.position}" /></td>
				<td><c:out value="${res.pilot}" /></td>
			</tr>
		</c:forEach>
	</table>

	<h2>Records</h2>

	<table class="table table-striped">
	<thead>
		<tr>
			<th>Pole Lap</th>
			<th>Fastest Lap</th>
			<th>Circuit Record</th>
			<th>Best Lap</th>
		</tr>
		</thead>

		<tr>
			<td style="width: 25%;"><h5><b>Rider Name: </b> <c:out value="${record.vueltaDePole.nombrePiloto}" /></h5>
			<h5><b>Time: </b> <c:out value="${record.vueltaDePole.tiempo}" /> ms  </h5>	
			<h5><b>Average Speed:</b>  <c:out value="${record.vueltaDePole.kmh}" /> Km/h  </h5>	
			</td>
			
			<td style="width: 25%;"><h5><b>Rider Name: </b> <c:out value="${record.vueltaRapida.nombrePiloto}" /></h5>
			<h5><b>Fastest Lap: </b> <c:out value="${record.vueltaRapida.vuelta}" /></h5>
			<h5><b>Time: </b> <c:out value="${record.vueltaRapida.tiempo}" /> ms  </h5>	
			<h5><b>Average Speed:</b>  <c:out value="${record.vueltaRapida.kmh}" /> Km/h  </h5>	
			</td>
			<td style="width: 25%;"><h5><b>Rider Name: </b> <c:out value="${record.recordDelCircuito.nombrePiloto}" /></h5>
			<h5><b>Year: </b> <c:out value="${record.recordDelCircuito.anyo}" /></h5>
			<h5><b>Time: </b> <c:out value="${record.recordDelCircuito.tiempo}" /> ms  </h5>	
			<h5><b>Average Speed:</b>  <c:out value="${record.recordDelCircuito.kmh}" /> Km/h  </h5>	
			</td>			

			<td style="width: 25%;"><h5><b>Rider Name: </b> <c:out value="${record.mejorVuelta.nombrePiloto}" /></h5>
			<h5><b>Year: </b> <c:out value="${record.recordDelCircuito.anyo}" /></h5>
			<h5><b>Time: </b> <c:out value="${record.mejorVuelta.tiempo}" /> ms  </h5>	
			<h5><b>Average Speed:</b>  <c:out value="${record.mejorVuelta.kmh}" /> Km/h  </h5>	
			</td>
		</tr>
	</table>

</petclinic:layout>
