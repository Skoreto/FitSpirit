<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/shared/layoutSidebarHeader.jsp" %>

<header class="page-heading clearfix">
    <h1 class="heading-title pull-left">${pageTitle}</h1>
    <!-- ==== DROBECKOVA NAVIGACE ==== -->
    <div class="breadcrumbs pull-right">
		<ul class="breadcrumbs-list">
		 <li class="breadcrumbs-label">Nacházíte se zde:</li>
		 <li><a href="<spring:url value="/staffs/index" htmlEscape="true"/>">Obsluha</a><i class="fa fa-angle-right"></i></li>
		 <li class="current">Detail obsluhy</li>
		</ul>             
    </div>
</header>
<div class="page-content">
    <div class="row page-row">
    	<div class="col-md-8 col-sm-7" id="dynamicContent">
		<!-- ==== HLAVNI OBSAH AKTUALNI STRANKY ==== -->

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
        </tbody>
    </table>
</div>

<div class="form-group margin-top">
    <div class="col-sm-offset-8 col-sm-2">
        <a href="<spring:url value="/staffs/index" htmlEscape="true"/>" class="btn btn-info"><span class="glyphicon glyphicon-chevron-left"></span> Obsluha</a>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/shared/layoutSidebarFooter.jsp" %>