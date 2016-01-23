<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/shared/layoutSidebarHeader.jsp" %>

<img src="<spring:url value="/static/uploads/activityTypeImages/${activityType.illustrationImageName}" htmlEscape="true" />" />

<p>Cena lekce: ${activityType.price} Kč</p>

<span>${activityType.description}</span>

<%@ include file="/WEB-INF/jsp/shared/layoutSidebarFooter.jsp" %>