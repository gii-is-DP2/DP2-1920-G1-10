<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="citas">
	<jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#dateTime").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>

    </jsp:attribute>
	<jsp:body>
        <h2>
			<c:if test="${cita['new']}">New </c:if>Cita</h2>

        <b>Pet</b>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Name</th>
                <th>Birth Date</th>
                <th>Type</th>
                <th>Owner</th>
            </tr>
            </thead>
            <tr>
                <td><c:out value="${cita.pet1.name}" /></td>
                <td><petclinic:localDate
						date="${visit.pet.birthDate}" pattern="yyyy/MM/dd" /></td>
                <td><c:out value="${cita.pet1.type.name}" /></td>
                <td><c:out
						value="${cita.pet1.owner.firstName} ${cita.pet1.owner.lastName}" /></td>
            </tr>
        </table>
 <c:if test="${cita['new']}">
        <form:form modelAttribute="cita" class="form-horizontal">
            <div class="form-group has-feedback">
			

			
			<form:label label="pet2" path="Pet2"> Pet 2 </form:label>
			<form:select id="Pet2Id" path="Pet2.id">
				<form:option value="0" label="---" />
				<form:options items="${pets}" itemLabel="name" itemValue="id" />
			</form:select>

			<petclinic:inputField label="place" name="place" />
			<petclinic:inputField label="dateTime" name="dateTime" />
			
			<form:label path="status">
            
        </form:label>
       
        <form:errors cssClass="error" path="status" />
        <br />

		</div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="petId"
						value="${cita.pet1.id}" />
                     <input type="hidden" name="citaId"
						value="${cita.id}" />
						 <input type="hidden" name="status"
						value="PENDING" />
                    <button class="btn btn-default" type="submit">Add cita</button>
                </div>
            </div>
        </form:form>
</c:if>




<c:if test="${!cita['new']}">
        <form:form modelAttribute="cita" class="form-horizontal">
            <div class="form-group has-feedback">
			
			  <fieldset>
			<form:label  label="status" path="status">
            Status
        </form:label>
        <form:select id="status" path="status" >
            <form:option value="PENDING" label="PENDING" />
            <form:option value="ACCEPTED" label="ACCEPTED"  />
            <form:option value="REJECTED" label="REJECTED"  />
            </form:select>
            </fieldset>
			
             <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="petId"
						value="${cita.pet1.id}" />
                     <input type="hidden" name="citaId"
						value="${cita.id}" />
                    <button class="btn btn-default" type="submit">Add cita</button>



</div>
</form:form>
</c:if>
        <br />

       
    </jsp:body>

</petclinic:layout>