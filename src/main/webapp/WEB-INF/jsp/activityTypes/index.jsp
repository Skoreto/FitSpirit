<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/shared/layoutSidebarHeader.jsp" %>


<div class="page-row">
    <p>Naše studio je zaměřené pro klientky všech věkových kategorií a různé fyzické kondice. Z aktivit, které nabízíme si vybere opravdu každý!</p>
</div>
<div class="row page-row">
  <c:forEach var="room" items="${activityTypes.activityTypeList}">
        <div class="col-md-6 col-sm-6 col-xs-12 text-center">
            <div class="album-cover">
                    <a href="<spring:url value="/static/uploads/activityTypeImages/${activityType.illustrationImageName}" htmlEscape="true" />"><img class="img-responsive" src="<spring:url value="/static/uploads/activityTypeImages/${activityType.illustrationImageName}" htmlEscape="true" />" alt="Ilustrace ${activityType.name}" /></a>
                <div class="desc">
                    <h4><small><spring:url value="/static/uploads/activityTypeImages/${activityType.illustrationImageName}" htmlEscape="true" />" title="${activityType.name}">${activityType.name}</a></small></h4>                  
                	<a href="<spring:url value="/activityTypes/${activityType.id}/edit" htmlEscape="true"/>" class="btn btn-warning"><span class="glyphicon glyphicon-pencil"></span> Upravit</a>                                   
                    <a href="<spring:url value="/activityTypes/${activityType.id}/delete" htmlEscape="true"/>" class="btn btn-danger"><span class="glyphicon glyphicon-trash"></span> Zrušit</a>
                </div>
            </div>
        </div>
  </c:forEach>
</div>

<a href="<spring:url value="/activityTypes/create" htmlEscape="true"/>" class="btn btn-default pull-right">Přidat aktivitu</a>

<%@ include file="/WEB-INF/jsp/shared/layoutSidebarFooter.jsp" %>