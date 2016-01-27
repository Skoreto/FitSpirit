<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/admin/shared/layoutSidebarHeader.jsp" %>

<div class="form-group margin-bottom text-center">
	<img src="<spring:url value="/static/uploads/userImages/${user.profilePhotoName}" htmlEscape="true" />" class="img-thumbnail" />
</div>

<div class="form-group text-center col-sm-offset-2 col-sm-8">
    <table class="table table-bordered table-hover table-condensed text-left">
        <thead>

        </thead>
        <tbody>
        	<tr>
                <td>Login</td>
                <td>${user.login}</td>
            </tr>
            <tr>
                <td>Jméno</td>
                <td>${user.firstName}</td>
            </tr>
            <tr>
                <td>Příjmení</td>
                <td>${user.lastName}</td>
            </tr>
             <tr>
                <td>Ulice</td>
                <td>${user.street}</td>
            </tr>
            <tr>
                <td>Město</td>
                <td>${user.city}</td>
            </tr>
            <tr>
                <td>PSČ</td>
                <td>${user.postcode}</td>
            </tr>
            <tr>
                <td>E-mail</td>
                <td>${user.mail}</td>
            </tr>
            <tr>
                <td>Telefon</td>
                <td>${user.telephone}</td>
            </tr>
            <tr>
                <td>Kredit</td>
                <td>${user.credit} Kč</td>
            </tr>
        </tbody>
    </table>
</div>

<div class="form-group margin-top">
    <div class="col-sm-offset-8 col-sm-2">
        <a href="<spring:url value="/admin/clients/indexStaff" htmlEscape="true"/>" class="btn btn-info"><span class="glyphicon glyphicon-chevron-left"></span> Klienti</a>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/admin/shared/layoutSidebarFooter.jsp" %>