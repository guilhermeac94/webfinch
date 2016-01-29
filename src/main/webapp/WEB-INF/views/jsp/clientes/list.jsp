<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

    <jsp:include page="../fragments/header.jsp" />

    <body>

        <div class="container">

            <c:if test="${not empty msg}">
                <div class="alert alert-${css} alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <strong>${msg}</strong>
                </div>
            </c:if>

            <h1>Clientes</h1>

            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>#ID</th>
                        <th>Nome</th>
                        <th>Email</th>
                        <th>Sexo</th>
                        <th>Ação</th>
                    </tr>
                </thead>

                <c:forEach var="cliente" items="${clientes}">
                    <tr>
                        <td>${cliente.id}</td>
                        <td>${cliente.name}</td>
                        <td>${cliente.email}</td>
                        <td>${cliente.sex}</td>						
                        <td>
                            <spring:url value="/clientes/${cliente.id}" var="clienteUrl" />
                            <spring:url value="/clientes/${cliente.id}/delete" var="deleteUrl" /> 
                            <spring:url value="/clientes/${cliente.id}/update" var="updateUrl" />

                            <button class="btn btn-info" onclick="location.href = '${clienteUrl}'">Detalhado</button>
                            <button class="btn btn-primary" onclick="location.href = '${updateUrl}'">Editar</button>
                            <button class="btn btn-danger" onclick="this.disabled = true; post('${deleteUrl}')">Deletar</button></td>
                    </tr>
                </c:forEach>
            </table>

        </div>

        <jsp:include page="../fragments/footer.jsp" />

    </body>
</html>