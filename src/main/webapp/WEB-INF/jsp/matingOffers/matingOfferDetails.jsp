<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="matingOffers">

	<h2>matingOffer Information</h2>


	<table class="table table-striped">
		<tr>
			<th>Pet</th>
			<td><b><c:out value="${matingOffer.pet.name}" /></b></td>
		</tr>
		<tr>
			<th>Owner</th>
			<td><b><c:out value="${matingOffer.pet.owner.user.username}" /></b></td>
		</tr>
		<tr>
			<th>Description</th>
			<td><c:out value="${matingOffer.description}" /></td>
		</tr>
	</table>



	<br />
	<br />
	<br />
	<h2>Citas:</h2>

	<table class="table table-striped" id="TablaCitas">
		<c:forEach var="cita" items="${matingOffer.citas}">

			<tr>
				<td valign="top">
					<dl class="dl-horizontal">
						<dt>Date</dt>
						<dd>
							<c:out value="${cita.dateTime}" />
						</dd>
						<dt>Petname</dt>
						<dd>
							<c:out value="${cita.pet2.name}" />
						</dd>
						<dt>Type</dt>
						<dd>
							<c:out value="${cita.place}" />
						</dd>
						<dt>place</dt>
						<dd>
							<c:out value="${cita.pet2.type.name}" />
						</dd>
						<dt>Gender</dt>
						<dd>
							<c:out value="${cita.pet2.gender.name}" />
						</dd>
						<dt>Status</dt>
						<dd>
							<c:out value="${cita.status}" />
						</dd>
						<spring:url value="/pets/{petId}/citas/edit/{citaId}"
							var="citaUrl">
							<spring:param name="citaId" value="${cita.id}" />

							<spring:param name="petId" value="${matingOffer.pet.id}" />
							<a href="/pets/${matingOffer.pet.id}/matingOffers/${matingOffer.id}/citas/edit/${cita.id}">Edit
								cita </a>
						</spring:url>


						<spring:url value="/citas/delete/{citaId}" var="citaUrl">
							<spring:param name="citaId" value="${cita.id}" />

							<spring:param name="petId" value="${matingOffer.pet.id}" />
							<a href="/matingOffers/${matingOffer.id}/citas/delete/${cita.id}">delete cita </a>
						</spring:url>
				</td>
		</c:forEach>




		<spring:url value="/pets/{petId}/citas/new" var="citaUrl">

			<spring:param name="petId" value="${matingOffer.pet.id}" />
			<a
				href="/pets/${matingOffer.pet.id}/matingOffers/${matingOffer.id}/citas/new">Add
				cita </a>
		</spring:url>

		</dl>
	</table>





</petclinic:layout>
