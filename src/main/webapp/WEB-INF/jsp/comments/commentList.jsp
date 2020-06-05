<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="booking">
    <h2 id= "listadoComentarios">Comments</h2>

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${comments}" var="comment">
				<tr>

					<td><a href="/products/${comment.producto.id}"><c:out
								value="${comment.producto.name}" /></a></td>

					<td><c:out value="${comment.fecha} " /></td>
					<td><spring:url value="/comments/{commentId}" var="commentUrl">
							<spring:param name="commentId" value="${comment.id}" />
						</spring:url> <a href="${fn:escapeXml(commentUrl)}">Show details</a></td>
					<td><spring:url value="/comments/delete/{commentId}"
							var="commentUrl">
							<spring:param name="commentId" value="${comment.id}" />
						</spring:url> <a href="${fn:escapeXml(commentUrl)}">Delete</a></td>

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
