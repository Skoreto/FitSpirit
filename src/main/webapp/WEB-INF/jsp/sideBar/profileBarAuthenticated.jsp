<c:choose>
	<c:when test="${empty loggedInUser}">
		<style type="text/css">
		    .avatar {
		        background-image: url(../static/images/manSilhouette.png);
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
		        background-image: url(../static/uploads/userImages/${loggedInUser.profilePhotoName});
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