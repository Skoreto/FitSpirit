<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/shared/layoutSidebarHeader.jsp" %>

<c:choose>
	<c:when test="${room['new']}"><c:set var="method" value="post"/></c:when>
	<c:otherwise><c:set var="method" value="put"/></c:otherwise>
</c:choose>

<h2><c:if test="${room['new']}">Nová </c:if>místnost:</h2>

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
            <input type="file" name="file"/>
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