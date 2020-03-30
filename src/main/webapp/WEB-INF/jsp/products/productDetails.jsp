<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="products">

	<h2>Product Information</h2>

	<div>
		<img style="width:20%;height:20%;" src="${product.urlImage}" />
	</div>

	<table class="table table-striped">
		<tr>
			<th>Name</th>
			<td><b><c:out value="${product.name}" /></b></td>
		</tr>
		<tr>
			<th>Description</th>
			<td><c:out value="${product.description}" /></td>
		</tr>
		<tr>
			<th>Price</th>
			<td><c:out value="${product.price}" /></td>
		</tr>
		<tr>
			<th>Stock</th>
			<td><c:out value="${product.stock}" /></td>
		</tr>
	</table>

	<spring:url value="{productId}/purchase" var="purchaseUrl">
		<spring:param name="purchaseId" value="${product.id}" />
	</spring:url>
	<a href="${fn:escapeXml(purchaseUrl)}" class="btn btn-default">Purchase</a>

	<br />
	<br />
	<br />

</petclinic:layout>