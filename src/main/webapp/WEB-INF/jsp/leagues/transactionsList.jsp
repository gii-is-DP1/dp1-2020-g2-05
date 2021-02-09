<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="transactionsList" userTeam="${team.id}" userTeamName="${team.name}" userLeague="${team.league.id}" userLeagueName="${team.league.name}" userMoney="${team.money}">
	<h2>
		Current money:
		<c:out value="${money}" />
	</h2>
	<h2>Transactions</h2>
	<table id="transactionsTable" class="table table-striped">
		<thead>
			<tr>
				<th>Date</th>
				<th>Remaining money</th>
				<th>Amount</th>
				<th>Concept</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${transactions}" var="transaction">
				<tr>
					<td><c:out value="${transaction.date}" /></td>
					<td><c:out value="${transaction.remainingMoney}" /></td>
					<td><c:choose>
							<c:when
								test="${transaction.amount > 0}">
								<p style="color: green;">
									+<c:out value="${transaction.amount}" />
								</p>
							</c:when>

							<c:otherwise>
								<p style="color: red;">
									<c:out value="${transaction.amount}" />
								</p>
							</c:otherwise>
						</c:choose>
					</td>

					<td><c:out value="${transaction.concept}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>