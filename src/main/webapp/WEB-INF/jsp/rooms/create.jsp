<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/shared/layoutSidebarHeader.jsp" %>

<form class="form-horizontal" enctype ="multipart/form-data">
	<div class="form-group">
	    <label class="col-sm-2 control-label">Název místnosti</label>
	    <div class="col-sm-10">
	    	<input type="text" name="roomName" class="form-control" />
	    </div>
	</div>
	
	<div class="form-group">
	    <label class="col-sm-2 control-label">Ilustrace místnosti</label>
	    <div class="col-sm-10">
	        <input type="file" name="roomImage"/>
	    </div>
	</div>
	
	<div class="form-group">
	    <label class="col-sm-2 control-label">Krátký popis</label>
	    <div class="col-sm-10">
	        <input type="text" name="roomDescription" class="form-control tinymce" />
	    </div>
	</div>    
	
	<br/>
	<br/>
	
	<div class="form-group">
	    <div class="col-sm-offset-2 col-sm-10">
	        <button type="submit" class="btn btn-default">Vytvořit místnost</button>
	    </div>
	</div>
</form>


<%@ include file="/WEB-INF/jsp/shared/layoutSidebarFooter.jsp" %>