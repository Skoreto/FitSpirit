<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/shared/layoutSidebarHeader.jsp" %>

<c:choose>
	<c:when test="${room['new']}"><c:set var="currentTitle" value="Nová místnost"/></c:when>
	<c:otherwise><c:set var="currentTitle" value="Úprava místnosti"/></c:otherwise>
</c:choose>

<header class="page-heading clearfix">
    <h1 class="heading-title pull-left">${pageTitle}</h1>
    <!-- ==== DROBECKOVA NAVIGACE ==== -->
    <div class="breadcrumbs pull-right">
		<ul class="breadcrumbs-list">
		 <li class="breadcrumbs-label">Nacházíte se zde:</li>
		 <li><a href="<spring:url value="/rooms/index" htmlEscape="true"/>">Místnosti</a><i class="fa fa-angle-right"></i></li>
		 <li class="current">${currentTitle}</li>
		</ul>             
    </div>
</header>
<div class="page-content">
    <div class="row page-row">
    	<div class="col-md-8 col-sm-7" id="dynamicContent">
		<!-- ==== HLAVNI OBSAH AKTUALNI STRANKY ==== -->

<c:choose>
	<c:when test="${room['new']}"><c:set var="method" value="post"/></c:when>
	<c:otherwise><c:set var="method" value="put"/></c:otherwise>
</c:choose>

<form:form class="form-horizontal" modelAttribute="room" method="${method}" enctype="multipart/form-data">
	<div class="form-group">
	    <label class="col-sm-2 control-label">Název místnosti <form:errors path="name" cssClass="errors"/></label>
	    <div class="col-sm-10">
	    	<form:input type="text" path="name" class="form-control" />
	    </div>
	</div>
	
	<div class="form-group">
        <label class="col-sm-2 control-label">Ilustrace místnosti</label>
        <div class="col-sm-10">
            <input type="file" name="file" />
        </div>
    </div>
		 
	<br/>
	<br/>
	
	<div class="form-group">
	    <div class="col-sm-offset-2 col-sm-10">
	    	<c:choose>
	    		<c:when test="${room['new']}">
	    			<button type="submit" class="btn btn-default">Vytvořit místnost</button>
	    		</c:when>
	    		<c:otherwise>
           	 		<button type="submit" class="btn btn-default">Upravit místnost</button>
          		</c:otherwise>
	    	</c:choose>       
	    </div>
	</div>
</form:form>

<%@ include file="/WEB-INF/jsp/shared/layoutSidebarFooter.jsp" %>