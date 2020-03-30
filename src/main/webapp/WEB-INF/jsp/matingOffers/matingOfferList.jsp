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

<petclinic:layout pageName="matingOffers">
	<h2>Mating Offers</h2>

	<table id="matingOffersTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Name</th>
				<th style="width: 200px;">Description</th>
				<th>Type</th>
				<th>Date</th>
				<sec:authorize access="hasAuthority('admin')">
					<th>Actions</th>
				</sec:authorize>

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${matingOffers}" var="matingOffer">
				<tr>
					<td><c:out value="${matingOffer.pet.name}" /></td>
					<td><c:out value="${matingOffer.description}" /></td>
					<td><c:out value="${matingOffer.pet.type}" /></td>
					<td><c:out value="${matingOffer.date}" /></td>
					<sec:authorize access="hasAuthority('admin')">
						<td><spring:url value="/matingOffers/delete/{matingOfferId}"
								var="matingOfferUrl">
								<spring:param name="matingOfferId" value="${matingOffer.id}" />
							</spring:url> <a href="${fn:escapeXml(matingOfferUrl)}">Delete</a></td>
					</sec:authorize>
					 <td><spring:url value="pets/{petId}/matingOffers/{matingOfferId}"
                                var="citatUrl">
                                <spring:param name="petId" value="${pet.id}"/>
                                <spring:param name="matingOfferId" value="${matingOffer.id}" />
                            </spring:url> <a href="/pets/${matingOffer.pet.id}/matingOffers/${matingOffer.id}">Entrar</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<a class="btn btn-default"
		href='<spring:url value="/matingOffers/new" htmlEscape="true"/>'>Add
		Mating Offer</a>

</petclinic:layout>