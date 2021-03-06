<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/shared/layoutSidelessHeader.jsp" %>

<div id="fb-root"></div>
<script>
    (function (d, s, id) {
        var js, fjs = d.getElementsByTagName(s)[0];
        if (d.getElementById(id)) return;
        js = d.createElement(s); js.id = id;
        js.src = "//connect.facebook.net/cs_CZ/sdk.js#xfbml=1&version=v2.3";
        fjs.parentNode.insertBefore(js, fjs);
    }(document, 'script', 'facebook-jssdk'));
</script>


<div id="promo-slider" class="slider flexslider">
    <ul class="slides">
        <li>
            <img src="static/images/flexslider/carousel1.png" alt="" />
            <p class="flex-caption">
                <span class="main">Buď fit s FitSpirit</span>
                <br />
                <span class="secondary clearfix">Skupinová cvičení pro všechny věkové kategorie</span>
            </p>
        </li>
        <li>
            <img src="static/images/flexslider/carousel3.png" alt="" />
            <p class="flex-caption">
                <span class="main">Svěř se do rukou profesionálů</span>
                <br />
                <span class="secondary clearfix">Náš tým zkušených instruktorů tě vrátí do kondice</span>
            </p>
        </li>
        <li>
            <img src="static/images/flexslider/carousel2.png" alt="" />
            <p class="flex-caption">
                <span class="main">TRX</span>
                <br />
                <span class="secondary clearfix">Nejsme jen činkárna - vyzkoušej trénink TRX</span>
            </p>
        </li>
        <li>
            <img src="static/images/flexslider/kettlebelly.png" alt="Kettlebelly" />
            <p class="flex-caption">
                <span class="main">Kettlebell</span>
                <br />
                <span class="secondary clearfix">Nově skupinová cvičení s kettlebelly</span>
            </p>
        </li>
    </ul>
</div>


<section class="promo box box-dark">
    <div class="col-md-9">
        <h1 class="section-heading">Proč FitClub SPIRIT</h1>
        <p>Chceme Ti pomoci na cestě za tvými cíli. Jsme profesionálové, náš čas věnujeme jen těm, kteří to myslí vážně. Hledáš snadnou cestu "k cíli"? Přestaň věřit na pohádky! Jsme tu pro každého, kdo chce být opravdu v kondici a zdravý. Kdo chápe, že k tomu je třeba trénink.</p>
    </div>
    <div class="col-md-3">
        <a class="btn btn-cta" href="<spring:url value="/registration/create" htmlEscape="true"/>"><i class="fa fa-play-circle"></i>Začni registrací</a>
    </div>
</section>

<div class="row cols-wrapper">
    <div class="col-md-3">
        <section class="events">
            <h1 class="section-heading text-highlight"><span class="line">Nadcházející lekce</span></h1>
            <div class="section-content">
            	<!-- Dočasné řešení. Nutné upravit eventsBar pro Sideless layout. -->    
                <%@ include file="/WEB-INF/jsp/sideBar/lessonsWidget.jsp" %>
			</div>
        </section>
    </div>
    <div class="col-md-6">
        <section class="facebook-follow-us">
            <h1 class="section-heading text-highlight"><span class="line">Sledujte nás na Facebooku</span></h1>
            <div class="section-content">
                <div class="fb-page" data-href="https://www.facebook.com/FitSpirit" data-width="500" data-hide-cover="false" data-show-facepile="false" data-show-posts="false">
                    <div class="fb-xfbml-parse-ignore">
                        <blockquote cite="https://www.facebook.com/FitSpirit">
                            <a href="https://www.facebook.com/FitSpirit">FitSpirit.ca</a>
                        </blockquote>
                    </div>
                </div>
            </div>
        </section>
        <section class="video">
            <h1 class="section-heading text-highlight"><span class="line">Videa z akcí</span></h1>
            <div class="carousel-controls">
                <a class="prev" href="#videos-carousel" data-slide="prev"><i class="fa fa-caret-left"></i></a>
                <a class="next" href="#videos-carousel" data-slide="next"><i class="fa fa-caret-right"></i></a>
            </div>
            <div class="section-content">
                <div id="videos-carousel" class="videos-carousel carousel slide">
                    <div class="carousel-inner">
                        <div class="item active">
                            <iframe class="video-iframe" src="//www.youtube.com/embed/bZGuydQOJWo?rel=0&amp;wmode=transparent" frameborder="0"></iframe>
                        </div>
                        <div class="item">
                            <iframe class="video-iframe" src="//www.youtube.com/embed/xlMXCjiPs88?rel=0&amp;wmode=transparent" frameborder="0"></iframe>
                        </div>
                        <div class="item">
                            <iframe class="video-iframe" src="//www.youtube.com/embed/N660zJLvZ00?rel=0&amp;wmode=transparent" frameborder="0"></iframe>
                        </div>
                    </div>
                </div>
                <p class="description">FitClub SPIRIT každoročně pořádá řadu akcí, do kterých se může zapojit kdokoli z široké veřejnosti. Akce mají zpravidla charakter dobročinné činnosti.</p>
            </div>
        </section>
    </div>
    <div class="col-md-3">
        <section class="links">
            <h1 class="section-heading text-highlight"><span class="line">Profil uživatele</span></h1>			
			<c:choose>
				<c:when test="${empty loggedInUser}">
					<style type="text/css">
					    .avatar {
					        background-image: url(static/images/manSilhouette.png);
					    }
					</style>	
					<div class="section-content">
					    <div class="avatar"></div>
					    <div class="side-barTomSko">
					        <p class="text-muted">Nepřihlášený uživatel</p>
					    </div>
					</div>	
				</c:when>
			  	<c:otherwise>
			    	<style type="text/css">
					    .avatar {
					        background-image: url(static/uploads/userImages/${loggedInUser.profilePhotoName});
					    }
					</style>	
					<div class="section-content">
					    <div class="avatar"></div>
					    <div class="side-barTomSko">
					        <p class="text-muted">${loggedInUser.firstName} ${loggedInUser.lastName}</p>
					        <c:if test="${loggedInUser.userRole.id == 3}">
					        	<p class="text-muted">Kredit: ${loggedInUser.credit} Kč</p>
					        </c:if>
					    </div>
					</div>
				</c:otherwise>
			</c:choose>
        </section>
        <section class="testimonials">
            <h1 class="section-heading text-highlight"><span class="line"> Ohlasy</span></h1>
            <div class="carousel-controls">
                <a class="prev" href="#testimonials-carousel" data-slide="prev"><i class="fa fa-caret-left"></i></a>
                <a class="next" href="#testimonials-carousel" data-slide="next"><i class="fa fa-caret-right"></i></a>
            </div>
            <div class="section-content">
                <div id="testimonials-carousel" class="testimonials-carousel carousel slide">
                    <div class="carousel-inner">
                        <div class="item active">
                            <blockquote class="quote">
                                <p>
                                    <i class="fa fa-quote-left"></i>Jsem nadšená z možnosti pracovat ve skvělém kolektivu týmu Fitness Club SPIRIT. Příjemné prostředí, školení instruktoři, moderní aktivity, ... a mnoho dalších důvodů, proč se k nám klienti rádi vrací.
                                </p>
                            </blockquote>
                            <div class="row">
                                <p class="people col-md-8 col-sm-3 col-xs-8"><span class="name">Marie Michaleová</span><br /><span class="title">Instruktorka pilates</span></p>
                                <img class="profile col-md-4 pull-right" src="static/images/carousels/marus.jpg" alt="Marie Michaelová" />
                            </div>
                        </div>
                        <div class="item">
                            <blockquote class="quote">
                                <p>
                                    <i class="fa fa-quote-left"></i>Chceš zažít pravou Crossfitovou atmosféru? Jsi na správném místě! Oceňuji bohatou výbavu náčiní a dostatek prostoru. Kolektiv FitSpirit tě vyhecuje k tvým nejlepším výkonům. Tak na co čekáš? Registruj se!
                                </p>
                            </blockquote>
                            <div class="row">
                                <p class="people col-md-8 col-sm-3 col-xs-8"><span class="name">Marek Peřina</span><br /><span class="title"> Pravidelný návštěvník</span></p>
                                <img class="profile col-md-4 pull-right" src="static/images/carousels/marek.jpg" alt="Marek Peřina náhled" />
                            </div>
                        </div>
                        <div class="item">
                            <blockquote class="quote">
                                <p>
                                    <i class="fa fa-quote-left"></i>Dlouho jsem hledala místo, kde lidé o cvičení mají opravdu zájem. Znáte to, holky jdou cvičit, aby měli co postnout na facebook a chlapy pokecat než se přesunou do hospody. To neplatí ve Spiritu! Sešla se skvélá parta.
                                </p>
                            </blockquote>
                            <div class="row">
                                <p class="people col-md-8 col-sm-3 col-xs-8"><span class="name">Bára Prejzková</span><br /><span class="title"> Stálá klientka</span></p>
                                <img class="profile col-md-4 pull-right" src="static/images/carousels/bara.jpg" alt="Bára Prejzková náhled" />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/shared/layoutSidelessFooter.jsp" %>