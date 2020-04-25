<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->

<petclinic:layout pageName="products">
	<h2>Products</h2>

	<table id="productsTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Name</th>
				<th style="width: 200px;">Description</th>
				<th>Price</th>
				<th>Stock</th>
				<th>Book</th>
				<sec:authorize access="hasAuthority('admin')">
					<th>Delete</th>
				</sec:authorize>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${products}" var="product">
				<tr>
					<td><spring:url value="/products/{productId}" var="productUrl">
							<spring:param name="productId" value="${product.id}" />
						</spring:url> <a href="${fn:escapeXml(productUrl)}"><c:out
								value="${product.name}" /></a></td>
					<td><c:out value="${product.description}" /></td>
					<td><c:out value="${product.price}" /></td>
					<td><c:out value="${product.stock}" /></td>

					<spring:url value="/bookings/new/{productId}" var="bookingUrl">
						<spring:param name="productId" value="${product.id}" />
					</spring:url>
					<td><a href="${fn:escapeXml(bookingUrl)}">Book</a></td>

					<sec:authorize access="hasAuthority('admin')">
						<td><spring:url value="/products/delete/{productId}"
								var="productUrl">
								<spring:param name="productId" value="${product.id}" />
							</spring:url> <a href="${fn:escapeXml(productUrl)}">Delete</a></td>
					</sec:authorize>

				</tr>
			</c:forEach>
		</tbody>
	</table>
	<sec:authorize access="hasAuthority('admin')">
		<a href="/products/new">New Product</a>
	</sec:authorize>

</petclinic:layout>