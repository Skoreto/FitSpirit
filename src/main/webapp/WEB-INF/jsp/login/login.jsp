<%@ include file="/WEB-INF/jsp/includes.jsp" %>

<!DOCTYPE html>
<html lang="cs">
<head>
    <meta charset="utf-8">
    <title>Přihlašovací obrazovka</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="<spring:url value="/static/styles/bootstrap.min.css" htmlEscape="true" />" rel="stylesheet" id="bootstrap-css">
    <link href="<spring:url value="/static/styles/login/loginPage.css" htmlEscape="true" />" rel="stylesheet">
</head>
<body>
<div class="container">     
    <div class="login-container">
        <h1 hidden="hidden">Přihlášení</h1>
        <div class="avatar"></div>
        <div><p><strong><c:out value="${message}" /></strong></p></div>
            <form:form class="form-signin" method="POST">
                <div class="form-box">
                    <input name="login" type="text" class="form-control" placeholder="login">
                    <input type="password" name="password" class="form-control" placeholder="heslo" value="h">
                    <button class="btn btn-theme btn-block login" type="submit" onclick=" getAjaxContent() " id="submitButton" data-loading-text="Ověřuji...">Přihlásit</button>
                </div>
            </form:form>
        </div>   
</div>

<script src="<spring:url value="/static/scripts/jquery-2.1.3.min.js" htmlEscape="true" />"></script>
<script src="<spring:url value="/static/scripts/bootstrap.min.js" htmlEscape="true" />"></script>

<script type="text/javascript">
    // Obsluha loadingu "Ověřuji..."
    $('#submitButton').on('click', function () {
        var $btn = $(this).button('loading');
    })
</script>

</body>
</html>