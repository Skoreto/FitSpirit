                    </div>
                    <aside class="page-sidebar col-md-3 col-md-offset-1 col-sm-4 col-sm-offset-1">
                        <!-- ==== PANEL PROFIL UŽIVATELE ==== -->
                        <section class="widget has-divider ">
                            <h3 class="title col-md-offset-1" hidden="hidden">Profil uživatele</h3>
                            <%@ include file="/WEB-INF/jsp/sideBar/profileBarAuthenticated.jsp" %>
                        </section>
                        <!-- ==== PANEL NADCHÃZEJÍCÍ LEKCE ==== -->
                        <section class="events col-md-offset-1">
                            <h3 class="title">Nadcházející lekce</h3>
                            <%@ include file="/WEB-INF/jsp/sideBar/eventsBar.jsp" %>
                        </section>
                    </aside>
                </div>
            </div>
        </div>
    </div>
    </div>
    <footer class="footer">
       <div class="footer-content">
           <div class="container">
               <div class="row">
                   <div class="footer-col col-md-3 col-sm-4 about">
                       <div class="footer-col-inner">
                           <h3>O nás</h3>
                           <ul>
                               <li><a href="#"><i class="fa fa-caret-right"></i>Kdo jsme</a></li>
                               <li><a href="#"><i class="fa fa-caret-right"></i>Kontaktujte nás</a></li>
                               <li><a href="#"><i class="fa fa-caret-right"></i>Cíle projektu</a></li>
                               <li><a href="#"><i class="fa fa-caret-right"></i>Implementované funkce</a></li>
                           </ul>
                       </div>
                   </div>
                   <div class="footer-col col-md-3 col-sm-4 about">
                       <div class="footer-col-inner">
                           <h3>Pravidla</h3>
                           <ul>
                               <li><i class="fa fa-caret-right"></i>Počet rezervací je omezen hodnotou nabitého kreditu klienta.</li>
                               <li><i class="fa fa-caret-right"></i>Rezervaci lze zrušit nejdéle do 6 hodin před zahájením lekce.</li>
                           </ul>
                       </div>
                   </div>
                   <div class="footer-col col-md-3 col-sm-4 about">
                       <div class="footer-col-inner">
                           <h3>Otevírací doba</h3>
                           <table class="col-sm-8">
                               <thead>
                               </thead>
                               <tbody>
                                   <tr>
                                       <td>PO - PÁ </td>
                                       <td>7:00 - 21:30</td>
                                   </tr>
                                   <tr>
                                       <td>SO</td>
                                       <td>9:00 - 20:00</td>
                                   </tr>
                                   <tr>
                                       <td>NE</td>
                                       <td>9:00 - 21:00</td>
                                   </tr>
                               </tbody>
                           </table>
                       </div>
                   </div>
                   <div class="footer-col col-md-3 col-sm-12 contact">
                       <div class="footer-col-inner">
                           <h3>Kontaktujte nás</h3>
                           <div class="row">
                               <p class="adr clearfix col-md-12 col-sm-4">
                                   <i class="fa fa-map-marker pull-left"></i>
                                   <span class="pull-left">
                                       U Struhy 1240<br />
                                       Poděbrady<br />
                                       290 01<br />
                                       Česká republika
                                   </span>
                               </p>
                               <p class="col-md-12 col-sm-4"><i class="fa fa-phone"></i>+420 325 626 700</p>
                               <p class="col-md-12 col-sm-4"><i class="fa fa-envelope"></i><a href="mailto:obsluha@fitspirit.cz">obsluha@fitspirit.cz</a></p>
                           </div>
                       </div>
                   </div>
               </div>
           </div>
       </div>
       <div class="bottom-bar">
           <div class="container">
               <div class="row">
                   <small class="copyright col-md-6 col-sm-12 col-xs-12">Copyright 2016 FitClub SPIRIT Poděbrady | Školní projekt <a href="mailto:tomik.sko@seznam.cz">Tomáš Skořepa</a></small>
                   <ul class="social pull-right col-md-6 col-sm-12 col-xs-12">
                       <li><a href="https://www.facebook.com/FitSpirit"><i class="fa fa-facebook"></i></a></li>
                       <li class="row-end"><a href="https://www.youtube.com/user/FitSpiritgirls"><i class="fa fa-youtube"></i></a></li>
                   </ul>
               </div>
           </div>
       </div>
   </footer>

   <!-- Bootstrap core JavaScript
  ================================================== -->
  <!-- Placed at the end of the document so the pages load faster -->

  <!-- Tut -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
  <!-- jQuery UI nutnÃ© k autocomplete --> <!--  
  <script type="text/javascript" src="@Url.Content("~/Scripts/jquery-ui-1.11.4.min.js")"></script> -->
  <!-- NutnÃ© k DatePickeru a Ajaxu -->
  <script type="text/javascript" src="<spring:url value="/static/scripts/jquery-2.1.3.min.js" htmlEscape="true" />"></script>
  <!-- NutnÃ© k Ajaxu --> <!--  
  <script type="text/javascript" src="@Url.Content("~/Scripts/jquery.unobtrusive-ajax.min.js")"></script>
  <script type="text/javascript" src="@Url.Content("~/Scripts/MicrosoftAjax.js")"></script>
  <script type="text/javascript" src="@Url.Content("~/Scripts/MicrosoftMvcAjax.js")"></script> -->
  <!-- Bootstrap hlavnÃ­-->
  <script type="text/javascript" src="<spring:url value="/static/scripts/bootstrap.min.js" htmlEscape="true" />"></script>
  <!-- Unobtrusive validace formulÃ¡ÅÅ¯ --> <!-- 
  <script type="text/javascript" src="@Url.Content("~/Scripts/jquery.validate.min.js")"></script>
  <script type="text/javascript" src="@Url.Content("~/Scripts/jquery.validate.unobtrusive.min.js")"></script> -->
  <!-- blueimp Bootstrap Gallery, FlexSlider 2.4.0 --> <!-- 
  <script type="text/javascript" src="@Url.Content("~/Scripts/blueimp-gallery/js/jquery.blueimp-gallery.min.js")"></script>  -->
  <script type="text/javascript" src="<spring:url value="/static/scripts/flexslider/jquery.flexslider-min.js" htmlEscape="true" />"></script>
  <!-- VLASTNÃ NASTAVENÃ => Flexslider a carousely hlavnÃ­ strany -->
    <script type="text/javascript" src="<spring:url value="/static/scripts/carousels-configuration.js" htmlEscape="true" />"></script>
</body>
</html>