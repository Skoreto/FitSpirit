<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/shared/layoutSidebarHeader.jsp" %>

<header class="page-heading clearfix">
    <h1 class="heading-title pull-left">${pageTitle}</h1>
    <!-- ==== DROBECKOVA NAVIGACE ==== -->
    <div class="breadcrumbs pull-right">
		<ul class="breadcrumbs-list">
		 <li class="breadcrumbs-label">Nacházíte se zde:</li>
		 <li><a href="<spring:url value="/activityTypes/index" htmlEscape="true"/>">Aktivity</a><i class="fa fa-angle-right"></i></li>
		 <li class="current">Detail aktivity</li>
		</ul>             
    </div>
</header>
<div class="page-content">
    <div class="row page-row">
    	<div class="col-md-8 col-sm-7" id="dynamicContent">
		<!-- ==== HLAVNI OBSAH AKTUALNI STRANKY ==== -->

<img src="<spring:url value="/static/uploads/activityTypeImages/${activityType.illustrationImageName}" htmlEscape="true" />" />

<br />
<br />

<p>Cena lekce: ${activityType.price} Kč</p>

<span>${activityType.description}</span>

<%@ include file="/WEB-INF/jsp/shared/layoutSidebarFooter.jsp" %>