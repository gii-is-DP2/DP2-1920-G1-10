<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
  pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="booking">
    <h2>Bookings</h2>

    <table id="bookingsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Product</th>
            <th>Quantity</th>
            <th style="width: 120px">Date</th>
            <th>Delete</th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${reservas}" var="booking">
            <tr>
                
                <td>
                    <a href="/products/${booking.producto.id}"><c:out value="${booking.producto.name}"/></a>
                </td>
              
                <td>
                    <c:out value="${booking.numProductos}"/>
                </td>
                <td>
                        <c:out value="${booking.fecha} "/>
                </td>
                <td>
                	<spring:url value="/bookings/delete/{bookingId}" var="bookingUrl">
								<spring:param name="bookingId" value="${booking.id}" />
					</spring:url> <a href="${fn:escapeXml(bookingUrl)}">Delete</a>
                </td>
      
<!--
                <td> 
                    <c:out value="${owner.user.username}"/> 
                </td>
                <td> 
                   <c:out value="${owner.user.password}"/> 
                </td> 
-->
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
