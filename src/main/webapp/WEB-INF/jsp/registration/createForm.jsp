<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/shared/layoutSidelessHeader.jsp" %>

<c:choose>
	<c:when test="${user['new']}"><c:set var="method" value="post"/></c:when>
	<c:otherwise><c:set var="method" value="put"/></c:otherwise>
</c:choose>

<div class="text-center">
    <h2>Registrace</h2>  
</div>

<form:form class="form-horizontal" modelAttribute="user" method="${method}" enctype="multipart/form-data">
    <div class="form-group margin-top">
        <label class="col-sm-3 control-label">Jméno <form:errors path="firstName" cssClass="errors"/></label>
        <div class="col-sm-6">
            <form:input type="text" path="firstName" class="form-control" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label">Příjmení <form:errors path="lastName" cssClass="errors"/></label>
        <div class="col-sm-6">
            <form:input type="text" path="lastName" class="form-control" />
        </div>
    </div>
    
    <div class="form-group">
        <label class="col-sm-3 control-label">Login <form:errors path="login" cssClass="errors"/></label>
        <div class="col-sm-6">
            <form:input type="text" path="login" class="form-control" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label">Heslo</label>
        <div class="col-sm-6">
            <form:input type="password" path="password" class="form-control" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label">Ulice <form:errors path="street" cssClass="errors"/></label>
        <div class="col-sm-6">
            <form:input type="text" path="street" class="form-control" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label">Město <form:errors path="city" cssClass="errors"/></label>
        <div class="col-sm-6">
            <form:input type="text" path="city" class="form-control" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label">PSČ <form:errors path="postcode" cssClass="errors"/></label>
        <div class="col-sm-6">
            <form:input type="text" path="postcode" class="form-control" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label">E-mail <form:errors path="mail" cssClass="errors"/></label>
        <div class="col-sm-6">
            <form:input type="email" path="mail" class="form-control" placeholder="jmeno@domena.cz" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label">Telefon <form:errors path="telephone" cssClass="errors"/></label>
        <div class="col-sm-6">
            <form:input type="text" path="telephone" class="form-control" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label">Foto</label>
        <div class="col-sm-6">
            <input type="file" name="file" />
            <span class="help-block">Doporučený rozměr 200x200px.</span>
        </div>
    </div>

    <div class="form-group">
        <div class="text-center">
            <button type="submit" class="btn btn-primary">Registrovat</button>
        </div>
    </div>
</form:form>

<%@ include file="/WEB-INF/jsp/shared/layoutSidelessFooter.jsp" %>