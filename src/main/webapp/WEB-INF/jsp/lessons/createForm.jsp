<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/shared/layoutSidebarHeader.jsp" %>

<header class="page-heading clearfix">
    <h1 class="heading-title pull-left">${pageTitle}</h1>
    <!-- ==== DROBECKOVA NAVIGACE ==== -->
    <div class="breadcrumbs pull-right">
		<ul class="breadcrumbs-list">
		 <li class="breadcrumbs-label">Nacházíte se zde:</li>
		 <li><a href="<spring:url value="/lessons/index" htmlEscape="true"/>">Lekce</a><i class="fa fa-angle-right"></i></li>
		 <li class="current">Nová lekce</li>
		</ul>             
    </div>
</header>
<div class="page-content">
    <div class="row page-row">
    	<div class="col-md-8 col-sm-7" id="dynamicContent">
		<!-- ==== HLAVNI OBSAH AKTUALNI STRANKY ==== -->

<c:choose>
	<c:when test="${lesson['new']}"><c:set var="method" value="post"/></c:when>
	<c:otherwise><c:set var="method" value="put"/></c:otherwise>
</c:choose>

<form:form class="form-horizontal" modelAttribute="lesson" method="${method}">

	<div class="form-group">
        <label class="col-sm-2 control-label">Čas zahájení</label>
        <div class="col-sm-10">
            <div class="input-group date" id='datetimepicker1'>
                <!-- Suplováno validací HTML5 -->
                <input type="datetime" name="startTime" class="form-control" required title="Zadejte čas ve formátu dd.MM.rrrr H:mm."/>
                <span class="input-group-addon">
                    <span class="glyphicon glyphicon-calendar"></span>
                </span>              
            </div>           
        </div>
    </div>
    
    <div class="form-group">
        <label class="col-sm-2 control-label">Čas ukončení</label>
        <div class="col-sm-10">
            <div class="input-group date" id='datetimepicker2'>
                <!-- Suplováno validací HTML5 -->
                <input type="datetime" name="endTime" class="form-control" required="required" title="Zadejte čas ve formátu dd.MM.rrrr H:mm." />
                <span class="input-group-addon">
                    <span class="glyphicon glyphicon-calendar"></span>
                </span>
            </div>
        </div>
    </div> 
    
    <div class="form-group">
        <label class="col-sm-2 control-label">Aktivita</label>
        <div class="col-sm-10">
        	<form:select path="activityType" class="form-control" >
        		<form:options items="${activityTypes}" itemLabel="name" itemValue="id" />
        	</form:select>
        
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-2 control-label">Místo konání</label>
        <div class="col-sm-10">
            <form:select path="room" class="form-control" >
        		<form:options items="${rooms}" itemLabel="name" itemValue="id" />
        	</form:select>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-2 control-label">Kapacita</label>
        <div class="col-sm-10">
            <form:input type="text" path="originalCapacity" class="form-control" />
        </div>
    </div>
    
    <div class="form-group">
        <label class="col-sm-2 control-label">Instruktor</label>
        <div class="col-sm-10">
            <input name="instructor" type="text" class="form-control" value="${loggedInUser.firstName} ${loggedInUser.lastName} (ID ${loggedInUser.id})" disabled="disabled">
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-2 control-label">Poznámka</label>
        <div class="col-sm-10">
            <form:input type="text" path="description" class="form-control tinymce" />
        </div>
    </div>
    
    <div class="form-group margin-top">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-success pull-right margin-left"><span class="glyphicon glyphicon-ok"></span> Vytvořit</button>
            <button type="reset" class="btn btn-default pull-right margin-left"><span class="glyphicon glyphicon-repeat"></span> Reset</button>
            <a href="<spring:url value="/lessons/index" htmlEscape="true"/>" class="btn btn-danger pull-right"><span class="glyphicon glyphicon-remove"></span> Zrušit</a>
        </div>
    </div>
</form:form>

<%@ include file="/WEB-INF/jsp/shared/layoutSidebarFooter.jsp" %>