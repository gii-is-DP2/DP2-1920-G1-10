<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="Bookings">
    <jsp:attribute name="customScript">
        <script>
           
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2>Product</h2>

        <form:form modelAttribute="booking" class="form-horizontal" action = "/bookings/save?id=${product.id}">
            <div class="form-group has-feedback">
                
                <petclinic:inputField label="Cantidad" name="numProductos"/>
              	
              
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${booking.id}"/>
                  <!--    <input type="hidden" name="producto" value="${product}"/>
                   <input type="hidden" name="user" value="${user}"/>
                    <input type="hidden" name="date" value="${date}"/>  -->
                    <button class="btn btn-default" type="submit">Save booking</button>
                </div>
            </div>
        </form:form>
    </jsp:body>
</petclinic:layout>