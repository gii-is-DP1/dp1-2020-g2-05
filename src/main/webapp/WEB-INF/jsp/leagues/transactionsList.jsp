<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ page import="org.springframework.samples.petclinic.model.TransactionType"%>

<petclinic:layout pageName="transaction">
	<h2>Transaction</h2>
	<table id="tradesTable" class="table table-striped">
		<thead>
			<tr>
				<th>Date</th>
				<th>Remaining money</th>
				<th>Price</th>
				<th>Concept</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${transaction}" var="transaction">
				<tr>
					<td><c:out value="${transaction.date}" /></td>
					<td><c:out value="${transaction.remainingMoney}" /></td>
					<td>
						<c:if test="${transactionrade.transactionType != TransactionType.BUY}">
							<p style="color: green;">
								+ <c:out value="${transaction.price}" />
							</p>
						</c:if> 
					
						<c:if test="${transaction.transactionType != TransactionType.BUY}">
							<p style="color: red;">
								- <c:out value="${transaction.price}" />
							</p>
						</c:if>
					</td>

					<td><c:out value="${transaction.concept}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>
