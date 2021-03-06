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

<div id="blueimp-gallery" class="blueimp-gallery">
    <div class="slides"></div>
    <h3 class="title"></h3>
    <a class="prev">‹</a>
    <a class="next">›</a>
    <a class="close">×</a>
    <a class="play-pause"></a>
    <ol class="indicator"></ol>
</div>

<div class="page-row">
    <p>Chceme, abyste se u nás cítily příjemně a tomu jsme podřídili zbudování celého studia. Pro maminky s dětmi máme připravený dětský koutek se spoustou hraček.</p>
</div>
<div class="row page-row">
  <c:forEach var="room" items="${rooms.roomList}">
        <div class="col-md-6 col-sm-6 col-xs-12 text-center">
            <div class="album-cover rooms-client">
                    <a href="<spring:url value="/static/uploads/roomImages/${room.illustrationImageName}" htmlEscape="true" />" data-gallery><img class="img-responsive" src="<spring:url value="/static/uploads/roomImages/${room.illustrationImageName}" htmlEscape="true" />" alt="Ilustrace ${room.name}" /></a>
                <div class="desc">
                    <h4><small><a href="#" title="${room.name}">${room.name}</a></small></h4>                  
                </div>
            </div>
        </div>
  </c:forEach>
</div>

<%@ include file="/WEB-INF/jsp/shared/layoutSidebarFooter.jsp" %>