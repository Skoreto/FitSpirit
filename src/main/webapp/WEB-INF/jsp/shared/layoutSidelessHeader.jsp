<!DOCTYPE html>
<html lang="cs">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Rezervační systém fiktivního FitClubu SPIRIT Poděbrady">
    <meta name="author" content="Tomáš Skořepa">
    <link rel="icon" href="static/images/favicon.ico">

    <title>${pageTitle} | FitSPIRIT</title>

    <!-- Globální Bootstrap CSS -->
    <link href="<spring:url value="/static/styles/bootstrap.min.css" htmlEscape="true" />" rel="stylesheet">
    <!-- Hlavní CSS -->
    <link href="<spring:url value="/static/styles/stylesTomSkoRed.css" htmlEscape="true" />" rel="stylesheet">
    <!-- Pluginy CSS => Font Awesome, FlexSlider, blueimp Gallery, BS 3 DatePicker od Eonasdan -->
    <link href="<spring:url value="/static/styles/font-awesome/css/font-awesome.min.css" htmlEscape="true" />" rel="stylesheet">
    <link href="<spring:url value="/static/styles/flexslider/flexslider.css" htmlEscape="true" />" rel="stylesheet">
    <link href="<spring:url value="/static/styles/blueimp-gallery/css/blueimp-gallery.min.css" htmlEscape="true" />" rel="stylesheet">
    <!-- Fonty Open Sans z CDN Google https://www.google.com/fonts#UsePlace:use/Collection:Open+Sans -->
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700&amp;subset=latin-ext" rel="stylesheet">
    <!-- HTML5Shiv a Respond.js - skripty pro aplikaci CSS na HTML5 elementy ve starÅ¡Ã­ch IE -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->   
    <style type="text/css">
		.avatar {
		    background-image: url(static/images/manSilhouette.png);
		}
	</style>
</head>
<body class="home-page">
    <div class="wrapper">
        <header class="header">
            <div class="header-main container">
                <h1 class="logo col-md-4 col-sm-4">
                    <a href="<spring:url value="/index" htmlEscape="true"/>"><img id="logo" src="<spring:url value="/static/images/logoFitSpirit.png" htmlEscape="true" />" alt="FitSPIRIT"></a>
                </h1>
                <div class="info col-md-8 col-sm-8">
                    <ul class="menu-top navbar-right hidden-xs">
						<c:choose>
							<c:when test="${empty loggedInUser}">
								<li class="divider"><a href="<spring:url value="/login" htmlEscape="true"/>"><i class="fa fa-sign-in"></i> Přihlásit se</a></li>
							</c:when>
						  	<c:otherwise>
		                        <li class="divider">Přihlášen jako ${loggedInUser.userRole.identificator}<a href="<spring:url value="/logout" htmlEscape="true"/>"><i class="fa fa-sign-in"></i> Odhlásit se</a></li>
    						</c:otherwise>
						</c:choose>             
                        <li class="divider"><a href="<spring:url value="/index" htmlEscape="true"/>">Úvod</a></li>
                        <li class="divider"><a href="#">FAQ</a></li>
                        <li class="divider"><a href="#">Ceník</a></li>
                        <li><a href="#">Kontakt</a></li>    
                    </ul>
                    <br />
                    <div class="contact pull-right">
                        <p class="phone"><i class="fa fa-phone"></i>Volejte na +420 325 626 700</p>
                        <p class="email"><i class="fa fa-envelope"></i><a href="mailto:obsluha@fitspirit.cz">obsluha@fitspirit.cz</a></p>
                    </div>
                </div>
            </div>
        </header>
        <!-- ==== NAVIGACE ==== -->
        <nav class="main-nav" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <button class="navbar-toggle" type="button" data-toggle="collapse" data-target="#navbar-collapse">
                        <span class="sr-only">Rozbalit navigaci</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>
                <div class="navbar-collapse collapse" id="navbar-collapse">
                    <ul class="nav navbar-nav">
                        <%@ include file="/WEB-INF/jsp/shared/navbar.jsp" %>                       
                    </ul>
                </div>
            </div>
        </nav>
        <!-- ==== NÁSLEDUJE OBSAH JEDNOTLIVÉ STRÁNKY ==== -->
        <div class="content container">
	       <!-- @RenderSection("FlexSlider", false) -->
	       <div class="page-wrapper">
	           <!--  @RenderSection("PageHeading", false) -->
	           <div class="page-content" id="dynamicContent">
				<!-- Hlavní tělo stránky, tady se v .NETu injektuje -->
