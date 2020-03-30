<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="owners">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#date").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2><c:if test="${matingOffer['new']}">New </c:if>matingOffer</h2>

        

        <form:form modelAttribute="matingOffer" class="form-horizontal">
            <div class="form-group has-feedback">
			
			
			<form:label label="pet" path="Pet"> Pet </form:label>
			<form:select id="PetId" path="Pet.id">
				<form:option value="0" label="---" />
				<form:options items="${pets}" itemLabel="name" itemValue="id" />
			</form:select>
			
			<petclinic:inputField label="date" name="date" />
			<petclinic:inputField label="description" name="description" />
			
	

		</div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="petId" value="${matingOffer.pet.id}"/>
                     <input type="hidden" name="citaId" value="${matingOffer.id}"/>
                     
                    <button class="btn btn-default" type="submit">Add matingOffer</button>
                </div>
            </div>
        </form:form>

        <br/>
       
    </jsp:body>

</petclinic:layout>