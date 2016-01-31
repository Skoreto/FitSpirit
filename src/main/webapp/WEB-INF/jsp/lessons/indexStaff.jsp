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
            <th>Datum</th>
            <th>Čas</th>
            <th>Aktivita</th>
            <th>Místnost</th>
            <th>Kapacita</th>
            <th>Instruktor</th>
            <th><span hidden="hidden">Akce</span></th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="lesson" items="${lessons.lessonList}">
            <tr class="${lesson.active ? "" : "row-disabled"}">
                <td><fmt:formatDate value="${lesson.startTime}" pattern="E d.M." /></td>
                <td><fmt:formatDate value="${lesson.startTime}" pattern="H:mm" /> - <fmt:formatDate value="${lesson.endTime}" pattern="H:mm" /></td>
                <td><a href="<spring:url value="/activityTypes/${lesson.activityType.id}" htmlEscape="true" />">${lesson.activityType.name}</a></td>
                <td>${lesson.room.name}</td>
                <td>${lesson.actualCapacity}/${lesson.originalCapacity}</td>
                <td>${lesson.instructor.firstName.substring(0, 1)}. ${lesson.instructor.lastName}</td>
                <td>
                    <c:choose>
						<c:when test="${lesson.active}">
	                        <c:choose>
								<c:when test="${lesson.actualCapacity == lesson.originalCapacity}">
		                            <div class="btn-group">
		                                <a href="<spring:url value="/lessons/${lesson.id}" htmlEscape="true" />" class="btn btn-info"><span class="glyphicon glyphicon-file"></span> Detail</a>
		                                <button data-toggle="dropdown" class="btn btn-info dropdown-toggle"><span class="caret"></span></button>
		                                <ul class="dropdown-menu">
		                                	<li><a href="<spring:url value="/lessons/${lesson.id}/edit" htmlEscape="true" />"><span class="glyphicon glyphicon-pencil"></span> Upravit</a></li>
		                                    <li><a href="<spring:url value="/lessons/${lesson.id}/delete" htmlEscape="true" />"><span class="glyphicon glyphicon-trash"></span> Smazat</a></li>
		                                </ul>
		                            </div>
		                        </c:when>
		                        <c:otherwise>
		                        	<a href="<spring:url value="/lessons/${lesson.id}" htmlEscape="true" />" class="btn btn-info"><span class="glyphicon glyphicon-file"></span> Detail</a>
		                        </c:otherwise>
		                	</c:choose>
	                    </c:when>
	                    <c:otherwise>
	                    	<a href="<spring:url value="/lessons/${lesson.id}" htmlEscape="true" />" class="btn btn-warning"><span class="glyphicon glyphicon-file"></span> Detail</a>
	                    </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<%@ include file="/WEB-INF/jsp/shared/layoutSidebarFooter.jsp" %>