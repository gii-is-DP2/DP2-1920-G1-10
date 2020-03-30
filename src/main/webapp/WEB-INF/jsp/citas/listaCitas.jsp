<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="citas">
    <h2>citas</h2>

    <table id="citasTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">pet1</th>
            <th style="width: 200px;">pet2</th>
             <th style="width: 200px;">Actions</th>
           
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${citas}" var="citas">
            <tr>
                
                <td>
                    <c:out value="${cita.pet1}"/>
                </td>
                <td>
                    <c:out value="${cita.pet2}"/>
                </td>
            <td><spring:url value="/citas/delete/{citaId}"
                                var="citatUrl">
                                <spring:param name="citaId" value="${cita.id}" />
                            </spring:url> <a href="${fn:escapeXml(citaUrl)}">Delete</a></td>
               
 
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
