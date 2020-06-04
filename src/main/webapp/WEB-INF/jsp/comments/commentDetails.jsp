<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="comments">

	<h2>Comment Information</h2>

	<div>
		<img style="width:20%;height:20%;" src="${comment.producto.urlImage}" />
	</div>

	<table class="table table-striped">
		<tr>
			<th>Comment's Product</th>
			<td><b><c:out value="${comment.producto.name}" /></b></td>
		</tr>
		<tr>
			<th>Email</th>
			<td><c:out value="${comment.email}" /></td>
		</tr>
		<tr>
			<th>Description</th>
			<td><c:out value="${comment.descripcion}" /></td>
		</tr>
		<tr>
			<th>Fecha</th>
			<td><c:out value="${comment.fecha}" /></td>
		</tr>
	</table>


	<br />
	<br />
	<br />

</petclinic:layout>