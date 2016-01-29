<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
    <body>
        <jsp:include page="../fragments/header.jsp" />

        <div class="container">

            <c:choose>
                <c:when test="${create}">
                    <h1>Novo Cliente</h1>
                </c:when>
                <c:otherwise>
                    <h1>Editar Cliente</h1>
                </c:otherwise>
            </c:choose>
            <br />

            <c:if test="${not empty msg}">
                <div class="alert alert-${css} alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <strong>${msg}</strong>
                </div>
            </c:if>

            <c:choose>
                <c:when test="${create}">
                    <spring:url value="/clientes/new" var="clienteActionUrl" />
                </c:when>
                <c:otherwise>
                    <spring:url value="/clientes/update" var="clienteActionUrl" />
                </c:otherwise>
            </c:choose>
            <form:form class="form-horizontal" method="post" modelAttribute="clienteForm" action="${clienteActionUrl}">

                <spring:bind path="id">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <label class="col-sm-2 control-label">ID</label>
                        <div class="col-sm-10">
                            <c:choose>
                                <c:when test="${create}">
                                    <form:input path="id" type="number" class="form-control " id="id" placeholder="ID" />
                                    <form:errors path="id" class="control-label" />
                                </c:when>
                                <c:otherwise>
                                    <form:input type="hidden" path="id" id="id"/>
                                    <form:input type="number" path="id" id="id" class="form-control input-sm" disabled="true"  placeholder="ID" />
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </spring:bind>

                <spring:bind path="name">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <label class="col-sm-2 control-label">Nome</label>
                        <div class="col-sm-10">
                            <form:input path="name" type="text" class="form-control " id="name" placeholder="Nome" />
                            <form:errors path="name" class="control-label" />
                        </div>
                    </div>
                </spring:bind>


                <spring:bind path="email">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <label class="col-sm-2 control-label">Email</label>
                        <div class="col-sm-10">
                            <form:input path="email" class="form-control" id="email" placeholder="Email" />
                            <form:errors path="email" class="control-label" />
                        </div>
                    </div>
                </spring:bind>

                <spring:bind path="address">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <label class="col-sm-2 control-label">Endereço</label>
                        <div class="col-sm-10">
                            <form:textarea path="address" rows="5" class="form-control" id="address" placeholder="Endereço" />
                            <form:errors path="address" class="control-label" />
                        </div>
                    </div>
                </spring:bind>


                <spring:bind path="sex">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <label class="col-sm-2 control-label">Sexo</label>
                        <div class="col-sm-10">
                            <label class="radio-inline"> <form:radiobutton path="sex" value="M" /> Masculino
                            </label> <label class="radio-inline"> <form:radiobutton path="sex" value="F" /> Feminino
                            </label> <br />
                            <form:errors path="sex" class="control-label" />
                        </div>
                    </div>
                </spring:bind>

                <spring:bind path="phone">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <label class="col-sm-2 control-label">Telefone</label>
                        <div class="col-sm-10">
                            <form:input path="phone" type="number" class="form-control " id="phone" placeholder="Telefone" />
                            <form:errors path="phone" class="control-label" />
                        </div>
                    </div>
                </spring:bind>


                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <c:choose>
                            <c:when test="${create}">
                                <button type="submit" class="btn-lg btn-primary pull-right">Adicionar</button>
                            </c:when>
                            <c:otherwise>
                                <button type="submit" class="btn-lg btn-primary pull-right">Alterar</button>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </form:form>

        </div>

        <jsp:include page="../fragments/footer.jsp" />

    </body>
</html>