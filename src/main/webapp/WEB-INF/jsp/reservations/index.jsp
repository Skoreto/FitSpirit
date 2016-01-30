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
        <th>Čas rezervace</th>
        <th>Stav</th>
        <th><span hidden="hidden">Akce</span></th>
    </tr>
    </thead>
    <tbody>
	    <c:forEach var="reservation" items="${reservations.reservationList}"> 
	        <c:choose>
				<c:when test="${reservation.lesson.active}">
		            <c:choose>
						<c:when test="${reservation.cancellable}">
				            <tr>
				                <td><fmt:formatDate value="${reservation.lesson.startTime}" pattern="EEEE d.M." /></td>
				                <td><fmt:formatDate value="${reservation.lesson.startTime}" pattern="H:mm" /> - <fmt:formatDate value="${reservation.lesson.endTime}" pattern="H:mm" /></td> 
				                <td><a href="<spring:url value="/activityTypes/${reservation.lesson.activityType.id}" htmlEscape="true" />">${reservation.lesson.activityType.name}</a></td>
				                <td><fmt:formatDate value="${reservation.reservationTime}" pattern="E d.M. H:mm" /></td>
				                <td>
				                    <span class="label label-success"><i class="fa fa-check-square-o"> rezervováno</i></span>
				                </td>
				                <td>
				                    <a href="<spring:url value="/lessons/${reservation.lesson.id}" htmlEscape="true" />" class="btn btn-info"><span class="glyphicon glyphicon-trash"></span> Detail</a>
				                    <a href="<spring:url value="/reservations/${reservation.id}/cancel" htmlEscape="true" />" class="btn btn-danger"><span class="glyphicon glyphicon-trash"></span> Zrušit</a>
				                </td>
				            </tr>
			            </c:when> 
			            <c:otherwise>
				            <tr>
				                <td><fmt:formatDate value="${reservation.lesson.startTime}" pattern="EEEE d.M." /></td>
				                <td><fmt:formatDate value="${reservation.lesson.startTime}" pattern="H:mm" /> - <fmt:formatDate value="${reservation.lesson.endTime}" pattern="H:mm" /></td> 
				                <td><a href="<spring:url value="/activityTypes/${reservation.lesson.activityType.id}" htmlEscape="true" />">${reservation.lesson.activityType.name}</a></td>
				                <td><fmt:formatDate value="${reservation.reservationTime}" pattern="E d.M. H:mm" /></td>
				                <td>
				                    <span class="label label-danger"><i class="fa fa-check-square-o"> upsáno</i></span>
				                </td>
				                <td>
				                    <a href="<spring:url value="/lessons/${reservation.lesson.id}" htmlEscape="true" />" class="btn btn-info"><span class="glyphicon glyphicon-trash"></span> Detail</a>
				                </td>
				            </tr>
			            </c:otherwise>
					</c:choose> 
		        </c:when> 
		        <c:otherwise>
		            <tr class="row-disabled">
		                <td><fmt:formatDate value="${reservation.lesson.startTime}" pattern="EEEE d.M." /></td>
		                <td><fmt:formatDate value="${reservation.lesson.startTime}" pattern="H:mm" /> - <fmt:formatDate value="${reservation.lesson.endTime}" pattern="H:mm" /></td>
		                <td><a href="<spring:url value="/activityTypes/${reservation.lesson.activityType.id}" htmlEscape="true" />">${reservation.lesson.activityType.name}</a></td>
		                <td><fmt:formatDate value="${reservation.reservationTime}" pattern="E d.M. H:mm" /></td>
		                <td>
		                    <span class="label label-warning"><i class="fa fa-clock-o"> uplynulé</i></span>
		                </td>
		                <td>
		                    <a href="<spring:url value="/lessons/${reservation.lesson.id}" htmlEscape="true" />" class="btn btn-info"><span class="glyphicon glyphicon-trash"></span> Detail</a>
		                </td>
		            </tr>
		        </c:otherwise>
			</c:choose>
	    </c:forEach>
    </tbody>
</table>

<%@ include file="/WEB-INF/jsp/shared/layoutSidebarFooter.jsp" %>