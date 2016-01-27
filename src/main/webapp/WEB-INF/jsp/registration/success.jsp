<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/shared/layoutSidelessHeader.jsp" %>

<style type="text/css">
    .avatar {
        background-image: url(../../static/uploads/userImages/${user.profilePhotoName});
    }
</style>

<div class="text-center">
    <h2>Registrace dokončena!</h2>
</div>

<div class="success-container">
    <div class="avatar"></div>
    <div class="form-box">
        <span>
            ${user.firstName} ${user.lastName}<br/><br/>
            login: ${user.login}
        </span>          
    </div>
</div>

<div class="text-center">
    <span>Váš účet bude plně aktivován při první návštěvě fitness centra.</span> 
</div>

<div class="form-group margin-top margin-bottom">
    <div class="text-center">
        <a href="<spring:url value="/index" htmlEscape="true"/>" class="btn btn-info">Pokračovat <span class="glyphicon glyphicon-chevron-right"></span></a>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/shared/layoutSidelessFooter.jsp" %>