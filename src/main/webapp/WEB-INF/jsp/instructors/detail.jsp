<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/shared/layoutSidebarHeader.jsp" %>

<header class="page-heading clearfix">
    <h1 class="heading-title pull-left">${pageTitle}</h1>
    <!-- ==== DROBECKOVA NAVIGACE ==== -->
    <div class="breadcrumbs pull-right">
		<ul class="breadcrumbs-list">
		 <li class="breadcrumbs-label">Nacházíte se zde:</li>
		 <li><a href="<spring:url value="/instructors/index" htmlEscape="true"/>">Instruktoři</a><i class="fa fa-angle-right"></i></li>
		 <li class="current">Detail instruktora</li>
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
                <td>Jméno</td>
                <td>${user.firstName}</td>
            </tr>
            <tr>
                <td>Příjmení</td>
                <td>${user.lastName}</td>
            </tr>
        </tbody>
    </table>
</div>

<div class="form-group col-sm-offset-2 col-sm-8">
    ${user.description}
</div>

<div class="form-group margin-top">
    <div class="col-sm-offset-8 col-sm-2">
        <a href="<spring:url value="/instructors/index" htmlEscape="true"/>" class="btn btn-info"><span class="glyphicon glyphicon-chevron-left"></span> Instruktoři</a>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/shared/layoutSidebarFooter.jsp" %>