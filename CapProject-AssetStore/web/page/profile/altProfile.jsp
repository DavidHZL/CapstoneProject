<%-- 
    Document   : altProfile
    Created on : Apr 22, 2022, 2:56:26 PM
    Author     : Dadvid
--%>

<jsp:include page="/page/link/header-main.jsp"/>
<main>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script type="text/javascript" src="/CapProject-AssetStore/page/profile/altProfile.js"></script>
    <script type="text/javascript">
        var altUserProfile = JSON.parse('${altProfile}');
    </script>
    <div id="profileContent">
            
    </div>
</main>
<jsp:include page="/page/link/footer.jsp"/>