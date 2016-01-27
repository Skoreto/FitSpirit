<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/admin/shared/layoutSidebarHeader.jsp" %>

<div class="row">
    <div class="col-lg-6">
        <form:form>
            <div class="input-group">
                <input class="form-control pull-left" type="text" placeholder="Vyhledat klienta" name="phrase" id="searchBox"/>
                <span class="input-group-btn">
                    <button type="submit" class="btn btn-theme"><i class="fa fa-search"></i></button>
                </span>
            </div>
        </form:form>
    </div>
</div>

<br/>

<table class="table table-hover table-condensed table-boxed">
    <thead>
    <tr>
        <th>#</th>
        <th>Jméno</th>
        <th>Kredit</th>
        <th>Aktivní</th>
        <th><span hidden="hidden">Akce</span></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${users.userList}">
        <tr class="${user.active ? "active" : "row-disabled"}">
            <td>${user.id}</td>
            <td><a href="<spring:url value="/admin/clients/${user.id}" htmlEscape="true" />">${user.firstName} ${user.lastName}</a></td>
            <td>${user.credit} Kč</td>          
            <c:choose>
				<c:when test="${user.active}">
					<td><span class="label label-success"><i class="fa fa-check"> ano</i></span></td>
	                <td>
	                    <div class="btn-group">
	                        <a href="<spring:url value="/admin/clients/${user.id}" htmlEscape="true" />" class="btn btn-info"><span class="glyphicon glyphicon-file"></span> Detail</a>
	                        <button data-toggle="dropdown" class="btn btn-info dropdown-toggle"><span class="caret"></span></button>
	                        <ul class="dropdown-menu">
	                            <li><a href="<spring:url value="/admin/clients/${user.id}/edit" htmlEscape="true" />"><span class="glyphicon glyphicon-pencil"></span> Upravit</a></li>
	                        </ul>
	                    </div>
	                    <a href="<spring:url value="/admin/clients/${user.id}/creditAdministration" htmlEscape="true" />" class="btn btn-warning"><span class="glyphicon glyphicon-plus"></span> Kredit</a>
	                </td>
				</c:when>
				<c:otherwise>
					<td><span class="label label-danger"><i class="fa fa-close"> ne</i></span></td>
                <td>
                    <div class="btn-group">
                        <a href="<spring:url value="/admin/clients/${user.id}" htmlEscape="true" />" class="btn btn-info"><span class="glyphicon glyphicon-file"></span> Detail</a>
                        <button data-toggle="dropdown" class="btn btn-info dropdown-toggle"><span class="caret"></span></button>
                        <ul class="dropdown-menu">
                            <li><a href="<spring:url value="/admin/clients/${user.id}/edit" htmlEscape="true" />"><span class="glyphicon glyphicon-pencil"></span> Upravit</a></li>
                            <li><a href="<spring:url value="/admin/clients/${user.id}/delete" htmlEscape="true" />"><span class="glyphicon glyphicon-trash"></span> Smazat</a></li>
                        </ul>
                    </div>
                    <a href="<spring:url value="/clients/${user.id}/activate" htmlEscape="true" />" class="btn btn-success"><span class="glyphicon glyphicon-ok"></span> Aktivovat</a>
                </td>			
				</c:otherwise>
			</c:choose>
        </tr>
    </c:forEach>
    </tbody>
</table>

<%@ include file="/WEB-INF/jsp/admin/shared/layoutSidebarFooter.jsp" %>