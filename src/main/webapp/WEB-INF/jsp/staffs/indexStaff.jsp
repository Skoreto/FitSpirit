<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/shared/layoutSidebarHeader.jsp" %>

<header class="page-heading clearfix">
    <h1 class="heading-title pull-left">${pageTitle}</h1>
    <!-- ==== DROBECKOVA NAVIGACE ==== -->
    <div class="breadcrumbs pull-right">           
    </div>
</header>
<div class="page-content">
    <div class="row page-row">
    	<div class="col-md-8 col-sm-7" id="dynamicContent">
		<!-- ==== HLAVNI OBSAH AKTUALNI STRANKY ==== -->

<table class="table table-hover table-condensed table-boxed">
    <thead>
        <tr>
            <th>Id</th>
            <th>Jméno</th>
            <th>Aktivní</th>
            <th><span hidden="hidden">Akce</span></th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="staff" items="${staffs.userList}">
            <tr>
                <td>${staff.id}</td>
                <td><a href="<spring:url value="/staffs/${staff.id}" htmlEscape="true" />">${staff.firstName} ${staff.lastName}</a></td>
                <c:choose>
					<c:when test="${staff.active}">
	                    <td><span class="label label-success"><i class="fa fa-check"> ano</i></span></td>
	                </c:when>
	                <c:otherwise>
	                    <td><span class="label label-danger"><i class="fa fa-close"> ne</i></span></td>
                	</c:otherwise>
				</c:choose>
                <td>
                    <div class="btn-group">
                        <a href="<spring:url value="/staffs/${staff.id}" htmlEscape="true" />" class="btn btn-info"><span class="glyphicon glyphicon-file"></span> Detail</a>
                        <button data-toggle="dropdown" class="btn btn-info dropdown-toggle"><span class="caret"></span></button>
                        <ul class="dropdown-menu">
                            <li><a href="<spring:url value="/staffs/${staff.id}/edit" htmlEscape="true" />"><span class="glyphicon glyphicon-pencil"></span> Upravit</a></li>
                            <li><a href="<spring:url value="/staffs/${staff.id}/delete" htmlEscape="true" />"><span class="glyphicon glyphicon-trash"></span> Smazat</a></li>
                        </ul>
                    </div>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<a href="<spring:url value="/staffs/create" htmlEscape="true"/>" class="btn btn-default pull-right">Přidat obsluhu</a>

<%@ include file="/WEB-INF/jsp/shared/layoutSidebarFooter.jsp" %>