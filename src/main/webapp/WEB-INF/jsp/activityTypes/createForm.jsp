<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/shared/layoutSidebarHeader.jsp" %>

<c:choose>
	<c:when test="${activityType['new']}"><c:set var="method" value="post"/></c:when>
	<c:otherwise><c:set var="method" value="put"/></c:otherwise>
</c:choose>

<h2><c:if test="${activityType['new']}">Nová </c:if>aktivita:</h2>

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
	    	<form:input type="text" path="shortDescription" class="form-control" />
	    </div>
	</div>
    
	<div class="form-group">
	    <label class="col-sm-2 control-label">Detailní popis</label>
	    <div class="col-sm-10">
	    	<form:input type="text" path="description" class="form-control" />
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