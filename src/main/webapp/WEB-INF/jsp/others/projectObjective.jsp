<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/shared/layoutSidebarHeader.jsp" %>

<header class="page-heading clearfix">
    <h1 class="heading-title pull-left">${pageTitle}</h1>
    <!-- ==== DROBECKOVA NAVIGACE ==== -->
    <div class="breadcrumbs pull-right">           
    </div>
</header>
<div class="page-content">
    <div class="row page-row">
    	<div class="col-md-8 col-sm-7" id="dynamicContent">
		<!-- ==== HLAVNI OBSAH AKTUALNI STRANKY ==== -->

<div class="project-objective">
    <p>
        Cílem projektu bylo vytvořit rezervační a redakční systém pro fiktivní fitness centrum FitSpirit. V interní části aplikace rozlišuje 3 druhy rolí uživatelských účtů s různými oprávněními – klient, instruktor, obsluha. Externí funkce aplikace může využívat také nepřihlášený uživatel.
    </p>

    <img src="<spring:url value="/static/images/others/placeit2.jpg" htmlEscape="true" />" alt="Ukázka responzivity 1" class="img-thumbnail">

    <h2>Nepřihlášený uživatel</h2>
    Každý návštěvník webové aplikace má možnost sledovat aktuální dění ve fitness centru. Má přístup k seznamu vypsaných a uplynulých lekcí, galerii, seznamu druhů aktivit, provozovaných fitness centrem a ceníku. Může zobrazit profily instruktorů, kteří aktuálně ve fitness centru pracují nebo seznam místností, ve kterých se konkrétní lekce odehrávají. Především má možnost registrovat se a přihlásit do systému, kde mu aplikace zpřístupní další funkce.
    <h2>Klient</h2>
    <p>
        Po úspěšné registraci musí klient vyčkat na aktivaci účtu obsluhou. Ta účet aktivuje po ověření údajů klienta na baru fitness centra. Zde si klient také může nabít kredit účtu, který poté uplatní při rezervacích na lekce. Do aplikace se klient přihlásí pod loginem, který mu aplikace vygenerovala, a heslem, které si sám zvolil.
    </p>
    <p>
        V případě, že má klient dostatečný kredit, může se rezervovat na aktuálně vypsané lekce. Za každou registraci se mu odečte kredit v hodnotě ceny lekce dané aktivity. Rezervaci lze zrušit nejdéle do 6 hodin před zahájením lekce, přičemž se odečtený kredit přičte v plné výši zpět.
    </p>
    <p>
        Správu rezervací provádí v podsekcích Lekce a Rezervace.
    </p>

    <img src="<spring:url value="/static/images/others/placeit4.jpg" htmlEscape="true" />" alt="Ukázka responzivity 3" class="img-thumbnail">

    <h2>Instruktor</h2>
    <p>
        Účet nového instruktora FitSpirit vytvoří obsluha. Instruktor má poté možnost vypsat novou lekci do systému – zvolí čas zahájení/ukončení, druh aktivity, místnost konání, kapacitu lekce a případně doplní poznámku. Lekce, na které dosud není přihlášen žádný klient, má možnost upravit či zrušit.
    </p>
    <h2>Obsluha</h2>
    <p>
        Obsluha spravuje veškeré aktivity, místnosti, fotografie v galerii – to zahrnuje možnosti přidávání, úprav, deaktivace a mazání. V sekci Lekce má privilegia pro úpravy lekcí v případě, že původní instruktor například onemocní, ale lekci převezme instruktor náhradní. Obdobné náhlé změny lze řešit pouze přes obsluhu.
    </p>
    <p>
        Obsluha dále udržuje evidenci o klientech, instruktorech a uživatelích v roli obsluhy. Mimo jiné má možnost vyhledat účet klienta, aktivovat ho a dobít uhrazený kredit. Obdobně má možnost deaktivovat účet instruktora. Implementováno je také rozhraní pro správu uživatelských rolí.
    </p>

    <img src="<spring:url value="/static/images/others/placeit6.jpg" htmlEscape="true" />" alt="Ukázka responzivity 6" class="img-thumbnail">

    <h2>Orientační Use Case Diagram</h2>
    <a href="<spring:url value="/static/images/others/UseCaseDiagram.jpg" htmlEscape="true" />"><img src="<spring:url value="/static/images/others/UseCaseDiagram.jpg" htmlEscape="true" />" alt="Orientační Use Case Diagram projektu"></a>
</div>

<%@ include file="/WEB-INF/jsp/shared/layoutSidebarFooter.jsp" %>