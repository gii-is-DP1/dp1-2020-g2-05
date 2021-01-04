<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="trades">
	<h2>Trades</h2>

	<table id="tradesTable" class="table table-striped">
		<thead>
			<tr>
				<th>Date</th>
				<th>Price</th>
				<th>Concept></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${trades.trades}" var="trade">
				<tr>
					<td><c:out value="${trade.date}" /></td>

					<c:if ${trade.transactionType == TransactionType.BUY}>
						<td>
							<p style="color: green;">
								+
								<c:out value="${trade.price}" />
							</p>
						</td>
						<td>
							<p>Venta de</p> <c:out value="${trade.concept}" />
						</td>
					</c:if>

					<c:if ${trade.transactionType == TransactionType.BUY}>
						<td>
							<p style="color: red;">
								+
								<c:out value="${trade.price}" />
							</p>
						</td>
						<td>
							<p>
								Compra de
								<c:out value="${trade.concept}" />
							</p>
						</td>
					</c:if>


				</tr>
			</c:forEach>
		</tbody>
	</table>

</petclinic:layout>
