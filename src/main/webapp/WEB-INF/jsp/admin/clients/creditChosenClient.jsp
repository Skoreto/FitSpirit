<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/admin/shared/layoutSidebarHeader.jsp" %>

<c:choose>
	<c:when test="${user['new']}"><c:set var="method" value="post"/></c:when>
	<c:otherwise><c:set var="method" value="put"/></c:otherwise>
</c:choose>

<form:form class="form-horizontal" modelAttribute="user" method="${method}">   
    <div class="form-group">
        <label class="control-label col-xs-2">Klient</label>
        <div class="col-xs-10">
            <input type="text" class="form-control" value="${user.firstName} ${user.lastName} (ID ${user.id})" disabled="disabled">
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-2">Kredit</label>
        <div class="col-sm-10">
            <div class="input-group">
                <input type="text" name="addedCredit" value="0.0" class="form-control" />
                <span class="input-group-addon">Kč</span>
            </div>
        </div>
    </div>

    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-warning pull-right margin-left"><span class="glyphicon glyphicon-plus"></span> Připsat kredit</button>
            <a href="<spring:url value="/admin/clients/indexStaff" htmlEscape="true"/>" class="btn btn-danger pull-right"><span class="glyphicon glyphicon-remove"></span> Zrušit</a>
        </div>
    </div>
</form:form>

<%@ include file="/WEB-INF/jsp/admin/shared/layoutSidebarFooter.jsp" %>