<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/shared/layoutSidebarHeader.jsp" %>

<c:choose>
	<c:when test="${activityType['new']}"><c:set var="currentTitle" value="Nová aktivita"/></c:when>
	<c:otherwise><c:set var="currentTitle" value="Úprava aktivity"/></c:otherwise>
</c:choose>

<header class="page-heading clearfix">
    <h1 class="heading-title pull-left">${pageTitle}</h1>
    <!-- ==== DROBECKOVA NAVIGACE ==== -->
    <div class="breadcrumbs pull-right">
		<ul class="breadcrumbs-list">
		 <li class="breadcrumbs-label">Nacházíte se zde:</li>
		 <li><a href="<spring:url value="/activityTypes/index" htmlEscape="true"/>">Aktivity</a><i class="fa fa-angle-right"></i></li>
		 <li class="current">${currentTitle}</li>
		</ul>             
    </div>
</header>
<div class="page-content">
    <div class="row page-row">
    	<div class="col-md-8 col-sm-7" id="dynamicContent">
		<!-- ==== HLAVNI OBSAH AKTUALNI STRANKY ==== -->

<c:choose>
	<c:when test="${activityType['new']}"><c:set var="method" value="post"/></c:when>
	<c:otherwise><c:set var="method" value="put"/></c:otherwise>
</c:choose>

<form:form class="form-horizontal" modelAttribute="activityType" method="${method}" enctype="multipart/form-data">
	<div class="form-group">
	    <label class="col-sm-2 control-label">Název aktivity <form:errors path="name" cssClass="errors"/></label>
	    <div class="col-sm-10">
	    	<form:input type="text" path="name" class="form-control" />
	    </div>
	</div>
	
	<div class="form-group">
        <label class="col-sm-2 control-label">Cena lekce</label>
        <div class="col-sm-10 input-group">
			<form:input type="text" path="price" class="form-control" />
            <span class="input-group-addon">Kč</span>
        </div>
    </div>
	
	<div class="form-group">
        <label class="col-sm-2 control-label">Ilustrace aktivity</label>
        <div class="col-sm-10">
            <input type="file" name="file" />
        </div>
    </div>
    
	<div class="form-group">
	    <label class="col-sm-2 control-label">Krátký popis</label>
	    <div class="col-sm-10">
	    	<form:input type="text" path="shortDescription" class="form-control tinymce" />
	    </div>
	</div>
    
	<div class="form-group">
	    <label class="col-sm-2 control-label">Detailní popis</label>
	    <div class="col-sm-10">
	    	<form:input type="text" path="description" class="form-control tinymce" />
	    </div>
	</div>
		 
	<br/>
	<br/>
	
	<div class="form-group">
	    <div class="col-sm-offset-2 col-sm-10">
	    	<c:choose>
	    		<c:when test="${activityType['new']}">
	    			<button type="submit" class="btn btn-default">Vytvořit aktivitu</button>
	    		</c:when>
	    		<c:otherwise>
           	 		<button type="submit" class="btn btn-default">Upravit aktivitu</button>
          		</c:otherwise>
	    	</c:choose>       
	    </div>
	</div>
</form:form>

<%@ include file="/WEB-INF/jsp/shared/layoutSidebarFooter.jsp" %>