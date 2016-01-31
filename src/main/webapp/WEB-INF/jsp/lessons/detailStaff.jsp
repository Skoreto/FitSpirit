<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/shared/layoutSidebarHeader.jsp" %>

<header class="page-heading clearfix">
    <h1 class="heading-title pull-left">${pageTitle}</h1>
    <!-- ==== DROBECKOVA NAVIGACE ==== -->
    <div class="breadcrumbs pull-right">
		<ul class="breadcrumbs-list">
		 <li class="breadcrumbs-label">Nacházíte se zde:</li>
		 <li><a href="<spring:url value="/lessons/index" htmlEscape="true"/>">Lekce</a><i class="fa fa-angle-right"></i></li>
		 <li class="current">Detail lekce</li>
		</ul>             
    </div>
</header>
<div class="page-content">
    <div class="row page-row">
    	<div class="col-md-8 col-sm-7" id="dynamicContent">
		<!-- ==== HLAVNI OBSAH AKTUALNI STRANKY ==== -->

<br />

<div class="panel panel-info">
    <div class="panel-heading">Detail lekce ${lesson.activityType.name}</div>
    <table class="table table-bordered table-hover table-condensed">
        <thead>

        </thead>
        <tbody>
            <tr>
                <td>Id</td>
                <td>${lesson.id}</td>
            </tr>
            <tr>
                <td>Čas zahájení</td>
                <td><fmt:formatDate value="${lesson.startTime}" pattern="EEEE d.M.yyyy H:mm" /></td>
            </tr>
            <tr>
                <td>Čas ukončení</td>
                <td><fmt:formatDate value="${lesson.endTime}" pattern="EEEE d.M.yyyy H:mm" /></td>
            </tr>
            <tr>
                <td>Aktivita</td>
                <td>${lesson.activityType.name}</td>
            </tr>
            <tr>
                <td>Místnost</td>
                <td>${lesson.room.name}</td>
            </tr>
            <tr>
                <td>Kapacita</td>
                <td>${lesson.actualCapacity}/${lesson.originalCapacity}</td>
            </tr>
            <tr>
                <td>Instruktor</td>
                <td>${lesson.instructor.firstName} ${lesson.instructor.lastName}</td>
            </tr>
        </tbody>
    </table>
</div>

${lesson.description}
    
<br />

<div class="panel panel-warning">
    <div class="panel-heading">Registrovaní klienti</div>
    <ul class="list-group">
        <c:forEach var="user" items="${users.userList}">
            <li class="list-group-item"><span class="badge">ID ${user.id}</span>${user.firstName} ${user.lastName}</li>
        </c:forEach>
    </ul>
</div>

<br />
<br />

<a href="<spring:url value="/lessons/index" htmlEscape="true" />" class="btn btn-info pull-right"><span class="glyphicon glyphicon-triangle-left"></span> Zpět</a>

<%@ include file="/WEB-INF/jsp/shared/layoutSidebarFooter.jsp" %>